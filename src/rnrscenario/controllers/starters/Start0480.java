/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers.starters;

import rnrcore.CoreTime;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.missions.starters.StarterBase;

@ScenarioClass(scenarioStage=6)
public final class Start0480
extends StarterBase {
    private static final int DAYS_FOR_QUEST = 5;

    public Start0480() {
        CoreTime competitionTime = new CoreTime();
        competitionTime.plus_days(5);
        competitionTime.sHour(23);
        competitionTime.sMinute(59);
        int year = competitionTime.gYear();
        int month = competitionTime.gMonth();
        int date = competitionTime.gDate();
        int hour = competitionTime.gHour();
        int minute = competitionTime.gMinute();
        this.time_mission_end = new CoreTime(year, month, date, hour, minute);
    }
}

