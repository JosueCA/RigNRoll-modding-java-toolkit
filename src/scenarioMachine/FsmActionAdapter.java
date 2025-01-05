/*
 * Decompiled with CFR 0.151.
 */
package scenarioMachine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import scenarioMachine.FiniteState;
import scenarioMachine.FsmActionListener;
import scriptActions.ScriptAction;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class FsmActionAdapter
implements FsmActionListener {
    private ArrayList<ScriptAction> actionList = null;

    protected final void actAllActions() {
        for (ScriptAction newVar : this.actionList) {
            newVar.act();
        }
    }

    public FsmActionAdapter(List<ScriptAction> actionList) {
        if (null != actionList) {
            this.actionList = new ArrayList<ScriptAction>(actionList);
        }
    }

    // @Override
    public void stateEntered(FiniteState state) {
    }

    // @Override
    public void stateLeaved(FiniteState state) {
    }

    // @Override
    public void stateProcessed(FiniteState state) {
    }

    // @Override
    public List<ScriptAction> getAllAvalibleActions() {
        if (null == this.actionList || this.actionList.isEmpty()) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(this.actionList);
    }
}

