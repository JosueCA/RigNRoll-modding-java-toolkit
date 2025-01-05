/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.List;
import players.RaceTrajectory;
import rnrcore.IXMLSerializable;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.PiterPanFinalrace;
import rnrscenario.controllers.ScenarioHost;
import scenarioUtils.Pair;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlserialization.RaceSerializator;
import xmlutils.Node;

@ScenarioClass(scenarioStage=7)
public class PiterPanFinalRaceSerializator
implements IXMLSerializable {
    private PiterPanFinalrace serializationTarget = null;
    private final ScenarioHost host;
    private static final RaceSerializator customDataSaver = new RaceSerializator(PiterPanFinalRaceSerializator.class.getAnnotation(ScenarioClass.class).scenarioStage());

    public PiterPanFinalRaceSerializator(PiterPanFinalrace value, ScenarioHost scenarioHost) {
        assert (null != scenarioHost) : "'scenarioHost' must be non-null reference";
        this.host = scenarioHost;
        this.serializationTarget = value;
    }

    public PiterPanFinalRaceSerializator(ScenarioHost scenarioHost) {
        assert (null != scenarioHost) : "'scenarioHost' must be non-null reference";
        this.host = scenarioHost;
        this.serializationTarget = null;
    }

    public static String getNodeName() {
        return "piterpanfinalrace";
    }

    public static void serializeXML(PiterPanFinalrace value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("finished", value.isRaceFinished());
        Helper.addAttribute("racename", value.getRaceName(), attributes);
        Helper.printOpenNodeWithAttributes(stream, PiterPanFinalRaceSerializator.getNodeName(), attributes);
        customDataSaver.serializeXML(value, stream);
        Helper.closeNode(stream, PiterPanFinalRaceSerializator.getNodeName());
    }

    public static void deserializeXML(Node node, ScenarioHost host) {
        boolean finished;
        String finishedString = node.getAttribute("finished");
        String raceName = node.getAttribute("racename");
        if (null == raceName || 0 == "null".compareTo(raceName)) {
            Log.error("ERRORR. PiterPanFinalRaceSerializator on deserializeXML has no attribute with name racename");
            raceName = "pp_race";
        }
        if (!(finished = Helper.ConvertToBooleanAndWarn(finishedString, "finished", "PiterPanFinalRaceSerializator on deserializeXML"))) {
            RaceTrajectory.createTrajectoryForRaceWithPP();
            PiterPanFinalrace result = new PiterPanFinalrace(raceName, finished, host);
            Node raceNode = node.getNamedChild(customDataSaver.getNodeName());
            if (null == raceNode) {
                Log.error("ERRORR. PiterPanFinalRaceSerializator on deserializeXML has no child node with name  " + customDataSaver.getNodeName());
            }
            customDataSaver.deserializeXML(result, raceNode);
        } else {
            Log.error("ERRORR. PiterPanFinalRaceSerializator on deserializeXML has no child node with name  " + customDataSaver.getNodeName());
        }
    }

    public void deSerialize(Node node) {
        PiterPanFinalRaceSerializator.deserializeXML(node, this.host);
    }

    public void serialize(PrintStream stream) {
        PiterPanFinalRaceSerializator.serializeXML(this.serializationTarget, stream);
    }
}

