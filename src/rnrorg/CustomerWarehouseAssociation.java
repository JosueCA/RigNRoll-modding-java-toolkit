// Decompiled with: CFR 0.152
// Class Version: 5
package rnrorg;

import rnrcore.loc;
import rnrorg.INPC;

public class CustomerWarehouseAssociation
implements INPC {
    private static final String ID = "customer warehouse association";
    private static final String SHORT_ID = "CWA";

    public String getName() {
        return loc.getCustomerName(ID);
    }

    public String getShortName() {
        return loc.getCustomerName(SHORT_ID);
    }

    public String getID() {
        return ID;
    }
}
