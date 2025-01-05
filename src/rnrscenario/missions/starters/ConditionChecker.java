/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.starters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import rnrscenario.missions.starters.CheckersRestorer;
import scriptEvents.EventsControllerHelper;
import xmlserialization.nxs.AnnotatedSerializable;
import xmlserialization.nxs.SaveTo;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class ConditionChecker
implements AnnotatedSerializable {
    private static List<ConditionChecker> allCheckers = Collections.synchronizedList(new ArrayList());
    private String conditionId;
    @SaveTo(destinationNodeName="mission_name", constructorArgumentNumber=0)
    String missionName;

    public static void clearAllCheckers() {
        for (ConditionChecker checker : allCheckers) {
            checker.unsubscribeSelfFromMessages();
        }
        allCheckers.clear();
    }

    public static List<ConditionChecker> getAllCheckers() {
        return allCheckers;
    }

    public ConditionChecker(String missionName, String conditionId) {
        assert (null != missionName);
        assert (null != conditionId);
        this.missionName = missionName;
        this.conditionId = conditionId;
        allCheckers.add(this);
        EventsControllerHelper.getInstance().addMessageListener(this, "missionFinished", missionName + " failed");
    }

    public void missionFinished() {
        this.unsubscribeSelfFromMessages();
        allCheckers.remove(this);
    }

    private void unsubscribeSelfFromMessages() {
        EventsControllerHelper.getInstance().removeMessageListener(this, "missionFinished", this.missionName + " failed");
    }

    public final String getMissionName() {
        return this.missionName;
    }

    final boolean checkCondition() {
        if (this.check()) {
            this.missionFinished();
            return true;
        }
        return false;
    }

    // @Override
    public final void finalizeDeserialization() {
        CheckersRestorer.sendCheckerToNativeOnAfterLoad(this);
    }

    // @Override
    public final String getId() {
        return this.conditionId;
    }

    public abstract boolean check();
}

