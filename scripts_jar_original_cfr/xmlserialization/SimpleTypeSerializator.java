/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import xmlserialization.Helper;
import xmlutils.Node;
import xmlutils.NodeList;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SimpleTypeSerializator {
    public static String getNodeNameInteger() {
        return "int";
    }

    public static String getNodeNameString() {
        return "string";
    }

    public static void serializeXMLInteger(int value, PrintStream stream) {
        Helper.printClosedNodeWithAttributes(stream, "int", Helper.createSingleAttribute("value", value));
    }

    public static void serializeXMLInteger(long value, PrintStream stream) {
        Helper.printClosedNodeWithAttributes(stream, "int", Helper.createSingleAttribute("value", value));
    }

    public static int deserializeXMLInteger(Node node) {
        String value = node.getAttribute("value");
        if (null == value) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    public static long deserializeXMLLong(Node node) {
        String value = node.getAttribute("value");
        if (null == value) {
            return 0L;
        }
        return Integer.parseInt(value);
    }

    public static void serializeXMLString(String value, PrintStream stream) {
        Helper.printClosedNodeWithAttributes(stream, "string", Helper.createSingleAttribute("value", value));
    }

    public static void serializeXMLStringList(Collection<String> value, PrintStream stream) {
        if (null == value || null == stream) {
            return;
        }
        Helper.openNode(stream, "string_list");
        for (String s : value) {
            SimpleTypeSerializator.serializeXMLString(s, stream);
        }
        Helper.closeNode(stream, "string_list");
    }

    public static List<String> deserializeXMLStringList(Node node) {
        if (null == node) {
            return Collections.emptyList();
        }
        LinkedList<String> result = new LinkedList<String>();
        NodeList strings = node.getNamedChildren("string");
        for (Node string : strings) {
            result.add(SimpleTypeSerializator.deserializeXMLString(string));
        }
        return result;
    }

    public static String deserializeXMLString(Node node) {
        return node.getAttribute("value");
    }
}

