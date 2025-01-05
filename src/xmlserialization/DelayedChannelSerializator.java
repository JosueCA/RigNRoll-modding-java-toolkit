/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import org.w3c.dom.Node;
import rnrcore.XmlSerializable;
import rnrloggers.MissionsLogger;
import rnrscenario.missions.DelayedResourceDisposer;
import rnrscenario.missions.Disposable;
import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.MissionPhase;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.infochannels.InformationChannel;
import rnrscenario.missions.infochannels.InformationChannelData;
import rnrscenario.missions.infochannels.NoSuchChannelException;
import rnrscenario.missions.map.PointsController;
import scenarioUtils.Pair;
import xmlserialization.Helper;
import xmlutils.NodeList;

public class DelayedChannelSerializator
implements XmlSerializable {
    private static DelayedChannelSerializator instance = new DelayedChannelSerializator();

    public static DelayedChannelSerializator getInstance() {
        return instance;
    }

    public String getRootNodeName() {
        return DelayedChannelSerializator.getNodeName();
    }

    public void loadFromNode(Node node) {
        DelayedChannelSerializator.deserializeXML(new xmlutils.Node(node));
    }

    public void yourNodeWasNotFound() {
    }

    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        DelayedChannelSerializator.serializeXML(stream);
    }

    public static String getNodeName() {
        return "delayed_channel";
    }

    public static void serializeXML(PrintStream stream) {
        Helper.openNode(stream, DelayedChannelSerializator.getNodeName());
        List<DelayedResourceDisposer.DelayedDisposeChannelData> tasks = DelayedResourceDisposer.getInstance().getDelayedDisposeChannelsData();
        LinkedList<String> mission_printed = new LinkedList<String>();
        for (DelayedResourceDisposer.DelayedDisposeChannelData task : tasks) {
            String mission = task.missionName;
            if (mission_printed.contains(mission)) continue;
            int time = task.secondsRemained;
            List<Pair<String, String>> attributes = Helper.createSingleAttribute("name", mission);
            Helper.addAttribute("time", time, attributes);
            Helper.printClosedNodeWithAttributes(stream, "mission", attributes);
            mission_printed.add(mission);
        }
        Helper.closeNode(stream, DelayedChannelSerializator.getNodeName());
    }

    public static void deserializeXML(xmlutils.Node node) {
        NodeList elements = node.getNamedChildren("mission");
        if (null != elements && !elements.isEmpty()) {
            for (xmlutils.Node element : elements) {
                final String mission = element.getAttribute("name");
                String timestr = element.getAttribute("time");
                int time = Helper.ConvertToIntegerAndWarn(timestr, "time", "Remined time of delayed channel");
                MissionInfo mi = MissionSystemInitializer.getMissionsManager().getMissionInfo(mission);
                if (mi == null) continue;
                List<MissionPhase> waysToEnd = mi.getEndPhase();
                for (MissionPhase possibleEnd : waysToEnd) {
                    for (InformationChannelData channelDescription : possibleEnd.getInfoChannels()) {
                        try {
                            final InformationChannel channel = channelDescription.makeWare();
                            final ArrayList<String> pointsToClear = new ArrayList<String>(channelDescription.getPlacesNames());
                            if (!channel.isNoMainFinishSucces()) continue;
                            Disposable resourceCleaner = new Disposable(){

                                public void dispose() {
                                    channel.dispose();
                                    PointsController.getInstance().freePoints(pointsToClear, MissionSystemInitializer.getMissionsManager().getMissionPlacement(mission));
                                }
                            };
                            DelayedResourceDisposer.getInstance().addResourceToDispose(resourceCleaner, mission, channel.getUid(), time);
                            possibleEnd.getUIController().placeRecourcesThroghChannel(channel, mission, channelDescription.getResource());
                            possibleEnd.getUIController().postInfo();
                        }
                        catch (NoSuchChannelException e) {
                            MissionsLogger.getInstance().doLog("wrong channel name data: " + e.getMessage(), Level.SEVERE);
                        }
                    }
                }
            }
        }
    }
}

