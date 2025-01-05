/*
 * Decompiled with CFR 0.151.
 */
package menu;

import menu.MenuAfterInitNarrator;
import menu.menucreation;
import menu.menues;
import rnrcore.Helper;
import rnrcore.TypicalAnm;
import rnrcore.eng;
import rnrcore.loc;

public class PastFewDaysMenu
extends TypicalAnm
implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\specmenu.xml";
    private static final String GROUP = "BLACK SCREEN and TITRES";
    private static final String TEXTFIELD = "text";
    String text_ref = "specmenu.xml\\BLACK SCREEN and TITRES\\BLACK SCREEN and TITRES\\text";
    double time_show = 4.0;
    long last_menu;

    public void restartMenu(long _menu) {
    }

    public void InitMenu(long _menu) {
        this.last_menu = _menu;
        menues.InitXml(_menu, XML, GROUP);
        long control = menues.FindFieldInMenu(_menu, TEXTFIELD);
        if (0L != control) {
            menues.SetFieldText(control, loc.getMENUString(this.text_ref));
        } else {
            eng.err("ERRORR. Cannot find control text in xml ..\\data\\config\\menu\\specmenu.xml");
        }
    }

    public void AfterInitMenu(long _menu) {
        MenuAfterInitNarrator.justShow(_menu);
        eng.CreateInfinitScriptAnimation(this);
    }

    public void exitMenu(long _menu) {
    }

    public String getMenuId() {
        return "blackscreentitresMENU";
    }

    public static long create() {
        long last_menu = menues.createSimpleMenu(new PastFewDaysMenu());
        return last_menu;
    }

    public boolean animaterun(double dt) {
        if (dt >= this.time_show) {
            menues.CallMenuCallBack_ExitMenu(this.last_menu);
            Helper.peekNativeMessage("resumescript");
            return true;
        }
        return false;
    }
}

