/*
 * Decompiled with CFR 0.151.
 */
package scenarioMachine;

import java.util.LinkedList;
import java.util.List;
import scenarioMachine.FiniteState;
import scriptActions.ScriptAction;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface EventStateProcessor {
    public FiniteState processEvent(List<ScriptEvent> var1, LinkedList<FiniteState> var2, FiniteState var3);

    public List<ScriptAction> getAllAvalibleActions(List var1);

    public List<List<ScriptEvent>> getAllAvalibleEvents();

    public List<Integer> getDataToSave();

    public void restoreFromData(List<Integer> var1);
}

