/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.MenuAfterInitNarrator;
import menu.menucreation;
import menu.menues;
import rnrcore.loc;

public class MenuPause
implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\SPECMENU.XML";
    private static final String GROUP = "PAUSE";
    private static final String PICTURE = "Text - GAME PAUSED";
    private static final String LOC_TEXT = "specmenu.xml\\PAUSE\\PAUSE\\PAUSE - ALL\\Text - GAME PAUSED";
    private long _menu = 0L;
    private long picture = 0L;
    private static long this_menu = 0L;

    public void restartMenu(long _menu) {
    }

    public static void createPauseMenu() {
        if (0L != this_menu) {
            return;
        }
        this_menu = menues.createSimpleMenu((menucreation)new MenuPause(), 5);
    }

    public static void deletePauseMenu() {
        if (0L == this_menu) {
            return;
        }
        long menu = this_menu;
        this_menu = 0L;
        menues.CallMenuCallBack_ExitMenu(menu);
    }

    private MenuPause() {
    }

    public void InitMenu(long menu) {
        this._menu = menu;
        menues.InitXml(this._menu, XML, GROUP);
        this.picture = menues.FindFieldInMenu(this._menu, PICTURE);
        menues.SetFieldText(this.picture, loc.getMENUString(LOC_TEXT));
    }

    public void AfterInitMenu(long _menu) {
        MenuAfterInitNarrator.justShow(_menu);
    }

    public void exitMenu(long _menu) {
    }

    public String getMenuId() {
        return "pauseMENU";
    }
}

