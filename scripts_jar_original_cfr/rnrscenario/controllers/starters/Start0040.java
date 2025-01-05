/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers.starters;

import menu.JavaEvents;
import rnrcore.CoreTime;
import rnrcore.eng;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.missions.starters.AuxTraceData;
import rnrscenario.missions.starters.StarterBase;
import rnrscr.Helper;

@ScenarioClass(scenarioStage=1)
public final class Start0040
extends StarterBase {
    public Start0040() {
        AuxTraceData data = new AuxTraceData(Helper.getCurrentPosition(), eng.getControlPointPosition("Oxnard_Bar_01"));
        JavaEvents.SendEvent(41, 1, data);
        CoreTime competitionTime = data.date;
        CoreTime currentTime = new CoreTime();
        if (competitionTime.moreThanOnTime(currentTime, new CoreTime(0, 0, 0, 0, 30)) <= 0) {
            competitionTime.plus(new CoreTime(0, 0, 0, 0, 30));
        }
        int year = competitionTime.gYear();
        int month = competitionTime.gMonth();
        int date = competitionTime.gDate();
        int hour = competitionTime.gHour();
        int minute = competitionTime.gMinute();
        this.time_mission_end = new CoreTime(year, month, date, hour, minute);
    }
}

