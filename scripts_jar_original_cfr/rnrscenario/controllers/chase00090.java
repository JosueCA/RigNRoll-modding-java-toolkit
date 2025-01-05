/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers;

import players.Chase;
import players.Crew;
import players.actorveh;
import players.aiplayer;
import rnrconfig.Config;
import rnrcore.ChaseObjectXmlSerializable;
import rnrcore.ConvertGameTime;
import rnrcore.CoreTime;
import rnrcore.ObjectXmlSerializable;
import rnrcore.SCRtalkingperson;
import rnrcore.Sphere;
import rnrcore.eng;
import rnrcore.vectorJ;
import rnrscenario.IShootChasing;
import rnrscenario.animation.ShootingSeriesAnimation;
import rnrscenario.config.ConfigManager;
import rnrscenario.configurators.ChaseToRescueDorothyConfig;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.ResqueDorothyShootAnimate;
import rnrscenario.controllers.ScenarioController;
import rnrscenario.controllers.ScenarioHost;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.map.Place;
import rnrscenario.sctask;
import rnrscr.drvscripts;
import scriptEvents.EventsControllerHelper;
import xmlserialization.nxs.AnnotatedSerializable;
import xmlserialization.nxs.LoadFrom;
import xmlserialization.nxs.SaveTo;

