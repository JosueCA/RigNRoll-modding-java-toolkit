/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import menu.JavaEvents;
import rnrcore.CoreTime;

public class ConvertGameTime {
    private static final int MARKER_ADD_TO_CURRENT_TIME = 0;
    private static final int MARKER_ADD_TO_GIVEN_TIME = 1;

    public static CoreTime convertFromCurrent(int seconds) {
        Data data = new Data(seconds);
        JavaEvents.SendEvent(70, 0, data);
        return data.date;
    }

    public static CoreTime convertFromGiven(int seconds, CoreTime time) {
        assert (null != time) : "'time' must be non-null argument";
        Data data = new Data(seconds, time);
        JavaEvents.SendEvent(70, 1, data);
        return data.date;
    }

    static class Data {
        CoreTime date = new CoreTime();
        int seconds;
        CoreTime dateFrom = null;

        Data(int seconds, CoreTime timefrom) {
            this.seconds = seconds;
            this.dateFrom = timefrom;
        }

        Data(int seconds) {
            this.seconds = seconds;
        }
    }
}

