/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers;

import players.CarName;
import players.Crew;
import players.actorveh;
import players.aiplayer;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscenario.Helper;
import rnrscenario.consistency.ScenarioClass;

@ScenarioClass(scenarioStage=-1, fieldWithDesiredStage="myScenarioStage")
public final class PiterPanRaceHelper {
    private int myScenarioStage;

    PiterPanRaceHelper(int stage2) {
        this.myScenarioStage = stage2;
    }

    public actorveh createPiterPanCarAndPassangers(vectorJ raceStartPosition) {
        aiplayer bandit = aiplayer.getSimpleAiplayer("SC_BANDIT3LOW");
        aiplayer PiterPan = aiplayer.getSimpleAiplayer("SC_PITERPANLOW");
        actorveh banditsCar = eng.CreateCarForScenario(CarName.CAR_PITER_PAN, new matrixJ(), raceStartPosition);
        Crew.addMappedCar("ARGOSY BANDIT", banditsCar);
        bandit.bePassangerOfCar(banditsCar);
        PiterPan.beDriverOfCar(banditsCar);
        Helper.makePowerEngine(banditsCar);
        return banditsCar;
    }
}

