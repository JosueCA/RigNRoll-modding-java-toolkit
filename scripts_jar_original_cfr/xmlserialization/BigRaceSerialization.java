/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.List;
import org.w3c.dom.Node;
import rnrcore.CoreTime;
import rnrcore.XmlSerializable;
import rnrrating.BigRace;
import scenarioUtils.Pair;
import xmlserialization.CoreTimeSerialization;
import xmlserialization.Helper;
import xmlserialization.Log;

public class BigRaceSerialization
implements XmlSerializable {
    private static BigRaceSerialization instance = new BigRaceSerialization();

    public static BigRaceSerialization getInstance() {
        return instance;
    }

    public String getRootNodeName() {
        return BigRaceSerialization.getNodeName();
    }

    public void loadFromNode(Node nodeDom) {
        BigRaceSerialization.deserializeXML(new xmlutils.Node(nodeDom));
    }

    public void yourNodeWasNotFound() {
    }

    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        BigRaceSerialization.serializeXML(BigRace.gReference(), stream);
    }

    public static String getNodeName() {
        return "bigrace";
    }

    public static void serializeXML(BigRace value, PrintStream stream) {
        CoreTime nextRace;
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("firsttime", value.isFirst_time_scheduled_race());
        Helper.addAttribute("monstercuppassed", value.isMostercup_processed(), attributes);
        Helper.addAttribute("seriehigh", value.getSeries_high(), attributes);
        Helper.addAttribute("serieslow", value.getSeries_low(), attributes);
        Helper.printOpenNodeWithAttributes(stream, BigRaceSerialization.getNodeName(), attributes);
        CoreTime lastTimeScheduled = value.getLasttimesceduled();
        if (null != lastTimeScheduled) {
            Helper.openNode(stream, "lasttimescheduled");
            CoreTimeSerialization.serializeXML(lastTimeScheduled, stream);
            Helper.closeNode(stream, "lasttimescheduled");
        }
        if (null != (nextRace = value.getNext_race())) {
            Helper.openNode(stream, "nextrace");
            CoreTimeSerialization.serializeXML(nextRace, stream);
            Helper.closeNode(stream, "nextrace");
        }
        Helper.closeNode(stream, BigRaceSerialization.getNodeName());
    }

    public static void deserializeXML(xmlutils.Node node) {
        xmlutils.Node nodeTime;
        String firstTimeString = node.getAttribute("firsttime");
        String mostercuppassedString = node.getAttribute("monstercuppassed");
        String serieshighString = node.getAttribute("seriehigh");
        String serieslowString = node.getAttribute("serieslow");
        int seriesHigh = BigRaceSerialization.ConvertToIntegerAndWarn(serieshighString, "seriehigh");
        int seriesLow = BigRaceSerialization.ConvertToIntegerAndWarn(serieslowString, "serieslow");
        boolean firsttimeValue = BigRaceSerialization.ConvertToBooleanAndWarn(firstTimeString, "firsttime");
        boolean monstercuppassedValue = BigRaceSerialization.ConvertToBooleanAndWarn(mostercuppassedString, "monstercuppassed");
        BigRace.gReference().setFirst_time_scheduled_race(firsttimeValue);
        BigRace.gReference().setMostercup_processed(monstercuppassedValue);
        BigRace.gReference().setSeries_high(seriesHigh);
        BigRace.gReference().setSeries_low(seriesLow);
        xmlutils.Node lastTimeNode = node.getNamedChild("lasttimescheduled");
        xmlutils.Node nextRaceNode = node.getNamedChild("nextrace");
        if (null != lastTimeNode && null != (nodeTime = lastTimeNode.getNamedChild(CoreTimeSerialization.getNodeName()))) {
            CoreTime lastTimeValue = CoreTimeSerialization.deserializeXML(nodeTime);
            BigRace.gReference().setLasttimesceduled(lastTimeValue);
            if (null != nextRaceNode) {
                CoreTime nextRaceValue = CoreTimeSerialization.deserializeXML(nodeTime);
                BigRace.gReference().setNext_race(nextRaceValue);
            }
        }
    }

    private static int ConvertToIntegerAndWarn(String stringValue, String attributeName) {
        if (null == stringValue) {
            Log.error("BigRaceSerialization in deserializeXML cannot find attribute " + attributeName);
            return 0;
        }
        try {
            return Integer.parseInt(stringValue);
        }
        catch (NumberFormatException e) {
            Log.error("BigRaceSerialization in deserializeXML cannot convert attribute " + attributeName + " with value " + stringValue + " to integer.");
            return 0;
        }
    }

    private static boolean ConvertToBooleanAndWarn(String stringValue, String attributeName) {
        if (null == stringValue) {
            Log.error("BigRaceSerialization in deserializeXML cannot find attribute " + attributeName);
            return false;
        }
        try {
            return Boolean.parseBoolean(stringValue);
        }
        catch (NumberFormatException e) {
            Log.error("BigRaceSerialization in deserializeXML cannot convert attribute " + attributeName + " with value " + stringValue + " to boolean.");
            return false;
        }
    }
}

