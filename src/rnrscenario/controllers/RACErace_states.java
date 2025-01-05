/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Set;
import players.actorveh;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.RACErace_state_single;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@ScenarioClass(scenarioStage=-1, fieldWithDesiredStage="scenarioStage")
public class RACErace_states {
    private final int scenarioStage;
    private double timeElapsed = 0.0;
    private boolean anyonfinish = false;
    private int lastplace = 1;
    private int statesucceded = 0;
    private HashMap<actorveh, RACErace_state_single> participants = new HashMap();

    public RACErace_states(int scenarioStage) {
        this.scenarioStage = scenarioStage;
    }

    public void addParticipant(actorveh player) {
        if (!this.participants.containsKey(player)) {
            this.participants.put(player, new RACErace_state_single(this.scenarioStage));
        }
    }

    public Set<actorveh> getParticipants() {
        return this.participants.keySet();
    }

    public RACErace_state_single getState(actorveh pl) {
        if (pl == null) {
            return null;
        }
        return this.participants.get(pl);
    }

    public void updatePlaces() {
        Collection<RACErace_state_single> coll = this.participants.values();
        ArrayList<RACErace_state_single> lst = new ArrayList<RACErace_state_single>(coll);
        Collections.sort(lst);
        ListIterator<RACErace_state_single> iter = lst.listIterator();
        int place = this.lastplace;
        while (iter.hasNext()) {
            RACErace_state_single data = (RACErace_state_single)iter.next();
            if (data.isFinished()) continue;
            data.setPlace(place++);
        }
    }

    public double getTimeElapsed() {
        return this.timeElapsed;
    }

    public void setTimeElapsed(double timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public boolean isAnyonfinish() {
        return this.anyonfinish;
    }

    public void setAnyonfinish(boolean anyonfinish) {
        this.anyonfinish = anyonfinish;
    }

    public int getLastplace() {
        return this.lastplace;
    }

    public void setLastplace(int lastplace) {
        this.lastplace = lastplace;
    }

    public int getStatesucceded() {
        return this.statesucceded;
    }

    public void setStatesucceded(int statesucceded) {
        this.statesucceded = statesucceded;
    }

    public HashMap<actorveh, RACErace_state_single> getParticipantsAllData() {
        return this.participants;
    }

    public void setParticipantsAllData(HashMap<actorveh, RACErace_state_single> data) {
        this.participants = data;
    }
}

