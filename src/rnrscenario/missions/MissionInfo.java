// Decompiled with: CFR 0.152
// Class Version: 5
package rnrscenario.missions;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;

import org.w3c.dom.Node;

import rickroll.log.RickRollLog;
import rnrloggers.MissionsLogger;
import rnrorg.IStoreorgelement;
import rnrorg.MissionEventsMaker;
import rnrorg.MissionOrganiser;
import rnrorg.Organizers;
import rnrscenario.missions.IChannelEventListener;
import rnrscenario.missions.IMissionStarter;
import rnrscenario.missions.IStartMissionListener;
import rnrscenario.missions.MissionCreationContext;
import rnrscenario.missions.MissionPhase;
import rnrscenario.missions.MissionStartActions;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.MissionXmlStrings;
import rnrscenario.missions.QuestItem;
import rnrscenario.missions.SingleMission;
import rnrscenario.missions.StartMissionListeners;
import rnrscenario.missions.UniqueNamesGenerator;
import rnrscenario.missions.infochannels.InformationChannelData;
import rnrscenario.missions.map.Place;
import scenarioUtils.AdvancedClass;
import scenarioXml.ObjectProperties;
import scenarioXml.XmlFilter;
import scriptActions.FailOrgAction;
import scriptActions.FinishOrgAction;
import scriptActions.MissionActionRemoveResourcesFade;
import scriptActions.ScriptAction;
import scriptActions.StartOrgAction;
import scriptEvents.EventsController;
import scriptEvents.MissionEndchecker;
import scriptEvents.MissionStartedOnPoint;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class MissionInfo
implements IStartMissionListener,
IChannelEventListener {
    private IMissionStarter starter = new CommonStarter();
    private static UniqueNamesGenerator namesGenerator = null;
    private static final int ERROR_MESSAGE_CAPACITY = 64;
    private static final int ARRAYS_CAPACITY = 8;
    private List<MissionPhase> endInfo = new ArrayList<MissionPhase>(8);
    private MissionPhase startInfo = null;
    private List<InformationChannelData> allChannels = new ArrayList<InformationChannelData>(8);
    private List<QuestItem> questItem = null;
    private Set<String> dependentMissions = new HashSet<String>();
    private String name = null;
    private boolean missionDependFromOther = false;
    private boolean missionDependFromOtherByActivation = false;
    private boolean scenarioMission = false;
    private boolean selfPlacedMission = false;
    private boolean missionIsUnique = false;
    private boolean missionIsBounded = false;
    private String description = null;
    private String externalId = null;
    private String finishPoint = null;
    private String lastChnnelEventPoint = null;
    private boolean isLastChannelImmediate = false;
    private String missionStartPlaceName = null;
    private static String loading_name;

    public boolean hasAcceptAction() {
        for (InformationChannelData ch : this.allChannels) {
            if (!ch.hasAcceptAction()) continue;
            return true;
        }
        return false;
    }

    public int getNeedParking(String point, boolean withloaditem) {
        int np = 0;
        for (InformationChannelData id : this.allChannels) {
            Place place = MissionSystemInitializer.getMissionsMap().getPlace(point);
            if (place.getTip() != 0 || !id.getPlacesNames().contains(point)) continue;
            if (id.is_start() && withloaditem) {
                for (QuestItem qi : this.questItem) {
                    if (!qi.isSemitrailer()) continue;
                    ++np;
                }
            }
            if (!id.is_add_item()) continue;
            ++np;
        }
        return np;
    }

    public boolean isScenario() {
        return this.scenarioMission;
    }

    public static void init() {
        namesGenerator = new UniqueNamesGenerator("mission");
    }

    public static void deinit() {
        namesGenerator = null;
    }

    public static String getLoadingMissionName() {
        return loading_name;
    }

    public void setStarter(IMissionStarter to_set) {
        this.starter = to_set;
    }

    public List<MissionPhase> getEndPhase() {
        return this.endInfo;
    }

    private QuestItem makeWare(ObjectProperties source) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        assert (null != source) : "source must be valid non-null reference";
        
        
        // RICK
    	// RickRollLog.log("MissionInfo QuestItem makeWare for name: " + source.getName());
        // END RICK
    	
    	
        String questItemClassName = source.getName();
        if (null == questItemClassName || 0 == questItemClassName.length()) {
            MissionsLogger.getInstance().doLog("found quest item node without name; ignored", Level.WARNING);
            return null;
        }
        AdvancedClass classSearcher = new AdvancedClass(questItemClassName, MissionXmlStrings.PACKAGES_WITH_QUEST_ITEMS);
        Class questItemClass = classSearcher.getInternal();
        Constructor creator = questItemClass.getDeclaredConstructor(new Class[0]);
        creator.setAccessible(true);
        QuestItem ware = (QuestItem)creator.newInstance(new Object[0]);
        
        
        Set<Entry<Object, Object>>  properties = source.getParams().entrySet();
        for (Map.Entry property : properties) {
            try {
                Field fieldToSet = questItemClass.getDeclaredField((String)property.getKey());
                fieldToSet.setAccessible(true);
                fieldToSet.set(ware, property.getValue());
                
                
                // RICK
//            	RickRollLog.log("MissionInfo QuestItem makeWare for name: " 
//                + source.getName() 
//                + "; Set field: " + (String)property.getKey()
//                + "; Set value: " + property.getValue());
                // END RICK
            	
            	
            }
            catch (NoSuchFieldException e) {
                StringBuilder errorMessage = new StringBuilder(64);
                errorMessage.append("Illegal data in XML, field ");
                errorMessage.append((String)property.getKey());
                errorMessage.append(" hasn't been found; exception: ");
                errorMessage.append(e.getLocalizedMessage());
                MissionsLogger.getInstance().doLog(errorMessage.toString(), Level.SEVERE);
            }
            catch (IllegalAccessException e) {
                MissionsLogger.getInstance().doLog("Access denided to field; exception: " + e.getLocalizedMessage(), Level.SEVERE);
            }
        }
        return ware;
    }

    private void processInternalError(Exception e) {
        assert (null != e) : "e must be valid non-null reference";
        StringBuilder errorMessage = new StringBuilder(64);
        errorMessage.append("Internal error; exception message: ");
        errorMessage.append(e.getLocalizedMessage());
        MissionsLogger.getInstance().doLog(errorMessage.toString(), Level.SEVERE);
    }

    private void logErrorMessage(String prefex, ObjectProperties badSource, Exception e) {
        assert (null != prefex) : "prefex must be valid non-null reference";
        assert (null != badSource) : "badSource must be valid non-null reference";
        assert (null != e) : "e must be valid non-null reference";
        StringBuilder errorMessage = new StringBuilder(64);
        errorMessage.append(prefex);
        errorMessage.append(badSource.getName());
        errorMessage.append("'; exception message: ");
        errorMessage.append(e.getLocalizedMessage());
        MissionsLogger.getInstance().doLog(errorMessage.toString(), Level.SEVERE);
    }

    private QuestItem constructQuestItem(ObjectProperties source) {
        assert (null != source) : "source must be valid non-null reference";
        try {
            return this.makeWare(source);
        }
        catch (ClassNotFoundException e) {
            this.logErrorMessage("Illegal data in XML, can't find quest item with name ' ", source, e);
        }
        catch (NoSuchMethodException e) {
            this.logErrorMessage("Internal error, constructor with no arguments hasn't been realized in class ' ", source, e);
        }
        catch (IllegalAccessException e) {
            this.processInternalError(e);
        }
        catch (InvocationTargetException e) {
            this.processInternalError(e);
        }
        catch (InstantiationException e) {
            this.processInternalError(e);
        }
        return null;
    }

    private void loadQuestItems(Node source) {
        if (null != source) {
            XmlFilter dataMiner = new XmlFilter(source.getChildNodes());
            ArrayList<ObjectProperties> questItemsProperties = ObjectProperties.extractListEx(dataMiner, "quest_item");
            this.questItem = new ArrayList<QuestItem>(questItemsProperties.size());
            for (ObjectProperties properties : questItemsProperties) {
                QuestItem ware = this.constructQuestItem(properties);
                if (null == ware) continue;
                
                
                
                // RICK
            	// RickRollLog.log("MissionInfo loadQuestItems; Added ware: " + properties.getName());
                // END RICK
            	
            	
                this.questItem.add(ware);
            }
        } else {
            this.questItem = new ArrayList<QuestItem>();
        }
    }

    private static boolean loadBooleanFlagFromAttribute(Node attributesSource, String attributeName) {
        assert (null != attributesSource) : "attributesSource must be non-null reference";
        assert (null != attributeName) : "attributeName must be non-null reference";
        Node attributeNode = attributesSource.getAttributes().getNamedItem(attributeName);
        return null != attributeNode && Boolean.parseBoolean(attributeNode.getTextContent());
    }

    private static String loadStringAttribute(Node attributesSource, String attributeName) {
        assert (null != attributesSource) : "attributesSource must be non-null reference";
        assert (null != attributeName) : "attributeName must be non-null reference";
        Node attributeNode = attributesSource.getAttributes().getNamedItem(attributeName);
        if (XmlFilter.textContentExists(attributeNode)) {
            return attributeNode.getTextContent();
        }
        return null;
    }

    public MissionInfo(String mission_name, String finishPointName, String organizerName, QuestItem qi, List<InformationChannelData> start_channels, List<ScriptAction> start_actions, List<ScriptAction> success_actions, List<ScriptAction> damaged_actions, List<ScriptAction> decline_actions, List<ScriptAction> timeoutcomplete_actions, List<ScriptAction> timeoutpickup_actions) {
    	
    	// RICK
    	// RickRollLog.log("MissionInfo 2 with mission_name: " + mission_name);
        // END RICK
    	
        this.finishPoint = finishPointName;
        this.missionDependFromOther = false;
        this.missionDependFromOtherByActivation = false;
        this.missionIsUnique = true;
        this.missionIsBounded = false;
        this.description = "null";
        this.externalId = "null";
        loading_name = this.name = mission_name;
        MissionOrganiser.getInstance().addMission(this.name, organizerName);
        if (this.questItem == null) {
            this.questItem = new ArrayList<QuestItem>();
        }
        this.questItem.add(qi);
        this.startInfo = new MissionPhase(start_channels, start_actions, null);
        this.allChannels.addAll(this.startInfo.getInfoChannels());
        ArrayList<ScriptAction> load_actions = new ArrayList<ScriptAction>();
        load_actions.add(new StartOrgAction(organizerName));
        MissionStartActions START = new MissionStartActions(this.name, organizerName, load_actions);
        EventsController.getInstance().addListener(START);
        MissionEndchecker checker = new MissionEndchecker("success");
        ArrayList<ScriptAction> load_actions2 = new ArrayList<ScriptAction>();
        load_actions2.add(new FinishOrgAction(organizerName));
        load_actions2.add(new MissionActionRemoveResourcesFade());
        load_actions2.addAll(success_actions);
        MissionPhase phase = new MissionPhase(new ArrayList<InformationChannelData>(), load_actions2, checker);
        this.endInfo.add(phase);
        String[] fail_names = new String[]{"fail damaged", "decline", "fail timeout complete", "fail timeout pickup"};
        int DAMAGED = 0;
        int DECLINE = 1;
        int TO_COMPLETE = 2;
        int TO_PICKUP = 3;
        int[] fail_types = new int[]{2, 3, 1, 0};
        for (int i = 0; i < fail_names.length; ++i) {
            MissionEndchecker checker2 = new MissionEndchecker(fail_names[i]);
            ArrayList<ScriptAction> load_actions3 = new ArrayList<ScriptAction>();
            FailOrgAction act = new FailOrgAction(organizerName);
            act.type_fail = fail_types[i];
            load_actions3.add(act);
            if (i == DAMAGED) {
                load_actions3.addAll(damaged_actions);
            } else if (i == DECLINE) {
                load_actions3.addAll(decline_actions);
            } else if (i == TO_COMPLETE) {
                load_actions3.addAll(timeoutcomplete_actions);
            } else if (i == TO_PICKUP) {
                load_actions3.addAll(timeoutpickup_actions);
            }
            MissionPhase phase2 = new MissionPhase(new ArrayList<InformationChannelData>(), load_actions3, checker2);
            this.endInfo.add(phase2);
        }
        loading_name = null;
    }

    public MissionInfo(Node dataSource, String finishPointName) {
    	
    	// RICK
    	// RickRollLog.log("MissionInfo 1");
        // END RICK
    	
        Node nameAttributeNode;
        assert (null != dataSource) : "name must be non-null reference";
        assert (null != finishPointName) : "finishPointName must be non-null reference";
        this.finishPoint = finishPointName;
        this.missionDependFromOther = MissionInfo.loadBooleanFlagFromAttribute(dataSource, "depend");
        this.missionDependFromOtherByActivation = MissionInfo.loadBooleanFlagFromAttribute(dataSource, "dependByActivation");
        this.scenarioMission = MissionInfo.loadBooleanFlagFromAttribute(dataSource, "scenarioMission");
        this.missionIsUnique = MissionInfo.loadBooleanFlagFromAttribute(dataSource, "uniq");
        this.missionIsBounded = MissionInfo.loadBooleanFlagFromAttribute(dataSource, "bounded");
        this.description = MissionInfo.loadStringAttribute(dataSource, "description");
        this.externalId = MissionInfo.loadStringAttribute(dataSource, "id");
        String organizerName = MissionInfo.loadStringAttribute(dataSource, "org");
        if (null == organizerName) {
            organizerName = "unknown";
        }
        this.name = XmlFilter.textContentExists(nameAttributeNode = dataSource.getAttributes().getNamedItem("name")) ? nameAttributeNode.getTextContent() : namesGenerator.getName();
        loading_name = this.name;
        MissionOrganiser.getInstance().addMission(this.name, organizerName);
        MissionCreationContext context = new MissionCreationContext(this.name);
        this.loadQuestItems(dataSource);
        context.enterPhase();
        this.startInfo = new MissionPhase(dataSource, organizerName, context);
        context.exitPhase();
        this.allChannels.addAll(this.startInfo.getInfoChannels());
        MissionStartActions START = new MissionStartActions(this.name, organizerName);
        START.extractActions(dataSource, "start");
        EventsController.getInstance().addListener(START);
        XmlFilter nodeMiner = new XmlFilter(dataSource.getChildNodes());
        Node endNode = nodeMiner.nodeNameNext("end");
        while (null != endNode) {
            context.enterPhase();
            MissionPhase phase = new MissionPhase(endNode, organizerName, context);
            context.exitPhase();
            this.endInfo.add(phase);
            this.allChannels.addAll(phase.getInfoChannels());
            endNode = nodeMiner.nodeNameNext("end");
        }
        this.loadDependences(nodeMiner);
        loading_name = null;
    }

    private void loadDependences(XmlFilter nodeMiner) {
        assert (null != nodeMiner) : "nodeMiner must be non-null reference";
        Node dependencesRootNode = nodeMiner.goOnStart().nodeNameNext("dependences");
        if (null != dependencesRootNode && 0 < dependencesRootNode.getChildNodes().getLength()) {
            nodeMiner = new XmlFilter(dependencesRootNode.getChildNodes());
            Node dependentMissionNode = nodeMiner.nodeNameNext("parentFor");
            while (null != dependentMissionNode) {
                Node dependentMissionNameNode = dependentMissionNode.getAttributes().getNamedItem("mission");
                if (XmlFilter.textContentExists(dependentMissionNameNode)) {
                    this.dependentMissions.add(dependentMissionNameNode.getTextContent());
                }
                dependentMissionNode = nodeMiner.nodeNameNext("parentFor");
            }
        }
    }

    public String getName() {
        return this.name;
    }

    void createItems() {
        for (QuestItem item : this.questItem) {
            item.doPlace(this.name);
        }
    }

    void executeStartActions() {
        this.startInfo.execute();
    }

    SingleMission constructMission() {
    	
    	// RICK
    	// RickRollLog.log("MissionInfo SingleMission constructMission() name: " + this.name);
        // END RICK
    	
    	
    	
    	
        SingleMission ware = new SingleMission(this.name, this.endInfo);
        ware.setSerializationUid(this.name);
        return ware;
    }

    public boolean isDepend() {
        return this.missionDependFromOther;
    }

    public boolean isDependByActivation() {
        return this.missionDependFromOtherByActivation;
    }

    public boolean hasStartDependence() {
        return this.missionDependFromOtherByActivation || this.missionDependFromOther;
    }

    public void setScenarioMission(boolean scenarioMission) {
        this.scenarioMission = scenarioMission;
    }

    public boolean isScenarioMission() {
        return this.scenarioMission;
    }

    public boolean isSelfPlacedMission() {
        return this.selfPlacedMission;
    }

    public void setSelfPlacedMission(boolean value) {
        this.selfPlacedMission = value;
    }

    public boolean isUnique() {
        return this.missionIsUnique;
    }

    public boolean isBounded() {
        return this.missionIsBounded;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public String getFinishPoint() {
        return this.finishPoint;
    }

    public String getQuestItemPlacement() {
        if (this.questItem.isEmpty()) {
            MissionsLogger.getInstance().doLog("no quest items loaded for mission " + this.name, Level.SEVERE);
            return null;
        }
        return this.questItem.get(0).getPlacement();
    }

    public String getDescription() {
        return this.description;
    }

    public List<InformationChannelData> getInfoStartChannels() {
        return this.startInfo.getInfoChannels();
    }

    public List<InformationChannelData> getInfoEndChannels() {
        ArrayList<InformationChannelData> res = new ArrayList<InformationChannelData>();
        for (MissionPhase phase : this.endInfo) {
            res.addAll(phase.getInfoChannels());
        }
        return res;
    }

    public List<InformationChannelData> getAllChannels() {
        return Collections.unmodifiableList(this.allChannels);
    }

    public boolean isImportant() {
        IStoreorgelement org;
        boolean isMissionImportant = false;
        String orgName = MissionOrganiser.getInstance().getOrgForMission(this.name);
        if (null != orgName && null != (org = Organizers.getInstance().get(orgName))) {
            isMissionImportant = org.isImportant();
        }
        return isMissionImportant;
    }

    //@Override
    public String missionStarted() {
        EventsController.getInstance().eventHappen(new MissionStartedOnPoint(this.name, this.lastChnnelEventPoint));
        return this.starter.startMission(this.name, this.isLastChannelImmediate, this.lastChnnelEventPoint, this.getQuestItemPlacement(), this.finishPoint);
    }

    //@Override
    public void missionStartedOnPoint(String missionStartPlaceName) {
    	
    	// RICK
    	// RickRollLog.log("MissionInfo missionStarted with startplacename");
        // END RICK
    	
    	
        EventsController.getInstance().eventHappen(new MissionStartedOnPoint(this.name, this.lastChnnelEventPoint));
        this.starter.startMission(this.name, this.isLastChannelImmediate, this.lastChnnelEventPoint, this.getQuestItemPlacement(), this.finishPoint, missionStartPlaceName);
    }

    //@Override
    public void eventOnChannel(String pointName, String channelUid, boolean isChannelImmediate) {
    	
    	
    	// RICK
    	// RickRollLog.log("MissionInfo eventOnChannel");
        // END RICK
    	
    	
        this.lastChnnelEventPoint = pointName;
        this.isLastChannelImmediate = isChannelImmediate;
        for (String mission_name : this.dependentMissions) {
            IChannelEventListener lst;
            if (mission_name.compareTo(this.name) == 0 || (lst = StartMissionListeners.getInstance().getChannleEventListener(mission_name)) == null) continue;
            lst.eventOnChannel(pointName, channelUid, isChannelImmediate);
        }
    }

    public Collection<String> getDependent() {
        return this.dependentMissions;
    }

    public String getMissionStartPlaceName() {
        return this.missionStartPlaceName;
    }

    public void setMissionStartPlaceName(String missionStartPlaceName) {
        this.missionStartPlaceName = missionStartPlaceName;
    }

    static class CommonStarter
    implements IMissionStarter {
        CommonStarter() {
        }

        public String startMission(String mission_name, boolean isChannelImmeiate, String info_channel_point, String qi_point, String finish_point) {
            MissionEventsMaker.startMission(mission_name, isChannelImmeiate, info_channel_point, qi_point, finish_point);
            return null;
        }

        public void startMission(String mission_name, boolean isChannelImmeiate, String info_channel_point, String qi_point, String finish_point, String missionPlaceStartName) {
        	
            MissionEventsMaker.startMission(mission_name, isChannelImmeiate, info_channel_point, qi_point, finish_point);
        }
    }
}
