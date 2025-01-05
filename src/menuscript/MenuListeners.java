/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import java.util.ArrayList;
import java.util.Iterator;
import menuscript.IMenuListener;

public class MenuListeners {
    private ArrayList<IMenuListener> listeners = new ArrayList();

    public void addListener(IMenuListener listener) {
        this.listeners.add(listener);
    }

    public void open() {
        Iterator<IMenuListener> iter = this.listeners.iterator();
        while (iter.hasNext()) {
            iter.next().onOpen();
        }
    }

    public void close() {
        Iterator<IMenuListener> iter = this.listeners.iterator();
        while (iter.hasNext()) {
            iter.next().onClose();
        }
    }
}

