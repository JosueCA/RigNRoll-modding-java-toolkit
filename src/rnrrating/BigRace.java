/*
 * Decompiled with CFR 0.151.
 */
package rnrrating;

import java.util.ArrayList;
import rnrcore.CoreTime;
import rnrrating.IBigRaceEventsListener;
import rnrrating.RateSystem;
import rnrscenario.ScenarioFlagsManager;

public class BigRace {
    public static final int FLAG_SIMPLE = 1;
    public static final int FLAG_SCENARIO = 2;
    public static final int FLAG_EMMEDIATE = 4;
    public static final int series1 = 1;
    public static final int series2 = 2;
    public static final int series3 = 3;
    public static final int series4 = 4;
    public static final int series5 = 5;
    public static int series_highest = 4;
    public static int[] limits_series = new int[]{0, 100, 1000, 10000, 100000, 1000000};
    private static final double prob_disturbance = 0.9;
    private static final double frequency = 0.3333333333333333;
    private static BigRace race = null;
    private ArrayList<IBigRaceEventsListener> listeners = new ArrayList();
    private ArrayList<IBigRaceEventsListener> deleted_listeners = new ArrayList();
    private ArrayList<IBigRaceEventsListener> new_listeners = new ArrayList();
    private boolean listerns_are_working = false;
    private CoreTime lasttimesceduled = null;
    private CoreTime next_race = null;
    private boolean first_time_scheduled_race = true;
    private int series_low = 1;
    private int series_high = 2;
    private boolean mostercup_processed = false;

    BigRace() {
    }

    public static BigRace gReference() {
        if (race == null) {
            race = new BigRace();
        }
        return race;
    }

    public static final void process() {
        if (race == null) {
            return;
        }
        race.coreProcess();
    }

    public static final void initiate() {
        race = new BigRace();
        race.startBigRaceSystem();
    }

    public static final void deinit() {
        race = null;
    }

    void startBigRaceSystem() {
        this.series_low = 1;
        this.series_high = 2;
        this.mostercup_processed = false;
        this.next_race = null;
        this.lasttimesceduled = new CoreTime();
        this.lasttimesceduled.plus_days(2);
    }

    private double calculateProbability() {
        double rating = RateSystem.gLiveRating();
        double posibility = this.getCalculatedProbability(rating, this.series_low, this.series_high);
        if (posibility > 1.0 && this.series_high < series_highest) {
            ++this.series_low;
            ++this.series_high;
            posibility = this.calculateProbability();
        }
        return posibility;
    }

    private double ln(double value) {
        if (value <= 0.0) {
            value = 1.0;
        }
        return Math.log(value);
    }

    double getCalculatedProbability(double rating, int serieslow, int serieshigh) {
        int i = serieslow;
        int i1 = serieshigh;
        int i2 = serieshigh + 1;
        double res_posibility = rating < (double)limits_series[i] ? 0.0 : (serieshigh < series_highest ? (this.ln(rating) - this.ln(limits_series[i])) / (this.ln(limits_series[i1]) + (this.ln(limits_series[i2]) - this.ln(limits_series[i1])) * 0.9 - this.ln(limits_series[i])) : (rating - (double)limits_series[i]) / (double)(limits_series[i1] - limits_series[i]));
        return res_posibility;
    }

    private void sceduleBigRace(double probability) {
        if (this.mostercup_processed) {
            return;
        }
        int next_series = this.series_low;
        if (probability > Math.random()) {
            if (this.series_high == series_highest && this.mostercup_processed) {
                next_series = this.series_low;
            } else {
                next_series = this.series_high;
                boolean bl = this.mostercup_processed = this.series_high == series_highest;
                if (this.mostercup_processed && ScenarioFlagsManager.getInstance() != null) {
                    ScenarioFlagsManager.getInstance().setFlagValueTimed("Start_M_CUP_news", 2);
                }
            }
        } else {
            next_series = this.series_low;
        }
        this.next_race = new CoreTime();
        this.lasttimesceduled = new CoreTime(this.next_race);
        this.next_race.plus(CoreTime.days(2));
        int hours = (int)(23.0 * Math.random());
        int minuts = (int)(59.0 * Math.random());
        BigRace.prepareBigRace(1, next_series, limits_series[next_series], this.next_race.gYear(), this.next_race.gMonth(), this.next_race.gDate(), hours, minuts);
    }

    public static int sceduleScenarioRace(int days) {
        int next_series = BigRace.race.series_low;
        int hours = (int)(23.0 * Math.random());
        int minuts = (int)(59.0 * Math.random());
        BigRace.race.next_race = new CoreTime();
        BigRace.race.next_race.plus(CoreTime.days(days));
        return BigRace.prepareBigRace(6, next_series, limits_series[next_series], BigRace.race.next_race.gYear(), BigRace.race.next_race.gMonth(), BigRace.race.next_race.gDate(), hours, minuts);
    }

