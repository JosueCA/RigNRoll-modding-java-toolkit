/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.starters;

import rnrcore.CoreTime;
import rnrcore.vectorJ;

public class AuxTraceData {
    public vectorJ pos1;
    public vectorJ pos2;
    public int seconds;
    public CoreTime date;

    public AuxTraceData(vectorJ pos1, vectorJ pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.date = new CoreTime();
        this.seconds = 1;
    }
}

