/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import rnrorg.CustomerWarehouseAssociation;
import rnrorg.EmptyCustomer;
import rnrorg.IDeclineOrgListener;
import rnrorg.INPC;
import rnrorg.IStoreorgelement;
import rnrorg.MissionEventsMaker;
import rnrorg.MissionTime;
import rnrorg.QuestCargoParams;
import rnrorg.QuestCustomer;
import rnrorg.QuestNPC;
import rnrorg.RewardForfeit;
import rnrorg.Scorgelement;
import rnrorg.journable;
import scenarioUtils.Pair;
import xmlserialization.Helper;
import xmlserialization.JournalElementSerialization;
import xmlserialization.ListElementSerializator;
import xmlserialization.Log;
import xmlutils.Node;
import xmlutils.NodeList;

public class OrganizerNodeSerialization {
    public static String getNodeName() {
        return "organisernode";
    }

    public static void serializeXML(Scorgelement value, PrintStream stream) {
        ArrayList<IDeclineOrgListener> declineListeners;
        journable[] failNote;
        journable finishNote;
        journable startNote;
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("id", value.getName());
        Helper.addAttribute("type", value.getType().toString(), attributes);
        Helper.addAttribute("status", value.getStatus().toString(), attributes);
        Helper.addAttribute("hasfragility", value.hasFragility(), attributes);
        Helper.addAttribute("fragility", value.getFragility(), attributes);
        Helper.addAttribute("important", value.isImportant(), attributes);
        Helper.addAttribute("rewardflag", value.getRewardFlag(), attributes);
        Helper.addAttribute("forfeit_flag", value.getForfeitFlag(), attributes);
        Helper.addAttribute("description", value.getDescriptionOriginal(), attributes);
        Helper.addAttribute("coef_time_to_pickup", value.getCoefTimePickup(), attributes);
        Helper.addAttribute("coef_time_to_complete", value.getCoefTimeComplete(), attributes);
        Helper.addAttribute("startpoint", value.getStartPoint(), attributes);
        Helper.addAttribute("pickuppoint", value.getLoadPoint(), attributes);
        Helper.addAttribute("finishpoint", value.getCompletePoint(), attributes);
        Helper.printOpenNodeWithAttributes(stream, OrganizerNodeSerialization.getNodeName(), attributes);
        RewardForfeit reward = value.getReward();
        OrganizerNodeSerialization.serializeRewardForfeit(stream, "reward", reward);
        RewardForfeit forfeit = value.getForfeit();
        OrganizerNodeSerialization.serializeRewardForfeit(stream, "forfeit", forfeit);
        INPC customer = value.getCustomer();
        if (null != customer) {
            if (customer instanceof CustomerWarehouseAssociation) {
                Helper.printClosedNodeWithAttributes(stream, "customer_wh", null);
            } else if (customer instanceof EmptyCustomer) {
                Helper.printClosedNodeWithAttributes(stream, "customer_empty", null);
            } else if (customer instanceof QuestCustomer) {
                Helper.printClosedNodeWithAttributes(stream, "customer_quest", Helper.createSingleAttribute("customer_name", customer.getID()));
            } else if (customer instanceof QuestNPC) {
                Helper.printClosedNodeWithAttributes(stream, "customer_npc", Helper.createSingleAttribute("customer_name", customer.getID()));
            }
        }
        if (null != (startNote = value.getStartNote())) {
            Helper.openNode(stream, "startNote");
            JournalElementSerialization.serializeXML(startNote, stream);
            Helper.closeNode(stream, "startNote");
        }
        if (null != (finishNote = value.getFinishNote())) {
            Helper.openNode(stream, "finishNote");
            JournalElementSerialization.serializeXML(finishNote, stream);
            Helper.closeNode(stream, "finishNote");
        }
        if (null != (failNote = value.getFailNote())) {
            Helper.openNode(stream, "failNotes");
            for (journable element : failNote) {
                ListElementSerializator.serializeXMLListelementOpen(stream);
                if (null == element) {
                    Helper.printClosedNodeWithAttributes(stream, "failNotes_empty", null);
                } else {
                    JournalElementSerialization.serializeXML(element, stream);
                }
                ListElementSerializator.serializeXMLListelementClose(stream);
            }
            Helper.closeNode(stream, "failNotes");
        }
        if (null != (declineListeners = value.getListeners()) && !declineListeners.isEmpty()) {
            IDeclineOrgListener listener;
            if (declineListeners.size() > 1) {
                Log.error("OrganizerNodeSerialization in serializeXML has decline listeners more that 1");
            }
            if ((listener = declineListeners.get(0)) instanceof MissionEventsMaker.DeclineMissionListener) {
                MissionEventsMaker.DeclineMissionListener declineMissionListener = (MissionEventsMaker.DeclineMissionListener)listener;
                Helper.printClosedNodeWithAttributes(stream, "declinelistener", Helper.createSingleAttribute("missionName", declineMissionListener.getMission_name()));
            } else {
                Log.error("OrganizerNodeSerialization in serializeXML has decline listeners with unrecognized type " + listener.getClass().getName());
            }
        }
        Helper.closeNode(stream, OrganizerNodeSerialization.getNodeName());
    }

