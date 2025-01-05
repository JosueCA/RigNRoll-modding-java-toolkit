/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.infochannels;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import rnrloggers.MissionsLogger;
import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.MissionsController;
import rnrscenario.missions.NeedPostMissionInfoEvent;
import rnrscenario.missions.StartMissionListeners;
import rnrscenario.missions.infochannels.InfoChannelEventsListener;
import rnrscenario.missions.infochannels.InformationChannel;
import rnrscenario.missions.infochannels.InformationChannelData;
import rnrscenario.missions.infochannels.InformationChannelsFactory;
import rnrscenario.missions.infochannels.NoSuchChannelException;
import scriptActions.ScriptAction;
import scriptEvents.EventListener;
import scriptEvents.EventsController;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class MissionsInfoPoster
implements MissionsController,
EventListener {
    static final long serialVersionUID = 0L;
    private Map<String, PostedInfo> postedInfo = new LinkedHashMap<String, PostedInfo>();
    private Map<String, List<ChannelWithResource>> channelsOfDependentMissions = new HashMap<String, List<ChannelWithResource>>();

    // @Override
    public void eventHappened(List<ScriptEvent> eventTuple) {
        for (ScriptEvent event2 : eventTuple) {
            String missionName;
            if (!(event2 instanceof NeedPostMissionInfoEvent) || null == (missionName = ((NeedPostMissionInfoEvent)event2).getMissionName())) continue;
            List<ChannelWithResource> channels = this.channelsOfDependentMissions.remove(missionName);
            if (null != channels) {
                for (ChannelWithResource channelWithResource : channels) {
                    channelWithResource.post();
                }
                continue;
            }
            MissionsLogger.getInstance().doLog("MissionsInfoPoster: trying to post info of unexisting mission " + missionName, Level.SEVERE);
        }
    }

    // @Override
    public void uploadMission(final MissionInfo target) {
        PostMisssionOrNotDecision postInfoOrNot;
        assert (null != target) : "target must be non-null reference";
        StartMissionListeners.getInstance().registerChannelEventListener(target.getName(), target);
        StartMissionListeners.getInstance().registerStartMissionListener(target.getName(), target);
        List<InformationChannelData> channels = target.getInfoStartChannels();
        if (channels.isEmpty()) {
            MissionsLogger.getInstance().doLog("came mission info without avalible start channels", Level.SEVERE);
            return;
        }
        LinkedList<InformationChannel> resources = new LinkedList<InformationChannel>();
        LinkedList<String> pointsToFree = new LinkedList<String>();
        this.postedInfo.put(target.getName(), new PostedInfo(resources, pointsToFree, target.getName()));
        if (!target.hasStartDependence()) {
            postInfoOrNot = new PostMisssionOrNotDecision(){

                void makeDesision(InformationChannel channel, String resource) {
                    channel.makeStartChannelPost(target, resource);
                }
            };
        } else if (target.isDependByActivation()) {
            final LinkedList store = new LinkedList();
            this.channelsOfDependentMissions.put(target.getName(), store);
            postInfoOrNot = new PostMisssionOrNotDecision(){

                void makeDesision(InformationChannel channel, String resource) {
                    store.add(new ChannelWithResource(resource, channel, target));
                }
            };
        } else {
            if (!channels.isEmpty()) {
                MissionsLogger.getInstance().doLog("found dependent mission with start channels", Level.WARNING);
            }
            for (InformationChannelData channelData : channels) {
                pointsToFree.addAll(channelData.getPlacesNames());
            }
            this.postedInfo.put(target.getName(), new PostedInfo(Collections.<InformationChannel>emptyList(), pointsToFree, target.getName()));
            return;
        }
        for (InformationChannelData channelData : channels) {
            try {
                InformationChannel channelToPost = channelData.makeWare(target.getName(), target.getFinishPoint(), target.getQuestItemPlacement(), target);
                pointsToFree.addAll(channelData.getPlacesNames());
                InfoChannelEventsListener channelsListener = new InfoChannelEventsListener(channelToPost.getUid());
                LinkedList<ScriptAction> resourceDisposer = new LinkedList<ScriptAction>();
                resourceDisposer.add(new PostedResourceMakeNotActive(target.getName()));
                resourceDisposer.add(new PostedResourceDisposer(target.getName()));
                List<ScriptAction> emptyList = Collections.emptyList();
                channelsListener.useCallbacks(emptyList, emptyList, emptyList, resourceDisposer, InformationChannelsFactory.getInstance().getCloseChannelInfo(channelData.getChannelName()));
                EventsController.getInstance().addListener(channelsListener);
                resources.add(channelToPost);
                postInfoOrNot.makeDesision(channelToPost, channelData.getResource());
            }
            catch (NoSuchChannelException e) {
                String errorDescription = "Invalid channel name came: " + channelData.getChannelName();
                MissionsLogger.getInstance().doLog(errorDescription, Level.SEVERE);
            }
        }
    }

    // @Override
    public void unloadMission(String target) {
        if (null != target) {
            StartMissionListeners.getInstance().unregisterChannelEventListener(target);
            StartMissionListeners.getInstance().unregisterStartMissionListener(target);
            new PostedResourceDisposer(target).act();
        }
    }

    public InformationChannel getPostedInformationChannel(String missionName, String channelUid) {
        if (this.postedInfo == null) {
            return null;
        }
        if (!this.postedInfo.containsKey(missionName)) {
            return null;
        }
        PostedInfo channelsInfo = this.postedInfo.get(missionName);
        assert (channelsInfo != null);
        assert (channelsInfo.infoChannels != null);
        for (InformationChannel channel : channelsInfo.infoChannels) {
            if (channel.getUid().compareTo(channelUid) != 0) continue;
            return channel;
        }
        return null;
    }

    private abstract class PostMisssionOrNotDecision {
        private PostMisssionOrNotDecision() {
        }

        abstract void makeDesision(InformationChannel var1, String var2);
    }

    private class PostedResourceDisposer
    extends ScriptAction {
        static final long serialVersionUID = 0L;
        private String missionWhichResourcesNeedToBeDisposed = null;

        public PostedResourceDisposer(String missionWichResourcesNeedToBeDisposed) {
            assert (null != missionWichResourcesNeedToBeDisposed) : " must be non-null reference";
            this.missionWhichResourcesNeedToBeDisposed = missionWichResourcesNeedToBeDisposed;
        }

        public void act() {
            PostedInfo resources = (PostedInfo)MissionsInfoPoster.this.postedInfo.remove(this.missionWhichResourcesNeedToBeDisposed);
            if (null != resources) {
                resources.dispose();
            }
        }
    }

    private class PostedResourceMakeNotActive
    extends ScriptAction {
        static final long serialVersionUID = 0L;
        private String mission_name = null;

        public PostedResourceMakeNotActive(String mission_name) {
            this.mission_name = mission_name;
        }

        public void act() {
            PostedInfo resources = (PostedInfo)MissionsInfoPoster.this.postedInfo.get(this.mission_name);
            if (null != resources) {
                resources.makeNotActive();
            }
        }
    }

    private static final class ChannelWithResource {
        private String resource = null;
        private MissionInfo mission = null;
        private InformationChannel channel = null;

        ChannelWithResource(String resource, InformationChannel channel, MissionInfo mission) {
            this.resource = resource;
            this.channel = channel;
            this.mission = mission;
        }

        void post() {
            this.channel.makeStartChannelPost(this.mission, this.resource);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static final class PostedInfo {
        private List<InformationChannel> infoChannels = null;
        private Iterable<String> pointsInWorld = null;
        private String mission_name = null;

        PostedInfo(List<InformationChannel> infoChannels, Iterable<String> pointsInWorld, String mn) {
            assert (null != infoChannels) : "infoChannels must be non-null reference";
            assert (null != pointsInWorld) : "pointsInWorld must be non-null reference";
            this.infoChannels = infoChannels;
            this.pointsInWorld = pointsInWorld;
            this.mission_name = mn;
        }

        void dispose() {
            for (InformationChannel informationChannel : this.infoChannels) {
                informationChannel.dispose();
            }
        }

        void makeNotActive() {
            for (InformationChannel informationChannel : this.infoChannels) {
                informationChannel.makeNotActive();
            }
        }
    }
}

