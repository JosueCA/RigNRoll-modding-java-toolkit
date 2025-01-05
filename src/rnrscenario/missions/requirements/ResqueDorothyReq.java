/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.requirements;

import rnrscenario.missions.requirements.Requirement;

public class ResqueDorothyReq
extends Requirement {
    static final long serialVersionUID = 0L;
    rnrscenario.ResqueDorothyReq req = new rnrscenario.ResqueDorothyReq();

    public boolean check() {
        return this.req.check();
    }

    public int getPriorityIncrement() {
        return 0;
    }
}

