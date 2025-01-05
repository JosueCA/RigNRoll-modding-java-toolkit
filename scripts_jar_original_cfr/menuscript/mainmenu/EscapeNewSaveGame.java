/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import menuscript.mainmenu.EscapeSaveLoad;
import menuscript.mainmenu.INewSaveGameLine;
import menuscript.mainmenu.LoadGameTable;
import menuscript.mainmenu.SaveLoadCommonManagement;
import rnrcore.loc;

public class EscapeNewSaveGame
implements INewSaveGameLine {
    private String SAVE_GAME_NAME = null;
    private String SAVE_GAME_NAME_FIRST = null;
    private static final String TEXT = loc.getMENUString("common\\[EMPTY]");
    EscapeSaveLoad menu = null;
    private boolean first_time = true;

    public String getSaveName() {
        return this.SAVE_GAME_NAME;
    }

    public boolean isGoodName() {
        return this.SAVE_GAME_NAME != null && this.SAVE_GAME_NAME.compareTo("") != 0 && this.SAVE_GAME_NAME.compareTo(this.SAVE_GAME_NAME_FIRST) != 0;
    }

    public EscapeNewSaveGame(long _menu, LoadGameTable table, EscapeSaveLoad __menu) {
        SaveLoadCommonManagement.Media game_media = new SaveLoadCommonManagement.Media();
        game_media.game_type = 1;
        game_media.media_name = TEXT;
        game_media.media_time = SaveLoadCommonManagement.getSaveLoadCommonManager().GetCurrentMediaTime();
        this.menu = __menu;
        table.addTABLEDATAline(this, game_media);
    }

    public boolean canDelete(SaveLoadCommonManagement.Media data) {
        return false;
    }

    public boolean canLoad(SaveLoadCommonManagement.Media data) {
        return false;
    }

    public boolean isPersistant() {
        return true;
    }

    public boolean rename(SaveLoadCommonManagement.Media data, String oldName, String newName) {
        if (null == this.SAVE_GAME_NAME) {
            this.SAVE_GAME_NAME = oldName;
        }
        if (newName.compareToIgnoreCase("") == 0) {
            return false;
        }
        if (this.SAVE_GAME_NAME.compareToIgnoreCase(newName) != 0) {
            this.SAVE_GAME_NAME = newName;
            data.media_name = this.SAVE_GAME_NAME_FIRST;
            this.menu.SaveGame();
            return true;
        }
        return false;
    }

    public boolean dismiss(SaveLoadCommonManagement.Media data, String newname) {
        data.media_name = this.SAVE_GAME_NAME_FIRST;
        this.SAVE_GAME_NAME = newname.compareTo("") == 0 ? this.SAVE_GAME_NAME_FIRST : newname;
        return true;
    }

    public boolean clearOnEnterEdit(SaveLoadCommonManagement.Media data) {
        if (this.first_time || this.SAVE_GAME_NAME_FIRST.compareToIgnoreCase(data.media_name) == 0) {
            this.SAVE_GAME_NAME_FIRST = data.media_name;
            this.SAVE_GAME_NAME = "";
            this.first_time = false;
            return true;
        }
        return false;
    }

    public boolean canBeDeleted(SaveLoadCommonManagement.Media data) {
        return false;
    }

    public boolean canBeLoaded(SaveLoadCommonManagement.Media data) {
        return false;
    }

    public boolean isMediaCurrent(SaveLoadCommonManagement.Media data) {
        return true;
    }
}

