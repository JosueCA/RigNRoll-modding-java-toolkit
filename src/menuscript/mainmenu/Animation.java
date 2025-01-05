/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

public class Animation {
    private static boolean end_animation = false;

    static final boolean animtionEnded() {
        return end_animation;
    }

    static final int alpha_fadein(double time, double period) {
        double alfa = time / period;
        boolean bl = end_animation = alfa >= 1.0;
        if (end_animation) {
            alfa = 1.0;
        }
        return (int)Math.floor(255.0 * alfa);
    }

    static final int alpha_fadeout(double time, double period) {
        double alfa = (period - time) / period;
        boolean bl = end_animation = alfa < 0.0;
        if (end_animation) {
            alfa = 0.0;
        }
        return (int)Math.floor(256.0 * alfa);
    }
}

