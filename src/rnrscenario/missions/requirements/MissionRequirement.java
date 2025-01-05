/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.requirements;

import rnrscenario.missions.requirements.Requirement;

public abstract class MissionRequirement
extends Requirement {
    private String mission = null;

    protected MissionRequirement(String name) {
        assert (null != name) : "mission must be non-null reference";
        this.mission = name;
    }

    String missionName() {
        return this.mission;
    }

    public int getPriorityIncrement() {
        return 0;
    }
}

