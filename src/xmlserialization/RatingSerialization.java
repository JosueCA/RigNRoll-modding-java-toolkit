/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.w3c.dom.Node;
import rnrcore.XmlSerializable;
import rnrrating.LivePlayerRatingSystem;
import rnrrating.NPCRatingSystem;
import rnrrating.PlayerRatingStats;
import rnrrating.RateSystem;
import rnrrating.RatedItem;
import rnrrating.Rater;
import scenarioUtils.Pair;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlutils.NodeList;

public class RatingSerialization
implements XmlSerializable {
    private static RatingSerialization instance = null;

    public static RatingSerialization getInstance() {
        if (null == instance) {
            instance = new RatingSerialization();
        }
        return instance;
    }

    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        RatingSerialization.serializeXML(stream);
    }

    public void loadFromNode(Node node) {
        RatingSerialization.deserializeXML(new xmlutils.Node(node));
    }

    public void yourNodeWasNotFound() {
    }

    public String getRootNodeName() {
        return RatingSerialization.getNodeName();
    }

    public static String getNodeName() {
        return "rating_system";
    }

    public static void serializeXML(PrintStream stream) {
        Helper.openNode(stream, RatingSerialization.getNodeName());
        RateSystem value = RateSystem.gSystem();
        LivePlayerRatingSystem liveRatingSystem = value.getLive();
        Helper.openNode(stream, "live_rating_system");
        RatingSerialization.serializeXML(liveRatingSystem, stream);
        Helper.closeNode(stream, "live_rating_system");
        NPCRatingSystem npcRatingSystem = value.getNpc();
        HashMap<String, PlayerRatingStats> npcRatings = npcRatingSystem.getRating();
        Set<Map.Entry<String, PlayerRatingStats>> npcRatingsEntries = npcRatings.entrySet();
        for (Map.Entry<String, PlayerRatingStats> entry : npcRatingsEntries) {
            Helper.printOpenNodeWithAttributes(stream, "npc_rating_system", Helper.createSingleAttribute("name", entry.getKey()));
            RatingSerialization.serializeXML(entry.getValue(), stream);
            Helper.closeNode(stream, "npc_rating_system");
        }
        Helper.closeNode(stream, RatingSerialization.getNodeName());
    }

    public static void serializeXML(PlayerRatingStats value, PrintStream stream) {
        RatedItem ratedItem = value.getRating();
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("id", ratedItem.getId());
        Helper.addAttribute("rating", ratedItem.getRating(), attributes);
        Helper.printOpenNodeWithAttributes(stream, "player_rating", attributes);
        Rater rater = value.getCurrent();
        if (rater != null) {
            Helper.openNode(stream, "current_rater");
            RatingSerialization.serializeXML(rater, stream);
            Helper.closeNode(stream, "current_rater");
        }
        HashMap<String, Rater> currentMissions = value.getCurrent_missions();
        Set<Map.Entry<String, Rater>> entrySetMissions = currentMissions.entrySet();
        for (Map.Entry<String, Rater> entry : entrySetMissions) {
            Helper.printOpenNodeWithAttributes(stream, "current_rater_mission", Helper.createSingleAttribute("name", entry.getKey()));
            RatingSerialization.serializeXML(entry.getValue(), stream);
            Helper.closeNode(stream, "current_rater_mission");
        }
        Helper.closeNode(stream, "player_rating");
    }

    public static void serializeXML(Rater value, PrintStream stream) {
        int[] ratingValues;
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("checkpoint", value.getCheckPointRating());
        Helper.addAttribute("money", value.getMoney(), attributes);
        Helper.printOpenNodeWithAttributes(stream, "rater", attributes);
        for (int i : ratingValues = value.getRatingRates()) {
            Helper.printClosedNodeWithAttributes(stream, "rating_value", Helper.createSingleAttribute("value", i));
        }
        Helper.closeNode(stream, "rater");
    }

    public static void deserializeXML(xmlutils.Node node) {
        NodeList npcRatingNodes;
        xmlutils.Node playerRatingStatsNode;
        RateSystem value = RateSystem.gSystem();
        xmlutils.Node liveRatingNode = node.getNamedChild("live_rating_system");
        if (liveRatingNode != null && (playerRatingStatsNode = liveRatingNode.getNamedChild("player_rating")) != null) {
            PlayerRatingStats playerStats = RatingSerialization.deserializePlayerRatingStatsXML(playerRatingStatsNode);
            value.setLive(new LivePlayerRatingSystem(playerStats));
        }
        if ((npcRatingNodes = node.getNamedChildren("npc_rating_system")) != null) {
            HashMap<String, PlayerRatingStats> npsStatsCollection = new HashMap<String, PlayerRatingStats>();
            for (xmlutils.Node npcNode : npcRatingNodes) {
                xmlutils.Node playerRatingStatsNode2;
                String name = npcNode.getAttribute("name");
                if (name == null) {
                    Log.error("Node npc_rating_system has no attribute name");
                }
                if ((playerRatingStatsNode2 = npcNode.getNamedChild("player_rating")) == null) {
                    Log.error("Node npc_rating_system has no node  player_rating");
                }
                PlayerRatingStats playerStats = RatingSerialization.deserializePlayerRatingStatsXML(playerRatingStatsNode2);
                npsStatsCollection.put(name, playerStats);
            }
            NPCRatingSystem npcRatingSystem = new NPCRatingSystem();
            npcRatingSystem.setRating(npsStatsCollection);
            value.setNpc(npcRatingSystem);
        }
    }

    public static PlayerRatingStats deserializePlayerRatingStatsXML(xmlutils.Node node) {
        NodeList missionRatersNodes;
        xmlutils.Node raterNode;
        String idString = node.getAttribute("id");
        String ratingValueString = node.getAttribute("rating");
        double ratingValue = Helper.ConvertToDoubleAndWarn(ratingValueString, "rating", "Node player_rating has non integer attribute rating");
        PlayerRatingStats result = new PlayerRatingStats(idString);
        RatedItem ratedItem = new RatedItem(idString);
        ratedItem.setRating(ratingValue);
        result.setRating(ratedItem);
        xmlutils.Node currentRater = node.getNamedChild("current_rater");
        if (currentRater != null && (raterNode = currentRater.getNamedChild("rater")) != null) {
            Rater rater = RatingSerialization.deserializeRaterXML(raterNode);
            result.setCurrent(rater);
        }
        if ((missionRatersNodes = node.getNamedChildren("current_rater_mission")) != null) {
            HashMap<String, Rater> missionRatersCollection = new HashMap<String, Rater>();
            for (xmlutils.Node missionRaterNode : missionRatersNodes) {
                String missionName = missionRaterNode.getAttribute("name");
                if (missionName == null) {
                    Log.error("Node current_rater_mission has no attribute name");
                }
                xmlutils.Node missionRater = missionRaterNode.getNamedChild("rater");
                Rater rater = RatingSerialization.deserializeRaterXML(missionRater);
                missionRatersCollection.put(missionName, rater);
            }
            result.setCurrent_missions(missionRatersCollection);
        }
        return result;
    }

    public static Rater deserializeRaterXML(xmlutils.Node node) {
        Rater result = new Rater();
        String checkPointString = node.getAttribute("checkpoint");
        String moneyString = node.getAttribute("money");
        int checkPointValue = Helper.ConvertToIntegerAndWarn(checkPointString, "checkpoint", "ERRORR in Rater deserializeRaterXML");
        result.setCheckPointRating(checkPointValue);
        int moneyValue = Helper.ConvertToIntegerAndWarn(moneyString, "money", "ERRORR in Rater deserializeRaterXML");
        result.setMoney(moneyValue);
        NodeList ratingValuesNodes = node.getNamedChildren("rating_value");
        if (ratingValuesNodes != null) {
            int[] ratingValues = new int[ratingValuesNodes.size()];
            for (int i = 0; i < ratingValuesNodes.size(); ++i) {
                int value;
                xmlutils.Node singleRatingValue = (xmlutils.Node)ratingValuesNodes.get(i);
                String ratingValueString = singleRatingValue.getAttribute("value");
                ratingValues[i] = value = Helper.ConvertToIntegerAndWarn(ratingValueString, "value", "ERRORR in Rater deserializeRaterXML");
            }
            result.setRatingRates(ratingValues);
        }
        return result;
    }
}

