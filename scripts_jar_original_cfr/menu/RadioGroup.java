/*
 * Decompiled with CFR 0.151.
 */
package menu;

import java.util.ArrayList;
import java.util.Iterator;
import menu.IActivePressedTracker;
import menu.IRadioAccess;
import menu.IRadioChangeListener;
import menu.MENUbutton_field;
import menu.menues;
import rnrcore.eng;

public class RadioGroup {
    private static final String[] METHODS_ACTIVEPRESS_TRACK = new String[]{"onPressEvent", "onReleaseEvent", "onReleaseEvent", "onActive", "onPassive"};
    private static final int[] EVENTS_ACTIVEPRESS = new int[]{8, 4, 22, 3, 5};
    protected ArrayList<IRadioChangeListener> listeners = new ArrayList();
    protected ArrayList<IRadioAccess> access_listeners = new ArrayList();
    private ArrayList<IActivePressedTracker> activepressed_listeners = new ArrayList();
    MENUbutton_field[] buttons = null;
    protected boolean undersession = false;
    protected boolean silent_selecting = false;
    protected int focus_member = 0;
    boolean f_deselecting = false;
    private static final int SINGLE_SELECTION = 1;
    private static final int MULTY_SELECTION = 2;
    private int mode = 1;

    public RadioGroup(long _menu, MENUbutton_field[] buttons) {
        this.buttons = buttons;
        for (int i = 0; i < buttons.length; ++i) {
            menues.SetScriptOnControl(_menu, buttons[i], this, "onSelect", 10L);
            menues.SetScriptOnControl(_menu, buttons[i], this, "onDeselectSelect", 11L);
        }
    }

    public RadioGroup(long _menu, MENUbutton_field[] buttons, boolean track_active_pressed) {
        this.buttons = buttons;
        for (int i = 0; i < buttons.length; ++i) {
            menues.SetScriptOnControl(_menu, buttons[i], this, "onSelect", 10L);
            menues.SetScriptOnControl(_menu, buttons[i], this, "onDeselectSelect", 11L);
            if (!track_active_pressed) continue;
            for (int j = 0; j < METHODS_ACTIVEPRESS_TRACK.length; ++j) {
                menues.SetScriptOnControl(_menu, buttons[i], this, METHODS_ACTIVEPRESS_TRACK[j], EVENTS_ACTIVEPRESS[j]);
            }
        }
    }

    protected void beginsession() {
        this.undersession = true;
    }

    protected void endsession() {
        this.undersession = false;
    }

    public int getSelected() {
        return this.focus_member;
    }

    public int[] getMultySelected() {
        int[] all = this.getSelectedLines();
        int count = 0;
        for (int i : all) {
            count += i;
        }
        int[] res = new int[count];
        count = 0;
        int count_all = 0;
        for (int i : all) {
            if (i != 0) {
                res[count++] = count_all;
            }
            ++count_all;
        }
        return res;
    }

    public void deselect(int nom) {
        if (nom >= 0 && nom <= this.buttons.length - 1) {
            menues.SetFieldState(this.buttons[nom].nativePointer, 0);
        } else {
            eng.err("bad select action on RadioGroup. nom is " + nom);
        }
    }

    public void silentDeselect(int nom) {
        this.silent_selecting = true;
        if (nom >= 0 && nom <= this.buttons.length - 1) {
            menues.SetFieldState(this.buttons[nom].nativePointer, 0);
        } else {
            eng.err("bad select action on RadioGroup. nom is " + nom);
        }
        this.silent_selecting = false;
    }

    public void select(int nom) {
        if (nom >= 0 && nom <= this.buttons.length - 1) {
            menues.SetFieldState(this.buttons[nom].nativePointer, 1);
        } else {
            eng.err("bad select action on RadioGroup. nom is " + nom);
        }
    }

    public void makeactive(int nom, boolean value) {
        if (nom >= 0 && nom <= this.buttons.length - 1) {
            menues.setActiveControl(this.buttons[nom].nativePointer, value);
        } else {
            eng.err("bad makeactive action on RadioGroup. nom is " + nom);
        }
    }

    public void makepressed(int nom, boolean value) {
        if (nom >= 0 && nom <= this.buttons.length - 1) {
            menues.setPressedControl(this.buttons[nom].nativePointer, value);
        } else {
            eng.err("bad makeactive action on RadioGroup. nom is " + nom);
        }
    }

    public void silentSelect(int nom) {
        this.silent_selecting = true;
        if (nom >= 0 && nom <= this.buttons.length - 1) {
            menues.SetFieldState(this.buttons[nom].nativePointer, 1);
        } else {
            eng.err("bad select action on RadioGroup. nom is " + nom);
        }
        this.silent_selecting = false;
    }

    public void deselectall() {
        this.f_deselecting = true;
        for (int i = 0; i < this.buttons.length; ++i) {
            menues.SetFieldState(this.buttons[i].nativePointer, 0);
        }
        this.focus_member = 0;
        this.f_deselecting = false;
    }

