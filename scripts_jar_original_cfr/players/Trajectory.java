/*
 * Decompiled with CFR 0.151.
 */
package players;

import rnrcore.vectorJ;

public final class Trajectory {
    private Trajectory() {
    }

    public static native void createTrajectory(String var0, String var1, String var2);

    public static native void removeTrajectory(String var0);

    public static native vectorJ gStart(String var0);

    public static native vectorJ gFinish(String var0);
}

