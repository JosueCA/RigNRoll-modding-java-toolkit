/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.tech;

import players.Crew;
import players.actorveh;
import players.aiplayer;
import players.semitrailer;
import rnrcore.SCRuniperson;
import rnrcore.WorldState;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.teleport.ITeleported;
import rnrcore.teleport.MakeTeleport;
import rnrcore.vectorJ;
import rnrscenario.tech.SleepOnTime;
import rnrscr.drvscripts;
import rnrscr.parkingplace;

public final class Helper {
    private Helper() {
    }

    public static void waitGameWorldLoad() {
        while (true) {
            eng.lock();
            if (WorldState.checkStateWorld_GameWorld()) break;
            eng.unlock();
            new SleepOnTime(100);
        }
        eng.unlock();
    }

    public static void waitVehicleChanged() {
        while (true) {
            eng.lock();
            if (WorldState.checkStateWorld_VehicleChanged()) break;
            eng.unlock();
            new SleepOnTime(100);
        }
        eng.unlock();
    }

    public static void waitSimpleState() {
        while (true) {
            eng.lock();
            if (WorldState.checkStateState("simple")) break;
            eng.unlock();
            new SleepOnTime(100);
        }
        eng.unlock();
    }

    public static void waitTeleport() {
        Teleport tp = new Teleport();
        MakeTeleport.teleport(tp);
        while (true) {
            eng.lock();
            if (tp.was_teleported) break;
            eng.unlock();
            new SleepOnTime(100);
        }
        eng.unlock();
    }

    public static void waitLostSemitrailer(actorveh car) {
        while (true) {
            eng.lock();
            semitrailer current_semi = car.querryTrailer();
            if (null == current_semi) break;
            eng.unlock();
            new SleepOnTime(100);
        }
        eng.unlock();
    }

    public static void waitParked(actorveh car) {
        while (true) {
            eng.lock();
            boolean value = car.isparked();
            if (value) break;
            eng.unlock();
            new SleepOnTime(100);
        }
        eng.unlock();
    }

    public static void waitLoaded(actorveh car) {
        while (true) {
            boolean isLoaded;
            eng.lock();
            car.UpdateCar();
            boolean bl = isLoaded = car.getCar() != 0;
            if (isLoaded) break;
            eng.unlock();
            new SleepOnTime(100);
        }
        eng.unlock();
    }

    public static void makeParkAndComeOut(String name, int num) {
        parkingplace nearestParkingPlace;
        actorveh liveCar = Crew.getIgrokCar();
        vectorJ currentPlayerPosition = liveCar.gPosition();
        if (currentPlayerPosition != null) {
            nearestParkingPlace = parkingplace.findParkingByName(name, currentPlayerPosition);
            if (nearestParkingPlace == null) {
                return;
            }
        } else {
            return;
        }
        eng.lock();
        liveCar.makeParkingAnimated(nearestParkingPlace, 4.0, num);
        eng.unlock();
        Helper.waitParked(liveCar);
        Helper.makeComeOut();
    }

    public static void makeComeOut() {
        actorveh liveCar = Crew.getIgrokCar();
        aiplayer livePlayer = Crew.getIgrok();
        drvscripts.helper.playOutOffCarThreaded(livePlayer, liveCar);
    }

    public static void makeComeInAndLeaveParking() {
        eng.lock();
        eng.returnCameraToGameWorld();
        eng.unlock();
        Helper.waitGameWorldLoad();
        aiplayer livePlayer = Crew.getIgrok();
        actorveh liveCar = Crew.getIgrokCar();
        liveCar.UpdateCar();
        if (null == livePlayer || 0 == liveCar.getCar()) {
            return;
        }
        SCRuniperson person = livePlayer.getModel();
        person.play();
        drvscripts.helper.playInsideCarThreaded(livePlayer, liveCar);
        rnrscr.Helper.restoreCameraToIgrokCar();
        eng.lock();
        liveCar.leaveParking();
        eng.unlock();
    }

    public static void makeComeInAndLeaveParkingFast() {
        eng.returnCameraToGameWorld();
        Helper.waitGameWorldLoad();
        aiplayer livePlayer = Crew.getIgrok();
        actorveh liveCar = Crew.getIgrokCar();
        liveCar.UpdateCar();
        if (null == livePlayer || 0 == liveCar.getCar()) {
            return;
        }
        SCRuniperson person = livePlayer.getModel();
        person.play();
        eng.SwitchDriver_in_cabin(liveCar.getCar());
        rnrscr.Helper.restoreCameraToIgrokCar();
        eng.lock();
        liveCar.leaveParking();
        eng.unlock();
    }

    public static matrixJ makeMatrixAlignedToZ(matrixJ originalM) {
        matrixJ newM = new matrixJ();
        newM.Set2(0.0, 0.0, 1.0);
        vectorJ newY = new vectorJ(originalM.v1);
        newY.z = 0.0;
        newY.norm();
        vectorJ newX = newY.oCross(newM.v2);
        newM.Set0(newX.x, newX.y, newX.z);
        newM.Set1(newY.x, newY.y, newY.z);
        return newM;
    }

    public static class Teleport
    implements ITeleported {
        boolean was_teleported = false;

        public void teleported() {
            this.was_teleported = true;
        }
    }
}

