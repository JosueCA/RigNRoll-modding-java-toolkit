/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import players.Crew;
import players.aiplayer;
import rnrcore.CoreTime;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.vectorJ;
import rnrorg.MissionEventsMaker;
import rnrscenario.missions.MissionEndUIController;
import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.MissionPlacement;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.infochannels.InformationChannelData;
import rnrscenario.missions.map.PointsController;
import rnrscenario.missions.requirements.MissionsLog;
import rnrscr.Dialog;
import rnrscr.DialogsSet;
import rnrscr.IMissionInformation;
import rnrscr.IPointActivated;
import rnrscr.MissionHelper;
import rnrscr.SODialogParams;
import scriptEvents.EventsControllerHelper;
import scriptEvents.SuccessEventChannel;

public class MissionDialogs {
    private static MissionDialogs m_instance = null;
    private ArrayList<IMissionInformation> m_mission_infos = new ArrayList();
    private ArrayList<IMissionInformation> m_cached_mission_infos = new ArrayList();
    private String m_rotatingMission = null;
    private HashMap<String, IPointActivated> m_activatePointListeners = new HashMap();

    private MissionDialogs() {
    }

    public static MissionDialogs getReference() {
        if (null == m_instance) {
            m_instance = new MissionDialogs();
        }
        return m_instance;
    }

    public static void deinit() {
        m_instance = null;
    }

    public static DialogsSet queueDialogsForSO(int tip, vectorJ position, CoreTime time) {
        DialogsSet res = new DialogsSet();
        for (IMissionInformation dialog : MissionDialogs.getReference().m_mission_infos) {
            if (!dialog.isDialog() || !dialog.isInfoPosted() || !dialog.isInformationActive() || !dialog.hasPoint() || !MissionHelper.isThatPlace(tip, position, dialog.getPointName()) || !dialog.isFinishInformaton() && !MissionEventsMaker.freeSlotForMission(dialog.getMissionName()) || !dialog.checkTimePeriods(time)) continue;
            res.addQuest(new SODialogParams(dialog.getIdentitie(), MissionEventsMaker.queueNPCResourseForDialog(dialog), dialog.getDialogName(), 1, dialog.wasPlayed(), dialog.isFinishInformaton(), dialog.getPointName(), dialog.getMissionName()));
        }
        return res;
    }

    public static IMissionInformation queueDialog(String channelUID) {
        for (IMissionInformation d : MissionDialogs.getReference().m_mission_infos) {
            if (d.getChannelId().compareToIgnoreCase(channelUID) != 0) continue;
            return d;
        }
        return null;
    }

    public static void AddDialog(IMissionInformation dialog) {
        MissionDialogs.getReference().m_mission_infos.add(dialog);
    }

    public static void addDialogLiveDialog(String resource_name, String identitie, IPointActivated listener) {
        MissionDialogs.addDialogCarDialog(resource_name, identitie, listener);
    }

    public static void addDialogCarDialog(String resource_name, String identitie, IPointActivated listener) {
        IMissionInformation dialog = MissionDialogs.getMissionInfo(resource_name);
        MissionDialogs.getReference().m_activatePointListeners.put(dialog.getChannelId(), listener);
        if (!MissionSystemInitializer.getMissionsManager().isLoading()) {
            MissionEventsMaker.RegisterActivationPoint(dialog.getMissionName(), dialog.getChannelId());
        }
    }

    public static void startDialogCarDialog(String resource, String identie) {
        IMissionInformation dialog = MissionDialogs.getMissionInfo(resource);
        aiplayer npc = MissionEventsMaker.queueNPCPlayer_FreeFromVoter(dialog, identie);
        dialog.freeVoter();
        Dialog.getDialog(dialog.getDialogName()).start_car_leave_car_on_end(npc, Crew.getIgrok().getModel(), dialog.getIdentitie());
    }

    public static void activateDialog(String dialogname) {
        for (IMissionInformation dialog : MissionDialogs.getReference().m_mission_infos) {
            if (dialog.getDialogName().compareTo(dialogname) != 0) continue;
            dialog.postInfo();
            break;
        }
    }

    public static void RemoveDialog(String dialogname) {
        IMissionInformation dialog;
        if (null != MissionDialogs.getReference().m_rotatingMission && dialogname.compareTo(MissionDialogs.getReference().m_rotatingMission) == 0) {
            return;
        }
        Iterator<IMissionInformation> iter = MissionDialogs.getReference().m_mission_infos.iterator();
        while (iter.hasNext()) {
            dialog = iter.next();
            if (dialog.getDialogName().compareTo(dialogname) != 0) continue;
            iter.remove();
            break;
        }
        iter = MissionDialogs.getReference().m_cached_mission_infos.iterator();
        while (iter.hasNext()) {
            dialog = iter.next();
            if (dialog.getDialogName().compareTo(dialogname) != 0) continue;
            iter.remove();
            break;
        }
    }

