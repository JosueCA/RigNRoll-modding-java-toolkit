/*
 * Decompiled with CFR 0.151.
 */
package rnrscr.smi;

import menu.KeyPair;
import menu.MacroKit;
import rnrcore.loc;
import rnrscr.smi.Article;

public class BigRaceSummary
extends Article {
    public String raceName = "race no name";
    public String notLocalizedraceName = "race no name";
    public String finishWarehouse = "finish no name";
    public String goldDriverName = "gold no name";
    public String silverDriverName = "silver no name";
    public String bronzeDriverName = "bronze no name";
    public int place = 1;
    public boolean bAnyPrize = true;
    private boolean hasNonGoldPlayersInSummary = true;
    public int type;

    public BigRaceSummary(int type, String raceName, String finishWarehouse, String goldDriverName, String silverDriverName, String bronzeDriverName, boolean bIsIgrokPrizer, int igroksPlace, int yearArticleLife, int monthArticleLife, int dayArticleLife, int hourArticleLife) {
        super(type);
        this.notLocalizedraceName = raceName;
        this.type = type;
        this.raceName = loc.getBigraceFullName(raceName);
        this.finishWarehouse = finishWarehouse;
        this.goldDriverName = goldDriverName;
        this.silverDriverName = silverDriverName;
        this.bronzeDriverName = bronzeDriverName;
        this.place = igroksPlace;
        this.bAnyPrize = bIsIgrokPrizer;
        this.hasNonGoldPlayersInSummary = true;
        this.setDueToTime(yearArticleLife, monthArticleLife, dayArticleLife, hourArticleLife);
    }

    public BigRaceSummary(int type, String raceName, String finishWarehouse, String goldDriverName, boolean bIsIgrokPrizer, int igroksPlace, int yearArticleLife, int monthArticleLife, int dayArticleLife, int hourArticleLife) {
        super(type);
        this.notLocalizedraceName = raceName;
        this.type = type;
        this.raceName = loc.getBigraceFullName(raceName);
        this.finishWarehouse = finishWarehouse;
        this.goldDriverName = goldDriverName;
        this.place = igroksPlace;
        this.bAnyPrize = bIsIgrokPrizer;
        this.hasNonGoldPlayersInSummary = false;
        this.setDueToTime(yearArticleLife, monthArticleLife, dayArticleLife, hourArticleLife);
    }

    public String getBody() {
        String body_node = "";
        switch (this.type) {
            case 1: {
                body_node = this.hasNonGoldPlayersInSummary ? "BIGRACESUMMARY BODY 1" : "BIGRACESUMMARY SHORT BODY 1";
                break;
            }
            case 2: {
                body_node = this.hasNonGoldPlayersInSummary ? "BIGRACESUMMARY BODY 2" : "BIGRACESUMMARY SHORT BODY 2";
                break;
            }
            case 3: {
                body_node = this.hasNonGoldPlayersInSummary ? "BIGRACESUMMARY BODY 3" : "BIGRACESUMMARY SHORT BODY 3";
                break;
            }
            case 4: {
                body_node = this.hasNonGoldPlayersInSummary ? "BIGRACESUMMARY BODY 4" : "BIGRACESUMMARY SHORT BODY 4";
            }
        }
        KeyPair[] template = new KeyPair[]{new KeyPair("DESTINATION", this.finishWarehouse), new KeyPair("FULLRACENAME", this.raceName), new KeyPair("DRIVERNAME", this.goldDriverName), new KeyPair("DRIVERNAME1", this.silverDriverName), new KeyPair("DRIVERNAME2", this.bronzeDriverName)};
        return MacroKit.Parse(loc.getNewspaperString(body_node), template);
    }

    public String getHeader() {
        String header_node = "";
        switch (this.type) {
            case 1: {
                header_node = "BIGRACESUMMARY HEADER 1";
                break;
            }
            case 2: {
                header_node = "BIGRACESUMMARY HEADER 2";
                break;
            }
            case 3: {
                header_node = "BIGRACESUMMARY HEADER 3";
                break;
            }
            case 4: {
                header_node = "BIGRACESUMMARY HEADER 4";
            }
        }
        return loc.getNewspaperString(header_node);
    }

    public boolean isRaceSummary() {
        return true;
    }

    public String getTexture() {
        String ret = "bigraceshot_";
        switch (this.type) {
            case 1: {
                ret = ret + "04_";
                break;
            }
            case 2: {
                ret = ret + "03_";
                break;
            }
            case 3: {
                ret = ret + "02_";
                break;
            }
            case 4: {
                ret = ret + "01_";
            }
        }
        ret = ret + this.notLocalizedraceName;
        if (this.bAnyPrize) {
            switch (this.place) {
                case 0: {
                    ret = ret + "_gold";
                    break;
                }
                case 1: {
                    ret = ret + "_silver";
                    break;
                }
                case 2: {
                    ret = ret + "_bronze";
                    break;
                }
                default: {
                    ret = ret + "noprize";
                    break;
                }
            }
        } else {
            ret = ret + "noprize";
        }
        return ret;
    }

    public int getPriority() {
        return 10;
    }
}

