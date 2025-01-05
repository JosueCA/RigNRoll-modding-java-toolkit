/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers;

import rnrscenario.consistency.ScenarioClass;

@ScenarioClass(scenarioStage=-1, fieldWithDesiredStage="scenarioStage")
public class RACErace_state_single
implements Comparable {
    private final int scenarioStage;
    private boolean finished = false;
    private int place = 0;
    private double distance = 0.0;

    public RACErace_state_single(int scenarioStage) {
        this.scenarioStage = scenarioStage;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getPlace() {
        return this.place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int compareTo(Object arg0) {
        RACErace_state_single data = (RACErace_state_single)arg0;
        if (this.finished && data.finished) {
            return 0;
        }
        if (this.finished) {
            return -1;
        }
        if (data.finished) {
            return 1;
        }
        return (int)(this.distance - data.distance);
    }
}

