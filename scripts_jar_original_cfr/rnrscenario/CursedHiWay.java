/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import menu.JavaEvents;
import menu.menues;
import rnrcore.CursedHiWayObjectXmlSerializable;
import rnrcore.ObjectXmlSerializable;
import rnrcore.TypicalAnm;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.vectorJ;
import rnrscenario.StaticScenarioStuff;
import rnrscr.Helper;
import scriptEvents.EventsControllerHelper;

public class CursedHiWay
extends TypicalAnm {
    private static final int[] EVENTS = new int[]{21350, 21380, 21370};
    private static final String[] EVENT_METHODS = new String[]{"on_enter_zone", "on_exit_zone", "on_downfall"};
    private static final String DWORD = "DWORD_CursedHiWay";
    private static final String[] MESSAGES = new String[]{"1370_enter", "1370_exit", "1370_downfall", "sc01300 failed", "sc01300 loaded"};
    private static final int MESSAGE_ENTER = 0;
    private static final int MESSAGE_EXIT = 1;
    private static final int MESSAGE_DOWNFALL = 2;
    private static final int MESSAGE_QUESTFAILED = 3;
    private static final int MESSAGE_QUESTLOADED = 4;
    private static final String METHOD_LOAD = "onLoad";
    private static final String METHOD_FAIL = "onFail";
    private static final double VELOCITY_DECREASE = 0.05;
    private static CursedHiWay instance = null;
    private int[] event_ids = null;
    private double last_time = 0.0;
    private boolean first_time = true;
    private InfluenceZone zone = null;
    private vectorJ positionInfluenceZone = null;
    private boolean on_animation = false;
    private boolean release_animation = false;
    private boolean stop_animation = false;
    private double last_coef = 0.0;
    private boolean dead_from_mist = false;
    private ObjectXmlSerializable serializator = null;

    public static void finishCursedHiWay() {
        if (null == instance) {
            return;
        }
        CursedHiWay.instance.stop_animation = true;
        eng.setdword(DWORD, 0);
        if (null != CursedHiWay.instance.event_ids) {
            for (int i = 0; i < CursedHiWay.instance.event_ids.length; ++i) {
                event.removeEventObject(CursedHiWay.instance.event_ids[i]);
            }
        }
        instance = null;
        JavaEvents.SendEvent(72, 0, new Data(0.0));
    }

    public CursedHiWay() {
        instance = this;
        StaticScenarioStuff.makeReadyCursedHiWay(true);
        EventsControllerHelper.getInstance().addMessageListener(this, METHOD_LOAD, MESSAGES[4]);
        EventsControllerHelper.getInstance().addMessageListener(this, METHOD_FAIL, MESSAGES[3]);
        this.serializator = new CursedHiWayObjectXmlSerializable(this);
        this.serializator.registerObjectXmlSerializable();
    }

    public void onLoad() {
        this.event_ids = new int[EVENTS.length];
        for (int i = 0; i < EVENTS.length; ++i) {
            this.event_ids[i] = event.eventObject(EVENTS[i], this, EVENT_METHODS[i]);
        }
        eng.setdword(DWORD, 1);
    }

    public void onFail() {
        if (!this.on_animation) {
            // empty if block
        }
    }

    public void on_enter_zone() {
        this.on_animation = true;
        EventsControllerHelper.messageEventHappened(MESSAGES[0]);
        this.positionInfluenceZone = Helper.getCurrentPosition();
        this.zone = new InfluenceZone(this.positionInfluenceZone);
        eng.CreateInfinitScriptAnimation(this);
    }

    public void on_exit_zone() {
        this.release_animation = true;
        EventsControllerHelper.messageEventHappened(MESSAGES[1]);
        eng.enableCabinView(true);
    }

    public void on_downfall() {
        this.stop_animation = true;
        EventsControllerHelper.messageEventHappened(MESSAGES[2]);
    }

    public boolean animaterun(double dt) {
        if (this.stop_animation) {
            this.serializator.unRegisterObjectXmlSerializable();
            return true;
        }
        if (this.dead_from_mist) {
            JavaEvents.SendEvent(72, 0, new Data(1.0));
            this.serializator.unRegisterObjectXmlSerializable();
            return true;
        }
        if (this.first_time) {
            this.last_time = dt;
            this.first_time = false;
        }
        double diff = 0.05 * (dt - this.last_time);
        if (this.release_animation || !this.zone.isInside()) {
            diff *= -1.0;
        }
        this.last_coef += diff;
        if (this.last_coef > 1.0) {
            this.last_coef = 1.0;
            this.dead_from_mist = true;
            menues.gameoverMenu();
            eng.disableControl();
        } else if (this.last_coef < 0.0) {
            this.last_coef = 0.0;
        }
        JavaEvents.SendEvent(72, 0, new Data(this.last_coef));
        if (this.last_coef > 0.001) {
            eng.enableCabinView(false);
        } else {
            eng.enableCabinView(true);
        }
        this.last_time = dt;
        return false;
    }

    public boolean isDead_from_mist() {
        return this.dead_from_mist;
    }

    public void setDead_from_mist(boolean dead_from_mist) {
        this.dead_from_mist = dead_from_mist;
    }

    public double getLast_coef() {
        return this.last_coef;
    }

    public void setLast_coef(double last_coef) {
        this.last_coef = last_coef;
    }

    public boolean isOn_animation() {
        return this.on_animation;
    }

    public void setOn_animation(boolean on_animation) {
        this.on_animation = on_animation;
    }

    public vectorJ getPositionInfluenceZone() {
        return this.positionInfluenceZone;
    }

    public void setPositionInfluenceZone(vectorJ value) {
        if (null == value) {
            return;
        }
        this.positionInfluenceZone = value;
        this.zone = new InfluenceZone(this.positionInfluenceZone);
    }

    public boolean isRelease_animation() {
        return this.release_animation;
    }

    public void setRelease_animation(boolean release_animation) {
        this.release_animation = release_animation;
    }

    public boolean isStop_animation() {
        return this.stop_animation;
    }

    public void setStop_animation(boolean stop_animation) {
        this.stop_animation = stop_animation;
    }

    static class InfluenceZone {
        vectorJ P0;
        vectorJ n;
        vectorJ m;
        double n_coef;
        double m_coef;

        InfluenceZone(vectorJ posP0) {
            this.P0 = posP0;
            this.P0.z = 0.0;
            vectorJ pos = eng.getControlPointPosition("Cursed Highway Scene");
            pos.z = 0.0;
            this.n = pos.oMinusN(this.P0);
            this.m = this.n.oCross(new vectorJ(0.0, 0.0, 1.0));
            this.m.norm();
            this.m.mult(100.0);
            vectorJ shift_p0 = new vectorJ(this.m);
            shift_p0.mult(0.5);
            this.P0.oMinus(shift_p0);
            this.n_coef = this.n.length();
            this.n_coef *= this.n_coef;
            this.m_coef = this.m.length();
            this.m_coef *= this.m_coef;
            this.n_coef = 1.0 / this.n_coef;
            this.m_coef = 1.0 / this.m_coef;
        }

        boolean isInside() {
            vectorJ pos = Helper.getCurrentPosition();
            pos.oMinus(this.P0);
            double pos_n = pos.dot(this.n) * this.n_coef;
            if (pos_n < 0.0 || pos_n > 1.0) {
                return false;
            }
            double pos_m = pos.dot(this.m) * this.m_coef;
            return !(pos_m < 0.0) && !(pos_m > 1.0);
        }
    }

    static class Data {
        double value;

        Data() {
            this.value = 0.0;
        }

        Data(double value) {
            this.value = value;
        }
    }
}

