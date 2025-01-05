/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers;

import players.Chase;
import players.Crew;
import players.actorveh;
import rnrcore.ObjectXmlSerializable;
import rnrcore.PiterPanDoomedRaceObjectXmlSerializable;
import rnrcore.cameratrack;
import rnrcore.eng;
import rnrcore.vectorJ;
import rnrscenario.Ithreadprocedure;
import rnrscenario.ThreadTask;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.PiterPanRaceHelper;
import rnrscenario.controllers.RACEspace;
import rnrscenario.controllers.RaceFailCondition;
import rnrscenario.controllers.ScenarioController;
import rnrscenario.controllers.ScenarioHost;
import rnrscenario.tech.Helper;
import rnrscr.Office;
import scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage=5)
public class PiterPandoomedrace
extends RACEspace
implements ScenarioController {
    private static final String Piter_Pan_doomed_race_call = "Piter Pan doomed race call";
    private static final String Piter_Pan_doomed_race_finished = "Piter Pan doomed race finished";
    private static final String Piter_Pan_doomed_race_win = "Piter Pan doomed race win";
    public static final String PLAYER_LOOSE_CAR_EVENT = "Player loose car";
    public static final String PLAYER_LOOSE_CAR_METHOD = "playerLooseCar";
    private static final double banditCarInitialVelocity = 5.0;
    private String comment;
    private boolean resetPiterPenAiParameters = true;
    private boolean raceFinished = false;
    private String raceName;
    private final PiterPanRaceHelper myHelper = new PiterPanRaceHelper(PiterPandoomedrace.class.getAnnotation(ScenarioClass.class).scenarioStage());
    private final ScenarioHost host;
    private ObjectXmlSerializable serializator = null;

    public PiterPandoomedrace(String racename, int type, ScenarioHost scenarioHost) {
        super(racename, type, PiterPandoomedrace.class.getAnnotation(ScenarioClass.class).scenarioStage());
        assert (null != scenarioHost) : "'scenarioHost' must be non-null reference";
        this.raceName = racename;
        this.host = scenarioHost;
        this.host.registerController(this);
        actorveh banditsCar = this.myHelper.createPiterPanCarAndPassangers(this.conditions.getStartPosition());
        actorveh ourCar = Crew.getIgrokCar();
        this.addParticipant(banditsCar);
        this.addParticipant(ourCar);
        this.finish_onvip(true);
        this.failDetector = new RaceFailCondition();
        this.placeallOnStart();
        this.start();
        Chase chase = new Chase();
        chase.paramModerateChasing();
        chase.be_ahead(banditsCar, ourCar);
        this.resetPiterPenAiParameters = false;
        banditsCar.sVeclocity(5.0);
        banditsCar.showOnMap(true);
        this.serializator = new PiterPanDoomedRaceObjectXmlSerializable(this, this.host);
        this.serializator.registerObjectXmlSerializable();
        EventsControllerHelper.getInstance().addMessageListener(this, PLAYER_LOOSE_CAR_METHOD, PLAYER_LOOSE_CAR_EVENT);
    }

    public PiterPandoomedrace(String racename, boolean finished, ScenarioHost scenarioHost) throws IllegalStateException {
        super(racename, 3, PiterPandoomedrace.class.getAnnotation(ScenarioClass.class).scenarioStage());
        assert (null != scenarioHost) : "'scenarioHost' must be non-null reference";
        this.host = scenarioHost;
        this.host.registerController(this);
        if (finished) {
            throw new IllegalStateException("Illegal data was saved because of errors in ObjectXmlSerializator with map");
        }
        this.raceFinished = finished;
        this.raceName = racename;
        this.finish_onvip(true);
        this.failDetector = new RaceFailCondition();
        this.placeallOnStart();
        this.start();
        this.serializator = new PiterPanDoomedRaceObjectXmlSerializable(this, this.host);
        EventsControllerHelper.getInstance().addMessageListener(this, PLAYER_LOOSE_CAR_METHOD, PLAYER_LOOSE_CAR_EVENT);
        this.serializator.registerObjectXmlSerializable();
    }

    public void playerLooseCar() {
        EventsControllerHelper.getInstance().removeMessageListener(this, PLAYER_LOOSE_CAR_METHOD, PLAYER_LOOSE_CAR_EVENT);
        for (actorveh car : this.states.getParticipants()) {
            car.leave_target();
        }
        ThreadTask.create(new Ithreadprocedure(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            public void call() {
                try {
                    eng.waitUntilEngineCanPlayScene();
                    eng.lock();
                    eng.startMangedFadeAnimation();
                    actorveh playerCar = Crew.getIgrokCar();
                    assert (playerCar != null);
                    playerCar.detachSemitrailer();
                    vectorJ pos_car = playerCar.gPosition();
                    assert (pos_car != null);
                    eng.SwitchDriver_in_cabin(playerCar.getCar());
                    Crew.getIgrokCar().UpdateCar();
                    cameratrack.AttachCameraToCar(Crew.getIgrokCar().getCar());
                    vectorJ officePosition = eng.getNamedOfficePosition("office_OV_01");
                    playerCar.teleport(officePosition);
                    eng.unlock();
                    Helper.waitTeleport();
                    eng.predefinedAnimationLaunchedFromJava(false);
                    eng.waitUntilEngineCanPlayScene();
                    eng.lock();
                    playerCar.takeoff_currentcar().delete();
                    Office.teleport();
                    eng.unlock();
                    eng.predefinedAnimationLaunchedFromJava(false);
                }
                catch (InterruptedException e) {
                    System.err.print(e.getMessage());
                    e.printStackTrace(System.err);
                }
                finally {
                    eng.predefinedAnimationLaunchedFromJava(false);
                }
            }

            public void take(ThreadTask safe) {
            }
        });
    }

    private void unregisterFromHosts() {
        this.serializator.unRegisterObjectXmlSerializable();
        this.host.unregisterController(this);
        this.finishImmediately();
    }

    public void gameDeinitLaunched() {
        EventsControllerHelper.getInstance().removeMessageListener(this, PLAYER_LOOSE_CAR_METHOD, PLAYER_LOOSE_CAR_EVENT);
        this.unregisterFromHosts();
        this.clearResources();
    }

    protected void finishRace() {
        if (this.raceFinished) {
            return;
        }
        this.unregisterFromHosts();
        this.raceFinished = true;
        if (this.states.getState(Crew.getIgrokCar()).getPlace() == 1) {
            this.comment = "We won!";
            this.placeallOnFinish();
            EventsControllerHelper.getInstance().removeMessageListener(this, PLAYER_LOOSE_CAR_METHOD, PLAYER_LOOSE_CAR_EVENT);
            EventsControllerHelper.messageEventHappened(Piter_Pan_doomed_race_win);
        } else {
            this.comment = "We lost...";
            if (0 != (this.states.getStatesucceded() & 0x1000)) {
                this.clearResources();
                EventsControllerHelper.messageEventHappened(Piter_Pan_doomed_race_call);
            } else {
                this.placeAllOnFinishIfPlayerNotFinished();
                EventsControllerHelper.messageEventHappened(Piter_Pan_doomed_race_finished);
            }
        }
    }

    public void run() {
        super.run();
        if (this.raceFinished) {
            eng.pager("Race Finished\n" + this.comment);
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
}

