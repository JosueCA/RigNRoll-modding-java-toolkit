/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import players.Crew;
import players.actorveh;
import players.aiplayer;
import rnrcore.CoreTime;
import rnrcore.IScriptTask;
import rnrcore.SCRuniperson;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.scenetrack;
import rnrcore.vectorJ;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.map.Place;
import rnrscr.Bar;
import rnrscr.BarMenuCreator;
import rnrscr.CarInOutTasks;
import rnrscr.Dialog;
import rnrscr.DialogsSet;
import rnrscr.IBarMoveNewspaperAnimation;
import rnrscr.MissionDialogs;
import rnrscr.SODialogParams;
import rnrscr.SOscene;
import rnrscr.drvscripts;
import rnrscr.specobjects;

public class BarScene {
    public static final String OUTSIDE_SCENE = "barin outside";
    public static final String INSIDE_SCENE = "barin inside ";
    public static final String NEWSPAPER_SCENE = "barin move newspaper ";
    public static final String OUTSIDE_DOOR_PREFIX = "Space_DoorToBar_";
    public static final String INSIDE_DOOR_PREFIX = "Space_DoorBar0";
    public static final String INSIDE_NEWSPAPER_PREFIX = "Space_NewspaperBar0";
    public static final boolean MENU_ON_SINGLE_PAPER = true;
    private actorveh car;
    private long task;

    public void PersonageInBar() {
        aiplayer player = Crew.getIgrok();
        SCRuniperson person = player.getModel();
        this.car = Crew.getIgrokCar();
        this.task = eng.CreateTASK(person);
        long startinsideanimation = eng.AddScriptTask(this.task, new StartBarScene());
        long person_ingame = eng.AddScriptTask(this.task, new PlacePersonInGame(person));
        long game_world = eng.AddChangeWorldTask(this.task, "game", "simple");
        long movetocar = eng.AddScriptTask(this.task, drvscripts.placePersonToCar(person, this.car));
        long realesecar = eng.AddScriptTask(this.task, new ReleaseFromParking());
        long freeall = eng.AddScriptTask(this.task, new FinishTask());
        long NEWSPAPEREND = eng.AddEventTask(this.task);
        eng.EventTask_onBARMessageClosed(NEWSPAPEREND, false);
        eng.OnEndTASK(NEWSPAPEREND, "play", game_world);
        eng.OnEndTASK(game_world, "play", movetocar);
        eng.OnEndTASK(game_world, "play", person_ingame);
        SOscene SC = new SOscene();
        SC.task = this.task;
        SC.person = person;
        SC.actor = player;
        SC.vehicle = this.car;
        long PARking = SC.waitParkingEvent();
        CarInOutTasks CAR_out = SC.makecaroutOnEnd(PARking, false);
        CarInOutTasks CAR_in = SC.makecarinOnEnd(game_world);
        SC.restorecameratoCarOnEnd(CAR_in.getMTaskToWait());
        eng.playTASK(PARking);
        eng.disableControl();
        eng.OnEndTASK(CAR_in.getMTaskToWait(), "play", realesecar);
        eng.OnEndTASK(CAR_in.getMTaskToWait(), "play", freeall);
        eng.OnEndTASK(CAR_out.getMTaskToWait(), "play", startinsideanimation);
    }

    public void PersonageInBar_short() {
        long PARking;
        aiplayer player = Crew.getIgrok();
        SCRuniperson person = player.getModel();
        this.car = Crew.getIgrokCar();
        this.task = eng.CreateTASK(person);
        long startinsideanimation = eng.AddScriptTask(this.task, new StartBarScene());
        long person_ingame = eng.AddScriptTask(this.task, new PlacePersonInGame(person));
        long game_world = eng.AddChangeWorldTask(this.task, "game", "simple");
        long movetocar = eng.AddScriptTask(this.task, drvscripts.placePersonToCar(person, this.car));
        long realesecar = eng.AddScriptTask(this.task, new ReleaseFromParking());
        long freeall = eng.AddScriptTask(this.task, new FinishTask());
        long NEWSPAPEREND = eng.AddEventTask(this.task);
        eng.EventTask_onBARMessageClosed(NEWSPAPEREND, false);
        eng.OnEndTASK(NEWSPAPEREND, "play", game_world);
        eng.OnEndTASK(game_world, "play", movetocar);
        eng.OnEndTASK(game_world, "play", person_ingame);
        SOscene SC = new SOscene();
        SC.task = this.task;
        SC.person = person;
        SC.actor = player;
        SC.vehicle = this.car;
        long CAR_out = PARking = SC.waitParkingEvent();
        CarInOutTasks CAR_in = SC.makecarinOnEnd(game_world);
        SC.restorecameratoCarOnEnd(CAR_in.getMTaskToWait());
        eng.playTASK(PARking);
        eng.disableControl();
        eng.OnEndTASK(CAR_in.getMTaskToWait(), "play", realesecar);
        eng.OnEndTASK(CAR_in.getMTaskToWait(), "play", freeall);
        eng.OnEndTASK(CAR_out, "play", startinsideanimation);
    }

