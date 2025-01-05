/*
 * Decompiled with CFR 0.151.
 */
package menu;

import menu.menues;

public class FabricControlColor {
    public static final void setControlAlfa(long control, int alpha) {
        if (alpha < 0) {
            alpha = 0;
        }
        if (alpha > 255) {
            alpha = 255;
        }
        int color = 0xFFFFFF | alpha << 24;
        menues.SetControlColorOnAllSatetesAndTextsAnimated(control, color);
    }
}

