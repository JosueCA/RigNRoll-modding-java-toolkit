/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import menuscript.IPoPUpMenuListener;
import menuscript.PoPUpMenu;
import menuscript.mainmenu.LoadGameTable;
import menuscript.mainmenu.MainMenu;
import menuscript.mainmenu.Panel;

public class Replay
extends Panel {
    private static final String[] PANELS_GROUPS = new String[0];
    private static final String PANELNAME = "REPLAY";
    private static final String[] _BUTTONS = new String[]{"Button REPLAY PLAY", "Button REPLAY DELETE"};
    private static final String _TABLE = "TABLEGROUP - REPLAY - 13 40";
    private static final String _RANGER = "Tableranger - REPLAY";
    private static final String _XML_NAME = "..\\data\\config\\menu\\menu_MAIN.xml";
    private static final String _LINES = "Tablegroup - ELEMENTS - Replay Lines";
    private static final String[] _ELEMENTS = new String[]{"REPLAY - ClipName", "REPLAY - ClipName Edit", "REPLAY - ClipDate", "REPLAY - ClipName - QuickRace", "REPLAY - ClipName Edit - QuickRace", "REPLAY - ClipDate - QuickRace"};
    private static final String CANNOTSHOW_GROUP = "Tablegroup - REPLAY - NO FILES FOUND";
    private static final String CANNOTSHOW_WINDOW = "SINGLE PLAYER - REPLAY - NoFilesFound";
    private PoPUpMenu info_cannotshow = null;
    LoadGameTable table = null;

    Replay(MainMenu menu, IPoPUpMenuListener warn_lst) {
        super(menu, PANELNAME, PANELS_GROUPS);
        this.table = new LoadGameTable(this, menu._menu, _BUTTONS, _TABLE, _RANGER, _XML_NAME, _LINES, _ELEMENTS, 7, 0, true, "BUTTON - REPLAY - SAVEGAMENAME TITLE", "BUTTON - REPLAY - DATE TITLE", "REPLAY - SCREENSHOT - SAVEGAMENAME");
        this.info_cannotshow = new PoPUpMenu(menu._menu, _XML_NAME, CANNOTSHOW_GROUP, CANNOTSHOW_WINDOW);
        this.info_cannotshow.addListener(warn_lst);
    }

    public void init() {
        super.init();
        this.info_cannotshow.afterInit();
        this.table.afterInit(this);
    }

    public void updateWindowContext() {
        super.updateWindowContext();
        this.table.update(this);
    }

    final boolean isClipsListEmpty() {
        return LoadGameTable.isLoadgameListEmpty(7, true);
    }

    final void warnAboutEmptyList() {
        this.info_cannotshow.show();
    }

    public void exitMenu() {
        super.exitMenu();
        this.table.deinit();
    }
}

