/*
 * Decompiled with CFR 0.151.
 */
package players;

import players.actorveh;
import rnrcore.matrixJ;
import rnrcore.vectorJ;

public class vehicle {
    int p_vehicle = 0;

    public static native vehicle create(String var0, int var1);

    public native void delete();

    public native void placeInGarage();

    public native void placeToWorldFromGarage();

    public native void setLeased(boolean var1);

    public static native void changeLiveVehicle(actorveh var0, vehicle var1, matrixJ var2, vectorJ var3);

    public static native void cacheVehicleWithName(vehicle var0, String var1);

    public static native vehicle getCacheVehicleByName(String var0);
}

