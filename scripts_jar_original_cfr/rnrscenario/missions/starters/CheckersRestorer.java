/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.starters;

import java.util.ArrayList;
import rnrorg.MissionEventsMaker;
import rnrscenario.missions.starters.ConditionChecker;

public final class CheckersRestorer {
    private static final ArrayList<ConditionChecker> checkersToRestore = new ArrayList();

    public static void sendCheckerToNativeOnAfterLoad(ConditionChecker conditionChecker) {
        checkersToRestore.add(conditionChecker);
    }

    public static void nativeReadyToAcceptConditionCheckers() {
        for (ConditionChecker conditionChecker : checkersToRestore) {
            MissionEventsMaker.makeMissionFinishChecker(conditionChecker);
        }
        checkersToRestore.clear();
    }
}

