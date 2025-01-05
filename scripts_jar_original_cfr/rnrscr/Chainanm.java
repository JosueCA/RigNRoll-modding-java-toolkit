/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import rnrcore.SCRanimparts;
import rnrcore.SCRperson;
import rnrcore.vectorJ;

public class Chainanm {
    long nativePointer = 0L;

    public native void nchain(SCRperson var1);

    public native void Add(int var1, vectorJ var2, int var3, int var4);

    public native void Add(int var1, int var2, int var3, double var4);

    public native void Add(int var1, vectorJ var2, int var3, SCRanimparts var4);

    public native void Add(int var1, int var2, SCRanimparts var3, double var4);

    public native void AddAsk(int var1, double var2);

    public void Add(int _task, int _groupmovwment, int _typemovwment) {
        this.Add(_task, _groupmovwment, _typemovwment, 0.0);
    }

    public void Add(int _task, int _groupmovwment, SCRanimparts inanim) {
        this.Add(_task, _groupmovwment, inanim, 0.0);
    }

    Chainanm() {
    }
}

