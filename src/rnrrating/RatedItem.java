/*
 * Decompiled with CFR 0.151.
 */
package rnrrating;

import rnrcore.Log;

public class RatedItem {
    private String id = "Non identified";
    private double d_rating = 20.0;

    public RatedItem(String id) {
        this.id = id;
    }

    public boolean compare(String id) {
        return this.id.compareToIgnoreCase(id) == 0;
    }

    public void addRating(double rate) {
        this.d_rating += rate;
        Log.rating(this.id + "\taddRating\t" + rate + "\tTotal\t" + this.d_rating);
    }

    public void subRating(double rate) {
        this.d_rating -= rate;
        this.d_rating = this.d_rating < 20.0 ? 20.0 : this.d_rating;
        Log.rating(this.id + "\tsubRating\t" + rate + "\tTotal\t" + this.d_rating);
    }

    public void setRating(double rate) {
        this.d_rating = rate;
        this.d_rating = this.d_rating < 20.0 ? 20.0 : this.d_rating;
        Log.rating(this.id + "\tsetRating\t" + rate + "\tTotal\t" + this.d_rating);
    }

    public double getRating() {
        return this.d_rating;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

