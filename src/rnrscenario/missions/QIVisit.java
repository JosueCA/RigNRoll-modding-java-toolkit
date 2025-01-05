/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import java.util.List;
import rnrorg.MissionEventsMaker;
import rnrscenario.missions.QuestItem;
import scriptEvents.EventListener;
import scriptEvents.MissionStartedOnPoint;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class QIVisit
extends QuestItem
implements EventListener {
    static final long serialVersionUID = 0L;
    private String mission_name;
    private String point_name;
    public String model;
    public String place;

    @Override
    public String getPlacement() {
        return this.point_name;
    }

    @Override
    public void doPlace(String mission_name) {
        this.mission_name = mission_name;
        MissionEventsMaker.createQuestItemVisit(mission_name, this.model, this.point_name == null ? "" : this.point_name);
    }

    // @Override
    public void eventHappened(List<ScriptEvent> eventTuple) {
        for (ScriptEvent event2 : eventTuple) {
            MissionStartedOnPoint mission_event;
            if (!(event2 instanceof MissionStartedOnPoint) || !(mission_event = (MissionStartedOnPoint)event2).isMission(this.mission_name)) continue;
            this.point_name = mission_event.getPointName();
            MissionEventsMaker.createQuestItemVisit(this.mission_name, this.model, this.point_name);
        }
    }

    @Override
    public boolean isSemitrailer() {
        return false;
    }
}

