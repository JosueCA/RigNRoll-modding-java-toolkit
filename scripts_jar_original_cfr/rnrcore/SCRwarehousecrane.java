/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.vectorJ;

public class SCRwarehousecrane {
    public long nativePointer = 0L;

    public native void nWarehousecrane(String var1, String var2);

    public native void FindCraneParts(String var1);

    public native void SetPosition(vectorJ var1);

    public native void SetDirection(vectorJ var1);

    public native void MoveMain(double var1, double var3, String var5);

    public native void CLAWenclose(double var1, double var3, String var5);

    public native void CLAWsqueez(double var1, double var3, String var5);

    public native void AnimateArm(double var1, double var3, String var5);

    public static SCRwarehousecrane CreateCrane(String strr, String strri) {
        SCRwarehousecrane whCranei = new SCRwarehousecrane();
        whCranei.nWarehousecrane(strr, strri);
        return whCranei;
    }
}

