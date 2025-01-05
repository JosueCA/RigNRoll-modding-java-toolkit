/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.List;
import players.RaceTrajectory;
import rnrcore.IXMLSerializable;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.PiterPandoomedrace;
import rnrscenario.controllers.ScenarioHost;
import scenarioUtils.Pair;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlserialization.RaceSerializator;
import xmlutils.Node;

@ScenarioClass(scenarioStage=5)
public class PiterPanDoomedRaceSerializator
implements IXMLSerializable {
    private PiterPandoomedrace serializationTarget = null;
    private ScenarioHost host;
    private static final RaceSerializator customDataSaver = new RaceSerializator(PiterPanDoomedRaceSerializator.class.getAnnotation(ScenarioClass.class).scenarioStage());

    public PiterPanDoomedRaceSerializator(PiterPandoomedrace value, ScenarioHost scenarioHost) {
        assert (null != scenarioHost) : "'scenarioHost' must be non-null reference";
        this.serializationTarget = value;
        this.host = scenarioHost;
    }

    public PiterPanDoomedRaceSerializator(ScenarioHost scenarioHost) {
        assert (null != scenarioHost) : "'scenarioHost' must be non-null reference";
        this.serializationTarget = null;
        this.host = scenarioHost;
    }

    public static String getNodeName() {
        return "piterpandommedrace";
    }

    public static void serializeXML(PiterPandoomedrace value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("finished", value.isRaceFinished());
        Helper.addAttribute("racename", value.getRaceName(), attributes);
        Helper.printOpenNodeWithAttributes(stream, PiterPanDoomedRaceSerializator.getNodeName(), attributes);
        customDataSaver.serializeXML(value, stream);
        Helper.closeNode(stream, PiterPanDoomedRaceSerializator.getNodeName());
    }

    public static void deserializeXML(Node node, ScenarioHost host) {
        String finishedString = node.getAttribute("finished");
        String raceName = node.getAttribute("racename");
        if (null == raceName || 0 == "null".compareTo(raceName)) {
            Log.error("ERRORR. PiterPanDoomedRaceSerializator on deserializeXML has no attribute with name racename");
            raceName = "pp_race";
        }
        boolean finished = Helper.ConvertToBooleanAndWarn(finishedString, "finished", "PiterPanDoomedRaceSerializator on deserializeXML");
        try {
            RaceTrajectory.createTrajectoryForRaceWithPP();
            PiterPandoomedrace result = new PiterPandoomedrace(raceName, finished, host);
            Node raceNode = node.getNamedChild(customDataSaver.getNodeName());
            if (null == raceNode) {
                Log.error("ERRORR. PiterPanDoomedRaceSerializator on deserializeXML has no child node with name  " + customDataSaver.getNodeName());
            }
            customDataSaver.deserializeXML(result, raceNode);
        }
        catch (IllegalStateException e) {
            Log.error("ERRORR. Bad save detected: trying to recover game state. Error message: " + e.getMessage());
        }
    }

    public void deSerialize(Node node) {
        PiterPanDoomedRaceSerializator.deserializeXML(node, this.host);
    }

    public void serialize(PrintStream stream) {
        PiterPanDoomedRaceSerializator.serializeXML(this.serializationTarget, stream);
    }
}

