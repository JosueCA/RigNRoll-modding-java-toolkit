/*
 * Decompiled with CFR 0.151.
 */
package rnr.bigrace.articles;

import menu.KeyPair;
import menu.MacroKit;
import rnr.bigrace.race.BigRaceLeagueId;
import rnr.bigrace.race.BigRacePlaceId;
import rnr.bigrace.race.BigRaceStageId;
import rnrcore.loc;
import rnrscr.smi.Article;

public final class BigRaceSummaryArticle
extends Article {
    private String m_seriesId = "series no name";
    private String m_destinationName = "destination no name";
    private BigRaceLeagueId m_leagueId;
    private BigRaceStageId m_stageId;
    private BigRacePlaceId m_placeId;
    private String m_firstPlaceDriverName = "first place no name";
    private String m_secondPlaceDriverName = "second place no name";
    private String m_thirdPlaceDriverName = "third place no name";

    public BigRaceSummaryArticle(String seriesId, String destinationName, BigRaceLeagueId leagueId, BigRaceStageId stageId, BigRacePlaceId placeId, String firstPlaceDriverName, String secondPlaceDriverName, String thirdPlaceDriverName, int articleLifeYear, int articleLifeMonth, int articleLifeDay, int articleLifeHour) {
        this.m_seriesId = seriesId;
        this.m_destinationName = destinationName;
        this.m_leagueId = leagueId;
        this.m_stageId = stageId;
        this.m_placeId = placeId;
        this.m_firstPlaceDriverName = firstPlaceDriverName;
        this.m_secondPlaceDriverName = secondPlaceDriverName;
        this.m_thirdPlaceDriverName = thirdPlaceDriverName;
        this.setDueToTime(articleLifeYear, articleLifeMonth, articleLifeDay, articleLifeHour);
    }

    public String getDestinationName() {
        return this.m_destinationName;
    }

    public String getSeriesId() {
        return this.m_seriesId;
    }

    public BigRaceStageId getStageId() {
        return this.m_stageId;
    }

    public BigRacePlaceId getPlaceId() {
        return this.m_placeId;
    }

    public BigRaceLeagueId getLeagueId() {
        return this.m_leagueId;
    }

    public String getFirstPlaceDriverName() {
        return this.m_firstPlaceDriverName;
    }

    public String getSecondPlaceDriverName() {
        return this.m_secondPlaceDriverName;
    }

    public String getThirdPlaceDriverName() {
        return this.m_thirdPlaceDriverName;
    }

    public String getHeader() {
        String header = this.m_leagueId.getId() == BigRaceLeagueId.MONSTER_LEAGUE.getId() && this.m_stageId.getId() == BigRaceStageId.FINAL_STAGE.getId() ? "CHAMPIONSHIPSUMMARY HEADER" : "BIGRACESUMMARY HEADER";
        return loc.getNewspaperString(header);
    }

    public String getBody() {
        String body = this.m_leagueId.getId() == BigRaceLeagueId.MONSTER_LEAGUE.getId() && this.m_stageId.getId() == BigRaceStageId.FINAL_STAGE.getId() ? "CHAMPIONSHIPSUMMARY BODY" : "BIGRACESUMMARY BODY";
        KeyPair[] template = new KeyPair[]{new KeyPair("FULLRACENAME", loc.getBigraceFullName(this.m_seriesId)), new KeyPair("DESTINATION", loc.getCityName(this.m_destinationName)), new KeyPair("LEAGUE", this.m_leagueId.getLocId()), new KeyPair("STAGE", this.m_stageId.getLocId()), new KeyPair("DRIVERNAME", this.m_firstPlaceDriverName), new KeyPair("DRIVERNAME1", this.m_secondPlaceDriverName), new KeyPair("DRIVERNAME2", this.m_thirdPlaceDriverName)};
        return MacroKit.Parse(loc.getNewspaperString(body), template);
    }

    public String getTexture() {
        String texture = "bigraceshot_";
        texture = texture + this.getLeague() + "_";
        texture = texture + this.m_seriesId + "_";
        texture = texture + this.getPlace();
        return texture;
    }

    public boolean isRaceSummary() {
        return true;
    }

    public int getPriority() {
        return 30;
    }

    private String getLeague() {
        if (this.m_leagueId.getId() == BigRaceLeagueId.MONSTER_LEAGUE.getId()) {
            return "01";
        }
        if (this.m_leagueId.getId() == BigRaceLeagueId.CHAMPIONS_LEAGUE.getId()) {
            return "02";
        }
        if (this.m_leagueId.getId() == BigRaceLeagueId.PREMIER_LEAGUE.getId()) {
            return "03";
        }
        if (this.m_leagueId.getId() == BigRaceLeagueId.FIRST_LEAGUE.getId()) {
            return "04";
        }
        if (this.m_leagueId.getId() == BigRaceLeagueId.SECOND_LEAGUE.getId()) {
            return "05";
        }
        return "05";
    }

    private String getPlace() {
        if (this.m_placeId.getId() == BigRacePlaceId.FIRST_PLACE.getId()) {
            return "gold";
        }
        if (this.m_placeId.getId() == BigRacePlaceId.SECOND_PLACE.getId()) {
            return "silver";
        }
        if (this.m_placeId.getId() == BigRacePlaceId.THIRD_PLACE.getId()) {
            return "bronze";
        }
        if (this.m_placeId.getId() == BigRacePlaceId.PARTAKING_PLACE.getId()) {
            return "noprize";
        }
        return "noprize";
    }
}

