/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.ArrayList;
import rnrcore.CoreTime;
import rnrscr.IInterruptCall;

public class CBVideoInterruptCalls {
    private ArrayList<IInterruptCall> calls = new ArrayList();
    private static CBVideoInterruptCalls instance;

    private static CBVideoInterruptCalls getInstance() {
        if (instance == null) {
            instance = new CBVideoInterruptCalls();
        }
        return instance;
    }

    public static void add(IInterruptCall call) {
        CBVideoInterruptCalls.getInstance().calls.add(call);
    }

    public static void remove(IInterruptCall call) {
        CBVideoInterruptCalls.getInstance().calls.remove(call);
    }

    public static CoreTime interruptCall(CoreTime from, CoreTime to) {
        return CBVideoInterruptCalls.getInstance()._interruptCall(from, to);
    }

    private CoreTime _interruptCall(CoreTime from, CoreTime to) {
        for (IInterruptCall call : this.calls) {
            CoreTime calltime = call.getInterruptTime();
            if (calltime.moreThan(from) <= 0 || calltime.moreThan(to) >= 0) continue;
            return calltime;
        }
        return to;
    }
}

