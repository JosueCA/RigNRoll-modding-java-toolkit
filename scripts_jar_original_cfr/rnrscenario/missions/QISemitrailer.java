/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import rnrorg.MissionEventsMaker;
import rnrscenario.missions.MissionPlacement;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.QuestItem;

public class QISemitrailer
extends QuestItem {
    static final long serialVersionUID = 0L;
    public String model;
    public String place;

    public String getPlacement() {
        return this.place;
    }

    public void doPlace(String mission_name) {
        MissionPlacement mp = MissionSystemInitializer.getMissionsManager().getMissionPlacement(mission_name);
        MissionEventsMaker.createQuestItemSemitrailer(mission_name, this.model, this.place);
    }

    public boolean isSemitrailer() {
        return true;
    }
}

