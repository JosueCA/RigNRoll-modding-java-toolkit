/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.SCRperson;
import rnrcore.vectorJ;

public class SCRanimparts {
    public long nativePointer = 0L;
    public vectorJ shift;

    public vectorJ PermanentShift() {
        return this.shift;
    }

    public void PermanentShift(double x, double y, double z) {
        this.shift = new vectorJ(x, y, z);
    }

    public native void TuneLoop(boolean var1);

    public native void TuneAsinch(boolean var1);

    public native void Tune(double var1, boolean var3, boolean var4);

    public native void Tune(double var1, boolean var3);

    public void Tune(double scale) {
        this.Tune(scale, false);
    }

    public native void setTimeNScalePreventRandom(double var1, double var3);

    public native void SetShift(vectorJ var1);

    public native void SetDir(vectorJ var1);

    public native void SetVelocity(double var1);

    public native void nAnimParts(SCRperson var1, String var2, String var3, String var4, String var5, String var6, String var7);

    public native void AnimPartsIgnorNull(SCRperson var1, String var2, String var3, String var4, String var5, String var6, String var7);

    public native void SetUpAsinchron(double var1, double var3);

    public native void MoveHip();
}

