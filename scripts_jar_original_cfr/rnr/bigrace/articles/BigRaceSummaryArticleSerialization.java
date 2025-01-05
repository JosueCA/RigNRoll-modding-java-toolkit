/*
 * Decompiled with CFR 0.151.
 */
package rnr.bigrace.articles;

import java.io.PrintStream;
import java.util.ArrayList;
import rnr.bigrace.articles.BigRaceSummaryArticle;
import rnrscr.smi.Newspapers;
import scenarioUtils.Pair;
import xmlserialization.Helper;
import xmlutils.Node;

public final class BigRaceSummaryArticleSerialization {
    public static String getNodeName() {
        return "newspaper_bigrace_summary_article";
    }

    public static void serializeXML(BigRaceSummaryArticle article, PrintStream stream) {
        ArrayList<Pair<String, String>> attributes = new ArrayList<Pair<String, String>>();
        Helper.addAttribute("seriesid", article.getSeriesId(), attributes);
        Helper.addAttribute("destination", article.getDestinationName(), attributes);
        Helper.addAttribute("leagueid", article.getLeagueId().getId(), attributes);
        Helper.addAttribute("stageid", article.getStageId().getId(), attributes);
        Helper.addAttribute("placeid", article.getPlaceId().getId(), attributes);
        Helper.addAttribute("firstprizername", article.getFirstPlaceDriverName(), attributes);
        Helper.addAttribute("secondprizername", article.getSecondPlaceDriverName(), attributes);
        Helper.addAttribute("thirdprizername", article.getThirdPlaceDriverName(), attributes);
        Helper.addAttribute("year", Integer.toString(article.getDueToTime().gYear()), attributes);
        Helper.addAttribute("month", Integer.toString(article.getDueToTime().gMonth()), attributes);
        Helper.addAttribute("day", Integer.toString(article.getDueToTime().gDate()), attributes);
        Helper.addAttribute("hour", Integer.toString(article.getDueToTime().gHour()), attributes);
        Helper.printClosedNodeWithAttributes(stream, BigRaceSummaryArticleSerialization.getNodeName(), attributes);
    }

    public static void deserializeXML(Node node) {
        String errorMsg = "BigRaceSummaryArticleSerialization on deserializeXML ";
        String seriesId = node.getAttribute("seriesid");
        String destinationName = node.getAttribute("destination");
        String leagueIdString = node.getAttribute("leagueid");
        String stageIdString = node.getAttribute("stageid");
        String placeIdString = node.getAttribute("placeid");
        String firstPlaceDriver = node.getAttribute("firstprizername");
        String secondPlaceDriver = node.getAttribute("secondprizername");
        String thirdPlaceDriver = node.getAttribute("thirdprizername");
        String articleLifeYearString = node.getAttribute("thirdprizername");
        String articleLifeMonthString = node.getAttribute("thirdprizername");
        String articleLifeDayString = node.getAttribute("thirdprizername");
        String articleLifeHourString = node.getAttribute("thirdprizername");
        int leagueId = Helper.ConvertToIntegerAndWarn(leagueIdString, "leagueid", errorMsg);
        int stageId = Helper.ConvertToIntegerAndWarn(stageIdString, "stageid", errorMsg);
        int placeId = Helper.ConvertToIntegerAndWarn(placeIdString, "placeid", errorMsg);
        int articleLifeYear = Helper.ConvertToIntegerAndWarn(articleLifeYearString, "year", errorMsg);
        int articleLifeMonth = Helper.ConvertToIntegerAndWarn(articleLifeMonthString, "month", errorMsg);
        int articleLifeDay = Helper.ConvertToIntegerAndWarn(articleLifeDayString, "day", errorMsg);
        int articleLifeHour = Helper.ConvertToIntegerAndWarn(articleLifeHourString, "hour", errorMsg);
        Newspapers.addBigRaceSummaryArticle(seriesId, destinationName, leagueId, stageId, placeId, firstPlaceDriver, secondPlaceDriver, thirdPlaceDriver, articleLifeYear, articleLifeMonth, articleLifeDay, articleLifeHour);
    }
}

