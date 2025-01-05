/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import players.aiplayer;
import rnrscenario.cCBVideoCall_Caller;
import rnrscr.CBVideocallelemnt;
import rnrscr.cbapparatus;

public class CBVideoStroredCall {
    public String name = "unnamed";
    public String dialogname = "no dialog";
    private aiplayer who;
    public float timecall = 10.0f;
    public boolean talkanyway = true;

    CBVideoStroredCall(String name, String dialogname, float timecall, boolean talkanyway) {
        this.name = name;
        this.dialogname = dialogname;
        this.timecall = timecall;
        this.talkanyway = talkanyway;
    }

    public void setIdentitie(String identitie) {
        this.who = new aiplayer(identitie);
    }

    public void makecall(String identitie, String mission_name) {
        this.who = new aiplayer(identitie);
        CBVideocallelemnt call = cbapparatus.getInstance().makecall(this.who, this.dialogname);
        call.setStoreName(this.name);
        call.timeforanswer = this.timecall;
        call.talkanyway = this.talkanyway;
    }

    public void makecall() {
        CBVideocallelemnt call = cbapparatus.getInstance().makecall(this.who, this.dialogname);
        call.setStoreName(this.name);
        call.timeforanswer = this.timecall;
        call.talkanyway = this.talkanyway;
    }

    CBVideocallelemnt makeImmediateCall() {
        CBVideocallelemnt call = cbapparatus.getInstance().makecall(this.who, this.dialogname);
        call.setStoreName(this.name);
        call.timeforanswer = this.timecall;
        call.talkanyway = this.talkanyway;
        cCBVideoCall_Caller.makeImmediateCall(call);
        return call;
    }
}

