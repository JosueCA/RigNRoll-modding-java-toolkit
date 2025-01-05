/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.List;
import rnrcore.CoreTime;
import scenarioUtils.Pair;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlutils.Node;

public class CoreTimeSerialization {
    public static String getNodeName() {
        return "coretime";
    }

    public static void serializeXML(CoreTime time, PrintStream stream) {
        List<Pair<String, String>> attrinutes = Helper.createSingleAttribute("year", time.gYear());
        Helper.addAttribute("month", time.gMonth(), attrinutes);
        Helper.addAttribute("date", time.gDate(), attrinutes);
        Helper.addAttribute("hour", time.gHour(), attrinutes);
        Helper.addAttribute("minuten", time.gMinute(), attrinutes);
        Helper.printClosedNodeWithAttributes(stream, CoreTimeSerialization.getNodeName(), attrinutes);
    }

    public static CoreTime deserializeXML(Node node) {
        String yearString = node.getAttribute("year");
        String monthString = node.getAttribute("month");
        String dateString = node.getAttribute("date");
        String hourString = node.getAttribute("hour");
        String minutsString = node.getAttribute("minuten");
        int yearValue = CoreTimeSerialization.ConvertToIntegerAndWarn(yearString, "year");
        int monthValue = CoreTimeSerialization.ConvertToIntegerAndWarn(monthString, "month");
        int dateValue = CoreTimeSerialization.ConvertToIntegerAndWarn(dateString, "date");
        int hourValue = CoreTimeSerialization.ConvertToIntegerAndWarn(hourString, "hour");
        int minutValue = CoreTimeSerialization.ConvertToIntegerAndWarn(minutsString, "minuten");
        CoreTime result = new CoreTime(yearValue, monthValue, dateValue, hourValue, minutValue);
        return result;
    }

    private static int ConvertToIntegerAndWarn(String stringValue, String attributeName) {
        if (null == stringValue) {
            Log.error("CoreTimeSerialization in deserializeXML cannot find attribute " + attributeName);
            return 0;
        }
        try {
            int intValue = Integer.parseInt(stringValue);
            return intValue;
        }
        catch (NumberFormatException e) {
            Log.error("CoreTimeSerialization in deserializeXML cannot convert attribute " + attributeName + " with value " + stringValue + " to integer.");
            return 0;
        }
    }
}

