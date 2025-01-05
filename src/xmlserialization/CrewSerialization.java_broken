/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import players.Crew;
import players.ICarCreationController;
import players.LiveCarCreationController;
import players.MappingCars;
import players.ScenarioCarCreationController;
import xmlserialization.CarAnimationControllerSerialization;
import xmlserialization.Helper;
import xmlserialization.LiveCarCreationControllerSerialization;
import xmlserialization.MappedCarsSerialization;
import xmlserialization.ScenarioCarCreationControllerSerialization;
import xmlutils.Node;

public class CrewSerialization {
    public static String getNodeName() {
        return "crew";
    }

    public static void serializeXML(Crew value, PrintStream stream) {
        Helper.openNode(stream, CrewSerialization.getNodeName());
        ICarCreationController controller = value.getCarCreationController();
        CarAnimationControllerSerialization.serializeXML(controller, stream);
        MappingCars mappedCars = value.getMappingCars();
        MappedCarsSerialization.serializeXML(mappedCars, stream);
        controller = value.getScenarioCarCreationController();
        ScenarioCarCreationControllerSerialization.serializeXML((ScenarioCarCreationController)controller, stream);
        controller = value.getLiveCarCreationController();
        LiveCarCreationControllerSerialization.serializeXML((LiveCarCreationController)controller, stream);
        Helper.closeNode(stream, CrewSerialization.getNodeName());
    }

    public static Crew deserializeXML(Node node) {
        Node mappedCars;
        Node liveCarCreationController;
        Node scenarioCarCreationController;
        Crew result = new Crew();
        Node carAnimationController = node.getNamedChild(CarAnimationControllerSerialization.getNodeName());
        if (null != carAnimationController) {
            result.setCarCreationController(CarAnimationControllerSerialization.deserializeXML(carAnimationController));
        }
        if (null != (scenarioCarCreationController = node.getNamedChild(ScenarioCarCreationControllerSerialization.getNodeName()))) {
            result.setScenarioCarCreationController(ScenarioCarCreationControllerSerialization.deserializeXML(scenarioCarCreationController));
        }
        if (null != (liveCarCreationController = node.getNamedChild(LiveCarCreationControllerSerialization.getNodeName()))) {
            result.setLiveCarCreationController(LiveCarCreationControllerSerialization.deserializeXML(liveCarCreationController));
        }
        if (null != (mappedCars = node.getNamedChild(MappedCarsSerialization.getNodeName()))) {
            result.setMappingCars(MappedCarsSerialization.deserializeXML(mappedCars));
        }
        return result;
    }
}

