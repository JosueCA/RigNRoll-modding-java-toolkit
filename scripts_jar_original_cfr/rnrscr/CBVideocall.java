/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import players.IcontaktCB;
import rnrscr.incomingCall;

public interface CBVideocall
extends incomingCall {
    public IcontaktCB who();

    public void registerMesageCallBack(Object var1, String var2);

    public void registerRequestCallBack(Object var1, String var2);

    public void start();

    public void pause(boolean var1);

    public void answer(int var1);

    public boolean gFinished();

    public float gTimeForAnswer();

    public boolean talkAnyWay();

    public String getDialogName();

    public String getResourceName();
}

