// Decompiled with: CFR 0.152
// Class Version: 5
package rnrscenario.missions;

import rnrscenario.missions.Dumper;
import rnrscenario.missions.MissionFactory;
import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.MissionList;
import rnrscenario.missions.MissionManager;
import rnrscenario.missions.MissionStartResourses;
import rnrscenario.missions.infochannels.ArticleChannel;
import rnrscenario.missions.infochannels.CarDialogChannel;
import rnrscenario.missions.infochannels.CbvChannel;
import rnrscenario.missions.infochannels.ExternalChannel;
import rnrscenario.missions.infochannels.InBarTalkChannel;
import rnrscenario.missions.infochannels.InfoChannelEventCallback;
import rnrscenario.missions.infochannels.InformationChannelsFactory;
import rnrscenario.missions.infochannels.LiveDialogChannel;
import rnrscenario.missions.infochannels.MissionsInfoPoster;
import rnrscenario.missions.infochannels.NewspaperChannel;
import rnrscenario.missions.infochannels.RadioChannel;
import rnrscenario.missions.map.MissionsMap;
import rnrscenario.missions.map.PointsController;
import rnrscenario.missions.requirements.MissionsLog;
import rnrscenario.missions.starters.ConditionChecker;
import rnrscenario.sctask;
import scriptEvents.EventsController;
import scriptEvents.MissionEndchecker;

public final class MissionSystemInitializer {
    private static MissionsMap map = null;
    private static MissionsInfoPoster channelsPoster = null;
    private static MissionManager missionsManager = null;
    private static MissionList activatedMissions = null;
    private static MissionFactory missionFactory = null;
    private static MissionStartResourses missionsStartResources = null;
    private static sctask missionDumper = null;

    public static void firstInitializeSystem() {
    }

    public static void initializeSystem() {
        map = new MissionsMap();
        InformationChannelsFactory channelsFactory = InformationChannelsFactory.getInstance();
        channelsFactory.addChannelPrototype("ExternalChannel", new ExternalChannel(), true, true, false, InfoChannelEventCallback.ChannelClose.DELAYED_ANSWERS);
        channelsFactory.addChannelPrototype("CbvChannel", new CbvChannel(map), true, false, true, InfoChannelEventCallback.ChannelClose.DIALOG);
        channelsFactory.addChannelPrototype("CarDialogChannel", new CarDialogChannel(), true, false, true, InfoChannelEventCallback.ChannelClose.DIALOG);
        channelsFactory.addChannelPrototype("LiveDialogChannel", new LiveDialogChannel(), true, false, true, InfoChannelEventCallback.ChannelClose.DIALOG);
        channelsFactory.addChannelPrototype("RadioChannel", new RadioChannel(map), false, false, false, InfoChannelEventCallback.ChannelClose.DELAYED_ANSWERS);
        channelsFactory.addChannelPrototype("NewspaperChannel", new NewspaperChannel(), false, true, false, InfoChannelEventCallback.ChannelClose.DELAYED_ANSWERS);
        channelsFactory.addChannelPrototype("ArticleChannel", new ArticleChannel(), false, true, false, InfoChannelEventCallback.ChannelClose.DELAYED_ANSWERS);
        channelsFactory.addChannelPrototype("InBarTalkChannel", new InBarTalkChannel(), true, true, true, InfoChannelEventCallback.ChannelClose.DIALOG);
        MissionInfo.init();
        missionsStartResources = new MissionStartResourses();
        channelsPoster = new MissionsInfoPoster();
        missionFactory = new MissionFactory();
        activatedMissions = new MissionList(missionFactory);
        EventsController.getInstance().addListener(activatedMissions);
        EventsController.getInstance().addListener(channelsPoster);
        missionsManager = new MissionManager(map);
        missionsManager.addMissionController(missionsStartResources);
        missionsManager.addMissionController(channelsPoster);
        missionsManager.addMissionController(missionFactory);
        Dumper dumper = new Dumper();
        missionDumper = dumper;
        dumper.addTask(PointsController.getInstance());
        dumper.addTask(MissionsLog.getInstance());
        dumper.on();
    }

    public static void deinitializeSystem() {
        InformationChannelsFactory.deinit();
        MissionEndchecker.deinit();
        MissionInfo.deinit();
        ConditionChecker.clearAllCheckers();
        map.deinit();
        map = null;
        if (null != missionsManager) {
            missionsManager.finishImmediately();
            missionsManager.deinitialize();
            missionsManager = null;
        }
        missionsStartResources = null;
        channelsPoster = null;
        missionFactory = null;
        activatedMissions = null;
        if (null != missionDumper) {
            missionDumper.finishImmediately();
        }
        missionDumper = null;
    }

    public static MissionList getActivatedMissions() {
        return activatedMissions;
    }

    public static MissionManager getMissionsManager() {
        return missionsManager;
    }

    public static MissionsMap getMissionsMap() {
        return map;
    }

    public static MissionsInfoPoster getChannelsPoster() {
        return channelsPoster;
    }
}
