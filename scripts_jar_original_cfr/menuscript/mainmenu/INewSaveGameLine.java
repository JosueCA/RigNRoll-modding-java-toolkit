/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import menuscript.mainmenu.SaveLoadCommonManagement;

public interface INewSaveGameLine {
    public boolean isMediaCurrent(SaveLoadCommonManagement.Media var1);

    public boolean canBeLoaded(SaveLoadCommonManagement.Media var1);

    public boolean canBeDeleted(SaveLoadCommonManagement.Media var1);

    public boolean clearOnEnterEdit(SaveLoadCommonManagement.Media var1);

    public boolean dismiss(SaveLoadCommonManagement.Media var1, String var2);

    public boolean rename(SaveLoadCommonManagement.Media var1, String var2, String var3);

    public boolean canLoad(SaveLoadCommonManagement.Media var1);

    public boolean canDelete(SaveLoadCommonManagement.Media var1);

    public boolean isPersistant();
}

