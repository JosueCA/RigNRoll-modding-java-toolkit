/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.IXMLSerializable;
import rnrcore.ScriptRef;
import rnrcore.anm;
import rnrcore.eng;
import scriptEvents.EventsControllerHelper;

public class TimerMessage
implements anm {
    private double period = 180.0;
    private String text = null;
    private ScriptRef uid = new ScriptRef();

    public TimerMessage(double time, String message) {
        this.text = message;
        this.period = time;
        this.period = 300.0;
        if (time < 1.0E-4) {
            EventsControllerHelper.messageEventHappened(this.text);
            return;
        }
        eng.CreateInfinitScriptAnimation(this);
    }

    public void setUid(int value) {
        this.uid.setUid(value);
    }

    public int getUid() {
        return this.uid.getUid(this);
    }

    public void removeRef() {
        this.uid.removeRef(this);
    }

    public void updateNative(int p) {
    }

    public boolean animaterun(double dt) {
        if (this.period < dt) {
            EventsControllerHelper.messageEventHappened(this.text);
            return true;
        }
        return false;
    }

    public IXMLSerializable getXmlSerializator() {
        return null;
    }
}

