/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import java.io.Serializable;
import menu.JavaEvents;
import players.actorveh;
import rnrcore.vectorJ;

public class FreeWayTrace
implements Serializable {
    static final long serialVersionUID = 0L;
    private double FREEWAY_LENGTH = 1200.0;
    double freeway_trace = 500.0;
    double length = 0.0;
    vectorJ pos_start;
    vectorJ pos_end;
    vectorJ pos_trace;
    actorveh player;

    public FreeWayTrace(actorveh player) {
        this.player = player;
        JavaEvents.SendEvent(39, 0, this);
    }

    public boolean isOnFreeWay() {
        return this.FREEWAY_LENGTH < this.length;
    }

    public vectorJ getTracePosition() {
        return this.pos_trace;
    }

    public vectorJ getEndPosition() {
        return this.pos_end;
    }

    public vectorJ getStartPosition() {
        return this.pos_start;
    }
}

