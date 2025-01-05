/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import rnrscr.AdvancedRandom;

public class BarTimeZone {
    private int manCount = 0;
    private int womanCount = 0;

    public void ProcessZone(int hour, double Scale, boolean isEgg) {
        int peopleCount = 5;
        if (hour >= 0 && hour < 8) {
            peopleCount = (int)(Scale * (double)AdvancedRandom.RandFromInreval(1, 2));
        }
        if (hour >= 8 && hour < 10) {
            peopleCount = (int)(Scale * (double)AdvancedRandom.RandFromInreval(10, 15));
        }
        if (hour >= 10 && hour < 13 || hour >= 14 && hour < 18) {
            peopleCount = (int)(Scale * (double)AdvancedRandom.RandFromInreval(5, 8));
        }
        if (hour >= 13 && hour < 14) {
            peopleCount = (int)(Scale * (double)AdvancedRandom.RandFromInreval(12, 17));
        }
        if (hour >= 18 && hour <= 23) {
            peopleCount = (int)(Scale * (double)AdvancedRandom.RandFromInreval(15, 19));
        }
        this.manCount = peopleCount == 1 ? (AdvancedRandom.RandFromInreval(1, 9) > 7 ? 0 : 1) : (peopleCount == 2 ? (AdvancedRandom.RandFromInreval(1, 9) > 7 ? 1 : 2) : AdvancedRandom.RandFromInreval(2 * peopleCount / 3, peopleCount));
        this.womanCount = peopleCount - this.manCount;
    }

    public int GetManCount() {
        return this.manCount;
    }

    public int GetWomanCount() {
        return this.womanCount;
    }
}

