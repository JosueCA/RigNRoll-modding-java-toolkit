/*
 * Decompiled with CFR 0.151.
 */
package rnrscr.smi;

import menu.KeyPair;
import menu.MacroKit;
import menuscript.Converts;
import rnrcore.CoreTime;
import rnrcore.loc;
import rnrscr.IMissionInformation;
import rnrscr.smi.NewspaperQuest;

public class BigracePrepareInformation
extends NewspaperQuest {
    private String raceName = "race no name";
    private String shortRaceName = "short no name";
    private String startWarehouse = "start no name";
    private String finishWarehouse = "finish no name";
    private CoreTime startTime = null;
    private CoreTime finishTime = null;
    private int type;
    private int moneyPrize;
    private double ratingThreshold;

    private void setStartTime(int year, int month, int day, int hour, int minut) {
        this.startTime = new CoreTime(year, month, day, hour, minut);
    }

    private void setfinishTime(int year, int month, int day, int hour, int minut) {
        this.finishTime = new CoreTime(year, month, day, hour, minut);
    }

    public BigracePrepareInformation(String news_name, IMissionInformation mission_info, int type, String raceName, String shortRaceName, String startWarehouse, String finishWarehouse, int moneyPrize, double ratingThreshold, int yearstart, int monthstart, int daystart, int hourstart, int minstart, int yearfinish, int monthfinish, int dayfinish, int hourfinish, int minfinish, int yearArticleLife, int monthArticleLife, int dayArticleLife, int hourArticleLife) {
        super(type, news_name, mission_info);
        this.type = type;
        this.raceName = loc.getBigraceFullName(raceName);
        this.shortRaceName = loc.getBigraceShortName(shortRaceName);
        this.startWarehouse = loc.getCityName(startWarehouse);
        this.finishWarehouse = loc.getCityName(finishWarehouse);
        this.moneyPrize = moneyPrize;
        this.ratingThreshold = ratingThreshold;
        this.setStartTime(yearstart, monthstart, daystart, hourstart, minstart);
        this.setfinishTime(yearfinish, monthfinish, dayfinish, hourfinish, minfinish);
        this.setDueToTime(yearArticleLife, monthArticleLife, dayArticleLife, hourArticleLife);
    }

    public String getBody() {
        String body_node = "";
        switch (this.type) {
            case 1: {
                body_node = "BIGRACEPREPARE BODY 1";
                break;
            }
            case 2: {
                body_node = "BIGRACEPREPARE BODY 2";
                break;
            }
            case 3: {
                body_node = "BIGRACEPREPARE BODY 3";
                break;
            }
            case 4: {
                body_node = "BIGRACEPREPARE BODY 4";
            }
        }
        KeyPair[] template = new KeyPair[]{new KeyPair("DESTINATION", this.finishWarehouse), new KeyPair("SOURCE", this.startWarehouse), new KeyPair("PRIZE", "" + this.moneyPrize), new KeyPair("FULLRACENAME", "" + this.raceName), new KeyPair("SHORTRACENAME", "" + this.shortRaceName), new KeyPair("RATING", "" + this.ratingThreshold)};
        String ret = MacroKit.Parse(loc.getNewspaperString(body_node), template);
        String ret1 = Converts.ConvertDateAbsolute(ret, this.startTime.gMonth(), this.startTime.gDate(), this.startTime.gYear(), this.startTime.gHour(), this.startTime.gMinute());
        KeyPair[] pair = new KeyPair[]{new KeyPair("FULL_DATE1", "FULL_DATE")};
        String source1 = MacroKit.Parse(ret1, pair);
        String ret2 = Converts.ConvertDateAbsolute(source1, this.finishTime.gMonth(), this.finishTime.gDate(), this.finishTime.gYear(), this.finishTime.gHour(), this.finishTime.gMinute());
        return ret2;
    }

    public String getHeader() {
        String header_node = "";
        switch (this.type) {
            case 1: {
                header_node = "BIGRACEPREPARE HEADER 1";
                break;
            }
            case 2: {
                header_node = "BIGRACEPREPARE HEADER 2";
                break;
            }
            case 3: {
                header_node = "BIGRACEPREPARE HEADER 3";
                break;
            }
            case 4: {
                header_node = "BIGRACEPREPARE HEADER 4";
            }
        }
        return loc.getNewspaperString(header_node);
    }

    public boolean isRaceAnnouncement() {
        return true;
    }

    public String getTexture() {
        switch (this.type) {
            case 1: {
                return "tex_menu_News_Rally04";
            }
            case 2: {
                return "tex_menu_News_Rally03";
            }
            case 3: {
                return "tex_menu_News_Rally04";
            }
            case 4: {
                return "tex_menu_News_Rally01";
            }
        }
        return "error";
    }

    public int getPriority() {
        return 25;
    }
}

