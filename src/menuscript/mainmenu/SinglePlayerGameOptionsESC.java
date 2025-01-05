/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import menu.MENUsimplebutton_field;
import menu.SMenu;
import menuscript.mainmenu.CA;
import menuscript.mainmenu.MainMenu;
import menuscript.mainmenu.Panel;
import menuscript.mainmenu.SinglePlayerGameOptions;

public class SinglePlayerGameOptionsESC
extends SinglePlayerGameOptions {
    private static String filename;
    private static String prev_table;
    private static String prev_ranger;
    private static String _TABLE;
    private static String _RANGER;
    private static String _FILE;

    private static void changeTableStrings() {
        prev_table = TABLE;
        prev_ranger = RANGER;
        filename = CA.FILENAME;
        TABLE = _TABLE;
        RANGER = _RANGER;
        CA.FILENAME = _FILE;
    }

    private static void changeBACK() {
        TABLE = prev_table;
        RANGER = prev_ranger;
        CA.FILENAME = filename;
    }

    public static SinglePlayerGameOptionsESC create(long _menu, long exitButton, long defaultButton, long okButton, long applyButton, int game_type) {
        SinglePlayerGameOptionsESC.changeTableStrings();
        SinglePlayerGameOptionsESC result = new SinglePlayerGameOptionsESC(_menu, exitButton, defaultButton, okButton, applyButton, game_type);
        SinglePlayerGameOptionsESC.changeBACK();
        return result;
    }

    private SinglePlayerGameOptionsESC(long _menu, long exitButton, long defaultButton, long okButton, long applyButton, int game_type) {
        super(_menu, new long[0], 0L, exitButton, defaultButton, okButton, applyButton, new Panel(new MainMenu("menu_esc.xml"), "ESC PANEL", new String[0]), game_type, true);
    }

    public void OnExit(long _menu, MENUsimplebutton_field button) {
        this.update();
    }

    public void OnExit(long _menu, SMenu window) {
    }

    public void exitMenu() {
        super.exitMenu();
    }

    static {
        _TABLE = "TABLEGROUP GAMEPLAY 02 - 5 65";
        _RANGER = null;
        _FILE = "..\\data\\config\\menu\\menu_esc.xml";
    }
}

