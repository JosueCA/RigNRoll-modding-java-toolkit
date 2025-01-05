/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.infochannels;

import java.util.ArrayList;
import java.util.List;
import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.infochannels.InformationChannel;
import rnrscenario.missions.map.AbstractMissionsMap;
import rnrscenario.missions.map.MapEventsListener;
import rnrscenario.missions.map.Place;
import rnrscr.IChannelPointChanges;
import scriptEvents.EventListener;
import scriptEvents.EventsController;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class DelayedChannel
extends InformationChannel
implements MapEventsListener,
EventListener,
IChannelPointChanges {
    private static final double CHANNEL_DISNANCE = 450.0;
    private AbstractMissionsMap map = null;
    private String missionName = null;
    private boolean immediatelyPost = false;
    private String channelPoint = null;
    private boolean channelListenerAdded = false;

    @Override
    protected void renewChachedObject(String resource) {
        super.renewChachedObject(resource);
        if (!this.channelListenerAdded && null != this.cachedInfo) {
            this.channelListenerAdded = true;
            this.cachedInfo.addChannelPlaceChangedListener(this);
        }
    }

    void immediatelyPost(boolean need) {
        this.immediatelyPost = need;
    }

    // @Override
    public String getMissionName() {
        return this.missionName;
    }

    // @Override
    public String getPointName() {
        return this.channelPoint;
    }

    // @Override
    public void setOnPoint(String pointName) {
        this.channelPoint = pointName;
        this.map.addListener(this, 450.0);
    }

    // @Override
    public void freeFromPoint() {
        if (null != this.channelPoint) {
            this.channelPoint = null;
            this.map.removeListener(this);
        }
    }

    DelayedChannel(AbstractMissionsMap map) {
        assert (null != map) : "map must be non-null reference";
        this.map = map;
    }

    AbstractMissionsMap getMap() {
        return this.map;
    }

    @Override
    public final void postStartMissionInfo(MissionInfo info, String resource) {
        this.missionName = info.getName();
        this.renewChachedObject(resource);
        this.postStartMissionInfo_Detailed(info, resource);
        if (this.whereCanAppear().isEmpty()) {
            Place place = MissionSystemInitializer.getMissionsMap().getNearestPlaceByType(1);
            ArrayList<String> places = new ArrayList<String>();
            places.add(place.getName());
            this.addPlaces(places);
            this.cachedInfo.setPointName(place.getName());
            this.realInfoPost(this.missionName, resource, false);
        }
    }

    abstract void postStartMissionInfo_Detailed(MissionInfo var1, String var2);

    @Override
    public final void postInfo(String missionName, String resource, boolean useMainInfo) {
        this.missionName = missionName;
        this.renewChachedObject(resource);
        if (this.immediatelyPost || this.channelPoint != null && this.map.onPlace(this, 450.0)) {
            this.realInfoPost(missionName, this.resourceHold, false);
            this.map.removeListener(this);
        }
    }

    // @Override
    public final void placeAreaEntered() {
        this.realInfoPost(this.missionName, this.resourceHold, false);
        this.map.removeListener(this);
    }

    public void placeAreaLeaved(Place where) {
    }

    // @Override
    public final void eventHappened(List<ScriptEvent> eventTuple) {
        for (ScriptEvent event2 : eventTuple) {
            if (!(event2 instanceof ScriptEvent)) continue;
            this.realInfoPost(this.missionName, this.resourceHold, false);
            EventsController.getInstance().removeListener(this);
        }
    }

    @Override
    public abstract DelayedChannel clone();
}

