/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.requirements;

import java.util.HashMap;
import java.util.Map;
import rnrscenario.missions.requirements.Requirement;

public final class MissionAppearConditions {
    private static final int MAP_CAPACITY = 64;
    private Map<String, Requirement> conditions = new HashMap<String, Requirement>(64);

    public void addRequirement(String missionName, Requirement condition) {
        assert (null != condition) : "condition must be non-null reference";
        assert (null != missionName) : "missionName must be non-null reference";
        this.conditions.put(missionName, condition);
    }

    public boolean missionAvalible(String name) {
        assert (null != name) : "name must be non-null reference";
        Requirement condition = this.conditions.get(name);
        return null == condition || condition.check();
    }

    public Requirement getRequirement(String name) {
        if (null != name) {
            return this.conditions.get(name);
        }
        return null;
    }
}

