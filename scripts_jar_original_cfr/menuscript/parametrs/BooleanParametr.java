/*
 * Decompiled with CFR 0.151.
 */
package menuscript.parametrs;

import menuscript.parametrs.IBooleanValueChanger;
import menuscript.parametrs.IParametr;

public class BooleanParametr
implements IParametr {
    private IBooleanValueChanger changer = null;
    private boolean confirmed_value;
    private boolean value;
    private boolean default_value;
    private boolean f_default_setted = false;

    BooleanParametr(boolean value, boolean default_value, IBooleanValueChanger ch) {
        this.value = value;
        this.default_value = default_value;
        this.confirmed_value = value;
        this.changer = ch;
    }

    public boolean getBoolean() {
        return this.confirmed_value;
    }

    public int getInteger() {
        return -1;
    }

    public boolean isBoolean() {
        return true;
    }

    public boolean isInteger() {
        return false;
    }

    public void setBoolean(boolean value) {
        this.confirmed_value = value;
    }

    public void setInteger(int value) {
    }

    public void setBooleanDefault(boolean value) {
        this.default_value = value;
        this.f_default_setted = true;
    }

    public void setIntegerDefault(int value) {
    }

    public void update() {
        this.value = this.confirmed_value;
        this.changer.reciveValue(this.value);
    }

    public void updateDefault() {
        if (!this.f_default_setted) {
            this.default_value = this.value;
        }
        this.f_default_setted = true;
        this.update();
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
        this.value = value;
        if (null != this.changer) {
            this.changer.reciveValue(value);
        }
    }

    public boolean getBooleanChange() {
        return this.value;
    }

    public void setIntegerChange(int value) {
    }

    public int getIntegerChange() {
        return -1;
    }
}

