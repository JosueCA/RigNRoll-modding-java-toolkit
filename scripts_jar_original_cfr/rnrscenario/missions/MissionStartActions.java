/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import rnrscenario.missions.CreateMissionEvent;
import rnrscenario.scenarioscript;
import scenarioMachine.FiniteStateMachine;
import scenarioXml.FiniteStatesSetBuilder;
import scenarioXml.ObjectProperties;
import scenarioXml.XmlFilter;
import scriptActions.ScriptAction;
import scriptEvents.EventListener;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class MissionStartActions
implements EventListener {
    private ArrayList<ScriptAction> actions = new ArrayList();
    private String mission_name = null;
    private String org_name = null;

    public MissionStartActions(String mission_name, String org_name) {
        this.mission_name = mission_name;
        this.org_name = org_name;
    }

    public MissionStartActions(String mission_name, String org_name, ArrayList<ScriptAction> load_actions) {
        this.mission_name = mission_name;
        this.org_name = org_name;
        this.actions.addAll(load_actions);
    }

    public void extractActions(Node source, String node_name) {
        NodeList list = source.getChildNodes();
        Node strta_node = null;
        for (int i = 0; i < list.getLength(); ++i) {
            Node node = list.item(i);
            if (node.getNodeName().compareToIgnoreCase(node_name) != 0) continue;
            strta_node = node;
            break;
        }
        if (null == strta_node) {
            return;
        }
        FiniteStateMachine scenarioMachine = scenarioscript.script.getScenarioMachine();
        XmlFilter actionFilter = new XmlFilter(strta_node.getChildNodes());
        ArrayList<ObjectProperties> actionsOnStartRawInfo = ObjectProperties.extractListEx(actionFilter);
        this.actions = FiniteStatesSetBuilder.actionsFromActionList(actionsOnStartRawInfo, this.org_name, scenarioMachine);
    }

    @Override
    public void eventHappened(List<ScriptEvent> eventTuple) {
        for (ScriptEvent event2 : eventTuple) {
            if (!(event2 instanceof CreateMissionEvent) || this.mission_name.compareToIgnoreCase(((CreateMissionEvent)event2).getMissionName()) != 0) continue;
            for (ScriptAction action : this.actions) {
                action.act();
            }
        }
    }
}

