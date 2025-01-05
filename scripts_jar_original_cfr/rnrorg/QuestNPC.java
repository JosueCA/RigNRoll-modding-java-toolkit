/*
 * Decompiled with CFR 0.151.
 */
package rnrorg;

import rnrorg.INPC;
import rnrorg.MissionEventsMaker;

public class QuestNPC
implements INPC {
    private String id = null;

    public QuestNPC(String id) {
        this.id = id;
    }

    public String getName() {
        return MissionEventsMaker.getLocalisationNPCInfo(this.id);
    }

    public String getID() {
        return this.id;
    }
}

