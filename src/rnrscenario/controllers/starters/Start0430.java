/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers.starters;

import rnrcore.CoreTime;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.missions.starters.ConditionChecker;
import rnrscenario.missions.starters.StarterBase;
import xmlserialization.nxs.DeserializationConstructor;

@ScenarioClass(scenarioStage=4)
public final class Start0430
extends StarterBase {
    public Start0430() {
        CoreTime currentTime = new CoreTime();
        currentTime.plus_days(3);
        currentTime.sHour(23);
        currentTime.sMinute(59);
        this.time_mission_end = new CoreTime(currentTime);
    }

    public ConditionChecker getConditionChecker(String missionName) {
        return new Checker(missionName);
    }

    public static class Checker
    extends ConditionChecker {
        public static String getUid() {
            return "Start0430";
        }

        @DeserializationConstructor
        public Checker(String missionName) {
            super(missionName, Checker.getUid());
        }

        public boolean check() {
            CoreTime currentTime = new CoreTime();
            int currentHour = currentTime.gHour();
            return currentHour >= 16 && currentHour < 24;
        }
    }
}

