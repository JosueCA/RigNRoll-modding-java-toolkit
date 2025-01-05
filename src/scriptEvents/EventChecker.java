/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.util.List;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface EventChecker {
    public boolean checkEvent(List<ScriptEvent> var1);

    public ScriptEvent lastPossetiveChecked();

    public List<ScriptEvent> getExpectantEvent();

    public String isValid();

    public void deactivateChecker();
}

