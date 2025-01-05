/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.vectorJ;

public final class traffic {
    private static final int CUT_SCENE_RADIUS = 1000;
    public static final int REGULAR_MODE = 0;
    public static final int CHASE_MODE = 1;
    public static final int CUT_SCENE_MODE = 1;

    private traffic() {
    }

    public static native void restoreTrafficImmediately();

    public static native void restoreTrafficImmediatelyTemporary();

    public static native void cleanTrafficSmooth();

    public static native void cleanTrafficImmediately();

    public static native void cleanTrafficSmooth(double var0, vectorJ var2);

    public static native void cleanTrafficImmediatelyTemporary(double var0, vectorJ var2);

    public static native void setTrafficMode(int var0);

    public static native void setTrafficModeTemporary(int var0);

    public static void enterCutSceneMode(vectorJ position) {
        traffic.setTrafficMode(1);
        traffic.cleanTrafficSmooth(1000.0, position);
    }

    public static void enterCutSceneModeImmediately() {
        traffic.setTrafficMode(1);
        traffic.cleanTrafficImmediately();
    }

    public static void enterChaseModeSmooth() {
        traffic.setTrafficMode(1);
        traffic.cleanTrafficSmooth();
    }

    public static void enterChaseModeImmediately() {
        traffic.setTrafficMode(1);
        traffic.cleanTrafficImmediately();
    }
}

