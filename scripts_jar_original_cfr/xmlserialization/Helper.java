/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import scenarioUtils.Pair;
import xmlserialization.Log;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Helper {
    private static List<Pair<String, String>> s_xmlSpecialSymbols = new ArrayList<Pair<String, String>>();

    private static String replaceSpecialSymbols(String dirtyString) {
        if (null == dirtyString) {
            return null;
        }
        StringBuffer buffer = new StringBuffer(dirtyString);
        for (Pair<String, String> pair : s_xmlSpecialSymbols) {
            int lastSymbol = 0;
            int firstIndex = buffer.indexOf(pair.getFirst(), lastSymbol);
            int szWas = pair.getFirst().length();
            int szReplace = pair.getSecond().length();
            while (firstIndex != -1) {
                buffer.replace(firstIndex, firstIndex + szWas, pair.getSecond());
                lastSymbol = firstIndex + szReplace;
                firstIndex = buffer.indexOf(pair.getFirst(), lastSymbol);
            }
        }
        String cleanString = buffer.toString();
        return cleanString;
    }

    public static String printNodeWithAttributes(String nodeName, List<Pair<String, String>> attributeValueSet) {
        if (null == attributeValueSet) {
            return nodeName;
        }
        String result = nodeName;
        for (Pair<String, String> pair : attributeValueSet) {
            result = result + ' ' + pair.getFirst() + "=\"" + Helper.replaceSpecialSymbols(pair.getSecond()) + '\"';
        }
        return result;
    }

    public static void printClosedNodeWithAttributes(PrintStream stream, String nodename, List<Pair<String, String>> attributeValueSet) {
        stream.println('<' + Helper.printNodeWithAttributes(nodename, attributeValueSet) + "/>");
    }

    public static void printClosedNodeWithAttributes(PrintStream stream, String nodename, int version, List<Pair<String, String>> attributeValueSet) {
        Pair<String, String> addVersionattribute = new Pair<String, String>("version", Integer.toString(version));
        attributeValueSet.add(addVersionattribute);
        stream.println('<' + Helper.printNodeWithAttributes(nodename, attributeValueSet) + "/>");
    }

    public static void printOpenNodeWithAttributes(PrintStream stream, String nodeName, List<Pair<String, String>> attributeValueSet) {
        stream.println('<' + Helper.printNodeWithAttributes(nodeName, attributeValueSet) + '>');
    }

    public static void printOpenNodeWithAttributes(PrintStream stream, String nodeName, int version, List<Pair<String, String>> attributeValueSet) {
        Pair<String, String> addVersionattribute = new Pair<String, String>("version", Integer.toString(version));
        attributeValueSet.add(addVersionattribute);
        stream.println('<' + Helper.printNodeWithAttributes(nodeName, attributeValueSet) + '>');
    }

    public static void openNode(PrintStream stream, String nodeName) {
        stream.println('<' + nodeName + '>');
    }

    public static void openNode(PrintStream stream, String nodeName, int version) {
        stream.println('<' + nodeName + ' ' + "version" + "=\"" + version + "\">");
    }

    public static void closeNode(PrintStream stream, String nodeName) {
        stream.println("</" + nodeName + '>');
    }

    public static List<Pair<String, String>> createSingleAttribute(String name, String value) {
        ArrayList<Pair<String, String>> result = new ArrayList<Pair<String, String>>();
        result.add(new Pair<String, String>(name, value));
        return result;
    }

    public static List<Pair<String, String>> createSingleAttribute(String name, int value) {
        ArrayList<Pair<String, String>> result = new ArrayList<Pair<String, String>>();
        result.add(new Pair<String, String>(name, Integer.toString(value)));
        return result;
    }

    public static List<Pair<String, String>> createSingleAttribute(String name, boolean value) {
        ArrayList<Pair<String, String>> result = new ArrayList<Pair<String, String>>();
        result.add(new Pair<String, String>(name, Boolean.toString(value)));
        return result;
    }

    public static List<Pair<String, String>> createSingleAttribute(String name, double value) {
        ArrayList<Pair<String, String>> result = new ArrayList<Pair<String, String>>();
        result.add(new Pair<String, String>(name, Double.toString(value)));
        return result;
    }

    public static void addAttribute(String name, int value, List<Pair<String, String>> attributes) {
        attributes.add(new Pair<String, String>(name, Integer.toString(value)));
    }

    public static void addAttribute(String name, String value, List<Pair<String, String>> attributes) {
        attributes.add(new Pair<String, String>(name, value));
    }

    public static void addAttribute(String name, boolean value, List<Pair<String, String>> attributes) {
        attributes.add(new Pair<String, String>(name, Boolean.toString(value)));
    }

    public static void addAttribute(String name, double value, List<Pair<String, String>> attributes) {
        attributes.add(new Pair<String, String>(name, Double.toString(value)));
    }

    public static int ConvertToIntegerAndWarn(String stringValue, String attributeName, String message) {
        if (null == stringValue) {
            Log.error(message + " cannot find attribute " + attributeName);
            return 0;
        }
        try {
            return Integer.parseInt(stringValue);
        }
        catch (NumberFormatException e) {
            Log.error(message + " cannot convert attribute " + attributeName + " with value " + stringValue + " to integer.");
            return 0;
        }
    }

    public static boolean ConvertToBooleanAndWarn(String stringValue, String attributeName, String message) {
        if (null == stringValue) {
            Log.error(message + " cannot find attribute " + attributeName);
            return false;
        }
        if (0 != "false".compareToIgnoreCase(stringValue) && 0 != "true".compareToIgnoreCase(stringValue)) {
            Log.error(message + " cannot convert attribute " + attributeName + " with value " + stringValue + " to boolean.");
            return false;
        }
        return Boolean.parseBoolean(stringValue);
    }

    public static double ConvertToDoubleAndWarn(String stringValue, String attributeName, String message) {
        if (null == stringValue) {
            Log.error(message + " cannot find attribute " + attributeName);
            return 0.0;
        }
        try {
            return Double.parseDouble(stringValue);
        }
        catch (NumberFormatException e) {
            Log.error(message + " cannot convert attribute " + attributeName + " with value " + stringValue + " to boolean.");
            return 0.0;
        }
    }

    static {
        s_xmlSpecialSymbols.add(new Pair<String, String>("&", "&amp;"));
        s_xmlSpecialSymbols.add(new Pair<String, String>("<", "&lt;"));
        s_xmlSpecialSymbols.add(new Pair<String, String>(">", "&gt;"));
        s_xmlSpecialSymbols.add(new Pair<String, String>("\"", "&quot;"));
        s_xmlSpecialSymbols.add(new Pair<String, String>("'", "&#39;"));
    }
}

