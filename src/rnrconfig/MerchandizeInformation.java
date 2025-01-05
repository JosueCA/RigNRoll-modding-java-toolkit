/*
 * Decompiled with CFR 0.151.
 */
package rnrconfig;

import menu.JavaEvents;

public class MerchandizeInformation {
    private String merchandizeName;
    private String realName;

    public MerchandizeInformation(String merchandizeName) {
        this.merchandizeName = merchandizeName;
        this.realName = merchandizeName;
        if (null == this.merchandizeName || this.merchandizeName.compareTo("") == 0 || this.merchandizeName.compareTo(" ") == 0) {
            return;
        }
        JavaEvents.SendEvent(77, 0, this);
    }

    public String getMerchandizeName() {
        return this.merchandizeName;
    }

    public String getRealName() {
        return this.realName;
    }
}