    public static Scorgelement deserializeXML(Node node) {
        Node journalNote;
        String nameCustomer;
        String errorMessage = "OrganizerNodeSerialization in deserializeXML ";
        String name = node.getAttribute("id");
        String typeString = node.getAttribute("type");
        String importantString = node.getAttribute("important");
        String rewardFlagString = node.getAttribute("rewardflag");
        String forfeitFlagString = node.getAttribute("forfeit_flag");
        String descriptionString = node.getAttribute("description");
        String coefTimePickupString = node.getAttribute("coef_time_to_pickup");
        String coeftimecompleteString = node.getAttribute("coef_time_to_complete");
        String startPoint = node.getAttribute("startpoint");
        String loadPoint = node.getAttribute("pickuppoint");
        String completePoint = node.getAttribute("finishpoint");
        String statusString = node.getAttribute("status");
        String hasFragilityString = node.getAttribute("hasfragility");
        String fragilityString = node.getAttribute("fragility");
        IStoreorgelement.Status status = IStoreorgelement.Status.valueOf(statusString);
        boolean hasFragility = Helper.ConvertToBooleanAndWarn(hasFragilityString, "hasfragility", errorMessage);
        double fragilityValue = Helper.ConvertToDoubleAndWarn(fragilityString, "fragility", errorMessage);
        IStoreorgelement.Type type = IStoreorgelement.Type.valueOf(typeString);
        boolean isImportant = Helper.ConvertToBooleanAndWarn(importantString, "important", errorMessage);
        int rewardFlag = Helper.ConvertToIntegerAndWarn(rewardFlagString, "rewardflag", errorMessage);
        int forfeitFlag = Helper.ConvertToIntegerAndWarn(forfeitFlagString, "forfeit_flag", errorMessage);
        double coefTimePickUp = Helper.ConvertToDoubleAndWarn(coefTimePickupString, "coef_time_to_pickup", errorMessage);
        double coefTimeComplete = Helper.ConvertToDoubleAndWarn(coeftimecompleteString, "coef_time_to_complete", errorMessage);
        MissionTime coefTimePickUpResult = new MissionTime(coefTimePickUp);
        MissionTime coefTimeCompleteResult = new MissionTime(coefTimeComplete);
        Node rewardNode = node.getNamedChild("reward");
        Node forfeitNode = node.getNamedChild("forfeit");
        Node customerWarehouseAssociationNode = node.getNamedChild("customer_wh");
        Node customerEmptyNode = node.getNamedChild("customer_empty");
        Node customerQuestNode = node.getNamedChild("customer_quest");
        Node customerNpcNode = node.getNamedChild("customer_npc");
        Node startNoteNode = node.getNamedChild("startNote");
        Node finishNoteNode = node.getNamedChild("finishNote");
        Node failNoteNode = node.getNamedChild("failNotes");
        Node declineListenerNode = node.getNamedChild("declinelistener");
        RewardForfeit reward = OrganizerNodeSerialization.deserializeRewardForfeit(rewardNode);
        RewardForfeit forfeit = OrganizerNodeSerialization.deserializeRewardForfeit(forfeitNode);
        INPC customer = null;
        journable startNote = null;
        journable finishNote = null;
        journable[] failNotes = null;
        MissionEventsMaker.DeclineMissionListener declineListener = null;
        if (null != customerWarehouseAssociationNode) {
            customer = new CustomerWarehouseAssociation();
        } else if (null != customerEmptyNode) {
            customer = new EmptyCustomer();
        } else if (null != customerQuestNode) {
            nameCustomer = customerQuestNode.getAttribute("customer_name");
            if (null == nameCustomer) {
                Log.error("OrganizerNodeSerialization on deserializeXML has no attribute with name customer_name on node named customer_quest");
            } else {
                customer = new QuestCustomer(nameCustomer);
            }
        } else if (null != customerNpcNode) {
            nameCustomer = customerNpcNode.getAttribute("customer_name");
            if (null == nameCustomer) {
                Log.error("OrganizerNodeSerialization on deserializeXML has no attribute with name customer_name on node named customer_npc");
            } else {
                customer = new QuestNPC(nameCustomer);
            }
        }
        if (null != startNoteNode && null != (journalNote = startNoteNode.getNamedChild(JournalElementSerialization.getNodeName()))) {
            startNote = JournalElementSerialization.deserializeXML(journalNote);
        }
        if (null != finishNoteNode && null != (journalNote = finishNoteNode.getNamedChild(JournalElementSerialization.getNodeName()))) {
            finishNote = JournalElementSerialization.deserializeXML(journalNote);
        }
        if (null != failNoteNode) {
            NodeList listFailNodes = failNoteNode.getNamedChildren(ListElementSerializator.getNodeName());
            failNotes = new journable[listFailNodes.size()];
            int i = 0;
            for (Node element : listFailNodes) {
                Node emptyNode = element.getNamedChild("failNotes_empty");
                Node journalNode = element.getNamedChild(JournalElementSerialization.getNodeName());
                if (null == emptyNode) {
                    journable jou;
                    failNotes[i] = jou = JournalElementSerialization.deserializeXML(journalNode);
                }
                ++i;
            }
        }
        if (null != declineListenerNode) {
            String missionName = declineListenerNode.getAttribute("missionName");
            if (null == missionName) {
                Log.error(errorMessage + " node with name " + "declinelistener" + " has no attribute with name " + "missionName");
            } else {
                declineListener = new MissionEventsMaker.DeclineMissionListener(missionName);
            }
        }
        Scorgelement orgElement = new Scorgelement(name, type, isImportant, rewardFlag, reward, forfeitFlag, forfeit, descriptionString, customer, coefTimePickUpResult, coefTimeCompleteResult, startNote, finishNote, failNotes);
        orgElement.setSerialPoints(startPoint, loadPoint, completePoint);
        orgElement.setStatus(status);
        QuestCargoParams cargoParams = new QuestCargoParams();
        cargoParams.setCargoParams(hasFragility, fragilityValue);
        orgElement.setCargoParams(cargoParams);
        if (null != declineListener) {
            ArrayList<IDeclineOrgListener> declineListeners = new ArrayList<IDeclineOrgListener>();
            declineListeners.add(declineListener);
            orgElement.setListeners(declineListeners);
        }
        return orgElement;
    }

