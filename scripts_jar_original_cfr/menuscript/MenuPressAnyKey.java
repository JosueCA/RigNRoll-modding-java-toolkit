/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.MenuAfterInitNarrator;
import menu.menucreation;
import menu.menues;

public class MenuPressAnyKey
implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\SPECMENU.XML";
    private static final String GROUP = "PRESS ANY KEY";
    private long _menu = 0L;
    private static long this_menu = 0L;

    public void restartMenu(long _menu) {
    }

    public static void createPauseMenu() {
        if (0L != this_menu) {
            return;
        }
        this_menu = menues.createSimpleMenu((menucreation)new MenuPressAnyKey(), 5);
    }

    public static void deletePauseMenu() {
        if (0L == this_menu) {
            return;
        }
        long menu = this_menu;
        this_menu = 0L;
        menues.CallMenuCallBack_ExitMenu(menu);
    }

    private MenuPressAnyKey() {
    }

    public void InitMenu(long menu) {
        this._menu = menu;
        menues.InitXml(this._menu, XML, GROUP);
    }

    public void AfterInitMenu(long _menu) {
        MenuAfterInitNarrator.justShow(_menu);
    }

    public void exitMenu(long _menu) {
    }

    public String getMenuId() {
        return "pressanykeyMENU";
    }
}

