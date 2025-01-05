/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.w3c.dom.Node;
import rnrcore.XmlSerializable;
import rnrscenario.missions.requirements.MissionsLog;
import xmlserialization.Helper;
import xmlserialization.ListElementSerializator;
import xmlserialization.Log;
import xmlserialization.SimpleTypeSerializator;
import xmlutils.NodeList;

public class MissionsLogSerialization
implements XmlSerializable {
    private static MissionsLogSerialization instance = new MissionsLogSerialization();

    public static MissionsLogSerialization getInstance() {
        return instance;
    }

    public String getRootNodeName() {
        return MissionsLogSerialization.getNodeName();
    }

    public void loadFromNode(Node node) {
        MissionsLogSerialization.deserializeXML(new xmlutils.Node(node));
    }

    public void yourNodeWasNotFound() {
    }

    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        MissionsLogSerialization.serializeXML(MissionsLog.getInstance(), stream);
    }

    public static String getNodeName() {
        return "missionslog";
    }

    public static void serializeXML(MissionsLog value, PrintStream stream) {
        Helper.openNode(stream, MissionsLogSerialization.getNodeName());
        Map<String, MissionsLog.MissionState> states = value.getMissionsEvents();
        Set<Map.Entry<String, MissionsLog.MissionState>> set = states.entrySet();
        for (Map.Entry<String, MissionsLog.MissionState> entry : set) {
            ListElementSerializator.serializeXMLListelementOpen(stream);
            SimpleTypeSerializator.serializeXMLString(entry.getKey(), stream);
            Collection<MissionsLog.Event> events = entry.getValue().getOccuredEvents();
            ListElementSerializator.serializeXMLListelementOpen(stream);
            for (MissionsLog.Event event2 : events) {
                SimpleTypeSerializator.serializeXMLString(event2.toString(), stream);
            }
            ListElementSerializator.serializeXMLListelementClose(stream);
            ListElementSerializator.serializeXMLListelementClose(stream);
        }
        Helper.closeNode(stream, MissionsLogSerialization.getNodeName());
    }

    public static void deserializeXML(xmlutils.Node node) {
        MissionsLog manager = MissionsLog.getInstance();
        NodeList listStates = node.getNamedChildren(ListElementSerializator.getNodeName());
        HashMap<String, MissionsLog.MissionState> states = new HashMap<String, MissionsLog.MissionState>(listStates.size());
        for (xmlutils.Node stateNode : listStates) {
            xmlutils.Node missionNameNode = stateNode.getNamedChild(SimpleTypeSerializator.getNodeNameString());
            String missionName = null;
            if (null == missionNameNode) {
                Log.error("MissionsLogSerialization in deserializeXML has no named node " + SimpleTypeSerializator.getNodeNameString() + " in node named " + ListElementSerializator.getNodeName());
            } else {
                missionName = SimpleTypeSerializator.deserializeXMLString(missionNameNode);
            }
            MissionsLog.MissionState missionState = new MissionsLog.MissionState();
            xmlutils.Node eventNode = stateNode.getNamedChild(ListElementSerializator.getNodeName());
            if (null != eventNode) {
                NodeList listEvents = eventNode.getNamedChildren(SimpleTypeSerializator.getNodeNameString());
                for (xmlutils.Node event2 : listEvents) {
                    String eventString = SimpleTypeSerializator.deserializeXMLString(event2);
                    MissionsLog.Event eventValue = MissionsLog.Event.valueOf(eventString);
                    missionState.eventHappen(eventValue);
                }
            }
            states.put(missionName, missionState);
        }
        manager.setMissionsEvents(states);
    }
}

