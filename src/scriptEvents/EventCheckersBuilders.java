/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.util.ArrayList;
import scriptEvents.EventChecker;
import scriptEvents.MissionEndchecker;

public class EventCheckersBuilders {
    private static ArrayList<EventChecker> to_construct = new ArrayList();

    public static void add_to_construct(EventChecker checker) {
        to_construct.add(checker);
    }

    public static void do_construct() {
        for (EventChecker checker : to_construct) {
            if (!(checker instanceof MissionEndchecker)) continue;
            MissionEndchecker mission_checker = (MissionEndchecker)checker;
            mission_checker.construct();
        }
        to_construct.clear();
    }
}

