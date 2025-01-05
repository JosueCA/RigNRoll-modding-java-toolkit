/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import players.Crew;
import players.actorveh;
import players.semitrailer;
import players.vehicle;
import rnrcore.Collide;
import rnrcore.Helper;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscenario.BigRaceScenario;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.stage;
import rnrscenario.tech.SleepOnTime;
import rnrscr.parkingplace;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=-2)
public final class sc01100
extends stage {
    private static boolean m_debugScene = false;

    public static void setDebugScene() {
        m_debugScene = true;
    }

    public sc01100(Object monitor) {
        super(monitor, "sc01100");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();
        eng.startMangedFadeAnimation();
        eng.unlock();
        rnrscenario.tech.Helper.waitLostSemitrailer(Crew.getIgrokCar());
        eng.lock();
        actorveh car = Crew.getIgrokCar();
        vectorJ P_car = car.gPosition();
        car.makeParking(parkingplace.findParkingByName("John_House_Parking", P_car), 1);
        eng.unlock();
        new SleepOnTime(1000);
        eng.lock();
        matrixJ M_car = car.gMatrix();
        P_car = car.gPosition();
        semitrailer trailer = semitrailer.create("model_Gepard_Trailer", M_car, P_car);
        car.attach(trailer);
        eng.unlock();
        eng.lock();
        matrixJ M = car.gMatrix();
        vectorJ carpos = car.gPosition();
        vectorJ shift = new vectorJ(M.v0);
        vectorJ shiftdir = new vectorJ(M.v1);
        shift.mult(-7.0);
        shiftdir.mult(-7.0);
        carpos.oPlus(shift);
        carpos.oPlus(shiftdir);
        vectorJ pos1 = carpos.oPlusN(new vectorJ(0.0, 0.0, 100.0));
        vectorJ pos2 = carpos.oPlusN(new vectorJ(0.0, 0.0, -100.0));
        vectorJ P = Collide.collidePoint(pos1, pos2);
        if (P == null) {
            P = car.gPositionSaddle();
        }
        eng.unlock();
        class Presets {
            public matrixJ M;
            public vectorJ P;

            Presets(matrixJ M, vectorJ P) {
                this.M = M;
                this.P = P;
                this.getM();
                this.getP();
            }

            public matrixJ getM() {
                return this.M;
            }

            public vectorJ getP() {
                return this.P;
            }
        }
        new trackscripts(this.getSyncMonitor()).PlaySceneXMLThreaded("01100", false, new Presets(M, P));
        eng.lock();
        car.leaveParking();
        eng.enableControl();
        eng.unlock();
        rnrscenario.tech.Helper.waitSimpleState();
        if (!m_debugScene) {
            eng.lock();
            trailer.delete();
            vehicle carlast = car.querryCurrentCar();
            P_car.oPlus(new vectorJ(0.0, -100.0, 0.0));
            vehicle gepard = vehicle.create("GEPARD", 0);
            gepard.setLeased(true);
            vehicle.changeLiveVehicle(car, gepard, M_car, P_car);
            eng.unlock();
            rnrscenario.tech.Helper.waitVehicleChanged();
            eng.lock();
            car.UpdateCar();
            eng.SwitchDriver_in_cabin(car.getCar());
            rnrscr.Helper.restoreCameraToIgrokCar();
            BigRaceScenario.teleport(carlast);
            eng.unlock();
        } else {
            m_debugScene = false;
            eng.lock();
            trailer.delete();
            eng.SwitchDriver_in_cabin(car.getCar());
            rnrscr.Helper.restoreCameraToIgrokCar();
            eng.unlock();
        }
        eng.lock();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}

