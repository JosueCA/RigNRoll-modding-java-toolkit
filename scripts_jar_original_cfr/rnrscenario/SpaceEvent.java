/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import players.actorveh;
import rnrcore.TypicalAnm;
import rnrcore.eng;
import rnrcore.vectorJ;
import rnrscenario.SpaceEventGeometry;
import rnrscenario.SpaceEventListener;

public class SpaceEvent
extends TypicalAnm {
    private boolean m_finished;
    private SpaceEventGeometry m_eventGeometry;
    private actorveh m_car;
    private SpaceEventListener m_listenerEvent;

    public SpaceEvent(SpaceEventGeometry eventGeometry, actorveh car, SpaceEventListener listener) {
        assert (eventGeometry != null);
        assert (car != null);
        this.m_finished = false;
        this.m_eventGeometry = eventGeometry;
        this.m_car = car;
        this.m_listenerEvent = listener;
        eng.CreateInfinitScriptAnimation(this);
    }

    public boolean animaterun(double dt) {
        if (this.m_finished) {
            return true;
        }
        vectorJ position = this.m_car.gPosition();
        boolean hitEvent = this.m_eventGeometry.inEvent(position);
        if (hitEvent) {
            this.m_listenerEvent.hitevent();
        }
        return false;
    }

    public void finish() {
        this.m_finished = true;
    }
}

