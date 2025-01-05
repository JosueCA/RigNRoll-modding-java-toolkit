/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import rnrcore.CoreTime;
import rnrloggers.ScriptsLogger;
import scriptEvents.EventChecker;
import scriptEvents.ScriptEvent;
import scriptEvents.TimeEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class TimeEventChecker
implements EventChecker {
    static final long serialVersionUID = 0L;
    private CoreTime fromhour = null;
    private CoreTime tohour = null;
    private ScriptEvent lastSuccessfullyChecked = null;

    @Override
    public void deactivateChecker() {
    }

    public TimeEventChecker(CoreTime start, CoreTime end) {
        if (null == start || null == end) {
            throw new IllegalArgumentException("all arguments must be non-null reference");
        }
        this.fromhour = start;
        this.tohour = end;
    }

    public TimeEventChecker() {
    }

    @Override
    public boolean checkEvent(List<ScriptEvent> eventTuple) {
        if (null == this.fromhour || null == this.tohour) {
            String error = "TimeEventChecker wasn't correctly initialized, can't check event";
            ScriptsLogger.getInstance().log(Level.SEVERE, 4, error);
            System.err.println(error);
            return false;
        }
        for (ScriptEvent event2 : eventTuple) {
            TimeEvent timeEvent;
            CoreTime eventTime;
            if (!(event2 instanceof TimeEvent) || (eventTime = (timeEvent = (TimeEvent)event2).getTime()).gHour() < this.fromhour.gHour() || eventTime.gHour() > this.tohour.gHour()) continue;
            this.lastSuccessfullyChecked = event2;
            return true;
        }
        return false;
    }

    @Override
    public ScriptEvent lastPossetiveChecked() {
        return this.lastSuccessfullyChecked;
    }

    @Override
    public List<ScriptEvent> getExpectantEvent() {
        ArrayList<ScriptEvent> out = new ArrayList<ScriptEvent>(1);
        out.add(new TimeEvent(new CoreTime(0, 0, 0, this.fromhour.gHour(), 0)));
        return out;
    }

    @Override
    public String isValid() {
        String error = new String();
        if (null == this.fromhour) {
            error = error + "fromhour is null ";
        }
        if (null == this.tohour) {
            error = error + "tohour is null ";
        }
        if (0 == error.length()) {
            return null;
        }
        return error;
    }
}

