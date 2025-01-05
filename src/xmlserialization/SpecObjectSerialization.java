/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.List;
import rnrscr.SOPresets;
import rnrscr.cSpecObjects;
import scenarioUtils.Pair;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlserialization.SoPresetSerialization;
import xmlutils.Node;

public class SpecObjectSerialization {
    public static String getNodeName() {
        return "specobject";
    }

    public static void serializeXML(cSpecObjects value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("name", value.name);
        Helper.addAttribute("type", value.sotip, attributes);
        Helper.printOpenNodeWithAttributes(stream, SpecObjectSerialization.getNodeName(), attributes);
        if (null != value.Presets()) {
            SoPresetSerialization.serializeXML(value.Presets(), stream);
        }
        Helper.closeNode(stream, SpecObjectSerialization.getNodeName());
    }

    public static cSpecObjects deserializeXML(Node node) {
        cSpecObjects so = new cSpecObjects();
        String name = node.getAttribute("name");
        String typeString = node.getAttribute("type");
        if (null == name) {
            Log.error("SpecObjectSerialization in deserializeXML has no attribute name");
        }
        int typeValue = Helper.ConvertToIntegerAndWarn(typeString, "type", "SpecObjectSerialization in deserializeXML ");
        so.name = name;
        so.sotip = typeValue;
        Node presetsNode = node.getNamedChild(SoPresetSerialization.getNodeName());
        if (null != presetsNode) {
            SOPresets presets2 = SoPresetSerialization.deserializeXML(presetsNode);
            so.SetPresets(presets2);
        }
        return so;
    }
}

