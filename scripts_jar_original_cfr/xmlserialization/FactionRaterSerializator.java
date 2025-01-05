/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Node;
import rnrcore.XmlSerializable;
import rnrrating.FactionRater;
import scenarioUtils.Pair;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlutils.NodeList;

public class FactionRaterSerializator
implements XmlSerializable {
    private static FactionRaterSerializator instance = null;

    private FactionRaterSerializator() {
    }

    public static FactionRaterSerializator getInstance() {
        if (instance == null) {
            instance = new FactionRaterSerializator();
        }
        return instance;
    }

    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        FactionRaterSerializator.serializeXML(stream);
    }

    public void loadFromNode(Node node) {
        FactionRaterSerializator.deserialize(new xmlutils.Node(node));
    }

    public void yourNodeWasNotFound() {
    }

    public String getRootNodeName() {
        return FactionRaterSerializator.getNodeName();
    }

    public static String getNodeName() {
        return "faction_rater";
    }

    public static void serializeXML(PrintStream stream) {
        Helper.openNode(stream, FactionRaterSerializator.getNodeName());
        List<Pair<String, String>> factionsData = FactionRater.getSerializationData();
        if (factionsData != null && !factionsData.isEmpty()) {
            for (Pair<String, String> factionSingleData : factionsData) {
                List<Pair<String, String>> factionAttributes = Helper.createSingleAttribute("name", factionSingleData.getFirst());
                Helper.addAttribute("value", factionSingleData.getSecond(), factionAttributes);
                Helper.printClosedNodeWithAttributes(stream, "faction", factionAttributes);
            }
        }
        Helper.closeNode(stream, FactionRaterSerializator.getNodeName());
    }

    public static void deserialize(xmlutils.Node node) {
        NodeList factionDataNodes = node.getNamedChildren("faction");
        ArrayList<Pair<String, String>> factionsData = new ArrayList<Pair<String, String>>();
        if (factionDataNodes != null && !factionDataNodes.isEmpty()) {
            for (xmlutils.Node singleFactionNode : factionDataNodes) {
                String valueAttributeString;
                String nameAttributeString = singleFactionNode.getAttribute("name");
                if (nameAttributeString == null) {
                    Log.error("FactionRaterSerializator deserialize. Node faction has no attribute name");
                }
                if ((valueAttributeString = singleFactionNode.getAttribute("value")) == null) {
                    Log.error("FactionRaterSerializator deserialize. Node faction has no attribute value");
                }
                factionsData.add(new Pair<String, String>(nameAttributeString, valueAttributeString));
            }
        }
        FactionRater.setSerializationData(factionsData);
    }
}