@ScenarioClass(scenarioStage=1)
public final class chase00090
extends sctask
implements IShootChasing,
AnnotatedSerializable,
ScenarioController {
    private static final String BAR_WHERE_BANDITS_GO_AFTER_CHASE = "MP_Bar_OV_SB_01";
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int ANIMATION_TIME_DELTA = 0;
    public static final String MY_SERIALIZATION_UID = "chase00090";
    private static final boolean NO_MESSAGES = !Config.debugInformationEnabled;
    private int minutesToEscape = 3;
    private double distanceToEscape = 500.0;
    private double distanceToKill = 10.0;
    private double distanceToShoot = 200.0;
    @SaveTo(destinationNodeName="chase_start_time")
    @LoadFrom(sourceNodeName="chase_start_time")
    private CoreTime chaseStartTime = null;
    private static aiplayer dorothy = null;
    private vectorJ barInSantaBarbaraPosition = new vectorJ(1800.0, -23.5, 21.0);
    private ResqueDorothyShootAnimate shootAnimation = null;
    private ShootingSeriesAnimation shootSeries = new ShootingSeriesAnimation();
    private SCRtalkingperson dorothyAnimation = null;
    private final actorveh playerCar;
    private final actorveh banditsCar;
    private boolean isChaserCarRegistered = false;
    private boolean needShooting = false;
    private boolean predefinedAnimationFinished = false;
    private boolean debugMode = false;
    private boolean scheduleChasingStart = false;
    private ChaseToRescueDorothyConfig config;
    private final ObjectXmlSerializable serializator;
    private ScenarioHost host;
    private static chase00090 instance = null;
    private final Object latch = new Object();
    private static final double MILLISECONDS_IN_SECOND = 1000.0;
    private vectorJ previousBanditsPosition = null;
    private Sphere playerCarBoundingShpere = null;

    private boolean escapedFromChase(vectorJ playerPosition, vectorJ banditsPosition) {
        assert (null != playerPosition && null != banditsPosition) : "all arguments must be non-null references";
        CoreTime chaseEndTime = ConvertGameTime.convertFromGiven(this.minutesToEscape * 60, this.chaseStartTime);
        return this.distanceToEscape * this.distanceToEscape < playerPosition.len2(banditsPosition) || 0 < new CoreTime().moreThan(chaseEndTime);
    }

    public static chase00090 getInstance() {
        return instance;
    }

    public chase00090() {
        super(0, true);
        Place bar;
        instance = this;
        if (null != ConfigManager.getGlobal()) {
            this.config = (ChaseToRescueDorothyConfig)ConfigManager.getGlobal().getConfig(2);
        }
        if (null != MissionSystemInitializer.getMissionsMap() && null != (bar = MissionSystemInitializer.getMissionsMap().getPlace(BAR_WHERE_BANDITS_GO_AFTER_CHASE))) {
            this.barInSantaBarbaraPosition = bar.getCoords();
        }
        this.banditsCar = Crew.getMappedCar("ARGOSY BANDIT");
        this.playerCar = Crew.getIgrokCar();
        this.serializator = new ChaseObjectXmlSerializable(this);
        this.serializator.registerObjectXmlSerializable();
    }

    public static void constructStarted(ScenarioHost host) {
        chase00090 ware = new chase00090();
        ware.setHost(host);
        ware.shootAnimation = new ResqueDorothyShootAnimate(ware, ware.banditsCar, false);
        ware.start();
        ware.banditsCar.showOnMap(true);
    }

    public void setHost(ScenarioHost scenarioHost) {
        scenarioHost.registerController(this);
        this.host = scenarioHost;
    }

    private void deleteBandits() {
        eng.console("switch Dword_Freight_Argosy_Passanger_Window 0");
        this.banditsCar.leave_target();
        this.banditsCar.deactivate();
    }

    public static void deinit() {
        if (null != instance) {
            chase00090 singleton = instance;
            instance = null;
            if (null != singleton.shootAnimation) {
                singleton.shootAnimation.finish();
            }
            singleton.stopAnimateDorothy(true);
            singleton.deleteBandits();
            singleton.finishImmediately();
            singleton.finish();
        }
    }

    public void finish() {
        if (!this.f_finished) {
            super.finish();
            this.serializator.unRegisterObjectXmlSerializable();
            this.host.unregisterController(this);
        }
    }

    public void gameDeinitLaunched() {
        chase00090.deinit();
    }

    private void debugMessage(String message) {
        if (NO_MESSAGES) {
            return;
        }
        eng.writeLog(message);
        eng.pager(message);
    }

    private void updateParamsFromConfig() {
        if (null != this.config) {
            this.minutesToEscape = this.config.getTimeToRescue();
            this.distanceToEscape = this.config.getDistanceToRescue();
            this.distanceToKill = this.config.getDistanceToKill();
            this.distanceToShoot = this.config.getDistanceToShoot();
        }
    }

    public void run() {
        this.updateParamsFromConfig();
        if (this.scheduleChasingStart && 0 != this.banditsCar.getAi_player() && 0 != this.playerCar.getAi_player()) {
            this.startChasingPlayer();
            this.scheduleChasingStart = false;
        }
        if (!this.isChaserCarRegistered && 0 != this.banditsCar.getCar()) {
            this.banditsCar.registerCar("banditcar");
            this.isChaserCarRegistered = true;
        }
        this.animateDorothy();
        this.animateChasing();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void animateChasing() {
        Object object = this.latch;
        synchronized (object) {
            if (!this.predefinedAnimationFinished) {
                this.needShooting = true;
                return;
            }
        }
        vectorJ playerPosition = this.playerCar.gPosition();
        vectorJ banditsPosition = this.banditsCar.gPosition();
        if (this.escapedFromChase(playerPosition, banditsPosition)) {
            this.debugMessage("escaped from bandits");
            this.needShooting = false;
            if (!this.debugMode) {
                this.stopChasingPlayer();
                this.successRescued();
                chase00090.deinit();
            }
        } else if (playerPosition.len2(banditsPosition) < this.distanceToShoot * this.distanceToShoot) {
            this.debugMessage("shooting distance");
            this.needShooting = true;
            this.playerCarBoundingShpere.setCenter(playerPosition.x, playerPosition.y, playerPosition.z);
            if (this.playerCarBoundingShpere.intersecs(this.previousBanditsPosition, banditsPosition) && !this.debugMode && eng.canRunScenarioAnimation()) {
                this.debugMessage("death distance");
                this.finishImmediately();
                this.blowPlayersCar();
                chase00090.deinit();
            }
            this.previousBanditsPosition = banditsPosition;
        } else {
            this.needShooting = false;
        }
    }

    public static void setDebugModeOff() {
        if (null != instance) {
            chase00090.instance.debugMode = false;
        }
    }

    public static void setDebugMode() {
        if (null != instance) {
            chase00090.instance.debugMode = true;
        }
    }

    private void animateDorothy() {
        if (null == this.dorothyAnimation) {
            if (null == dorothy) {
                return;
            }
            this.dorothyAnimation = new SCRtalkingperson(dorothy.getModel());
            this.dorothyAnimation.playAnimation("DOROTY_NEW_pas_cycle", 1.0);
        }
    }

    private void stopChasingPlayer() {
        this.banditsCar.leave_target();
        this.banditsCar.autopilotTo(this.barInSantaBarbaraPosition);
        this.debugMessage("Bandit went to SB BAR");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void startChasingPlayer() {
        Object object = this.latch;
        synchronized (object) {
            assert (!this.predefinedAnimationFinished) : "illegal state: chase already in progress";
            if (null == this.chaseStartTime) {
                this.chaseStartTime = new CoreTime();
            }
            this.predefinedAnimationFinished = true;
        }
        this.playerCarBoundingShpere = new Sphere(0.0, 0.0, 0.0, this.distanceToKill);
        this.previousBanditsPosition = this.banditsCar.gPosition();
        Chase chase = new Chase();
        chase.setParameters("easyChasing");
        chase.makechase(this.banditsCar, this.playerCar);
        this.debugMessage("Bandit started chase player");
    }

    private void stopAnimateDorothy(boolean removeFromCar) {
        if (null != this.dorothyAnimation) {
            this.dorothyAnimation.stop();
            this.dorothyAnimation = null;
        }
        if (null != dorothy && removeFromCar) {
            dorothy.abondoneCar(this.playerCar);
            dorothy = null;
        }
    }

    private void blowPlayersCar() {
        drvscripts.BlowScene(Crew.getIgrok(), Crew.getIgrokCar());
        this.stopAnimateDorothy(false);
        this.stopChasingPlayer();
        if (null != this.shootAnimation) {
            this.shootAnimation.finish();
            this.shootAnimation = null;
        }
    }

    private void successRescued() {
        this.shootAnimation.finish();
        this.shootAnimation = null;
        this.stopAnimateDorothy(true);
        this.deleteBandits();
        EventsControllerHelper.messageEventHappened("Dorothy chase finished");
    }

    public static aiplayer getDorothy() {
        return dorothy;
    }

    public static void setDorothy(aiplayer dorothy) {
        chase00090.dorothy = dorothy;
    }

    public static void createDorothyPassanger() {
        dorothy = aiplayer.getSimpleAiplayer("SC_DOROTHYLOW");
        dorothy.bePassangerOfCar(Crew.getIgrokCar());
    }

    public static void stopDorothyPassanger() {
        dorothy.abondoneCar(Crew.getIgrokCar());
        dorothy = null;
    }

    public void finalizeDeserialization() {
        this.shootAnimation = new ResqueDorothyShootAnimate(this, this.banditsCar, true);
        this.scheduleChasingStart = true;
        this.start();
    }

    public String getId() {
        return MY_SERIALIZATION_UID;
    }

    public void aimed_hard() {
    }

    public void aimed() {
    }

    public boolean proceedShooting() {
        return true;
    }

    public boolean shootMade() {
        this.shootSeries.animate((double)System.currentTimeMillis() / 1000.0);
        return this.needShooting && this.shootSeries.isShooting();
    }

    public boolean useShootDetection() {
        return true;
    }
}

