/*
 * Decompiled with CFR 0.151.
 */
package menuscript.office;

import menu.MENU_ranger;
import menu.MenuControls;
import menu.menucreation;
import menu.menues;
import menuscript.PoPUpMenu;

public class testOfficeMenu
implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\menu_office.xml";
    private static final String CONTROLS_MAIN = "OFFICE";
    private static final String XML_WARNING_HAS_DEPT = "..\\data\\config\\menu\\menu_unsettled_debt.xml";
    private static final String CONTROLS_WARNING_HASDEPT = "Message Debt";
    private PoPUpMenu warning_has_dept = null;
    MenuControls allmenu = null;

    public void restartMenu(long _menu) {
    }

    public static long create() {
        return menues.createSimpleMenu(new testOfficeMenu(), 1000000.0, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public void InitMenu(long _menu) {
        this.allmenu = new MenuControls(_menu, XML, CONTROLS_MAIN);
        this.warning_has_dept = new PoPUpMenu(_menu, XML_WARNING_HAS_DEPT, CONTROLS_WARNING_HASDEPT, CONTROLS_WARNING_HASDEPT, false);
    }

    public void AfterInitMenu(long _menu) {
        this.warning_has_dept.afterInit();
        this.warning_has_dept.show();
        menues.setShowMenu(_menu, true);
        menues.SetStopWorld(_menu, true);
        menues.WindowSet_ShowCursor(_menu, true);
        long control = menues.FindFieldInMenu(_menu, "MF - My Fleet - Tableranger");
        MENU_ranger ranger = menues.ConvertRanger(control);
        ranger.max_value = 3000;
        ranger.min_value = 0;
        ranger.current_value = 1500;
        menues.UpdateField(ranger);
    }

    public void exitMenu(long _menu) {
    }

    public String getMenuId() {
        return "officeMENU";
    }
}

