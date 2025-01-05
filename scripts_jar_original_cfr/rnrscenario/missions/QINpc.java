/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import rnrorg.MissionEventsMaker;
import rnrscenario.missions.QuestItem;
import scriptEvents.CreateNpcFromQuestItem;
import scriptEvents.EventsControllerHelper;

public class QINpc
extends QuestItem {
    static final long serialVersionUID = 0L;
    public String model;
    public String place;

    public String getPlacement() {
        return this.place;
    }

    public void doPlace(String mission_name) {
        EventsControllerHelper.eventHappened(new CreateNpcFromQuestItem(this, mission_name));
    }

    public void doDealyedPlace(String mission_name, boolean make_place) {
        if (make_place) {
            MissionEventsMaker.createQuestItemPassanger(mission_name, this.model, this.place);
        } else {
            MissionEventsMaker.createQuestItemPassangerNoAnimation(mission_name, this.model, this.place);
        }
    }

    public boolean isSemitrailer() {
        return false;
    }
}

