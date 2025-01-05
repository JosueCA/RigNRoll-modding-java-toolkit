/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers;

import players.Crew;
import players.actorveh;
import rnrcore.IXMLSerializable;
import rnrcore.ObjectXmlSerializable;
import rnrcore.RedRockCanyonObjectXmlSerializable;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.vectorJ;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.ScenarioController;
import rnrscenario.controllers.ScenarioHost;
import rnrscenario.sctask;
import scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage=13)
public class RedRockCanyon
extends sctask
implements ScenarioController {
    private static final int GEOMETRY_TRIGGER_RED_ROCK_ENTER = 21540;
    private static final int GEOMETRY_TRIGGER_RED_ROCK_EXIT = 21545;
    private static final int GEOMETRY_TRIGGER_RED_ROCK_COCH_CALL_1 = 21546;
    private static final int GEOMETRY_TRIGGER_RED_ROCK_COCH_CALL_2 = 21547;
    private static final int GEOMETRY_TRIGGER_RED_ROCK_COCH_CALL_3 = 21548;
    private int cochCallCallbacksIndex = 0;
    private static final int SECONDS_BETWEEN_CALL_TO_RUN = 3;
    private static final double RED_ROCK_CANYON_EVENT_SQR_RADIUS = 2.704E7;
    public static final String RED_ROCK_CENTER_NAME = "Red Rock Canyon Center";
    private static final String MESSAGE_GOOD = "reach Red Rock Canyon";
    private static final String MESSAGE_BAD = "fail Red Rock Canyon";
    private static final String MESSAGE_CALL_KOH = "ready call 1520";
    private vectorJ redRockCenter;
    private int[] callbacksOnTriggersIds = new int[]{0, 0, 0, 0, 0};
    private boolean cochCalled = false;
    private boolean mathewCalled = false;
    private boolean resourcesCleared = false;
    private final ObjectXmlSerializable serializator;
    private ScenarioHost host;

    private void deleteCallbacks() {
        for (int i = 0; i < this.callbacksOnTriggersIds.length; ++i) {
            int callbacksOnTriggersId = this.callbacksOnTriggersIds[i];
            if (0 == callbacksOnTriggersId) continue;
            event.removeEventObject(callbacksOnTriggersId);
            this.callbacksOnTriggersIds[i] = 0;
        }
    }

    public void clearResources() {
        if (this.resourcesCleared) {
            return;
        }
        this.deleteCallbacks();
        this.finishImmediately();
        this.serializator.unRegisterObjectXmlSerializable();
        this.host.unregisterController(this);
        EventsControllerHelper.getInstance().removeMessageListener(this, "clearResources", "m01500 failed");
        this.resourcesCleared = true;
    }

    public void gameDeinitLaunched() {
        this.clearResources();
    }

    public RedRockCanyon(ScenarioHost scenarioHost, boolean mathewCalled) {
        super(3, true);
        this.mathewCalled = mathewCalled;
        if (mathewCalled) {
            this.registerTriggerCallbacks();
        } else {
            this.start();
        }
        this.redRockCenter = eng.getControlPointPosition(RED_ROCK_CENTER_NAME);
        this.serializator = new RedRockCanyonObjectXmlSerializable(this);
        this.serializator.registerObjectXmlSerializable();
        EventsControllerHelper.getInstance().addMessageListener(this, "clearResources", "m01500 failed");
        this.host = scenarioHost;
        this.host.registerController(this);
    }

    private void registerTriggerCallbacks() {
        int index = 0;
        this.callbacksOnTriggersIds[index++] = event.eventObject(21540, this, "enterGood");
        this.callbacksOnTriggersIds[index++] = event.eventObject(21545, this, "enterBad");
        this.cochCallCallbacksIndex = index;
        this.callbacksOnTriggersIds[index++] = event.eventObject(21546, this, "launchCochCall");
        this.callbacksOnTriggersIds[index++] = event.eventObject(21547, this, "launchCochCall");
        this.callbacksOnTriggersIds[index++] = event.eventObject(21548, this, "launchCochCall");
        assert (index == this.callbacksOnTriggersIds.length);
    }

    public void run() {
        actorveh playerCar = Crew.getIgrokCar();
        if (null != playerCar && null != this.redRockCenter && 2.704E7 < playerCar.gPosition().len2(this.redRockCenter)) {
            EventsControllerHelper.messageEventHappened("player far from red rock");
            this.registerTriggerCallbacks();
            this.finish();
            this.mathewCalled = true;
        }
    }

    public void enterGood() {
        this.clearResources();
        EventsControllerHelper.messageEventHappened(MESSAGE_GOOD);
    }

    public void enterBad() {
        this.clearResources();
        EventsControllerHelper.messageEventHappened(MESSAGE_BAD);
    }

    public void launchCochCall() {
        if (!this.cochCalled) {
            EventsControllerHelper.messageEventHappened(MESSAGE_CALL_KOH);
            for (int i = this.cochCallCallbacksIndex; i < this.callbacksOnTriggersIds.length; ++i) {
                if (0 == this.callbacksOnTriggersIds[i]) continue;
                event.removeEventObject(this.callbacksOnTriggersIds[i]);
                this.callbacksOnTriggersIds[i] = 0;
            }
            this.cochCalled = false;
        }
    }

    public IXMLSerializable getXmlSerializator() {
        return null;
    }

    public boolean isCochCalled() {
        return this.cochCalled;
    }

    public boolean isMathewCalled() {
        return this.mathewCalled;
    }

    public void setCochCalled(boolean cochCalled) {
        this.cochCalled = cochCalled;
    }
}

