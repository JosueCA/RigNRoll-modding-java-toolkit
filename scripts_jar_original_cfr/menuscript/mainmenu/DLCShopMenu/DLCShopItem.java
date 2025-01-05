/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu.DLCShopMenu;

import menuscript.DLCShopManager;

public final class DLCShopItem {
    String id = null;
    String name = null;
    int price = 0;
    boolean installed = false;
    boolean activated = false;

    DLCShopItem() {
    }

    DLCShopItem(DLCShopManager.Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.price = store.getPrice();
        this.installed = store.getIsInstalled();
        this.activated = store.getIsPurchased();
    }

    DLCShopItem(DLCShopManager.Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.installed = item.getIsInstalled();
        this.activated = item.getIsPurchased();
    }
}

