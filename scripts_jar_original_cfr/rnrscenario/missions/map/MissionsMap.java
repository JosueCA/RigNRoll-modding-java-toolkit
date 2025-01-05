/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.map;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import rnrcore.vectorJ;
import rnrloggers.MissionsLogger;
import rnrorg.MissionEventsMaker;
import rnrscenario.missions.infochannels.CbvChannel;
import rnrscenario.missions.map.AbstractMissionsMap;
import rnrscenario.missions.map.MapEventsListener;
import rnrscenario.missions.map.Place;
import rnrscenario.sctask;
import rnrscr.Helper;

public final class MissionsMap
extends sctask
implements AbstractMissionsMap {
    private static final int UPDATE_FREQUENCY = 3;
    private static final int PLACES_CAPACITY = 512;
    private static double LOADING_DISTANCE = 1000000.0;
    private static double UNLOADING_DISTANCE = 4000000.0;
    private final Map<String, Place> places = new LinkedHashMap<String, Place>(512);
    private final List<cMapEventListener> listeners = new LinkedList<cMapEventListener>();
    private final List<MapEventsListener> listenersToDelete = new LinkedList<MapEventsListener>();
    private boolean is_running = false;

    public MissionsMap() {
        super(3, false);
        super.start();
    }

    public boolean onLoadingDistance(String pointname, vectorJ pos) {
        Place place = this.getPlace(pointname);
        if (place == null) {
            MissionsLogger.getInstance().doLog("onLoadingDistance cannot find point named " + pointname, Level.SEVERE);
            return false;
        }
        return place.distance2(pos) < LOADING_DISTANCE;
    }

    public boolean afterUnloadingDistance(String pointname, vectorJ pos) {
        Place place = this.getPlace(pointname);
        if (place == null) {
            MissionsLogger.getInstance().doLog("afterUnloadingDistance cannot find point named " + pointname, Level.SEVERE);
            return false;
        }
        return place.distance2(pos) > UNLOADING_DISTANCE;
    }

    public Place getPlace(String name) {
        if (null == name) {
            return null;
        }
        return this.places.get(name);
    }

    public void addListener(MapEventsListener maplistener, double distance) {
        String point_name;
        if (null != maplistener && (point_name = maplistener.getPointName()) != null) {
            this.listeners.add(new cMapEventListener(this.places.get(point_name), maplistener, distance));
        }
    }

    public void removeListener(MapEventsListener maplistener) {
        if (null != maplistener) {
            if (this.listenersToDelete.contains(maplistener)) {
                return;
            }
            this.listenersToDelete.add(maplistener);
            if (!this.is_running) {
                for (MapEventsListener listener : this.listenersToDelete) {
                    Iterator<cMapEventListener> iter = this.listeners.iterator();
                    while (iter.hasNext()) {
                        cMapEventListener lst = iter.next();
                        if (!lst.listener.equals(listener)) continue;
                        lst.listener.placeAreaEntered();
                        iter.remove();
                    }
                }
                this.listenersToDelete.clear();
            }
        }
    }

    public void addPlace(Place target) {
        if (null != target) {
            this.places.put(target.getName(), target);
        }
    }

    public Place getNearestPlace() {
        if (this.places.isEmpty()) {
            return null;
        }
        Place nearestFound = this.places.values().iterator().next();
        vectorJ pos = Helper.getCurrentPosition();
        double distance = nearestFound.distance(pos);
        for (Map.Entry<String, Place> place : this.places.entrySet()) {
            double newDistance = place.getValue().distance(pos);
            if (!(newDistance < distance)) continue;
            nearestFound = place.getValue();
            distance = newDistance;
        }
        return nearestFound;
    }

    public Place getNearestPlaceByType(int type) {
        if (this.places.isEmpty()) {
            return null;
        }
        Place nearestFound = this.places.values().iterator().next();
        vectorJ pos = Helper.getCurrentPosition();
        double distance = nearestFound.distance(pos);
        for (Map.Entry<String, Place> place : this.places.entrySet()) {
            double newDistance;
            if (place.getValue().getTip() != type || !((newDistance = place.getValue().distance(pos)) < distance)) continue;
            nearestFound = place.getValue();
            distance = newDistance;
        }
        return nearestFound;
    }

    public Place getNearestPlaceByType(int type, Place not_place) {
        if (this.places.isEmpty()) {
            return null;
        }
        Place nearestFound = this.places.values().iterator().next();
        vectorJ pos = Helper.getCurrentPosition();
        double distance = nearestFound.distance(pos);
        for (Map.Entry<String, Place> place : this.places.entrySet()) {
            double newDistance;
            if (place.getValue().getTip() != type || !((newDistance = place.getValue().distance(pos)) < distance) || not_place.equals(place.getValue())) continue;
            nearestFound = place.getValue();
            distance = newDistance;
        }
        return nearestFound;
    }

    public boolean onPlace(MapEventsListener listener, double distance) {
        vectorJ position = Helper.getCurrentPosition();
        String point_name = listener.getPointName();
        if (point_name != null) {
            vectorJ pos = this.places.get(point_name).getCoords();
            return Math.abs(pos.x - position.x) + Math.abs(pos.y - position.y) < distance;
        }
        return false;
    }

    public void run() {
        vectorJ position = Helper.getCurrentPosition();
        if (null == position) {
            return;
        }
        this.is_running = true;
        for (cMapEventListener lst : this.listeners) {
            vectorJ pos = lst.place.getCoords();
            if (!(Math.abs(pos.x - position.x) + Math.abs(pos.y - position.y) < lst.distance) || lst.listener instanceof CbvChannel && !MissionEventsMaker.freeSlotForMission(lst.listener.getMissionName())) continue;
            lst.listener.placeAreaEntered();
        }
        for (MapEventsListener listener : this.listenersToDelete) {
            Iterator<cMapEventListener> iter = this.listeners.iterator();
            while (iter.hasNext()) {
                cMapEventListener lst = iter.next();
                if (!lst.listener.equals(listener)) continue;
                iter.remove();
            }
        }
        this.listenersToDelete.clear();
        this.is_running = false;
    }

    public void deinit() {
        this.places.clear();
        this.listeners.clear();
        this.listenersToDelete.clear();
        this.is_running = false;
        this.finishImmediately();
    }

    static class cMapEventListener {
        Place place;
        MapEventsListener listener;
        double distance;

        cMapEventListener(Place place, MapEventsListener listener, double distance) {
            this.place = place;
            this.listener = listener;
            this.distance = distance;
        }
    }
}

