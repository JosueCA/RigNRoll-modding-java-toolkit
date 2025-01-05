/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.List;
import rnrscr.STOHistory;
import scenarioUtils.Pair;
import xmlserialization.Helper;
import xmlutils.Node;

public class StoHistorySerialization {
    public static String getNodeName() {
        return "stohistory";
    }

    public static void serializeXML(STOHistory value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("cameWithNewCar", value.isCameWithNewCar());
        Helper.addAttribute("count_meetings", value.getCount_meetings(), attributes);
        Helper.addAttribute("damagelevel", value.getDamagelevel(), attributes);
        Helper.addAttribute("lastcarmodel", value.getLastcarmodel(), attributes);
        Helper.printClosedNodeWithAttributes(stream, StoHistorySerialization.getNodeName(), attributes);
    }

    public static STOHistory deserializeXML(Node node) {
        String cameWithNewCarString = node.getAttribute("cameWithNewCar");
        String countMeetingsString = node.getAttribute("count_meetings");
        String damageLevelString = node.getAttribute("damagelevel");
        String lastCarModelString = node.getAttribute("lastcarmodel");
        boolean cameWithNewCarValue = Helper.ConvertToBooleanAndWarn(cameWithNewCarString, "cameWithNewCar", "StoHistorySerialization in deserializeXML ");
        int countMeetingsValue = Helper.ConvertToIntegerAndWarn(countMeetingsString, "count_meetings", "StoHistorySerialization in deserializeXML ");
        double damageLevelValue = Helper.ConvertToDoubleAndWarn(damageLevelString, "damagelevel", "StoHistorySerialization in deserializeXML ");
        STOHistory result = new STOHistory();
        if (null != lastCarModelString) {
            result.setLastcarmodel(lastCarModelString);
        }
        result.setCameWithNewCar(cameWithNewCarValue);
        result.setCount_meetings(countMeetingsValue);
        result.setDamagelevel(damageLevelValue);
        return result;
    }
}

