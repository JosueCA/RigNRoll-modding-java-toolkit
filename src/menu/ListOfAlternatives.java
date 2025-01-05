/*
 * Decompiled with CFR 0.151.
 */
package menu;

import menu.MENUsimplebutton_field;
import menu.SmartArrowedGroup;
import menu.menues;
import menuscript.parametrs.IIntegerValueChanger;
import rnrcore.Log;

public class ListOfAlternatives
extends SmartArrowedGroup
implements IIntegerValueChanger {
    private static final String VALUE = "VALUE";
    private String[] alteranlives = null;
    private long valu_field = 0L;
    private int choose = 0;
    private boolean f_fieldfound = false;

    public ListOfAlternatives(String xml_file, String xml_node_group, String title, String[] alteranlives, boolean bBlind) {
        super(xml_file, xml_node_group, title, bBlind);
        this.alteranlives = alteranlives;
        if (null == alteranlives || alteranlives.length == 0) {
            Log.menu("List alternatives. Alternatives are empty");
        }
    }

    public void load(long _menu) {
        super.load(_menu);
        for (int i = 0; i < this.controls.length; ++i) {
            if (this.readValueField(this.controls[i])) continue;
        }
    }

    public void init() {
        super.init();
        this.updateValue();
    }

    private boolean readValueField(long field) {
        String name = menues.GetFieldName(field);
        if (name.compareTo(VALUE) == 0) {
            this.valu_field = field;
            this.f_fieldfound = true;
            return true;
        }
        return false;
    }

    private void updateValue() {
        if (!this.f_fieldfound) {
            Log.menu("List alternatives. Value field was not found and trying ti updates.");
            return;
        }
        if (this.choose >= this.alteranlives.length) {
            Log.menu("List alternatives. wrong behaivoir on updateValue. choose>=alteranlives.length. Values:\tchoose:\t" + this.choose + "\talteranlives.length:\t" + this.alteranlives.length);
            return;
        }
        menues.SetFieldText(this.valu_field, this.alteranlives[this.choose]);
        menues.UpdateMenuField(menues.ConvertMenuFields(this.valu_field));
    }

    public void OnLeft(long _menu, MENUsimplebutton_field button) {
        boolean value_changed = false;
        if (this.choose != 0) {
            --this.choose;
            value_changed = true;
        }
        this.updateValue();
        if (value_changed) {
            this.callValueChanged();
        }
    }

    public void OnRight(long _menu, MENUsimplebutton_field button) {
        int prev_value = this.choose++;
        if (this.choose >= this.alteranlives.length) {
            this.choose = this.alteranlives.length - 1;
        }
        this.updateValue();
        if (prev_value != this.choose) {
            this.callValueChanged();
        }
    }

    public void setValue(int value) {
        int prev_value = this.choose;
        this.choose = value;
        this.updateValue();
        if (prev_value != this.choose) {
            this.callValueChanged();
        }
    }

    public int getValue() {
        return this.choose;
    }

    public String getStringValue() {
        return this.alteranlives[this.choose];
    }

    public int changeValue() {
        return this.getValue();
    }

    public void reciveValue(int value) {
        this.setValue(value);
    }
}

