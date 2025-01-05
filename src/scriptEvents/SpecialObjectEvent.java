/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import rnrscr.cSpecObjects;
import scriptEvents.ScriptEvent;

public final class SpecialObjectEvent
implements ScriptEvent {
    static final long serialVersionUID = 0L;
    private static final int DEFAULT_STRING_BUILDER_CAPACITY = 100;
    private EventType eventType = EventType.any;
    private int soType = 0;
    private String soName = "unknown";

    public String getObjectName() {
        return this.soName;
    }

    public int getSoType() {
        return this.soType;
    }

    public void setObject(cSpecObjects entered) {
        this.soName = entered.name;
        this.soType = entered.sotip;
    }

    public SpecialObjectEvent(cSpecObjects object, EventType eventType) {
        if (null == object) {
            System.err.println("SpecialObjectEvent.<init>: failed to construct instance, object must be non-null");
        }
        this.soName = object.name;
        this.soType = object.sotip;
        this.eventType = eventType;
    }

    public SpecialObjectEvent(String objectName, int objectType, EventType eventType) {
        if (null == objectName) {
            System.err.println("SpecialObjectEvent.<init>: failed to construct instance, object must be non-null");
        }
        this.soName = objectName;
        this.soType = objectType;
        this.eventType = eventType;
    }

    SpecialObjectEvent() {
    }

    public EventType getEventType() {
        return this.eventType;
    }

    void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(100);
        builder.append("SpecialObjectEvent");
        if (null != this.soName) {
            builder.append(" name == " + this.soName + ';');
        }
        builder.append(" event type == " + this.eventType.name());
        return builder.toString();
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum EventType {
        enter,
        exit,
        f2,
        any,
        none;

    }
}

