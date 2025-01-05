/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers;

import players.CarName;
import players.Crew;
import players.actorveh;
import rnrcore.ConvertGameTime;
import rnrcore.CoreTime;
import rnrcore.IScriptTask;
import rnrcore.IXMLSerializable;
import rnrcore.ObjectXmlSerializable;
import rnrcore.Preparesc00060ObjectXmlSerializable;
import rnrcore.ScriptRef;
import rnrcore.anm;
import rnrcore.eng;
import rnrcore.event;
import rnrscenario.StaticScenarioStuff;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.ScenarioController;
import rnrscenario.controllers.ScenarioHost;
import rnrscenario.controllers.chase00090;
import rnrscenario.scenarioscript;
import rnrscenario.sctask;
import rnrscr.cSpecObjects;
import rnrscr.parkingplace;
import rnrscr.specobjects;
import scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage=1)
public final class preparesc00060
extends sctask
implements anm,
ScenarioController {
    private static final int GEOMETRY_TRIGGER_NEAR_OXNARD_BAR_FORWARD = 20060;
    private static final int GEOMETRY_TRIGGER_NEAR_OXNARD_BAR_BACKWARD = 20061;
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int BANDITS_DISAPPEARS_FROM_BAR_TIME = 5;
    public static final String MESSAGE_SCENE_WITH_DOROTHY = "sceneendedwithDorothy";
    private static final String DROP_QUEST = "Quest Dropped Out";
    private static final String BAR_NAME_WERE_DOROTHY_IS = "Room_Oxnard_Bar1";
    private static final String METHOD_RESQUE_DROPPED = "resques_dorothy_drop_by_time";
    private static final String METHOD_CHASE_WITH_DOROTHY = "startChase";
    private static final String METHOD_DOROTHY_RESCUE_TOO_LATE = "m00040Failed";
    private static final String METHOD_FINISH = "finish";
    private static final String MESSAGE_RESQUE_STARTED = "resques_dorothy_started";
    private static final String MESSAGE_RESQUE_DROPPED = "resques_dorothy_drop_by_time";
    private static final String MESSAGE_dorothy_rescue_too_late = "m00040 failed";
    private static final String DIALOG_NAME = "cb00040";
    public static final String BANDIT_CAR_NAME_FOR_REGISTRY = "banditcar";
    private static final int CYCLE_CALL_DELAY_SECONDS = 3;
    private actorveh banditsCar = null;
    private boolean alreadyCalled = false;
    private boolean checkecondition = true;
    private CoreTime questFailureTime = null;
    private CoreTime questStartTime = null;
    private CoreTime banditsDissapearFromBarTimeDelta = new CoreTime(0, 0, 0, 6, 0);
    private boolean exitFromMotelDetected = false;
    private int callbackOnExitFromMotel;
    private boolean counterTurnedOn = false;
    private boolean counterTriggered = false;
    private int[] triggerCallbackIds = new int[]{0, 0};
    private static preparesc00060 instance;
    private final ScenarioHost host;
    private ObjectXmlSerializable serializator = null;
    private ScriptRef uid = new ScriptRef();
    private static final String MESSAGE_CB00040_DECLINED = "cb00040 declined";

    public static preparesc00060 getInstance() {
        return instance;
    }

    public IXMLSerializable getXmlSerializator() {
        return null;
    }

    public int getUid() {
        return this.uid.getUid(this);
    }

    public void setUid(int value) {
        this.uid.setUid(value);
    }

    public void removeRef() {
        this.uid.removeRef(this);
    }

    public void updateNative(int p) {
    }

    public boolean animaterun(double dt) {
        return true;
    }

    public preparesc00060(ScenarioHost scenarioHost, boolean restored) {
        super(3, true);
        assert (null != scenarioHost) : "'scenarioHost' must be non-null reference";
        this.host = scenarioHost;
        instance = this;
        this.host.registerController(this);
        StaticScenarioStuff.makeReadyPreparesc00060(true);
        EventsControllerHelper.getInstance().addMessageListener(this, METHOD_CHASE_WITH_DOROTHY, MESSAGE_SCENE_WITH_DOROTHY);
        EventsControllerHelper.getInstance().addMessageListener(this, METHOD_DOROTHY_RESCUE_TOO_LATE, MESSAGE_dorothy_rescue_too_late);
        EventsControllerHelper.getInstance().addMessageListener(this, "resques_dorothy_drop_by_time", "resques_dorothy_drop_by_time");
        EventsControllerHelper.getInstance().addMessageListener(this, METHOD_FINISH, MESSAGE_CB00040_DECLINED);
        EventsControllerHelper.getInstance().addMessageListener(this, "questStarted", "cb00040 accepted");
        @ScenarioClass(scenarioStage=1)
        final class OneShotMotelExitCallBack
        implements IScriptTask {
            OneShotMotelExitCallBack() {
            }

            public void launch() {
                preparesc00060.this.exitFromMotelDetected = true;
                preparesc00060.this.callbackOnExitFromMotel = event.createScriptObject(2, new OneShotMotelExitCallBack());
            }
        }
        this.callbackOnExitFromMotel = event.createScriptObject(2, new OneShotMotelExitCallBack());
        this.serializator = new Preparesc00060ObjectXmlSerializable(this, this.host);
        this.serializator.registerObjectXmlSerializable();
        if (!restored) {
            this.triggerCallbackIds[0] = event.eventObject(20061, this, "MethewCall");
            this.triggerCallbackIds[1] = event.eventObject(20060, this, "MethewCall");
        }
    }

    private void deleteCallbacksOnTriggers() {
        for (int triggerCallbackId : this.triggerCallbackIds) {
            if (0 == triggerCallbackId) continue;
            event.removeEventObject(triggerCallbackId);
        }
    }

    public void run() {
        if (this.notExpired()) {
            this.prepareBanditCar();
        }
    }

    boolean prepareBanditCar() {
        if (null != this.banditsCar) {
            return false;
        }
        specobjects so = specobjects.getInstance();
        cSpecObjects ob = so.GetNearestLoadedParkingPlace_nearBarNamed(BAR_NAME_WERE_DOROTHY_IS, 200.0);
        if (null != ob) {
            this.banditsCar = eng.CreateCarForScenario(CarName.CAR_BANDITS, ob.matrix, ob.position);
            if (0 == this.banditsCar.getCar()) {
                eng.err("0 == banditsCar.car in " + this.getClass().getName());
            }
            this.banditsCar.registerCar(BANDIT_CAR_NAME_FOR_REGISTRY);
            Crew.addMappedCar("ARGOSY BANDIT", this.banditsCar);
            parkingplace parking = parkingplace.findParkingByName("Oxnard_Parking_01", ob.position);
            this.banditsCar.makeParking(parking, 4);
        }
        return true;
    }

    public void m00040Failed() {
        this.counterTurnedOn = true;
        this.questFailureTime = new CoreTime();
    }

    public void questStarted() {
        this.questStartTime = new CoreTime();
    }

    private boolean notExpired() {
        if (!this.counterTurnedOn) {
            return true;
        }
        CoreTime banditsInBarEndTime = ConvertGameTime.convertFromGiven(300, this.questFailureTime);
        CoreTime currentTime = new CoreTime();
        if (!this.counterTriggered && this.banditsLeftBar(banditsInBarEndTime, currentTime)) {
            instance = null;
            this.counterTriggered = true;
            this.counterTurnedOn = false;
            eng.writeLog("resque of Dorothy droped out.");
            eng.pager("Resque of Dorothy failed. Quest droped out.");
            if (this.exitFromMotelDetected) {
                this.banditsCar.deactivate();
                this.banditsCar = null;
            } else {
                this.banditsCar.noNeedForPredefineAnimation();
            }
            this.finish();
            EventsControllerHelper.messageEventHappened(DROP_QUEST);
            return false;
        }
        this.exitFromMotelDetected = false;
        return true;
    }

    private boolean banditsLeftBar(CoreTime banditsInBarEndTime, CoreTime currentTime) {
        return 0 < currentTime.moreThan(banditsInBarEndTime) || 0 < currentTime.moreThanOnTime(this.questStartTime, this.banditsDissapearFromBarTimeDelta);
    }

    public void finish() {
        if (!this.f_finished) {
            this.f_finished = true;
            this.finishImmediately();
            EventsControllerHelper.getInstance().removeMessageListener(this, METHOD_CHASE_WITH_DOROTHY, MESSAGE_SCENE_WITH_DOROTHY);
            EventsControllerHelper.getInstance().removeMessageListener(this, METHOD_DOROTHY_RESCUE_TOO_LATE, MESSAGE_dorothy_rescue_too_late);
            EventsControllerHelper.getInstance().removeMessageListener(this, "resques_dorothy_drop_by_time", "resques_dorothy_drop_by_time");
            EventsControllerHelper.getInstance().removeMessageListener(this, METHOD_FINISH, MESSAGE_CB00040_DECLINED);
            EventsControllerHelper.getInstance().removeMessageListener(this, "questStarted", "cb00040 accepted");
            event.deleteScriptObject(this.callbackOnExitFromMotel);
            this.serializator.unRegisterObjectXmlSerializable();
            this.host.unregisterController(this);
            this.deleteCallbacksOnTriggers();
        }
    }

    void MethewCall() {
        if (this.alreadyCalled) {
            return;
        }
        EventsControllerHelper.messageEventHappened(MESSAGE_RESQUE_STARTED);
        scenarioscript.script.launchCall(DIALOG_NAME);
        this.alreadyCalled = true;
        this.deleteCallbacksOnTriggers();
    }

    void startChase() {
        this.finish();
        chase00090.constructStarted(this.host);
    }

    public void resques_dorothy_drop_by_time() {
        this.finish();
    }

    public void gameDeinitLaunched() {
        this.finish();
    }

    public boolean isAlreadyCalled() {
        return this.alreadyCalled;
    }

    public void setAlreadyCalled(boolean alreadyCalled) {
        this.alreadyCalled = alreadyCalled;
        if (!alreadyCalled) {
            this.triggerCallbackIds[1] = event.eventObject(20060, this, "MethewCall");
            this.triggerCallbackIds[0] = event.eventObject(20061, this, "MethewCall");
        }
    }

    public actorveh getBanditsCar() {
        return this.banditsCar;
    }

    public void setBanditsCar(actorveh banditsCar) {
        this.banditsCar = banditsCar;
    }

    public boolean isCheckecondition() {
        return this.checkecondition;
    }

    public void setCheckecondition(boolean checkecondition) {
        this.checkecondition = checkecondition;
    }

    public boolean isCounterTriggered() {
        return this.counterTriggered;
    }

    public void setCounterTriggered(boolean counterTriggered) {
        this.counterTriggered = counterTriggered;
    }

    public boolean isCounterTurnedOn() {
        return this.counterTurnedOn;
    }

    public void setCounterTurnedOn(boolean counterTurnedOn) {
        this.counterTurnedOn = counterTurnedOn;
    }

    public CoreTime getQuestFailureTime() {
        return this.questFailureTime;
    }

    public void setQuestFailureTime(CoreTime questFailureTime) {
        this.questFailureTime = questFailureTime;
    }

    public CoreTime getQuestStartTime() {
        return this.questStartTime;
    }

    public void setQuestStartTime(CoreTime questStartTime) {
        this.questStartTime = questStartTime;
    }
}

