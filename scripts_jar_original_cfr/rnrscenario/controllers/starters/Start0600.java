/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers.starters;

import rnrcore.CoreTime;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.missions.starters.ConditionChecker;
import rnrscenario.missions.starters.StarterBase;
import xmlserialization.nxs.DeserializationConstructor;

@ScenarioClass(scenarioStage=8)
public final class Start0600
extends StarterBase {
    public Start0600() {
        CoreTime currentTime = new CoreTime();
        currentTime.plus_days(2);
        currentTime.sHour(23);
        currentTime.sMinute(59);
        this.time_mission_end = new CoreTime(currentTime);
    }

    public ConditionChecker getConditionChecker(String missionName) {
        return null;
    }

    @ScenarioClass(scenarioStage=8)
    public static class Checker
    extends ConditionChecker {
        public static String getUid() {
            return "Start0600";
        }

        @DeserializationConstructor
        public Checker(String missionName) {
            super(missionName, Checker.getUid());
        }

        public boolean check() {
            return true;
        }
    }
}

