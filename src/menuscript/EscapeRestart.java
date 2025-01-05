/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.Common;
import menu.JavaEvents;
import menu.MENUsimplebutton_field;
import menu.menues;
import menuscript.ISubMenu;
import menuscript.ParentMenu;
import menuscript.WindowParentMenu;
import menuscript.mainmenu.SaveLoadCommonManagement;
import rnrcore.Log;

public class EscapeRestart
extends WindowParentMenu
implements ISubMenu {
    private static final String[] BUTTONS = new String[]{"BUTT - RESTART - YES", "BUTT - RESTART - CANCEL"};
    private static final String[] BUTTONS_METHODS = new String[]{"quitAppl", "quitCancel"};
    private static final String FILE = "..\\data\\config\\menu\\menu_esc.xml";
    private static final String MENU = "RESTART GAME";
    long _menu;
    ParentMenu m_parent;

    EscapeRestart(long _menu) {
        super(_menu, FILE, MENU);
        this._menu = _menu;
        if (BUTTONS.length != BUTTONS_METHODS.length) {
            Log.menu("EscapeQuit has bad initializers: BUTTONS.length!=BUTTONS_METHODS.length");
        }
    }

    public int LeaveSubMenu() {
        this.hide();
        return 0;
    }

    public String GetCustomText() {
        return null;
    }

    public void Refresh() {
    }

    public void SetParent(ParentMenu parent) {
        this.m_parent = parent;
    }

    public void UserDecision(int type) {
    }

    public void InitMenu(long _menu) {
        this.uiTools = new Common(_menu);
        for (int i = 0; i < BUTTONS.length; ++i) {
            this.uiTools.SetScriptOnButton(BUTTONS[i], this, BUTTONS_METHODS[i]);
        }
    }

    public void quitAppl(long _menu, MENUsimplebutton_field button) {
        menues.CallMenuCallBack_ExitMenu(_menu);
        JavaEvents.SendEvent(23, 2, this);
    }

    public void quitCancel(long _menu, MENUsimplebutton_field button) {
    }

    public void AfterInitMenu(long _menu) {
    }

    public void exitMenu(long _menu) {
    }

    public void Activate() {
        if (!SaveLoadCommonManagement.getSaveLoadCommonManager().IsTheCurrentGameSaved()) {
            this.show();
        } else {
            menues.CallMenuCallBack_ExitMenu(this._menu);
            JavaEvents.SendEvent(23, 2, this);
        }
    }

    public void afterInit() {
        this.hide();
    }

    public void exitMenu() {
    }
}

