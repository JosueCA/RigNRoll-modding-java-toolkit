/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.ArrayList;
import rnrscr.ModelManager;
import rnrscr.Presets;
import rnrscr.SOPresets;
import rnrscr.STOHistory;
import rnrscr.STOpresets;
import xmlserialization.Helper;
import xmlserialization.ModelPresetsSerialization;
import xmlserialization.StoHistorySerialization;
import xmlutils.Node;
import xmlutils.NodeList;

public class SoPresetSerialization {
    public static String getNodeName() {
        return "presets";
    }

    public static void serializeXML(SOPresets value, PrintStream stream) {
        Helper.openNode(stream, SoPresetSerialization.getNodeName());
        ArrayList<ModelManager.ModelPresets> models = value.getModels();
        if (null != models) {
            Helper.openNode(stream, "presetsmodels");
            for (ModelManager.ModelPresets preset2 : models) {
                ModelPresetsSerialization.serializeXML(preset2, stream);
            }
            Helper.closeNode(stream, "presetsmodels");
        }
        if (value instanceof STOpresets) {
            STOpresets stopresets = (STOpresets)value;
            Helper.printOpenNodeWithAttributes(stream, "stopresets", Helper.createSingleAttribute("money", stopresets.getMoney()));
            STOHistory history = stopresets.getHistorycomming();
            if (null != history) {
                StoHistorySerialization.serializeXML(history, stream);
            }
            Helper.closeNode(stream, "stopresets");
        }
        Helper.closeNode(stream, SoPresetSerialization.getNodeName());
    }

    public static SOPresets deserializeXML(Node node) {
        Node stoPresetNode;
        Presets result = null;
        ArrayList<ModelManager.ModelPresets> modelPresets = new ArrayList<ModelManager.ModelPresets>();
        Node models = node.getNamedChild("presetsmodels");
        if (null != models) {
            NodeList allModelPresets = models.getNamedChildren(ModelPresetsSerialization.getNodeName());
            for (Node singleModelPreset : allModelPresets) {
                ModelManager.ModelPresets model_preset = ModelPresetsSerialization.deserializeXML(singleModelPreset);
                modelPresets.add(model_preset);
            }
        }
        if (null != (stoPresetNode = node.getNamedChild("stopresets"))) {
            STOpresets stopresets = new STOpresets();
            result = stopresets;
            String moneyString = stoPresetNode.getAttribute("money");
            int moneyValue = Helper.ConvertToIntegerAndWarn(moneyString, "money", "SoPresetSerialization in deserializeXML ");
            stopresets.setMoney(moneyValue);
            Node historyNode = stoPresetNode.getNamedChild(StoHistorySerialization.getNodeName());
            if (null != historyNode) {
                STOHistory history = StoHistorySerialization.deserializeXML(historyNode);
                stopresets.setHistorycomming(history);
            }
        } else {
            result = new Presets();
        }
        result.setModels(modelPresets);
        return result;
    }
}

