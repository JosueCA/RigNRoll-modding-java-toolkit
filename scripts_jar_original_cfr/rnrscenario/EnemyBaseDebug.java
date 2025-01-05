/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import players.Crew;
import players.actorveh;
import players.vehicle;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscenario.Helper;
import rnrscenario.controllers.EnemyBase;
import scriptEvents.EventsControllerHelper;

public class EnemyBaseDebug {
    public static void assault() {
        EventsControllerHelper.messageEventHappened("start_1600");
        Helper.start_quest("enemybase_blowcar");
        Helper.start_quest("2015_enemybase");
        Helper.phase_quest("2015_enemybase", 6);
        vectorJ pos = EnemyBase.getPOINT_LINEUP();
        for (int i = 0; i < EnemyBase.CAR_NAMES_ASSAULT.length; ++i) {
            pos.x -= 4.0;
            if (i == 0) {
                vectorJ ipos = rnrscr.Helper.getCurrentPosition();
                ipos.x += 100.0;
                ipos.y += 100.0;
                EnemyBase.getInstance().cars_assault[i] = eng.CreateCarForScenario(EnemyBase.CAR_NAMES_ASSAULT[i], new matrixJ(), ipos);
                vectorJ pos_1 = EnemyBase.getInstance().cars_assault[i].gPosition();
                matrixJ mat_1 = EnemyBase.getInstance().cars_assault[i].gMatrix();
                vehicle gepard = EnemyBase.getInstance().cars_assault[i].takeoff_currentcar();
                gepard.setLeased(true);
                actorveh ourcar = Crew.getIgrokCar();
                vehicle last_vehicle = ourcar.querryCurrentCar();
                vehicle.changeLiveVehicle(ourcar, gepard, mat_1, pos_1);
                Helper.placeLiveCarInGarage(last_vehicle);
                continue;
            }
            EnemyBase.getInstance().cars_assault[i] = eng.CreateCarForScenario(EnemyBase.CAR_NAMES_ASSAULT[i], new matrixJ(), pos);
            if (i == 2) {
                EnemyBase.getInstance().cars_assault[i].registerCar("DAKOTA");
                continue;
            }
            if (i != 1) continue;
            EnemyBase.getInstance().cars_assault[i].registerCar("JOHN");
        }
        EnemyBase.getInstance().cars_assault[0].deactivate();
        eng.setdword("DWORD_EnemyBaseAssaultTeam", 0);
        eng.setdword("DWORD_EnemyBaseAssault", 1);
        eng.setdword("DWORD_EnemyBase", 1);
    }
}

