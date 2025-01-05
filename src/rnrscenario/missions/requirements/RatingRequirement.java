/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.requirements;

import rnrrating.RateSystem;
import rnrscenario.missions.requirements.ScalarInIntervalRequirement;

public final class RatingRequirement
extends ScalarInIntervalRequirement {
    static final long serialVersionUID = 0L;
    static double debugRating = 0.5;

    public RatingRequirement(double lowerBound, double upperBound) {
        super(lowerBound, upperBound);
    }

    double getParameterValue() {
        if (nativeLibraryExists) {
            return RateSystem.gNormalLiveRating();
        }
        return debugRating;
    }

    public int getPriorityIncrement() {
        return 1000;
    }
}

