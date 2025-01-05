/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import rnrcore.MacroBody;
import rnrcore.MacroBodyLocString;
import rnrcore.MacroBodyString;
import rnrcore.MacroBuilder;
import rnrcore.Macros;
import scenarioUtils.Pair;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlutils.Node;
import xmlutils.NodeList;

public class MacroSerialization {
    public static String getNodeName() {
        return "macrosBody";
    }

    public static void serializeXML(MacroBody macros, PrintStream stream) {
        ArrayList<Pair<String, String>> attr = new ArrayList<Pair<String, String>>();
        if (macros instanceof MacroBodyString) {
            MacroBodyString macrosBodyString = (MacroBodyString)macros;
            Helper.addAttribute("macrosBodySimple", "", attr);
            Helper.addAttribute("value", macrosBodyString.getBody(), attr);
        } else if (macros instanceof MacroBodyLocString) {
            MacroBodyLocString macrosBodyLoc = (MacroBodyLocString)macros;
            Helper.addAttribute("macrosBodyLocref", "", attr);
            Helper.addAttribute("namespace", macrosBodyLoc.getNamespace(), attr);
            Helper.addAttribute("locref", macrosBodyLoc.getLocref(), attr);
        }
        Helper.printOpenNodeWithAttributes(stream, MacroSerialization.getNodeName(), attr);
        List<Macros> lstMacroses = macros.getMacrosList();
        if (null != lstMacroses) {
            for (Macros macro : lstMacroses) {
                Helper.printOpenNodeWithAttributes(stream, "macro", Helper.createSingleAttribute("key", macro.getKey()));
                MacroSerialization.serializeXML(macro.getBody(), stream);
                Helper.closeNode(stream, "macro");
            }
        }
        Helper.closeNode(stream, MacroSerialization.getNodeName());
    }

    public static MacroBody deserializeXML(Node node) {
        String isSimple = node.getAttribute("macrosBodySimple");
        String isLocRef = node.getAttribute("macrosBodyLocref");
        if (isSimple == null && isLocRef == null) {
            Log.error("MacroSerialization on deserialize. Cannot find any of attributes macrosBodySimple, macrosBodyLocref");
        }
        ArrayList<Macros> macroces = new ArrayList<Macros>();
        NodeList macrosNodes = node.getNamedChildren("macro");
        if (null != macrosNodes) {
            for (Node macroNode : macrosNodes) {
                Node macroBodyNode;
                String key = macroNode.getAttribute("key");
                if (null == key) {
                    Log.error("MacroSerialization on deserialize. Cannot find attributes key in node macro");
                }
                if (null == (macroBodyNode = macroNode.getNamedChild(MacroSerialization.getNodeName()))) {
                    Log.error("MacroSerialization on deserialize. Cannot find named node  " + MacroSerialization.getNodeName() + " in node " + "macro");
                }
                MacroBody macroBody = MacroSerialization.deserializeXML(macroBodyNode);
                Macros newMacros = new Macros(key, macroBody);
                macroces.add(newMacros);
            }
        }
        MacroBody res = null;
        if (null != isSimple) {
            String valueString = node.getAttribute("value");
            res = MacroBuilder.makeMacroBody(valueString, macroces);
        }
        if (null != isLocRef) {
            String namespaceString = node.getAttribute("namespace");
            String locrefString = node.getAttribute("locref");
            res = MacroBuilder.makeMacroBody(namespaceString, locrefString, macroces);
        }
        return res;
    }
}

