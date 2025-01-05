/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import java.util.ArrayList;
import java.util.Vector;
import menu.DateData;
import menu.PastFewDaysMenu;
import menu.ProgressIndicatorMenu;
import menu.TimeData;
import menu.Titres;
import menu.menues;
import menu.pager;
import menuscript.AnswerMenu;
import menuscript.BannerMenu;
import menuscript.BarMenu;
import menuscript.BikMenu;
import menuscript.BillOfLadingMenu;
import menuscript.EscapeMenu;
import menuscript.HeadUpDisplay;
import menuscript.MessageDebtSale;
import menuscript.MessageWindow;
import menuscript.MissionSuccessPicture;
import menuscript.Motelmenues;
import menuscript.NotifyGameOver;
import menuscript.PanelMenu;
import menuscript.PoliceMenu;
import menuscript.RacePanelMenu;
import menuscript.RoadService;
import menuscript.STOmenues;
import menuscript.ScenarioBigRaceConfirmation;
import menuscript.TotalVictoryMenu;
import menuscript.VictoryMenu;
import menuscript.WHmenues;
import menuscript.cbvideo.Dialogitem;
import menuscript.cbvideo.MenuCall;
import menuscript.cbvideo.MenuCallFullDialog;
import menuscript.cbvideo.MenuNotify;
import menuscript.gasstationmenu;
import menuscript.mainmenu.StartMenu;
import menuscript.menuBridgeToll;
import menuscript.office.OfficeMenu;
import menuscript.org.OrganiserMenu;
import players.IcontaktCB;
import rnrcore.CoreTime;
import rnrcore.SCRuniperson;
import rnrcore.vectorJ;
import rnrscr.ILeaveMenuListener;

public class CreateAllMenues {
    public long lastMenuCreated = 0L;

    public void introMENU() {
        this.lastMenuCreated = BikMenu.createMenu();
    }

    public void mainMENU() {
        this.lastMenuCreated = StartMenu.create();
    }

    public void escMENU() {
        this.lastMenuCreated = EscapeMenu.CreateEscapeMenu();
    }

    public void tutorialTruckStopMENU() {
        this.lastMenuCreated = MessageWindow.CreateMessageWindow("TUTORIAL - WAREHOUSE", true, true, 30.0, "ESC", true, false);
    }

    public void tutorialWarehouseMENU() {
        this.lastMenuCreated = MessageWindow.CreateMessageWindow("TUTORIAL - TRUCKSTOP", true, true, 30.0, "ESC", true, false);
    }

    public void f2gasMENU() {
        this.lastMenuCreated = new menues().SpecObjectMessage(2);
    }

    public void f2repairMENU() {
        this.lastMenuCreated = new menues().SpecObjectMessage(3);
    }

    public void f2barMENU() {
        this.lastMenuCreated = new menues().SpecObjectMessage(4);
    }

    public void f2motelMENU() {
        this.lastMenuCreated = new menues().SpecObjectMessage(6);
    }

    public void f2officeMENU() {
        this.lastMenuCreated = new menues().SpecObjectMessage(7);
    }

    public void f2policeMENU() {
        this.lastMenuCreated = new menues().SpecObjectMessage(8);
    }

    public void f2defaultMENU() {
        this.lastMenuCreated = new menues().SpecObjectMessage(10);
    }

    public void headupMENU() {
        this.lastMenuCreated = HeadUpDisplay.create();
    }

    public void cbvnotifyMENU() {
        this.lastMenuCreated = MenuNotify.create(1000000.0).getMenuDescriptor();
    }

    public void cbvcallMENU() {
        this.lastMenuCreated = MenuCall.create().getMenuDescriptor();
    }

    public void cbvfulldialogMENU() {
        ArrayList<Dialogitem> items = new ArrayList<Dialogitem>();
        for (int i = 0; i < 5; ++i) {
            Dialogitem item = new Dialogitem(new ContacterDummy(), "blah blah");
            items.add(item);
        }
        this.lastMenuCreated = MenuCallFullDialog.create(items).getMenuDescriptor();
    }

    public void pagerMENU() {
        this.lastMenuCreated = menues.createSimpleMenu(new pager());
    }

