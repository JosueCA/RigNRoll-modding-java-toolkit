/*
 * Decompiled with CFR 0.151.
 */
package menu;

import menu.IRadioAccess;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.RadioGroup;
import menu.SmartArrowedGroup;
import menu.menues;
import menuscript.parametrs.IBooleanValueChanger;
import rnrcore.Log;

public class RadioGroupSmartSwitch
extends SmartArrowedGroup
implements IBooleanValueChanger,
IRadioAccess {
    private static final String[] SWITCHBUTTONS = new String[]{"OFF", "ON"};
    private MENUbutton_field[] indicators = new MENUbutton_field[2];
    private int indicators_size = 0;
    private RadioGroup radioButtons = null;
    private boolean value = false;

    public RadioGroupSmartSwitch(String xml_file, String xml_node_group, String title, boolean value, boolean bBlind) {
        super(xml_file, xml_node_group, title, bBlind);
        this.value = value;
    }

    public void load(long _menu) {
        super.load(_menu);
        for (int nm = 0; nm < SWITCHBUTTONS.length; ++nm) {
            for (int i = 0; i < this.controls.length; ++i) {
                String name = menues.GetFieldName(this.controls[i]);
                if (name.compareTo(SWITCHBUTTONS[nm]) != 0) continue;
                MENUbutton_field button = menues.ConvertButton(this.controls[i]);
                button.userid = nm;
                menues.UpdateField(button);
                this.addIndicator(button);
            }
        }
        this.initRadioGroup(_menu);
    }

    public void init() {
        super.init();
        this.setupValue();
    }

    private void addIndicator(MENUbutton_field field) {
        if (this.indicators_size >= 2) {
            Log.menu("Switch group. AddIndicator detected more then 2 elements.");
            return;
        }
        this.indicators[this.indicators_size++] = field;
    }

    private void initRadioGroup(long _menu) {
        if (this.indicators_size != 2) {
            Log.menu("Switch Group. Cannot initialize radio group. indicators_size is " + this.indicators_size);
            return;
        }
        this.radioButtons = new RadioGroup(_menu, this.indicators);
        this.radioButtons.addListener(this);
        this.radioButtons.addAccessListener(this);
    }

    private void setupValue() {
        this.radioButtons.select(this.value ? 1 : 0);
    }

    private void debug_text() {
    }

    public void OnLeft(long _menu, MENUsimplebutton_field button) {
        this.radioButtons.select(0);
        this.value = false;
        this.debug_text();
    }

    public void OnRight(long _menu, MENUsimplebutton_field button) {
        this.radioButtons.select(1);
        this.value = true;
        this.debug_text();
    }

    public void controlSelected(MENUbutton_field button, int cs) {
        this.value = button.userid == 1;
        this.debug_text();
    }

    public void setValue(boolean value) {
        this.value = value;
        this.setupValue();
    }

    public boolean getValue() {
        return this.value;
    }

    public boolean changeValue() {
        return this.getValue();
    }

    public void reciveValue(boolean value) {
        this.setValue(value);
    }

    public boolean controlAccessed(MENUbutton_field button, int state) {
        if (state == 0) {
            if (!this.value && this.radioButtons.getSelected() == 0) {
                return false;
            }
            if (this.value && this.radioButtons.getSelected() == 1) {
                return false;
            }
        }
        return true;
    }
}

