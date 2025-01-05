/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.Map;
import java.util.Set;
import rnrscenario.missions.PriorityTable;
import xmlserialization.Helper;
import xmlserialization.ListElementSerializator;
import xmlserialization.Log;
import xmlserialization.SimpleTypeSerializator;
import xmlutils.Node;
import xmlutils.NodeList;

public class MissionsPrioritySerialization {
    public static String getNodeName() {
        return "missionspriorities";
    }

    public static void serializeXML(PriorityTable value, PrintStream stream) {
        if (null == value) {
            return;
        }
        Helper.openNode(stream, MissionsPrioritySerialization.getNodeName());
        Map<String, Integer> table = value.getPriorities();
        if (null != table && !table.isEmpty()) {
            Set<Map.Entry<String, Integer>> set = table.entrySet();
            for (Map.Entry<String, Integer> entry : set) {
                ListElementSerializator.serializeXMLListelementOpen(stream);
                SimpleTypeSerializator.serializeXMLString(entry.getKey(), stream);
                SimpleTypeSerializator.serializeXMLInteger(entry.getValue(), stream);
                ListElementSerializator.serializeXMLListelementClose(stream);
            }
        }
        Helper.closeNode(stream, MissionsPrioritySerialization.getNodeName());
    }

    public static void deserializeXML(PriorityTable table, Node node) {
        NodeList listElements = node.getNamedChildren(ListElementSerializator.getNodeName());
        if (null != listElements && !listElements.isEmpty()) {
            for (Node element : listElements) {
                Node missionNameNode = element.getNamedChild(SimpleTypeSerializator.getNodeNameString());
                Node priorityNode = element.getNamedChild(SimpleTypeSerializator.getNodeNameInteger());
                String missionName = null;
                int priority = 0;
                if (null == missionNameNode) {
                    Log.error("MissionsPrioritySerialization in deserializeXML has no node named " + SimpleTypeSerializator.getNodeNameString() + " in node named " + ListElementSerializator.getNodeName());
                } else {
                    missionName = SimpleTypeSerializator.deserializeXMLString(missionNameNode);
                }
                if (null == priorityNode) {
                    Log.error("MissionsPrioritySerialization in deserializeXML has no node named " + SimpleTypeSerializator.getNodeNameInteger() + " in node named " + ListElementSerializator.getNodeName());
                } else {
                    priority = SimpleTypeSerializator.deserializeXMLInteger(priorityNode);
                }
                table.registerMissionPriority(missionName, priority);
            }
        }
    }
}