    class FinishTask
    implements IScriptTask {
        FinishTask() {
        }

        public void launch() {
            eng.DeleteTASK(BarScene.this.task);
            eng.enableControl();
        }
    }

    class ReleaseFromParking
    implements IScriptTask {
        ReleaseFromParking() {
        }

        public void launch() {
            BarScene.this.car.leaveParking();
        }
    }

    static class PlacePersonInGame
    implements IScriptTask {
        private SCRuniperson person;

        PlacePersonInGame(SCRuniperson person) {
            this.person = person;
        }

        public void launch() {
            this.person.SetInGameWorld();
            eng.returnCameraToGameWorld();
        }
    }

    class StartBarScene
    implements IScriptTask,
    IBarMoveNewspaperAnimation {
        private static final String METHOD_FINISHED = "barinFinished";
        private static final String METHOD_INSIDE = "barinInside";
        String bardoor;
        String newspaper;
        private long scene_outside;
        private String last_dialog = null;

        StartBarScene() {
        }

        public void launch() {
            long scene;
            this.scene_outside = scene = scenetrack.CreateSceneXML(BarScene.OUTSIDE_SCENE, 3, specobjects.getBarPresets());
            event.eventObject((int)scene + 1, this, METHOD_INSIDE);
            eng.AddSceneTrackToTask(BarScene.this.task, scene);
        }

        public void barinInside() {
            this.bardoor = BarScene.INSIDE_DOOR_PREFIX + Bar.barType;
            this.newspaper = BarScene.INSIDE_NEWSPAPER_PREFIX + Bar.barType;
            long scene = scenetrack.CreateSceneXML(BarScene.INSIDE_SCENE + Bar.barType, 17, this);
            event.eventObject((int)scene, this, METHOD_FINISHED);
            eng.AddSceneTrackToTask(BarScene.this.task, scene);
        }

        private boolean playImmediateDialogs() {
            vectorJ pos;
            DialogsSet set;
            int size;
            if (null != this.last_dialog) {
                Bar.getInstance().endDialog(this.last_dialog);
            }
            if ((size = (set = MissionDialogs.queueDialogsForSO(8, pos = Bar.getCurrentBarPosition(), new CoreTime())).getQuestCount()) == 0) {
                return false;
            }
            for (int i = 0; i < size; ++i) {
                SODialogParams params2 = set.getQuest(i);
                if (params2.wasPlayed() || !params2.isfinishDialog()) continue;
                this.last_dialog = params2.getDescription();
                Bar.getInstance().startDialog(params2.getDescription());
                params2.play();
                Dialog.getDialog(params2.getDescription()).start_bar(params2.getNpcModel(), Crew.getIgrok().getModel(), params2.getIdentitie());
                event.eventObject(9850, this, METHOD_FINISHED);
                return true;
            }
            return false;
        }

        public void barinFinished() {
            scenetrack.DeleteScene(this.scene_outside);
            if (this.playImmediateDialogs()) {
                return;
            }
            Place bar_place = MissionSystemInitializer.getMissionsMap().getNearestPlaceByType(0);
            String bar_name = null;
            if (null != bar_place) {
                bar_name = bar_place.getName();
            }
            BarMenuCreator.CreateBarMenu(Bar.getCurrentBarPosition(), this);
        }

        public long playMoveNeswpaper() {
            long scene = scenetrack.CreateSceneXML(BarScene.NEWSPAPER_SCENE + Bar.barType, 9, this);
            eng.AddSceneTrackToTask(BarScene.this.task, scene);
            return scene;
        }

        public void CreateBarMenu() {
            BarMenuCreator.CreateBarMenu(Bar.getCurrentBarPosition(), this);
        }
    }
}

