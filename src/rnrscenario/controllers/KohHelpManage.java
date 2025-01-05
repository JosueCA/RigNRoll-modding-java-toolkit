/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers;

import java.util.ArrayList;
import java.util.List;
import players.Crew;
import players.actorveh;
import rnrcore.CoreTime;
import rnrcore.KohHelpObjectXmlSerializable;
import rnrcore.ObjectXmlSerializable;
import rnrcore.eng;
import rnrcore.vectorJ;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.KohHelp;
import rnrscenario.controllers.Location;
import rnrscenario.controllers.MeetingPlace;
import rnrscenario.controllers.ScenarioController;
import rnrscenario.controllers.ScenarioHost;
import rnrscenario.sctask;
import scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage=0)
public class KohHelpManage
extends sctask
implements ScenarioController {
    private static final int MY_STAGE_NUMBER = KohHelpManage.class.getAnnotation(ScenarioClass.class).scenarioStage();
    private static final String MESSAGE_MAKE_KOH = "makeKohDummy";
    private static final String METH_MAKE_KOH = "setMakeKohDummy";
    private static final String METH_KOH_NOT_ACCTUAL = "setKohWasNotMade";
    private static final double COCH_POINT_ACTIVATION_DISTANCE = 500.0;
    private static final int COCH_POINT_INDEX = 0;
    private static final int NICK_POINT_INDEX = 1;
    private static final String[][] MEETING_POINTS = new String[][]{{"CochKeyPoint_OV_SB", "NikKeyPoint_OV_SB"}, {"CochKeyPoint_OV_LA", "NikKeyPoint_OV_LA"}};
    private List<MeetingPlace> cochMeetingPlaces = null;
    private Location nickLocationOnMeetingPlace = null;
    private static final int SCENE_INVALID = -1;
    private static final int SCENE30 = 0;
    private actorveh player\u00d1ar;
    private static final CoreTime DAYS_TO_FAILURE_MEETING_WITH_COCH = CoreTime.days(1);
    private KohHelp spawnedController = null;
    private int current_scene = 0;
    private boolean makeKoh = false;
    private boolean wasMadeKoh = false;
    private CoreTime timeKohOrdered = null;
    private final ScenarioHost host;
    private ObjectXmlSerializable serializator = null;
    static KohHelpManage instance = null;
    private boolean scheduleRestoreNickLocationOnMeetingPlace = false;

    public static void constructSingleton(ScenarioHost host) {
        instance = new KohHelpManage(host);
    }

    public static KohHelpManage getInstance() {
        return instance;
    }

    private KohHelpManage(ScenarioHost scenarioHost) {
        super(3, true);
        assert (null != scenarioHost) : "'scenarioHost' must be non-null reference";
        this.host = scenarioHost;
        this.player\u00d1ar = Crew.getIgrokCar();
        this.serializator = new KohHelpObjectXmlSerializable(this, this.host);
        this.serializator.registerObjectXmlSerializable();
        this.host.registerController(this);
        EventsControllerHelper.getInstance().addMessageListener(this, METH_MAKE_KOH, MESSAGE_MAKE_KOH);
        EventsControllerHelper.getInstance().addMessageListener(this, METH_KOH_NOT_ACCTUAL, "Escape Help Koh");
        EventsControllerHelper.getInstance().addMessageListener(this, METH_KOH_NOT_ACCTUAL, "Accept Help Koh");
    }

    public void finish() {
        if (!this.f_finished) {
            super.finish();
            if (null != EventsControllerHelper.getInstance()) {
                EventsControllerHelper.getInstance().removeMessageListener(this, METH_MAKE_KOH, MESSAGE_MAKE_KOH);
                EventsControllerHelper.getInstance().removeMessageListener(this, METH_KOH_NOT_ACCTUAL, "Escape Help Koh");
                EventsControllerHelper.getInstance().removeMessageListener(this, METH_KOH_NOT_ACCTUAL, "Accept Help Koh");
            }
            this.host.unregisterController(this);
            this.serializator.unRegisterObjectXmlSerializable();
        }
    }

    public static void deinit() {
        if (null != instance) {
            if (null != KohHelpManage.instance.spawnedController) {
                KohHelpManage.instance.spawnedController.switchOff();
                KohHelpManage.instance.spawnedController = null;
            }
            instance.finish();
            instance.finishImmediately();
            instance = null;
        }
    }

    static void accept() {
        if (-1 == KohHelpManage.instance.current_scene) {
            eng.err("SCENE_INVALID == getInstance().current_scene");
        }
    }

    public void gameDeinitLaunched() {
        KohHelpManage.deinit();
    }

    public static void questFinished() {
        if (null != instance) {
            instance.nullScene();
            KohHelpManage.deinit();
        }
    }

    public void setMakeKohDummy() {
        eng.pager("Koh is prepared");
        this.makeKoh = true;
        this.timeKohOrdered = new CoreTime();
    }

    public void setKohWasNotMade() {
        this.wasMadeKoh = false;
    }

    private void nullScene() {
        this.makeKoh = false;
    }

    public void debugSetNickLocation() {
        this.nickLocationOnMeetingPlace = new Location(MY_STAGE_NUMBER, Crew.getIgrokCar().gPosition(), Crew.getIgrokCar().gMatrix());
    }

    public Location getNickLocation() {
        return this.nickLocationOnMeetingPlace;
    }

    public void run() {
        double sqrDistance;
        int i;
        double smallestSqrDistance;
        vectorJ cochCarPosition;
        actorveh cochCar;
        this.createMeetingLocationsIfNull();
        if (this.scheduleRestoreNickLocationOnMeetingPlace && null != (cochCar = Crew.getMappedCar("KOH")) && null != (cochCarPosition = cochCar.gPosition()) && 0.0 < cochCarPosition.length() && 0 < this.cochMeetingPlaces.size()) {
            this.nickLocationOnMeetingPlace = this.cochMeetingPlaces.get(0).getNickLocation();
            smallestSqrDistance = cochCarPosition.len2(this.nickLocationOnMeetingPlace.getPosition());
            for (i = 1; i < this.cochMeetingPlaces.size(); ++i) {
                Location nickPosition = this.cochMeetingPlaces.get(i).getNickLocation();
                sqrDistance = cochCarPosition.len2(nickPosition.getPosition());
                if (!(sqrDistance < smallestSqrDistance)) continue;
                smallestSqrDistance = sqrDistance;
                this.nickLocationOnMeetingPlace = nickPosition;
            }
            this.scheduleRestoreNickLocationOnMeetingPlace = false;
        }
        if (0 < new CoreTime().moreThanOnTime(this.timeKohOrdered, DAYS_TO_FAILURE_MEETING_WITH_COCH)) {
            this.makeKoh = false;
            EventsControllerHelper.messageEventHappened("Escape Help Koh");
            KohHelpManage.questFinished();
            return;
        }
        if (this.makeKoh && !this.cochMeetingPlaces.isEmpty()) {
            MeetingPlace nearestMeetingPosition = this.cochMeetingPlaces.get(0);
            vectorJ playerPosition = this.player\u00d1ar.gPosition();
            smallestSqrDistance = playerPosition.len2(nearestMeetingPosition.getCochLocation().getPosition());
            for (i = 1; i < this.cochMeetingPlaces.size(); ++i) {
                MeetingPlace placementPosition = this.cochMeetingPlaces.get(i);
                sqrDistance = playerPosition.len2(placementPosition.getCochLocation().getPosition());
                if (!(sqrDistance < smallestSqrDistance)) continue;
                smallestSqrDistance = sqrDistance;
                nearestMeetingPosition = placementPosition;
            }
            if (smallestSqrDistance < 250000.0) {
                this.spawnedController = KohHelp.prepare(nearestMeetingPosition.getCochLocation());
                this.nickLocationOnMeetingPlace = nearestMeetingPosition.getNickLocation();
                this.makeKoh = false;
                this.wasMadeKoh = true;
            }
        }
    }

    private void createMeetingLocationsIfNull() {
        if (null == this.cochMeetingPlaces) {
            this.cochMeetingPlaces = new ArrayList<MeetingPlace>(MEETING_POINTS.length);
            for (String[] cochMeetingPoint : MEETING_POINTS) {
                try {
                    MeetingPlace place = new MeetingPlace(MY_STAGE_NUMBER, cochMeetingPoint[0], cochMeetingPoint[1]);
                    this.cochMeetingPlaces.add(place);
                }
                catch (IllegalStateException e) {
                    eng.writeLog(String.format("WARNING: meeting place with coch was not found! names: %s, %s.", cochMeetingPoint[0], cochMeetingPoint[1]));
                }
            }
        }
    }

    public void prepareKohDeserealize() {
        if (this.wasMadeKoh) {
            this.spawnedController = KohHelp.prepareSerialize();
            this.scheduleRestoreNickLocationOnMeetingPlace = true;
        }
    }

    public int getCurrent_scene() {
        return this.current_scene;
    }

    public void setCurrent_scene(int current_scene) {
        this.current_scene = current_scene;
    }

    public boolean isF_makeKoh() {
        return this.makeKoh;
    }

    public void setF_makeKoh(boolean koh) {
        this.makeKoh = koh;
    }

    public boolean isF_wasMadeKoh() {
        return this.wasMadeKoh;
    }

    public void setF_wasMadeKoh(boolean madeKoh) {
        this.wasMadeKoh = madeKoh;
    }

    public CoreTime getTimeKohOrdered() {
        return this.timeKohOrdered;
    }

    public void setTimeKohOrdered(CoreTime timeKohOrdered) {
        this.timeKohOrdered = timeKohOrdered;
    }
}

