/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu.DLCShopMenu;

import menu.Common;
import menu.MENUsimplebutton_field;
import menu.SMenu;
import menu.menues;
import menuscript.WindowParentMenu;
import menuscript.mainmenu.DLCShopMenu.DLCShopMenuFilePath;
import menuscript.mainmenu.DLCShopMenu.DLCShopTable;

public class EscapeDLCShopMenu
extends WindowParentMenu {
    private static final String FILE_NAME = DLCShopMenuFilePath.getMenuPath(DLCShopMenuFilePath.DLCShopMenuType.ESCAPE_MENU);
    private static final String MENU_NAME = "DLC STORE";
    private long m_menu;
    private long m_cancelButtonId;
    DLCShopTable m_dlcShopTable;
    Common m_common;

    public EscapeDLCShopMenu(long _menu) {
        super(_menu, FILE_NAME, MENU_NAME);
        this.m_menu = _menu;
        this.m_common = new Common(_menu);
    }

    public void InitMenu(long _menu) {
        super.InitMenu(_menu);
        this.m_dlcShopTable = new DLCShopTable(this.m_common, MENU_NAME, DLCShopMenuFilePath.DLCShopMenuType.ESCAPE_MENU);
        this.m_dlcShopTable.Setup(40, 16, FILE_NAME, "Tablegroup - ELEMENTS - DLC Lines", "TABLEGROUP - DLC - 16 40");
        this.m_dlcShopTable.attachRanger(this.m_common.FindScroller("Tableranger - DLC CONTENT"));
        this.m_cancelButtonId = menues.FindFieldInMenu(_menu, "DLC STORE Exit");
    }

    public void afterInit() {
        super.afterInit();
        this.m_dlcShopTable.afterInitMenu();
        if (0L != this.m_cancelButtonId) {
            menues.SetScriptOnControl(this.m_menu, menues.ConvertMenuFields(this.m_cancelButtonId), this, "OnCancelPressed", 4L);
            this.OnCancelPressed(0L, null);
        }
    }

    public void OnCancelPressed(long _menu, MENUsimplebutton_field button) {
        if (this.m_menu == _menu && null != button && button.nativePointer == this.m_cancelButtonId) {
            this.OnClose(_menu, button);
        }
    }

    protected void OnClose(long _menu, MENUsimplebutton_field button) {
        this.m_dlcShopTable.hide();
        super.hide();
    }

    protected void OnExit(long _menu, SMenu wnd) {
        this.m_dlcShopTable.hide();
        super.hide();
    }

    public void deinit() {
        this.m_dlcShopTable.deinit();
    }
}

