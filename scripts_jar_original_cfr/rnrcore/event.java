/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.IScriptTask;
import rnrorg.organaiser;

public class event {
    public static final int eventMainMenuClosed = 25001;
    public static final int eventShowPoliceMessage = 9801;
    public static final int eventEndShowPoliceMessage = 9802;
    public static final int eventDialogFinished = 9850;
    public static final int eventParkingFinished = 25001;
    public static final int eventStartDevelopMotelMenu = 7002;
    public static final int eventEndDevelopMotelMenu = 7003;
    public static final int eventStartDevelopRepairMenu = 2002;
    public static final int eventEndDevelopRepairMenu = 2003;
    public static final int eventStartDevelopOfficeMenu = 6005;
    public static final int eventEndDevelopOfficeMenu = 6006;
    public static final int eventHideKeyboard = 6007;
    public static final int PAKING = 0;
    public static final int BAR_EXITMENU = 1;
    public static final int MOTEL_EXITMENU = 2;
    public static final int MOTEL_DEVELOPFINISHED = 3;
    public static final int NEWCAR_CREATED = 4;
    public static final int OFFICE_EXITMENU = 5;
    public static final int GASSTATION_MESSAGECLOSED = 6;
    public static final int GASSTATION_LEAVE = 7;
    public static final int GASSTATION_CAME = 8;
    public static final int WH_LEAVEGATES = 9;
    public static final int WH_CAME = 10;
    public static final int WH_LEAVE = 11;
    public static final int WH_LOAD = 12;
    public static final int WH_UNLOAD = 13;
    public static final int WH_GOFROMEMPTY = 14;
    public static final int WH_GOFROMLOADED = 15;
    public static final int WH_EXITMENU = 16;
    public static final int STO_CAME = 17;
    public static final int STO_LEAVEGATES = 18;
    public static final int STO_INSIDE = 19;
    long nativePointer;

    public native void eventScript(int var1, String var2, String var3);

    public static native int eventObject(int var0, Object var1, String var2);

    public static native void removeEventObject(int var0);

    public static native void Setevent(int var0);

    public static native void SetScriptevent(long var0);

    public static event CreateEventScript(int ID, String script, String method) {
        event ev = new event();
        ev.eventScript(ID, script, method);
        return ev;
    }

    public void SetScriptevent_NS(long ID) {
        event.SetScriptevent(ID);
    }

    public static native int createScriptObject(int var0, IScriptTask var1);

    public static native void deleteScriptObject(int var0);

    public static native void finishScenarioMission(String var0);

    public static void failScenarioMission(String name) {
        organaiser.declineMission(name);
    }
}

