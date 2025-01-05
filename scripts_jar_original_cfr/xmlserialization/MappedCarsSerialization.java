/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import players.MappingCars;
import players.actorveh;
import xmlserialization.ActorVehSerializator;
import xmlserialization.Helper;
import xmlserialization.ListElementSerializator;
import xmlserialization.SimpleTypeSerializator;
import xmlutils.Node;
import xmlutils.NodeList;

public class MappedCarsSerialization {
    public static String getNodeName() {
        return "mappedcars";
    }

    public static void serializeXML(MappingCars value, PrintStream stream) {
        Helper.openNode(stream, MappedCarsSerialization.getNodeName());
        Helper.openNode(stream, "list");
        HashMap<String, actorveh> mappedCars = value.getMappedCars();
        Set<Map.Entry<String, actorveh>> set = mappedCars.entrySet();
        for (Map.Entry<String, actorveh> entry : set) {
            ListElementSerializator.serializeXMLListelementOpen(stream);
            SimpleTypeSerializator.serializeXMLString(entry.getKey(), stream);
            ActorVehSerializator.serializeXML(entry.getValue(), stream);
            ListElementSerializator.serializeXMLListelementClose(stream);
        }
        Helper.closeNode(stream, "list");
        Helper.closeNode(stream, MappedCarsSerialization.getNodeName());
    }

    public static MappingCars deserializeXML(Node node) {
        MappingCars result = new MappingCars();
        NodeList listNodes = node.getNamedChildren("list");
        if (!listNodes.isEmpty()) {
            HashMap<String, actorveh> mappedCars = new HashMap<String, actorveh>();
            NodeList elements = ((Node)listNodes.get(0)).getNamedChildren(ListElementSerializator.getNodeName());
            for (Node element : elements) {
                String value = null;
                actorveh car = null;
                NodeList carsList = element.getNamedChildren(ActorVehSerializator.getNodeName());
                NodeList valueList = element.getNamedChildren(SimpleTypeSerializator.getNodeNameString());
                if (!carsList.isEmpty()) {
                    car = ActorVehSerializator.deserializeXML((Node)carsList.get(0));
                }
                if (!valueList.isEmpty()) {
                    value = SimpleTypeSerializator.deserializeXMLString((Node)valueList.get(0));
                }
                mappedCars.put(value, car);
            }
            result.setMappedCars(mappedCars);
        }
        return result;
    }
}

