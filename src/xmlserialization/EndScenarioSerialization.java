/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import rnrcore.XmlSerializable;
import rnrscenario.EndScenario;
import xmlserialization.ActionTaskDaysSerialization;
import xmlserialization.Helper;
import xmlutils.Node;
import xmlutils.NodeList;

public class EndScenarioSerialization
implements XmlSerializable {
    private static EndScenarioSerialization instance = new EndScenarioSerialization();

    public static EndScenarioSerialization getInstance() {
        return instance;
    }

    public static String getNodeName() {
        return "endscenario";
    }

    public static void serializeXML(EndScenario value, PrintStream stream) {
        Helper.openNode(stream, EndScenarioSerialization.getNodeName());
        List<EndScenario.DelayedAction> list = value.getDelayedActions();
        if (null != list) {
            Helper.openNode(stream, "endscenariotimeactions");
            for (EndScenario.DelayedAction item : list) {
                ActionTaskDaysSerialization.serializeXML(item, stream);
            }
            Helper.closeNode(stream, "endscenariotimeactions");
        }
        Helper.closeNode(stream, EndScenarioSerialization.getNodeName());
    }

    public static void deserializeXML(Node node) {
        EndScenario value = EndScenario.getInstance();
        Node timeActionsNode = node.getNamedChild("endscenariotimeactions");
        if (null != timeActionsNode) {
            ArrayList<EndScenario.DelayedAction> timeActionsList = new ArrayList<EndScenario.DelayedAction>();
            NodeList list = timeActionsNode.getNamedChildren(ActionTaskDaysSerialization.getNodeName());
            for (Node itemNode : list) {
                EndScenario.DelayedAction timeAction = ActionTaskDaysSerialization.deserializeXML(itemNode);
                timeActionsList.add(timeAction);
            }
            value.setDelayedActions(timeActionsList);
        }
    }

    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        EndScenarioSerialization.serializeXML(EndScenario.getInstance(), stream);
    }

    public void loadFromNode(org.w3c.dom.Node node) {
        EndScenarioSerialization.deserializeXML(new Node(node));
    }

    public void yourNodeWasNotFound() {
    }

    public String getRootNodeName() {
        return EndScenarioSerialization.getNodeName();
    }
}

