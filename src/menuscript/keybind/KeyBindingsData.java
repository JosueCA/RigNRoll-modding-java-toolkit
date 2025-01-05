/*
 * Decompiled with CFR 0.151.
 */
package menuscript.keybind;

import java.util.ArrayList;
import java.util.Iterator;
import menuscript.keybind.Bind;
import rnrcore.Log;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class KeyBindingsData {
    private String deviceName;
    private ArrayList<Bind> actions = new ArrayList();
    private boolean f_check_access = false;

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String name) {
        this.deviceName = name;
    }

    public void addBind(Bind bind) {
        if (this.f_check_access) {
            Log.menu("KeyBindingsData. Wrong add BindData bahaivouir.");
            return;
        }
        this.actions.add(bind);
    }

    public Iterator<Bind> getIterator() {
        if (this.f_check_access) {
            Log.menu("KeyBindingsData. Wrong add getIterator bahaivouir.");
            return null;
        }
        this.f_check_access = true;
        return this.actions.iterator();
    }

    public void freeIterator() {
        if (!this.f_check_access) {
            Log.menu("KeyBindingsData. Wrong add freeIterator bahaivouir.");
            return;
        }
        this.f_check_access = false;
    }

    public void updateBind(Bind bind) {
        Iterator<Bind> iter = this.getIterator();
        while (iter.hasNext()) {
            Bind original = iter.next();
            if (original.action.getActionnom() != bind.action.getActionnom()) continue;
            original.axe = null != bind.axe ? bind.axe.clone() : null;
            if (null != bind.key) {
                original.key = bind.key.clone();
                continue;
            }
            original.key = null;
        }
        this.freeIterator();
    }

    public int size() {
        return this.actions.size();
    }

    public Bind getBind(int i) {
        if (i >= this.size()) {
            return null;
        }
        return this.actions.get(i);
    }
}

