/*
 * Decompiled with CFR 0.151.
 */
package rnrcore.teleport;

import rnrcore.TypicalAnm;
import rnrcore.eng;
import rnrcore.teleport.ITeleported;

public class MakeTeleport
extends TypicalAnm {
    private static final int NON_KARDS_PASS = 2;
    private ITeleported callback;
    private int count_anim = 0;

    MakeTeleport(ITeleported cb) {
        this.callback = cb;
        eng.CreateInfinitScriptAnimation(this);
    }

    public boolean animaterun(double dt) {
        if (2 > this.count_anim++) {
            return false;
        }
        if (null != this.callback) {
            this.callback.teleported();
        }
        return true;
    }

    public static void teleport(ITeleported cb) {
        new MakeTeleport(cb);
    }
}

