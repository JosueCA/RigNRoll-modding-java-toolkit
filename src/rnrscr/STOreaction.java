/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.HashMap;
import java.util.Map;
import rnrcore.CoreTime;
import rnrscr.RaceHistory;
import rnrscr.RandomAccesString;
import rnrscr.STOHistory;
import rnrscr.stoscene;

public class STOreaction {
    private static final String sceneType1 = "sto1";
    private static final String sceneType2 = "sto2";
    private static final String sceneType3 = "sto3";
    private static final String sceneType4 = "sto4";
    private static final String sceneType5 = "sto5";
    private static final String XXX_BadRacer1 = "XXX_BadRacer1";
    private static final String XXX_BadRacer2 = "XXX_BadRacer2";
    private static final String XXX_Champion1 = "XXX_Champion1";
    private static final String XXX_Champion2 = "XXX_Champion2";
    private static final String XXX_Champion3 = "XXX_Champion3";
    private static final String XXX_Lostrace1 = "XXX_Lostrace1";
    private static final String XXX_Lostrace2 = "XXX_Lostrace2";
    private static final String XXX_Lostrace3 = "XXX_Lostrace3";
    private static final String XXX_NicA_Howmytruck1 = "XXX_NicA_Howmytruck1";
    private static final String XXX_NicA_Howmytruck2 = "XXX_NicA_Howmytruck2";
    private static final String XXX_NicA_Howmytruck3 = "XXX_NicA_Howmytruck3";
    private static final String XXX_NicA_Howmytruck4 = "XXX_NicA_Howmytruck4";
    private static final String XXX_NicA_Howmytruck5 = "XXX_NicA_Howmytruck5";
    private static final String XXX_Letscheck1 = "XXX_Letscheck1";
    private static final String XXX_Letscheck2 = "XXX_Letscheck2";
    private static final String XXX_Letscheck3 = "XXX_Letscheck3";
    private static final String XXX_Letscheck4 = "XXX_Letscheck4";
    private static final String XXX_NicA_Gday = "XXX_NicA_Gday";
    private static final String XXX_NicA_Gevng = "XXX_NicA_Gevng";
    private static final String XXX_NicA_Gmng = "XXX_NicA_Gmng";
    private static final String XXX_NicA_Hello = "XXX_NicA_Hello";
    private static final String XXX_NicA_Hi = "XXX_NicA_Hi";
    private static final String XXX_Gday = "XXX_Gday";
    private static final String XXX_Gevng = "XXX_Gevng";
    private static final String XXX_Gmng = "XXX_Gmng";
    private static final String XXX_Hello = "XXX_Hello";
    private static final String XXX_Hi = "XXX_Hi";
    private static final String XXX_Lookgood = "XXX_Lookgood";
    private static final String XXX_Nicetomeet = "XXX_Nicetomeet";
    private static final String XXX_Whoisthat = "XXX_Whoisthat";
    private static final String XXX_DamagedTruck1 = "XXX_DamagedTruck1";
    private static final String XXX_NewTruck1 = "XXX_NewTruck1";
    private static final String XXX_NewTruck2 = "XXX_NewTruck2";
    private static final String XXX_NewTruck3 = "XXX_NewTruck3";
    private static final String XXX_Wonrace1 = "XXX_Wnrc1";
    private static final String XXX_Wonrace2 = "XXX_Wnrc2";
    private static final String XXX_Wonrace3 = "XXX_Wnrc3";
    Map<DayTime, RandomAccesString> vNickSalute = new HashMap<DayTime, RandomAccesString>();
    Map<DayTime, RandomAccesString> vRepSalute1st = new HashMap<DayTime, RandomAccesString>();
    RandomAccesString vRepSalute2nd = new RandomAccesString();
    RandomAccesString vRepSalute2ndNewTruck = new RandomAccesString();
    RandomAccesString vRepSalute2ndDamagedTruck = new RandomAccesString();
    RandomAccesString vRepWonrace = new RandomAccesString();
    RandomAccesString vRepLostRace = new RandomAccesString();
    RandomAccesString vRepChampion = new RandomAccesString();
    RandomAccesString vRepBadRacer = new RandomAccesString();
    RandomAccesString vNicReply = new RandomAccesString();
    RandomAccesString vRepReply = new RandomAccesString();

