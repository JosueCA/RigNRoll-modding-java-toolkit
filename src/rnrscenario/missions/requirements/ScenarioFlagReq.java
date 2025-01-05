/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.requirements;

import rnrscenario.ScenarioFlagsManager;
import rnrscenario.missions.requirements.Requirement;

public class ScenarioFlagReq
extends Requirement {
    private String name = "undefined";

    public ScenarioFlagReq(String s) {
        this.name = s;
    }

    public boolean check() {
        return ScenarioFlagsManager.getInstance().getFlagValue(this.name);
    }

    public int getPriorityIncrement() {
        return 0;
    }
}

