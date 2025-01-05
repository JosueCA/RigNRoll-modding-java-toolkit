/*
 * Decompiled with CFR 0.151.
 */
package rnrconfig;

import java.util.Vector;
import xmlutils.Node;
import xmlutils.NodeList;
import xmlutils.XmlUtils;

public class ReservedCars {
    private static Vector list = null;
    private static final String FILENAME = "..\\data\\config\\reservedvehicles.xml";
    private static final String ROOT = "vehicle";
    private static final String NAME = "name";
    private static final String COLORS = "color";
    private static final String NUM = "num";

    private static void parse(Node node) {
        String vehicle_name = node.getAttribute(NAME);
        NodeList childs = node.getNamedChildren(COLORS);
        if (childs == null || childs.size() == 0) {
            ReservedVehicle item = new ReservedVehicle();
            item.name = vehicle_name;
            item.color = -1;
            list.add(item);
        } else {
            for (int i = 0; i < childs.size(); ++i) {
                if (childs.get(i) == null) continue;
                String s_number = ((Node)childs.get(i)).getAttribute(NUM);
                ReservedVehicle item = new ReservedVehicle();
                item.name = vehicle_name;
                item.color = new Integer(s_number);
                list.add(item);
            }
        }
    }

    private static void pasrse() {
        NodeList vehicles;
        if (list != null && list.size() != 0) {
            return;
        }
        list = new Vector();
        Node top = XmlUtils.parse(FILENAME);
        if (top != null && (vehicles = top.getNamedChildren(ROOT)) != null) {
            for (int i = 0; i < vehicles.size(); ++i) {
                ReservedCars.parse((Node)vehicles.get(i));
            }
        }
    }

    public static void reserve() {
        ReservedCars.pasrse();
        if (list != null) {
            for (int i = 0; i < list.size(); ++i) {
                ReservedVehicle item = (ReservedVehicle)list.get(i);
                ReservedCars.reserveCar(item.name, item.color, true);
            }
        }
    }

    public static native void reserveCar(String var0, int var1, boolean var2);

    private static class ReservedVehicle {
        String name;
        int color;

        private ReservedVehicle() {
        }
    }
}

