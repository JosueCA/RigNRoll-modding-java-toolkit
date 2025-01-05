/*
 * Decompiled with CFR 0.151.
 */
package rnr.bigrace.articles;

import java.io.PrintStream;
import java.util.ArrayList;
import rnr.bigrace.articles.BigRaceAnnounceArticle;
import rnrscr.smi.Newspapers;
import scenarioUtils.Pair;
import xmlserialization.Helper;
import xmlutils.Node;

public final class BigRaceAnnounceArticleSerialization {
    public static String getNodeName() {
        return "newspaper_bigrace_announce_article";
    }

    public static void serializeXML(BigRaceAnnounceArticle article, PrintStream stream) {
        ArrayList<Pair<String, String>> attributes = new ArrayList<Pair<String, String>>();
        Helper.addAttribute("seriesid", article.getSeriesId(), attributes);
        Helper.addAttribute("source", article.getSourceName(), attributes);
        Helper.addAttribute("destination", article.getDestinationName(), attributes);
        Helper.addAttribute("leagueid", article.getLeagueId().getId(), attributes);
        Helper.addAttribute("stageid", article.getStageId().getId(), attributes);
        Helper.addAttribute("money", article.getPrizeMoney(), attributes);
        Helper.addAttribute("rating", article.getPrizeRating(), attributes);
        Helper.addAttribute("year", Integer.toString(article.getDueToTime().gYear()), attributes);
        Helper.addAttribute("month", Integer.toString(article.getDueToTime().gMonth()), attributes);
        Helper.addAttribute("day", Integer.toString(article.getDueToTime().gDate()), attributes);
        Helper.addAttribute("hour", Integer.toString(article.getDueToTime().gHour()), attributes);
        Helper.printClosedNodeWithAttributes(stream, BigRaceAnnounceArticleSerialization.getNodeName(), attributes);
    }

    public static void deserializeXML(Node node) {
        String errorMsg = "BigRaceAnnounceArticleSerialization on deserializeXML ";
        String seriesId = node.getAttribute("seriesid");
        String sourceName = node.getAttribute("source");
        String destinationName = node.getAttribute("destination");
        String prizeMoneyString = node.getAttribute("money");
        String prizeRatingString = node.getAttribute("rating");
        String leagueIdString = node.getAttribute("leagueid");
        String stageIdString = node.getAttribute("stageid");
        String articleLifeYearString = node.getAttribute("year");
        String articleLifeMonthString = node.getAttribute("month");
        String articleLifeDayString = node.getAttribute("day");
        String articleLifeHourString = node.getAttribute("hour");
        int leagueId = Helper.ConvertToIntegerAndWarn(leagueIdString, "leagueid", errorMsg);
        int stageId = Helper.ConvertToIntegerAndWarn(stageIdString, "stageid", errorMsg);
        int prizeMoney = Helper.ConvertToIntegerAndWarn(prizeMoneyString, "money", errorMsg);
        double prizeRating = Helper.ConvertToDoubleAndWarn(prizeRatingString, "rating", errorMsg);
        int articleLifeYear = Helper.ConvertToIntegerAndWarn(articleLifeYearString, "year", errorMsg);
        int articleLifeMonth = Helper.ConvertToIntegerAndWarn(articleLifeMonthString, "month", errorMsg);
        int articleLifeDay = Helper.ConvertToIntegerAndWarn(articleLifeDayString, "day", errorMsg);
        int articleLifeHour = Helper.ConvertToIntegerAndWarn(articleLifeHourString, "hour", errorMsg);
        Newspapers.addBigRaceAnnounceArticle(seriesId, sourceName, destinationName, leagueId, stageId, prizeMoney, prizeRating, articleLifeYear, articleLifeMonth, articleLifeDay, articleLifeHour);
    }
}

