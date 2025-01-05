/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.requirements;

import rnrscenario.missions.requirements.ScalarInIntervalRequirement;

public final class TimeRequirement
extends ScalarInIntervalRequirement {
    static final long serialVersionUID = 0L;
    static double debugTime = 0.5;

    public TimeRequirement(double lowerBound, double upperBound) {
        super(lowerBound, upperBound);
    }

    private native double getTime();

    double getParameterValue() {
        if (nativeLibraryExists) {
            return this.getTime();
        }
        return debugTime;
    }

    public int getPriorityIncrement() {
        return 0;
    }
}

