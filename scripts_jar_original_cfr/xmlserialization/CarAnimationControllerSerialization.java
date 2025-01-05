/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import players.CarAnimationController;
import players.DriversModelsPool;
import players.actorveh;
import xmlserialization.ActorVehSerializator;
import xmlserialization.Helper;
import xmlserialization.ListElementSerializator;
import xmlserialization.ModelPoolSerializator;
import xmlserialization.SimpleTypeSerializator;
import xmlutils.Node;
import xmlutils.NodeList;

public class CarAnimationControllerSerialization {
    public static String getNodeName() {
        return "caranimationscontroller";
    }

    public static void serializeXML(CarAnimationController value, PrintStream stream) {
        Helper.openNode(stream, CarAnimationControllerSerialization.getNodeName());
        DriversModelsPool pool = value.getPool();
        ModelPoolSerializator.serializeXML(pool, stream);
        HashMap<actorveh, String> assignedAnimations = value.getAssignedAnimations();
        Set<Map.Entry<actorveh, String>> set = assignedAnimations.entrySet();
        Helper.openNode(stream, "assignedanimations");
        for (Map.Entry<actorveh, String> entry : set) {
            ListElementSerializator.serializeXMLListelementOpen(stream);
            ActorVehSerializator.serializeXML(entry.getKey(), stream);
            SimpleTypeSerializator.serializeXMLString(entry.getValue(), stream);
            ListElementSerializator.serializeXMLListelementClose(stream);
        }
        Helper.closeNode(stream, "assignedanimations");
        ArrayList<CarAnimationController.TypeCarPair> loadedCars = value.getLoadedCars();
        Helper.openNode(stream, "loadedcars");
        for (CarAnimationController.TypeCarPair typeCarPair : loadedCars) {
            ListElementSerializator.serializeXMLListelementOpen(stream);
            ActorVehSerializator.serializeXML(typeCarPair.getCar(), stream);
            SimpleTypeSerializator.serializeXMLInteger(typeCarPair.getTypePlayer(), stream);
            ListElementSerializator.serializeXMLListelementClose(stream);
        }
        Helper.closeNode(stream, "loadedcars");
        ArrayList<CarAnimationController.TypeCarPair> nonloadedCars = value.getNonloadedCars();
        Helper.openNode(stream, "nonloadedcars");
        for (CarAnimationController.TypeCarPair entry : nonloadedCars) {
            ListElementSerializator.serializeXMLListelementOpen(stream);
            ActorVehSerializator.serializeXML(entry.getCar(), stream);
            SimpleTypeSerializator.serializeXMLInteger(entry.getTypePlayer(), stream);
            ListElementSerializator.serializeXMLListelementClose(stream);
        }
        Helper.closeNode(stream, "nonloadedcars");
        Helper.closeNode(stream, CarAnimationControllerSerialization.getNodeName());
    }

    public static CarAnimationController deserializeXML(Node node) {
        NodeList nonLoadedCars;
        Node loadedCars;
        Node assignedAnimations;
        CarAnimationController result = new CarAnimationController();
        Node poolNode = node.getNamedChild(ModelPoolSerializator.getNodeName());
        if (null != poolNode) {
            DriversModelsPool pool = ModelPoolSerializator.deserializeXML(poolNode);
            result.setPool(pool);
        }
        if (null != (assignedAnimations = node.getNamedChild("assignedanimations"))) {
            HashMap<actorveh, String> assignedAnimationsResult = new HashMap<actorveh, String>();
            NodeList list = assignedAnimations.getNamedChildren(ListElementSerializator.getNodeName());
            for (Node nodeList : list) {
                NodeList stringNode;
                actorveh car = null;
                String string = null;
                NodeList actorvehNode = nodeList.getNamedChildren(ActorVehSerializator.getNodeName());
                if (!actorvehNode.isEmpty()) {
                    car = ActorVehSerializator.deserializeXML((Node)actorvehNode.get(0));
                }
                if (!(stringNode = nodeList.getNamedChildren(SimpleTypeSerializator.getNodeNameString())).isEmpty()) {
                    string = SimpleTypeSerializator.deserializeXMLString((Node)stringNode.get(0));
                }
                assignedAnimationsResult.put(car, string);
            }
            result.setAssignedAnimations(assignedAnimationsResult);
        }
        if (null != (loadedCars = node.getNamedChild("loadedcars"))) {
            ArrayList<CarAnimationController.TypeCarPair> loadedCarsResult = new ArrayList<CarAnimationController.TypeCarPair>();
            NodeList list = loadedCars.getNamedChildren(ListElementSerializator.getNodeName());
            for (Node nodeList : list) {
                Node intNode;
                actorveh car = null;
                int value = 0;
                Node actorvehNode = nodeList.getNamedChild(ActorVehSerializator.getNodeName());
                if (null != actorvehNode) {
                    car = ActorVehSerializator.deserializeXML(actorvehNode);
                }
                if (null != (intNode = nodeList.getNamedChild(SimpleTypeSerializator.getNodeNameInteger()))) {
                    value = SimpleTypeSerializator.deserializeXMLInteger(intNode);
                }
                loadedCarsResult.add(new CarAnimationController.TypeCarPair(value, car));
            }
            result.setLoadedCars(loadedCarsResult);
        }
        if (!(nonLoadedCars = node.getNamedChildren("nonloadedcars")).isEmpty()) {
            ArrayList<CarAnimationController.TypeCarPair> nonLoadedCarsResult = new ArrayList<CarAnimationController.TypeCarPair>();
            Node nodenonLoadedCars = (Node)nonLoadedCars.get(0);
            NodeList list = nodenonLoadedCars.getNamedChildren(ListElementSerializator.getNodeName());
            for (Node nodeList : list) {
                NodeList intNode;
                actorveh car = null;
                int value = 0;
                NodeList actorvehNode = nodeList.getNamedChildren(ActorVehSerializator.getNodeName());
                if (!actorvehNode.isEmpty()) {
                    car = ActorVehSerializator.deserializeXML((Node)actorvehNode.get(0));
                }
                if (!(intNode = nodeList.getNamedChildren(SimpleTypeSerializator.getNodeNameInteger())).isEmpty()) {
                    value = SimpleTypeSerializator.deserializeXMLInteger((Node)intNode.get(0));
                }
                nonLoadedCarsResult.add(new CarAnimationController.TypeCarPair(value, car));
            }
            result.setNonloadedCars(nonLoadedCarsResult);
        }
        return result;
    }
}

