/*
 * Decompiled with CFR 0.151.
 */
package rnrscr.smi;

import menu.KeyPair;
import menu.MacroKit;
import rnrcore.loc;
import rnrscr.smi.Article;

public class BigraceFailure
extends Article {
    public String raceName = "no name";
    public String shortRaceName = "short no name";
    public String startWarehouse = "start no name";

    public BigraceFailure(String raceName, String shortRaceName, String startWarehouse, int yearArticleLife, int monthArticleLife, int dayArticleLife, int hourArticleLife) {
        this.raceName = loc.getBigraceFullName(raceName);
        this.shortRaceName = loc.getBigraceShortName(shortRaceName);
        this.startWarehouse = startWarehouse;
        this.setDueToTime(yearArticleLife, monthArticleLife, dayArticleLife, hourArticleLife);
    }

    public String getBody() {
        KeyPair[] template = new KeyPair[]{new KeyPair("FULLRACENAME", this.raceName), new KeyPair("SOURCE", this.startWarehouse)};
        return MacroKit.Parse(loc.getNewspaperString("BIGRACEFAILURE BODY"), template);
    }

    public String getHeader() {
        KeyPair[] template = new KeyPair[]{new KeyPair("SHORTRACENAME", this.shortRaceName)};
        return MacroKit.Parse(loc.getNewspaperString("BIGRACEFAILURE HEADER"), template);
    }

    public boolean isRaceAnnouncement() {
        return true;
    }

    public String getTexture() {
        return "tex_menu_News_Rally_failed";
    }

    public int getPriority() {
        return 10;
    }
}

