/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import java.util.LinkedList;
import java.util.List;
import rnrorg.MissionEventsMaker;
import rnrscenario.missions.MissionList;
import rnrscenario.missions.infochannels.CarDialogChannel;
import rnrscenario.missions.infochannels.InformationChannel;
import scriptEvents.EventListener;
import scriptEvents.EventsController;
import scriptEvents.ScriptEvent;
import scriptEvents.SuccessEventChannel;
import scriptEvents.SuccessEventPicture;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class MissionEndUIController
implements EventListener {
    public String missionName = null;
    public List<ResourceInfo> resources = new LinkedList<ResourceInfo>();
    public static int maxMission = 0;
    public static int curMission = 0;
    private int numMission = 0;
    private static boolean numcheck = false;

    public static void setMaxMission(int i) {
        maxMission = 0;
        curMission = 0;
        numcheck = true;
    }

    public void realEnd() {
        EventsController.getInstance().removeListener(this);
        MissionEventsMaker.missionOnMoney(this.missionName);
        MissionList.dialogsFinished();
    }

    public static void nextMissionToEvent() {
        if (numcheck && ++curMission == maxMission) {
            numcheck = false;
        }
    }

    @Override
    public void eventHappened(List<ScriptEvent> eventTuple) {
        if (numcheck && this.numMission != curMission) {
            return;
        }
        for (ScriptEvent event2 : eventTuple) {
            if (event2 instanceof SuccessEventPicture) {
                this.realEnd();
                return;
            }
            if (!(event2 instanceof SuccessEventChannel)) continue;
            this.endDialog();
            return;
        }
    }

    public void placeRecourcesThroghChannel(InformationChannel channel, String missionName, String resourceName) {
        this.missionName = missionName;
        if (null != channel) {
            this.resources.add(new ResourceInfo(channel, missionName, resourceName));
        }
    }

    public boolean postInfo() {
        boolean r = false;
        for (ResourceInfo resourceInfo : this.resources) {
            boolean miss_passanger = resourceInfo.channel instanceof CarDialogChannel;
            boolean passanger_present = MissionEventsMaker.isPassanerSlotBuzzy();
            if (passanger_present) {
                passanger_present = MissionEventsMaker.samePassanger(this.missionName);
            }
            if (!resourceInfo.channel.isFinish() || !resourceInfo.channel.isMain() || miss_passanger && !passanger_present) continue;
            resourceInfo.place();
            r = true;
        }
        return r;
    }

    public boolean reg() {
        if (this.missionName == null) {
            return false;
        }
        EventsController.getInstance().addListener(this);
        this.numMission = maxMission++;
        if (numcheck) {
            // empty if block
        }
        return true;
    }

    public void endDialog() {
    }

    public final class ResourceInfo {
        private InformationChannel channel = null;
        private String missionName = null;
        private String resourceName = null;
        private boolean succes = true;

        public ResourceInfo(InformationChannel channel, String missionName, String resourceName) {
            this.channel = channel;
            this.missionName = missionName;
            this.resourceName = resourceName;
        }

        void place() {
            this.channel.postInfo(this.missionName, this.resourceName, true);
        }
    }
}

