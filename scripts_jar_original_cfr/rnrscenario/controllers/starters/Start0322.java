/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers.starters;

import rnrcore.CoreTime;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.missions.starters.StarterBase;

@ScenarioClass(scenarioStage=2)
public final class Start0322
extends StarterBase {
    public Start0322() {
        CoreTime currentTime = new CoreTime();
        currentTime.plus_days(1);
        this.time_mission_end = new CoreTime(currentTime);
    }
}

