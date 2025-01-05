/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers;

import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import players.Chase;
import players.Crew;
import players.actorveh;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.traffic;
import rnrcore.vectorJ;
import rnrscenario.Scanimatedtask;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.RACErace_state_single;
import rnrscenario.controllers.RACErace_states;
import rnrscenario.controllers.RACEspace_conditions;
import rnrscenario.controllers.RaceFailCondition;
import rnrscr.Helper;

@ScenarioClass(scenarioStage=-1, fieldWithDesiredStage="scenarioStage")
public abstract class RACEspace
extends Scanimatedtask {
    private final int scenarioStage;
    private static final int updatestate = 1;
    private static final int completestate = 2;
    private static final int finishstate = 4;
    private static final int cyclestate = 8;
    public static final int FINISH_RACE_END_SCENE = 465;
    public static final int FINISH_RACE_END_SCENE_BADLY = 530;
    public static final String FINISH_RACE_END_SCENE_METHOD = "finish_race_end_scene";
    public static final String FINISH_RACE_END_SCENE_BADLY_METHOD = "finish_race_end_scene_badly";
    private int raceFSMstate;
    protected RACErace_states states;
    protected RACEspace_conditions conditions;
    protected RaceFailCondition failDetector;
    private int event_id = 0;
    private int event_badly_id = 0;
    protected boolean m_isBadEnd = false;
    private boolean scheduleCleanTraffic = true;

    protected void clearResources() {
        event.removeEventObject(this.event_badly_id);
        event.removeEventObject(this.event_id);
        this.deleteParticipants();
    }

    public void finish_race_end_scene() {
        assert (0 != this.event_id);
        this.clearResources();
    }

    public void finish_race_end_scene_badly() {
        assert (0 != this.event_badly_id);
        this.clearResources();
        this.m_isBadEnd = true;
    }

    private void deleteParticipants() {
        Set<actorveh> participants = this.states.getParticipants();
        for (actorveh participant : participants) {
            if (Helper.isCarLive(participant)) continue;
            participant.leave_target();
            participant.deactivate();
        }
        participants.clear();
    }

    public RACEspace(String racename, int tip, int currentScenarioStage) {
        super(tip, true);
        this.scenarioStage = currentScenarioStage;
        this.conditions = new RACEspace_conditions(racename, this.scenarioStage);
        this.states = new RACErace_states(this.scenarioStage);
        this.failDetector = null;
        this.raceFSMstate = 1;
        eng.CreateInfinitScriptAnimation(this);
        eng.blockSO(1024);
        this.event_id = event.eventObject(465, this, FINISH_RACE_END_SCENE_METHOD);
        this.event_badly_id = event.eventObject(530, this, FINISH_RACE_END_SCENE_BADLY_METHOD);
    }

    public void addParticipant(actorveh pl) {
        this.states.addParticipant(pl);
    }

    boolean makeBanditsChasingPlayer() {
        actorveh piterPenCar;
        actorveh playerCar = Crew.getIgrokCar();
        if (this.canStartChase(playerCar, piterPenCar = Crew.getMappedCar("ARGOSY BANDIT"))) {
            Chase chase = new Chase();
            chase.paramModerateChasing();
            chase.be_ahead(piterPenCar, playerCar);
            piterPenCar.setNeverUnloadFlag();
            return true;
        }
        return false;
    }

    private boolean canStartChase(actorveh playerCar, actorveh piterPenCar) {
        return null != playerCar && null != piterPenCar && 0 != playerCar.getAi_player() && 0 != piterPenCar.getAi_player();
    }

    public void start() {
        eng.makePoliceImmunity(true);
        this.f_started = true;
        Set<actorveh> participants = this.states.getParticipants();
        if (participants.isEmpty()) {
            return;
        }
        Iterator<actorveh> iter = participants.iterator();
        Vector<actorveh> vec_participants = new Vector<actorveh>();
        while (iter.hasNext()) {
            actorveh pl = iter.next();
            if (Helper.isCarLive(pl)) continue;
            vec_participants.add(pl);
        }
        actorveh.makerace(vec_participants, this.conditions.gRaceName());
    }

    public void placeallOnStart() {
        Set<actorveh> participants = this.states.getParticipants();
        if (participants.isEmpty()) {
            return;
        }
        Iterator<actorveh> iter = participants.iterator();
        Vector<actorveh> vec_participants = new Vector<actorveh>();
        while (iter.hasNext()) {
            actorveh pl = iter.next();
            pl.stoprace();
            vec_participants.add(pl);
        }
        actorveh.aligncars_inRaceStart(vec_participants, this.conditions.gRaceName(), 20.0, 10.0, 2, 1);
    }

    public void placeallOnFinish() {
        eng.startMangedFadeAnimation();
        Set<actorveh> participants = this.states.getParticipants();
        if (participants.isEmpty()) {
            return;
        }
        Iterator<actorveh> iter = participants.iterator();
        Vector<actorveh> vec_participants = new Vector<actorveh>();
        while (iter.hasNext()) {
            actorveh pl = iter.next();
            pl.leave_target();
            pl.stoprace();
            pl.sVeclocity(0.0);
            vec_participants.add(pl);
        }
        actorveh.aligncars_inRaceFinish(vec_participants, this.conditions.gRaceName(), 40.0, 0.0, 1, 0);
    }

    public void placeAllOnFinishIfPlayerNotFinished() {
        eng.startMangedFadeAnimation();
        Set<actorveh> participants = this.states.getParticipants();
        if (participants.isEmpty()) {
            return;
        }
        Iterator<actorveh> iter = participants.iterator();
        Vector<actorveh> vec_participants = new Vector<actorveh>();
        while (iter.hasNext()) {
            actorveh pl = iter.next();
            pl.leave_target();
            pl.stoprace();
            pl.sVeclocity(0.0);
            if (this.states.getState(pl).isFinished() && !Helper.isCarLive(pl)) continue;
            vec_participants.add(pl);
        }
        actorveh.aligncars_inRaceFinish(vec_participants, this.conditions.gRaceName(), 40.0, 0.0, 1, 0);
    }

    public void finish_onlast(boolean useforcemajor) {
        if (useforcemajor) {
            this.conditions.setcondition(4097);
        } else {
            this.conditions.setcondition(1);
        }
    }

    public void finish_onvip(boolean useforcemajor) {
        if (useforcemajor) {
            this.conditions.setcondition(4112);
        } else {
            this.conditions.setcondition(16);
        }
    }

    public void finish_onfirst(boolean useforcemajor) {
        if (useforcemajor) {
            this.conditions.setcondition(4352);
        } else {
            this.conditions.setcondition(256);
        }
    }

    public void finish_onforcemajor() {
        this.conditions.setcondition(4096);
    }

    void updateStates(double dt) {
        this.states.setTimeElapsed(dt);
        Set<actorveh> players = this.states.getParticipants();
        if (players == null || players.size() == 0) {
            return;
        }
        Iterator<actorveh> iter = players.iterator();
        boolean firstonefinished = false;
        boolean lastonefinished = false;
        boolean vipfinished = false;
        while (iter.hasNext()) {
            actorveh pl = iter.next();
            RACErace_state_single state = this.states.getState(pl);
            if (state.isFinished()) continue;
            vectorJ posparticipant = pl.gPosition();
            state.setDistance(this.conditions.checkPosition(posparticipant));
            state.setFinished(this.conditions.checkPositionOnFinish(state.getDistance()));
            if (state.isFinished()) {
                if (!vipfinished) {
                    vipfinished = Helper.isCarLive(pl);
                }
                lastonefinished = this.states.getLastplace() == players.size();
                state.setPlace(this.states.getLastplace());
                this.states.setLastplace(this.states.getLastplace() + 1);
            }
            if (this.states.isAnyonfinish()) continue;
            this.states.setAnyonfinish(state.isFinished());
            firstonefinished = this.states.isAnyonfinish();
        }
        this.states.updatePlaces();
        if (firstonefinished) {
            this.states.setStatesucceded(this.states.getStatesucceded() | 0x100);
        }
        if (lastonefinished) {
            this.states.setStatesucceded(this.states.getStatesucceded() | 1);
        }
        if (vipfinished) {
            this.states.setStatesucceded(this.states.getStatesucceded() | 0x10);
        }
        if (this.failDetector != null && this.failDetector.raceFailed()) {
            this.states.setStatesucceded(this.states.getStatesucceded() | 0x1000);
        }
        this.raceFSMstate = 2;
    }

    void completeConditions() {
        this.raceFSMstate = this.conditions.compareConditions(this.states.getStatesucceded()) ? 4 : 8;
    }

    void cycleRaceFSM() {
        switch (this.raceFSMstate) {
            case 8: {
                this.raceFSMstate = 1;
            }
        }
    }

    void workFSM(double dt) {
        switch (this.raceFSMstate) {
            case 1: {
                this.updateStates(dt);
                if (this.raceFSMstate != 1) break;
                return;
            }
            case 2: {
                this.completeConditions();
                break;
            }
            case 4: {
                this.finishRace();
                return;
            }
            case 8: {
                this.cycleRaceFSM();
                return;
            }
        }
        this.workFSM(dt);
    }

    public boolean animaterun(double dt) {
        if (eng.canRunScenarioAnimation()) {
            this.workFSM(dt);
        }
        return this.raceFSMstate == 4;
    }

    protected abstract void finishRace();

    public void run() {
        if (this.scheduleCleanTraffic) {
            traffic.enterChaseModeImmediately();
            this.scheduleCleanTraffic = false;
        }
    }

    public RACEspace_conditions getConditions() {
        return this.conditions;
    }

    public void setConditions(RACEspace_conditions conditions) {
        this.conditions = conditions;
    }

    public RaceFailCondition getFailDetector() {
        return this.failDetector;
    }

    public void setFailDetector(RaceFailCondition failDetector) {
        this.failDetector = failDetector;
    }

    public int getRaceFSMstate() {
        return this.raceFSMstate;
    }

    public void setRaceFSMstate(int raceFSMstate) {
        this.raceFSMstate = raceFSMstate;
    }

    public RACErace_states getStates() {
        return this.states;
    }

    public void setStates(RACErace_states states) {
        this.states = states;
    }
}

