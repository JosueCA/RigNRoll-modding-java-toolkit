/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import players.actorveh;
import players.vehicle;
import rnrscenario.scenarioscript;
import scriptActions.SingleStepScenarioAdvanceAction;
import scriptActions.StartScenarioBranchAction;

public class Helper {
    private static final String MC_VEHICLE_CACHE_NAME = "lastcar";

    public static void start_quest(String quest_name) {
        StartScenarioBranchAction action = new StartScenarioBranchAction(quest_name, scenarioscript.script.getScenarioMachine());
        action.act();
    }

    public static void phase_quest(String quest_name, int state) {
        SingleStepScenarioAdvanceAction action = new SingleStepScenarioAdvanceAction(quest_name, state, scenarioscript.script.getScenarioMachine());
        action.act();
    }

    public static void makePowerEngine(actorveh car) {
        assert (car != null);
        car.changeEngine("BANDITS_ENGINE");
    }

    public static void placeLiveCarInGarage(vehicle car) {
        vehicle.cacheVehicleWithName(car, MC_VEHICLE_CACHE_NAME);
        car.placeInGarage();
    }

    public static vehicle getLiveCarFromGarage() {
        vehicle lastCar = vehicle.getCacheVehicleByName(MC_VEHICLE_CACHE_NAME);
        assert (lastCar != null);
        lastCar.placeToWorldFromGarage();
        return lastCar;
    }
}

