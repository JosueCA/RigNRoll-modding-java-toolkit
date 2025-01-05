/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.lang.reflect.Method;
import java.util.HashMap;
import rnrloggers.ScenarioLogger;
import scriptEvents.CbVideoCallback;
import scriptEvents.ScriptEvent;

public class CbVideoEvent
implements ScriptEvent {
    private static HashMap<EventType, Method> methodResolveTable = new HashMap();
    private String cbVideoCallName = null;
    private Method cbVideoCallbackMethodToCall = null;
    private Object[] parametersToMethod = null;

    public CbVideoEvent(String cbVideoCallName, EventType whatHappened, int answerNomber) {
        this.cbVideoCallName = cbVideoCallName;
        this.cbVideoCallbackMethodToCall = methodResolveTable.get((Object)whatHappened);
        if (EventType.ANSWER == whatHappened) {
            this.parametersToMethod = new Object[1];
            this.parametersToMethod[0] = answerNomber;
        } else {
            this.parametersToMethod = new Object[0];
        }
    }

    Object[] getParameters() {
        return this.parametersToMethod;
    }

    Method getMethodToCall() {
        return this.cbVideoCallbackMethodToCall;
    }

    String getCbVideoCallName() {
        return this.cbVideoCallName;
    }

    public String toString() {
        if (null != this.cbVideoCallName) {
            if (null != this.cbVideoCallbackMethodToCall) {
                return "CbVideo " + this.cbVideoCallName + ' ' + this.cbVideoCallbackMethodToCall.getName();
            }
            return "CbVideo " + this.cbVideoCallName;
        }
        return new String();
    }

    static {
        try {
            methodResolveTable.put(EventType.START, CbVideoCallback.class.getDeclaredMethod("onStart", new Class[0]));
            methodResolveTable.put(EventType.FINISH, CbVideoCallback.class.getDeclaredMethod("onFinish", new Class[0]));
            methodResolveTable.put(EventType.ANSWER, CbVideoCallback.class.getDeclaredMethod("onAnswer", Integer.TYPE));
        }
        catch (NoSuchMethodException exception) {
            ScenarioLogger.getInstance().machineWarning("failed to initialize CbVideoEvent callback lookup table");
            ScenarioLogger.getInstance().machineWarning(exception.getLocalizedMessage());
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum EventType {
        START,
        FINISH,
        ANSWER;

    }
}

