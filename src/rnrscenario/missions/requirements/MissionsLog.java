/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.requirements;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import rnrloggers.MissionsLogger;
import rnrscenario.missions.Dumpable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class MissionsLog
implements Dumpable {
    private static MissionsLog ourInstance = new MissionsLog();
    private static final int DEFAULT_MAP_CAPACITY = 128;
    private Map<String, MissionState> missionsEvents = new HashMap<String, MissionState>(128);

    public static void deinit() {
        ourInstance = new MissionsLog();
    }

    public static MissionsLog getInstance() {
        return ourInstance;
    }

    private MissionsLog() {
    }

    public MissionState getMissionState(String missionName) {
        return this.missionsEvents.get(missionName);
    }

    public void eventHappen(String missionName, Event what) {
        MissionState state = this.getMissionState(missionName);
        if (null == state) {
            state = new MissionState();
            state.eventHappen(what);
            this.missionsEvents.put(missionName, state);
        } else {
            state.eventHappen(what);
        }
    }

    // @Override
    public void makeDump(OutputStream target) {
        PrintWriter out = new PrintWriter(target);
        out.println("MISSION EVENTS:");
        for (Map.Entry<String, MissionState> state : this.missionsEvents.entrySet()) {
            out.println("\tMISSION " + state.getKey() + ':');
            for (Event event2 : state.getValue().getOccuredEvents()) {
                out.print("\t\t");
                out.println(event2.name());
            }
        }
    }

    public Map<String, MissionState> getMissionsEvents() {
        return this.missionsEvents;
    }

    public void setMissionsEvents(Map<String, MissionState> missionsEvents) {
        this.missionsEvents = missionsEvents;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class MissionState {
        private List<Event> events = new LinkedList<Event>();
        private boolean missionFinished = false;

        public void eventHappen(Event what) {
            if (null != what) {
                this.events.add(what);
                this.missionFinished |= what.missionFinished();
            } else {
                MissionsLogger.getInstance().doLog("Invalid event came to mission stati in MissionsLog", Level.SEVERE);
            }
        }

        public Collection<Event> getOccuredEvents() {
            return Collections.unmodifiableList(this.events);
        }

        public boolean missionFinished() {
            return this.missionFinished;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum Event {
        PLAYER_INFORMED_ABOUT_MISSION(false, false, false),
        PLAYER_DECLINED_MISSION(true, false, false),
        MISSION_ACCEPTED(true, false, false),
        MISSION_DROPPED(true, true, true),
        MISSION_COMPLETE(true, false, true),
        FRIGHT_BROKEN(true, true, true),
        FREIGHT_DELIVERY_EXPIRED(true, true, true),
        FREIGHT_LOADING_EXPIRED(true, true, true);

        private final boolean playerReactedOnMission;
        private final boolean missionFailed;
        private final boolean missionFinished;

        private Event(boolean playerReactedOnMission, boolean missionFailed, boolean missionFinished) {
            this.playerReactedOnMission = playerReactedOnMission;
            this.missionFailed = missionFailed;
            this.missionFinished = missionFinished;
        }

        public boolean wasMissionConsideredByPlayer() {
            return this.playerReactedOnMission;
        }

        public boolean missionFailed() {
            return this.missionFailed;
        }

        public boolean missionFinished() {
            return this.missionFinished;
        }
    }
}

