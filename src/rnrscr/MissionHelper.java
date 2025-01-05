/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import rnrcore.vectorJ;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.map.Place;

public class MissionHelper {
    private static double point_devias = 100.0;

    public static Place getPlace(String name) {
        return MissionSystemInitializer.getMissionsMap().getPlace(name);
    }

    public static boolean isThatPlace(int tip, vectorJ position, String place_name) {
        Place place = MissionHelper.getPlace(place_name);
        switch (tip) {
            case 8: {
                if (place.getTip() == 0) break;
                return false;
            }
            default: {
                return false;
            }
        }
        return place.distance(position) < point_devias;
    }
}

