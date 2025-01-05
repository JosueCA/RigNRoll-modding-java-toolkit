/*
 * Decompiled with CFR 0.151.
 */
package scenarioMachine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import rnrloggers.ScenarioLogger;
import scenarioMachine.FiniteState;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class FiniteStatesSet {
    private static final int DEFAULT_SET_CAPACITY = 128;
    private final HashMap<String, FiniteState> statesSet = new HashMap(128);

    public void addState(FiniteState state) {
        if (null == state) {
            throw new IllegalArgumentException("state must be non-null reference");
        }
        String stateName = state.getName();
        if (!this.statesSet.containsKey(stateName)) {
            this.statesSet.put(stateName, state);
        } else {
            ScenarioLogger.getInstance().machineLog(Level.WARNING, "failed to add state to set: state already exists");
        }
    }

    public FiniteState findState(String name) {
        return this.statesSet.get(name);
    }

    public Map<String, FiniteState> getStates() {
        return Collections.unmodifiableMap(this.statesSet);
    }
}

