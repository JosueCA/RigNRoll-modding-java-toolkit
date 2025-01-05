/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import menu.JavaEvents;
import rnrscr.CollectionOfData;

public class EventsHelper {
    public static CollectionOfData getWarehousesData() {
        CollectionOfData res = new CollectionOfData();
        JavaEvents.SendEvent(44, 8, res);
        return res;
    }
}

