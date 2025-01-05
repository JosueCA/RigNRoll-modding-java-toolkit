/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import java.util.Random;

public class Auxi {
    public static boolean checkProbability(int prob) {
        Random rnd2 = new Random();
        double dou = rnd2.nextDouble();
        System.err.print("checkProbability gets nextDouble " + dou + "\n");
        return (double)prob >= 100.0 * dou;
    }
}

