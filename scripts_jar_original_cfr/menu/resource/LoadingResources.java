/*
 * Decompiled with CFR 0.151.
 */
package menu.resource;

import java.util.HashMap;
import menu.resource.IMenuResources;
import menu.resource.MenuLoadPack;
import menu.resource.ResourcesDescription;

public class LoadingResources {
    private HashMap<String, MenuLoadPack> loadingPacks = new HashMap();

    public void startMenuLoading(String menuID) {
        MenuLoadPack pack = ResourcesDescription.getInstance().getPack(menuID);
        if (null != pack) {
            this.loadingPacks.put(menuID, pack);
        }
    }

    public void leaveMenu(String menuID) {
        if (!this.loadingPacks.containsKey(menuID)) {
            return;
        }
        this.loadingPacks.remove(menuID);
    }

    public IMenuResources getMenuResource(String menuID, String resourceID) {
        if (!this.loadingPacks.containsKey(menuID)) {
            return null;
        }
        return this.loadingPacks.get(menuID).getResource(resourceID);
    }
}

