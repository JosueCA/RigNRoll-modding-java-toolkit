/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import menu.menues;
import menuscript.menuBridgeToll;
import rnrcore.TypicalAnm;
import rnrcore.eng;
import rnrcore.vectorJ;
import rnrscr.Helper;
import rnrscr.ILeaveMenuListener;

public class BridgeTall
extends TypicalAnm {
    public static final int ENTER = 0;
    public static final int EXIT = 1;
    public static final int DEFAULT = -1;
    private static int last_event = -1;
    public static final double DISTANCE_EXIT_TALL_RULE = 160000.0;
    private static BridgeTall animation = null;
    private static boolean m_menu_is_on = false;
    private boolean to_stop = false;
    private vectorJ pos_enter = Helper.getCurrentPosition();

    public BridgeTall() {
        eng.CreateInfinitScriptAnimation(this);
    }

    public static void enterBridgeTall(int event2) {
        if (m_menu_is_on) {
            return;
        }
        if (0 == last_event && 1 == event2) {
            eng.log("Make tall.");
            last_event = -1;
            if (null != animation) {
                animation.stop_anim();
                animation = null;
            }
            if (menues.cancreate_messagewindow()) {
                m_menu_is_on = true;
                menuBridgeToll.CreateBridgeTollMenu(new ILeaveMenuListener(){

                    public void menuLeaved() {
                        m_menu_is_on = false;
                    }
                });
            }
        } else if (-1 == last_event && 0 == event2) {
            last_event = 0;
            if (null != animation) {
                animation.stop_anim();
            }
            animation = new BridgeTall();
        }
    }

    private void stop_anim() {
        this.to_stop = true;
    }

    public boolean animaterun(double dt) {
        if (this.to_stop) {
            animation = null;
            return true;
        }
        vectorJ pos = Helper.getCurrentPosition();
        if (160000.0 < pos.len2(this.pos_enter)) {
            this.stop_anim();
            animation = null;
            last_event = -1;
        }
        return false;
    }
}

