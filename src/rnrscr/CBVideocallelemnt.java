/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import players.IcontaktCB;
import players.aiplayer;
import rnrscr.CBVideocall;
import rnrscr.cbdialogmessage;
import scriptEvents.CbVideoEvent;
import scriptEvents.EventsController;

public class CBVideocallelemnt
implements CBVideocall {
    public long nativePointer;
    public aiplayer whocalls;
    String dialogname;
    private String store_name;
    boolean finished;
    public float timeforanswer = 10.0f;
    public boolean talkanyway = true;
    private String dilaog_name = null;

    public void pause(boolean value) {
        this.dialogpause(value);
    }

    public boolean talkAnyWay() {
        return this.talkanyway;
    }

    public float gTimeForAnswer() {
        return this.timeforanswer;
    }

    public String incommingCallMessage() {
        return this.incommingMessage();
    }

    public void setStoreName(String store_name) {
        this.store_name = store_name;
    }

    public void setDialogName(String name) {
        this.dilaog_name = name;
    }

    public String getDialogName() {
        return this.dilaog_name;
    }

    CBVideocallelemnt(aiplayer pl, String dial) {
        this.whocalls = pl;
        this.dialogname = dial;
        this.finished = false;
    }

    public String getResourceName() {
        return this.store_name;
    }

    public IcontaktCB who() {
        return this.whocalls.createCBContacter();
    }

    public void registerMesageCallBack(Object obj, String methodname) {
        this.registerMesageCB(obj, methodname);
    }

    public void registerRequestCallBack(Object obj, String methodname) {
        this.registerRequestCB(obj, methodname);
    }

    public void start() {
        this.dialogstart();
        EventsController.getInstance().eventHappen(new CbVideoEvent(this.store_name, CbVideoEvent.EventType.START, 0));
    }

    public void answer(int res) {
        this.requestanswer(res);
        EventsController.getInstance().eventHappen(new CbVideoEvent(this.store_name, CbVideoEvent.EventType.ANSWER, res));
    }

    public void endfromnative() {
        EventsController.getInstance().eventHappen(new CbVideoEvent(this.store_name, CbVideoEvent.EventType.FINISH, 0));
        this.finished = true;
    }

    public boolean gFinished() {
        return this.finished;
    }

    void examplecallback(cbdialogmessage mess) {
    }

    void examplerequestcallback(int _state) {
    }

    public boolean isCallIncoming() {
        return this.isCallIncomingNative();
    }

    public native void registerMesageCB(Object var1, String var2);

    public native void registerRequestCB(Object var1, String var2);

    public native void requestanswer(int var1);

    public native void dialogstart();

    public native void dialogpause(boolean var1);

    public native void initiate();

    public native String incommingMessage();

    public native boolean isCallIncomingNative();
}