    public void banner01MENU() {
        this.lastMenuCreated = BannerMenu.CreateBannerMenu(1, "source", "destination", "RaceName", "Logoname", 100, 100.0, new TimeData(), new DateData());
    }

    public void banner02MENU() {
        this.lastMenuCreated = BannerMenu.CreateBannerMenu(2, "source", "destination", "RaceName", "Logoname", 100, 100.0, new TimeData(), new DateData());
    }

    public void banner03MENU() {
        this.lastMenuCreated = BannerMenu.CreateBannerMenu(3, "source", "destination", "RaceName", "Logoname", 100, 100.0, new TimeData(), new DateData());
    }

    public void banner04MENU() {
        this.lastMenuCreated = BannerMenu.CreateBannerMenu(4, "source", "destination", "RaceName", "Logoname", 100, 100.0, new TimeData(), new DateData());
    }

    public void racecheckin01MENU() {
        this.lastMenuCreated = BannerMenu.CreateStellaCheckIn(1, new CoreTime());
    }

    public void racecheckin02MENU() {
        this.lastMenuCreated = BannerMenu.CreateStellaCheckIn(2, new CoreTime());
    }

    public void racecheckin03MENU() {
        this.lastMenuCreated = BannerMenu.CreateStellaCheckIn(3, new CoreTime());
    }

    public void racecheckin04MENU() {
        this.lastMenuCreated = BannerMenu.CreateStellaCheckIn(4, new CoreTime());
    }

    public void racestartin01MENU() {
        this.lastMenuCreated = BannerMenu.CreateStellaStartIn(1);
    }

    public void racestartin02MENU() {
        this.lastMenuCreated = BannerMenu.CreateStellaStartIn(2);
    }

    public void racestartin03MENU() {
        this.lastMenuCreated = BannerMenu.CreateStellaStartIn(3);
    }

    public void racestartin04MENU() {
        this.lastMenuCreated = BannerMenu.CreateStellaStartIn(4);
    }

    public void stellaPreparingToOrdersMENU() {
        this.lastMenuCreated = BannerMenu.CreateStellaPrepareToOrders();
    }

    public void stellaPreparingToRaceMENU() {
        this.lastMenuCreated = BannerMenu.CreateStellaPrepareToRace();
    }

    public void stellaWilcomMENU() {
        this.lastMenuCreated = BannerMenu.CreateStellaWelcome("some city");
    }

    public void barMENU() {
        this.lastMenuCreated = BarMenu.CreateBarMenu("bar name", new vectorJ(), null);
    }

    public void officeMENU() {
        this.lastMenuCreated = OfficeMenu.create();
    }

    public void gasMENU() {
        this.lastMenuCreated = menues.createSimpleMenu(new gasstationmenu());
    }

    public void motelMENU() {
        this.lastMenuCreated = menues.createSimpleMenu(new Motelmenues());
    }

    public void stoMENU() {
        this.lastMenuCreated = menues.createSimpleMenu(new STOmenues());
    }

    public void warehouseMENU() {
        this.lastMenuCreated = menues.createSimpleMenu(new WHmenues());
    }

    public void preparetoRaceInMENU() {
        this.lastMenuCreated = RacePanelMenu.PanelPreparingToRaceIn();
    }

    public void preparetoRaceOutMENU() {
        this.lastMenuCreated = RacePanelMenu.PanelPreparingToRaceOut();
    }

    public void preparetoOrdersInMENU() {
        this.lastMenuCreated = RacePanelMenu.PanelPreparingToOrdersIn();
    }

    public void preparetoOrdersOutMENU() {
        this.lastMenuCreated = RacePanelMenu.PanelPreparingToOrdersOut();
    }

    public void participantsRaceIn01MENU() {
        RacePanelMenu.ParticipantInfo[] info = new RacePanelMenu.ParticipantInfo[]{new RacePanelMenu.ParticipantInfo("VANO", 100.0, 1, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 2, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 3, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 4, 2)};
        this.lastMenuCreated = RacePanelMenu.PanelListOfParticipantsIn(1, "racename", "logoname", info);
    }

