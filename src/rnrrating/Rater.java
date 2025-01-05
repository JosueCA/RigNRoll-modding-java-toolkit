/*
 * Decompiled with CFR 0.151.
 */
package rnrrating;

public class Rater {
    private static final int numRates = 6;
    private int[] ratingRates = new int[6];
    private int checkPointRating;
    private int money = 0;

    public Rater() {
        for (int i = 0; i < 6; ++i) {
            this.ratingRates[i] = 0;
        }
    }

    public int gRatingOnStart() {
        return this.ratingRates[0];
    }

    public int gRatingOnFinish() {
        return this.ratingRates[1];
    }

    public int gRatingOnFail() {
        return this.ratingRates[2];
    }

    public int gRatingOnGold() {
        return this.ratingRates[3];
    }

    public int gRatingOnSilver() {
        return this.ratingRates[4];
    }

    public int gRatingOnBronze() {
        return this.ratingRates[5];
    }

    public int gRatingOnCheckPoint() {
        return this.checkPointRating;
    }

    public int gMoney() {
        return this.money;
    }

    public void setCheckPointRating(int value) {
        this.checkPointRating = value;
    }

    public static Rater initWhDelievery(int ongold, int onsilver, int onbronze, int onfinish, int onfail) {
        Rater rt = new Rater();
        rt.ratingRates[0] = 0;
        rt.ratingRates[1] = onfinish;
        rt.ratingRates[2] = onfail;
        rt.ratingRates[3] = ongold;
        rt.ratingRates[4] = onsilver;
        rt.ratingRates[5] = onbronze;
        return rt;
    }

    public static Rater initMission(int on_succes, int on_fail, double coef_succes, double coef_fail) {
        Rater rt = new Rater();
        rt.ratingRates[0] = 0;
        rt.ratingRates[1] = (int)((double)on_succes * coef_succes);
        rt.ratingRates[2] = (int)((double)on_fail * coef_fail);
        rt.ratingRates[3] = 0;
        rt.ratingRates[4] = 0;
        rt.ratingRates[5] = 0;
        return rt;
    }

    public static Rater initWhOtherOrder(int ongold, int onsilver, int onbronze, int onfinish, int onfail) {
        return Rater.initWhDelievery(ongold, onsilver, onbronze, onfinish, onfail);
    }

    public static Rater initWhDrivingContest(int ongold, int onsilver, int onbronze, int onfinish, int onfail) {
        return Rater.initWhDelievery(ongold, onsilver, onbronze, onfinish, onfail);
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int[] getRatingRates() {
        return this.ratingRates;
    }

    public void setRatingRates(int[] ratingRates) {
        this.ratingRates = ratingRates;
    }

    public int getCheckPointRating() {
        return this.checkPointRating;
    }
}

