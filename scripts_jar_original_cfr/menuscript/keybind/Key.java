/*
 * Decompiled with CFR 0.151.
 */
package menuscript.keybind;

public class Key {
    private String keyname;
    private int keycode;

    public String getKeyname() {
        return this.keyname;
    }

    public void setKeyname(String keyname) {
        this.keyname = keyname;
    }

    public int getKeycode() {
        return this.keycode;
    }

    public void setKeycode(int keycode) {
        this.keycode = keycode;
    }

    public Key clone() {
        Key res = new Key();
        res.keyname = this.keyname;
        res.keycode = this.keycode;
        return res;
    }
}

