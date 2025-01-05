/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Node;
import rnrcore.XmlSerializable;
import rnrscenario.missions.MissionList;
import rnrscenario.missions.MissionSystemInitializer;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlserialization.SimpleTypeSerializator;
import xmlutils.NodeList;

public class MissionsInitiatedSerialization
implements XmlSerializable {
    private static MissionsInitiatedSerialization instance = new MissionsInitiatedSerialization();

    public static MissionsInitiatedSerialization getInstance() {
        return instance;
    }

    public String getRootNodeName() {
        return MissionsInitiatedSerialization.getNodeName();
    }

    public void loadFromNode(Node node) {
        MissionsInitiatedSerialization.deserializeXML(new xmlutils.Node(node));
    }

    public void yourNodeWasNotFound() {
    }

    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        MissionsInitiatedSerialization.serializeXML(MissionSystemInitializer.getActivatedMissions(), stream);
    }

    public static String getNodeName() {
        return "missionsactivated";
    }

    public static void serializeXML(MissionList value, PrintStream stream) {
        if (value == null) {
            Log.error("MissionsInitiatedSerialization.serializeXML value=null");
            return;
        }
        Helper.openNode(stream, MissionsInitiatedSerialization.getNodeName());
        List<String> missionsNames = value.getStartedMissions();
        if (null != missionsNames && !missionsNames.isEmpty()) {
            for (String missionName : missionsNames) {
                SimpleTypeSerializator.serializeXMLString(missionName, stream);
            }
        }
        Helper.closeNode(stream, MissionsInitiatedSerialization.getNodeName());
    }

    public static void deserializeXML(xmlutils.Node node) {
        NodeList listMissionsNames = node.getNamedChildren(SimpleTypeSerializator.getNodeNameString());
        ArrayList<String> missionsNames = new ArrayList<String>();
        if (null != listMissionsNames && !listMissionsNames.isEmpty()) {
            for (xmlutils.Node nameNode : listMissionsNames) {
                String missionName = SimpleTypeSerializator.deserializeXMLString(nameNode);
                missionsNames.add(missionName);
            }
        }
        MissionSystemInitializer.getActivatedMissions().restoreStartedMissions(missionsNames);
    }
}

