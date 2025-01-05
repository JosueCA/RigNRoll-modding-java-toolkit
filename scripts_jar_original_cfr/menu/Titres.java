/*
 * Decompiled with CFR 0.151.
 */
package menu;

import menu.MenuAfterInitNarrator;
import menu.menucreation;
import menu.menues;
import rnrcore.eng;

public class Titres
implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\specmenu.xml";
    private static final String GROUP = "titre";
    private static final String TEXTFIELD = "text";
    private static long last_menu;
    private String text;

    public void restartMenu(long _menu) {
    }

    public Titres(String text) {
        this.text = text;
    }

    public void InitMenu(long _menu) {
        menues.InitXml(_menu, XML, GROUP);
        long control = menues.FindFieldInMenu(_menu, TEXTFIELD);
        if (0L != control) {
            menues.SetFieldText(control, this.text);
        } else {
            eng.err("ERRORR. Cannot find control text in xml ..\\data\\config\\menu\\specmenu.xml");
        }
    }

    public void AfterInitMenu(long _menu) {
        MenuAfterInitNarrator.justShow(_menu);
    }

    public void exitMenu(long _menu) {
        if (last_menu == _menu) {
            last_menu = 0L;
        } else {
            eng.writeLog("Titre exitMenu with bad code: " + _menu + " Last menu: " + last_menu);
        }
    }

    public String getMenuId() {
        return "titreMENU";
    }

    public static void clearTitres() {
        if (last_menu != 0L) {
            menues.CallMenuCallBack_ExitMenu(last_menu);
            last_menu = 0L;
        }
    }

    public static long create(float dt, String text) {
        if (last_menu != 0L) {
            menues.CallMenuCallBack_ExitMenu(last_menu);
            last_menu = 0L;
        }
        last_menu = menues.createTitre(new Titres(text), dt);
        return last_menu;
    }
}

