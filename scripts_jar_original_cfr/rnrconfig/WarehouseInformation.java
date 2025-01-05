/*
 * Decompiled with CFR 0.151.
 */
package rnrconfig;

import menu.JavaEvents;

public class WarehouseInformation {
    private String warehouseName;
    private String realName;

    public WarehouseInformation(String warehouseName) {
        this.warehouseName = warehouseName;
        this.realName = warehouseName;
        if (null == this.warehouseName || this.warehouseName.compareTo("") == 0 || this.warehouseName.compareTo(" ") == 0) {
            return;
        }
        JavaEvents.SendEvent(78, 0, this);
    }

    public String getMerchandizeName() {
        return this.warehouseName;
    }

    public String getRealName() {
        return this.realName;
    }
}

