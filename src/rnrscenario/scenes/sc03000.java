/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import menu.JavaEvents;
import menuscript.TotalVictoryMenu;
import menuscript.VictoryMenuExitListener;
import players.CarName;
import players.Crew;
import players.actorveh;
import players.vehicle;
import rnrcore.SCRuniperson;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.traffic;
import rnrcore.vectorJ;
import rnrscenario.Helper;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.stage;
import rnrscenario.tech.SleepOnTime;
import rnrscr.cSpecObjects;
import rnrscr.drvscripts;
import rnrscr.specobjects;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=15)
public final class sc03000
extends stage {
    public static final String SCENE_OBZOR = "03000_obzor";
    public static final String[] SCENES_CHOOSE = new String[]{"03020", "03010", "03000"};
    public static final String[] SCENES_DAWN = new String[]{"03030_2", "03030_3", "03030_1"};
    public static final int MONICA_FINAL = 0;
    public static final int DOROTHY_FINAL = 1;
    public static final int SELF_FINAL = 2;
    public static final String PROC_SCENE = "KeyPoint_FinalScene";
    public static final String POINT_FINAL_SCENE = "FinalScene";
    public static final String POINT_DAWN_START = "DawnStart";
    public static final String POINT_DAWN_END = "DawnEnd";
    boolean has_menu = false;
    int result_menu_close = -1;
    int INDEX = 0;
    private boolean needTakeOffPlayerCar = true;

    protected void debugSetupPrecondition() {
        this.needTakeOffPlayerCar = false;
    }

    protected void debugSetupPostcondition() {
        this.needTakeOffPlayerCar = true;
    }

    public sc03000(Object monitor) {
        super(monitor, "sc03000");
    }

    protected void playSceneBody(cScriptFuncs automat) {
        Data _data;
        DataObzor _data_obzor;
        matrixJ obzorM;
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        eng.lock();
        eng.doWide(true);
        this.INDEX = 2;
        traffic.enterChaseModeSmooth();
        eng.disableControl();
        eng.startMangedFadeAnimation();
        vectorJ pos = eng.getControlPointPosition(POINT_FINAL_SCENE);
        actorveh playerCarGepard = Crew.getIgrokCar();
        playerCarGepard.teleport(pos);
        eng.unlock();
        rnrscenario.tech.Helper.waitTeleport();
        eng.lock();
        playerCarGepard.setHandBreak(true);
        cSpecObjects crossroad = specobjects.getInstance().GetLoadedNamedScenarioObject(PROC_SCENE);
        if (null != crossroad) {
            obzorM = new matrixJ(crossroad.matrix);
            obzorM.v0.mult(-1.0);
            obzorM.v1.mult(-1.0);
            vectorJ pObzor = new vectorJ(crossroad.position);
            vectorJ shift = new vectorJ(crossroad.matrix.v0);
            shift.mult(2.0);
            pObzor.oPlus(shift);
            _data_obzor = new DataObzor(obzorM, crossroad.position, pObzor);
            _data = new Data(crossroad.matrix, crossroad.position);
        } else {
            _data = new Data(new matrixJ(), new vectorJ());
            obzorM = new matrixJ();
            obzorM.v0.mult(-1.0);
            obzorM.v1.mult(-1.0);
            _data_obzor = new DataObzor(obzorM, new vectorJ(), new vectorJ());
        }
        eng.SwitchDriver_outside_cabin(playerCarGepard.getCar());
        SCRuniperson personMC = Crew.getIgrok().getModel();
        personMC.SetPosition(_data_obzor.P.oPlusN(new vectorJ(0.0, 0.0, -100.0)));
        personMC.stop();
        vectorJ p_forward = new vectorJ(_data.M.v1);
        vectorJ p_side = new vectorJ(_data.M.v0);
        p_forward.mult(100.0);
        p_side.mult(100.0);
        actorveh car_DOROTHY = eng.CreateCarForScenario(CarName.CAR_DOROTHY, _data.M, _data.P.oPlusN(p_forward).oPlusN(p_side));
        actorveh car_MONICA = eng.CreateCarForScenario(CarName.CAR_MONICA, _data.M, _data.P.oPlusN(p_forward));
        this.placeCars(car_DOROTHY, car_MONICA, playerCarGepard, _data.P, _data.M);
        eng.doWide(false);
        eng.unlock();
        trs.PlaySceneXMLThreaded(SCENE_OBZOR, false, _data_obzor);
        trs.PlaySceneXMLThreaded(SCENES_CHOOSE[this.INDEX], false, _data);
        eng.startMangedFadeAnimation();
        eng.enableControl();
        rnrscenario.tech.Helper.waitSimpleState();
        eng.disableControl();
        rnrscr.Helper.restoreCameraToIgrokCar();
        vectorJ pos_dawn = eng.getControlPointPosition(POINT_DAWN_START);
        pos_dawn.x -= 100.0;
        pos_dawn.y += 8.0;
        playerCarGepard.teleport(pos_dawn);
        rnrscenario.tech.Helper.waitTeleport();
        eng.lock();
        eng.doWide(true);
        playerCarGepard.sPosition(pos_dawn);
        matrixJ Mscene = new matrixJ();
        Mscene.v0 = new vectorJ(0.0, 1.0, 0.0);
        Mscene.v1 = new vectorJ(-1.0, 0.0, 0.0);
        _data = new Data(Mscene, pos_dawn);
        playerCarGepard.UpdateCar();
        playerCarGepard.registerCar("ourcar");
        eng.unlock();
        trs.PlaySceneXMLThreaded(SCENES_DAWN[this.INDEX], false, _data);
        eng.lock();
        boolean hasContest = rnrcore.Helper.hasContest();
        if (!hasContest) {
            this.has_menu = true;
            TotalVictoryMenu.createGameOverTotal(new VictoryMenuExitListener(){

                public void OnMenuExit(int result) {
                    sc03000.this.result_menu_close = result;
                }
            });
        }
        eng.unlock();
        car_DOROTHY.deactivate();
        car_MONICA.deactivate();
        if (this.has_menu) {
            while (true) {
                new SleepOnTime(100);
                eng.lock();
                if (-1 != this.result_menu_close) break;
                eng.unlock();
            }
            eng.unlock();
            switch (this.result_menu_close) {
                case 0: {
                    this.teleportToOfficeAndTakeOffGepard();
                    break;
                }
                case 1: {
                    eng.lock();
                    JavaEvents.SendEvent(23, 1, this);
                    eng.unlock();
                }
            }
        } else {
            this.teleportToOfficeAndTakeOffGepard();
        }
        eng.lock();
        eng.doWide(false);
        traffic.setTrafficMode(0);
        eng.enableControl();
        rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    private void teleportToOfficeAndTakeOffGepard() {
        eng.startMangedFadeAnimation();
        rnrscr.Helper.restoreCameraToIgrokCar();
        actorveh ourcar = Crew.getIgrokCar();
        if (this.needTakeOffPlayerCar) {
            matrixJ M = ourcar.gMatrix();
            vectorJ P = ourcar.gPosition();
            vehicle currentCar = ourcar.querryCurrentCar();
            vehicle lastVehicleFromGarage = Helper.getLiveCarFromGarage();
            vehicle.changeLiveVehicle(ourcar, lastVehicleFromGarage, M, P);
            rnrscenario.tech.Helper.waitVehicleChanged();
            currentCar.delete();
        }
        vectorJ ourOfficePosition = eng.getNamedOfficePosition("office_OV_01");
        ourcar.teleport(ourOfficePosition);
        rnrscenario.tech.Helper.waitTeleport();
        drvscripts.playInsideCarFast(Crew.getIgrok(), ourcar);
        eng.SwitchDriver_in_cabin(ourcar.getCar());
    }

    private void placeCars(actorveh car_kenworth, actorveh car_dodge, actorveh car_our, vectorJ P, matrixJ M) {
        vectorJ shiftY = new vectorJ(M.v1);
        shiftY.mult(1.0);
        shiftY.norm();
        shiftY.mult(11.5);
        vectorJ shiftYkenworth = new vectorJ(M.v1);
        shiftYkenworth.mult(1.0);
        shiftYkenworth.norm();
        shiftYkenworth.mult(13.5);
        vectorJ shiftY_our = new vectorJ(M.v1);
        shiftY_our.mult(1.0);
        shiftY_our.norm();
        shiftY_our.mult(11.5);
        vectorJ shiftX = new vectorJ(M.v0);
        shiftX.norm();
        vectorJ shiftX_kenworth = new vectorJ(shiftX);
        shiftX_kenworth.mult(4.0);
        vectorJ shiftX_dodge = new vectorJ(shiftX);
        shiftX_dodge.mult(-1.5);
        vectorJ shiftX_our = new vectorJ(shiftX);
        shiftX_our.mult(8.5);
        vectorJ pos_kenworth = new vectorJ(P);
        vectorJ pos_dodge = new vectorJ(P);
        vectorJ pos_our = new vectorJ(P);
        pos_kenworth.oPlus(shiftYkenworth);
        pos_kenworth.oPlus(shiftX_kenworth);
        pos_dodge.oPlus(shiftY);
        pos_dodge.oPlus(shiftX_dodge);
        pos_our.oPlus(shiftY_our);
        pos_our.oPlus(shiftX_our);
        matrixJ M_180 = new matrixJ(M);
        M_180.v0.mult(-1.0);
        M_180.v1.mult(-1.0);
        car_kenworth.sPosition(pos_kenworth, M_180);
        car_dodge.sPosition(pos_dodge, M_180);
        car_our.sPosition(pos_our, M_180);
    }

    static class DataObzor {
        vectorJ P;
        vectorJ Pshift;
        matrixJ M;
        matrixJ M_180;

        DataObzor(matrixJ M, vectorJ P, vectorJ Pshift) {
            this.P = P;
            this.Pshift = Pshift;
            this.M = M;
            this.M_180 = new matrixJ(M);
            this.M_180.v0.mult(-1.0);
            this.M_180.v1.mult(-1.0);
        }
    }

    static class Data {
        vectorJ P;
        matrixJ M;
        matrixJ M_180;

        Data(matrixJ M, vectorJ P) {
            this.P = P;
            this.M = M;
            this.M_180 = new matrixJ(M);
            this.M_180.v0.mult(-1.0);
            this.M_180.v1.mult(-1.0);
        }
    }
}

