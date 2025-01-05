/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu.DLCShopMenu;

import menu.Common;

public final class DLCShopMenuFilePath {
    public static String getMenuPath(DLCShopMenuType menuType) {
        return Common.ConstructPath(menuType == DLCShopMenuType.MAIN_MENU ? "menu_MAIN.xml" : "menu_esc.xml");
    }

    public static int getScreenShotId(DLCShopMenuType menuType) {
        return menuType == DLCShopMenuType.MAIN_MENU ? 20 : 21;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum DLCShopMenuType {
        MAIN_MENU,
        ESCAPE_MENU;

    }
}

