/*
 * Decompiled with CFR 0.151.
 */
package rnrorg;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import rnrcore.Log;
import rnrrating.FactionRater;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class RewardForfeit {
    private double coef_money = 0.0;
    private double coef_rate = 0.0;
    private double coef_rank = 0.0;
    private int money = 0;
    private double rate = 0.0;
    private int rank = 0;
    private int real_money = 0;
    private double real_rate = 0.0;
    private int real_rank = 0;
    private int flag = 0;
    private HashMap<String, Double> m_factionRating = new HashMap();

    public RewardForfeit(double coef_money, double coef_rate, double coef_rank) {
        this.coef_money = coef_money;
        this.coef_rate = coef_rate;
        this.coef_rank = coef_rank;
        if (coef_money != 0.0) {
            this.flag |= 1;
        }
        if (coef_rate != 0.0) {
            this.flag |= 4;
        }
    }

    public void sMoney(int money) {
        this.money = money;
    }

    public void sRate(double rate) {
        this.rate = rate;
    }

    public void sRank(int rank) {
        this.rank = rank;
    }

    public int gMoney() {
        return this.money;
    }

    public double gRate() {
        return this.rate;
    }

    public int gRank() {
        return this.rank;
    }

    public void setRealMoney(int money) {
        this.real_money = money;
    }

    public void setRealRate(double rate) {
        this.real_rate = rate;
    }

    public void setRealRank(int rank) {
        this.real_rank = rank;
    }

    public int getRealMoney() {
        return this.real_money;
    }

    public double getRealRate() {
        return this.real_rate;
    }

    public int getRealRank() {
        return this.real_rank;
    }

    public double gCoefMoney() {
        return this.coef_money;
    }

    public double gCoefRate() {
        return this.coef_rate;
    }

    public double gCoefRank() {
        return this.coef_rank;
    }

    public int getFlag() {
        return this.flag;
    }

    public void addFactionRating(String name, double value) {
        if (!this.m_factionRating.containsKey(name)) {
            this.m_factionRating.put(name, value);
        } else {
            Log.simpleMessage("RewardForfeit addFactionRating trying to add existing faction rating value");
        }
    }

    public HashMap<String, Double> getFactionRatings() {
        return this.m_factionRating;
    }

    public void applyFactionRatings(double coef) {
        Set<Map.Entry<String, Double>> setRatings = this.m_factionRating.entrySet();
        for (Map.Entry<String, Double> singleFactionRating : setRatings) {
            String factionName = singleFactionRating.getKey();
            double ratingDiff = singleFactionRating.getValue();
            FactionRater.addFactionRating(factionName, ratingDiff *= coef);
        }
    }
}

