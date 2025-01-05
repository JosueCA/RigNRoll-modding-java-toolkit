/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import java.util.ArrayList;
import menu.menues;
import menuscript.cbvideo.CBVideoAnimation;
import menuscript.cbvideo.MenuNotify;
import rnrscenario.sctask;
import rnrscr.CBVideocall;
import rnrscr.CBVideocallelemnt;
import rnrscr.cbapparatus;

public class cCBVideoCall_Caller
extends sctask {
    private static cCBVideoCall_Caller instance = null;

    public static void initialize() {
        cCBVideoCall_Caller.deinitialize();
        instance = new cCBVideoCall_Caller();
        instance.start();
    }

    public static void deinitialize() {
        if (null != instance) {
            instance.finishImmediately();
            instance = null;
        }
    }

    private cCBVideoCall_Caller() {
        super(3, false);
    }

    public void run() {
        ArrayList<CBVideocallelemnt> v = cbapparatus.getInstance().gCallers();
        for (CBVideocallelemnt aV : v) {
            if (!menues.cancreate_somenu()) continue;
            this.makeCall(aV);
        }
    }

    public static boolean makeImmediateCall(CBVideocall call) {
        if (menues.cancreate_somenu()) {
            instance.makeCall(call);
            return true;
        }
        return false;
    }

    private void makeCall(CBVideocall call) {
        if (!call.gFinished()) {
            if (call.isCallIncoming()) {
                MenuNotify menuNotify = MenuNotify.create(call.gTimeForAnswer());
                menuNotify.addListener(new CBVideoAnimation(call));
            } else {
                new CBVideoAnimation(call).onClose();
            }
            call.start();
        }
    }
}

