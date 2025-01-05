/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import menu.Common;
import menuscript.ConfirmDialog;
import menuscript.WindowParentMenu;
import menuscript.mainmenu.IWindowContext;
import menuscript.mainmenu.LoadGameTable;

public class EscapeSaveLoadReplay
extends WindowParentMenu
implements IWindowContext {
    private static final String FILE = "..\\data\\config\\menu\\menu_esc.xml";
    private static final String MENU = "LOAD CLIP";
    private static final String[] _BUTTONS = new String[]{"BUTT - CLIPS - PLAY", "BUTT - CLIPS - DELETE"};
    private static final String _TABLE = "TABLEGROUP - CLIP - NAME - 9 36";
    private static final String _RANGER = "LOAD CLIP LIST - Tableranger";
    private static final String _XML_NAME = "..\\data\\config\\menu\\menu_esc.xml";
    private static final String _LINES = "Tablegroup - ELEMENTS - CLIPS Lines";
    private static final String[] _ELEMENTS = new String[]{"CLIPS - CLIPNAME", "CLIPS - CLIPNAME Edit", "CLIPS - CLIPDATE", "CLIPS - CLIPNAME - QuickRace", "CLIPS - CLIPNAME Edit - QuickRace", "CLIPS - CLIPDATE - QuickRace"};
    private LoadGameTable table = null;

    public EscapeSaveLoadReplay(long _menu, Common common, ConfirmDialog dialog) {
        super(_menu, "..\\data\\config\\menu\\menu_esc.xml", MENU);
        this.uiTools = common;
        this.table = new LoadGameTable(this, _menu, _BUTTONS, _TABLE, _RANGER, "..\\data\\config\\menu\\menu_esc.xml", _LINES, _ELEMENTS, 7, 1, true, "LOAD CLIP LIST - CLIPNAME TITLE", "LOAD CLIP LIST - DATE TITLE", "CLIP - SCREENSHOT - TITLE");
    }

    public void afterInit() {
        super.afterInit();
        this.table.afterInit(this);
    }

    public void exitWindowContext() {
        this.hide();
    }

    public void closeMainWindowOfWindowContext() {
    }

    public void Activate() {
        this.updateWindowContext();
        super.Activate();
    }

    public void updateWindowContext() {
        this.table.update(this);
    }

    public void exitMenu() {
        super.exitMenu();
        this.table.deinit();
    }
}

