/*
 * Decompiled with CFR 0.151.
 */
package menuscript.parametrs;

import menuscript.parametrs.IIntegerValueChanger;
import menuscript.parametrs.IParametr;

public class IntegerParametr
implements IParametr {
    private IIntegerValueChanger changer = null;
    private int confirmed_value;
    private int value;
    private int default_value;
    private boolean f_default_setted = false;

    IntegerParametr(int value, int default_value, IIntegerValueChanger ch) {
        this.value = value;
        this.confirmed_value = value;
        this.default_value = default_value;
        this.changer = ch;
    }

    public boolean getBoolean() {
        return false;
    }

    public int getInteger() {
        return this.confirmed_value;
    }

    public boolean isBoolean() {
        return false;
    }

    public boolean isInteger() {
        return true;
    }

    public void setBoolean(boolean value) {
    }

    public void setInteger(int value) {
        this.confirmed_value = value;
    }

    public void setBooleanDefault(boolean value) {
    }

    public void setIntegerDefault(int value) {
        this.default_value = value;
        this.f_default_setted = true;
    }

    public void updateSilently() {
        this.value = this.confirmed_value;
    }

    public void update() {
        this.value = this.confirmed_value;
        this.changer.reciveValue(this.value);
    }

    public void updateDefault() {
        this.update();
        if (!this.f_default_setted) {
            this.default_value = this.value;
        }
        this.f_default_setted = true;
    }

    public void makeDefault() {
        this.value = this.default_value;
        this.changer.reciveValue(this.value);
    }

    public void readFromChanger(boolean to_confirm) {
        this.value = this.changer.changeValue();
        if (to_confirm) {
            this.confirmed_value = this.value;
        }
    }

    public boolean changed() {
        return this.confirmed_value != this.value || this.changer.changeValue() != this.value;
    }

    public void setBooleanChange(boolean value) {
    }

    public boolean getBooleanChange() {
        return false;
    }

    public void setIntegerChange(int value) {
        this.value = value;
        if (null != this.changer) {
            this.changer.reciveValue(value);
        }
    }

    public int getIntegerChange() {
        return this.value;
    }
}

