/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import players.RaceTrajectory;
import rnrscenario.sctask;

public final class FirstRun
extends sctask {
    FirstRun() {
        super(3, false);
    }

    public void run() {
        RaceTrajectory.createTrajectoryForRaceWithPP();
        this.finish();
    }
}

