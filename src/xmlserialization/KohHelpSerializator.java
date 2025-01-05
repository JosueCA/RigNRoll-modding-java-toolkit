/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.List;
import rnrcore.CoreTime;
import rnrcore.IXMLSerializable;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.KohHelpManage;
import rnrscenario.controllers.ScenarioHost;
import scenarioUtils.Pair;
import xmlserialization.CoreTimeSerialization;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlutils.Node;

@ScenarioClass(scenarioStage=0)
public class KohHelpSerializator
implements IXMLSerializable {
    private KohHelpManage slave = null;
    private final ScenarioHost scenarioHost;

    public KohHelpSerializator(KohHelpManage value, ScenarioHost host) {
        assert (null != host) : "'host' must be non-null reference";
        this.scenarioHost = host;
        this.slave = value;
    }

    public KohHelpSerializator(ScenarioHost host) {
        assert (null != host) : "'host' must be non-null reference";
        this.slave = null;
        this.scenarioHost = host;
    }

    public static String getNodeName() {
        return "kohhelp";
    }

    public static void serializeXML(KohHelpManage value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("current_scene", value.getCurrent_scene());
        Helper.addAttribute("f_makeKoh", value.isF_makeKoh(), attributes);
        Helper.addAttribute("f_wasMadeKoh", value.isF_wasMadeKoh(), attributes);
        Helper.printOpenNodeWithAttributes(stream, KohHelpSerializator.getNodeName(), attributes);
        CoreTime time = value.getTimeKohOrdered();
        if (null != time) {
            Helper.openNode(stream, "timeKohOrdered");
            CoreTimeSerialization.serializeXML(time, stream);
            Helper.closeNode(stream, "timeKohOrdered");
        }
        Helper.closeNode(stream, KohHelpSerializator.getNodeName());
    }

    public static void deserializeXML(Node node, ScenarioHost host) {
        String errorMessage = "KohHelpSerializator in deserializeXML ";
        String currentSceneString = node.getAttribute("current_scene");
        String toMakeKohString = node.getAttribute("f_makeKoh");
        String wasMakeKohString = node.getAttribute("f_wasMadeKoh");
        int currentSceneValue = Helper.ConvertToIntegerAndWarn(currentSceneString, "current_scene", errorMessage);
        boolean toMakeKohValue = Helper.ConvertToBooleanAndWarn(toMakeKohString, "f_makeKoh", errorMessage);
        boolean wasMakeKohValue = Helper.ConvertToBooleanAndWarn(wasMakeKohString, "f_wasMadeKoh", errorMessage);
        CoreTime timeKohOrdered = null;
        Node timeNode = node.getNamedChild("timeKohOrdered");
        if (null != timeNode) {
            Node itemNode = timeNode.getNamedChild(CoreTimeSerialization.getNodeName());
            if (null != itemNode) {
                timeKohOrdered = CoreTimeSerialization.deserializeXML(itemNode);
            } else {
                Log.error("ERRORR. KohHelpSerializator in deserializeXML has no node " + CoreTimeSerialization.getNodeName() + " in node " + "timeKohOrdered");
            }
        }
        if (null == KohHelpManage.getInstance()) {
            KohHelpManage.constructSingleton(host);
        }
        KohHelpManage value = KohHelpManage.getInstance();
        value.setCurrent_scene(currentSceneValue);
        value.setF_makeKoh(toMakeKohValue);
        value.setF_wasMadeKoh(wasMakeKohValue);
        value.setTimeKohOrdered(timeKohOrdered);
        value.prepareKohDeserealize();
        value.start();
    }

    public void deSerialize(Node node) {
        KohHelpSerializator.deserializeXML(node, this.scenarioHost);
    }

    public void serialize(PrintStream stream) {
        KohHelpSerializator.serializeXML(this.slave, stream);
    }
}

