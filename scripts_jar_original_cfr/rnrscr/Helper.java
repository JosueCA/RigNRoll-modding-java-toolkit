/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import players.Crew;
import players.actorveh;
import rnrcore.SCRuniperson;
import rnrcore.cameratrack;
import rnrcore.eng;
import rnrcore.vectorJ;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.map.Place;

public class Helper {
    public static void placePersonToCar(SCRuniperson person, actorveh car) {
        car.UpdateCar();
        if (car.getCar() == 0) {
            eng.err("placePersonToCar has unloaded car!");
        }
        person.SetInGameWorld();
        vectorJ poswheel = car.gPositionSteerWheel();
        vectorJ dirwheel = car.gDir();
        person.SetPosition(poswheel);
        person.SetDirection(dirwheel);
    }

    public static vectorJ getCurrentPosition() {
        if (null == Crew.getIgrokCar()) {
            return null;
        }
        return Crew.getIgrokCar().gPosition();
    }

    public static void getNearestGoodPoint(vectorJ pos) {
        if (null == Crew.getIgrokCar()) {
            return;
        }
        Place place = MissionSystemInitializer.getMissionsMap().getNearestPlaceByType(1);
        if (place != null) {
            pos.Set(place.getCoords());
            return;
        }
        place = MissionSystemInitializer.getMissionsMap().getNearestPlaceByType(0);
        if (place != null) {
            pos.Set(place.getCoords());
            return;
        }
        place = MissionSystemInitializer.getMissionsMap().getNearestPlaceByType(3);
        if (place == null) {
            pos.Set(Crew.getIgrokCar().gPosition());
            vectorJ direction = Crew.getIgrokCar().gDir();
            direction.mult(-20.0);
            pos.oPlus(direction);
        }
    }

    public static void restoreCameraToIgrokCar() {
        Crew.getIgrokCar().UpdateCar();
        cameratrack.AttachCameraToCar(Crew.getIgrokCar().getCar());
    }

    public static boolean isCarLive(actorveh car) {
        return car.getAi_player() == Crew.getIgrokCar().getAi_player();
    }
}

