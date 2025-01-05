/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.requirements;

import rnrscenario.missions.requirements.Requirement;

public abstract class ScalarInIntervalRequirement
extends Requirement {
    private double upperBound = 1.0;
    private double lowerBound = 0.0;
    static boolean nativeLibraryExists = true;

    protected ScalarInIntervalRequirement(double lowerBound, double upperBound) {
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }

    abstract double getParameterValue();

    public boolean check() {
        double value = this.getParameterValue();
        return value >= this.lowerBound - 1.0E-5 && value <= this.upperBound + 1.0E-5;
    }
}

