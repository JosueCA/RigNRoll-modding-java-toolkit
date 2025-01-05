/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import players.Chase;
import players.Crew;
import players.actorveh;
import players.aiplayer;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.stage;
import rnrscr.Helper;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=1)
public abstract class sc00060base
extends stage {
    int millisekonds_fora = 6000;
    int millisekonds_fora_short = 10000;
    Object syncMonitor = null;
    actorveh ourcar = null;
    actorveh BanditCar = null;
    trackscripts trs = null;
    aiplayer Bandit1 = null;
    boolean wasPrepared = false;

    public sc00060base(Object monitor, String sceneName) {
        super(monitor, sceneName);
    }

    void prepare() {
        if (!this.wasPrepared) {
            this.ourcar = Crew.getIgrokCar();
            eng.writeLog("SCENARIO RescueDorothy_succeeded");
            this.Bandit1 = aiplayer.getSimpleAiplayer("SC_BANDIT3LOW");
            this.BanditCar = Crew.getMappedCar("ARGOSY BANDIT");
            this.trs = new trackscripts(this.getSyncMonitor());
            eng.doWide(true);
            this.wasPrepared = true;
        }
    }

    void epilogue() {
        if (this.wasPrepared) {
            eng.lock();
            Helper.restoreCameraToIgrokCar();
            this.Bandit1.beDriverOfCar(this.BanditCar);
            this.BanditCar.leaveParking();
            Chase chase = new Chase();
            chase.setParameters("easyChasing");
            chase.makechase(this.BanditCar, this.ourcar);
            this.ourcar.stop_autopilot();
            eng.unlock();
            eng.console("switch Dword_Freight_Argosy_Passanger_Window 1");
            this.runVehicleScene();
        }
    }

    private void runVehicleScene() {
        eng.lock();
        actorveh carBandit = this.BanditCar;
        carBandit.registerCar("banditcar");
        this.ourcar.registerCar("ourcar");
        vectorJ pos = eng.getControlPointPosition("Room_Repair_Oxnard_01");
        assert (pos != null);
        Data data = new Data(new matrixJ(), pos, Crew.getIgrokCar());
        data.M_180 = new matrixJ();
        data.M_180.v0 = new vectorJ(-1.0, 0.0, 0.0);
        data.M_180.v1 = new vectorJ(0.0, -1.0, 0.0);
        eng.unlock();
        this.trs.PlaySceneXMLThreaded("00061", false, data);
        eng.lock();
        eng.doWide(false);
        eng.enableControl();
        this.ourcar.sVeclocity(20.0);
        this.BanditCar.sVeclocity(10.0);
        Helper.restoreCameraToIgrokCar();
        eng.unlock();
    }

    void waitFor(int timemillesec) throws InterruptedException {
        Thread.sleep(timemillesec);
    }

    static class Data {
        matrixJ M;
        matrixJ M_180;
        vectorJ P;
        actorveh car;

        Data() {
        }

        Data(matrixJ M, vectorJ P, actorveh car) {
            this.M = M;
            this.P = P;
            this.car = car;
        }
    }
}

