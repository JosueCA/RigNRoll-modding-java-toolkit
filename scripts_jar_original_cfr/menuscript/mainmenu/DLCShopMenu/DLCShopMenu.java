/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu.DLCShopMenu;

import menu.Common;
import menuscript.IPoPUpMenuListener;
import menuscript.PoPUpMenu;
import menuscript.mainmenu.DLCShopMenu.DLCShopMenuFilePath;
import menuscript.mainmenu.DLCShopMenu.DLCShopTable;
import menuscript.mainmenu.StartMenu;

public final class DLCShopMenu
extends PoPUpMenu {
    private static final String PANEL_NAME = "DLC STORE";
    private static final String[] POPUP_BUTTONS = new String[]{"DLC STORE Exit"};
    private static final String[] POPUP_METHODS = new String[]{"OnOk"};
    private StartMenu startMenu;
    DLCShopTable m_dlcShopTable;

    public DLCShopMenu(long _menu, StartMenu startMenu) {
        super(_menu, "..\\data\\config\\menu\\menu_MAIN.xml", PANEL_NAME, null, POPUP_BUTTONS, POPUP_METHODS, false);
        this.startMenu = startMenu;
        Common common = new Common(_menu);
        this.m_dlcShopTable = new DLCShopTable(common, PANEL_NAME, DLCShopMenuFilePath.DLCShopMenuType.MAIN_MENU);
        this.m_dlcShopTable.Setup(40, 16, DLCShopMenuFilePath.getMenuPath(DLCShopMenuFilePath.DLCShopMenuType.MAIN_MENU), "Tablegroup - ELEMENTS - DLC Lines", "TABLEGROUP - DLC - 16 40");
        this.m_dlcShopTable.attachRanger(common.FindScroller("Tableranger - DLC CONTENT"));
        this.addListener(new OpenCloseListener());
    }

    public void afterInit(long _menu) {
        this.m_dlcShopTable.afterInitMenu();
        super.afterInit();
    }

    public void OnDLCShopOpen() {
        this.m_dlcShopTable.setStartVisibilityOfButtons();
        this.startMenu.HideAllPanels(true);
    }

    public void OnDLCShopExit() {
        this.startMenu.HideAllPanels(false);
        this.m_dlcShopTable.hide();
    }

    public void deinit() {
        this.m_dlcShopTable.deinit();
    }

    class OpenCloseListener
    implements IPoPUpMenuListener {
        OpenCloseListener() {
        }

        public void onAgreeclose() {
            DLCShopMenu.this.OnDLCShopExit();
        }

        public void onCancel() {
        }

        public void onClose() {
        }

        public void onOpen() {
            DLCShopMenu.this.OnDLCShopOpen();
        }
    }
}

