/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import java.util.Vector;
import java.util.logging.Level;
import players.Crew;
import players.actorveh;
import rnrcore.Helper;
import rnrcore.INativeMessageEvent;
import rnrcore.SCRuniperson;
import rnrcore.SceneActorsPool;
import rnrcore.TypicalAnm;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.scenetrack;
import rnrcore.vectorJ;
import rnrloggers.MissionsLogger;
import rnrscenario.IShootChasing;
import rnrscr.camscripts;

public abstract class ShootChasing
extends TypicalAnm {
    private static final double ANGLE_MAX = 1.5707963267948966;
    private static final double ANGLE_MIN = -0.4537856055185257;
    private static final double SHOOT_DISTANCE = 100.0;
    private static final double SHOOT_MIN = 20.0;
    private static final double SHOOT_INTERVAL = 2.0;
    private String[] ACTOR_NAME = null;
    private String[] TRACKS = null;
    private static final String[] METHODS = new String[]{"setFrontCoef", "setSideCoef", "setBackCoef"};
    private static final int[] FLAGS = new int[]{512, 512, 516};
    private static final String[][] CLIPS = new String[][]{{"shoot_begin_front", "shoot_begin_side", "shoot_begin_back"}, {"shoot_fin_front", "shoot_fin_side", "shoot_fin_back"}, {"shoot_cyc_front", "shoot_cyc_side", "shoot_cyc_back"}};
    private static final int BEGIN = 0;
    private static final int FINISH = 1;
    private static final int CYCLE = 2;
    private static final int ALLTRACKS = 3;
    private static final int ALL_SIDES = 3;
    private long[] scenes = new long[3];
    private Rotation rotation = new Rotation();
    private SightActions actions = new SightActions();
    private Shooting shooting = new Shooting();
    private actorveh car_running = null;
    private actorveh car_chaser = null;
    private IShootChasing chase = null;
    private boolean finished = false;
    private boolean scenesPrepared = false;
    private boolean isFromLoadFlagForPrepareScenes = false;

    public ShootChasing(IShootChasing chase, actorveh car_chaser, actorveh car_running, String[] ACTOR_NAME, String[] TRACKS) {
        this.car_running = car_running;
        this.car_chaser = car_chaser;
        this.chase = chase;
        this.ACTOR_NAME = ACTOR_NAME;
        this.TRACKS = TRACKS;
    }

    protected final void init(boolean isFromLoad) {
        this.isFromLoadFlagForPrepareScenes = isFromLoad;
        eng.CreateInfinitScriptAnimation(this);
        this.createscenes();
        this.createeventslisteners();
        this.start_shoot();
    }

    protected abstract void prepareForScenes(boolean var1);

    protected abstract void prepareForFinish();

    protected abstract SCRuniperson getShoterModel();

    protected abstract SCRuniperson getGunModel();

    private boolean canCreateScenes() {
        int chaserCar;
        int n = chaserCar = this.getCar_chaser() == null ? 0 : this.getCar_chaser().getCar();
        if (chaserCar == 0) {
            return false;
        }
        int runningCar = this.getCar_running() == null ? 0 : this.getCar_running().getCar();
        return 0 != runningCar;
    }

    private void createscenes() {
        if (this.scenesPrepared || !this.canCreateScenes()) {
            return;
        }
        this.scenesPrepared = true;
        this.prepareForScenes(this.isFromLoadFlagForPrepareScenes);
        Vector<SceneActorsPool> pool = new Vector<SceneActorsPool>();
        pool.add(new SceneActorsPool("shooter", this.getShoterModel()));
        pool.add(new SceneActorsPool("gun", this.getGunModel()));
        Data data = new Data(Crew.getIgrokCar());
        for (int i = 0; i < 3; ++i) {
            this.scenes[i] = scenetrack.CreateSceneXMLPool(this.TRACKS[i], FLAGS[i], pool, data);
            if (this.scenes[i] != 0L) continue;
            MissionsLogger.getInstance().doLog("ShootChasing createscenes " + i + this.TRACKS[i], Level.INFO);
        }
    }

    private void createeventslisteners() {
        Helper.addNativeEventListener(new BeginFinished());
        Helper.addNativeEventListener(new FinishFinished());
    }

    private void start_shoot() {
        scenetrack.runFromStart(this.scenes[0]);
    }

    private void cycle_shoot() {
        this.shooting.start_shooting();
        scenetrack.runFromStart(this.scenes[2]);
        scenetrack.UpdateSceneFlags(this.scenes[2], 4);
    }

    private void finish_shoot() {
        this.shooting.end_shooting();
        scenetrack.UpdateSceneFlags(this.scenes[2], 256);
        scenetrack.runFromStart(this.scenes[1]);
    }

    private void turnoff_finish_shoot() {
        scenetrack.UpdateSceneFlags(this.scenes[1], 256);
    }

    public void finish() {
        this.prepareForFinish();
        this.finished = true;
        for (long scene : this.scenes) {
            scenetrack.DeleteScene(scene);
        }
    }

    public boolean animaterun(double dt) {
        if (this.finished) {
            return true;
        }
        this.createscenes();
        if (!this.scenesPrepared || eng.gameWorldStopped()) {
            return false;
        }
        this.updateRotation(dt);
        for (String aACTOR_NAME : this.ACTOR_NAME) {
            for (int i = 0; i < 3; ++i) {
                for (int side = 0; side < 3; ++side) {
                    scenetrack.ChangeClipParam(this.scenes[i], aACTOR_NAME, CLIPS[i][side], this, METHODS[side]);
                }
            }
        }
        return false;
    }

    public void setFrontCoef(camscripts.trackclipparams pars) {
        pars.Weight = this.rotation.front_coef;
    }

    public void setSideCoef(camscripts.trackclipparams pars) {
        pars.Weight = this.rotation.side_coef;
    }

    public void setBackCoef(camscripts.trackclipparams pars) {
        pars.Weight = this.rotation.back_coef;
    }

    private void updateRotation(double t) {
        Coef coef;
        if (null == this.car_running || null == this.chase) {
            coef = new Coef(Math.sin(t), false);
            coef = new Coef(2.0 * coef.coef - 1.0, false);
        } else {
            coef = this.animateStraightForward(this.car_running);
        }
        if (null != this.chase && !this.chase.proceedShooting()) {
            coef = new Coef(-2.0, false);
        }
        if (this.rotation.setCoef(coef.coef)) {
            this.actions.setOnSight(true);
            this.shooting.shoot(t, coef);
        } else {
            this.actions.setOnSight(false);
        }
    }

    @Deprecated
    private Coef animateFromCar(actorveh car) {
        double coef;
        boolean useChaseOnShootDetection;
        vectorJ pos0 = this.car_chaser.gPosition();
        matrixJ m0 = this.car_chaser.gMatrix();
        vectorJ pos1 = car.gPosition();
        vectorJ n_direction = vectorJ.oMinus(pos1, pos0);
        double len_2 = pos1.len2(pos0);
        boolean bl = useChaseOnShootDetection = this.chase != null && this.chase.useShootDetection();
        if (useChaseOnShootDetection && null != this.chase && !this.chase.shootMade()) {
            return new Coef(-2.0, false);
        }
        if (!useChaseOnShootDetection && len_2 > 10000.0) {
            return new Coef(-2.0, false);
        }
        n_direction.norm();
        double angle_sign = vectorJ.dot(n_direction, m0.v1);
        double cos_alfa = vectorJ.dot(n_direction, m0.v0);
        if (angle_sign > 0.0) {
            double angle = Math.acos(cos_alfa);
            coef = angle / 1.5707963267948966;
        } else {
            double angle = -Math.acos(cos_alfa);
            coef = -angle / -0.4537856055185257;
        }
        return new Coef(coef, len_2 <= 400.0);
    }

    private Coef animateStraightForward(actorveh car) {
        boolean useChaseOnShootDetection;
        vectorJ pos0 = this.car_chaser.gPosition();
        vectorJ pos1 = car.gPosition();
        double len_2 = pos1.len2(pos0);
        boolean bl = useChaseOnShootDetection = this.chase != null && this.chase.useShootDetection();
        if (useChaseOnShootDetection && null != this.chase && !this.chase.shootMade()) {
            return new Coef(-2.0, false);
        }
        if (!useChaseOnShootDetection && len_2 > 10000.0) {
            return new Coef(-2.0, false);
        }
        return new Coef(1.0, len_2 <= 400.0);
    }

    protected final actorveh getCar_chaser() {
        return this.car_chaser;
    }

    protected final actorveh getCar_running() {
        return this.car_running;
    }

    class Shooting {
        private double t_last = 0.0;
        private double t_start = 0.0;
        private boolean f_shooting = false;

        Shooting() {
        }

        void start_shooting() {
            this.f_shooting = true;
            this.t_start = this.t_last;
        }

        void end_shooting() {
            this.f_shooting = false;
        }

        void shoot(double t, Coef coef) {
            this.t_last = t;
            if (this.f_shooting) {
                if (t - this.t_start > 2.0) {
                    this.t_start = this.t_last;
                    if (null != ShootChasing.this.chase) {
                        ShootChasing.this.chase.aimed();
                    }
                }
                if (null != ShootChasing.this.chase && (coef.critical || coef.coef < 0.2)) {
                    ShootChasing.this.chase.aimed_hard();
                }
            }
        }
    }

    class SightActions {
        boolean on_sigth = false;
        boolean idle_state = true;

        SightActions() {
        }

        void setOnSight(boolean value) {
            if (value) {
                if (!this.on_sigth && this.idle_state) {
                    this.idle_state = false;
                    ShootChasing.this.start_shoot();
                }
            } else if (this.on_sigth) {
                this.stopShooting();
            }
            this.on_sigth = value;
        }

        void stopShooting() {
            ShootChasing.this.finish_shoot();
        }

        void start_shoot_finished() {
            if (this.on_sigth) {
                ShootChasing.this.cycle_shoot();
            } else {
                ShootChasing.this.finish_shoot();
            }
        }

        void finish_shoot_finished() {
            if (this.on_sigth) {
                ShootChasing.this.start_shoot();
            } else {
                ShootChasing.this.turnoff_finish_shoot();
                this.idle_state = true;
            }
        }
    }

    static class Coef {
        double coef;
        boolean critical;

        Coef(double coef, boolean critical) {
            this.coef = coef;
            this.critical = critical;
        }
    }

    final class FinishFinished
    implements INativeMessageEvent {
        FinishFinished() {
        }

        public String getMessage() {
            return "2070shootfinishfinish";
        }

        public void onEvent(String message) {
            ShootChasing.this.actions.finish_shoot_finished();
        }

        public boolean removeOnEvent() {
            return false;
        }
    }

    private final class BeginFinished
    implements INativeMessageEvent {
        private BeginFinished() {
        }

        public String getMessage() {
            return "2070shootbeginfinish";
        }

        public void onEvent(String message) {
            if (!ShootChasing.this.finished) {
                ShootChasing.this.actions.start_shoot_finished();
            }
        }

        public boolean removeOnEvent() {
            return false;
        }
    }

    static final class Rotation {
        double front_coef = 1.0;
        double side_coef = 0.0;
        double back_coef = 0.0;

        Rotation() {
        }

        public boolean setCoef(double value) {
            if (value >= 0.0 && value <= 1.0) {
                this.front_coef = value;
                this.side_coef = 1.0 - value;
                this.back_coef = 0.0;
                return true;
            }
            if (value < 0.0 && value > -1.0) {
                this.front_coef = 0.0;
                this.side_coef = 1.0 + value;
                this.back_coef = -value;
                return true;
            }
            return false;
        }
    }

    static final class Data {
        actorveh car;

        Data() {
        }

        Data(actorveh car) {
            this.car = car;
        }
    }
}

