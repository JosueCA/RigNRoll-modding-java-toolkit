/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.SCRuniperson;
import rnrcore.SCRwarehousecrane;
import rnrcore.vectorJ;

public class SCRwarehousoperator {
    public long nativePointer = 0L;

    public native void nWarehousoperator(String var1);

    public native void nWarehousoperator(SCRuniperson var1);

    public native void TakeCrane(SCRwarehousecrane var1);

    public native void SetPosition(vectorJ var1);

    public native void SetDirection(vectorJ var1);

    public native void play();

    public native void stop();

    public native void AddRuleAnimations(String var1);

    public native void AddFreeAnimation(String var1, String var2, int var3);

    public native void AddPultPodhodAnimation(String var1, String var2);

    public native void AddAnimationRandom(String var1, int var2);
}

