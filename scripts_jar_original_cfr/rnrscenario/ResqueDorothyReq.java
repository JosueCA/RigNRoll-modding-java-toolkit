/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import rnrscenario.StaticScenarioStuff;
import rnrscenario.missions.requirements.Requirement;

public class ResqueDorothyReq
extends Requirement {
    public boolean check() {
        return StaticScenarioStuff.isReadyPreparesc00060();
    }

    public int getPriorityIncrement() {
        return 0;
    }
}

