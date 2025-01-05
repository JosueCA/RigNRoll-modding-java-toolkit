/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.map;

import rnrcore.vectorJ;
import rnrscenario.missions.map.AbstractMissionsMap;
import rnrscenario.missions.map.MapEventsListener;
import rnrscenario.missions.map.NoPlacesException;
import rnrscenario.missions.map.Place;

public final class NullMissionMap
implements AbstractMissionsMap {
    private final Place proxyPlace = new Place();

    NullMissionMap() {
        this.proxyPlace.init(-1, "some place", new vectorJ(0.0, 0.0, 0.0));
    }

    void setPlayerPosition(vectorJ position) {
        assert (null != position) : "position must be non-null reference";
    }

    public void addPlace(Place target) {
    }

    public Place getNearestPlace() throws NoPlacesException {
        return this.proxyPlace;
    }

    public Place getCurrentPlace() {
        return null;
    }

    public void addListener(MapEventsListener listener) {
    }

    public void removeListener(MapEventsListener listener) {
    }

    public void addListener(MapEventsListener listener, double distance) {
    }

    public boolean onPlace(MapEventsListener listener, double distance) {
        return false;
    }

    public boolean onLoadingDistance(String pointname, vectorJ pos) {
        return false;
    }

    public boolean afterUnloadingDistance(String pointname, vectorJ pos) {
        return true;
    }
}

