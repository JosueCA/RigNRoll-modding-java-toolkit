/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers;

import players.Chase;
import players.Crew;
import players.actorveh;
import players.vehicle;
import rnrcore.ObjectXmlSerializable;
import rnrcore.PiterPanFinalRaceObjectXmlSerializable;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscenario.Helper;
import rnrscenario.Ithreadprocedure;
import rnrscenario.ThreadTask;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.PiterPanRaceHelper;
import rnrscenario.controllers.RACEspace;
import rnrscenario.controllers.RaceFailCondition;
import rnrscenario.controllers.ScenarioController;
import rnrscenario.controllers.ScenarioHost;
import scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage=7)
public class PiterPanFinalrace
extends RACEspace
implements ScenarioController {
    private static final String Piter_Pan_final_race_win = "Piter Pan final race win";
    private static final String Piter_Pan_final_race_loose = "Piter Pan final race loose";
    private static final String Piter_Pan_final_race_call = "Piter Pan final race call";
    private boolean resetPiterPenAiParameters = true;
    private boolean raceFinished = false;
    private String raceName;
    private final ScenarioHost host;
    private final ObjectXmlSerializable serializator;
    private final PiterPanRaceHelper myHelper = new PiterPanRaceHelper(PiterPanFinalrace.class.getAnnotation(ScenarioClass.class).scenarioStage());

    public PiterPanFinalrace(String racename, int tip, ScenarioHost scenarioHost) {
        super(racename, tip, PiterPanFinalrace.class.getAnnotation(ScenarioClass.class).scenarioStage());
        assert (null != scenarioHost) : "'scenarioHost' must be non-null reference";
        ThreadTask.create(new MakeRaceInitialization());
        this.host = scenarioHost;
        this.host.registerController(this);
        this.raceName = racename;
        this.serializator = new PiterPanFinalRaceObjectXmlSerializable(this, this.host);
        this.serializator.registerObjectXmlSerializable();
    }

    public PiterPanFinalrace(String racename, boolean finished, ScenarioHost scenarioHost) throws IllegalStateException {
        super(racename, 3, PiterPanFinalrace.class.getAnnotation(ScenarioClass.class).scenarioStage());
        assert (null != scenarioHost) : "'scenarioHost' must be non-null reference";
        this.raceName = racename;
        this.host = scenarioHost;
        if (finished) {
            throw new IllegalStateException("race saved after finish");
        }
        this.finish_onvip(true);
        this.failDetector = new RaceFailCondition();
        this.placeallOnStart();
        this.start();
        this.serializator = new PiterPanFinalRaceObjectXmlSerializable(this, this.host);
        this.serializator.registerObjectXmlSerializable();
        this.host.registerController(this);
    }

    private void unregisterFromHosts() {
        this.serializator.unRegisterObjectXmlSerializable();
        this.host.unregisterController(this);
        this.finishImmediately();
    }

    public void gameDeinitLaunched() {
        this.unregisterFromHosts();
        this.clearResources();
    }

    public void finish_race_end_scene() {
        super.finish_race_end_scene();
        \u00d1hageCarsBack sceneThread = new \u00d1hageCarsBack(this.m_isBadEnd);
        ThreadTask.create(sceneThread);
    }

    protected void finishRace() {
        if (this.raceFinished) {
            return;
        }
        eng.unblockSO(1024);
        if (0 == (this.states.getStatesucceded() & 0x1000)) {
            this.placeallOnFinish();
        }
        if (1 == this.states.getState(Crew.getIgrokCar()).getPlace() && 0 == (this.states.getStatesucceded() & 0x1000)) {
            EventsControllerHelper.messageEventHappened(Piter_Pan_final_race_win);
        } else if ((this.states.getStatesucceded() & 0x1000) != 0) {
            this.finish_race_end_scene();
            this.clearResources();
            EventsControllerHelper.messageEventHappened(Piter_Pan_final_race_call);
        } else {
            this.placeallOnFinish();
            EventsControllerHelper.messageEventHappened(Piter_Pan_final_race_loose);
        }
        this.unregisterFromHosts();
        this.raceFinished = true;
    }

    public void run() {
        super.run();
        if (this.raceFinished) {
            this.finish();
        } else if (this.resetPiterPenAiParameters && this.makeBanditsChasingPlayer()) {
            this.resetPiterPenAiParameters = false;
        }
    }

    public boolean isRaceFinished() {
        return this.raceFinished;
    }

    public void setRaceFinished(boolean raceFinished) {
        this.raceFinished = raceFinished;
    }

    public String getRaceName() {
        return this.raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    static class \u00d1hageCarsBack
    implements Ithreadprocedure {
        boolean m_playBankrupt = false;

        \u00d1hageCarsBack(boolean playBankrupt) {
            this.m_playBankrupt = playBankrupt;
        }

        public void call() {
            rnrscenario.tech.Helper.waitSimpleState();
            eng.lock();
            actorveh ourcar = Crew.getIgrokCar();
            matrixJ remM = ourcar.gMatrix();
            vectorJ remV = ourcar.gPosition();
            vectorJ shift = new vectorJ(remM.v1);
            shift.mult(30.0);
            remV.oPlus(shift);
            remM.v1.mult(-1.0);
            vehicle our_last_car = Helper.getLiveCarFromGarage();
            vehicle gepard = ourcar.querryCurrentCar();
            vehicle.changeLiveVehicle(ourcar, our_last_car, remM, remV);
            eng.unlock();
            rnrscenario.tech.Helper.waitVehicleChanged();
            eng.lock();
            gepard.delete();
            if (this.m_playBankrupt) {
                EventsControllerHelper.messageEventHappened("bankrupt");
            }
            eng.unlock();
        }

        public void take(ThreadTask safe) {
        }
    }

    class MakeRaceInitialization
    implements Ithreadprocedure {
        MakeRaceInitialization() {
        }

        public void call() {
            eng.lock();
            actorveh banditsCar = PiterPanFinalrace.this.myHelper.createPiterPanCarAndPassangers(PiterPanFinalrace.this.conditions.getStartPosition());
            PiterPanFinalrace.this.addParticipant(banditsCar);
            actorveh player = Crew.getIgrokCar();
            player.deleteSemitrailerIfExists();
            matrixJ remM = player.gMatrix();
            vectorJ remV = player.gPosition();
            vehicle gepard = vehicle.create("GEPARD", 0);
            gepard.setLeased(true);
            vehicle playerLastCar = player.querryCurrentCar();
            vehicle.changeLiveVehicle(player, gepard, remM, remV);
            eng.unlock();
            rnrscenario.tech.Helper.waitVehicleChanged();
            eng.lock();
            Helper.placeLiveCarInGarage(playerLastCar);
            PiterPanFinalrace.this.addParticipant(player);
            PiterPanFinalrace.this.finish_onvip(true);
            PiterPanFinalrace.this.failDetector = new RaceFailCondition();
            PiterPanFinalrace.this.placeallOnStart();
            PiterPanFinalrace.this.start();
            Chase ch = new Chase();
            ch.paramMadracing();
            ch.be_ahead(banditsCar, Crew.getIgrokCar());
            PiterPanFinalrace.this.resetPiterPenAiParameters = false;
            banditsCar.sVeclocity(10.0);
            banditsCar.showOnMap(true);
            eng.unlock();
        }

        public void take(ThreadTask safe) {
        }
    }
}

