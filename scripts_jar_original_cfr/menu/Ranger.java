/*
 * Decompiled with CFR 0.151.
 */
package menu;

import java.util.ArrayList;
import menu.IRangerChanged;
import menu.MENU_ranger;
import menu.menues;

public class Ranger {
    private static final String METH = "onChange";
    private long ranger;
    private boolean seilent_set_value = false;
    private ArrayList<IRangerChanged> listeners = new ArrayList();

    public void addListener(IRangerChanged lst) {
        this.listeners.add(lst);
    }

    public void removeListener(IRangerChanged lst) {
        this.listeners.remove(lst);
    }

    public Ranger(long _menu, String name, int min, int max, int curent) {
        this.ranger = menues.FindFieldInMenu(_menu, name);
        this.init(_menu, min, max, curent);
    }

    public Ranger(long _menu, long _ranger, int min, int max, int curent) {
        this.ranger = _ranger;
        this.init(_menu, min, max, curent);
    }

    private void init(long _menu, int min, int max, int curent) {
        MENU_ranger rng = menues.ConvertRanger(this.ranger);
        rng.max_value = min;
        rng.max_value = max;
        rng.current_value = curent;
        menues.UpdateField(rng);
        menues.SetScriptOnControl(_menu, rng, this, METH, 1L);
    }

    public void setState(int value) {
        menues.SetFieldState(this.ranger, value);
    }

    public void setValue(int value) {
        MENU_ranger rng = menues.ConvertRanger(this.ranger);
        rng.current_value = value;
        menues.UpdateField(rng);
        menues.ConvertRanger(this.ranger);
    }

    public void setValue(int value, boolean seilent) {
        boolean old = this.seilent_set_value;
        this.seilent_set_value = seilent;
        this.setValue(value);
        this.seilent_set_value = old;
    }

    public void setValue(int min, int max, int value, int page, boolean seilent) {
        boolean old = this.seilent_set_value;
        this.seilent_set_value = seilent;
        MENU_ranger rng = menues.ConvertRanger(this.ranger);
        rng.min_value = min;
        rng.max_value = max;
        rng.current_value = value;
        rng.page = page;
        menues.UpdateField(rng);
        menues.ConvertRanger(this.ranger);
        this.seilent_set_value = old;
    }

    public int getValue() {
        return menues.GetFieldState(this.ranger);
    }

    public void onChange(long _menu, MENU_ranger rng) {
        if (this.seilent_set_value) {
            return;
        }
        for (IRangerChanged iter : this.listeners) {
            iter.rangerChanged(rng.current_value);
        }
    }

    public void show() {
        menues.SetShowField(this.ranger, true);
    }

    public void hide() {
        menues.SetShowField(this.ranger, false);
    }
}

