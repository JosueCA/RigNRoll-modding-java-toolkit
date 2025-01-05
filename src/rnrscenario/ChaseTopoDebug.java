/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import java.util.Vector;
import players.Chase;
import players.Crew;
import players.Trajectory;
import players.actorveh;
import players.aiplayer;
import players.semitrailer;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscenario.ScenarioSave;
import rnrscenario.controllers.chaseTopo;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.scenarioscript;
import rnrscenario.test;
import rnrscr.MissionDialogs;

public class ChaseTopoDebug {
    public static void allTopo() {
        scenarioscript.script.getScenarioMachine().activateState("SC meet Topo", "SC meet Topo_phase_1");
    }

    public static void simplechase() {
        chaseTopo.PareGoodPsition GP = new chaseTopo.PareGoodPsition();
        GP.V = eng.getControlPointPosition("CP_exit5_1");
        actorveh car = eng.CreateCar_onway("FREIGHTLINER_ARGOSY", GP.V);
        car.sVeclocity(25.0);
        Crew.addMappedCar("ARGOSY BANDIT", car);
        aiplayer pl = aiplayer.getSimpleAiplayer("SC_BANDIT3LOW");
        pl.beDriverOfCar(car);
        Chase ch_ase = new Chase();
        ch_ase.paramModerateChasing();
        ch_ase.makechase(car, Crew.getIgrokCar());
    }

    public static void simpliestchase() {
        chaseTopo.PareGoodPsition GP = new chaseTopo.PareGoodPsition();
        vectorJ pos = Crew.getIgrokCar().gDir();
        pos.mult(-100.0);
        GP.M = Crew.getIgrokCar().gMatrix();
        GP.V = pos.oPlusN(Crew.getIgrokCar().gPosition());
        actorveh car = eng.CreateCar_onway("FREIGHTLINER_ARGOSY", GP.V);
        car.sVeclocity(25.0);
        Crew.addMappedCar("ARGOSY BANDIT", car);
        aiplayer pl = aiplayer.getSimpleAiplayer("SC_BANDIT3LOW");
        pl.beDriverOfCar(car);
        Chase ch_ase = new Chase();
        ch_ase.paramModerateChasing();
        ch_ase.makechase(car, Crew.getIgrokCar());
    }

    public static void testContestStalkers() {
        chaseTopo chase = scenarioscript.script.constructChaseTopo();
        chase.createRaceBanditsToBridge();
    }

    public static void createFriends() {
        matrixJ m = new matrixJ();
        vectorJ p = eng.getControlPointPosition("CP_LB_TS");
        vectorJ shift = new vectorJ(m.v0);
        shift.mult(5.0);
        actorveh kohcar = eng.CreateCarImmediatly("PETERBILT_378", m, p);
        actorveh dorcar = eng.CreateCarImmediatly("FREIGHTLINER_ARGOSY", m, p.oPlusN(shift));
        Crew.addMappedCar("DOROTHY", dorcar);
        Crew.addMappedCar("KOH", kohcar);
    }

    public static void justFriends() {
        actorveh car_dor = Crew.getMappedCar("DOROTHY");
        actorveh car_koh = Crew.getMappedCar("KOH");
        aiplayer.getScenarioAiplayer("SC_DOROTHY").abondoneCar(car_dor);
        aiplayer.getScenarioAiplayer("SC_KOH").abondoneCar(car_koh);
        Trajectory.createTrajectory("dorothytrajectory", "CP_ContBase_Start1", "CP_ContBase_Finish1");
        Trajectory.createTrajectory("kohtrajectory", "CP_ContBase_Start2", "CP_ContBase_Finish2");
        Vector<actorveh> players = new Vector<actorveh>();
        players.add(car_dor);
        actorveh.aligncars_inTrajectoryStart(players, "dorothytrajectory", 0.0, 0.0, 2, 1);
        players = new Vector();
        players.add(car_koh);
        actorveh.aligncars_inTrajectoryStart(players, "kohtrajectory", 0.0, 0.0, 2, 1);
        vectorJ shift1 = car_dor.gDir();
        shift1.mult(-10.0);
        vectorJ shift2 = car_koh.gDir();
        shift2.mult(-10.0);
        semitrailer trailerDor = semitrailer.create("model_Flat_bed_cargo3", car_dor.gMatrix(), car_dor.gPosition().oPlusN(shift1));
        semitrailer trailerKoh = semitrailer.create("model_Flat_bed_cargo3", car_koh.gMatrix(), car_koh.gPosition().oPlusN(shift2));
        car_dor.attach(trailerDor);
        car_koh.attach(trailerKoh);
    }

    public static void friendsRide() {
        actorveh car1 = Crew.getMappedCar("DOROTHY");
        actorveh car2 = Crew.getMappedCar("KOH");
        car1.sVeclocity(30.0);
        car2.sVeclocity(30.0);
        Vector<actorveh> players = new Vector<actorveh>();
        players.add(car1);
        actorveh.autopilotOnTrajectory(players, "dorothytrajectory");
        players = new Vector();
        players.add(car2);
        actorveh.autopilotOnTrajectory(players, "kohtrajectory");
        eng.setdword("DWORD_TopoQuest_Bridge", 1);
    }

    public static void friendsFinish() {
        actorveh car1 = Crew.getMappedCar("DOROTHY");
        actorveh car2 = Crew.getMappedCar("KOH");
        eng.setdword("DWORD_TopoQuest_Bridge", 1);
        eng.setdword("DWORD_TopoQuest_BridgeCol", 0);
        Trajectory.createTrajectory("nearbridge1", "CP_UnderBridge_Start1", "CP_UnderBridge_Finish1");
        Trajectory.createTrajectory("nearbridge2", "CP_UnderBridge_Start2", "CP_UnderBridge_Finish2");
        car1.stop_autopilot();
        car2.stop_autopilot();
        Vector<actorveh> players = new Vector<actorveh>();
        players.add(car1);
        actorveh.aligncars_inTrajectoryStart(players, "nearbridge1", 0.0, 0.0, 2, 1);
        players = new Vector();
        players.add(car2);
        actorveh.aligncars_inTrajectoryStart(players, "nearbridge2", 0.0, 0.0, 2, 1);
    }

    public static void darkTruck() {
        new test().createtrailer();
        MissionSystemInitializer.getMissionsManager().activateAsideMission("sc00610");
        MissionDialogs.sayAppear("sc00610_start_channel");
        eng.console("pickup sc00610");
        scenarioscript.script.getScenarioMachine().activateState("chasetopo", "chasetopo_phase_1");
        scenarioscript.script.getScenarioMachine().activateState("chasetopo", "chasetopo_phase_15");
        ScenarioSave.getInstance().CHASETOPO = scenarioscript.script.constructChaseTopo();
        ScenarioSave.getInstance().CHASETOPO.exitAnimation_punktB();
    }
}

