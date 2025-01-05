/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.requirements;

import rnrscenario.missions.requirements.Requirement;

public class CursedHiwayReq
extends Requirement {
    static final long serialVersionUID = 0L;
    rnrscenario.CursedHiwayReq req = new rnrscenario.CursedHiwayReq();

    public boolean check() {
        return this.req.check();
    }

    public int getPriorityIncrement() {
        return this.req.getPriorityIncrement();
    }
}

