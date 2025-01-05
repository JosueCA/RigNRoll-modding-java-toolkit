/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.infochannels;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import rnrorg.MissionEventsMaker;
import rnrorg.journal;
import rnrscenario.missions.Disposable;
import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.MissionManager;
import rnrscenario.missions.infochannels.DelayedInfoInformation;
import rnrscenario.missions.infochannels.DelayedRealInfoPoster;
import rnrscenario.missions.infochannels.IDealyedRealInfo;
import rnrscr.IMissionInformation;
import rnrscr.MissionDialogs;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class InformationChannel
implements Cloneable,
Disposable,
IDealyedRealInfo {
    private static final int DESIRED_POINTS_CAPACITY = 4;
    private String uid = null;
    private Set<String> avaliblePointsToAppear = new HashSet<String>(4);
    protected boolean isFinishChannel = false;
    protected boolean isMain = false;
    protected String identitie = null;
    protected String resourceHold = null;
    private boolean isImmediateChannel = false;
    protected IMissionInformation cachedInfo = null;
    protected DelayedInfoInformation delayedInfo = null;

    protected void renewChachedObject(String resource) {
        this.resourceHold = resource;
        if (null == this.cachedInfo) {
            this.cachedInfo = MissionDialogs.getMissionInfo(this.resourceHold);
        }
        if (this.cachedInfo != null) {
            this.cachedInfo.setChannelImmediate(this.isImmediateChannel);
        }
    }

    public IMissionInformation getMInfo() {
        return this.cachedInfo;
    }

    public boolean isMain() {
        return this.isMain;
    }

    public boolean isMainFinishSucces() {
        return this.isMain && this.isFinishChannel;
    }

    public boolean isNoMainFinishSucces() {
        return !this.isMain && this.isFinishChannel;
    }

    public boolean isFinish() {
        return this.isFinishChannel;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setIdentitie(String identitie) {
        this.identitie = identitie;
    }

    public void setMainChannel(boolean value) {
        this.isMain = value;
    }

    public void addPlaces(List<String> points) {
        assert (null != points) : "points must be non-null reference";
        this.avaliblePointsToAppear.addAll(points);
    }

    Set<String> whereCanAppear() {
        return this.avaliblePointsToAppear;
    }

    @Override
    public void dispose() {
        MissionEventsMaker.channelCleanResources(this.uid);
        this.clearResources();
    }

    public final void makeNotActive() {
        if (null == this.cachedInfo) {
            return;
        }
        this.cachedInfo.makeInformationNotActive();
        journal.getInstance().updateActiveNotes();
    }

    public String getUid() {
        return this.uid;
    }

    public void setFinishChannel(boolean value) {
        this.isFinishChannel = value;
    }

    public final void makeStartChannelPost(MissionInfo info, String resource) {
        this.resourceHold = resource;
        this.renewChachedObject(this.resourceHold);
        this.postStartMissionInfo(info, this.resourceHold);
        MissionEventsMaker.channalIsActive(this.uid);
    }

    public void postInfo(String mission_name, String resource, boolean useMainInfo) {
        this.resourceHold = resource;
        this.renewChachedObject(resource);
        this.realInfoPost(mission_name, this.resourceHold, useMainInfo);
    }

    public final void realInfoPost(String mission_name, String resource, boolean useMainInfo) {
        if (!MissionManager.getMSEnable() && !this.isFinishChannel) {
            return;
        }
        this.delayedInfo = new DelayedInfoInformation(mission_name, resource, useMainInfo);
        DelayedRealInfoPoster.getInstance().addPoster(this);
        MissionEventsMaker.channalIsActive(this.uid);
    }

    public abstract void postStartMissionInfo(MissionInfo var1, String var2);

    abstract void clearResources();

    public abstract InformationChannel clone();

    public void setImmediateChannel(boolean isImmediateChannel) {
        this.isImmediateChannel = isImmediateChannel;
    }
}

