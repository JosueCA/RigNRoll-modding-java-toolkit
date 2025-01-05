/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import org.w3c.dom.Node;
import rnrloggers.MissionsLogger;
import rnrscenario.missions.InfoChannelsXmlExtractor;
import rnrscenario.missions.MissionCreationContext;
import rnrscenario.missions.MissionEndUIController;
import rnrscenario.missions.NullMissionEndUIController;
import rnrscenario.missions.PostChannelsAfterMenu;
import rnrscenario.missions.infochannels.InformationChannelData;
import rnrscenario.scenarioscript;
import scenarioMachine.FiniteStateMachine;
import scenarioXml.FiniteStatesSetBuilder;
import scenarioXml.IsoCondition;
import scenarioXml.ObjectProperties;
import scenarioXml.XmlFilter;
import scriptActions.ScriptAction;
import scriptEvents.ComplexEventReaction;
import scriptEvents.EventChecker;
import scriptEvents.EventCheckersBuilders;
import scriptEvents.SpecialObjectEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class MissionPhase {
    private EventChecker condition = null;
    private List<ScriptAction> actions = new LinkedList<ScriptAction>();
    private ComplexEventReaction reaction = null;
    private MissionEndUIController uiController = null;
    private List<InformationChannelData> infoChannels = new LinkedList<InformationChannelData>();

    private void loadActions(Node source, String defaultArgument) {
        assert (null != source) : "source must be valid non-null reference";
        FiniteStateMachine scenarioMachine = scenarioscript.script.getScenarioMachine();
        XmlFilter actionFilter = new XmlFilter(source.getChildNodes());
        ArrayList<ObjectProperties> actionsOnStartRawInfo = ObjectProperties.extractListEx(actionFilter);
        this.actions = FiniteStatesSetBuilder.actionsFromActionList(actionsOnStartRawInfo, defaultArgument, scenarioMachine);
    }

    private void loadInformationChannels(Node source, MissionCreationContext context) {
        if (null != source) {
            XmlFilter childVisitor = new XmlFilter(source.getChildNodes());
            childVisitor.visitAllNodes("infochannel", new InfoChannelsXmlExtractor(this.infoChannels, context), null);
            int mainChannelsCount = 0;
            for (InformationChannelData loaded : this.infoChannels) {
                if (!loaded.isMarkedAsMain()) continue;
                ++mainChannelsCount;
            }
            if (1 < mainChannelsCount) {
                MissionsLogger.getInstance().doLog("found multiple main channel", Level.SEVERE);
            }
        }
    }

    private void loadCondition(Node source) {
        if (null == source) {
            return;
        }
        IsoCondition endConditionRawData = new IsoCondition();
        endConditionRawData.extractFromNode(source);
        this.condition = FiniteStatesSetBuilder.buildEventChecker(endConditionRawData, SpecialObjectEvent.EventType.f2);
    }

    MissionPhase(List<InformationChannelData> channels, List<ScriptAction> load_actions, EventChecker conditions) {
        this.actions.addAll(load_actions);
        this.infoChannels.addAll(channels);
        if (null != conditions) {
            this.reaction = new ComplexEventReaction(conditions, 0);
            this.reaction.addAction(this.actions);
        }
        EventCheckersBuilders.do_construct();
        if (null == this.uiController) {
            this.uiController = new NullMissionEndUIController();
        }
    }

    MissionPhase(Node dataSource, String defaultArgument, MissionCreationContext context) {
        assert (null != dataSource) : "dataSource must be non-null reference";
        this.loadActions(dataSource, defaultArgument);
        XmlFilter dataMiner = new XmlFilter(dataSource.getChildNodes());
        this.loadInformationChannels(dataMiner.nodeNameNext("channels"), context);
        dataMiner.goOnStart();
        this.loadCondition(dataMiner.nodeNameNext("cond"));
        if (null != this.condition) {
            this.reaction = new ComplexEventReaction(this.condition, 0);
            this.reaction.addAction(this.actions);
        }
        dataMiner.goOnStart();
        Node menuNode = dataMiner.nodeNameNext("menu");
        if (null != menuNode) {
            Node textNode = menuNode.getAttributes().getNamedItem("text");
            Node materialNode = menuNode.getAttributes().getNamedItem("material");
            if (XmlFilter.textContentExists(textNode) && XmlFilter.textContentExists(materialNode)) {
                this.uiController = new PostChannelsAfterMenu(textNode.getTextContent(), materialNode.getTextContent());
            }
        }
        if (null == this.uiController) {
            this.uiController = new NullMissionEndUIController();
        }
        EventCheckersBuilders.do_construct();
    }

    ComplexEventReaction getPhaseReaction() {
        return this.reaction;
    }

    public List<InformationChannelData> getInfoChannels() {
        return Collections.unmodifiableList(this.infoChannels);
    }

    void addOnAcceptAction(ScriptAction action) {
        if (null != action) {
            for (InformationChannelData infoChannelData : this.infoChannels) {
                infoChannelData.addOnAcceptAction(action);
            }
        }
    }

    void execute() {
        for (ScriptAction action : this.actions) {
            action.act();
        }
    }

    public MissionEndUIController getUIController() {
        return this.uiController;
    }
}

