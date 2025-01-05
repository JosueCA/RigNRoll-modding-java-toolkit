/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import java.util.Vector;
import menu.JavaEvents;
import rnrcore.eng;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DLCShopManager {
    static boolean bIsDebug = false;
    public static final int ACT_ERROR_CODE_SUCCESS = 0;
    public static final int ACT_ERROR_CODE_NO_SERVER_CONNECTION = 1;
    public static final int ACT_ERROR_CODE_TIMEOUT = 2;
    public static final int ACT_ERROR_CODE_BAD_SERIAL = 3;
    public static final int ACT_ERROR_CODE_ALREADY_ACTIVATED = 4;
    public static final int ACT_ERROR_CODE_ACTIVATE_REJEECT = 5;
    public static final int ACT_ERROR_CODE_UNKNOWN_STORE = 6;
    Vector<Store> stores = new Vector();
    private int out_result = 0;
    private int out_screenshotid = 0;
    private String dlc_detail = new String();
    private String dlc_serial = new String();
    private String dlc_id = new String();
    private static DLCShopManager instance;

    public static DLCShopManager getDLCShopManager() {
        if (null == instance) {
            instance = new DLCShopManager();
        }
        return instance;
    }

    public static Object getDLCShopManagerObject() {
        if (null == instance) {
            instance = new DLCShopManager();
        }
        return instance;
    }

    public void UpdateStore() {
        this.stores.clear();
        if (!bIsDebug) {
            JavaEvents.SendEvent(90, 0, this);
        } else {
            Store store1 = new Store();
            store1.id = "UnknownID";
            store1.name = "\u00d0\u00e0\u00f1\u00ea\u00f0\u00e0\u00f1\u00ea\u00e8 \u00ec\u00e0\u00f8\u00e8\u00ed";
            store1.price = 150;
            store1.bIsInstalled = true;
            store1.bIsPurchased = false;
            this.stores.add(store1);
            Store store2 = new Store();
            store2.id = "UnknownID";
            store2.name = "\u00ca\u00ee\u00ec\u00ef\u00eb\u00e5\u00ea\u00f2 \u00d1\u00ef\u00ee\u00f0\u00f2 1";
            store2.price = 75;
            store2.bIsInstalled = false;
            store2.bIsPurchased = true;
            this.stores.add(store2);
            Store store3 = new Store();
            store3.id = "UnknownID";
            store3.name = "\u00ca\u00ee\u00ec\u00ef\u00eb\u00e5\u00ea\u00f2 \u00d2\u00ff\u00e3\u00e0\u00f7\u00e8 1";
            store3.price = 90;
            store3.bIsInstalled = false;
            store3.bIsPurchased = true;
            this.stores.add(store3);
            Item item1 = new Item();
            item1.id = "UnknownID";
            item1.name = "F-liner Argosy \u00d1\u00ef\u00ee\u00f0\u00f2";
            item1.price = 30;
            item1.bIsInstalled = false;
            item1.bIsPurchased = true;
            Item item2 = new Item();
            item2.id = "UnknownID";
            item2.name = "Sterling 9500 \u00d1\u00ef\u00ee\u00f0\u00f2";
            item2.price = 30;
            item2.bIsInstalled = false;
            item2.bIsPurchased = true;
            Item item3 = new Item();
            item3.id = "UnknownID";
            item3.name = "Hercules Atlas \u00d1\u00ef\u00ee\u00f0\u00f2";
            item3.price = 30;
            item3.bIsInstalled = false;
            item3.bIsPurchased = true;
            store2.items.add(item1);
            store2.items.add(item2);
            store2.items.add(item3);
            Item item4 = new Item();
            item4.id = "UnknownID";
            item4.name = "Volvo VNL 650";
            item4.price = 30;
            item4.bIsInstalled = true;
            item4.bIsPurchased = false;
            Item item5 = new Item();
            item5.id = "UnknownID";
            item5.name = "Kenworth  T2000";
            item5.price = 30;
            item5.bIsInstalled = true;
            item5.bIsPurchased = false;
            Item item6 = new Item();
            item6.id = "UnknownID";
            item6.name = "F-liner  Cascadia";
            item6.price = 30;
            item6.bIsInstalled = true;
            item6.bIsPurchased = false;
            store3.items.add(item4);
            store3.items.add(item5);
            store3.items.add(item6);
        }
    }

    public Vector<Store> GetStores() {
        return this.stores;
    }

    public int Activate(String serial) {
        if (!bIsDebug) {
            this.dlc_serial = serial;
            JavaEvents.SendEvent(90, 1, this);
            this.UpdateStore();
            return this.out_result;
        }
        return 0;
    }

    public String UpdatePicture(String id, int screenshotid) {
        if (!bIsDebug) {
            this.dlc_id = id;
            this.out_screenshotid = screenshotid;
            JavaEvents.SendEvent(90, 2, this);
            return this.dlc_detail;
        }
        return "UnknownLockID";
    }

    public void GoToURL(String id) {
        if (!bIsDebug) {
            this.dlc_id = id;
            JavaEvents.SendEvent(90, 3, this);
        }
    }

    void DebugPrint() {
        this.UpdateStore();
        for (int i = 0; i < this.stores.size(); ++i) {
            Store store = this.stores.get(i);
            eng.writeLog("");
            eng.writeLog("");
            eng.writeLog("");
            eng.writeLog("id = " + store.id);
            eng.writeLog("name = " + store.name);
            eng.writeLog("price = " + store.price);
            eng.writeLog("ins = " + store.bIsInstalled);
            eng.writeLog("pur = " + store.bIsPurchased);
            eng.writeLog("{");
            for (int j = 0; j < store.items.size(); ++j) {
                Item item = store.items.get(j);
                eng.writeLog("\tid = " + item.id);
                eng.writeLog("\tname = " + item.name);
                eng.writeLog("\tprice = " + item.price);
                eng.writeLog("\tins = " + item.bIsInstalled);
                eng.writeLog("\tpur = " + item.bIsPurchased);
                eng.writeLog("\t");
            }
            eng.writeLog("}");
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class Store {
        String id;
        String name;
        int price;
        boolean bIsInstalled;
        boolean bIsPurchased;
        Vector<Item> items = new Vector();

        public String getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public int getPrice() {
            return this.price;
        }

        public boolean getIsInstalled() {
            return this.bIsInstalled;
        }

        public boolean getIsPurchased() {
            return this.bIsPurchased;
        }

        public Vector<Item> getItems() {
            return this.items;
        }

        Store() {
        }
    }

    public static class Item {
        String id;
        String name;
        int price;
        boolean bIsInstalled;
        boolean bIsPurchased;

        public String getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public int getPrice() {
            return this.price;
        }

        public boolean getIsInstalled() {
            return this.bIsInstalled;
        }

        public boolean getIsPurchased() {
            return this.bIsPurchased;
        }

        Item() {
        }
    }
}

