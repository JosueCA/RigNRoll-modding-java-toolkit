/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.SCRuniperson;
import rnrcore.vectorJ;

public class SCRcarpassanger {
    public long nativePointer = 0L;

    public native void initPassanger(SCRuniperson var1, int var2);

    public native void AddRockingAnimation(String var1, int var2, int var3);

    public native void AddSitPose(String var1);

    public native void play();

    public native void stop();

    public native void SetShift(vectorJ var1);

    public native void SitShift(vectorJ var1);

    public native void AddWalkingOutAnimation(String var1);

    public native void AddWalkingInAnimation(String var1);

    public native void attachToCar(int var1);

    public void removePassanger() {
        this.stop();
    }
}

