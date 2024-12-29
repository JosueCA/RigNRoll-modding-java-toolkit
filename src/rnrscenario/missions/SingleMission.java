// Decompiled with: CFR 0.152
// Class Version: 5
package rnrscenario.missions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import rnrloggers.MissionsLogger;
import rnrorg.MissionEventsMaker;
import rnrscenario.missions.DelayedResourceDisposer;
import rnrscenario.missions.Disposable;
import rnrscenario.missions.MissionPhase;
import rnrscenario.missions.MissionPlacement;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.infochannels.InformationChannel;
import rnrscenario.missions.infochannels.InformationChannelData;
import rnrscenario.missions.infochannels.NoSuchChannelException;
import rnrscenario.missions.map.PointsController;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
final class SingleMission {
    private ArrayList<MissionPhase> waysToEnd = null;
    private String serializationUid = null;
    private String mission_name = null;

    public SingleMission(String mission_name, List<MissionPhase> endConditions) {
        this.waysToEnd = new ArrayList<MissionPhase>(endConditions);
        this.mission_name = mission_name;
    }

    void setSerializationUid(String missionName) {
        this.serializationUid = missionName;
    }

    String getSerializationUid() {
        return this.serializationUid;
    }

    public boolean checkEnd(List<ScriptEvent> eventTuple) {
        boolean missionFinished = false;
        Iterator<MissionPhase> possibleEndIterator = this.waysToEnd.iterator();
        while (possibleEndIterator.hasNext()) {
            MissionPhase possibleEnd = possibleEndIterator.next();
            if (!possibleEnd.getPhaseReaction().react(eventTuple)) continue;
            for (InformationChannelData channelDescription : possibleEnd.getInfoChannels()) {
                try {
                    final InformationChannel channel = channelDescription.makeWare();
                    final ArrayList<String> pointsToClear = new ArrayList<String>(channelDescription.getPlacesNames());
                    int TASK_DELAY = 3600;
                    if (channel.isNoMainFinishSucces()) {
                        Disposable resourceCleaner = new Disposable(){

                            public void dispose() {
                                channel.dispose();
                                PointsController.getInstance().freePoints(pointsToClear, MissionSystemInitializer.getMissionsManager().getMissionPlacement(SingleMission.this.mission_name));
                            }
                        };
                        DelayedResourceDisposer.getInstance().addResourceToDispose(resourceCleaner, this.mission_name, channel.getUid(), 3600);
                    }
                    possibleEnd.getUIController().placeRecourcesThroghChannel(channel, this.mission_name, channelDescription.getResource());
                }
                catch (NoSuchChannelException e) {
                    MissionsLogger.getInstance().doLog("wrong channel name data: " + e.getMessage(), Level.SEVERE);
                }
            }
            if (possibleEnd.getUIController().reg()) {
                if (!possibleEnd.getUIController().postInfo()) {
                    possibleEnd.getUIController().endDialog();
                }
            } else {
                MissionEventsMaker.missionOnMoney(this.mission_name);
            }
            possibleEndIterator.remove();
            missionFinished = true;
        }
        if (missionFinished) {
            for (MissionPhase possibleEnd : this.waysToEnd) {
                for (InformationChannelData channelData : possibleEnd.getInfoChannels()) {
                    if (channelData.is_finish_delay()) continue;
                    MissionEventsMaker.channelCleanResources(channelData.getUid());
                    PointsController.getInstance().freePoints(channelData.getPlacesNames(), MissionSystemInitializer.getMissionsManager().getMissionPlacement(this.mission_name));
                }
            }
            MissionPlacement mp = MissionSystemInitializer.getMissionsManager().getMissionPlacement(this.mission_name);
            String s = mp.getInfo().getQuestItemPlacement();
            if (null != s) {
                ArrayList<String> pNames = new ArrayList<String>();
                pNames.add(s);
                PointsController.getInstance().freePoints(pNames, mp);
            }
            return true;
        }
        return false;
    }

    public String getMission_name() {
        return this.mission_name;
    }
}
