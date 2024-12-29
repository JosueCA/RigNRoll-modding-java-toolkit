// Decompiled with: CFR 0.152
// Class Version: 5
package rnrcore;

import menu.JavaEvents;
import rnrcore.INativeMessageEvent;
import rnrcore.NativeEventController;
import rnrcore.eng;
import rnrcore.vectorJ;

public final class Helper {
    private static String SCENE_FINISHED_EVENT = "debug_scene_finished";

    public static void addNativeEventListener(INativeMessageEvent listener) {
        NativeEventController.addNativeEventListener(listener);
    }

    public static void removeNativeEventListener(INativeMessageEvent listener) {
        NativeEventController.removeNativeListener(listener);
    }

    public static void peekNativeMessage(String message) {
        if (!eng.noNative) {
            JavaEvents.SendEvent(46, 0, new NativeMessage(message));
        }
    }

    public static void debugSceneFinishedEvent() {
        if (!eng.noNative) {
            JavaEvents.SendEvent(46, 0, new NativeMessage(SCENE_FINISHED_EVENT));
        }
    }

    public static vectorJ getPackageShift() {
        vectorJ pos = new vectorJ();
        JavaEvents.SendEvent(55, 0, pos);
        return pos;
    }

    public static int isWarehouseWaitCheckin() {
        class A {
            boolean flag;
            int value;

            A() {
            }
        }
        A test = new A();
        JavaEvents.SendEvent(69, 0, test);
        if (test.flag) {
            return test.value;
        }
        return -1;
    }

    public static void waitCheckinOnWarehouse() {
        class A {
            boolean flag;
            int value;

            A() {
            }
        }
        A test = new A();
        JavaEvents.SendEvent(69, 0, test);
        if (!test.flag) {
            eng.err("attempring waitCheckinOnWarehouse on base that is not pending for checking, or our rating is to low to paticipate in race");
            return;
        }
        JavaEvents.SendEvent(69, 1, new Object());
    }

    public static double getTimeToReachPointInSeconds(vectorJ point) {
        Data data = new Data();
        data.pos1 = rnrscr.Helper.getCurrentPosition();
        data.pos2 = point;
        JavaEvents.SendEvent(41, 0, data);
        return data.time;
    }

    public static double getTimeToReachFinishPointIsSeconds(String mission_name) {
        Data data = new Data();
        data.pos2 = data.pos1 = rnrscr.Helper.getCurrentPosition();
        data.mission_name = mission_name;
        JavaEvents.SendEvent(41, 2, data);
        return data.time;
    }

    public static double getTimeToReachFinishPointIsSecondsWarehouseOrder() {
        Data data = new Data();
        data.pos2 = data.pos1 = rnrscr.Helper.getCurrentPosition();
        JavaEvents.SendEvent(41, 3, data);
        return data.time;
    }

    public static boolean hasContest() {
        GameState game_state = new GameState();
        JavaEvents.SendEvent(40, 0, game_state);
        return game_state.has_economy_contest || game_state.has_race_contest || game_state.has_scenario_contest;
    }

    public static boolean winEconomy() {
        GameState game_state = new GameState();
        JavaEvents.SendEvent(40, 0, game_state);
        return !game_state.has_economy_contest && game_state.succeeded_economy_win;
    }

    static class Data {
        vectorJ pos1;
        vectorJ pos2;
        String mission_name;
        double time;

        Data() {
        }
    }

    static class NativeMessage {
        String message;

        NativeMessage() {
        }

        NativeMessage(String message) {
            this.message = message;
        }
    }

    static class GameState {
        boolean has_economy_contest;
        boolean succeeded_economy_win;
        boolean has_scenario_contest;
        boolean succeeded_scenario_win;
        boolean has_race_contest;
        boolean succeeded_race_win;
        boolean last_loose;

        GameState() {
        }
    }
}
