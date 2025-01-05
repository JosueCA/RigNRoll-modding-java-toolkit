/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import rnrcore.Helper;
import rnrcore.INativeMessageEvent;
import rnrloggers.MissionsLogger;
import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.SuccesFailMissionEvent;
import rnrscenario.missions.requirements.MissionsLog;
import scriptEvents.EventChecker;
import scriptEvents.EventCheckersBuilders;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class MissionEndchecker
implements EventChecker {
    static final long serialVersionUID = 0L;
    public static final String FAIL_TIMEOUT_PICKUP = "fail timeout pickup";
    public static final String FAIL_TIMEOUT_COMPLETE = "fail timeout complete";
    public static final String FAIL_DAMAGED = "fail damaged";
    public static final String FAIL_DECLINE = "decline";
    public static final String SUCCESS = "success";
    private static final String[] TYPE_END = new String[]{"fail timeout pickup", "fail timeout complete", "fail damaged", "decline", "success"};
    public static final String[] SUFFIX = new String[]{" mission failed timeout pickup", " mission failed timeout complete", " mission failed timeout damaged", " mission failed declined", " mission succeeded"};
    private static final Map<String, MissionsLog.Event> EVENTS_MAPPING = new HashMap<String, MissionsLog.Event>();
    private boolean constructed = false;
    private String type = "";
    private ScriptEvent event_to_emmit = null;
    private String mission_name = null;
    private INativeMessageEvent eventListener = null;
    private static HashMap<String, storeINativeMessageEvent> registeredEventListeners;

    public static void deinit() {
        if (null != registeredEventListeners) {
            registeredEventListeners.clear();
        }
        registeredEventListeners = new HashMap();
    }

    private static void peekOnMissionName(INativeMessageEvent listener_not_to_exclude, String mission_name) {
        if (!registeredEventListeners.containsKey(mission_name)) {
            MissionsLogger.getInstance().doLog("peekOnMissionName has no registered listeners for mission name " + mission_name, Level.SEVERE);
        }
        storeINativeMessageEvent storage = registeredEventListeners.get(mission_name);
        storage.remove_all_not_thatone(listener_not_to_exclude);
    }

    private static void registerListener(INativeMessageEvent listener, String mission_name) {
        storeINativeMessageEvent storage;
        if (!registeredEventListeners.containsKey(mission_name)) {
            storage = new storeINativeMessageEvent();
            registeredEventListeners.put(mission_name, storage);
        } else {
            storage = registeredEventListeners.get(mission_name);
        }
        storage.add(listener);
    }

    public MissionEndchecker() {
        this.mission_name = MissionInfo.getLoadingMissionName();
        EventCheckersBuilders.add_to_construct(this);
    }

    public MissionEndchecker(String type) {
        this.type = type;
        this.mission_name = MissionInfo.getLoadingMissionName();
        EventCheckersBuilders.add_to_construct(this);
    }

    public void construct() {
        if (this.constructed) {
            return;
        }
        this.constructed = true;
        this.event_to_emmit = new stubEvent();
        for (int i = 0; i < TYPE_END.length; ++i) {
            String str = TYPE_END[i];
            if (this.type.compareToIgnoreCase(str) != 0) continue;
            this.eventListener = new SuccesFailMissionEvent(this.mission_name, SUFFIX[i], this.event_to_emmit);
            Helper.addNativeEventListener(this.eventListener);
            MissionEndchecker.registerListener(this.eventListener, this.mission_name);
        }
    }

    // @Override
    public boolean checkEvent(List<ScriptEvent> eventTuple) {
        for (ScriptEvent event2 : eventTuple) {
            if (null == this.event_to_emmit || !this.event_to_emmit.equals(event2)) continue;
            MissionEndchecker.peekOnMissionName(this.eventListener, this.mission_name);
            MissionsLog.getInstance().eventHappen(this.mission_name, EVENTS_MAPPING.get(this.type));
            return true;
        }
        return false;
    }

    // @Override
    public ScriptEvent lastPossetiveChecked() {
        return null;
    }

    // @Override
    public List<ScriptEvent> getExpectantEvent() {
        return null;
    }

    // @Override
    public String isValid() {
        return null;
    }

    // @Override
    public void deactivateChecker() {
        Set<Map.Entry<String, storeINativeMessageEvent>> entries = registeredEventListeners.entrySet();
        for (Map.Entry<String, storeINativeMessageEvent> item : entries) {
            item.getValue().remove_all_not_thatone(null);
        }
    }

    static {
        EVENTS_MAPPING.put(FAIL_TIMEOUT_PICKUP, MissionsLog.Event.FREIGHT_LOADING_EXPIRED);
        EVENTS_MAPPING.put(FAIL_TIMEOUT_COMPLETE, MissionsLog.Event.FREIGHT_DELIVERY_EXPIRED);
        EVENTS_MAPPING.put(FAIL_DAMAGED, MissionsLog.Event.FRIGHT_BROKEN);
        EVENTS_MAPPING.put(FAIL_DECLINE, MissionsLog.Event.MISSION_DROPPED);
        EVENTS_MAPPING.put(SUCCESS, MissionsLog.Event.MISSION_COMPLETE);
        registeredEventListeners = new HashMap();
    }

    static class stubEvent
    implements ScriptEvent {
        stubEvent() {
        }
    }

    static class storeINativeMessageEvent {
        private ArrayList<INativeMessageEvent> listeners = new ArrayList();

        storeINativeMessageEvent() {
        }

        private void add(INativeMessageEvent listener) {
            this.listeners.add(listener);
        }

        private void remove_all_not_thatone(INativeMessageEvent listener) {
            for (INativeMessageEvent lst : this.listeners) {
                if (listener.equals(lst)) continue;
                Helper.removeNativeEventListener(lst);
            }
        }
    }
}

