/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

public class StaticScenarioStuff {
    private static boolean f_readyPrepareSc00060 = false;
    private static boolean f_readyCursedHiWay = false;

    public static boolean isReadyCursedHiWay() {
        return f_readyCursedHiWay;
    }

    public static void makeReadyCursedHiWay(boolean value) {
        f_readyCursedHiWay = value;
    }

    public static boolean isReadyPreparesc00060() {
        return f_readyPrepareSc00060;
    }

    public static void makeReadyPreparesc00060(boolean value) {
        f_readyPrepareSc00060 = value;
    }
}

