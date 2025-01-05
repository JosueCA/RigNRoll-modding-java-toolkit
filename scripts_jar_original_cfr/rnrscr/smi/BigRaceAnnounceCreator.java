/*
 * Decompiled with CFR 0.151.
 */
package rnrscr.smi;

import rnrscr.IMissionInformation;
import rnrscr.smi.Article;
import rnrscr.smi.BigracePrepareInformation;
import rnrscr.smi.IArticleCreator;

public class BigRaceAnnounceCreator
implements IArticleCreator {
    static final long serialVersionUID = 0L;
    private int race_uid;
    private int type;
    private String raceName;
    private String shortRaceName;
    private String startWarehouse;
    private String finishWarehouse;
    private int moneyPrize;
    private double ratingThreshold;
    private int yearstart;
    private int monthstart;
    private int daystart;
    private int hourstart;
    private int minstart;
    private int yearfinish;
    private int monthfinish;
    private int dayfinish;
    private int hourfinish;
    private int minfinish;
    private int yearArticleLife;
    private int monthArticleLife;
    private int dayArticleLife;
    private int hourArticleLife;

    public BigRaceAnnounceCreator(int race_uid, int type, String raceName, String shortRaceName, String startWarehouse, String finishWarehouse, int moneyPrize, double ratingThreshold, int yearstart, int monthstart, int daystart, int hourstart, int minstart, int yearfinish, int monthfinish, int dayfinish, int hourfinish, int minfinish, int yearArticleLife, int monthArticleLife, int dayArticleLife, int hourArticleLife) {
        this.race_uid = race_uid;
        this.type = type;
        this.raceName = raceName;
        this.shortRaceName = shortRaceName;
        this.startWarehouse = startWarehouse;
        this.finishWarehouse = finishWarehouse;
        this.moneyPrize = moneyPrize;
        this.ratingThreshold = ratingThreshold;
        this.yearstart = yearstart;
        this.monthstart = monthstart;
        this.daystart = daystart;
        this.hourstart = hourstart;
        this.minstart = minstart;
        this.yearfinish = yearfinish;
        this.monthfinish = monthfinish;
        this.dayfinish = dayfinish;
        this.hourfinish = hourfinish;
        this.minfinish = minfinish;
        this.yearArticleLife = yearArticleLife;
        this.monthArticleLife = monthArticleLife;
        this.dayArticleLife = dayArticleLife;
        this.hourArticleLife = hourArticleLife;
    }

    public Article create(String news_name, IMissionInformation mission_info) {
        BigracePrepareInformation article = new BigracePrepareInformation(news_name, mission_info, this.type, this.raceName, this.shortRaceName, this.startWarehouse, this.finishWarehouse, this.moneyPrize, this.ratingThreshold, this.yearstart, this.monthstart, this.daystart, this.hourstart, this.minstart, this.yearfinish, this.monthfinish, this.dayfinish, this.hourfinish, this.minfinish, this.yearArticleLife, this.monthArticleLife, this.dayArticleLife, this.hourArticleLife);
        article.setUid(this.race_uid);
        return article;
    }
}

