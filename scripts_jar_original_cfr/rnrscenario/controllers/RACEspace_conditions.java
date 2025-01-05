/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers;

import players.RaceTrajectory;
import rnrcore.vectorJ;
import rnrscenario.consistency.ScenarioClass;

@ScenarioClass(scenarioStage=-1, fieldWithDesiredStage="scenarioStage")
public class RACEspace_conditions {
    private final int scenarioStage;
    public static final int onlast = 1;
    public static final int onvip = 16;
    public static final int onfirst = 256;
    public static final int onforcemojor = 4096;
    private vectorJ cachedPointStartt = new vectorJ();
    private vectorJ cachedPointFinish = new vectorJ();
    private double delta_to_finish = 150.0;
    private int condition = 16;
    private String racename;

    RACEspace_conditions(String _racename, int desiredScenarioStage) {
        this.scenarioStage = desiredScenarioStage;
        this.racename = _racename;
        this.cachedPointStartt = RaceTrajectory.getStart(this.racename);
        this.cachedPointFinish = RaceTrajectory.getFinish(this.racename);
    }

    public String gRaceName() {
        return this.racename;
    }

    public vectorJ getStartPosition() {
        return this.cachedPointStartt;
    }

    public vectorJ getFinishPosition() {
        return this.cachedPointFinish;
    }

    public double checkPosition(vectorJ pos) {
        return this.cachedPointFinish.len2(pos);
    }

    public boolean checkPositionOnFinish(double pos) {
        return pos < this.delta_to_finish * this.delta_to_finish;
    }

    public double getDelta() {
        return this.delta_to_finish;
    }

    public void setcondition(int cond) {
        this.condition = cond;
    }

    public boolean compareConditions(int cond) {
        return (cond & this.condition) != 0;
    }

    public int getCondition() {
        return this.condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }
}

