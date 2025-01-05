/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

public class BooleanValueWithDefault {
    public boolean value;
    public boolean default_value;

    public BooleanValueWithDefault(boolean value) {
        this.value = value;
        this.updateDefault();
    }

    public void updateDefault() {
        this.default_value = this.value;
    }

    public void makeDefault() {
        this.value = this.default_value;
    }
}

