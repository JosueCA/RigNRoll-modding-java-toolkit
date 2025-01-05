/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import rnrloggers.ScenarioLogger;
import scriptActions.ScriptAction;
import scriptEvents.CbVideoCallback;
import scriptEvents.CbVideoEvent;
import scriptEvents.EventListener;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CbVideoEventsListener
implements EventListener {
    private HashMap<String, CbVideoCallback> eventsReactors = new HashMap();

    public void addEventReaction(String callName, CbVideoCallback callBack) {
        if (null != callName && null != callBack) {
            this.eventsReactors.put(callName, callBack);
        }
    }

    @Override
    public void eventHappened(List<ScriptEvent> eventTuple) {
        if (null != eventTuple) {
            for (ScriptEvent event2 : eventTuple) {
                if (!(event2 instanceof CbVideoEvent)) continue;
                CbVideoEvent cbVideoEvent = (CbVideoEvent)event2;
                CbVideoCallback callBack = this.eventsReactors.get(cbVideoEvent.getCbVideoCallName());
                if (null != callBack) {
                    try {
                        Method call = cbVideoEvent.getMethodToCall();
                        if (null != call) {
                            call.setAccessible(true);
                            call.invoke((Object)callBack, cbVideoEvent.getParameters());
                            continue;
                        }
                        ScenarioLogger.getInstance().machineWarning("CbVideoEventsListener.eventHappened: can't find callback method for video call; CBV name == " + cbVideoEvent.getCbVideoCallName());
                    }
                    catch (IllegalAccessException exception) {
                        ScenarioLogger.getInstance().machineLog(Level.SEVERE, exception.getMessage());
                    }
                    catch (InvocationTargetException exception) {
                        ScenarioLogger.getInstance().machineLog(Level.SEVERE, exception.getTargetException().getMessage());
                    }
                    continue;
                }
                ScenarioLogger.getInstance().machineLog(Level.INFO, "CbVideoEventsListener.eventHappened: can't find callback for video call; CBV name ==  " + cbVideoEvent.getCbVideoCallName());
            }
        } else {
            ScenarioLogger.getInstance().machineLog(Level.SEVERE, "CbVideoEventsListener.eventHappened: invalid argument(s)");
        }
    }

    public List<ScriptAction> getActionList() {
        LinkedList<ScriptAction> out = new LinkedList<ScriptAction>();
        for (CbVideoCallback callBack : this.eventsReactors.values()) {
            out.addAll(callBack.getActionList());
        }
        return out;
    }

    public Set<String> getCallNamesList() {
        return Collections.unmodifiableSet(this.eventsReactors.keySet());
    }
}

