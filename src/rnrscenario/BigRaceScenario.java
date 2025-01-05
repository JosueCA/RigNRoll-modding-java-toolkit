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
import rnrrating.BigRace;
import rnrrating.IBigRaceEventsListener;
import rnrscenario.Helper;
import scriptEvents.EventsControllerHelper;

public class BigRaceScenario
implements IBigRaceEventsListener {
    public static final int DAYS = 5;
    public static final String MESSAGE_WIN_RACE = "BigRace Quest Win";
    public static final String MESSAGE_LOOSE_RACE = "BigRace Quest Loose";
    private int race_uid = -1;
    private static BigRaceScenario instance = null;

    public BigRaceScenario() {
        instance = this;
        this.start();
    }

    private void start() {
        this.race_uid = BigRace.sceduleScenarioRace(5);
        if (this.race_uid == -1) {
            eng.err("ERRORR. Cannot schedule scenario race");
            return;
        }
        BigRace.setSemitailerForRaceForLivePlayer(this.race_uid, "model_Gepard_Trailer", 0);
        BigRace.addListener(this);
    }

    public static void teleport(vehicle last_car) {
        Helper.placeLiveCarInGarage(last_car);
        if (null != instance) {
            BigRace.teleportOnStart(BigRaceScenario.instance.race_uid);
        }
    }

    public static void finishRace() {
        if (null != instance) {
            actorveh ourcar = Crew.getIgrokCar();
            matrixJ remM = ourcar.gMatrix();
            vectorJ remV = ourcar.gPosition();
            vehicle lastCar = Helper.getLiveCarFromGarage();
            vehicle.changeLiveVehicle(ourcar, lastCar, remM, remV);
        }
    }

    public static void init() {
        BigRaceScenario.deinit();
    }

    public static void deinit() {
        if (null != instance) {
            BigRace.removeListener(instance);
        }
        instance = null;
    }

    public int getUid() {
        return this.race_uid;
    }

    public void raceFailed() {
        EventsControllerHelper.messageEventHappened(MESSAGE_WIN_RACE);
    }

    public void raceFinished(int place) {
        if (place == 1) {
            EventsControllerHelper.messageEventHappened(MESSAGE_WIN_RACE);
        } else {
            EventsControllerHelper.messageEventHappened(MESSAGE_LOOSE_RACE);
        }
        BigRace.removeListener(this);
    }

    public void raceStarted(boolean is_live) {
        if (!is_live) {
            EventsControllerHelper.messageEventHappened(MESSAGE_LOOSE_RACE);
            BigRace.removeListener(this);
        }
    }
}

