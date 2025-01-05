/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.map;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import players.Crew;
import rnrcore.eng;
import rnrscenario.missions.Dumpable;
import rnrscenario.missions.MissionPlacement;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.map.Place;
import rnrscr.parkingplace;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class PointsController
implements Dumpable {
    private static PointsController ourInstance = new PointsController();
    private Map<String, ReferenceCounter> lockedPoints = new HashMap<String, ReferenceCounter>();
    static final boolean OLD_PARKING = false;

    public static PointsController getInstance() {
        return ourInstance;
    }

    private PointsController() {
    }

    public static void deinitialize() {
        ourInstance = new PointsController();
    }

    public void lockPoints(Collection<String> pointsNames, MissionPlacement missionPlacement) {
        assert (null != pointsNames) : "pointName must be non-null reference";
        for (String pointName : pointsNames) {
            int parkingnumber;
            Place place = MissionSystemInitializer.getMissionsMap().getPlace(pointName);
            if (place.getTip() != 0) {
                ReferenceCounter refCounter = this.lockedPoints.get(pointName);
                if (null == refCounter) {
                    this.lockedPoints.put(pointName, new ReferenceCounter());
                    continue;
                }
                refCounter.increment();
                continue;
            }
            ArrayList<Integer> list = missionPlacement.getParkingPP(pointName);
            if (list != null) continue;
            parkingplace nearestParkingPlace = missionPlacement.getParking(pointName);
            ArrayList<Integer> listnumber = new ArrayList<Integer>();
            for (int np = missionPlacement.getParkingN(pointName); np > 0 && (parkingnumber = Crew.getIgrokCar().lockParkingForMission(nearestParkingPlace)) > 0; --np) {
                listnumber.add(parkingnumber);
            }
            missionPlacement.putParking(pointName, listnumber);
        }
    }

    public void freePoint(String pointName, MissionPlacement missionPlacement) {
        assert (null != pointName) : "pointName must be non-null reference";
        Place place = MissionSystemInitializer.getMissionsMap().getPlace(pointName);
        if (place.getTip() != 0) {
            ReferenceCounter refCounter = this.lockedPoints.get(pointName);
            if (null != refCounter && !refCounter.decrement()) {
                this.lockedPoints.remove(pointName);
                eng.log("PointController. Point with name " + pointName + " is free now.");
            }
        } else {
            parkingplace park = missionPlacement.getParking(pointName);
            ArrayList<Integer> listnumber = missionPlacement.getParkingPP(pointName);
            for (Integer i : listnumber) {
                Crew.getIgrokCar().freeParkingForMission(park, i);
            }
        }
    }

    public void freePoints(Iterable<String> points, MissionPlacement missionPlacement) {
        assert (null != points) : "points must be non-null reference";
        for (String point : points) {
            this.freePoint(point, missionPlacement);
        }
    }

    public void printLockedPoints() {
        eng.log("printLockedPoints: ");
        Set<Map.Entry<String, ReferenceCounter>> points = this.lockedPoints.entrySet();
        for (Map.Entry<String, ReferenceCounter> entry : points) {
            String mess = "Point :\t" + entry.getKey() + " . Refcount :\t" + entry.getValue().referenceCount;
            eng.log("\t" + mess);
        }
        eng.log("END printLockedPoints: ");
    }

    public boolean hasGroupFreePoint(Collection<String> group, MissionPlacement missionPlacement) {
        if (group.isEmpty()) {
            return true;
        }
        boolean res = false;
        for (String pointName : group) {
            Place place = MissionSystemInitializer.getMissionsMap().getPlace(pointName);
            if (null == place) continue;
            if (place.getTip() != 0) {
                if (this.lockedPoints.containsKey(pointName)) continue;
                return true;
            }
            parkingplace nearestParkingPlace = parkingplace.findNearestParking(place.getCoords());
            if (nearestParkingPlace == null) {
                eng.log(place.getName());
                eng.log(Double.toString(place.getCoords().x) + Double.toString(place.getCoords().y) + Double.toString(place.getCoords().z));
            }
            int np = missionPlacement.getInfo().getNeedParking(pointName, true);
            int parkingNumber = Crew.getIgrokCar().hasParkingForMission(nearestParkingPlace);
            if (parkingNumber < np) continue;
            missionPlacement.putParking(pointName, nearestParkingPlace);
            missionPlacement.putParking(pointName, np);
            res = true;
        }
        return res;
    }

    @Override
    public void makeDump(OutputStream target) {
        PrintWriter out = new PrintWriter(target);
        out.println("LOCKED POINTS:");
        for (String lockedPoint : this.lockedPoints.keySet()) {
            out.println(lockedPoint);
        }
    }

    private static final class ReferenceCounter {
        private int referenceCount = 1;

        private ReferenceCounter() {
        }

        void increment() {
            ++this.referenceCount;
        }

        boolean decrement() {
            --this.referenceCount;
            return 0 < this.referenceCount;
        }
    }
}

