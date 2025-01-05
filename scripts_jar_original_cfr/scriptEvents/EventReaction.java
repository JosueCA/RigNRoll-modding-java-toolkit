/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.io.Serializable;
import java.util.List;
import scriptActions.ScriptAction;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class EventReaction
implements Serializable {
    public static final int DEFAULT_UID = 0;
    private int uid = 0;

    EventReaction(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return this.uid;
    }

    abstract boolean react(List<ScriptEvent> var1);

    abstract ScriptEvent getLastReacted();

    abstract List<ScriptAction> getAllAvalibleReactions();

    abstract List<ScriptEvent> getAllAvalibleEvents();

    abstract void deactivateReactor();
}

