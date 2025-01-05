/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.w3c.dom.Node;
import rnrcore.CoreTime;
import rnrcore.XmlSerializable;
import rnrscenario.ScenarioFlagsManager;
import scenarioUtils.Pair;
import xmlserialization.CoreTimeSerialization;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlutils.NodeList;

public class ScenarioFlagsSerializator
implements XmlSerializable {
    private static ScenarioFlagsSerializator instance = null;

    private ScenarioFlagsSerializator() {
    }

    public static ScenarioFlagsSerializator getInstance() {
        if (instance == null) {
            instance = new ScenarioFlagsSerializator();
        }
        return instance;
    }

    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        ScenarioFlagsSerializator.serializeXML(ScenarioFlagsManager.getInstance(), stream);
    }

    public void loadFromNode(Node node) {
        ScenarioFlagsSerializator.deserializeXML(new xmlutils.Node(node));
    }

    public void yourNodeWasNotFound() {
    }

    public String getRootNodeName() {
        return ScenarioFlagsSerializator.getNodeName();
    }

    public static String getNodeName() {
        return "scenario_flags";
    }

    public static void serializeXML(ScenarioFlagsManager value, PrintStream stream) {
        HashMap<String, CoreTime> timedFlags;
        Helper.openNode(stream, ScenarioFlagsSerializator.getNodeName());
        HashMap<String, Boolean> staticFlags = value.getStaticFlags();
        if (staticFlags != null && !staticFlags.isEmpty()) {
            Set<Map.Entry<String, Boolean>> staticFlagsEntries = staticFlags.entrySet();
            for (Map.Entry<String, Boolean> staticFlagInfo : staticFlagsEntries) {
                List<Pair<String, String>> attributes = Helper.createSingleAttribute("name", staticFlagInfo.getKey());
                Helper.addAttribute("value", staticFlagInfo.getValue(), attributes);
                Helper.printClosedNodeWithAttributes(stream, "static_flag", attributes);
            }
        }
        if ((timedFlags = value.getTimedFlags()) != null && !timedFlags.isEmpty()) {
            Set<Map.Entry<String, CoreTime>> timedFlagsEntries = timedFlags.entrySet();
            for (Map.Entry<String, CoreTime> timedFlagInfo : timedFlagsEntries) {
                Helper.printOpenNodeWithAttributes(stream, "timed_flag", Helper.createSingleAttribute("name", timedFlagInfo.getKey()));
                CoreTimeSerialization.serializeXML(timedFlagInfo.getValue(), stream);
                Helper.closeNode(stream, "timed_flag");
            }
        }
        Helper.closeNode(stream, ScenarioFlagsSerializator.getNodeName());
    }

    public static void deserializeXML(xmlutils.Node node) {
        NodeList listTimedFlags;
        String errorMessage = "ScenarioFlagsSerializator on deserializeXML ";
        NodeList listStaticFlags = node.getNamedChildren("static_flag");
        if (listStaticFlags != null && !listStaticFlags.isEmpty()) {
            for (xmlutils.Node staticNode : listStaticFlags) {
                String nameAttribute = staticNode.getAttribute("name");
                String valueAttributeString = staticNode.getAttribute("value");
                boolean value = Helper.ConvertToBooleanAndWarn(valueAttributeString, "value", errorMessage);
                if (nameAttribute == null) {
                    Log.error(errorMessage + " node " + "static_flag" + " has no attribute " + "name");
                    continue;
                }
                ScenarioFlagsManager.getInstance().setFlagValue(nameAttribute, value);
            }
        }
        if ((listTimedFlags = node.getNamedChildren("timed_flag")) != null && !listTimedFlags.isEmpty()) {
            for (xmlutils.Node timedNode : listTimedFlags) {
                String nameAttribute = timedNode.getAttribute("name");
                if (nameAttribute == null) {
                    Log.error(errorMessage + " node " + "timed_flag" + " has no attribute " + "name");
                    continue;
                }
                NodeList coreTimeNodeList = timedNode.getNamedChildren(CoreTimeSerialization.getNodeName());
                if (coreTimeNodeList == null || coreTimeNodeList.isEmpty()) {
                    Log.error(errorMessage + " node " + "timed_flag" + " has no child nodes " + CoreTimeSerialization.getNodeName());
                    continue;
                }
                xmlutils.Node coreTimeNode = (xmlutils.Node)coreTimeNodeList.get(0);
                CoreTime finishTime = CoreTimeSerialization.deserializeXML(coreTimeNode);
                if (finishTime == null) {
                    Log.error(errorMessage + " node " + "timed_flag" + " has bad child node " + CoreTimeSerialization.getNodeName());
                    continue;
                }
                ScenarioFlagsManager.getInstance().setFlagValueTimed(nameAttribute, finishTime);
            }
        }
    }
}

