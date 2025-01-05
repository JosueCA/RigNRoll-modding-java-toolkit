/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import players.actorveh;
import rnrcore.IXMLSerializable;
import rnrcore.ScriptRef;
import rnrcore.anm;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.vectorJ;

public class ReachPosition
implements anm {
    vectorJ aimedpoint = null;
    actorveh player = null;
    double delta = 0.0;
    int eventid;
    private ScriptRef uid = new ScriptRef();

    public int getUid() {
        return this.uid.getUid(this);
    }

    public void setUid(int value) {
        this.uid.setUid(value);
    }

    public void updateNative(int p) {
    }

    public void removeRef() {
        this.uid.removeRef(this);
    }

    public ReachPosition(int eventid, vectorJ pos, actorveh pl, double distdelta) {
        this.aimedpoint = pos;
        this.player = pl;
        this.delta = distdelta * distdelta;
        this.eventid = eventid;
        eng.CreateInfinitScriptAnimation(this);
    }

    public boolean reach() {
        return this.aimedpoint.len2(this.player.gPosition()) < this.delta;
    }

    public boolean animaterun(double dt) {
        if (this.reach()) {
            event.SetScriptevent(this.eventid);
            return true;
        }
        return false;
    }

    public IXMLSerializable getXmlSerializator() {
        return null;
    }
}

