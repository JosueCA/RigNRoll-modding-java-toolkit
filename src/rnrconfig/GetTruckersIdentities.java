/*
 * Decompiled with CFR 0.151.
 */
package rnrconfig;

import java.util.ArrayList;
import menu.JavaEvents;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class GetTruckersIdentities {
    private ArrayList<String> data = new ArrayList();

    public GetTruckersIdentities() {
        JavaEvents.SendEvent(63, 0, this);
    }

    public void add(String identitie) {
        this.data.add(identitie);
    }

    public ArrayList<String> get() {
        return this.data;
    }
}

