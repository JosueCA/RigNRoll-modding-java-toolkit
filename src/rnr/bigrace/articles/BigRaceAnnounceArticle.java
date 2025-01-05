/*
 * Decompiled with CFR 0.151.
 */
package rnr.bigrace.articles;

import menu.KeyPair;
import menu.MacroKit;
import menuscript.Converts;
import rnr.bigrace.race.BigRaceLeagueId;
import rnr.bigrace.race.BigRaceStageId;
import rnrcore.eng;
import rnrcore.loc;
import rnrscr.smi.Article;

public final class BigRaceAnnounceArticle
extends Article {
    private String m_seriesId = "full race no name";
    private BigRaceLeagueId m_leagueId;
    private BigRaceStageId m_stageId;
    private String m_sourceName = "source no name";
    private String m_destinationName = "destination no name";
    private int m_prizeMoney;
    private double m_prizeRating;

    public BigRaceAnnounceArticle(String seriesId, String sourceName, String destinationName, BigRaceLeagueId leagueId, BigRaceStageId stageId, int prizeMoney, double prizeRating, int articleLifeYear, int articleLifeMonth, int articleLifeDay, int articleLifeHour) {
        this.m_seriesId = seriesId;
        this.m_sourceName = sourceName;
        this.m_destinationName = destinationName;
        this.m_leagueId = leagueId;
        this.m_stageId = stageId;
        this.m_prizeMoney = prizeMoney;
        this.m_prizeRating = prizeRating;
        this.setDueToTime(articleLifeYear, articleLifeMonth, articleLifeDay, articleLifeHour);
    }

    public boolean isThisArticle(String seriesId, String sourceName, String destinationName, BigRaceLeagueId leagueId, BigRaceStageId stageId, int prizeMoney, double prizeRating) {
        return this.m_seriesId.equals(seriesId) && this.m_sourceName.equals(sourceName) && this.m_destinationName.equals(destinationName) && this.m_leagueId.getId() == leagueId.getId() && this.m_stageId.getId() == stageId.getId() && this.m_prizeMoney == prizeMoney && this.m_prizeRating == prizeRating;
    }

    public String getSeriesId() {
        return this.m_seriesId;
    }

    public String getSourceName() {
        return this.m_sourceName;
    }

    public String getDestinationName() {
        return this.m_destinationName;
    }

    public BigRaceLeagueId getLeagueId() {
        return this.m_leagueId;
    }

    public BigRaceStageId getStageId() {
        return this.m_stageId;
    }

    public String getPrizeMoney() {
        return Integer.toString(this.m_prizeMoney);
    }

    public String getPrizeRating() {
        return Double.toString(this.m_prizeRating);
    }

    public String getHeader() {
        String header = this.m_leagueId.getId() == BigRaceLeagueId.MONSTER_LEAGUE.getId() ? "BIGRACEPREPARE HEADER 4" : (this.m_leagueId.getId() == BigRaceLeagueId.CHAMPIONS_LEAGUE.getId() ? "BIGRACEPREPARE HEADER 3" : (this.m_leagueId.getId() == BigRaceLeagueId.PREMIER_LEAGUE.getId() ? "BIGRACEPREPARE HEADER 2" : (this.m_leagueId.getId() == BigRaceLeagueId.FIRST_LEAGUE.getId() ? "BIGRACEPREPARE HEADER 1" : "BIGRACEPREPARE HEADER 0")));
        KeyPair[] template = new KeyPair[]{new KeyPair("SHORTRACENAME", loc.getBigraceShortName(this.m_seriesId))};
        return MacroKit.Parse(loc.getNewspaperString(header), template);
    }

    public String getBody() {
        String body = this.m_leagueId.getId() == BigRaceLeagueId.MONSTER_LEAGUE.getId() ? "BIGRACEPREPARE BODY 4" : (this.m_leagueId.getId() == BigRaceLeagueId.CHAMPIONS_LEAGUE.getId() ? "BIGRACEPREPARE BODY 3" : (this.m_leagueId.getId() == BigRaceLeagueId.PREMIER_LEAGUE.getId() ? "BIGRACEPREPARE BODY 2" : (this.m_leagueId.getId() == BigRaceLeagueId.FIRST_LEAGUE.getId() ? "BIGRACEPREPARE BODY 1" : "BIGRACEPREPARE BODY 0")));
        KeyPair[] template = new KeyPair[]{new KeyPair("FULLRACENAME", loc.getBigraceFullName(this.m_seriesId)), new KeyPair("SHORTRACENAME", loc.getBigraceShortName(this.m_seriesId)), new KeyPair("SOURCE", loc.getCityName(this.m_sourceName)), new KeyPair("DESTINATION", loc.getCityName(this.m_destinationName)), new KeyPair("LEAGUE", this.m_leagueId.getLocId()), new KeyPair("STAGE", this.m_stageId.getLocId()), new KeyPair("PRIZE", Integer.toString(this.m_prizeMoney)), new KeyPair("RATING", Converts.ConvertRating(eng.visualRating(this.m_prizeRating)))};
        return MacroKit.Parse(loc.getNewspaperString(body), template);
    }

    public String getTexture() {
        String texture = this.m_leagueId.getId() == BigRaceLeagueId.MONSTER_LEAGUE.getId() ? "tex_menu_News_Rally01_NEW" : (this.m_leagueId.getId() == BigRaceLeagueId.CHAMPIONS_LEAGUE.getId() ? "tex_menu_News_Rally02" : (this.m_leagueId.getId() == BigRaceLeagueId.PREMIER_LEAGUE.getId() ? "tex_menu_News_Rally03" : (this.m_leagueId.getId() == BigRaceLeagueId.FIRST_LEAGUE.getId() ? "tex_menu_News_Rally04" : "tex_menu_News_Rally05")));
        return texture;
    }

    public boolean isRaceAnnouncement() {
        return true;
    }

    public int getPriority() {
        return 25;
    }
}

