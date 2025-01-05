/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import rnrcore.event;
import rnrscenario.missions.CreateMissionEvent;
import rnrscenario.missions.MissionFactory;
import rnrscenario.missions.SingleMission;
import scriptEvents.EventListener;
import scriptEvents.ScriptEvent;
import xmlserialization.Log;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class MissionList
implements EventListener {
    private List<SingleMission> activeMissions = new LinkedList<SingleMission>();
    private MissionFactory factory = null;
    private static MissionList list;

    MissionList(MissionFactory factory) {
        assert (null != factory) : "factory must be valid non-null reference";
        this.factory = factory;
        list = this;
    }

    public static void dialogsFinished() {
        event.SetScriptevent(9850L);
    }

    private void createSingelMissionAddToList(String name) {
        SingleMission mission = this.factory.create(name);
        if (mission != null) {
            this.activeMissions.add(mission);
        }
    }

    private void startMissions(List<ScriptEvent> eventTuple) {
        assert (null != eventTuple) : "eventTuple must be valid non-null reference; check your EventsController's code";
        for (ScriptEvent event2 : eventTuple) {
            if (!(event2 instanceof CreateMissionEvent)) continue;
            this.createSingelMissionAddToList(((CreateMissionEvent)event2).getMissionName());
        }
    }

    private void transferEventsToActiveMissions(List<ScriptEvent> eventTuple) {
        if (null == eventTuple) {
            return;
        }
        ListIterator<SingleMission> missionIterator = this.activeMissions.listIterator();
        while (missionIterator.hasNext()) {
            SingleMission mission = missionIterator.next();
            if (mission == null || !mission.checkEnd(eventTuple)) continue;
            missionIterator.remove();
        }
    }

    // @Override
    public void eventHappened(List<ScriptEvent> eventTuple) {
        assert (null != eventTuple) : "eventTuple must be valid non-null reference; check your EventsController's code";
        this.transferEventsToActiveMissions(eventTuple);
        this.startMissions(eventTuple);
    }

    public List<String> getStartedMissions() {
        ArrayList<String> missionNames = new ArrayList<String>();
        if (this.activeMissions == null) {
            Log.error("MissionList.serializeXML activeMissions=null");
        }
        for (SingleMission mission : this.activeMissions) {
            if (mission == null) {
                Log.error("MissionList.serializeXML missions=null");
            }
            missionNames.add(mission.getMission_name());
        }
        return missionNames;
    }

    public void restoreStartedMissions(List<String> names) {
        for (String missionName : names) {
            this.createSingelMissionAddToList(missionName);
        }
    }
}

