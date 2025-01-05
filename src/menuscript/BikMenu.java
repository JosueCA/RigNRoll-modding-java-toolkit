/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.menucreation;
import menu.menues;

public class BikMenu
implements menucreation {
    public void exitMenu(long _menu) {
    }

    public void restartMenu(long _menu) {
    }

    public void InitMenu(long _menu) {
        menues.InitXml(_menu, "..\\data\\config\\menu\\intro.xml", "anim");
    }

    public void AfterInitMenu(long _menu) {
        menues.setShowMenu(_menu, true);
        menues.SetStopWorld(_menu, true);
    }

    public String getMenuId() {
        return "introMENU";
    }

    public static long createMenu() {
        return menues.createSimpleMenu(new BikMenu(), 1000000.0, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }
}

