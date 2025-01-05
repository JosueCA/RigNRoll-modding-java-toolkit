/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import menuscript.mainmenu.LoadGameTable;
import menuscript.mainmenu.Panel;
import menuscript.mainmenu.PanelDialog;

public class SinglePlayerLoadGame
extends PanelDialog {
    private static final String[] _BUTTONS = new String[]{"SINGLE PLAYER - LOAD GAME LOAD", "SINGLE PLAYER - LOAD GAME DELETE"};
    private static final String _TABLE = "TABLEGROUP - SINGLE PLAYER - LOAD GAME - 15 40";
    private static final String _RANGER = "Tableranger - SINGLE PLAYER - LOAD GAME";
    private static final String _XML_NAME = "..\\data\\config\\menu\\menu_MAIN.xml";
    private static final String _LINES = "Tablegroup - ELEMENTS - SaveGame Lines";
    private static final String[] _ELEMENTS = new String[]{"SINGLE PLAYER - LOAD GAME - SaveGameName", "SINGLE PLAYER - LOAD GAME - SaveGameName Edit", "SAVEGAME - DATE"};
    private static final int typeOfAGame = 1;
    LoadGameTable table = null;

    public SinglePlayerLoadGame(long _menu, long[] controls, long window, long exitButton, long defaultButton, long okButton, long applyButton, Panel parent) {
        super(_menu, window, controls, exitButton, defaultButton, okButton, applyButton, parent);
        this.table = new LoadGameTable(this, _menu, _BUTTONS, _TABLE, _RANGER, _XML_NAME, _LINES, _ELEMENTS, 1, 0, false, "BUTTON - SINGLE PLAYER - LOAD GAME - SAVEGAMENAME TITLE", "BUTTON - SINGLE PLAYER - LOAD GAME - DATE TITLE", "SINGLE PLAYER - LOAD GAME - SCREENSHOT - SAVEGAMENAME");
    }

    public void afterInit() {
        super.afterInit();
        this.table.afterInit(this);
    }

    public void update() {
        super.update();
        this.table.update(this);
    }

    public void exitMenu() {
        this.table.deinit();
        super.exitMenu();
    }

    public void readParamValues() {
        this.table.readParamValues();
    }
}

