/*
 * Decompiled with CFR 0.151.
 */
package scenarioMachine;

import java.util.List;
import scenarioMachine.FiniteState;
import scriptActions.ScriptAction;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface FsmActionListener {
    public void stateEntered(FiniteState var1);

    public void stateLeaved(FiniteState var1);

    public void stateProcessed(FiniteState var1);

    public List<ScriptAction> getAllAvalibleActions();
}

