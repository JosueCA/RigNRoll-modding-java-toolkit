/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.List;
import rnrcore.vectorJ;
import scenarioUtils.Pair;
import xmlserialization.Helper;
import xmlutils.Node;

public class Vector3dSerializator {
    public static String getNodeName() {
        return "vector3d";
    }

    public static void serializeXML(vectorJ value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("x", value.x);
        Helper.addAttribute("y", value.y, attributes);
        Helper.addAttribute("z", value.z, attributes);
        Helper.printClosedNodeWithAttributes(stream, Vector3dSerializator.getNodeName(), attributes);
    }

    public static vectorJ deserializeXML(Node node) {
        String xString = node.getAttribute("x");
        String yString = node.getAttribute("y");
        String zString = node.getAttribute("z");
        double x = Helper.ConvertToDoubleAndWarn(xString, "x", "Vector3dSerializator in deserializeXML ");
        double y = Helper.ConvertToDoubleAndWarn(yString, "y", "Vector3dSerializator in deserializeXML ");
        double z = Helper.ConvertToDoubleAndWarn(zString, "z", "Vector3dSerializator in deserializeXML ");
        return new vectorJ(x, y, z);
    }
}

