/*
 * Decompiled with CFR 0.151.
 */
package rnrorg;

import rnrorg.IStoreorgelement;
import rnrorg.WarehouseOrder;
import rnrscr.Helper;

public class UrgetAgent {
    public static IStoreorgelement.Status changeStatus(IStoreorgelement element, int secondsRest, String mission_name) {
        if (Helper.getCurrentPosition() == null) {
            return element.getStatus();
        }
        switch (element.getStatus()) {
            case pendingMission: {
                double secondToRide = element instanceof WarehouseOrder ? rnrcore.Helper.getTimeToReachFinishPointIsSecondsWarehouseOrder() : rnrcore.Helper.getTimeToReachFinishPointIsSeconds(mission_name);
                return (double)secondsRest < secondToRide * 0.7 ? IStoreorgelement.Status.urgentMission : IStoreorgelement.Status.pendingMission;
            }
            case urgentMission: {
                double secondToRide = element instanceof WarehouseOrder ? rnrcore.Helper.getTimeToReachFinishPointIsSecondsWarehouseOrder() : rnrcore.Helper.getTimeToReachFinishPointIsSeconds(mission_name);
                return (double)secondsRest > secondToRide * 0.8 ? IStoreorgelement.Status.pendingMission : IStoreorgelement.Status.urgentMission;
            }
        }
        return element.getStatus();
    }
}

