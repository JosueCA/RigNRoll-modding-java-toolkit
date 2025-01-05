/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.List;
import rnrcore.CoreTime;
import rnrorg.CustomerWarehouseAssociation;
import rnrorg.EmptyCustomer;
import rnrorg.INPC;
import rnrorg.IStoreorgelement;
import rnrorg.QuestCustomer;
import rnrorg.QuestNPC;
import rnrorg.RewardForfeit;
import rnrorg.WarehouseOrder;
import rnrorg.journable;
import scenarioUtils.Pair;
import xmlserialization.CoreTimeSerialization;
import xmlserialization.Helper;
import xmlserialization.JournalElementSerialization;
import xmlserialization.ListElementSerializator;
import xmlserialization.Log;
import xmlserialization.OrganizerNodeSerialization;
import xmlutils.Node;
import xmlutils.NodeList;

public class WarehouseOrganizerNodeSerialization {
    public static String getNodeName() {
        return "warehouseorder";
    }

    public static void serializeXML(WarehouseOrder value, PrintStream stream) {
        journable[] failNote;
        journable finishNote;
        journable startNote;
        CoreTime completeTime;
        CoreTime requestTime;
        INPC customer;
        RewardForfeit forfeit;
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("is_read", value.isRead());
        Helper.addAttribute("id", value.getName(), attributes);
        Helper.addAttribute("type", value.getType().toString(), attributes);
        Helper.addAttribute("status", value.getStatus().toString(), attributes);
        Helper.addAttribute("important", value.isImportant(), attributes);
        Helper.addAttribute("type_failed", value.getType_failed(), attributes);
        Helper.addAttribute("rewardFlag", value.getRewardFlag(), attributes);
        Helper.addAttribute("forfeitFlag", value.getForfeitFlag(), attributes);
        Helper.addAttribute("minutes_to_complete", value.get_minutes_toFail(), attributes);
        Helper.addAttribute("seconds_to_complete", value.get_seconds_toFail(), attributes);
        Helper.addAttribute("mission_state", value.getMissionState(), attributes);
        Helper.addAttribute("fragility", value.getFragility(), attributes);
        Helper.addAttribute("loadPoint", value.loadPoint(), attributes);
        Helper.addAttribute("completePoint", value.endPoint(), attributes);
        Helper.addAttribute("orderdescription", value.getOrderdescription(), attributes);
        Helper.addAttribute("racename", value.getRaceName(), attributes);
        Helper.addAttribute("logoname", value.getLogoName(), attributes);
        Helper.addAttribute("stageID", value.getStageID(), attributes);
        Helper.printOpenNodeWithAttributes(stream, WarehouseOrganizerNodeSerialization.getNodeName(), attributes);
        RewardForfeit reward = value.getReward();
        if (null != reward) {
            List<Pair<String, String>> tempAttributes = Helper.createSingleAttribute("coef_money", reward.gCoefMoney());
            Helper.addAttribute("coef_rate", reward.gCoefRate(), tempAttributes);
            Helper.addAttribute("coef_rank", reward.gCoefRank(), tempAttributes);
            Helper.addAttribute("estimate_money", reward.gMoney(), tempAttributes);
            Helper.addAttribute("estimate_rate", reward.gRate(), tempAttributes);
            Helper.addAttribute("estimate_rank", reward.gRank(), tempAttributes);
            Helper.addAttribute("real_money", reward.getRealMoney(), tempAttributes);
            Helper.printClosedNodeWithAttributes(stream, "reward", tempAttributes);
        }
        if (null != (forfeit = value.getForfeit())) {
            List<Pair<String, String>> tempAttributes = Helper.createSingleAttribute("coef_money", forfeit.gCoefMoney());
            Helper.addAttribute("coef_rate", forfeit.gCoefRate(), tempAttributes);
            Helper.addAttribute("coef_rank", forfeit.gCoefRank(), tempAttributes);
            Helper.addAttribute("estimate_money", forfeit.gMoney(), tempAttributes);
            Helper.addAttribute("estimate_rate", forfeit.gRate(), tempAttributes);
            Helper.addAttribute("estimate_rank", forfeit.gRank(), tempAttributes);
            Helper.addAttribute("real_money", forfeit.getRealMoney(), tempAttributes);
            Helper.printClosedNodeWithAttributes(stream, "forfeit", tempAttributes);
        }
        if (null != (customer = value.getCustomer())) {
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
        if (null != (requestTime = value.dateOfRequest())) {
            Helper.openNode(stream, "requestTime");
            CoreTimeSerialization.serializeXML(requestTime, stream);
            Helper.closeNode(stream, "requestTime");
        }
        if (null != (completeTime = value.timeToComplete())) {
            Helper.openNode(stream, "completeTime");
            CoreTimeSerialization.serializeXML(completeTime, stream);
            Helper.closeNode(stream, "completeTime");
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
        Helper.closeNode(stream, WarehouseOrganizerNodeSerialization.getNodeName());
    }

    public static WarehouseOrder deserializeXML(Node node) {
        String nameCustomer;
        Node nodeTime;
        String errorrMessage = "WarehouseOrganizerNodeSerialization in deserializeXML ";
        String isReadString = node.getAttribute("is_read");
        String nameString = node.getAttribute("id");
        String typeString = node.getAttribute("type");
        String statusString = node.getAttribute("status");
        String isImportantString = node.getAttribute("important");
        String typeFailedString = node.getAttribute("type_failed");
        String rewardFlagString = node.getAttribute("rewardFlag");
        String forfeitFlagString = node.getAttribute("forfeitFlag");
        String minutesToCompleteString = node.getAttribute("minutes_to_complete");
        String secondsToCompleteString = node.getAttribute("seconds_to_complete");
        String missionStateString = node.getAttribute("mission_state");
        String fragilityString = node.getAttribute("fragility");
        String loadpointString = node.getAttribute("loadPoint");
        String completePointString = node.getAttribute("completePoint");
        String orderdescriptionString = node.getAttribute("orderdescription");
        String raceNameString = node.getAttribute("racename");
        String logoString = node.getAttribute("logoname");
        String stageIdString = node.getAttribute("stageID");
        boolean isRead = Helper.ConvertToBooleanAndWarn(isReadString, "is_read", errorrMessage);
        IStoreorgelement.Type type = IStoreorgelement.Type.valueOf(typeString);
        IStoreorgelement.Status status = IStoreorgelement.Status.valueOf(statusString);
        boolean isImportant = Helper.ConvertToBooleanAndWarn(isImportantString, "important", errorrMessage);
        int typeFailed = Helper.ConvertToIntegerAndWarn(typeFailedString, "type_failed", errorrMessage);
        int rewardFlag = Helper.ConvertToIntegerAndWarn(rewardFlagString, "rewardFlag", errorrMessage);
        int forfeitFlag = Helper.ConvertToIntegerAndWarn(forfeitFlagString, "forfeitFlag", errorrMessage);
        int minutesToComplete = Helper.ConvertToIntegerAndWarn(minutesToCompleteString, "minutes_to_complete", errorrMessage);
        int secondsToComplete = Helper.ConvertToIntegerAndWarn(secondsToCompleteString, "seconds_to_complete", errorrMessage);
        int missionState = Helper.ConvertToIntegerAndWarn(missionStateString, "mission_state", errorrMessage);
        double fragility = Helper.ConvertToDoubleAndWarn(fragilityString, "fragility", errorrMessage);
        int stageId = stageIdString != null ? Helper.ConvertToIntegerAndWarn(stageIdString, "stageID", errorrMessage) : 0;
        Node rewardNode = node.getNamedChild("reward");
        Node forfeitNode = node.getNamedChild("forfeit");
        Node customerWarehouseAssociationNode = node.getNamedChild("customer_wh");
        Node customerEmptyNode = node.getNamedChild("customer_empty");
        Node customerQuestNode = node.getNamedChild("customer_quest");
        Node customerNpcNode = node.getNamedChild("customer_npc");
        Node startNoteNode = node.getNamedChild("startNote");
        Node finishNoteNode = node.getNamedChild("finishNote");
        Node failNoteNode = node.getNamedChild("failNotes");
        Node requestTimeNode = node.getNamedChild("requestTime");
        Node completeTimeNode = node.getNamedChild("completeTime");
        RewardForfeit reward = OrganizerNodeSerialization.deserializeRewardForfeit(rewardNode);
        RewardForfeit forfeit = OrganizerNodeSerialization.deserializeRewardForfeit(forfeitNode);
        INPC customer = null;
        journable startNote = null;
        journable finishNote = null;
        journable[] failNotes = null;
        CoreTime requestTime = null;
        CoreTime completeTime = null;
        if (null != requestTimeNode) {
            nodeTime = requestTimeNode.getNamedChild(CoreTimeSerialization.getNodeName());
            if (null == nodeTime) {
                Log.error(errorrMessage + " node named " + "requestTime" + " has no named node " + CoreTimeSerialization.getNodeName());
            } else {
                requestTime = CoreTimeSerialization.deserializeXML(nodeTime);
            }
        }
        if (null != completeTimeNode) {
            nodeTime = completeTimeNode.getNamedChild(CoreTimeSerialization.getNodeName());
            if (null == nodeTime) {
                Log.error(errorrMessage + " node named " + "completeTime" + " has no named node " + CoreTimeSerialization.getNodeName());
            } else {
                completeTime = CoreTimeSerialization.deserializeXML(nodeTime);
            }
        }
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
        if (null != startNoteNode) {
            startNote = JournalElementSerialization.deserializeXML(startNoteNode);
        }
        if (null != finishNoteNode) {
            finishNote = JournalElementSerialization.deserializeXML(finishNoteNode);
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
        WarehouseOrder result = new WarehouseOrder();
        result.setName(nameString);
        result.setLoadPoint(loadpointString);
        result.setCompletePoint(completePointString);
        result.setOrderDescription(orderdescriptionString);
        result.setRaceName(raceNameString);
        result.setLogoName(logoString);
        result.setStageID(stageId);
        result.setIs_read(isRead);
        result.setType(type);
        result.setStatus(status);
        result.setImportant(isImportant);
        result.setType_failed(typeFailed);
        result.setRewardFlag(rewardFlag);
        result.setForfeitFlag(forfeitFlag);
        result.updateTimeToComplete(minutesToComplete, secondsToComplete, missionState, "");
        result.setFragility(fragility);
        result.setReward(reward);
        result.setForfeit(forfeit);
        result.setCustomer(customer);
        result.setStartNote(startNote);
        result.setFinishNote(finishNote);
        result.setFailNote(failNotes);
        result.setRequestTime(requestTime);
        result.setCompleteTime(completeTime);
        return result;
    }
}