    STOreaction() {
        RandomAccesString straccess = new RandomAccesString();
        straccess.add(XXX_NicA_Gmng);
        straccess.add(XXX_NicA_Hello);
        straccess.add(XXX_NicA_Hi);
        this.vNickSalute.put(DayTime.morning, straccess);
        straccess = new RandomAccesString();
        straccess.add(XXX_NicA_Gday);
        straccess.add(XXX_NicA_Hello);
        straccess.add(XXX_NicA_Hi);
        this.vNickSalute.put(DayTime.day, straccess);
        straccess = new RandomAccesString();
        straccess.add(XXX_NicA_Gevng);
        straccess.add(XXX_NicA_Hello);
        straccess.add(XXX_NicA_Hi);
        this.vNickSalute.put(DayTime.evening, straccess);
        straccess = new RandomAccesString();
        straccess.add(XXX_NicA_Hello);
        straccess.add(XXX_NicA_Hi);
        this.vNickSalute.put(DayTime.night, straccess);
        straccess = new RandomAccesString();
        straccess.add(XXX_Gmng);
        straccess.add(XXX_Hello);
        straccess.add(XXX_Hi);
        this.vRepSalute1st.put(DayTime.morning, straccess);
        straccess = new RandomAccesString();
        straccess.add(XXX_Gday);
        straccess.add(XXX_Hello);
        straccess.add(XXX_Hi);
        this.vRepSalute1st.put(DayTime.day, straccess);
        straccess = new RandomAccesString();
        straccess.add(XXX_Gevng);
        straccess.add(XXX_Hello);
        straccess.add(XXX_Hi);
        this.vRepSalute1st.put(DayTime.evening, straccess);
        straccess = new RandomAccesString();
        straccess.add(XXX_Hello);
        straccess.add(XXX_Hi);
        this.vRepSalute1st.put(DayTime.night, straccess);
        this.vRepSalute2nd.add(XXX_Lookgood);
        this.vRepSalute2nd.add(XXX_Nicetomeet);
        this.vRepSalute2nd.add(XXX_Whoisthat);
        this.vRepSalute2ndNewTruck.add(XXX_NewTruck1);
        this.vRepSalute2ndNewTruck.add(XXX_NewTruck2);
        this.vRepSalute2ndNewTruck.add(XXX_NewTruck3);
        this.vRepSalute2ndDamagedTruck.add(XXX_DamagedTruck1);
        this.vRepWonrace.add(XXX_Wonrace1);
        this.vRepWonrace.add(XXX_Wonrace2);
        this.vRepWonrace.add(XXX_Wonrace3);
        this.vRepLostRace.add(XXX_Lostrace1);
        this.vRepLostRace.add(XXX_Lostrace2);
        this.vRepLostRace.add(XXX_Lostrace3);
        this.vRepChampion.add(XXX_Champion1);
        this.vRepChampion.add(XXX_Champion2);
        this.vRepChampion.add(XXX_Champion3);
        this.vRepBadRacer.add(XXX_BadRacer1);
        this.vRepBadRacer.add(XXX_BadRacer2);
        this.vNicReply.add(XXX_NicA_Howmytruck1);
        this.vNicReply.add(XXX_NicA_Howmytruck2);
        this.vNicReply.add(XXX_NicA_Howmytruck3);
        this.vNicReply.add(XXX_NicA_Howmytruck4);
        this.vNicReply.add(XXX_NicA_Howmytruck5);
        this.vRepReply.add(XXX_Letscheck1);
        this.vRepReply.add(XXX_Letscheck2);
        this.vRepReply.add(XXX_Letscheck3);
        this.vRepReply.add(XXX_Letscheck4);
    }

    private DayTime getDayTime() {
        CoreTime time = new CoreTime();
        int hour = time.gHour();
        return hour >= 6 && hour < 12 ? DayTime.morning : (hour >= 12 && hour < 17 ? DayTime.day : (hour >= 17 && hour < 21 ? DayTime.evening : DayTime.night));
    }

    void createReaction(stoscene.STOPreset preset2, STOHistory history) {
        DayTime timeOfDay = this.getDayTime();
        preset2.Nic1 = this.vNickSalute.get((Object)timeOfDay).get();
        preset2.Nic2 = this.vNicReply.get();
        preset2.Rep2 = this.vRepReply.get();
        if (history.isFirstTimeHere()) {
            preset2.Rep1 = this.vRepSalute1st.get((Object)timeOfDay).get();
            preset2.sceneName = sceneType1;
        } else if (RaceHistory.RH.wonBigRace(CoreTime.hours(2))) {
            preset2.Rep1 = this.vRepChampion.get();
            preset2.sceneName = sceneType4;
        } else if (RaceHistory.RH.wonSimpleRace(CoreTime.hours(2))) {
            preset2.Rep1 = this.vRepWonrace.get();
            preset2.sceneName = sceneType4;
        } else if (RaceHistory.RH.lostCountsOfBigRaces()) {
            preset2.Rep1 = this.vRepBadRacer.get();
            preset2.sceneName = sceneType3;
        } else if (RaceHistory.RH.lostBigRace()) {
            preset2.Rep1 = this.vRepLostRace.get();
            preset2.sceneName = sceneType5;
        } else if (history.carIsDamaged()) {
            preset2.Rep1 = this.vRepSalute2ndDamagedTruck.get();
            preset2.sceneName = sceneType3;
        } else if (history.newTruck()) {
            preset2.Rep1 = this.vRepSalute2ndNewTruck.get();
            preset2.sceneName = sceneType2;
        } else if (history.isSecondOrMoreTimeHere()) {
            preset2.Rep1 = this.vRepSalute2nd.get();
            preset2.sceneName = sceneType2;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static enum DayTime {
        morning,
        day,
        evening,
        night;

    }
}

