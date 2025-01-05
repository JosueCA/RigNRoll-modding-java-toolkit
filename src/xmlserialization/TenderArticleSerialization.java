/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import rnrscr.smi.Newspapers;
import rnrscr.smi.TenderInformation;
import scenarioUtils.Pair;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlserialization.SimpleTypeSerializator;
import xmlutils.Node;
import xmlutils.NodeList;

public class TenderArticleSerialization {
    public static String getNodeName() {
        return "newspaper_tender";
    }

    public static void serializeXML(TenderInformation value, PrintStream stream) {
        TenderInformation.FeeMultiplier multiplier = value.getMulpiplier();
        int multiplierValue = 1;
        if (multiplier == TenderInformation.FeeMultiplier.DOUBLE) {
            multiplierValue = 2;
        } else if (multiplier == TenderInformation.FeeMultiplier.QUADRUPLE) {
            multiplierValue = 4;
        } else if (multiplier == TenderInformation.FeeMultiplier.TRIPLE) {
            multiplierValue = 3;
        }
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("day", value.getTimeEnd().gDate());
        Helper.addAttribute("destination", value.getDestinationWarehouse(), attributes);
        Helper.addAttribute("hour", value.getTimeEnd().gHour(), attributes);
        Helper.addAttribute("month", value.getTimeEnd().gMonth(), attributes);
        Helper.addAttribute("multiplier", multiplierValue, attributes);
        Helper.addAttribute("year", value.getTimeEnd().gYear(), attributes);
        Helper.printOpenNodeWithAttributes(stream, TenderArticleSerialization.getNodeName(), attributes);
        ArrayList<String> baseNames = value.getWarehouses();
        if (null == baseNames || baseNames.isEmpty()) {
            Log.error("TenderArticleSerialization in serializeXML has empty Warehouses.");
        } else {
            Helper.openNode(stream, "bases");
            for (String baseName : baseNames) {
                SimpleTypeSerializator.serializeXMLString(baseName, stream);
            }
            Helper.closeNode(stream, "bases");
        }
        Helper.closeNode(stream, TenderArticleSerialization.getNodeName());
    }

    public static void deserializeXML(Node node) {
        String errorrMessage = "TenderArticleSerialization on deserializeXML ";
        String dayString = node.getAttribute("day");
        String destinationString = node.getAttribute("destination");
        String hourString = node.getAttribute("hour");
        String monthString = node.getAttribute("month");
        String multiplierString = node.getAttribute("multiplier");
        String yearString = node.getAttribute("year");
        int year = Helper.ConvertToIntegerAndWarn(yearString, "year", errorrMessage);
        int month = Helper.ConvertToIntegerAndWarn(monthString, "month", errorrMessage);
        int day = Helper.ConvertToIntegerAndWarn(dayString, "day", errorrMessage);
        int hour = Helper.ConvertToIntegerAndWarn(hourString, "hour", errorrMessage);
        int multiplier = Helper.ConvertToIntegerAndWarn(multiplierString, "multiplier", errorrMessage);
        Vector<String> bases = new Vector<String>();
        Node baseNamesNode = node.getNamedChild("bases");
        if (null == baseNamesNode) {
            Log.error(errorrMessage + " has no node named " + "bases");
        } else {
            NodeList listBaseNames = baseNamesNode.getNamedChildren(SimpleTypeSerializator.getNodeNameString());
            if (null == listBaseNames || listBaseNames.isEmpty()) {
                Log.error(errorrMessage + " node named " + "bases" + " has no children named " + SimpleTypeSerializator.getNodeNameString());
            } else {
                for (Node stringNode : listBaseNames) {
                    String baseName = SimpleTypeSerializator.deserializeXMLString(stringNode);
                    bases.add(baseName);
                }
            }
        }
        Newspapers.addTenderInformation(destinationString, bases, multiplier, year, month, day, hour);
    }
}

