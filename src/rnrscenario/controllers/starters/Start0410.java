/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers.starters;

import rnrcore.CoreTime;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.missions.starters.StarterBase;

@ScenarioClass(scenarioStage=3)
public final class Start0410
extends StarterBase {
    public Start0410() {
        CoreTime currentTime = new CoreTime();
        currentTime.plus_days(3);
        currentTime.sHour(23);
        currentTime.sMinute(59);
        this.time_mission_end = new CoreTime(currentTime);
    }
}

