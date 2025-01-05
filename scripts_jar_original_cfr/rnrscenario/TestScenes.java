/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import players.Crew;
import players.actorveh;
import rnrcore.ScenarioSync;
import rnrcore.TypicalAnm;
import rnrcore.eng;
import rnrcore.vectorJ;
import rnrscr.Helper;

public class TestScenes {
    public static void test_30() {
        vectorJ pos = new vectorJ();
        Helper.getNearestGoodPoint(pos);
        actorveh car = eng.CreateCar_onway("PETERBILT_378", pos);
        Crew.addMappedCar("KOH", car);
        TypicalAnm anm2 = new TypicalAnm(){

            public boolean animaterun(double dt) {
                actorveh car = Crew.getMappedCar("KOH");
                car.UpdateCar();
                if (car.getCar() == 0) {
                    return false;
                }
                ScenarioSync.setPlayScene("sc00030");
                return true;
            }
        };
        eng.CreateInfinitScriptAnimation(anm2);
    }
}

