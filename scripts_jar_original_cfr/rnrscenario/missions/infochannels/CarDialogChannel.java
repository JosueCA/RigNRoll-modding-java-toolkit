/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.infochannels;

import menu.menues;
import rnrcore.TypicalAnm;
import rnrcore.eng;
import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.infochannels.InformationChannel;
import rnrscr.IMissionInformation;
import rnrscr.IPointActivated;
import rnrscr.MissionDialogs;

public class CarDialogChannel
extends InformationChannel {
    static final long serialVersionUID = 0L;
    private String resource_hold = null;

    public void postStartMissionInfo(MissionInfo info, String resource) {
        this.resource_hold = resource;
        IMissionInformation dialog = MissionDialogs.getMissionInfo(resource);
        CarDialogOnPointActivate listener = new CarDialogOnPointActivate(dialog, this.identitie);
        MissionDialogs.addDialogCarDialog(resource, this.identitie, listener);
    }

    public InformationChannel clone() {
        return new CarDialogChannel();
    }

    void clearResources() {
        if (null != this.resource_hold) {
            MissionDialogs.RemoveDialog(this.resource_hold);
        }
    }

    public void delayedRealInfoPost() {
        String mission_name = this.delayedInfo.mission_name;
        String resource = this.delayedInfo.resource;
        boolean useMainInfo = this.delayedInfo.useMainInfo;
        this.resource_hold = resource;
        if (!menues.cancreate_messagewindow()) {
            new WaitApproptiateMomentDialog(this.getUid(), mission_name, resource, this.identitie, useMainInfo);
            return;
        }
        IMissionInformation dialog = MissionDialogs.getMissionInfo(resource);
        CarDialogOnPointActivate listener = new CarDialogOnPointActivate(dialog, this.identitie);
        if (useMainInfo && this.isMain) {
            listener.pointActivated();
        } else {
            MissionDialogs.addDialogCarDialog(resource, this.identitie, listener);
        }
    }

    class WaitApproptiateMomentDialog
    extends TypicalAnm {
        public String uid;
        public String mission_name;
        private String resource;
        private String id;
        private boolean useMainInfo;

        WaitApproptiateMomentDialog(String uid, String mission_name, String resource, String id, boolean useMainInfo) {
            this.uid = uid;
            this.mission_name = mission_name;
            this.resource = resource;
            this.id = id;
            this.useMainInfo = useMainInfo;
            eng.CreateInfinitScriptAnimation(this);
        }

        public boolean animaterun(double dt) {
            if (!menues.cancreate_messagewindow()) {
                return false;
            }
            IMissionInformation dialog = MissionDialogs.getMissionInfo(this.resource);
            CarDialogOnPointActivate listener = new CarDialogOnPointActivate(dialog, CarDialogChannel.this.identitie);
            if (this.useMainInfo && CarDialogChannel.this.isMain) {
                listener.pointActivated();
            } else {
                MissionDialogs.startDialogCarDialog(this.resource, CarDialogChannel.this.identitie);
            }
            return true;
        }
    }

    static class CarDialogOnPointActivate
    implements IPointActivated {
        private IMissionInformation info;
        private String identitie;

        CarDialogOnPointActivate(IMissionInformation info, String identitie) {
            this.info = info;
            this.identitie = identitie;
        }

        public void pointActivated() {
            MissionDialogs.startDialogCarDialog(this.info.getDialogName(), this.identitie);
        }
    }
}

