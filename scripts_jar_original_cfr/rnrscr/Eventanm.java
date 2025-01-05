/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import rnrcore.vectorJ;

public class Eventanm {
    long nativePointer = 0L;
    int ID;

    native int GetID();

    public native void nevent();

    public native void CreateEventScr(int var1, vectorJ var2, vectorJ var3, int var4);

    public native void CreateOnEndEvent(int var1, int var2);

    Eventanm() {
    }
}

