/*
 * Decompiled with CFR 0.151.
 */
package menu;

import menu.FocusManager;
import menu.IFocusHolder;
import menu.MENUText_field;
import menu.menues;
import rnrcore.EventsHolder;
import rnrcore.IEventListener;

public abstract class IWheelEnabled
implements IFocusHolder {
    private static ControlsMouseWheel wouse_wheel = null;
    private static ControlsCtrlAPressed ctrla = null;
    private static IWheelEnabled wheel_enabled = null;
    private boolean bHide = false;

    public abstract void ControlsMouseWheel(int var1);

    public void OnMouseInside(long _menu, MENUText_field button) {
        if (!this.bHide) {
            wheel_enabled = this;
        }
    }

    public void OnMouseOutside(long _menu, MENUText_field button) {
        if (wheel_enabled == this) {
            wheel_enabled = null;
        }
    }

    public void wheelInit(long _menu, String table_name) {
        long table = menues.FindFieldInMenu(_menu, table_name);
        if (table != 0L) {
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(table), this, "OnMouseInside", 6L);
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(table), this, "OnMouseOutside", 5L);
        }
        FocusManager.register(this);
    }

    public void wheelDeinit() {
        FocusManager.unRegister(this);
        if (wheel_enabled == this) {
            wheel_enabled = null;
        }
    }

    public void wheelControlSelected() {
        FocusManager.enterFocus(this);
    }

    public void wheelHide(boolean v) {
        this.bHide = v;
    }

    static {
        wouse_wheel = new ControlsMouseWheel();
        EventsHolder.addEventListenet(76, wouse_wheel);
        ctrla = new ControlsCtrlAPressed();
        EventsHolder.addEventListenet(32, ctrla);
    }

    static class ControlsMouseWheel
    implements IEventListener {
        ControlsMouseWheel() {
        }

        public void on_event(int value) {
            if (wheel_enabled == null) {
                return;
            }
            wheel_enabled.ControlsMouseWheel(value);
        }
    }

    static class ControlsCtrlAPressed
    implements IEventListener {
        ControlsCtrlAPressed() {
        }

        public void on_event(int value) {
            IFocusHolder focused = FocusManager.getFocused();
            if (focused == null) {
                return;
            }
            focused.ControlsCtrlAPressed();
        }
    }
}