    static void serializeRewardForfeit(PrintStream stream, String nodeName, RewardForfeit value) {
        if (null != value) {
            List<Pair<String, String>> tempAttributes = Helper.createSingleAttribute("coef_money", value.gCoefMoney());
            Helper.addAttribute("coef_rate", value.gCoefRate(), tempAttributes);
            Helper.addAttribute("coef_rank", value.gCoefRank(), tempAttributes);
            Helper.addAttribute("estimate_money", value.gMoney(), tempAttributes);
            Helper.addAttribute("estimate_rate", value.gRate(), tempAttributes);
            Helper.addAttribute("estimate_rank", value.gRank(), tempAttributes);
            Helper.addAttribute("real_money", value.getRealMoney(), tempAttributes);
            Helper.printOpenNodeWithAttributes(stream, nodeName, tempAttributes);
            HashMap<String, Double> factionRatings = value.getFactionRatings();
            if (factionRatings != null && !factionRatings.isEmpty()) {
                Set<Map.Entry<String, Double>> ratingsSet = factionRatings.entrySet();
                for (Map.Entry<String, Double> singleFactionEntry : ratingsSet) {
                    List<Pair<String, String>> factionAttributes = Helper.createSingleAttribute("name", singleFactionEntry.getKey());
                    Helper.addAttribute("value", singleFactionEntry.getValue(), factionAttributes);
                    Helper.printClosedNodeWithAttributes(stream, "faction", factionAttributes);
                }
            }
            Helper.closeNode(stream, nodeName);
        }
    }

