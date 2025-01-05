/*
 * Decompiled with CFR 0.151.
 */
package menu;

import menu.MENUsimplebutton_field;
import menu.SmartArrowedGroup;
import menu.menues;
import menuscript.parametrs.IIntegerValueChanger;
import rnrcore.Log;

public class RadioSmartSwitch
extends SmartArrowedGroup
implements IIntegerValueChanger {
    private static final String VALUE = "VALUE";
    private long button_value = 0L;
    private int state = 0;
    int max_states = 0;

    public void load(long _menu) {
        super.load(_menu);
        for (int i = 0; i < this.controls.length; ++i) {
            String name = menues.GetFieldName(this.controls[i]);
            if (name.compareTo(VALUE) != 0) continue;
            this.button_value = this.controls[i];
        }
        if (0L == this.button_value) {
            Log.menu("RadioSmartSwitch couldnt find radio button named VALUE");
        }
        this.updateValue();
    }

    public void init() {
        super.init();
    }

    public void show() {
        super.show();
        if (0L != this.button_value) {
            menues.SetBlindess(this.button_value, true);
            menues.SetIgnoreEvents(this.button_value, true);
        }
    }

    public void OnLeft(long _menu, MENUsimplebutton_field button) {
        if (this.state <= 0) {
            this.state = 0;
            return;
        }
        --this.state;
        this.updateValue();
    }

    public void OnRight(long _menu, MENUsimplebutton_field button) {
        if (this.state >= this.max_states) {
            this.state = this.max_states;
            return;
        }
        ++this.state;
        this.updateValue();
    }

    private void updateValue() {
        this.callValueChanged();
        if (0L == this.button_value) {
            return;
        }
        menues.SetFieldState(this.button_value, this.state);
    }

    public int changeValue() {
        return this.state;
    }

    public void reciveValue(int value) {
        if (value == this.state) {
            return;
        }
        this.state = value > this.max_states ? this.max_states : (value < 0 ? 0 : value);
        this.updateValue();
    }

    public RadioSmartSwitch(String xml_file, String xml_node_group, String title, int curstate, int max_states, boolean bBlind) {
        super(xml_file, xml_node_group, title, bBlind);
        this.state = curstate;
        this.max_states = max_states;
    }
}

