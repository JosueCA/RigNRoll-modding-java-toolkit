/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import scriptEvents.ScriptEvent;

public final class ScenarioStateNeedMoveEvent
implements ScriptEvent {
    static final long serialVersionUID = 0L;
    private int nodeIntName = -1;
    private String nodeStringName = null;

    public ScenarioStateNeedMoveEvent() {
    }

    public ScenarioStateNeedMoveEvent(String destinationNodeName, int destinationNodeID) {
        if (null == destinationNodeName) {
            throw new IllegalArgumentException("destinationNodeName must be non-null");
        }
        this.nodeIntName = destinationNodeID;
        this.nodeStringName = destinationNodeName;
    }

    public int getNodeIntName() {
        return this.nodeIntName;
    }

    public void setNodeIntName(int nodeIntName) {
        this.nodeIntName = nodeIntName;
    }

    public String getNodeStringName() {
        return this.nodeStringName;
    }

    public void setNodeStringName(String nodeStringName) {
        this.nodeStringName = nodeStringName;
    }

    public String toString() {
        if (null != this.nodeStringName) {
            return "ScenarioStateNeedMoveEvent with nodeStringName " + this.nodeStringName;
        }
        return new String();
    }
}