    private final void coreProcess() {
        CoreTime current = new CoreTime();
        if (this.lasttimesceduled != null) {
            
        	//
        	// RICK: variable had no value, guess it was true since later is changes to false...
        	boolean to_schedule = true;
        	// END
        	//
        	
            boolean bl = this.first_time_scheduled_race ? CoreTime.CompareByDays(current, this.lasttimesceduled) > 0 : (to_schedule = (double)CoreTime.CompareByDays(current, this.lasttimesceduled) * 0.3333333333333333 > 1.0);
            if (this.first_time_scheduled_race && to_schedule) {
                this.first_time_scheduled_race = false;
            }
            if (to_schedule) {
                double probability = this.calculateProbability();
                this.sceduleBigRace(probability);
            }
        }
    }

    public void DEBUGscheduleRace() {
        CoreTime raceTime = new CoreTime();
        CoreTime deltaTime = new CoreTime(0, 0, 0, 12, 0);
        raceTime.plus(deltaTime);
        BigRace.prepareBigRace(1, 1, limits_series[1], raceTime.gYear(), raceTime.gMonth(), raceTime.gDate(), raceTime.gHour(), raceTime.gMinute());
    }

    public static native int prepareBigRace(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7);

    public static native void teleportOnStart(int var0);

    public static native void setSemitailerForRaceForLivePlayer(int var0, String var1, int var2);

    public static void addListener(IBigRaceEventsListener lst) {
        if (BigRace.race.listerns_are_working) {
            BigRace.race.new_listeners.add(lst);
        } else {
            BigRace.race.listeners.add(lst);
        }
    }

    public static void removeListener(IBigRaceEventsListener lst) {
        if (BigRace.race.listerns_are_working) {
            BigRace.race.deleted_listeners.add(lst);
        } else {
            BigRace.race.listeners.remove(lst);
        }
    }

    public static void bigRaceStarted(int race_uid, boolean live_race) {
        BigRace.race.listerns_are_working = true;
        for (IBigRaceEventsListener lst : BigRace.race.listeners) {
            if (lst.getUid() != race_uid) continue;
            lst.raceStarted(live_race);
        }
        BigRace.race.listeners.addAll(BigRace.race.new_listeners);
        BigRace.race.listeners.removeAll(BigRace.race.deleted_listeners);
        BigRace.race.new_listeners.clear();
        BigRace.race.deleted_listeners.clear();
        BigRace.race.listerns_are_working = false;
    }

    public static void bigRaceFailedByLive(int race_uid) {
        BigRace.race.listerns_are_working = true;
        for (IBigRaceEventsListener lst : BigRace.race.listeners) {
            if (lst.getUid() != race_uid) continue;
            lst.raceFailed();
        }
        BigRace.race.listeners.addAll(BigRace.race.new_listeners);
        BigRace.race.listeners.removeAll(BigRace.race.deleted_listeners);
        BigRace.race.new_listeners.clear();
        BigRace.race.deleted_listeners.clear();
        BigRace.race.listerns_are_working = false;
    }

    public static void bigRaceFinishByLive(int race_uid, int place) {
        BigRace.race.listerns_are_working = true;
        for (IBigRaceEventsListener lst : BigRace.race.listeners) {
            if (lst.getUid() != race_uid) continue;
            lst.raceFinished(place);
        }
        BigRace.race.listeners.addAll(BigRace.race.new_listeners);
        BigRace.race.listeners.removeAll(BigRace.race.deleted_listeners);
        BigRace.race.new_listeners.clear();
        BigRace.race.deleted_listeners.clear();
        BigRace.race.listerns_are_working = false;
    }

    public boolean isFirst_time_scheduled_race() {
        return this.first_time_scheduled_race;
    }

    public void setFirst_time_scheduled_race(boolean first_time_scheduled_race) {
        this.first_time_scheduled_race = first_time_scheduled_race;
    }

    public CoreTime getLasttimesceduled() {
        return this.lasttimesceduled;
    }

    public void setLasttimesceduled(CoreTime lasttimesceduled) {
        this.lasttimesceduled = lasttimesceduled;
    }

    public boolean isMostercup_processed() {
        return this.mostercup_processed;
    }

    public void setMostercup_processed(boolean mostercup_processed) {
        this.mostercup_processed = mostercup_processed;
    }

    public CoreTime getNext_race() {
        return this.next_race;
    }

    public void setNext_race(CoreTime next_race) {
        this.next_race = next_race;
    }

    public int getSeries_high() {
        return this.series_high;
    }

    public void setSeries_high(int series_high) {
        this.series_high = series_high;
    }

    public int getSeries_low() {
        return this.series_low;
    }

    public void setSeries_low(int series_low) {
        this.series_low = series_low;
    }
}

