/*
 * Decompiled with CFR 0.151.
 */
package rnrorg;

public class MissionTime {
    private double time_coef = 1.0;

    public MissionTime(double coef) {
        this.time_coef = coef;
    }

    public MissionTime() {
    }

    public void makeUrgent() {
        this.time_coef = 0.7;
    }

    public void setCoef(double time) {
        this.time_coef = time;
    }

    public double getCoef() {
        return this.time_coef;
    }
}

