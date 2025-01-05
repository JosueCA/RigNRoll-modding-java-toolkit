/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import rnrcore.vectorJ;

public class parkingplace {
    long nativePointer = 0L;

    parkingplace() {
    }

    public static native parkingplace findNearestParking(vectorJ var0);

    public static native parkingplace findParkingByName(String var0, vectorJ var1);
}

