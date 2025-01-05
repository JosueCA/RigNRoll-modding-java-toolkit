/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.List;
import rnrcore.IXMLSerializable;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.RedRockCanyon;
import rnrscenario.controllers.ScenarioHost;
import scenarioUtils.Pair;
import xmlserialization.Helper;
import xmlutils.Node;

@ScenarioClass(scenarioStage=13)
public class RedRockCanyonSerializator
implements IXMLSerializable {
    private RedRockCanyon m_object = null;
    private ScenarioHost host;
    private static final String COCH_CALLED_ATTRIBUTE = "cochCalled";
    private static final String MATHEW_CALLED_ATTRIBUTE = "mathewCalled";

    public RedRockCanyonSerializator(RedRockCanyon value) {
        this.m_object = value;
    }

    public RedRockCanyonSerializator(ScenarioHost scenarioHost) {
        this.m_object = null;
        this.host = scenarioHost;
    }

    public static String getNodeName() {
        return "redRockCanyon";
    }

    public static void serializeXML(RedRockCanyon value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute(COCH_CALLED_ATTRIBUTE, value.isCochCalled());
        Helper.addAttribute(MATHEW_CALLED_ATTRIBUTE, value.isMathewCalled(), attributes);
        Helper.printClosedNodeWithAttributes(stream, RedRockCanyonSerializator.getNodeName(), attributes);
    }

    public static void deserializeXML(Node node, ScenarioHost host) {
        String cochCalledString = node.getAttribute(COCH_CALLED_ATTRIBUTE);
        String mathewCalledString = node.getAttribute(MATHEW_CALLED_ATTRIBUTE);
        boolean cochCalled = Helper.ConvertToBooleanAndWarn(cochCalledString, COCH_CALLED_ATTRIBUTE, "RedRockCanyonSerializator in deserializeXML");
        boolean mathewCalled = Helper.ConvertToBooleanAndWarn(mathewCalledString, MATHEW_CALLED_ATTRIBUTE, "RedRockCanyonSerializator in deserializeXML");
        RedRockCanyon result = new RedRockCanyon(host, mathewCalled);
        result.setCochCalled(cochCalled);
    }

    public void deSerialize(Node node) {
        RedRockCanyonSerializator.deserializeXML(node, this.host);
    }

    public void serialize(PrintStream stream) {
        RedRockCanyonSerializator.serializeXML(this.m_object, stream);
    }
}

