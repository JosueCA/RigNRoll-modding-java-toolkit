/*
 * Decompiled with CFR 0.151.
 */
package rnrorg;

import rnrcore.loc;
import rnrorg.INPC;

public class QuestCustomer
implements INPC {
    private String id = null;

    public QuestCustomer(String id) {
        this.id = id;
    }

    public String getName() {
        return loc.getCustomerName(this.id);
    }

    public String getID() {
        return this.id;
    }
}

