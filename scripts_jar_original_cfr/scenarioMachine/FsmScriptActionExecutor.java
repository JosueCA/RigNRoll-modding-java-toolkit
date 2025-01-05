/*
 * Decompiled with CFR 0.151.
 */
package scenarioMachine;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import scenarioMachine.FiniteState;
import scenarioMachine.FsmActionListener;
import scriptActions.ScriptAction;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class FsmScriptActionExecutor
implements FsmActionListener {
    private final LinkedList<ScriptAction> onEnterActions = new LinkedList();
    private final LinkedList<ScriptAction> onLeavedActions = new LinkedList();
    private final LinkedList<ScriptAction> onProcessActions = new LinkedList();

    @Override
    public void stateEntered(FiniteState state) {
        for (ScriptAction action : this.onEnterActions) {
            action.act();
        }
    }

    @Override
    public void stateLeaved(FiniteState state) {
        for (ScriptAction action : this.onLeavedActions) {
            action.act();
        }
    }

    @Override
    public void stateProcessed(FiniteState state) {
        for (ScriptAction action : this.onProcessActions) {
            action.act();
        }
    }

    void addOnEnterAction(ScriptAction action) {
        if (null == action) {
            throw new IllegalArgumentException("action must be non-null reference");
        }
        this.onEnterActions.add(action);
    }

    void addOnLeaveAction(ScriptAction action) {
        if (null == action) {
            throw new IllegalArgumentException("action must be non-null reference");
        }
        this.onLeavedActions.add(action);
    }

    void addOnProcessAction(ScriptAction action) {
        if (null == action) {
            throw new IllegalArgumentException("action must be non-null reference");
        }
        this.onProcessActions.add(action);
    }

    @Override
    public List<ScriptAction> getAllAvalibleActions() {
        return Collections.emptyList();
    }
}

