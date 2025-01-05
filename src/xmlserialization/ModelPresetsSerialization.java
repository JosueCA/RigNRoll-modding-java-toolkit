/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.List;
import rnrscr.ModelManager;
import scenarioUtils.Pair;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlutils.Node;

public class ModelPresetsSerialization {
    public static String getNodeName() {
        return "somodelpreset";
    }

    public static void serializeXML(ModelManager.ModelPresets value, PrintStream stream) {
        List<Pair<String, String>> attribute = Helper.createSingleAttribute("isman", value.isIs_man());
        Helper.addAttribute("name", value.getName(), attribute);
        Helper.addAttribute("placeflag", value.getPlaceFlags(), attribute);
        Helper.addAttribute("tag", value.getTag(), attribute);
        Helper.addAttribute("weight", value.getWeigh(), attribute);
        Helper.printClosedNodeWithAttributes(stream, ModelPresetsSerialization.getNodeName(), attribute);
    }

    public static ModelManager.ModelPresets deserializeXML(Node node) {
        String model_name = node.getAttribute("name");
        String placesString = node.getAttribute("placeflag");
        String tagStrign = node.getAttribute("tag");
        String ismanString = node.getAttribute("isman");
        String weightString = node.getAttribute("weight");
        int places = ModelPresetsSerialization.ConvertToIntegerAndWarn(placesString, "placeflag");
        int new_tag = ModelPresetsSerialization.ConvertToIntegerAndWarn(tagStrign, "tag");
        boolean is_man = ModelPresetsSerialization.ConvertToBooleanAndWarn(ismanString, "isman");
        int weight = ModelPresetsSerialization.ConvertToIntegerAndWarn(weightString, "weight");
        ModelManager.ModelPresets result = new ModelManager.ModelPresets(model_name, places, new_tag, is_man);
        result.setWeigh(weight);
        return result;
    }

    private static int ConvertToIntegerAndWarn(String stringValue, String attributeName) {
        if (null == stringValue) {
            Log.error("ModelPresetsSerialization in deserializeXML cannot find attribute " + attributeName);
            return 0;
        }
        try {
            int intValue = Integer.parseInt(stringValue);
            return intValue;
        }
        catch (NumberFormatException e) {
            Log.error("ModelPresetsSerialization in deserializeXML cannot convert attribute " + attributeName + " with value " + stringValue + " to integer.");
            return 0;
        }
    }

    private static boolean ConvertToBooleanAndWarn(String stringValue, String attributeName) {
        if (null == stringValue) {
            Log.error("ModelPresetsSerialization in deserializeXML cannot find attribute " + attributeName);
            return false;
        }
        try {
            boolean boolValue = Boolean.parseBoolean(stringValue);
            return boolValue;
        }
        catch (NumberFormatException e) {
            Log.error("ModelPresetsSerialization in deserializeXML cannot convert attribute " + attributeName + " with value " + stringValue + " to boolean.");
            return false;
        }
    }
}

