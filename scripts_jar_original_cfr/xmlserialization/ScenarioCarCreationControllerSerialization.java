/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.ArrayList;
import players.ScenarioCarCreationController;
import players.actorveh;
import xmlserialization.ActorVehSerializator;
import xmlserialization.Helper;
import xmlserialization.ListElementSerializator;
import xmlutils.Node;
import xmlutils.NodeList;

public class ScenarioCarCreationControllerSerialization {
    public static String getNodeName() {
        return "scenariocarcreationcontroller";
    }

    public static void serializeXML(ScenarioCarCreationController value, PrintStream stream) {
        Helper.openNode(stream, "scenariocarcreationcontroller");
        Helper.openNode(stream, "loadedcars");
        ArrayList<actorveh> cars = value.getCars();
        for (actorveh item : cars) {
            ListElementSerializator.serializeXMLListelementOpen(stream);
            ActorVehSerializator.serializeXML(item, stream);
            ListElementSerializator.serializeXMLListelementClose(stream);
        }
        Helper.closeNode(stream, "loadedcars");
        Helper.closeNode(stream, "scenariocarcreationcontroller");
    }

    public static ScenarioCarCreationController deserializeXML(Node node) {
        ScenarioCarCreationController controller = new ScenarioCarCreationController();
        NodeList listCars = node.getNamedChildren("loadedcars");
        if (!listCars.isEmpty()) {
            ArrayList<actorveh> cars = new ArrayList<actorveh>();
            NodeList listNodes = ((Node)listCars.get(0)).getNamedChildren(ListElementSerializator.getNodeName());
            for (Node listItem : listNodes) {
                actorveh car = null;
                NodeList actorvehNode = listItem.getNamedChildren(ActorVehSerializator.getNodeName());
                if (!actorvehNode.isEmpty()) {
                    car = ActorVehSerializator.deserializeXML((Node)actorvehNode.get(0));
                }
                if (null == car) continue;
                cars.add(car);
            }
            controller.setCars(cars);
        }
        return controller;
    }
}

