/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers.starters;

import menu.JavaEvents;
import rnrcore.CoreTime;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.EnemyBase;
import rnrscenario.missions.starters.AuxTraceData;
import rnrscenario.missions.starters.StarterBase;
import rnrscr.Helper;

@ScenarioClass(scenarioStage=1000)
public final class Start1600
extends StarterBase {
    public Start1600() {
        AuxTraceData data = new AuxTraceData(Helper.getCurrentPosition(), EnemyBase.getPOINT_TRUCKSTOP());
        JavaEvents.SendEvent(41, 1, data);
        CoreTime currentTime = data.date;
        int year = currentTime.gYear();
        int month = currentTime.gMonth();
        int date = currentTime.gDate();
        int hour = currentTime.gHour();
        this.time_mission_end = hour > 21 ? new CoreTime(year, month, date + 1, 21, 0) : new CoreTime(year, month, date, 21, 0);
    }
}

