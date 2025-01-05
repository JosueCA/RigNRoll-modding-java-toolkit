/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import players.Crew;
import players.actorveh;
import rnrcore.Helper;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.EnemyBase;
import rnrscenario.stage;
import rnrscr.parkingplace;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=14)
public final class sc02025
extends stage {
    public sc02025(Object monitor) {
        super(monitor, "sc02025");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();
        actorveh car = Crew.getIgrokCar();
        vectorJ pos = EnemyBase.getPOINT_TRUCKSTOP();
        parkingplace place = parkingplace.findParkingByName("pk_BR_MD_01", pos);
        car.makeParking(place, 5);
        eng.SwitchDriver_outside_cabin(car.getCar());
        vectorJ shift = new vectorJ(car.gDir());
        shift.mult(10.0);
        vectorJ newPos = shift.oPlusN(car.gPosition());
        class Data {
            public vectorJ P;
            public matrixJ M;

            Data(vectorJ P, matrixJ M) {
                this.P = P;
                this.M = M;
            }
        }
        Data _data = new Data(newPos, car.gMatrix());
        eng.unlock();
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        trs.PlaySceneXMLThreaded("02025", false, _data);
        rnrscenario.tech.Helper.makeComeInAndLeaveParkingFast();
        eng.lock();
        eng.enableControl();
        eng.unlock();
        rnrscenario.tech.Helper.waitSimpleState();
        if (null != EnemyBase.getInstance()) {
            EnemyBase.getInstance().met_assault_team();
        }
        eng.lock();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}

