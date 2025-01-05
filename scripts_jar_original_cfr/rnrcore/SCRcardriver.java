/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.SCRuniperson;
import rnrcore.vectorJ;

public class SCRcardriver {
    public long nativePointer = 0L;

    public native void nCarDriver(SCRuniperson var1);

    public native void AddSteeringAnimation(String var1, int var2, int var3);

    public native void AddSitPose(String var1);

    public native void play();

    public native void stop();

    public native void SetShift(vectorJ var1);

    public native void AddGearingAnimation(String var1, int var2);

    public native void AddPedalingAnimation(String var1);

    public native void AddMirrorAnimation(String var1);

    public native void SitShift(vectorJ var1);

    public native void AddWalkingOutAnimation(String var1);

    public native void AddWalkingInAnimation(String var1);

    public native void AddGasStationAnimation(String var1);

    public native void attachToCar(int var1);

    public static SCRcardriver CreateCarDriver(SCRuniperson Pers) {
        SCRcardriver drv = new SCRcardriver();
        drv.nCarDriver(Pers);
        return drv;
    }

    public void removeDriver() {
        this.stop();
    }
}

