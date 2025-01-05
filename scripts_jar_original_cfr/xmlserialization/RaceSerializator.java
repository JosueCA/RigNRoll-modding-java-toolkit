/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import players.actorveh;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.RACErace_state_single;
import rnrscenario.controllers.RACEspace;
import rnrscenario.controllers.RaceFailCondition;
import scenarioUtils.Pair;
import xmlserialization.ActorVehSerializator;
import xmlserialization.Helper;
import xmlserialization.ListElementSerializator;
import xmlserialization.Log;
import xmlserialization.nxs.SerializatorOfAnnotated;
import xmlutils.Node;
import xmlutils.NodeList;

@ScenarioClass(scenarioStage=-1, fieldWithDesiredStage="scenarioStage")
public class RaceSerializator {
    private final int scenarioStage;

    public RaceSerializator(int desiredScenarioStage) {
        this.scenarioStage = desiredScenarioStage;
    }

    public String getNodeName() {
        return "racespace";
    }

    public void serializeXML(RACEspace value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("raceFSMstate", value.getRaceFSMstate());
        Helper.addAttribute("race_condition__condition", value.getConditions().getCondition(), attributes);
        Helper.addAttribute("timeElapsed", value.getStates().getTimeElapsed(), attributes);
        Helper.addAttribute("anyonfinish", value.getStates().isAnyonfinish(), attributes);
        Helper.addAttribute("lastplace", value.getStates().getLastplace(), attributes);
        Helper.addAttribute("statesucceded", value.getStates().getStatesucceded(), attributes);
        Helper.printOpenNodeWithAttributes(stream, this.getNodeName(), attributes);
        SerializatorOfAnnotated.getInstance().saveState(stream, value.getFailDetector());
        HashMap<actorveh, RACErace_state_single> participants = value.getStates().getParticipantsAllData();
        if (null == participants || participants.isEmpty()) {
            Log.error("ERRORR. RaceSerializator on serializeXML has null or empty participants.");
        } else {
            Helper.openNode(stream, "participants");
            Set<Map.Entry<actorveh, RACErace_state_single>> set = participants.entrySet();
            for (Map.Entry<actorveh, RACErace_state_single> entry : set) {
                ListElementSerializator.serializeXMLListelementOpen(stream);
                ActorVehSerializator.serializeXML(entry.getKey(), stream);
                List<Pair<String, String>> singleDataAttributes = Helper.createSingleAttribute("finished", entry.getValue().isFinished());
                Helper.addAttribute("place", entry.getValue().getPlace(), singleDataAttributes);
                Helper.addAttribute("distance", entry.getValue().getDistance(), singleDataAttributes);
                Helper.printClosedNodeWithAttributes(stream, "participantssingledata", singleDataAttributes);
                ListElementSerializator.serializeXMLListelementClose(stream);
            }
            Helper.closeNode(stream, "participants");
        }
        Helper.closeNode(stream, this.getNodeName());
    }

    public void deserializeXML(RACEspace race, Node node) {
        String errorMessage = "RaceSerializator on deserializeXML ";
        String raceFsmStateString = node.getAttribute("raceFSMstate");
        String raceConditionsConditionString = node.getAttribute("race_condition__condition");
        String timeElapsedString = node.getAttribute("timeElapsed");
        String anyOnFinishString = node.getAttribute("anyonfinish");
        String lastPlaceString = node.getAttribute("lastplace");
        String stateSuccededString = node.getAttribute("statesucceded");
        int raceFsmStateValue = Helper.ConvertToIntegerAndWarn(raceFsmStateString, "raceFSMstate", errorMessage);
        int raceConditionsConditionValue = Helper.ConvertToIntegerAndWarn(raceConditionsConditionString, "race_condition__condition", errorMessage);
        double timeElapsedValue = Helper.ConvertToDoubleAndWarn(timeElapsedString, "timeElapsed", errorMessage);
        boolean anyOnFinishValue = Helper.ConvertToBooleanAndWarn(anyOnFinishString, "anyonfinish", errorMessage);
        int lastPlaceValue = Helper.ConvertToIntegerAndWarn(lastPlaceString, "lastplace", errorMessage);
        int stateSuccededValue = Helper.ConvertToIntegerAndWarn(stateSuccededString, "statesucceded", errorMessage);
        race.setRaceFSMstate(raceFsmStateValue);
        race.getConditions().setCondition(raceConditionsConditionValue);
        race.getStates().setTimeElapsed(timeElapsedValue);
        race.getStates().setAnyonfinish(anyOnFinishValue);
        race.getStates().setLastplace(lastPlaceValue);
        race.getStates().setStatesucceded(stateSuccededValue);
        RaceFailCondition raceFailDetector = (RaceFailCondition)SerializatorOfAnnotated.getInstance().loadStateOrNull(node.getNamedChild("RaceFailCondition"));
        if (null != raceFailDetector) {
            race.setFailDetector(raceFailDetector);
        }
        HashMap<actorveh, RACErace_state_single> participants = new HashMap<actorveh, RACErace_state_single>();
        Node nodeParticipants = node.getNamedChild("participants");
        if (null == nodeParticipants) {
            Log.error("ERRORR. RaceSerializator on deserializeXML has no child node with name participants");
        } else {
            NodeList listParticipants = nodeParticipants.getNamedChildren(ListElementSerializator.getNodeName());
            if (null == listParticipants || listParticipants.isEmpty()) {
                Log.error("ERRORR. RaceSerializator on deserializeXML has no elementt nodes with name " + ListElementSerializator.getNodeName() + " in node named " + "participants");
            } else {
                for (Node element : listParticipants) {
                    Node actorNode = element.getNamedChild(ActorVehSerializator.getNodeName());
                    Node singleDataNode = element.getNamedChild("participantssingledata");
                    if (null == actorNode) {
                        Log.error("ERRORR. RaceSerializator on deserializeXML has no child node with name " + ActorVehSerializator.getNodeName());
                    }
                    RACErace_state_single singleData = new RACErace_state_single(this.scenarioStage);
                    if (null == singleDataNode) {
                        Log.error("ERRORR. RaceSerializator on deserializeXML has no child node with name participantssingledata");
                        continue;
                    }
                    String sdFinisgedString = singleDataNode.getAttribute("finished");
                    boolean sdFinishedValue = Helper.ConvertToBooleanAndWarn(sdFinisgedString, "finished", errorMessage);
                    singleData.setFinished(sdFinishedValue);
                    String sdPlaceString = singleDataNode.getAttribute("place");
                    int sdPlaceValue = Helper.ConvertToIntegerAndWarn(sdPlaceString, "place", errorMessage);
                    singleData.setPlace(sdPlaceValue);
                    String sdDistanceString = singleDataNode.getAttribute("distance");
                    int sdDistanceValue = Helper.ConvertToIntegerAndWarn(sdDistanceString, "distance", errorMessage);
                    singleData.setDistance(sdDistanceValue);
                    actorveh car = ActorVehSerializator.deserializeXML(actorNode);
                    participants.put(car, singleData);
                }
            }
        }
        race.getStates().setParticipantsAllData(participants);
    }
}

