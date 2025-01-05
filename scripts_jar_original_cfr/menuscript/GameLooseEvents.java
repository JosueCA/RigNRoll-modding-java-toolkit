/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.JavaEvents;
import menuscript.TotalVictoryMenu;
import menuscript.VictoryMenu;
import menuscript.VictoryMenuExitListener;
import rnrcore.Helper;
import rnrcore.event;

public class GameLooseEvents {
    private static final boolean DEBUG_SHOW_TOTAL = false;

    public static void createEconomyLooseMenu() {
        VictoryMenu.createLooseEconomy(new VictoryMenuCloseListener());
    }

    public static void createSportLooseMenu() {
        VictoryMenu.createLooseSport(new VictoryMenuCloseListener());
    }

    public static void createSportWinMenu() {
        VictoryMenu.createWinSport(new VictoryMenuCloseListener());
    }

    static class TotalMenuCloseListener
    implements VictoryMenuExitListener {
        TotalMenuCloseListener() {
        }

        public void OnMenuExit(int result) {
            switch (result) {
                case 1: {
                    JavaEvents.SendEvent(23, 1, null);
                }
            }
            event.Setevent(9001);
        }
    }

    static class VictoryMenuCloseListener
    implements VictoryMenuExitListener {
        VictoryMenuCloseListener() {
        }

        public void OnMenuExit(int result) {
            boolean hasAnyContest = Helper.hasContest();
            if (!hasAnyContest) {
                TotalVictoryMenu.createGameOverTotal(new TotalMenuCloseListener());
            } else {
                event.Setevent(9001);
            }
        }
    }
}

