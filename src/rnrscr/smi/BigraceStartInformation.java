/*
 * Decompiled with CFR 0.151.
 */
package rnrscr.smi;

import menu.KeyPair;
import menu.MacroKit;
import rnrcore.loc;
import rnrscr.smi.Article;

public class BigraceStartInformation
extends Article {
    public String raceName = "race no name";
    public String startWarehouse = "start no name";
    public int numParticipants = 0;
    public String firstPositionDriverName = "first no name";

    public BigraceStartInformation(String raceName, String startWarehouse, int numParticipants, String firstPositionDriverName, int yearArticleLife, int monthArticleLife, int dayArticleLife, int hourArticleLife) {
        this.raceName = loc.getBigraceFullName(raceName);
        this.startWarehouse = startWarehouse;
        this.numParticipants = numParticipants;
        this.firstPositionDriverName = firstPositionDriverName;
        this.setDueToTime(yearArticleLife, monthArticleLife, dayArticleLife, hourArticleLife);
    }

    public String getBody() {
        KeyPair[] template = new KeyPair[]{new KeyPair("FULLRACENAME", this.raceName), new KeyPair("SOURCE", this.startWarehouse), new KeyPair("NUMBER", "" + this.numParticipants), new KeyPair("DRIVERNAME", "" + this.firstPositionDriverName)};
        return MacroKit.Parse(loc.getNewspaperString("BIGRACESTART BODY"), template);
    }

    public String getHeader() {
        return loc.getNewspaperString("BIGRACESTART HEADER");
    }

    public boolean isRaceAnnouncement() {
        return true;
    }

    public String getTexture() {
        return "tex_menu_News_Rally_started";
    }

    public int getPriority() {
        return 10;
    }
}

