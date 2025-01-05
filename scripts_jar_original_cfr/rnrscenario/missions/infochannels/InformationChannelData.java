/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.infochannels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.xpath.XPathExpression;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import rnrorg.MissionEventsMaker;
import rnrscenario.missions.AddItemVehicle;
import rnrscenario.missions.IAddItem;
import rnrscenario.missions.IStartMissionListener;
import rnrscenario.missions.MissionCreationContext;
import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.PriorityTable;
import rnrscenario.missions.infochannels.DelayedChannel;
import rnrscenario.missions.infochannels.IPlacableChannel;
import rnrscenario.missions.infochannels.InfoChannelEventsListener;
import rnrscenario.missions.infochannels.InformationChannel;
import rnrscenario.missions.infochannels.InformationChannelDataCreationException;
import rnrscenario.missions.infochannels.InformationChannelsFactory;
import rnrscenario.missions.infochannels.NewspaperChannel;
import rnrscenario.missions.infochannels.NoSuchChannelException;
import rnrscenario.missions.infochannels.RadioChannel;
import rnrscenario.scenarioscript;
import rnrscr.IMissionInformation;
import rnrscr.MissionDialogs;
import rnrscr.MissionInfoNotDialog;
import rnrscr.SODialogInformation;
import scenarioMachine.FiniteStateMachine;
import scenarioXml.FiniteStatesSetBuilder;
import scenarioXml.ObjectProperties;
import scenarioXml.XmlFilter;
import scenarioXml.XmlNodeDataProcessor;
import scriptActions.ScriptAction;
import scriptEvents.EventsController;
import xmlutils.NodeList;
import xmlutils.XmlUtils;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class InformationChannelData
implements IPlacableChannel {
    private static final String TAG_APPEAR = "appear";
    private static final String TAG_ACCEPT = "accept";
    private static final String TAG_DECLINE = "decline";
    private static final int INITIAL_MAP_CAPACITY = 3;
    private String channelName = null;
    private String resource = null;
    private String npc = null;
    private ArrayList<IAddItem> additem = new ArrayList();
    private ArrayList<String> pointsNames = new ArrayList();
    private boolean is_channel_marked_as_main = false;
    private boolean is_channel_marked_as_success = false;
    private boolean is_channel_marked_as_finish = false;
    private String uid = null;
    private String missionname_for_resources = null;
    private ArrayList<String> missionsBegunByThisChannel = null;
    private Object dataForChannelConstruct = null;
    private final Map<String, List<ScriptAction>> actionsOnEvents = new HashMap<String, List<ScriptAction>>(3);
    static XPathExpression expr;
    private static XmlFilter filter;
    private static XmlFilter actionsFilter;

    public String getUid() {
        return this.uid;
    }

    public boolean is_start() {
        return !this.is_channel_marked_as_finish;
    }

    public boolean is_add_item() {
        return !this.additem.isEmpty();
    }

    public boolean is_finish_delay() {
        if (this.is_channel_marked_as_main) {
            return false;
        }
        if (this.is_channel_marked_as_finish) {
            return true;
        }
        return true;
    }

    private void loadActionsOnEvent(Node source, String nodeName) {
        assert (null != source) : "source must be non-null reference";
        assert (null != nodeName) : "nodeName must be non-null reference";
        List<ScriptAction> destination = this.actionsOnEvents.get(nodeName);
        assert (null != destination) : "invalid nodeName, must be one of TAG_APPEAR, TAG_ACCEPT or TAG_DECLINE";
        Node rootOfActionList = new XmlFilter(source.getChildNodes()).nodeNameNext(nodeName);
        FiniteStateMachine scenarioMachine = scenarioscript.script.getScenarioMachine();
        XmlFilter actionFilter = new XmlFilter(rootOfActionList.getChildNodes());
        ArrayList<ObjectProperties> actionsOnStartRawInfo = ObjectProperties.extractListEx(actionFilter);
        destination.addAll(FiniteStatesSetBuilder.actionsFromActionList(actionsOnStartRawInfo, MissionInfo.getLoadingMissionName(), scenarioMachine));
    }

    static List<Node> getStartMissionActions(Node root) {
        if (null == filter) {
            filter = new XmlFilter(root.getChildNodes());
            actionsFilter = new XmlFilter(root.getChildNodes());
        } else {
            filter.reset(root.getChildNodes());
        }
        final ArrayList<Node> result = new ArrayList<Node>();
        Node childActionsHolder = filter.nextElement();
        while (null != childActionsHolder) {
            actionsFilter.reset(childActionsHolder.getChildNodes());
            actionsFilter.visitAllNodes("action", new XmlNodeDataProcessor(){

                public void process(Node target, Object param) {
                    if (null != target.getAttributes().getNamedItem("startmission")) {
                        result.add(target);
                    }
                }
            }, null);
            childActionsHolder = filter.nextElement();
        }
        return result;
    }

    private ArrayList<String> getMissionsBegunByChannel(Node node) {
        ArrayList<String> res = new ArrayList<String>();
        List<Node> actonslist = InformationChannelData.getStartMissionActions(node);
        for (Node nodeaction : actonslist) {
            NamedNodeMap actionattr = nodeaction.getAttributes();
            Node actionname = actionattr.getNamedItem("name");
            String mission_started = actionname.getNodeValue();
            if (0 == "this".compareTo(mission_started)) continue;
            res.add(mission_started);
        }
        return res;
    }

    public InformationChannelData(String channel_name, String resource_ref, Object dataForChannelConstruct, List<ScriptAction> on_appear, List<ScriptAction> on_accept, List<ScriptAction> on_decline, MissionCreationContext context) {
        context.enterChannel(channel_name);
        this.uid = context.getChannelUid(channel_name);
        this.actionsOnEvents.put(TAG_APPEAR, on_appear);
        this.actionsOnEvents.put(TAG_ACCEPT, on_accept);
        this.actionsOnEvents.put(TAG_DECLINE, on_decline);
        this.is_channel_marked_as_main = true;
        this.is_channel_marked_as_success = false;
        this.channelName = channel_name;
        this.resource = resource_ref;
        this.dataForChannelConstruct = dataForChannelConstruct;
        this.missionsBegunByThisChannel = new ArrayList();
    }

    public InformationChannelData(Node source, MissionCreationContext context) throws InformationChannelDataCreationException {
        assert (null != source) : "target must be non-null reference";
        this.missionsBegunByThisChannel = this.getMissionsBegunByChannel(source);
        this.actionsOnEvents.put(TAG_APPEAR, new LinkedList());
        this.actionsOnEvents.put(TAG_ACCEPT, new LinkedList());
        this.actionsOnEvents.put(TAG_DECLINE, new LinkedList());
        this.is_channel_marked_as_main = null != source.getAttributes().getNamedItem("main");
        boolean bl = this.is_channel_marked_as_success = null != source.getAttributes().getNamedItem("success");
        if (this.is_channel_marked_as_success) {
            this.is_channel_marked_as_finish = true;
        } else if (this.is_channel_marked_as_main) {
            this.is_channel_marked_as_finish = true;
        }
        Node nameNode = source.getAttributes().getNamedItem("name");
        Node resourceNode = source.getAttributes().getNamedItem("resource");
        boolean nameExist = XmlFilter.textContentExists(nameNode);
        boolean resourceExist = XmlFilter.textContentExists(resourceNode);
        if (!nameExist || !resourceExist) {
            throw new InformationChannelDataCreationException("found invalid channel node: invalid attributes");
        }
        this.channelName = nameNode.getTextContent();
        context.enterChannel(this.channelName);
        this.uid = context.getChannelUid(this.channelName);
        this.resource = resourceNode.getTextContent();
        this.loadActionsOnEvent(source, TAG_APPEAR);
        this.loadActionsOnEvent(source, TAG_ACCEPT);
        this.loadActionsOnEvent(source, TAG_DECLINE);
        new XmlFilter(source.getChildNodes()).visitAllNodes("point", new XmlNodeDataProcessor(){

            public void process(Node target, Object param) {
                Node nameAttribute = target.getAttributes().getNamedItem("name");
                if (XmlFilter.textContentExists(nameAttribute)) {
                    String tPointName = nameAttribute.getTextContent();
                    if (!InformationChannelData.this.pointsNames.contains(tPointName)) {
                        InformationChannelData.this.pointsNames.add(tPointName);
                    }
                }
            }
        }, null);
        Node npcNode = source.getAttributes().getNamedItem("npc");
        if (XmlFilter.textContentExists(npcNode)) {
            this.npc = npcNode.getTextContent();
        }
        xmlutils.Node adds = new xmlutils.Node(source);
        NodeList list_ads = adds.getNamedChildren("add_item");
        for (xmlutils.Node item : list_ads) {
            if (!item.hasAttribute("AddItemVehicle")) continue;
            String color = item.getAttribute("color");
            AddItemVehicle vehitem = new AddItemVehicle(item.getAttribute("model"), color != null ? color : "0");
            this.additem.add(vehitem);
        }
    }

    public InformationChannel makeWare(String missionSourceName, String finishPoint, String questItemPlacement, IStartMissionListener listener) throws NoSuchChannelException {
        InformationChannel ware = InformationChannelsFactory.getInstance().construct(this.channelName, this.dataForChannelConstruct);
        ware.setMainChannel(this.is_channel_marked_as_main);
        ware.setFinishChannel(this.is_channel_marked_as_finish);
        ware.setUid(this.uid);
        ware.setIdentitie(this.npc);
        ware.setImmediateChannel(this.isImmediate());
        ware.addPlaces(this.pointsNames);
        InfoChannelEventsListener channelsListener = new InfoChannelEventsListener(ware.getUid());
        ArrayList<ScriptAction> onend = new ArrayList<ScriptAction>();
        onend.add(new RemoveResources(this.missionname_for_resources, this.uid, ware));
        channelsListener.useCallbacks(this.getOnAccept(), this.getOnAppear(), this.getOnDecline(), onend, InformationChannelsFactory.getInstance().getCloseChannelInfo(this.channelName));
        EventsController.getInstance().addListener(channelsListener);
        return ware;
    }

    public InformationChannel makeWare() throws NoSuchChannelException {
        InformationChannel ware = InformationChannelsFactory.getInstance().construct(this.channelName, this.dataForChannelConstruct);
        ware.setMainChannel(this.is_channel_marked_as_main);
        ware.setFinishChannel(this.is_channel_marked_as_finish);
        ware.setUid(this.uid);
        ware.setIdentitie(this.npc);
        ware.setImmediateChannel(this.isImmediate());
        ware.addPlaces(this.pointsNames);
        if (ware instanceof DelayedChannel) {
            ((DelayedChannel)ware).immediatelyPost(this.is_channel_marked_as_main);
        }
        InfoChannelEventsListener channelsListener = new InfoChannelEventsListener(ware.getUid());
        ArrayList<ScriptAction> onEnd = new ArrayList<ScriptAction>();
        onEnd.add(new RemoveResources(this.missionname_for_resources, this.uid, ware));
        channelsListener.useCallbacks(this.getOnAccept(), this.getOnAppear(), this.getOnDecline(), onEnd, InformationChannelsFactory.getInstance().getCloseChannelInfo(this.channelName));
        EventsController.getInstance().addListener(channelsListener);
        return ware;
    }

    public List<String> getPlacesNames() {
        return Collections.unmodifiableList(this.pointsNames);
    }

    public String getChannelName() {
        return this.channelName;
    }

    public String getResource() {
        return this.resource;
    }

    public String getNpc() {
        return this.npc;
    }

    private List<ScriptAction> getOnAccept() {
        return this.actionsOnEvents.get(TAG_ACCEPT);
    }

    private List<ScriptAction> getOnAppear() {
        return this.actionsOnEvents.get(TAG_APPEAR);
    }

    private List<ScriptAction> getOnDecline() {
        return this.actionsOnEvents.get(TAG_DECLINE);
    }

    public boolean hasAcceptAction() {
        return !this.getOnAccept().isEmpty();
    }

    public void addOnAcceptAction(ScriptAction action) {
        if (null != action) {
            this.getOnAccept().add(action);
        }
    }

    IMissionInformation constructMissionInfo(boolean is_dialog, String uid, String mission_name, String resource, String identitie, boolean is_FinishChannel, ArrayList<String> dependantMissions) {
        if (!is_dialog) {
            return new MissionInfoNotDialog(uid, mission_name, resource, false, is_FinishChannel, dependantMissions);
        }
        return new SODialogInformation(uid, mission_name, resource, identitie, false, is_FinishChannel, dependantMissions);
    }

    public void placeResources(String mission_name, boolean missionImportant, boolean channelOnFinish) {
        if (!MissionSystemInitializer.getMissionsManager().isLoading()) {
            PriorityTable prior = MissionSystemInitializer.getMissionsManager().getPriorityTable();
            this.is_channel_marked_as_finish = channelOnFinish;
            MissionEventsMaker.createChannelResourcesPlaces(mission_name, this.uid, this.pointsNames, prior.getPriority(mission_name), missionImportant, channelOnFinish, this.is_channel_marked_as_success, this.is_channel_marked_as_main);
            if (channelOnFinish && this.is_channel_marked_as_success && InformationChannelsFactory.getInstance().isBoundedChannel(this.channelName)) {
                MissionEventsMaker.RegisterSuccesMissionChannelAsBounding(mission_name, this.uid);
            }
        }
        boolean is_dialog = InformationChannelsFactory.getInstance().isDialogChannel(this.channelName);
        MissionDialogs.AddDialog(this.constructMissionInfo(is_dialog, this.uid, mission_name, this.resource, this.npc, channelOnFinish, this.missionsBegunByThisChannel));
        this.missionname_for_resources = mission_name;
        if (!MissionSystemInitializer.getMissionsManager().isLoading()) {
            for (IAddItem item : this.additem) {
                item.place(mission_name, this.uid);
            }
            if (null != this.npc && "CbvChannel".compareTo(this.getChannelName()) != 0 && "CarDialogChannel".compareTo(this.getChannelName()) != 0) {
                MissionEventsMaker.createQuestItemNPC(mission_name, this.npc, this.uid);
            }
        }
    }

    public boolean isMarkedAsMain() {
        return this.is_channel_marked_as_main;
    }

    public boolean isImmediate() {
        return InformationChannelsFactory.getInstance().isImmediateChannel(this.channelName);
    }

    static {
        try {
            expr = XmlUtils.getXPath().compile("descendant::action[@startmission]");
        }
        catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    private static class RemoveResources
    extends ScriptAction {
        static final long serialVersionUID = 0L;
        private String resource = null;
        private String uid = null;
        InformationChannel informationChannel = null;

        RemoveResources(String resourceName, String resourceUid, InformationChannel finishChannel) {
            this.resource = resourceName;
            this.uid = resourceUid;
            this.informationChannel = finishChannel;
        }

        public void act() {
            MissionEventsMaker.clearResource(this.resource, this.uid);
            if (this.informationChannel != null && !(this.informationChannel instanceof RadioChannel) && !(this.informationChannel instanceof NewspaperChannel)) {
                this.informationChannel.dispose();
            }
        }
    }
}

