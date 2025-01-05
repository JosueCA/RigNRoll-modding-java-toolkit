/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers;

import players.CarName;
import players.Chase;
import players.Crew;
import players.CutSceneAuxPersonCreator;
import players.ScenarioPersonPassanger;
import players.actorveh;
import players.aiplayer;
import rnrcore.ChaseKohObjectXmlSerializable;
import rnrcore.ObjectXmlSerializable;
import rnrcore.SCRuniperson;
import rnrcore.TypicalAnm;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.matrixJ;
import rnrcore.teleport.ITeleported;
import rnrcore.teleport.MakeTeleport;
import rnrcore.vectorJ;
import rnrscenario.ChaseKohShootAnimate;
import rnrscenario.IShootChasing;
import rnrscenario.animation.ShootingSeriesAnimation;
import rnrscenario.config.Config;
import rnrscenario.config.ConfigManager;
import rnrscenario.configurators.ChaseCochConfig;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.ScenarioController;
import rnrscenario.controllers.ScenarioHost;
import rnrscenario.scenes.CrashBarScene;
import rnrscr.parkingplace;
import scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage=15)
public class ChaseKoh
extends TypicalAnm
implements IShootChasing,
ScenarioController {
    public static final String POINT_START = "ChaseKohStart";
    public static final String POINT_FINISH = "ChaseKohFinish";
    public static final String PROC_START = "KeyPoint_2050";
    public static final String DWORD_BAR_INSIDE = "Dword_BarIn4_Crash";
    public static final String MESSAGE_CREATE_START_SCENE = "start 2050";
    public static final String MESSAGE_SUCCES = "Chase Koh succes";
    public static final String MESSAGE_FAIL = "Chase Koh fail";
    public static final String MESSAGE_FAR = "Chase Koh far";
    public static final String DWORD_ChaseKoh = "DWORD_ChaseKoh";
    public static final String DWORD_Bar = "DWORD_ChaseKoh_DemolishedBar";
    private static final double DISTANCE_CHASE = 500.0;
    private static final double DISTANCE_KILL = 20.0;
    private static final double VELOCITY_TO_BLOW_AFTER_TELEPORT = 1.0;
    private static final int[] EVENTS = new int[]{22050};
    private static final String[] EVENT_METHODS = new String[]{"enter_lastchance"};
    private actorveh cochCar;
    private actorveh playerCar;
    private boolean needStopAnimation = false;
    private boolean scheduleAfterLoadActions = false;
    private final ScenarioHost host;
    private final ObjectXmlSerializable serializator;
    private static ChaseKoh instance = null;
    private TemporarySceneRoots inbar_sceneroots = null;
    private ShootCount shooting = null;
    private FarAway faraway;
    private boolean needResetAiParameters = true;
    private TooLong tooLong;
    private ChaseKohShootAnimate shoot_animation = null;
    private ShootingSeriesAnimation shootSeries = new ShootingSeriesAnimation();
    private actorveh carkohForShootAnimationSerialization = null;
    private WantBlowCar want_blow_car = null;
    private boolean chase_started = false;
    private aiplayer koh_chase_player = null;
    private boolean _last_chance_failed = false;
    private final int[] event_ids = new int[EVENTS.length];
    private boolean firstTime = true;

    public void gameDeinitLaunched() {
        this.endChase();
    }

    private static double sqrDistanseFromPlayerToCar(actorveh car) {
        assert (null != car);
        vectorJ otherPosition = car.gPosition();
        vectorJ playerPosition = Crew.getIgrokCar().gPosition();
        return otherPosition.len2(playerPosition);
    }

    public ChaseKoh(ScenarioHost host, boolean isGameLoading) {
        Config config = ConfigManager.getGlobal().getConfig(1);
        assert (config instanceof ChaseCochConfig) : "illegal type of config";
        this.tooLong = new TooLong((ChaseCochConfig)config);
        this.playerCar = Crew.getIgrokCar();
        if (isGameLoading) {
            this.scheduleAfterLoadActions = true;
        } else {
            this.playerCar.teleport(eng.getControlPointPosition(POINT_START));
            MakeTeleport.teleport(new TeleportToMiWuk());
        }
        eng.CreateInfinitScriptAnimation(this);
        instance = this;
        for (int i = 0; i < EVENTS.length; ++i) {
            this.event_ids[i] = event.eventObject(EVENTS[i], this, EVENT_METHODS[i]);
        }
        this.host = host;
        this.host.registerController(this);
        this.serializator = new ChaseKohObjectXmlSerializable(this);
        this.serializator.registerObjectXmlSerializable();
    }

    public static ChaseKoh getInstance() {
        return instance;
    }

    public boolean animaterun(double externalCounterValue) {
        if (!eng.canRunScenarioAnimation()) {
            return false;
        }
        if (this.firstTime) {
            if (this.scheduleAfterLoadActions) {
                this.cochCar.setNeverUnloadFlag();
            }
            eng.console("switch Dword_Gepard_Passanger_Window 1");
            eng.setdword(DWORD_ChaseKoh, 1);
            this.firstTime = false;
        }
        if (this.chase_started) {
            this.shootSeries.animate(externalCounterValue);
            if (this.shooting.done() || 400.0 > ChaseKoh.sqrDistanseFromPlayerToCar(this.cochCar)) {
                EventsControllerHelper.messageEventHappened(MESSAGE_SUCCES);
                this.chase_started = false;
            } else if (this.faraway.done() || this.tooLong.done(externalCounterValue) || this._last_chance_failed) {
                this.deleteCoch();
                this.finishShootingAnimation();
                EventsControllerHelper.messageEventHappened(MESSAGE_FAR);
                this.chase_started = false;
            } else if (this.needResetAiParameters && 0 != this.cochCar.getAi_player() && 0 != this.playerCar.getAi_player()) {
                Chase chase = new Chase();
                chase.paramModerateChasing();
                chase.be_ahead(this.cochCar, this.playerCar);
                this.needResetAiParameters = false;
            }
        }
        if (null != this.want_blow_car && this.want_blow_car.done()) {
            this.serializator.unRegisterObjectXmlSerializable();
            return true;
        }
        return this.needStopAnimation;
    }

    public void aimed() {
        if (null != this.shooting) {
            this.shooting.fast_shoot();
        }
    }

    public void aimed_hard() {
        if (null != this.shooting) {
            this.shooting.good_shoot();
        }
    }

    public boolean proceedShooting() {
        return this.chase_started;
    }

    public static void demolishBar() {
        eng.setdword(DWORD_ChaseKoh, 1);
    }

    public void start_chase() {
        if (null != this.inbar_sceneroots) {
            this.inbar_sceneroots.leave();
            this.inbar_sceneroots = null;
        }
        this.chase_started = true;
        vectorJ pos_start = eng.getControlPointPosition(POINT_START);
        this.faraway = new FarAway(this.cochCar);
        this.shooting = new ShootCount((ChaseCochConfig)ConfigManager.getGlobal().getConfig(1), this.cochCar);
        this.cochCar.sPosition(pos_start);
        aiplayer person_player = new aiplayer("SC_KOHLOW");
        person_player.setModelCreator(new ScenarioPersonPassanger(), "koh");
        person_player.beDriverOfCar(this.cochCar);
        this.koh_chase_player = person_player;
        vectorJ pos = eng.getControlPointPosition(POINT_FINISH);
        this.cochCar.autopilotTo(pos);
        Chase chase = new Chase();
        chase.paramModerateChasing();
        chase.be_ahead(this.cochCar, this.playerCar);
        this.needResetAiParameters = false;
        this.shoot_animation = new ChaseKohShootAnimate(this, this.cochCar, false);
        this.carkohForShootAnimationSerialization = this.cochCar;
        eng.console("switch Dword_Gepard_Passanger_Window 1");
        eng.setdword(DWORD_ChaseKoh, 1);
    }

    public void finishShootingAnimation() {
        if (null != this.shoot_animation) {
            this.shoot_animation.finish();
        }
        this.shoot_animation = null;
        this.carkohForShootAnimationSerialization = null;
    }

    public void endChaseButContinueRun() {
        this.endChase();
        this.needStopAnimation = false;
        this.serializator.registerObjectXmlSerializable();
    }

    public void endChase() {
        this.chase_started = false;
        this.needStopAnimation = true;
        this.deleteCoch();
        for (int _eventid : this.event_ids) {
            event.removeEventObject(_eventid);
        }
        this.finishShootingAnimation();
        eng.console("switch Dword_Gepard_Passanger_Window 0");
        eng.setdword(DWORD_ChaseKoh, 0);
        this.serializator.unRegisterObjectXmlSerializable();
        this.host.unregisterController(this);
    }

    private void deleteCoch() {
        if (null != this.cochCar) {
            if (null != this.koh_chase_player) {
                this.koh_chase_player.abondoneCar(this.cochCar);
                this.koh_chase_player = null;
            }
            this.cochCar.showOnMap(false);
            this.cochCar.leave_target();
            this.cochCar.stop_autopilot();
            this.cochCar.deactivate();
            this.cochCar = null;
        }
    }

    public void scheduleCarBlow() {
        this.want_blow_car = new WantBlowCar();
    }

    public aiplayer getKohChased() {
        return this.koh_chase_player;
    }

    void enter_lastchance() {
        this._last_chance_failed = true;
    }

    public boolean shootMade() {
        if (null != this.shooting) {
            return this.shootSeries.isShooting() && this.shooting.isDistanceAllowToShoot();
        }
        return this.shootSeries.isShooting();
    }

    public boolean useShootDetection() {
        return true;
    }

    public boolean is_last_chance_failed() {
        return this._last_chance_failed;
    }

    public void set_last_chance_failed(boolean _last_chance_failed) {
        this._last_chance_failed = _last_chance_failed;
    }

    public actorveh getCarkohForShootAnimationSerialization() {
        return this.carkohForShootAnimationSerialization;
    }

    public void setCarkohForShootAnimationSerialization(actorveh carkohForShootAnimationSerialization) {
        if (null == carkohForShootAnimationSerialization) {
            return;
        }
        this.carkohForShootAnimationSerialization = carkohForShootAnimationSerialization;
        this.shoot_animation = new ChaseKohShootAnimate(this, carkohForShootAnimationSerialization, true);
        this.cochCar = carkohForShootAnimationSerialization;
        this.cochCar.autopilotTo(eng.getControlPointPosition(POINT_FINISH));
        this.faraway = new FarAway(this.cochCar);
        this.shooting = new ShootCount((ChaseCochConfig)ConfigManager.getGlobal().getConfig(1), this.cochCar);
        Chase chase = new Chase();
        chase.paramModerateChasing();
        chase.be_ahead(this.cochCar, this.playerCar);
    }

    public boolean isChase_started() {
        return this.chase_started;
    }

    public void setChase_started(boolean chase_started) {
        this.chase_started = chase_started;
    }

    public aiplayer getKoh_chase_player() {
        return this.koh_chase_player;
    }

    public void setKoh_chase_player(aiplayer koh_chase_player) {
        this.koh_chase_player = koh_chase_player;
    }

    public WantBlowCar getWant_blow_car() {
        return this.want_blow_car;
    }

    public void setWant_blow_car(WantBlowCar want_blow_car) {
        this.want_blow_car = want_blow_car;
    }

    public TooLong getTooLong() {
        return this.tooLong;
    }

    public void setTooLong(TooLong tooLong) {
        this.tooLong = tooLong;
    }

    final class TeleportToMiWuk
    implements ITeleported {
        TeleportToMiWuk() {
        }

        public void teleported() {
            vectorJ position = eng.getControlPointPosition(ChaseKoh.POINT_START);
            parkingplace place = parkingplace.findParkingByName("pk_MW_01", position);
            ChaseKoh.this.playerCar.makeParking(place, 0);
            ChaseKoh.this.cochCar = eng.CreateCarForScenario(CarName.CAR_DAKOTA, new matrixJ(), position);
            ChaseKoh.this.cochCar.showOnMap(true);
            aiplayer person_player = new aiplayer("SC_KOH");
            person_player.sPoolBased("koh_cut_scene_driver");
            person_player.setModelCreator(new CutSceneAuxPersonCreator(), "koh");
            person_player.beDriverOfCar(ChaseKoh.this.cochCar);
            SCRuniperson koh_person = person_player.getModel();
            koh_person.SetInWorld("bar_crash");
            Crew.addMappedCar("KOH", ChaseKoh.this.cochCar);
            ChaseKoh.this.inbar_sceneroots = new TemporarySceneRoots(ChaseKoh.this.cochCar, koh_person, person_player);
            eng.setdword(ChaseKoh.DWORD_BAR_INSIDE, 1);
            new CrashBarScene().run();
        }
    }

    public static final class WantBlowCar {
        boolean done() {
            double playerCarVelocity = Crew.getIgrokCar().gVelocity().length();
            if (1.0 < playerCarVelocity) {
                EventsControllerHelper.messageEventHappened("player car exploded");
                EventsControllerHelper.messageEventHappened("blowcar");
                return true;
            }
            return false;
        }
    }

    public static final class TooLong {
        private boolean firstTime = true;
        private double previousCounterValue = 0.0;
        private double time = 0.0;
        private final ChaseCochConfig config;

        public TooLong(ChaseCochConfig config) {
            this.config = config;
        }

        boolean done(double externalTimeCounter) {
            if (this.firstTime) {
                this.firstTime = false;
                this.previousCounterValue = externalTimeCounter;
                return false;
            }
            this.time += externalTimeCounter - this.previousCounterValue;
            this.previousCounterValue = externalTimeCounter;
            return this.config.getTimeToCatchCoch() < this.time;
        }

        public double getTime() {
            return this.time;
        }

        public void setTime(double time) {
            this.time = time;
        }
    }

    static final class FarAway {
        private actorveh target = null;

        FarAway(actorveh target) {
            this.target = target;
        }

        boolean done() {
            return ChaseKoh.sqrDistanseFromPlayerToCar(this.target) > 250000.0;
        }
    }

    static final class ShootCount {
        private final int shootsToKill;
        private final double distanceToHit;
        private final actorveh target;
        int count = -1;
        boolean was_good_shot = false;

        public ShootCount(ChaseCochConfig chaseCochConfig, actorveh targetToShoot) {
            this.target = targetToShoot;
            this.shootsToKill = chaseCochConfig.getShootsToKillCoch();
            this.distanceToHit = chaseCochConfig.getDistanceToHitCoch();
        }

        void fast_shoot() {
            if (this.isDistanceAllowToShoot()) {
                ++this.count;
            }
        }

        void good_shoot() {
            this.was_good_shot = true;
        }

        boolean done() {
            return this.was_good_shot || this.count > this.shootsToKill;
        }

        public boolean isDistanceAllowToShoot() {
            return ChaseKoh.sqrDistanseFromPlayerToCar(this.target) < this.distanceToHit * this.distanceToHit;
        }
    }

    static class TemporarySceneRoots {
        actorveh car;
        SCRuniperson person;
        aiplayer player;

        TemporarySceneRoots(actorveh car, SCRuniperson person, aiplayer player) {
            this.car = car;
            this.person = person;
            this.player = player;
            person.lockPerson();
        }

        void leave() {
            this.player.abondoneCar(this.car);
            this.person.unlockPerson();
        }
    }
}

