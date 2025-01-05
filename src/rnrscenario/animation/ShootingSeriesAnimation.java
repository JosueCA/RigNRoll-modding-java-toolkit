/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.animation;

import java.util.Random;

public final class ShootingSeriesAnimation {
    static final int RELAXATION_MAX_DELTA = 2;
    static final int RELAXATION_MIN_TIME = 2;
    static final int MAX_TIME_TO_SHOOT_DELTA = 4;
    static final int MIN_TIME_TO_SHOOT = 2;
    private final Random random = new Random(System.nanoTime());
    private double lastSeriesTimeEnd = 0.0;
    private double currentTime = 0.0;
    private double previousCounterValue = 0.0;
    private double relaxationTime = 0.0;
    private boolean shooting = false;

    public void animate(double externalCounterValue) {
        if (0.0 == this.previousCounterValue) {
            this.startShooting();
            this.previousCounterValue = externalCounterValue;
            return;
        }
        this.currentTime += externalCounterValue - this.previousCounterValue;
        this.previousCounterValue = externalCounterValue;
        if (this.shooting) {
            if (this.currentTime > this.lastSeriesTimeEnd) {
                this.shooting = false;
                this.relaxationTime = this.random.nextDouble() * 2.0 + 2.0;
            }
        } else if (this.currentTime > this.lastSeriesTimeEnd + this.relaxationTime) {
            this.startShooting();
        }
    }

    private void startShooting() {
        this.lastSeriesTimeEnd = this.currentTime + 4.0 * this.random.nextDouble() + 2.0;
        this.shooting = true;
    }

    public boolean isShooting() {
        return this.shooting;
    }
}