    public static void sayYes(String dialog_name) {
        MissionDialogs.getReference().m_rotatingMission = dialog_name;
        for (IMissionInformation d : MissionDialogs.getReference().m_mission_infos) {
            if (d.getDialogName().compareToIgnoreCase(dialog_name) != 0) continue;
            d.receiveAnswer();
            ArrayList<String> mission_names_to_inform = d.getDependantMissions();
            mission_names_to_inform.add(d.getMissionName());
            for (String mission_name : mission_names_to_inform) {
                MissionEventsMaker.channelSayAccept(d.getChannelId(), mission_name, d.getPointName(), d.isChannelImmediate());
                MissionsLog.getInstance().eventHappen(mission_name, MissionsLog.Event.MISSION_ACCEPTED);
            }
            MissionDialogs.getReference().m_rotatingMission = null;
            return;
        }
        for (IMissionInformation d : MissionDialogs.getReference().m_cached_mission_infos) {
            if (d.getDialogName().compareToIgnoreCase(dialog_name) != 0) continue;
            d.receiveAnswer();
            ArrayList<String> mission_names_to_inform = d.getDependantMissions();
            mission_names_to_inform.add(d.getMissionName());
            for (String mission_name : mission_names_to_inform) {
                MissionEventsMaker.channelSayAccept(d.getChannelId(), mission_name, d.getPointName(), d.isChannelImmediate());
                MissionsLog.getInstance().eventHappen(mission_name, MissionsLog.Event.MISSION_ACCEPTED);
            }
            MissionDialogs.getReference().m_cached_mission_infos.remove(d);
            MissionDialogs.getReference().m_rotatingMission = null;
            return;
        }
        MissionDialogs.getReference().m_rotatingMission = null;
        eng.err("MissionDialogs. sayYes. Has no such dialog " + dialog_name);
    }

    public static void sayNo(String dialog_name) {
        MissionDialogs.getReference().m_rotatingMission = dialog_name;
        for (IMissionInformation d : MissionDialogs.getReference().m_mission_infos) {
            if (d.getDialogName().compareToIgnoreCase(dialog_name) != 0) continue;
            d.receiveAnswer();
            ArrayList<String> mission_names_to_inform = d.getDependantMissions();
            mission_names_to_inform.add(d.getMissionName());
            for (String mission_name : mission_names_to_inform) {
                MissionEventsMaker.channelSayDeclein(d.getChannelId(), mission_name, d.getPointName(), d.isChannelImmediate());
                MissionInfo mi = MissionSystemInitializer.getMissionsManager().getMissionInfo(mission_name);
                if (mi != null) {
                    for (InformationChannelData channelDescription : mi.getAllChannels()) {
                        ArrayList<String> pointsToClear = new ArrayList<String>(channelDescription.getPlacesNames());
                        PointsController.getInstance().freePoints(pointsToClear, MissionSystemInitializer.getMissionsManager().getMissionPlacement(mission_name));
                    }
                    MissionPlacement mp = MissionSystemInitializer.getMissionsManager().getMissionPlacement(mission_name);
                    String s = mp.getInfo().getQuestItemPlacement();
                    if (null != s) {
                        ArrayList<String> pNames = new ArrayList<String>();
                        pNames.add(s);
                        PointsController.getInstance().freePoints(pNames, mp);
                    }
                }
                MissionsLog.getInstance().eventHappen(mission_name, MissionsLog.Event.PLAYER_DECLINED_MISSION);
            }
            MissionDialogs.getReference().m_rotatingMission = null;
            return;
        }
        for (IMissionInformation d : MissionDialogs.getReference().m_cached_mission_infos) {
            if (d.getDialogName().compareToIgnoreCase(dialog_name) != 0) continue;
            d.receiveAnswer();
            ArrayList<String> mission_names_to_inform = d.getDependantMissions();
            mission_names_to_inform.add(d.getMissionName());
            for (String mission_name : mission_names_to_inform) {
                MissionEventsMaker.channelSayDeclein(d.getChannelId(), mission_name, d.getPointName(), d.isChannelImmediate());
                MissionInfo mi = MissionSystemInitializer.getMissionsManager().getMissionInfo(mission_name);
                if (mi != null) {
                    for (InformationChannelData channelDescription : mi.getAllChannels()) {
                        ArrayList<String> pointsToClear = new ArrayList<String>(channelDescription.getPlacesNames());
                        PointsController.getInstance().freePoints(pointsToClear, MissionSystemInitializer.getMissionsManager().getMissionPlacement(mission_name));
                    }
                }
                MissionsLog.getInstance().eventHappen(mission_name, MissionsLog.Event.PLAYER_DECLINED_MISSION);
            }
            MissionDialogs.getReference().m_cached_mission_infos.remove(d);
            MissionDialogs.getReference().m_rotatingMission = null;
            return;
        }
        MissionDialogs.getReference().m_rotatingMission = null;
        eng.err("MissionDialogs. sayNo. Has no such dialog " + dialog_name);
    }

