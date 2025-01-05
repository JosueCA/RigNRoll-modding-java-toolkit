/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.Random;

public class AdvancedRandom {
    private static Random rnd = null;

    public static final int RandFromInreval(int Min, int Max) {
        if (null == rnd) {
            rnd = new Random();
        }
        return Min + (int)Math.round(rnd.nextDouble() * (double)(Max - Min));
    }

    public static final double getRandomDouble() {
        if (null == rnd) {
            rnd = new Random();
        }
        return rnd.nextDouble();
    }

    public static final boolean probability(double value) {
        if (null == rnd) {
            rnd = new Random();
        }
        return rnd.nextDouble() < value;
    }
}

