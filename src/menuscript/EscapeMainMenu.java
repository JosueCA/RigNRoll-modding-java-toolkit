// Decompiled with: CFR 0.152
// Class Version: 5
package menuscript;

import menu.Common;
import menu.JavaEvents;
import menu.MENUsimplebutton_field;
import menu.menues;
import menuscript.ISubMenu;
import menuscript.WindowParentMenu;
import menuscript.mainmenu.SaveLoadCommonManagement;
import rnrcore.Log;

public class EscapeMainMenu
extends WindowParentMenu
implements ISubMenu {
    private static final String[] BUTTONS = new String[]{"BUTT - MAINMENU - YES", "BUTT - MAINMENU - CANCEL"};
    private static final String[] BUTTONS_METHODS = new String[]{"quitMainMenu", "mainmenuCancel"};
    private static final String FILE = "..\\data\\config\\menu\\menu_esc.xml";
    private static final String MENU = "MAIN MENU";
    long _menu;

    EscapeMainMenu(long _menu) {
        super(_menu, FILE, MENU);
        this._menu = _menu;
        if (BUTTONS.length != BUTTONS_METHODS.length) {
            Log.menu("EscapeMainMenu has bad initializers: BUTTONS.length!=BUTTONS_METHODS.length");
        }
    }

    public void InitMenu(long _menu) {
        this.uiTools = new Common(_menu);
        for (int i = 0; i < BUTTONS.length; ++i) {
            this.uiTools.SetScriptOnButton(BUTTONS[i], this, BUTTONS_METHODS[i]);
        }
    }

    public void quitMainMenu(long _menu, MENUsimplebutton_field button) {
        menues.CallMenuCallBack_ExitMenu(_menu);
        JavaEvents.SendEvent(23, 1, this);
    }

    public void mainmenuCancel(long _menu, MENUsimplebutton_field button) {
    }

    public void Activate() {
        if (!SaveLoadCommonManagement.getSaveLoadCommonManager().IsTheCurrentGameSaved()) {
            this.show();
        } else {
            menues.CallMenuCallBack_ExitMenu(this._menu);
            JavaEvents.SendEvent(23, 1, this);
        }
    }
}
