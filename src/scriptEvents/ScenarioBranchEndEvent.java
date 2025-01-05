/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.util.regex.Pattern;
import scriptEvents.ScriptEvent;

public class ScenarioBranchEndEvent
implements ScriptEvent {
    static final long serialVersionUID = 0L;
    private Pattern pattern = null;

    public Pattern getNodeNamePattern() {
        return this.pattern;
    }

    ScenarioBranchEndEvent(String nodeNamePattern) {
        if (null != nodeNamePattern) {
            this.pattern = Pattern.compile(nodeNamePattern);
        }
    }

    public String toString() {
        if (null != this.pattern) {
            return "ScenarioBranchEndEvent; pattern == " + this.pattern.pattern();
        }
        return this.getClass().getName();
    }
}