    public void participantsRaceOut01MENU() {
        RacePanelMenu.ParticipantInfo[] info = new RacePanelMenu.ParticipantInfo[]{new RacePanelMenu.ParticipantInfo("VANO", 100.0, 1, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 2, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 3, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 4, 2)};
        this.lastMenuCreated = RacePanelMenu.PanelListOfParticipantsOut(1, "racename", "logoname", info);
    }

    public void participantsRaceIn02MENU() {
        RacePanelMenu.ParticipantInfo[] info = new RacePanelMenu.ParticipantInfo[]{new RacePanelMenu.ParticipantInfo("VANO", 100.0, 1, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 2, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 3, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 4, 2)};
        this.lastMenuCreated = RacePanelMenu.PanelListOfParticipantsIn(2, "racename", "logoname", info);
    }

    public void participantsRaceOut02MENU() {
        RacePanelMenu.ParticipantInfo[] info = new RacePanelMenu.ParticipantInfo[]{new RacePanelMenu.ParticipantInfo("VANO", 100.0, 1, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 2, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 3, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 4, 2)};
        this.lastMenuCreated = RacePanelMenu.PanelListOfParticipantsOut(2, "racename", "logoname", info);
    }

    public void participantsRaceIn03MENU() {
        RacePanelMenu.ParticipantInfo[] info = new RacePanelMenu.ParticipantInfo[]{new RacePanelMenu.ParticipantInfo("VANO", 100.0, 1, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 2, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 3, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 4, 2)};
        this.lastMenuCreated = RacePanelMenu.PanelListOfParticipantsIn(3, "racename", "logoname", info);
    }

    public void participantsRaceOut03MENU() {
        RacePanelMenu.ParticipantInfo[] info = new RacePanelMenu.ParticipantInfo[]{new RacePanelMenu.ParticipantInfo("VANO", 100.0, 1, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 2, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 3, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 4, 2)};
        this.lastMenuCreated = RacePanelMenu.PanelListOfParticipantsOut(3, "racename", "logoname", info);
    }

    public void participantsRaceIn04MENU() {
        RacePanelMenu.ParticipantInfo[] info = new RacePanelMenu.ParticipantInfo[]{new RacePanelMenu.ParticipantInfo("VANO", 100.0, 1, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 2, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 3, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 4, 2)};
        this.lastMenuCreated = RacePanelMenu.PanelListOfParticipantsIn(4, "racename", "logoname", info);
    }

    public void participantsRaceOut04MENU() {
        RacePanelMenu.ParticipantInfo[] info = new RacePanelMenu.ParticipantInfo[]{new RacePanelMenu.ParticipantInfo("VANO", 100.0, 1, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 2, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 3, 2), new RacePanelMenu.ParticipantInfo("VANO", 100.0, 4, 2)};
        this.lastMenuCreated = RacePanelMenu.PanelListOfParticipantsOut(4, "racename", "logoname", info);
    }

    public void notinareaMENU() {
        this.lastMenuCreated = PanelMenu.PanelCommonNotInArea("some");
    }

    public void ordernothereMENU() {
        this.lastMenuCreated = PanelMenu.PanelCommonOrderNotHere("some");
    }

    public void missionothereMENU() {
        this.lastMenuCreated = PanelMenu.PanelCommonMissionNotHere("some");
    }

    public void semilostMENU() {
        this.lastMenuCreated = PanelMenu.PanelCommonSemitrailerLost("some");
    }

    public void deliveryFirstMENU() {
        this.lastMenuCreated = PanelMenu.PanelDeliveryFirst("some", 0, 0, 0, 0.0);
    }

    public void deliveryExecutedMENU() {
        this.lastMenuCreated = PanelMenu.PanelDeliveryExecuted("some", 0, 0, 0, 0.0);
    }

    public void contestFirstMENU() {
        this.lastMenuCreated = PanelMenu.PanelContestFirst("some", new PanelMenu.Time(), 0.0, 0.0, 0.0, 0, 0);
    }

    public void contestExecutedMENU() {
        this.lastMenuCreated = PanelMenu.PanelContestExecuted("some", 1, new PanelMenu.Time(), 0.0, 0.0, 0.0, 0);
    }

