/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.infochannels;

import java.util.logging.Level;
import players.Crew;
import players.aiplayer;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrloggers.MissionsLogger;
import rnrorg.MissionEventsMaker;
import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.infochannels.InformationChannel;
import rnrscenario.missions.map.Place;
import rnrscr.Dialog;
import rnrscr.IMissionInformation;
import rnrscr.IPointActivated;
import rnrscr.MissionDialogs;

public class LiveDialogChannel
extends InformationChannel {
    static final long serialVersionUID = 0L;
    private String resource_hold = null;
    static final float DIST_FOR_LIVEDIALOG = 10.0f;

    public void postStartMissionInfo(MissionInfo info, String resource) {
        this.resource_hold = resource;
        IMissionInformation dialog = MissionDialogs.getMissionInfo(resource);
        LiveDialogOnPointActivate listener = new LiveDialogOnPointActivate(dialog, this.identitie);
        MissionDialogs.addDialogLiveDialog(resource, this.identitie, listener);
    }

    public LiveDialogChannel clone() {
        return new LiveDialogChannel();
    }

    public final void dispose() {
        MissionEventsMaker.channelCleanResources(this.getUid());
        this.clearResources();
        if (null != this.cachedInfo) {
            MissionEventsMaker.channelSayEndEventToNative(this.cachedInfo.getMissionName(), this.getUid());
        }
    }

    void clearResources() {
        if (null != this.resource_hold) {
            MissionDialogs.RemoveDialog(this.resource_hold);
        }
    }

    public void delayedRealInfoPost() {
        String resource = this.delayedInfo.resource;
        boolean useMainInfo = this.delayedInfo.useMainInfo;
        this.resource_hold = resource;
        IMissionInformation dialog = MissionDialogs.getMissionInfo(resource);
        LiveDialogOnPointActivate listener = new LiveDialogOnPointActivate(dialog, this.identitie);
        if (useMainInfo && this.isMain) {
            listener.pointActivated();
        } else {
            MissionDialogs.addDialogLiveDialog(resource, this.identitie, listener);
        }
    }

    static class LiveDialogOnPointActivate
    implements IPointActivated {
        private IMissionInformation info;
        private String identitie;

        LiveDialogOnPointActivate(IMissionInformation info, String identitie) {
            this.info = info;
            this.identitie = identitie;
        }

        public void pointActivated() {
            aiplayer npc = MissionEventsMaker.queueNPCPlayer_FreeFromVoter(this.info, this.identitie);
            this.info.freeVoter();
            Dialog dlg = Dialog.getDialog(this.info.getDialogName());
            Place place = MissionSystemInitializer.getMissionsMap().getPlace(this.info.getPointName());
            if (place == null) {
                MissionsLogger.getInstance().doLog("LiveDialogChannel.pointActivated" + this.info.getPointName(), Level.INFO);
            } else {
                vectorJ carp = Crew.getIgrokCar().gPosition();
                vectorJ npcp = place.getCoords();
                double dist = Math.sqrt((carp.x - npcp.x) * (carp.x - npcp.x) + (carp.y - npcp.y) * (carp.y - npcp.y));
                if (dist < 10.0) {
                    dlg.start_car_leave_car_on_end(npc, Crew.getIgrok().getModel(), this.info.getIdentitie());
                } else {
                    dlg.start_simplePoint(Crew.getIgrok().getModel(), this.info.getIdentitie(), new matrixJ(), place.getCoords());
                }
            }
        }
    }
}

