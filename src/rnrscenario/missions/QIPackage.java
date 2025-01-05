/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import rnrorg.MissionEventsMaker;
import rnrscenario.missions.QuestItem;

public class QIPackage
extends QuestItem {
    static final long serialVersionUID = 0L;
    public String model;
    public String place;

    public String getPlacement() {
        return this.place;
    }

    public void doPlace(String mission_name) {
        MissionEventsMaker.createQuestItemPackage(mission_name, this.model, this.place);
    }

    public boolean isSemitrailer() {
        return false;
    }
}

