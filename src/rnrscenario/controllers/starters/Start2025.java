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

@ScenarioClass(scenarioStage=14)
public final class Start2025
extends StarterBase {
    public Start2025() {
        AuxTraceData data = new AuxTraceData(Helper.getCurrentPosition(), EnemyBase.getPOINT_TRUCKSTOP());
        JavaEvents.SendEvent(41, 1, data);
        CoreTime timeToComplitionPoint = data.date;
        CoreTime currentTime = new CoreTime();
        if (timeToComplitionPoint.moreThanOnTime(currentTime, new CoreTime(0, 0, 0, 0, 30)) <= 0) {
            timeToComplitionPoint.plus(new CoreTime(0, 0, 0, 0, 30));
        }
        this.time_mission_end = new CoreTime(timeToComplitionPoint);
    }
}

