/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscr.SOPresets;

public class cSpecObjects {
    private SOPresets presets = null;
    public int sotip;
    public String name = "";
    public vectorJ position = new vectorJ();
    public matrixJ matrix = new matrixJ();

    public SOPresets Presets() {
        return this.presets;
    }

    public void SetPresets(SOPresets pr) {
        this.presets = pr;
    }
}

