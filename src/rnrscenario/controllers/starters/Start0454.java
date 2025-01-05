/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers.starters;

import rnrcore.CoreTime;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.missions.starters.ConditionChecker;
import rnrscenario.missions.starters.StarterBase;
import xmlserialization.nxs.DeserializationConstructor;
import xmlserialization.nxs.LoadFrom;
import xmlserialization.nxs.SaveTo;

@ScenarioClass(scenarioStage=4)
public final class Start0454
extends StarterBase {
    private CoreTime timeOfStart = new CoreTime();

    public Start0454() {
        CoreTime currentTime = new CoreTime();
        currentTime.plus_days(3);
        currentTime.sHour(23);
        currentTime.sMinute(59);
        this.time_mission_end = new CoreTime(currentTime);
    }

    public ConditionChecker getConditionChecker(String missionName) {
        return new Checker(this.timeOfStart, missionName);
    }

    @ScenarioClass(scenarioStage=4)
    public static class Checker
    extends ConditionChecker {
        @SaveTo(destinationNodeName="start_time")
        @LoadFrom(sourceNodeName="start_time")
        CoreTime startTime;

        public CoreTime getStartTime() {
            return new CoreTime(this.startTime);
        }

        public static String getUid() {
            return "Start0454";
        }

        @DeserializationConstructor
        public Checker(String missionName) {
            super(missionName, Checker.getUid());
        }

        public Checker(CoreTime startTime, String missionName) {
            super(missionName, Checker.getUid());
            this.startTime = startTime;
        }

        public boolean check() {
            CoreTime currentTime = new CoreTime();
            if (currentTime.gDate() == this.startTime.gDate()) {
                return false;
            }
            int currentHour = currentTime.gHour();
            return currentHour >= 16 && currentHour < 24;
        }
    }
}

