/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.Vector;
import rnrcore.CoreTime;

public class RaceHistory {
    static RaceHistory RH = new RaceHistory();
    static final int simpleRace = 1;
    static final int bigRace = 2;
    static final int looserPlace = 0;
    static final int firstPlace = 1;
    static final int secondPlace = 2;
    static final int thirdPlace = 3;
    private Vector races = new Vector();

    private boolean wonRace(CoreTime diff, int typerace) {
        if (this.races.size() == 0) {
            return false;
        }
        singleRace rc = (singleRace)this.races.lastElement();
        if (rc.typeRace == typerace && rc.racePlace != 0) {
            return new CoreTime().moreThanOnTime(rc.timeFinish, diff) <= 0;
        }
        return false;
    }

    private boolean lostRace(int typerace) {
        if (this.races.size() == 0) {
            return false;
        }
        singleRace rc = (singleRace)this.races.lastElement();
        if (rc.typeRace == typerace) {
            return rc.racePlace == 0;
        }
        return false;
    }

    private boolean lostNumRaces(int typerace, int count) {
        if (this.races.size() < count) {
            return false;
        }
        int cnt = 0;
        for (int i = this.races.size() - 1; i >= 0; --i) {
            singleRace rc = (singleRace)this.races.elementAt(i);
            if (rc.typeRace != typerace) continue;
            if (rc.racePlace != 0) break;
            ++cnt;
        }
        return cnt >= count;
    }

    public void finishRace(int type, int place) {
        singleRace rc = new singleRace();
        rc.typeRace = type;
        rc.racePlace = place;
        rc.timeFinish = new CoreTime();
        this.races.add(rc);
    }

    public boolean wonBigRace(CoreTime diff) {
        return this.wonRace(diff, 2);
    }

    public boolean wonSimpleRace(CoreTime diff) {
        return this.wonRace(diff, 1);
    }

    public boolean lostBigRace() {
        return this.lostRace(2);
    }

    public boolean lostCountsOfBigRaces() {
        return this.lostNumRaces(2, 3);
    }

    class singleRace {
        CoreTime timeStart = null;
        CoreTime timeFinish = null;
        int typeRace;
        int racePlace = 0;

        singleRace() {
        }
    }
}

