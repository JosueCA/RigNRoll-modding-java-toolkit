/*
 * Decompiled with CFR 0.151.
 */
package menu;

import java.util.ArrayList;
import menu.IFocusHolder;

public class FocusManager {
    private ArrayList<IFocusHolder> data = new ArrayList();
    private ArrayList<IFocusHolder> lastFocuses = new ArrayList();
    private static FocusManager manager = null;

    private static FocusManager getManager() {
        if (null == manager) {
            manager = new FocusManager();
        }
        return manager;
    }

    private FocusManager() {
    }

    public static void register(IFocusHolder item) {
        FocusManager man = FocusManager.getManager();
        if (man.data.contains(item)) {
            return;
        }
        man.data.add(item);
        man.lastFocuses.add(item);
    }

    public static void unRegister(IFocusHolder item) {
        FocusManager man = FocusManager.getManager();
        man.data.remove(item);
        man.lastFocuses.remove(item);
    }

    public static void enterFocus(IFocusHolder item) {
        FocusManager man = FocusManager.getManager();
        if (!man.lastFocuses.isEmpty()) {
            man.lastFocuses.remove(item);
            if (!man.lastFocuses.isEmpty()) {
                IFocusHolder last = man.lastFocuses.get(man.lastFocuses.size() - 1);
                last.cbLeaveFocus();
                man.lastFocuses.remove(last);
            }
        }
        man.lastFocuses.add(item);
        item.cbEnterFocus();
    }

    public static void leaveFocus(IFocusHolder item) {
        FocusManager man = FocusManager.getManager();
        IFocusHolder last = man.lastFocuses.get(man.lastFocuses.size() - 1);
        if (last.equals(item)) {
            last.cbLeaveFocus();
            man.lastFocuses.remove(item);
            if (!man.lastFocuses.isEmpty()) {
                last = man.lastFocuses.get(man.lastFocuses.size() - 1);
                last.cbEnterFocus();
            }
        } else {
            man.lastFocuses.remove(item);
        }
    }

    public static boolean isFocused(IFocusHolder item) {
        FocusManager man = FocusManager.getManager();
        if (man.lastFocuses.isEmpty()) {
            return false;
        }
        return man.lastFocuses.get(man.lastFocuses.size() - 1).equals(item);
    }

    public static IFocusHolder getFocused() {
        FocusManager man = FocusManager.getManager();
        if (man.lastFocuses.isEmpty()) {
            return null;
        }
        return man.lastFocuses.get(man.lastFocuses.size() - 1);
    }
}

