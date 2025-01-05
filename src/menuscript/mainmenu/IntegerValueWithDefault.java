/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

public class IntegerValueWithDefault {
    public int value;
    public int default_value;

    public IntegerValueWithDefault(int value) {
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

