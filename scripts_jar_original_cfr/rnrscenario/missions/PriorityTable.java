/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import java.util.HashMap;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class PriorityTable {
    private final Map<String, Integer> priorities = new HashMap<String, Integer>();

    public void movedFromDependantToActive(String parentName, String missionName) {
        Integer priority = this.priorities.get(missionName);
        if (null != priority) {
            priority = priority + 100;
        } else {
            this.registerMissionPriority(missionName, 100 + this.getPriority(parentName));
        }
    }

    public int getPriority(String missionName) {
        Integer priority;
        if (null != missionName && null != (priority = this.priorities.get(missionName))) {
            return priority;
        }
        return 0;
    }

    public void registerMissionPriority(String missionName, int priotiry) {
        if (null != missionName) {
            this.priorities.put(missionName, priotiry);
        }
    }

    public Map<String, Integer> getPriorities() {
        return this.priorities;
    }
}