    static RewardForfeit deserializeRewardForfeit(Node node) {
        if (node == null) {
            return null;
        }
        String moneyCoefString = node.getAttribute("coef_money");
        String rateCoefString = node.getAttribute("coef_rate");
        String rankCoefString = node.getAttribute("coef_rank");
        String moneyString = node.getAttribute("estimate_money");
        String rateString = node.getAttribute("estimate_rate");
        String rankString = node.getAttribute("estimate_rank");
        String realMoneyString = node.getAttribute("real_money");
        double coefMoneyValue = Helper.ConvertToDoubleAndWarn(moneyCoefString, "coef_money", "OrganizerNodeSerialization on deserializeRewardForfeit ");
        double coefRateValue = Helper.ConvertToDoubleAndWarn(rateCoefString, "coef_rate", "OrganizerNodeSerialization on deserializeRewardForfeit ");
        double coefRankValue = Helper.ConvertToDoubleAndWarn(rankCoefString, "coef_rank", "OrganizerNodeSerialization on deserializeRewardForfeit ");
        double moneyValue = Helper.ConvertToDoubleAndWarn(moneyString, "estimate_money", "OrganizerNodeSerialization on deserializeRewardForfeit ");
        double rateValue = Helper.ConvertToDoubleAndWarn(rateString, "estimate_rate", "OrganizerNodeSerialization on deserializeRewardForfeit ");
        double rankValue = Helper.ConvertToDoubleAndWarn(rankString, "estimate_rank", "OrganizerNodeSerialization on deserializeRewardForfeit ");
        double realMoneyValue = Helper.ConvertToDoubleAndWarn(realMoneyString, "real_money", "OrganizerNodeSerialization on deserializeRewardForfeit ");
        RewardForfeit result = new RewardForfeit(coefMoneyValue, coefRateValue, coefRankValue);
        result.setRealMoney((int)realMoneyValue);
        result.sMoney((int)moneyValue);
        result.sRank((int)rankValue);
        result.sRate(rateValue);
        NodeList listFactionRewards = node.getNamedChildren("faction");
        if (listFactionRewards != null && !listFactionRewards.isEmpty()) {
            for (Node factionRewardNode : listFactionRewards) {
                String factionName = factionRewardNode.getAttribute("name");
                if (factionName == null) {
                    Log.error("deserializeRewardForfeit. Node faction has no attribute name");
                    continue;
                }
                String factionRewardValueString = factionRewardNode.getAttribute("value");
                if (factionRewardValueString == null) {
                    Log.error("deserializeRewardForfeit. Node faction has no attribute value");
                    continue;
                }
                double factionRewardValue = Helper.ConvertToDoubleAndWarn(factionRewardValueString, "value", "OrganizerNodeSerialization on deserializeRewardForfeit ");
                result.addFactionRating(factionName, factionRewardValue);
            }
        }
        return result;
    }
}

