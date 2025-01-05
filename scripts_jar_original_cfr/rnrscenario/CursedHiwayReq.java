/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import rnrscenario.StaticScenarioStuff;
import rnrscenario.missions.requirements.Requirement;

public class CursedHiwayReq
extends Requirement {
    public boolean check() {
        return StaticScenarioStuff.isReadyCursedHiWay();
    }

    public int getPriorityIncrement() {
        return 0;
    }
}