    public void addListener(IRadioChangeListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(IRadioChangeListener listener) {
        this.listeners.remove(listener);
    }

    public void addAccessListener(IRadioAccess listener) {
        this.access_listeners.add(listener);
    }

    public void addActiveListener(IActivePressedTracker listener) {
        this.activepressed_listeners.add(listener);
    }

    public void removeAccessListener(IRadioAccess listener) {
        this.access_listeners.remove(listener);
    }

    public void onSelect(long _menu, MENUbutton_field button) {
        int i;
        if (this.undersession || this.f_deselecting || this.silent_selecting && this.mode == 2) {
            return;
        }
        int[] states = new int[this.buttons.length];
        boolean can_be_selected = true;
        Iterator<IRadioAccess> iter = this.access_listeners.iterator();
        while (iter.hasNext()) {
            if (iter.next().controlAccessed(button, 1)) continue;
            can_be_selected = false;
            break;
        }
        int selected = this.getButtonNom(button);
        if (can_be_selected) {
            this.focus_member = selected;
        }
        switch (this.mode) {
            case 1: {
                if (!can_be_selected) break;
                for (i = 0; i < states.length; ++i) {
                    states[i] = i == selected ? 1 : 0;
                }
                break;
            }
        }
        this.beginsession();
        switch (this.mode) {
            case 1: {
                if (!can_be_selected) {
                    menues.SetFieldState(button.nativePointer, 0);
                    break;
                }
                for (i = 0; i < this.buttons.length; ++i) {
                    menues.SetFieldState(this.buttons[i].nativePointer, states[i]);
                }
                break;
            }
            case 2: {
                menues.SetFieldState(button.nativePointer, can_be_selected ? 1 : 0);
            }
        }
        this.endsession();
        if (can_be_selected && !this.silent_selecting) {
            Iterator<IRadioChangeListener> iter2 = this.listeners.iterator();
            while (iter2.hasNext()) {
                iter2.next().controlSelected(button, 1);
            }
        }
    }

    public void onDeselectSelect(long _menu, MENUbutton_field button) {
        if (this.undersession || this.f_deselecting) {
            return;
        }
        boolean can_be_deselected = true;
        Iterator<Object> iter = this.access_listeners.iterator();
        while (iter.hasNext()) {
            if (iter.next().controlAccessed(button, 0)) continue;
            can_be_deselected = false;
            break;
        }
        this.beginsession();
        switch (this.mode) {
            case 1: {
                if (can_be_deselected) break;
                for (int i = 0; i < this.buttons.length; ++i) {
                    menues.SetFieldState(this.buttons[i].nativePointer, this.buttons[i].nativePointer == button.nativePointer ? 1 : 0);
                }
                break;
            }
            case 2: {
                if (can_be_deselected) break;
                menues.SetFieldState(button.nativePointer, 1);
            }
        }
        this.endsession();
        switch (this.mode) {
            case 1: {
                if (this.silent_selecting) break;
                iter = this.listeners.iterator();
                while (iter.hasNext()) {
                    ((IRadioChangeListener)iter.next()).controlSelected(button, 1);
                }
                break;
            }
            case 2: {
                if (!can_be_deselected || this.silent_selecting) break;
                iter = this.listeners.iterator();
                while (iter.hasNext()) {
                    ((IRadioChangeListener)iter.next()).controlSelected(button, 0);
                }
                break;
            }
        }
    }

    public void setSingleSelectionMode() {
        this.mode = 1;
    }

    public void setMultySelectionMode() {
        this.mode = 2;
    }

    public boolean isSingleSelectionMode() {
        return this.mode == 1;
    }

    public void onPressEvent(long _menu, MENUbutton_field button) {
        for (IActivePressedTracker lst : this.activepressed_listeners) {
            lst.onPress(button.userid);
        }
    }

    public void onReleaseEvent(long _menu, MENUbutton_field button) {
        for (IActivePressedTracker lst : this.activepressed_listeners) {
            lst.onRelease(button.userid);
        }
    }

    public void onActive(long _menu, MENUbutton_field button) {
        for (IActivePressedTracker lst : this.activepressed_listeners) {
            lst.onActive(button.userid);
        }
    }

    public void onPassive(long _menu, MENUbutton_field button) {
        for (IActivePressedTracker lst : this.activepressed_listeners) {
            lst.onPassive(button.userid);
        }
    }

    private int getButtonNom(MENUbutton_field button) {
        for (int i = 0; i < this.buttons.length; ++i) {
            if (this.buttons[i].nativePointer != button.nativePointer) continue;
            return i;
        }
        return -1;
    }

    private int[] getSelectedLines() {
        int[] res = new int[this.buttons.length];
        for (int i = 0; i < res.length; ++i) {
            res[i] = menues.GetFieldState(this.buttons[i].nativePointer);
        }
        return res;
    }
}

