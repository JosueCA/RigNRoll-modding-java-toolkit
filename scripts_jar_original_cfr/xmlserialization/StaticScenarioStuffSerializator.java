/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.List;
import org.w3c.dom.Node;
import rnrcore.XmlSerializable;
import rnrscenario.StaticScenarioStuff;
import scenarioUtils.Pair;
import xmlserialization.Helper;

public class StaticScenarioStuffSerializator
implements XmlSerializable {
    private static StaticScenarioStuffSerializator instance = new StaticScenarioStuffSerializator();

    public static StaticScenarioStuffSerializator getInstance() {
        return instance;
    }

    public String getRootNodeName() {
        return StaticScenarioStuffSerializator.getNodeName();
    }

    public void loadFromNode(Node node) {
        StaticScenarioStuffSerializator.deserializeXML(new xmlutils.Node(node));
    }

    public void yourNodeWasNotFound() {
    }

    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        StaticScenarioStuffSerializator.serializeXML(stream);
    }

    public static String getNodeName() {
        return "staticscenariovariables";
    }

    public static void serializeXML(PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("readycursedhiway", StaticScenarioStuff.isReadyCursedHiWay());
        Helper.addAttribute("readypreparesc00060", StaticScenarioStuff.isReadyPreparesc00060(), attributes);
        Helper.printClosedNodeWithAttributes(stream, StaticScenarioStuffSerializator.getNodeName(), attributes);
    }

    public static void deserializeXML(xmlutils.Node node) {
        String errorMessage = "StaticScenarioStuffSerializator in deserializeXML";
        String readyCursedHiWayString = node.getAttribute("readycursedhiway");
        String readyPreparesc00060String = node.getAttribute("readypreparesc00060");
        boolean readyCursedHiWayValue = Helper.ConvertToBooleanAndWarn(readyCursedHiWayString, "readycursedhiway", errorMessage);
        boolean readyPreparesc00060Value = Helper.ConvertToBooleanAndWarn(readyPreparesc00060String, "readypreparesc00060", errorMessage);
        StaticScenarioStuff.makeReadyCursedHiWay(readyCursedHiWayValue);
        StaticScenarioStuff.makeReadyPreparesc00060(readyPreparesc00060Value);
    }
}

