/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.ArrayList;
import rnrcore.CoreTime;
import rnrscr.IChannelPointChanges;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface IMissionInformation {
    public boolean checkTimePeriods(CoreTime var1);

    public String getMissionName();

    public void addChannelPlaceChangedListener(IChannelPointChanges var1);

    public void freeFromPoint();

    public boolean hasPoint();

    public String getPointName();

    public void setPointName(String var1);

    public ArrayList<String> getDependantMissions();

    public String getDialogName();

    public String getChannelId();

    public boolean isDialog();

    public boolean wasVoterFreed();

    public void freeVoter();

    public void playMissionInfo();

    public boolean wasPlayed();

    public boolean isFinishInformaton();

    public String getIdentitie();

    public void makeInformationNotActive();

    public boolean isInformationActive();

    public void postInfo();

    public boolean isInfoPosted();

    public void receiveAnswer();

    public boolean hasQuestion();

    public void setChannelImmediate(boolean var1);

    public boolean isChannelImmediate();
}

