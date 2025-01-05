/*
 * Decompiled with CFR 0.151.
 */
package players;

import java.util.ArrayList;
import players.CarAnimationController;
import players.CrewNamesManager;
import players.LiveCarCreationController;
import players.MappingCars;
import players.ScenarioCarCreationController;
import players.actorveh;
import players.aiplayer;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Crew {
    private static Crew instance = null;
    private CarAnimationController carCreationController = new CarAnimationController();
    private ScenarioCarCreationController scenarioCarCreationController = new ScenarioCarCreationController();
    private LiveCarCreationController liveCarCreationController = new LiveCarCreationController();
    private MappingCars mappingCars = new MappingCars();

    public static Crew getInstance() {
        if (instance == null) {
            Crew.setInstance(new Crew());
        }
        return instance;
    }

    public static void setInstance(Crew value) {
        instance = value;
    }

    public actorveh addcrewman(int type, int playerId) {
        switch (type) {
            default: {
                return this.carCreationController.onCarCreate(type, playerId);
            }
            case 10: {
                return this.scenarioCarCreationController.onCarCreate(type, playerId);
            }
            case 1: 
        }
        return this.liveCarCreationController.onCarCreate(type, playerId);
    }

    public void removecrewman(int type, int playerId) {
        switch (type) {
            default: {
                this.carCreationController.onCarDelete(type, playerId);
                break;
            }
            case 10: {
                this.scenarioCarCreationController.onCarDelete(type, playerId);
                break;
            }
            case 1: {
                this.liveCarCreationController.onCarDelete(type, playerId);
            }
        }
        if (null != this.mappingCars) {
            this.mappingCars.removeMappedCar(playerId);
        }
    }

    public static aiplayer getIgrok() {
        return CrewNamesManager.getMainCharacterPlayer();
    }

    public static actorveh getIgrokCar() {
        if (null != Crew.getInstance().liveCarCreationController) {
            return Crew.getInstance().liveCarCreationController.getLiveCar();
        }
        return null;
    }

    public static void rotateNonLoadedModels() {
        if (null != Crew.getInstance().carCreationController) {
            Crew.getInstance().carCreationController.renewState();
        }
    }

    public static actorveh getMappedCar(String name) {
        if (null != Crew.getInstance().mappingCars) {
            return Crew.getInstance().mappingCars.getMappedCar(name);
        }
        return null;
    }

    public static void deactivateMappedCar(String name) {
        actorveh car;
        if (null != Crew.getInstance() && null != Crew.getInstance().mappingCars && null != (car = Crew.getInstance().mappingCars.getMappedCar(name))) {
            car.deactivate();
        }
    }

    public static void addMappedCar(String name, actorveh car) {
        if (null != Crew.getInstance().mappingCars) {
            Crew.getInstance().mappingCars.addMappedCar(name, car);
        }
    }

    public static void removeMappedCar(String name) {
        if (null != Crew.getInstance().mappingCars) {
            Crew.getInstance().mappingCars.removeMappedCar(name);
        }
    }

    public ArrayList<String> getPoolNames() {
        if (null != this.carCreationController) {
            return this.carCreationController.getPoolNames();
        }
        return null;
    }

    public CarAnimationController getCarCreationController() {
        return this.carCreationController;
    }

    public void setCarCreationController(CarAnimationController carCreationController) {
        this.carCreationController = carCreationController;
    }

    public LiveCarCreationController getLiveCarCreationController() {
        return this.liveCarCreationController;
    }

    public void setLiveCarCreationController(LiveCarCreationController liveCarCreationController) {
        this.liveCarCreationController = liveCarCreationController;
    }

    public MappingCars getMappingCars() {
        return this.mappingCars;
    }

    public void setMappingCars(MappingCars mappingCars) {
        this.mappingCars = mappingCars;
    }

    public ScenarioCarCreationController getScenarioCarCreationController() {
        return this.scenarioCarCreationController;
    }

    public void setScenarioCarCreationController(ScenarioCarCreationController scenarioCarCreationController) {
        this.scenarioCarCreationController = scenarioCarCreationController;
    }

    public void deinit() {
        if (null != this.carCreationController) {
            this.carCreationController.deinit();
        }
    }
}

