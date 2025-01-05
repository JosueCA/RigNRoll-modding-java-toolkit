/*
 * Decompiled with CFR 0.151.
 */
package scenarioXml;

import scenarioMachine.FiniteStateMachine;
import scenarioMachine.FiniteStatesSet;

public final class InternalScenarioRepresentation {
    private FiniteStatesSet statesSet = null;
    private FiniteStateMachine statesMachine = null;

    public InternalScenarioRepresentation(FiniteStatesSet statesSet, FiniteStateMachine statesMachine) {
        this.statesSet = statesSet;
        this.statesMachine = statesMachine;
    }

    public FiniteStatesSet getStatesSet() {
        return this.statesSet;
    }

    public FiniteStateMachine getStatesMachine() {
        return this.statesMachine;
    }
}