    public void contestDefaultMENU() {
        this.lastMenuCreated = PanelMenu.PanelContestDefaulted("some", 0.0, 0);
    }

    public void tenderFirstMENU() {
        this.lastMenuCreated = PanelMenu.PanelTenderFirst("some", 1, new PanelMenu.Time(), 0.0, 0.0, 0.0, 0);
    }

    public void tenderLateMENU() {
        this.lastMenuCreated = PanelMenu.PanelTenderLate("some", 0.0, 0);
    }

    public void forfeitEvacuationMENU() {
        this.lastMenuCreated = PanelMenu.PanelCommonForfeitEvacuation("some", 0, 0, 0.0, 0);
    }

    public void missionCanceledMENU() {
        this.lastMenuCreated = PanelMenu.PanelCommonMissionCancelled("some");
    }

    public void orderCanceledMENU() {
        this.lastMenuCreated = PanelMenu.PanelDeliveryOrderCancelled("some", 0.0, 0, 0, true);
    }

    public void orderCanceledCommonMENU() {
        this.lastMenuCreated = PanelMenu.PanelDeliveryCommonCancelled("some", 0.0, false);
    }

    public void towerdMENU() {
        this.lastMenuCreated = PanelMenu.PanelCommonTowed("some", 0, 0);
    }

    public void deliveryDamagedMENU() {
        this.lastMenuCreated = PanelMenu.PanelDeliveryDamaged("some", 0, 0.0, 0, true);
    }

    public void deliveryExpiredMENU() {
        this.lastMenuCreated = PanelMenu.PanelDeliveryExpired("some", 0, 0.0, 0, true);
    }

    public void tenderDefaultMENU() {
        this.lastMenuCreated = PanelMenu.PanelTenderDefaulted("some", 0.0, 0);
    }

    public void deliveryTowedCanceledMENU() {
        this.lastMenuCreated = PanelMenu.PanelDeliveryTowedCancelled("some", 0, 0, 0.0, 0);
    }

    public void deliveryTowedMENU() {
        this.lastMenuCreated = PanelMenu.PanelDeliveryTowed("some", 0, 0);
    }

