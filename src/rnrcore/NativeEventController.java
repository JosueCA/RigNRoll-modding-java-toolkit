// Decompiled with: CFR 0.152
// Class Version: 5
package rnrcore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import rnrcore.INativeMessageEvent;
import rnrcore.Log;
import rnrscenario.consistency.ScenarioGarbageFinder;
import rnrscenario.consistency.ScenarioStage;
import rnrscenario.consistency.StageChangedListener;

public class NativeEventController
implements StageChangedListener {
    private static NativeEventController instance = null;
    private Map<String, List<INativeMessageEvent>> registeredEvents = new HashMap<String, List<INativeMessageEvent>>();
    private List<INativeMessageEvent> m_invalideListeners = new ArrayList<INativeMessageEvent>();
    private List<INativeMessageEvent> m_newListeners = new ArrayList<INativeMessageEvent>();
    private ScenarioStage scenarioStageChange = null;
    private boolean processingEvent = false;

    private NativeEventController() {
        instance = this;
    }

    public static NativeEventController getInstance() {
        return instance;
    }

    public static void init() {
        instance = new NativeEventController();
    }

    public static void deinit() {
        instance = null;
    }

    public static void addNativeEventListener(INativeMessageEvent listener) {
        if (null == listener || listener.getMessage() == null || 0 == listener.getMessage().length()) {
            return;
        }
        if (!NativeEventController.instance.processingEvent) {
            Log.simpleMessage("NativeEventController addNativeEventListener " + listener.getMessage());
            if (NativeEventController.instance.registeredEvents.containsKey(listener.getMessage())) {
                NativeEventController.instance.registeredEvents.get(listener.getMessage()).add(listener);
            } else {
                ArrayList<INativeMessageEvent> list = new ArrayList<INativeMessageEvent>();
                list.add(listener);
                NativeEventController.instance.registeredEvents.put(listener.getMessage(), list);
            }
        } else {
            Log.simpleMessage("NativeEventController delayed addNativeEventListener " + listener.getMessage());
            NativeEventController.instance.m_newListeners.add(listener);
        }
    }

    public static void removeNativeListener(INativeMessageEvent listener) {
        if (null == listener || null == listener.getMessage() || 0 == listener.getMessage().length()) {
            return;
        }
        if (!NativeEventController.instance.processingEvent) {
            Log.simpleMessage("NativeEventController removeNativeListener " + listener.getMessage());
            if (NativeEventController.instance.registeredEvents.containsKey(listener.getMessage())) {
                NativeEventController.instance.registeredEvents.get(listener.getMessage()).remove(listener);
            }
        } else {
            Log.simpleMessage("NativeEventController delayed removeNativeListener " + listener.getMessage());
            NativeEventController.instance.m_invalideListeners.add(listener);
        }
    }

    public static void messageEventHappend(String message) {
        Log.simpleMessage("NativeEventController messageEventHappend " + message);
        NativeEventController.instance.processingEvent = true;
        if (NativeEventController.instance.registeredEvents.containsKey(message)) {
            List<INativeMessageEvent> listeners = NativeEventController.instance.registeredEvents.get(message);
            Iterator<INativeMessageEvent> iter = listeners.iterator();
            while (iter.hasNext()) {
                INativeMessageEvent listener = iter.next();
                if (NativeEventController.instance.m_invalideListeners.contains(listener)) continue;
                listener.onEvent(message);
                if (!listener.removeOnEvent()) continue;
                iter.remove();
            }
        }
        NativeEventController.instance.processingEvent = false;
        for (INativeMessageEvent listener : NativeEventController.instance.m_newListeners) {
            NativeEventController.addNativeEventListener(listener);
        }
        NativeEventController.instance.m_newListeners.clear();
        for (INativeMessageEvent listener : NativeEventController.instance.m_invalideListeners) {
            NativeEventController.removeNativeListener(listener);
        }
        NativeEventController.instance.m_invalideListeners.clear();
        if (null != NativeEventController.instance.scenarioStageChange) {
            ScenarioGarbageFinder.deleteOutOfDateScenarioObjects(NativeEventController.class.getName(), NativeEventController.instance.registeredEvents.values(), NativeEventController.instance.scenarioStageChange);
            NativeEventController.instance.scenarioStageChange = null;
        }
    }

    public void scenarioCheckPointReached(ScenarioStage scenarioStage) {
        if (this.processingEvent) {
            this.scenarioStageChange = scenarioStage;
        } else {
            ScenarioGarbageFinder.deleteOutOfDateScenarioObjects(this.getClass().getName(), this.registeredEvents.values(), scenarioStage);
        }
    }
}
