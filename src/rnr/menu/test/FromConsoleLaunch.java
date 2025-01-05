/*
 * Decompiled with CFR 0.151.
 */
package rnr.menu.test;

import java.util.Arrays;
import menu.TimeData;
import rnr.console.CommandsReceiver;
import rnr.menu.bigrace.PanelMenuFactoryBigRaceFail;
import rnr.menu.bigrace.PanelMenuFactoryBigRaceSuccess;
import rnr.menu.bigrace.data.PlayerStatisticsForWinPanel;
import rnr.menu.bigrace.data.RaceParticipantStatistics;

public final class FromConsoleLaunch {
    public static void init() {
        CommandsReceiver.addAbbreviation("menu", FromConsoleLaunch.class);
    }

    public static void bigRaceMenuPanelDropOrContinue() {
        PanelMenuFactoryBigRaceFail.createMenuPanelDropOrContinue(3, 1, "KINGS_ROAD");
    }

    public static void bigRaceMenuPanelSemitrailerLost() {
        PanelMenuFactoryBigRaceFail.createMenuPanelSemitrailerLost(4, 2, "KINGS_ROAD");
    }

    public static void bigRaceMenuDefaulted() {
        PanelMenuFactoryBigRaceFail.createMenuPanelDefaulted(0, 0, "KINGS_ROAD", 102, true);
    }

    public static void bigRaceMenuTowedAndDefaulted() {
        PanelMenuFactoryBigRaceFail.createMenuPanelTowedAndDefaulted(4, 1, "KINGS_ROAD", 2, 4, false);
    }

    public static void bigRaceMenuTowedAndDefaultedOnWarehouse() {
        PanelMenuFactoryBigRaceFail.createMenuPanelTowedAndDefaultedOnWarehouse(4, 1, "KINGS_ROAD", 2, 4, false);
    }

    public static void bigRaceMenuCanceledOnWarehouse() {
        PanelMenuFactoryBigRaceFail.createMenuPanelCancelledOnWarehouse(4, 2, "KINGS_ROAD", 1, false);
    }

    public static void bigRaceMenuDamaged() {
        PanelMenuFactoryBigRaceFail.createMenuPanelDamaged(4, 2, "KINGS_ROAD", 1, false);
    }

    public static void bigRaceMenuCanceled() {
        PanelMenuFactoryBigRaceFail.createMenuPanelCancelled(4, 2, "KINGS_ROAD", 1, false);
    }

    public static void bigRaceMenuWin() {
        PlayerStatisticsForWinPanel data = new PlayerStatisticsForWinPanel(101, 102.1, 103.2, 1000.0, 104.0, new TimeData(5, 6, 7), true);
        RaceParticipantStatistics[] statictics = new RaceParticipantStatistics[]{new RaceParticipantStatistics("John", new TimeData(1, 2, 3), 20.2, 10.1, 0, 71.0), new RaceParticipantStatistics("Nick", new TimeData(3, 2, 1), 10.1, 10.0, 1, -71.0)};
        PanelMenuFactoryBigRaceSuccess.createMenuPanelWin(1, 4, 2, "KINGS_ROAD", data, Arrays.asList(statictics));
    }

    public static void bigRaceMenuFinished() {
        PlayerStatisticsForWinPanel data = new PlayerStatisticsForWinPanel(101, 102.1, 103.2, 1000.0, 104.0, new TimeData(5, 6, 7), false);
        RaceParticipantStatistics[] statictics = new RaceParticipantStatistics[]{new RaceParticipantStatistics("John", new TimeData(1, 2, 3), 20.2, 10.1, 0, 71.0), new RaceParticipantStatistics("Nick", new TimeData(3, 2, 1), 10.1, 10.0, 1, 71.0)};
        PanelMenuFactoryBigRaceSuccess.createMenuPanelFinished(5, 1, 1, "KINGS_ROAD", data, Arrays.asList(statictics));
    }
}

