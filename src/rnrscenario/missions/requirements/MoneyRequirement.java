/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.requirements;

import rnrscenario.missions.requirements.ScalarInIntervalRequirement;

public final class MoneyRequirement
extends ScalarInIntervalRequirement {
    static final long serialVersionUID = 0L;
    static double debugMoney = 0.5;

    public MoneyRequirement(double lowerBound, double upperBound) {
        super(lowerBound, upperBound);
    }

    private native double getBalance();

    double getParameterValue() {
        if (nativeLibraryExists) {
            return this.getBalance();
        }
        return debugMoney;
    }

    public int getPriorityIncrement() {
        return 0;
    }
}

