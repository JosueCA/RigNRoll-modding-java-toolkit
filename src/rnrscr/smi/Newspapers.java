/*
 * Decompiled with CFR 0.151.
 */
package rnrscr.smi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;
import menu.KeyPair;
import menuscript.BarMenu;
import rnr.bigrace.articles.BigRaceAnnounceArticle;
import rnr.bigrace.articles.BigRaceSummaryArticle;
import rnr.bigrace.common.BigRaceUtils;
import rnr.bigrace.race.BigRaceLeagueId;
import rnr.bigrace.race.BigRacePlaceId;
import rnr.bigrace.race.BigRaceStageId;
import rnrcore.CoreTime;
import rnrscenario.missions.BigRaceAnnounceMission;
import rnrscr.IMissionInformation;
import rnrscr.smi.Article;
import rnrscr.smi.ArticleSorter;
import rnrscr.smi.BigraceFailure;
import rnrscr.smi.BigraceStartInformation;
import rnrscr.smi.NewspaperQuest;
import rnrscr.smi.TenderInformation;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Newspapers {
    private ArrayList<Article> articles = new ArrayList();
    private static Newspapers theonlynespaper = null;

    private static Newspapers getTheonly() {
        if (null == theonlynespaper) {
            theonlynespaper = new Newspapers();
        }
        return theonlynespaper;
    }

    public static void deinit() {
        theonlynespaper = null;
    }

    public static Newspapers getInstance() {
        return Newspapers.getTheonly();
    }

    private void addElement(Article acticle) {
        this.articles.add(acticle);
        Collections.sort(this.articles, new ArticleSorter());
    }

    protected void clearOldArticles() {
        CoreTime time = new CoreTime();
        Iterator<Article> iter = this.articles.iterator();
        while (iter.hasNext()) {
            Article s = iter.next();
            if (!s.isOldArticle(time)) continue;
            iter.remove();
        }
    }

    protected void clearSameNewsArticles(int uid) {
        Iterator<Article> iter = this.articles.iterator();
        while (iter.hasNext()) {
            Article s = iter.next();
            if (!s.sameNews(uid)) continue;
            iter.remove();
        }
    }

    protected void clearSameNewsArticles(Article article) {
        Iterator<Article> iter = this.articles.iterator();
        while (iter.hasNext()) {
            Article s = iter.next();
            if (!s.sameNews(article)) continue;
            iter.remove();
        }
    }

    protected void clearBigRaceAnnounceArticle(String seriesId, String sourceName, String destinationName, BigRaceLeagueId leagueId, BigRaceStageId stageId, int prizeMoney, double prizeRating) {
        Iterator<Article> iter = this.articles.iterator();
        while (iter.hasNext()) {
            BigRaceAnnounceArticle announceArticle;
            Article article = iter.next();
            if (!(article instanceof BigRaceAnnounceArticle) || !(announceArticle = (BigRaceAnnounceArticle)article).isThisArticle(seriesId, sourceName, destinationName, leagueId, stageId, prizeMoney, prizeRating)) continue;
            iter.remove();
        }
    }

    public static int numNews() {
        return Newspapers.getTheonly().articles.size();
    }

    public static BarMenu.BarEntry[] getTheOnlyNewsPaper_BarEntries(String point_name) {
        Newspapers.getTheonly().clearOldArticles();
        if (null == point_name) {
            return new BarMenu.BarEntry[0];
        }
        ArrayList<BarMenu.BarEntry> list_entries = new ArrayList<BarMenu.BarEntry>();
        for (Article s : Newspapers.getTheonly().articles) {
            BarMenu.BarEntry bar_article;
            NewspaperQuest quest = s.isQuest();
            if (null == quest) {
                bar_article = new BarMenu.BarEntry();
                bar_article.article = s;
                bar_article.papertext = s.getBody();
                bar_article.headline = s.getHeader();
                if (s.isNews()) {
                    bar_article.type = 0;
                } else if (s.isRaceAnnouncement()) {
                    bar_article.type = 1;
                } else if (s.isRaceSummary()) {
                    bar_article.type = 2;
                }
                bar_article.paperindex = s.paperIndex();
                bar_article.keys = new KeyPair[0];
                bar_article.texture_name = s.getTexture();
                list_entries.add(bar_article);
                continue;
            }
            if (!s.isRaceAnnouncement() && (!quest.getMissionInfo().hasPoint() || quest.getMissionInfo().getPointName().compareTo(point_name) != 0)) continue;
            if (s.isRaceAnnouncement()) {
                quest.getMissionInfo().setPointName(point_name);
            }
            bar_article = new BarMenu.BarEntry();
            bar_article.article = s;
            bar_article.papertext = s.getBody();
            bar_article.headline = s.getHeader();
            if (s.isNews()) {
                bar_article.type = 0;
            } else if (s.isRaceAnnouncement()) {
                bar_article.type = 1;
            } else if (s.isRaceSummary()) {
                bar_article.type = 2;
            }
            bar_article.texture_name = s.getTexture();
            bar_article.paperindex = s.paperIndex();
            bar_article.keys = new KeyPair[0];
            list_entries.add(bar_article);
        }
        BarMenu.BarEntry[] entries = new BarMenu.BarEntry[list_entries.size()];
        int count = 0;
        for (BarMenu.BarEntry entr : list_entries) {
            entries[count++] = entr;
        }
        return entries;
    }

    public static void addBigraceFailure(int race_uid, String raceName, String shortRaceName, String startWarehouse, int yearArticleLife, int monthArticleLife, int dayArticleLife, int hourArticleLife) {
        BigRaceAnnounceMission.clearAnnounced(race_uid);
        Newspapers.getTheonly().clearSameNewsArticles(race_uid);
        BigraceFailure article = new BigraceFailure(raceName, shortRaceName, startWarehouse, yearArticleLife, monthArticleLife, dayArticleLife, hourArticleLife);
        article.setUid(race_uid);
        Newspapers.getTheonly().addElement(article);
    }

    public static void addBigraceStartInformation(int race_uid, String raceName, String startWarehouse, int numParticipants, String firstPositionDriverName, int yearArticleLife, int monthArticleLife, int dayArticleLife, int hourArticleLife) {
        BigRaceAnnounceMission.clearAnnounced(race_uid);
        Newspapers.getTheonly().clearSameNewsArticles(race_uid);
        BigraceStartInformation article = new BigraceStartInformation(raceName, startWarehouse, numParticipants, firstPositionDriverName, yearArticleLife, monthArticleLife, dayArticleLife, hourArticleLife);
        article.setUid(race_uid);
        Newspapers.getTheonly().addElement(article);
    }

    public static void addBigRaceAnnounceArticle(String seriesId, String sourceName, String destinationName, int leagueId, int stageId, int prizeMoney, double prizeRating, int articleLifeYear, int articleLifeMonth, int articleLifeDay, int articleLifeHour) {
        if (!BigRaceUtils.isLeagueId(leagueId) || !BigRaceUtils.isStageId(stageId)) {
            return;
        }
        Newspapers.getTheonly().addElement(new BigRaceAnnounceArticle(seriesId, sourceName, destinationName, BigRaceUtils.convertToLeagueId(leagueId), BigRaceUtils.convertToStageId(stageId), prizeMoney, prizeRating, articleLifeYear, articleLifeMonth, articleLifeDay, articleLifeHour));
    }

    public static void deleteBigRaceAnnounceArticle(String seriesId, String sourceName, String destinationName, int leagueId, int stageId, int prizeMoney, double prizeRating) {
        if (!BigRaceUtils.isLeagueId(leagueId) || !BigRaceUtils.isStageId(stageId)) {
            return;
        }
        Newspapers.getTheonly().clearBigRaceAnnounceArticle(seriesId, sourceName, destinationName, BigRaceUtils.convertToLeagueId(leagueId), BigRaceUtils.convertToStageId(stageId), prizeMoney, prizeRating);
    }

    public static void addBigRaceSummaryArticle(String seriesId, String destinationName, int leagueId, int stageId, int placeId, String firstPlaceDriverName, String secondPlaceDriverName, String thirdPlaceDriverName, int articleLifeYear, int articleLifeMonth, int articleLifeDay, int articleLifeHour) {
        if (!(BigRaceUtils.isLeagueId(leagueId) && BigRaceUtils.isStageId(stageId) && BigRaceUtils.isPlaceId(placeId))) {
            return;
        }
        BigRacePlaceId bigRacePlaceId = BigRaceUtils.convertToPlaceId(placeId);
        if (!BigRaceUtils.isPrizePlace(bigRacePlaceId)) {
            return;
        }
        Newspapers.getTheonly().addElement(new BigRaceSummaryArticle(seriesId, destinationName, BigRaceUtils.convertToLeagueId(leagueId), BigRaceUtils.convertToStageId(stageId), bigRacePlaceId, firstPlaceDriverName, secondPlaceDriverName, thirdPlaceDriverName, articleLifeYear, articleLifeMonth, articleLifeDay, articleLifeHour));
    }

    public static void addTenderInformation(String destinationWarehouse, Vector baseNames, int multiplier, int yearArticleLife, int monthArticleLife, int dayArticleLife, int hourArticleLife) {
        Newspapers.getTheonly().addElement(new TenderInformation(destinationWarehouse, baseNames, multiplier, yearArticleLife, monthArticleLife, dayArticleLife, hourArticleLife));
    }

    public static void addMissionNewsReadyArticle(Article article) {
        Newspapers.getTheonly().clearSameNewsArticles(article);
        Newspapers.getTheonly().addElement(article);
    }

    public static void addMissionNews(String news_name, IMissionInformation mission_info) {
        Newspapers.getTheonly().addElement(new NewspaperQuest(news_name, mission_info));
    }

    public static void removeMissionNews(String news_name) {
        for (Article article : Newspapers.getTheonly().articles) {
            NewspaperQuest quest = article.isQuest();
            if (null == quest || quest.getMissionInfo().getDialogName().compareTo(news_name) != 0) continue;
            Newspapers.getTheonly().articles.remove(article);
            break;
        }
    }

    public ArrayList<Article> getArticles() {
        return this.articles;
    }
}

