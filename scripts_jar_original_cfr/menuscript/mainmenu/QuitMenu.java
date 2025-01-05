/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import menu.MENUsimplebutton_field;
import menu.menues;
import menuscript.mainmenu.MainMenu;
import menuscript.mainmenu.Panel;
import rnrcore.Log;
import rnrcore.eng;

public class QuitMenu
extends Panel {
    private static final String PANELNAME = "QUIT";
    private static final String QUITGAMEBUTTON = "button QUIT - OK";
    private static final String QUITGAMEMETHOD = "OnQuit";

    public QuitMenu(MainMenu menu) {
        super(menu, PANELNAME, new String[0]);
        long l_button = menu.findField(QUITGAMEBUTTON);
        if (0L == l_button) {
            Log.menu("QuitMenu constructor. Panel QUIT Cannot find button button QUIT - OK");
            return;
        }
        MENUsimplebutton_field qbutton = menues.ConvertSimpleButton(l_button);
        if (null == qbutton) {
            Log.menu("QuitMenu constructor. Panel QUIT has button button QUIT - OK of wrong type - not simple button.");
            return;
        }
        menues.SetScriptOnControl(menu._menu, qbutton, this, QUITGAMEMETHOD, 4L);
    }

    public void OnQuit(long _menu, MENUsimplebutton_field button) {
        eng.console("exit");
    }
}

