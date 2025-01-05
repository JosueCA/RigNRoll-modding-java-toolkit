/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.List;
import java.util.Vector;
import players.actorveh;
import rnrcore.IXMLSerializable;
import rnrcore.vectorJ;
import rnrscenario.ScenarioSave;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.chaseTopo;
import rnrscenario.scenarioscript;
import scenarioUtils.Pair;
import xmlserialization.ActorVehSerializator;
import xmlserialization.Helper;
import xmlserialization.ListElementSerializator;
import xmlserialization.Log;
import xmlutils.Node;
import xmlutils.NodeList;

@ScenarioClass(scenarioStage=9)
public class ChaseTopoSerializator
implements IXMLSerializable {
    private chaseTopo m_object = null;

    public ChaseTopoSerializator(chaseTopo value) {
        this.m_object = value;
    }

    public ChaseTopoSerializator() {
        this.m_object = null;
    }

    public static String getNodeName() {
        return "chasetopo";
    }

    public static void serializeXML(chaseTopo value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("alreadyblown", value.isAlreadyblown());
        Helper.addAttribute("count_timeSilentQuest", value.getCount_timeSilentQuest(), attributes);
        Helper.addAttribute("CURstate", value.getCURstate(), attributes);
        Helper.addAttribute("deinitDakota_done", value.isDeinitDakota_done(), attributes);
        Helper.addAttribute("finishcallKohrein", value.isFinishcallKohrein(), attributes);
        Helper.addAttribute("move_to_badscenario", value.isMove_to_badscenario(), attributes);
        Helper.addAttribute("move_to_chase", value.isMove_to_chase(), attributes);
        Helper.addAttribute("rightorder_on_bridgescene", value.isRightorder_on_bridgescene(), attributes);
        Helper.addAttribute("startcallKohrein", value.isStartcallKohrein(), attributes);
        Helper.addAttribute("to_prepare_punktA", value.isTo_prepare_punktA(), attributes);
        Helper.addAttribute("need_customer_call1", value.isM_needCustoremCall1(), attributes);
        Helper.addAttribute("to_prepatreDakota", value.isTo_prepatreDakota(), attributes);
        Helper.addAttribute("to_prepatreDarkTruck", value.isTo_prepatreDarkTruck(), attributes);
        Helper.addAttribute("to_stop", value.isTo_stop(), attributes);
        Helper.addAttribute("tostop", value.isTostop(), attributes);
        Helper.addAttribute("wait_customer_call2", value.isWaitCustomerFinisnCall2Event(), attributes);
        Helper.addAttribute("was_in_chaseagain0", value.isWas_in_chaseagain0(), attributes);
        Helper.addAttribute("is_animate_bridge", value.isM_animateBridge(), attributes);
        Helper.addAttribute("animate_bridge_time", value.getAnimateBridgeTime(), attributes);
        Helper.addAttribute("animate_bridge_starttime", value.getAnimateBridgeStartTime(), attributes);
        Helper.addAttribute("procceed_contest", value.isContestProceeding(), attributes);
        Helper.printOpenNodeWithAttributes(stream, ChaseTopoSerializator.getNodeName(), attributes);
        Vector<chaseTopo.Stalker> stalkers = value.getStalkers();
        if (null != stalkers && !stalkers.isEmpty()) {
            Helper.openNode(stream, "stalkers");
            for (chaseTopo.Stalker stalker : stalkers) {
                ListElementSerializator.serializeXMLListelementOpen(stream);
                ActorVehSerializator.serializeXML(stalker.getPlayer(), stream);
                ListElementSerializator.serializeXMLListelementClose(stream);
            }
            Helper.closeNode(stream, "stalkers");
        }
        Helper.closeNode(stream, ChaseTopoSerializator.getNodeName());
    }

    public static void deserializeXML(Node node) {
        String animateBridgeStartTimeString;
        String animateBridgeTimeString;
        String errorrMessage = "ChaseTopoSerializator on deserializeXML ";
        String alreadyblownString = node.getAttribute("alreadyblown");
        String count_timeSilentQuestString = node.getAttribute("count_timeSilentQuest");
        String CURstateString = node.getAttribute("CURstate");
        String deinitDakota_doneString = node.getAttribute("deinitDakota_done");
        String finishcallKohreinString = node.getAttribute("finishcallKohrein");
        String move_to_badscenarioString = node.getAttribute("move_to_badscenario");
        String move_to_chaseString = node.getAttribute("move_to_chase");
        String rightorder_on_bridgescenString = node.getAttribute("rightorder_on_bridgescene");
        String startcallKohreinString = node.getAttribute("startcallKohrein");
        String to_prepare_punktAString = node.getAttribute("to_prepare_punktA");
        String needCustoremCall1String = node.getAttribute("need_customer_call1");
        String to_prepatreDakotaString = node.getAttribute("to_prepatreDakota");
        String to_prepatreDarkTruckString = node.getAttribute("to_prepatreDarkTruck");
        String to_stopString = node.getAttribute("to_stop");
        String tostopString = node.getAttribute("tostop");
        String isAnimatingBridgeString = node.getAttribute("is_animate_bridge");
        String isContestProccedString = node.getAttribute("procceed_contest");
        if (null == isAnimatingBridgeString) {
            isAnimatingBridgeString = "false";
        }
        if (null == (animateBridgeTimeString = node.getAttribute("animate_bridge_time"))) {
            animateBridgeTimeString = "-1.";
        }
        if (null == (animateBridgeStartTimeString = node.getAttribute("animate_bridge_starttime"))) {
            animateBridgeStartTimeString = "-1.";
        }
        String was_in_chaseagain0String = node.getAttribute("was_in_chaseagain0");
        boolean alreadyblownValue = Helper.ConvertToBooleanAndWarn(alreadyblownString, "alreadyblown", errorrMessage);
        int count_timeSilentQuestValue = Helper.ConvertToIntegerAndWarn(count_timeSilentQuestString, "count_timeSilentQuest", errorrMessage);
        int CURstateValue = Helper.ConvertToIntegerAndWarn(CURstateString, "CURstate", errorrMessage);
        boolean deinitDakota_doneValue = Helper.ConvertToBooleanAndWarn(deinitDakota_doneString, "deinitDakota_done", errorrMessage);
        boolean finishcallKohreinValue = Helper.ConvertToBooleanAndWarn(finishcallKohreinString, "finishcallKohrein", errorrMessage);
        boolean move_to_badscenarioValue = Helper.ConvertToBooleanAndWarn(move_to_badscenarioString, "move_to_badscenario", errorrMessage);
        boolean move_to_chaseValue = Helper.ConvertToBooleanAndWarn(move_to_chaseString, "move_to_chase", errorrMessage);
        boolean rightorder_on_bridgescenValue = Helper.ConvertToBooleanAndWarn(rightorder_on_bridgescenString, "rightorder_on_bridgescene", errorrMessage);
        boolean startcallKohreinValue = Helper.ConvertToBooleanAndWarn(startcallKohreinString, "startcallKohrein", errorrMessage);
        boolean to_prepare_punktAValue = Helper.ConvertToBooleanAndWarn(to_prepare_punktAString, "to_prepare_punktA", errorrMessage);
        boolean to_prepatreDakotaValue = Helper.ConvertToBooleanAndWarn(to_prepatreDakotaString, "to_prepatreDakota", errorrMessage);
        boolean to_prepatreDarkTruckValue = Helper.ConvertToBooleanAndWarn(to_prepatreDarkTruckString, "to_prepatreDarkTruck", errorrMessage);
        boolean to_stopValue = Helper.ConvertToBooleanAndWarn(to_stopString, "to_stop", errorrMessage);
        boolean tostopValue = Helper.ConvertToBooleanAndWarn(tostopString, "tostop", errorrMessage);
        boolean was_in_chaseagain0Value = Helper.ConvertToBooleanAndWarn(was_in_chaseagain0String, "was_in_chaseagain0", errorrMessage);
        boolean isAnimatingBridgeValue = Helper.ConvertToBooleanAndWarn(isAnimatingBridgeString, "is_animate_bridge", errorrMessage);
        double animateBridgeTimeValue = Helper.ConvertToDoubleAndWarn(animateBridgeTimeString, "animate_bridge_time", errorrMessage);
        double animateBridgeStartTimeValue = Helper.ConvertToDoubleAndWarn(animateBridgeStartTimeString, "animate_bridge_starttime", errorrMessage);
        boolean isContestProcced = Helper.ConvertToBooleanAndWarn(isContestProccedString, "procceed_contest", errorrMessage);
        boolean needCustoremCall1 = true;
        if (needCustoremCall1String != null) {
            needCustoremCall1 = Helper.ConvertToBooleanAndWarn(isContestProccedString, "need_customer_call1", errorrMessage);
        }
        Vector<chaseTopo.Stalker> stalkers = new Vector<chaseTopo.Stalker>();
        Node stalkersNode = node.getNamedChild("stalkers");
        if (null != stalkersNode) {
            NodeList listElements = stalkersNode.getNamedChildren(ListElementSerializator.getNodeName());
            for (Node element : listElements) {
                actorveh car = null;
                vectorJ pos = null;
                Node actorvehNode = element.getNamedChild(ActorVehSerializator.getNodeName());
                if (null != actorvehNode) {
                    car = ActorVehSerializator.deserializeXML(actorvehNode);
                } else {
                    Log.error("ChaseTopoSerializator on deserializeXML has no node " + ActorVehSerializator.getNodeName() + "in list of " + "stalkers");
                }
                stalkers.add(new chaseTopo.Stalker(car, pos));
            }
        }
        chaseTopo result = scenarioscript.script.constructChaseTopo();
        ScenarioSave.getInstance().setChaseTopo(result);
        result.setAlreadyblown(alreadyblownValue);
        result.setCount_timeSilentQuest(count_timeSilentQuestValue);
        result.setTo_prepatreDakota(to_prepatreDakotaValue);
        result.setCURstate(CURstateValue);
        result.setDeinitDakota_done(deinitDakota_doneValue);
        result.setFinishcallKohrein(finishcallKohreinValue);
        result.setMove_to_badscenario(move_to_badscenarioValue);
        result.setMove_to_chase(move_to_chaseValue);
        result.setRightorder_on_bridgescene(rightorder_on_bridgescenValue);
        result.setStartcallKohrein(startcallKohreinValue);
        result.setTo_prepare_punktA(to_prepare_punktAValue);
        result.setTo_prepatreDarkTruck(to_prepatreDarkTruckValue);
        result.setTo_stop(to_stopValue);
        result.setTostop(tostopValue);
        result.setWas_in_chaseagain0(was_in_chaseagain0Value);
        result.setStalkers(stalkers);
        result.setAnimateBridgeTime(animateBridgeTimeValue);
        result.setAnimateBridgeStartTime(animateBridgeStartTimeValue);
        result.setM_animateBridge(isAnimatingBridgeValue);
        result.setContestProceeding(isContestProcced);
        result.setM_needCustoremCall1(needCustoremCall1);
    }

    public void deSerialize(Node node) {
        ChaseTopoSerializator.deserializeXML(node);
    }

    public void serialize(PrintStream stream) {
        ChaseTopoSerializator.serializeXML(this.m_object, stream);
    }
}

