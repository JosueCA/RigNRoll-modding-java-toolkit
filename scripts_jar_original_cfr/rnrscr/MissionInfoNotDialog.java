/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.ArrayList;
import java.util.logging.Level;
import rnrcore.CoreTime;
import rnrloggers.MissionsLogger;
import rnrscr.IChannelPointChanges;
import rnrscr.IMissionInformation;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class MissionInfoNotDialog
implements IMissionInformation {
    private String mission;
    private String pointName;
    private String dialogname;
    private String channelId;
    private boolean is_active = true;
    private boolean was_freed = false;
    private boolean played;
    private boolean isFinishInfo;
    private boolean f_hasPoint = false;
    private boolean is_info_posted = false;
    private boolean f_waitAnswer = true;
    private ArrayList<IChannelPointChanges> changePointListeners = new ArrayList();
    private ArrayList<String> dependantMissions = null;
    private boolean isChannelImmediate = false;

    public MissionInfoNotDialog(String channelId, String mission, String resource, boolean played, boolean isFinishInfo, ArrayList<String> dependantMissions) {
        this.dialogname = resource;
        this.mission = mission;
        this.channelId = channelId;
        this.played = played;
        this.isFinishInfo = isFinishInfo;
        this.dependantMissions = dependantMissions;
    }

    @Override
    public void addChannelPlaceChangedListener(IChannelPointChanges listener) {
        this.changePointListeners.add(listener);
        if (this.f_hasPoint) {
            listener.setOnPoint(this.pointName);
        } else {
            listener.freeFromPoint();
        }
    }

    @Override
    public String getMissionName() {
        return this.mission;
    }

    @Override
    public String getDialogName() {
        return this.dialogname;
    }

    @Override
    public String getChannelId() {
        return this.channelId;
    }

    @Override
    public ArrayList<String> getDependantMissions() {
        return this.dependantMissions;
    }

    @Override
    public boolean checkTimePeriods(CoreTime time) {
        return true;
    }

    @Override
    public boolean isDialog() {
        return false;
    }

    @Override
    public boolean wasVoterFreed() {
        return this.was_freed;
    }

    @Override
    public void freeVoter() {
        this.was_freed = true;
    }

    @Override
    public void playMissionInfo() {
        this.played = true;
    }

    @Override
    public boolean wasPlayed() {
        return this.played;
    }

    @Override
    public boolean isFinishInformaton() {
        return this.isFinishInfo;
    }

    @Override
    public String getIdentitie() {
        return null;
    }

    @Override
    public boolean isInformationActive() {
        return this.is_active;
    }

    @Override
    public void makeInformationNotActive() {
        this.is_active = false;
    }

    @Override
    public void freeFromPoint() {
        this.f_hasPoint = false;
        for (IChannelPointChanges item : this.changePointListeners) {
            item.freeFromPoint();
        }
    }

    @Override
    public boolean hasPoint() {
        return this.f_hasPoint;
    }

    @Override
    public String getPointName() {
        return this.pointName;
    }

    @Override
    public void setPointName(String point_name) {
        this.f_hasPoint = true;
        this.pointName = point_name;
        if (point_name == null) {
            MissionsLogger.getInstance().doLog("MissionInfoNotDialog.setPointName", Level.INFO);
        }
        for (IChannelPointChanges item : this.changePointListeners) {
            item.setOnPoint(point_name);
        }
    }

    @Override
    public void postInfo() {
        this.is_info_posted = true;
    }

    @Override
    public boolean isInfoPosted() {
        return this.is_info_posted;
    }

    @Override
    public void receiveAnswer() {
        this.f_waitAnswer = false;
    }

    @Override
    public boolean hasQuestion() {
        return this.f_waitAnswer;
    }

    @Override
    public void setChannelImmediate(boolean value) {
        this.isChannelImmediate = value;
    }

    @Override
    public boolean isChannelImmediate() {
        return this.isChannelImmediate;
    }
}

