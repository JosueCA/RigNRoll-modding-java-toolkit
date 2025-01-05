/*
 * Decompiled with CFR 0.151.
 */
package menu;

import menu.menues;
import menuscript.WHmenues;

public class NotifyInformation {
    public static boolean notify_should_stop_world = true;
    public static boolean notify_should_show_cursor = true;
    public static boolean notify_should_be_showed = true;
    public static boolean fMainMenuRequested = false;

    public static void reset() {
        notify_should_stop_world = true;
        notify_should_show_cursor = true;
        notify_should_be_showed = true;
    }

    public static void onWHmessages() {
        notify_should_stop_world = false;
        notify_should_show_cursor = false;
        notify_should_be_showed = false;
    }

    public static void onWHmessagesRenew() {
        notify_should_stop_world = false;
        notify_should_show_cursor = true;
        notify_should_be_showed = true;
    }

    public static void notifyAfterInit(long _menu) {
        menues.WindowSet_ShowCursor(_menu, notify_should_show_cursor);
        menues.SetStopWorld(_menu, notify_should_stop_world);
        if (notify_should_be_showed) {
            menues.setShowMenu(_menu, true);
        }
        NotifyInformation.reset();
    }

    public static void changeInteriorAfterInit(long _menu) {
        menues.WindowSet_ShowCursor(_menu, false);
        menues.SetStopWorld(_menu, false);
        menues.setShowMenu(_menu, true);
    }

    public static void leaveWarehouseAfterInit(long _menu) {
        NotifyInformation.changeInteriorAfterInit(_menu);
    }

    public static void requestMainMenu() {
        fMainMenuRequested = true;
    }

    public static void hasDeliveryMessages() {
        WHmenues.whmenu_immediateshow = true;
    }

    public static void startShowMessages() {
        NotifyInformation.onWHmessages();
        fMainMenuRequested = false;
    }

    public static void hasOneMessage() {
        if (!fMainMenuRequested) {
            NotifyInformation.onWHmessages();
        }
        fMainMenuRequested = false;
    }

    public static void panelFolded() {
        WHmenues.whmenu_immediateshow = false;
        fMainMenuRequested = false;
    }

    public static void panelUnFolded() {
        WHmenues.whmenu_immediateshow = true;
    }
}

