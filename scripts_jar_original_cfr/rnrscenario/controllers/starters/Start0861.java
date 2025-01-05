/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers.starters;

import rnrcore.CoreTime;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.missions.starters.StarterBase;

@ScenarioClass(scenarioStage=10)
public final class Start0861
extends StarterBase {
    public Start0861() {
        CoreTime currentTime = new CoreTime();
        currentTime.plus_days(2);
        this.time_mission_end = new CoreTime(currentTime);
    }
}

