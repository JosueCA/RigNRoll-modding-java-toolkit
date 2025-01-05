/*
 * Decompiled with CFR 0.151.
 */
package menuscript.office;

import java.util.ArrayList;
import menu.MENUsimplebutton_field;
import menu.menues;
import menuscript.IUpdateListener;
import menuscript.office.IDirtyListener;
import menuscript.office.IWarningListener;
import menuscript.office.OfficeMenu;

public abstract class ApplicationTab {
    private static final String[] BUTTONS = new String[]{"BUTTON - APPLY - ", "BUTTON - DISCARD - "};
    private static final String[] METHODS = new String[]{"onApply", "onDiscard"};
    private ArrayList<IDirtyListener> dirty_listeners = new ArrayList();
    private ArrayList<IWarningListener> warning_listeners = new ArrayList();
    private ArrayList<IUpdateListener> update_listeners = new ArrayList();
    protected boolean f_dirty = true;
    OfficeMenu parent = null;

    protected void setDirty() {
        if (this.f_dirty) {
            return;
        }
        this.f_dirty = true;
        for (IDirtyListener listener : this.dirty_listeners) {
            listener.settedDirty(this);
        }
    }

    public void addListener(IWarningListener lst) {
        this.warning_listeners.add(lst);
    }

    public void addListenerUpdate(IUpdateListener lst) {
        this.update_listeners.add(lst);
    }

    public void addListenerDirty(IDirtyListener lst) {
        this.dirty_listeners.add(lst);
    }

    protected void makeNotEnoughMoney() {
        for (IWarningListener lst : this.warning_listeners) {
            lst.makeNotEnoughMoney();
        }
    }

    protected void makeNotEnoughTurns(int num_bases) {
        for (IWarningListener lst : this.warning_listeners) {
            lst.makeNotEnoughTurnOver(num_bases);
        }
    }

    protected void makeNotEnoughCars() {
        for (IWarningListener lst : this.warning_listeners) {
            lst.makeNotEnoughCars();
        }
    }

    protected void makeUpdate() {
        for (IUpdateListener lst : this.update_listeners) {
            lst.onUpdate();
        }
    }

    public ApplicationTab(long _menu, String name, OfficeMenu parent) {
        for (int i = 0; i < BUTTONS.length; ++i) {
            long button = menues.FindFieldInMenu(_menu, BUTTONS[i] + name);
            if (0L == button) continue;
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(button), this, METHODS[i], 4L);
        }
        this.parent = parent;
    }

    public void onApply(long _menu, MENUsimplebutton_field button) {
        this.apply();
    }

    public void onDiscard(long _menu, MENUsimplebutton_field button) {
        this.discard();
    }

    public abstract void afterInit();

    public boolean update() {
        if (!this.f_dirty) {
            return false;
        }
        this.f_dirty = false;
        return true;
    }

    public abstract void deinit();

    public abstract void apply();

    public abstract void discard();
}