    public void raceQualified01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelQualified(1, "racename", "logoname", 1, 1.0, 1.0, "destination", "checkpoint", new TimeData(), "driver", 0.0, 2, 23);
    }

    public void raceNotQualified01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelNotqualified(1, "racename", "logoname", 1, 1.0, 1.0, "destination", "checkpoint", new TimeData(), "driver", 0.0);
    }

    public void raceCheckPointFirst01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelCheckpointFirst(1, "source", "destination", "racename", "logoname", true, "finita", "nexta", new TimeData(), 1.0, new TimeData(), 0.0, 0.0);
    }

    public void raceDropOrContinue01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelDropOrContinue(1, "racename", "logoname");
    }

    public void raceCheckpointMissed01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelCheckpointMissed(1, "racename", "logoname", "next", new TimeData(), "finish");
    }

    public void raceNotAParticipant01MENU() {
        this.lastMenuCreated = RacePanelMenu.RaceNotAParticipant(1, "racename", "logoname");
    }

    public void raceWinGold01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(1, "source", "destination", "racename", "logoname", 0, 0, 0.0, new TimeData(), 0.0, 0.0, 1);
    }

    public void raceWinSilver01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(1, "source", "destination", "racename", "logoname", 1, 0, 0.0, new TimeData(), 0.0, 0.0, 1);
    }

    public void raceWinBronze01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(1, "source", "destination", "racename", "logoname", 2, 0, 0.0, new TimeData(), 0.0, 0.0, 1);
    }

    public void raceSummary01MENU() {
        RacePanelMenu.SummaryInfo[] info = new RacePanelMenu.SummaryInfo[]{new RacePanelMenu.SummaryInfo("name", 0.0, 2, new TimeData(), 0.0), new RacePanelMenu.SummaryInfo("name", 0.0, 3, new TimeData(), 0.0)};
        this.lastMenuCreated = RacePanelMenu.PanelSummaryReport(1, "racename", "logoname", info);
    }

    public void raceFinish01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceFinish(1, "source", "destination", "racename", "logoname", 1, 1.0, new TimeData(), 0.0, 0.0, 0);
    }

    public void raceTowedDefaultedIn01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelTowedDefaultedIn(1, "name", "logo", 1, 1.0, 1);
    }

    public void raceTowedDefaulted01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelTowedAndDefaulted(1, "racename", "logoname", 1, 1.0, 1);
    }

    public void raceCanceled01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceCancelled(1, "racename", "logoname", 1.0, true);
    }

    public void raceDefaulted01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceDefaulted(1, "racename", "logoname", 1.0, true);
    }

    public void raceQualified02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelQualified(2, "racename", "logoname", 1, 1.0, 1.0, "destination", "checkpoint", new TimeData(), "driver", 0.0, 2, 23);
    }

    public void raceNotQualified02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelNotqualified(2, "racename", "logoname", 1, 1.0, 1.0, "destination", "checkpoint", new TimeData(), "driver", 0.0);
    }

    public void raceCheckPointFirst02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelCheckpointFirst(2, "source", "destination", "racename", "logoname", true, "finita", "nexta", new TimeData(), 1.0, new TimeData(), 0.0, 0.0);
    }

    public void raceDropOrContinue02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelDropOrContinue(2, "racename", "logoname");
    }

    public void raceCheckpointMissed02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelCheckpointMissed(2, "racename", "logoname", "next", new TimeData(), "finish");
    }

    public void raceNotAParticipant02MENU() {
        this.lastMenuCreated = RacePanelMenu.RaceNotAParticipant(2, "racename", "logoname");
    }

    public void raceWinGold02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(2, "source", "destination", "racename", "logoname", 0, 0, 0.0, new TimeData(), 0.0, 0.0, 1);
    }

    public void raceWinSilver02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(2, "source", "destination", "racename", "logoname", 1, 0, 0.0, new TimeData(), 0.0, 0.0, 1);
    }

    public void raceWinBronze02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(2, "source", "destination", "racename", "logoname", 2, 0, 0.0, new TimeData(), 0.0, 0.0, 1);
    }

    public void raceSummary02MENU() {
        RacePanelMenu.SummaryInfo[] info = new RacePanelMenu.SummaryInfo[]{new RacePanelMenu.SummaryInfo("name", 0.0, 2, new TimeData(), 0.0), new RacePanelMenu.SummaryInfo("name", 0.0, 3, new TimeData(), 0.0)};
        this.lastMenuCreated = RacePanelMenu.PanelSummaryReport(2, "racename", "logoname", info);
    }

    public void raceFinish02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceFinish(2, "source", "destination", "racename", "logoname", 1, 1.0, new TimeData(), 0.0, 0.0, 0);
    }

    public void raceTowedDefaultedIn02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelTowedDefaultedIn(2, "racename", "logoname", 1, 1.0, 1);
    }

    public void raceTowedDefaulted02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelTowedAndDefaulted(2, "racename", "logoname", 1, 1.0, 1);
    }

    public void raceCanceled02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceCancelled(2, "racename", "logoname", 1.0, true);
    }

    public void raceDefaulted02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceDefaulted(2, "racename", "logoname", 1.0, true);
    }

    public void raceQualified03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelQualified(3, "racename", "logoname", 1, 1.0, 1.0, "destination", "checkpoint", new TimeData(), "driver", 0.0, 2, 23);
    }

    public void raceNotQualified03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelNotqualified(3, "racename", "logoname", 1, 1.0, 1.0, "destination", "checkpoint", new TimeData(), "driver", 0.0);
    }

    public void raceCheckPointFirst03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelCheckpointFirst(3, "source", "destination", "racename", "logoname", true, "finita", "nexta", new TimeData(), 1.0, new TimeData(), 0.0, 0.0);
    }

    public void raceDropOrContinue03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelDropOrContinue(3, "racename", "logoname");
    }

    public void raceCheckpointMissed03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelCheckpointMissed(3, "racename", "logoname", "next", new TimeData(), "finish");
    }

    public void raceNotAParticipant03MENU() {
        this.lastMenuCreated = RacePanelMenu.RaceNotAParticipant(3, "racename", "logoname");
    }

    public void raceWinGold03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(3, "source", "destination", "racename", "logoname", 0, 0, 0.0, new TimeData(), 0.0, 0.0, 1);
    }

    public void raceWinSilver03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(3, "source", "destination", "racename", "logoname", 1, 0, 0.0, new TimeData(), 0.0, 0.0, 1);
    }

    public void raceWinBronze03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(3, "source", "destination", "racename", "logoname", 2, 0, 0.0, new TimeData(), 0.0, 0.0, 1);
    }

    public void raceSummary03MENU() {
        RacePanelMenu.SummaryInfo[] info = new RacePanelMenu.SummaryInfo[]{new RacePanelMenu.SummaryInfo("name", 0.0, 2, new TimeData(), 0.0), new RacePanelMenu.SummaryInfo("name", 0.0, 3, new TimeData(), 0.0)};
        this.lastMenuCreated = RacePanelMenu.PanelSummaryReport(3, "racename", "logoname", info);
    }

    public void raceFinish03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceFinish(3, "source", "destination", "racename", "logoname", 1, 1.0, new TimeData(), 0.0, 0.0, 0);
    }

    public void raceTowedDefaultedIn03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelTowedDefaultedIn(3, "racenae", "logoname", 1, 1.0, 1);
    }

    public void raceTowedDefaulted03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelTowedAndDefaulted(3, "racename", "logoname", 1, 1.0, 1);
    }

    public void raceCanceled03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceCancelled(3, "racename", "logoname", 1.0, true);
    }

    public void raceDefaulted03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceDefaulted(3, "racename", "logoname", 1.0, true);
    }

    public void raceQualified04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelQualified(4, "racename", "logoname", 1, 1.0, 1.0, "destination", "checkpoint", new TimeData(), "driver", 0.0, 2, 23);
    }

    public void raceNotQualified04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelNotqualified(4, "racename", "logoname", 1, 1.0, 1.0, "destination", "checkpoint", new TimeData(), "driver", 0.0);
    }

    public void raceCheckPointFirst04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelCheckpointFirst(4, "source", "destination", "racename", "logoname", true, "finita", "nexta", new TimeData(), 1.0, new TimeData(), 0.0, 0.0);
    }

    public void raceDropOrContinue04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelDropOrContinue(4, "racename", "logoname");
    }

    public void raceCheckpointMissed04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelCheckpointMissed(4, "racename", "logoname", "next", new TimeData(), "finish");
    }

    public void raceNotAParticipant04MENU() {
        this.lastMenuCreated = RacePanelMenu.RaceNotAParticipant(4, "racename", "logoname");
    }

    public void raceWinGold04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(4, "source", "destination", "racename", "logoname", 0, 0, 0.0, new TimeData(), 0.0, 0.0, 1);
    }

    public void raceWinSilver04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(4, "source", "destination", "racename", "logoname", 1, 0, 0.0, new TimeData(), 0.0, 0.0, 1);
    }

    public void raceWinBronze04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(4, "source", "destination", "racename", "logoname", 2, 0, 0.0, new TimeData(), 0.0, 0.0, 1);
    }

    public void raceSummary04MENU() {
        RacePanelMenu.SummaryInfo[] info = new RacePanelMenu.SummaryInfo[]{new RacePanelMenu.SummaryInfo("name", 0.0, 2, new TimeData(), 0.0), new RacePanelMenu.SummaryInfo("name", 0.0, 3, new TimeData(), 0.0)};
        this.lastMenuCreated = RacePanelMenu.PanelSummaryReport(4, "racename", "logoname", info);
    }

    public void raceFinish04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceFinish(4, "source", "destination", "racename", "logoname", 1, 1.0, new TimeData(), 0.0, 0.0, 0);
    }

    public void raceTowedDefaultedIn04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelTowedDefaultedIn(4, "racename", "logoname", 1, 1.0, 1);
    }

    public void raceTowedDefaulted04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelTowedAndDefaulted(4, "racename", "logoname", 1, 1.0, 1);
    }

    public void raceCanceled04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceCancelled(4, "racename", "logoname", 1.0, true);
    }

    public void raceDefaulted04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceDefaulted(4, "racename", "logoname", 1.0, true);
    }

    public void roadserviceMENU() {
        this.lastMenuCreated = RoadService.create(1, 1, 1, 1, 1);
    }

    public void scenarioBigRaceConfirmationMENU() {
        this.lastMenuCreated = ScenarioBigRaceConfirmation.createScenarioBigRaceConfirmationMenu("Oxnard", new CoreTime(), "LA", new DateData(), 1005);
    }

    public void policeMENU() {
        this.lastMenuCreated = PoliceMenu.CreatePoliceMenu(new Vector(), 1, 1);
    }

    public void unsettledDebtMENU() {
        this.lastMenuCreated = MessageDebtSale.CreateMessageDeptMenu(10000);
    }

    public void gameoverEndMENU() {
        this.lastMenuCreated = NotifyGameOver.CreateNotifyGameOver("ESC", 10000.0, 0);
    }

    public void gameoverEndBlackScreenMENU() {
        this.lastMenuCreated = NotifyGameOver.CreateNotifyGameOver("ESC", 10000.0, 4);
    }

    public void gameoverJailMENU() {
        this.lastMenuCreated = NotifyGameOver.CreateNotifyGameOver("ESC", 10000.0, 1);
    }

    public void gameoverMurderMENU() {
        this.lastMenuCreated = NotifyGameOver.CreateNotifyGameOver("ESC", 10000.0, 2);
    }

    public void gameoverBankruptMENU() {
        this.lastMenuCreated = NotifyGameOver.CreateNotifyGameOver("ESC", 10000.0, 3);
    }

    public void gameoverTOTALMENU() {
        this.lastMenuCreated = TotalVictoryMenu.createGameOverTotal(null);
    }

    public void createLooseEconomy() {
        this.lastMenuCreated = VictoryMenu.createLooseEconomy(null);
    }

    public void createLooseSocial() {
        this.lastMenuCreated = VictoryMenu.createLooseSocial(null);
    }

    public void createLooseSport() {
        this.lastMenuCreated = VictoryMenu.createLooseSport(null);
    }

    public void createWinEconomy() {
        this.lastMenuCreated = VictoryMenu.createWinEconomy(null);
    }

    public void createWinSocial() {
        this.lastMenuCreated = VictoryMenu.createWinSocial(null);
    }

    public void createWinSport() {
        this.lastMenuCreated = VictoryMenu.createWinSport(null);
    }

    public void organiserMENU() {
        this.lastMenuCreated = OrganiserMenu.create();
    }

    public void answerMENU() {
        String[] tt = new String[]{"yes", "no"};
        this.lastMenuCreated = AnswerMenu.createAnswerMenu(tt, null);
    }

    public void missionSuccessMENU() {
        this.lastMenuCreated = MissionSuccessPicture.create("mission_name", "text_message", new CoreTime(), "texture_name");
    }

    public void billOfLadingMENU() {
        this.lastMenuCreated = BillOfLadingMenu.create();
    }

    public void bridgeTollMENU() {
        this.lastMenuCreated = menuBridgeToll.CreateBridgeTollMenu(new ILeaveMenuListener(){

            public void menuLeaved() {
            }
        });
    }

    public void progressIndicatorMENU() {
        this.lastMenuCreated = ProgressIndicatorMenu.CreateProgressIndicatorMenu();
    }

    public void titreMENU() {
        this.lastMenuCreated = Titres.create(100.0f, "some some");
    }

    public void blackscreentitresMENU() {
        this.lastMenuCreated = PastFewDaysMenu.create();
    }

    static class ContacterDummy
    implements IcontaktCB {
        ContacterDummy() {
        }

        public boolean accessible() {
            return false;
        }

        public String gFirstName() {
            return "gFirstName";
        }

        public String gLastName() {
            return "gLastName";
        }

        public String gModelname() {
            return "gModelname";
        }

        public String gNickName() {
            return "gNickName";
        }

        public SCRuniperson load_n_getModel() {
            return null;
        }
    }
}

