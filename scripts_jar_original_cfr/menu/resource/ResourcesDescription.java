/*
 * Decompiled with CFR 0.151.
 */
package menu.resource;

import java.util.HashMap;
import menu.resource.MenuLoadPack;

public class ResourcesDescription {
    private HashMap<String, MenuLoadPack> packs = new HashMap();
    private static ResourcesDescription instance = null;

    public static ResourcesDescription getInstance() {
        if (instance == null) {
            instance = new ResourcesDescription();
        }
        return instance;
    }

    public MenuLoadPack getPack(String menuid) {
        return this.packs.get(menuid);
    }

    private ResourcesDescription() {
    }
}

