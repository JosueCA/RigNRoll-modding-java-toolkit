/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.Common;
import menu.MENUsimplebutton_field;
import menuscript.ISubMenu;
import menuscript.ParentMenu;
import menuscript.WindowParentMenu;
import menuscript.mainmenu.SaveLoadCommonManagement;
import rnrcore.Log;
import rnrcore.eng;

public class EscapeQuit
extends WindowParentMenu
implements ISubMenu {
    private static final String[] BUTTONS = new String[]{"BUTT - QUITGAME - YES", "BUTT - QUITGAME - CANCEL"};
    private static final String[] BUTTONS_METHODS = new String[]{"quitAppl", "quitCancel"};
    private static final String FILE = "..\\data\\config\\menu\\menu_esc.xml";
    private static final String MENU = "QUIT GAME";
    ParentMenu m_parent;

    EscapeQuit(long _menu) {
        super(_menu, FILE, MENU);
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
        eng.console("exit");
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
            eng.console("exit");
        }
    }

    public void afterInit() {
        this.hide();
    }

    public void exitMenu() {
    }
}

