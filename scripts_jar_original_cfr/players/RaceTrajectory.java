/*
 * Decompiled with CFR 0.151.
 */
package players;

import java.util.HashSet;
import java.util.Set;
import rnrcore.eng;
import rnrcore.vectorJ;

public final class RaceTrajectory {
    public static final String NONAME_RACE = "no name";
    public static final String PP_RACE = "pp_race";
    private static final Set<String> createdTrajectories = new HashSet<String>();

    private RaceTrajectory() {
    }

    public static void createTrajectoryForRaceWithPP() {
        RaceTrajectory.create(PP_RACE, "Race_Start_SB", "Race_Finish_AT");
    }

    @Deprecated
    private static native void createTrajectory(String var0, String var1, String var2);

    @Deprecated
    private static native void removeTrajectory(String var0);

    @Deprecated
    private static native vectorJ gStart(String var0);

    @Deprecated
    private static native vectorJ gFinish(String var0);

    public static void create(String trajectoryName, String startZone, String finishZone) {
        if (!createdTrajectories.contains(trajectoryName)) {
            RaceTrajectory.createTrajectory(trajectoryName, startZone, finishZone);
            createdTrajectories.add(trajectoryName);
        }
    }

    public static void remove(String trajectoryName) {
        if (createdTrajectories.contains(trajectoryName)) {
            RaceTrajectory.removeTrajectory(trajectoryName);
            createdTrajectories.remove(trajectoryName);
        }
    }

    public static vectorJ getStart(String trajectoryName) {
        if (createdTrajectories.contains(trajectoryName)) {
            return RaceTrajectory.gStart(trajectoryName);
        }
        eng.err("race trajectory '" + trajectoryName + "' has not been created");
        return new vectorJ();
    }

    public static vectorJ getFinish(String trajectoryName) {
        if (createdTrajectories.contains(trajectoryName)) {
            return RaceTrajectory.gFinish(trajectoryName);
        }
        eng.err("race trajectory '" + trajectoryName + "' has not been created");
        return new vectorJ();
    }
}

