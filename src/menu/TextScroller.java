/*
 * Decompiled with CFR 0.151.
 */
package menu;

import java.util.Vector;
import menu.Common;
import menu.IWheelEnabled;
import menu.MENUText_field;
import menu.MENU_ranger;
import menu.menues;

public class TextScroller
extends IWheelEnabled {
    MENU_ranger m_ranger;
    int m_numlines;
    int m_linewidth;
    int m_lineonscreen;
    int m_startbase;
    Vector m_TextFields = new Vector();

    public TextScroller(Common common, MENU_ranger ranger, int numlines, int lineonscreen, int linewidth, int startbaseline, boolean bHideRangerIfTextSmall, String scroller_group) {
        this.m_ranger = ranger;
        this.m_linewidth = linewidth;
        this.m_lineonscreen = lineonscreen;
        this.m_startbase = startbaseline;
        this.m_ranger.min_value = 0;
        this.m_ranger.current_value = 0;
        this.wheelInit(common.GetMenu(), scroller_group);
        if (numlines < lineonscreen) {
            this.m_ranger.max_value = 0;
            if (bHideRangerIfTextSmall) {
                menues.SetShowField(ranger.nativePointer, false);
                this.wheelHide(true);
            }
        } else {
            this.m_ranger.max_value = numlines - lineonscreen;
            if (bHideRangerIfTextSmall) {
                menues.SetShowField(ranger.nativePointer, true);
                this.wheelHide(false);
            }
        }
        this.m_ranger.page = lineonscreen;
        menues.UpdateField(this.m_ranger);
        menues.SetScriptOnControl(common.GetMenu(), this.m_ranger, this, "OnRanger", 1L);
    }

    public TextScroller(long _menu, MENU_ranger ranger, int numlines, int lineonscreen, int linewidth, int startbaseline, long group2hide, boolean bHideRangerIfTextSmall, String scroller_group) {
        this.m_ranger = ranger;
        this.m_linewidth = linewidth;
        this.m_lineonscreen = lineonscreen;
        this.m_startbase = startbaseline;
        this.wheelInit(_menu, scroller_group);
        this.m_ranger.min_value = 0;
        this.m_ranger.current_value = 0;
        if (numlines <= lineonscreen) {
            this.m_ranger.max_value = 0;
            if (bHideRangerIfTextSmall) {
                if (group2hide != 0L) {
                    menues.SetShowField(group2hide, false);
                }
                this.wheelHide(true);
            }
        } else {
            this.m_ranger.max_value = numlines - lineonscreen;
            if (bHideRangerIfTextSmall) {
                if (group2hide != 0L) {
                    menues.SetShowField(group2hide, true);
                }
                this.wheelHide(false);
            }
        }
        this.m_ranger.page = lineonscreen;
        menues.UpdateField(this.m_ranger);
        menues.SetScriptOnControl(_menu, this.m_ranger, this, "OnRanger", 1L);
    }

    public void Deinit() {
        this.wheelDeinit();
    }

    public void cbEnterFocus() {
    }

    public void cbLeaveFocus() {
    }

    public void ControlsCtrlAPressed() {
    }

    public void ControlsMouseWheel(int value) {
        if (this.m_ranger == null) {
            return;
        }
        this.m_ranger.current_value -= value;
        if (this.m_ranger.current_value > this.m_ranger.max_value) {
            this.m_ranger.current_value = this.m_ranger.max_value;
        }
        if (this.m_ranger.current_value < this.m_ranger.min_value) {
            this.m_ranger.current_value = this.m_ranger.min_value;
        }
        menues.UpdateField(this.m_ranger);
    }

    public void AddTextControl(MENUText_field textfield) {
        this.m_TextFields.add(textfield);
    }

    void OnRanger(long _menu, MENU_ranger ranger) {
        for (int i = 0; i < this.m_TextFields.size(); ++i) {
            MENUText_field t = (MENUText_field)this.m_TextFields.get(i);
            menues.SetBaseLine(t.nativePointer, this.m_startbase - ranger.current_value * this.m_linewidth);
        }
    }
}