    public static void sayAppear(String dialog_name) {
        MissionDialogs.getReference().m_rotatingMission = dialog_name;
        for (IMissionInformation d : MissionDialogs.getReference().m_mission_infos) {
            if (d.wasPlayed() || d.getDialogName().compareToIgnoreCase(dialog_name) != 0) continue;
            d.playMissionInfo();
            if (!d.isFinishInformaton()) {
                MissionEventsMaker.channelSayAppear(d.getChannelId(), d.getMissionName(), d.getPointName(), d.isChannelImmediate());
            }
            MissionsLog.getInstance().eventHappen(d.getMissionName(), MissionsLog.Event.PLAYER_INFORMED_ABOUT_MISSION);
            ArrayList<String> mission_names_to_inform = d.getDependantMissions();
            for (String mission_name : mission_names_to_inform) {
                if (d.isFinishInformaton()) {
                    MissionEventsMaker.channelSayAppear(d.getChannelId(), mission_name, d.getPointName(), d.isChannelImmediate());
                }
                MissionsLog.getInstance().eventHappen(mission_name, MissionsLog.Event.PLAYER_INFORMED_ABOUT_MISSION);
            }
            MissionDialogs.getReference().m_rotatingMission = null;
            return;
        }
        MissionDialogs.getReference().m_rotatingMission = null;
        eng.err("MissionDialogs. sayAppear. Has no such dialog " + dialog_name);
    }

    public static void sayEnd(String dialog_name) {
        if (MissionDialogs.hasFinish(dialog_name)) {
            EventsControllerHelper.eventHappened(new SuccessEventChannel());
            MissionEndUIController.nextMissionToEvent();
        } else {
            event.SetScriptevent(9850L);
        }
        for (IMissionInformation d : MissionDialogs.getReference().m_mission_infos) {
            if (d.getDialogName().compareToIgnoreCase(dialog_name) != 0) continue;
            ArrayList<String> mission_names_to_inform = d.getDependantMissions();
            mission_names_to_inform.add(d.getMissionName());
            for (String mission_name : mission_names_to_inform) {
                MissionEventsMaker.channelSayEnd(d.getChannelId(), mission_name, d.getPointName(), d.isChannelImmediate());
            }
            if (d.wasVoterFreed()) {
                MissionEventsMaker.npcPlayer_ResumeVoter(d);
            }
            MissionDialogs.getReference().m_cached_mission_infos.add(d);
            MissionDialogs.getReference().m_mission_infos.remove(d);
            return;
        }
        eng.err("MissionDialogs. sayEnd. Has no such dialog " + dialog_name);
    }

    public static boolean hasFinish(String dialog_name) {
        for (IMissionInformation d : MissionDialogs.getReference().m_mission_infos) {
            if (d.getDialogName().compareToIgnoreCase(dialog_name) != 0 || !d.isFinishInformaton()) continue;
            return true;
        }
        return false;
    }

    public static IMissionInformation getMissionInfo(String dialog_name) {
        for (IMissionInformation d : MissionDialogs.getReference().m_mission_infos) {
            if (d.getDialogName().compareToIgnoreCase(dialog_name) != 0) continue;
            return d;
        }
        eng.err("MissionDialogs. getMissionInfo. Has no such dialog " + dialog_name);
        return null;
    }

    public static IMissionInformation getMissionInfoAndInCachedToo(String dialog_name) {
        IMissionInformation missionInfo = MissionDialogs.getMissionInfo(dialog_name);
        if (missionInfo != null) {
            return missionInfo;
        }
        for (IMissionInformation d : MissionDialogs.getReference().m_cached_mission_infos) {
            if (d.getDialogName().compareToIgnoreCase(dialog_name) != 0) continue;
            return d;
        }
        eng.err("MissionDialogs. getMissionInfo. Has no such dialog " + dialog_name);
        return null;
    }

    public static void pointActivated(String resourceid) {
        if (!MissionDialogs.getReference().m_activatePointListeners.containsKey(resourceid)) {
            return;
        }
        MissionDialogs.getReference().m_activatePointListeners.get(resourceid).pointActivated();
        MissionDialogs.getReference().m_activatePointListeners.remove(resourceid);
    }

    public static void moveChannelOnPoint(String channel_uid, String point_name) {
        for (IMissionInformation d : MissionDialogs.getReference().m_mission_infos) {
            if (d.getChannelId().compareToIgnoreCase(channel_uid) != 0) continue;
            d.setPointName(point_name);
        }
    }

    public static void freeChannelFromPoint(String channel_uid) {
        for (IMissionInformation d : MissionDialogs.getReference().m_mission_infos) {
            if (d.getChannelId().compareToIgnoreCase(channel_uid) != 0) continue;
            d.freeFromPoint();
        }
    }

    public static class dayTimePeriod {
        int hour_from;
        int hour_to;

        boolean isInPeriod(CoreTime time) {
            return time.gHour() >= this.hour_from && time.gHour() >= this.hour_to;
        }
    }

    public static class TimePeriod {
        CoreTime from;
        CoreTime to;

        boolean isInPeriod(CoreTime time) {
            return time.moreThan(this.from) >= 0 && time.moreThan(this.to) < 0;
        }
    }
}

