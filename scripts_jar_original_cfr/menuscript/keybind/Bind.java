/*
 * Decompiled with CFR 0.151.
 */
package menuscript.keybind;

import menuscript.keybind.Action;
import menuscript.keybind.Axe;
import menuscript.keybind.Key;

public class Bind {
    public Action action = null;
    public Key key = null;
    public Axe axe = null;

    public Bind clone() {
        Bind res = new Bind();
        res.action = this.action;
        if (null != this.key) {
            res.key = this.key.clone();
        }
        if (null != this.axe) {
            res.axe = this.axe.clone();
        }
        return res;
    }
}

