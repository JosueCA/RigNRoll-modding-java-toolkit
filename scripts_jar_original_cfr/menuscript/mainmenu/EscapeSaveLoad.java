/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import menu.Common;
import menu.KeyPair;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.menues;
import menuscript.ConfirmDialog;
import menuscript.IPoPUpMenuListener;
import menuscript.PoPUpMenu;
import menuscript.WindowParentMenu;
import menuscript.mainmenu.EscapeNewSaveGame;
import menuscript.mainmenu.IWindowContext;
import menuscript.mainmenu.LoadGameTable;
import menuscript.mainmenu.SaveLoadCommonManagement;
import rnrcore.Log;

public class EscapeSaveLoad
extends WindowParentMenu
implements IWindowContext {
    private static final String FILE = "..\\data\\config\\menu\\menu_esc.xml";
    private static final String MENU = "SAVE LOAD GAME";
    private static final String[] _BUTTONS = new String[]{"BUTT - SAVE/LOAD - LOAD", "BUTT - SAVE/LOAD - DELETE"};
    private static final String _TABLE = "TABLEGROUP - GAME - NAME - 9 36";
    private static final String _RANGER = "SAVE/LOAD LIST - Tableranger";
    private static final String _XML_NAME = "..\\data\\config\\menu\\menu_esc.xml";
    private static final String _LINES = "Tablegroup - ELEMENTS - SaveGame Lines";
    private static final String[] _ELEMENTS = new String[]{"SaveGameName", "SaveGameName Edit", "SaveGameTime"};
    private static final int typeOfAGame = 1;
    private static final int typeOfSave = 4;
    private static final String[] LOCAL_BUTTONS = new String[]{"BUTT - SAVE/LOAD - SAVE"};
    private static final String[] LOCAL_METHODS = new String[]{"onSave"};
    private static final String REPLACE_GROUP = "Tablegroup - CONFIRM REPLACE";
    private static final String REPLACE_WINDOW = "CONFIRM REPLACE";
    private static final String REPLACE_TEXT = "REPLACE";
    private static final String REPLACE_TEXT_KEY = "PROFILENAME";
    private static final String ENTERNAME_GROUP = "Tablegroup - SAVELOAD - ENTER GAME NAME";
    private static final String ENTERNAME_WINDOW = "ENTER GAME NAME";
    private PoPUpMenu warning_replace = null;
    private PoPUpMenu warning_entername = null;
    private String replace_text_store;
    private long replace_text = 0L;
    private static final int REPLACE_CONFIRM = 1;
    private static final int ENTERNAME_OK = 2;
    private LoadGameTable table = null;
    private EscapeNewSaveGame savegame = null;
    private String pendedname = null;

    public EscapeSaveLoad(long _menu, Common common, ConfirmDialog dialog) {
        super(_menu, "..\\data\\config\\menu\\menu_esc.xml", MENU);
        this.uiTools = common;
        for (int i = 0; i < LOCAL_BUTTONS.length; ++i) {
            Object field = menues.ConvertMenuFields(menues.FindFieldInMenu(_menu, LOCAL_BUTTONS[i]));
            menues.SetScriptOnControl(_menu, field, this, LOCAL_METHODS[i], 4L);
        }
        this.table = new LoadGameTable(this, _menu, _BUTTONS, _TABLE, _RANGER, "..\\data\\config\\menu\\menu_esc.xml", _LINES, _ELEMENTS, 1, 1, false, "SAVE/LOAD LIST - FILENAME TITLE", "SAVE/LOAD LIST - DATE TITLE", "GAME - SCREENSHOT - TITLE");
        this.savegame = new EscapeNewSaveGame(_menu, this.table, this);
        this.warning_replace = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_esc.xml", REPLACE_GROUP, REPLACE_WINDOW);
        this.warning_replace.addListener(new InWarning(1));
        this.replace_text = this.warning_replace.getField(REPLACE_TEXT);
        this.warning_entername = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_esc.xml", ENTERNAME_GROUP, ENTERNAME_WINDOW);
        this.warning_entername.addListener(new InWarning(2));
        if (this.replace_text != 0L) {
            this.replace_text_store = menues.GetFieldText(this.replace_text);
        }
        SaveLoadCommonManagement.getSaveLoadCommonManager().OnEnterESCmenu();
    }

    public void afterInit() {
        super.afterInit();
        this.table.afterInit(this);
        this.warning_replace.afterInit();
        this.warning_entername.afterInit();
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

    public void SaveGame() {
        this.pendedname = this.table.GetSelectedMediaName();
        if (this.pendedname == null) {
            if (!this.savegame.isGoodName()) {
                this.warning_entername.show();
                return;
            }
            this.pendedname = this.savegame.getSaveName();
        }
        if (!SaveLoadCommonManagement.getSaveLoadCommonManager().SetSaveGameFlag(this.pendedname, 1, 4)) {
            if (this.replace_text != 0L) {
                KeyPair[] keys = new KeyPair[]{new KeyPair(REPLACE_TEXT_KEY, this.pendedname)};
                menues.SetFieldText(this.replace_text, MacroKit.Parse(this.replace_text_store, keys));
                menues.UpdateMenuField(menues.ConvertMenuFields(this.replace_text));
            }
            this.warning_replace.show();
        } else {
            this.exitWindowContext();
        }
    }

    public void onSave(long _menu, MENUsimplebutton_field button) {
        this.SaveGame();
    }

    public void exitMenu() {
        super.exitMenu();
        this.table.deinit();
    }

    class InWarning
    implements IPoPUpMenuListener {
        private int type;

        InWarning(int type) {
            this.type = type;
        }

        public void onAgreeclose() {
            switch (this.type) {
                case 1: {
                    SaveLoadCommonManagement.getSaveLoadCommonManager().DeleteExistsMedia(EscapeSaveLoad.this.pendedname, 1, 4);
                    if (!SaveLoadCommonManagement.getSaveLoadCommonManager().SetSaveGameFlag(EscapeSaveLoad.this.pendedname, 1, 4)) {
                        Log.menu("ERRORR. Bad behaivoir on ne save game named " + EscapeSaveLoad.this.pendedname);
                    }
                    EscapeSaveLoad.this.hide();
                    break;
                }
            }
        }

        public void onCancel() {
        }

        public void onClose() {
        }

        public void onOpen() {
        }
    }
}

