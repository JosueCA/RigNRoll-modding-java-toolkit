/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.requirements;

import rnrscenario.missions.requirements.ScalarInIntervalRequirement;

public final class RankRequirement
extends ScalarInIntervalRequirement {
    static final long serialVersionUID = 0L;
    static double debugRank = 0.5;

    public RankRequirement(double lowerBound, double upperBound) {
        super(lowerBound, upperBound);
    }

    private native double getRank();

    double getParameterValue() {
        if (nativeLibraryExists) {
            return this.getRank();
        }
        return debugRank;
    }

    public int getPriorityIncrement() {
        return 0;
    }
}

