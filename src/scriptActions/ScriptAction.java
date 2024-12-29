// Decompiled with: CFR 0.152
// Class Version: 5
package scriptActions;

import java.util.LinkedList;
import java.util.List;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class ScriptAction
implements Comparable<ScriptAction> {
    static final int HIGH_PRIORITY = 4;
    static final int MEDIUM_PRIORITY = 8;
    static final int LOW_PRIORITY = 16;
    public static final String DATA_NODE_STRING = "data";
    private int priority;

    public static List<ScriptAction> pack(ScriptAction action) {
        LinkedList<ScriptAction> result = new LinkedList<ScriptAction>();
        result.add(action);
        return result;
    }

    public ScriptAction() {
        this.priority = 4;
    }

    public ScriptAction(int priority) {
        this.priority = priority;
    }

    // @Override
    public int compareTo(ScriptAction o) {
        if (null == o) {
            return -1;
        }
        if (this.priority < o.priority) {
            return -1;
        }
        if (this.priority > o.priority) {
            return 1;
        }
        return 0;
    }

    public boolean validate() {
        return true;
    }

    public boolean hasChildAction() {
        return false;
    }

    public ScriptAction getChildAction() {
        return null;
    }

    public boolean actActionAsScenarioNode() {
        return false;
    }

    public ScriptEvent getExactEventForConditionOnActivate() {
        return null;
    }

    public ScriptEvent getExactEventForConditionOnDeactivate() {
        return null;
    }

    public abstract void act();
}
