/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.List;
import players.actorveh;
import rnrcore.CoreTime;
import rnrcore.IXMLSerializable;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.ScenarioHost;
import rnrscenario.controllers.preparesc00060;
import scenarioUtils.Pair;
import xmlserialization.ActorVehSerializator;
import xmlserialization.CoreTimeSerialization;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlutils.Node;

@ScenarioClass(scenarioStage=1)
public class Preparesc00060Serializator
implements IXMLSerializable {
    private preparesc00060 serializationTarget;
    private final ScenarioHost host;

    public Preparesc00060Serializator(preparesc00060 value, ScenarioHost scenarioHost) {
        assert (null != scenarioHost) : "'scenarioHost' must be non-null reference";
        this.serializationTarget = value;
        this.host = scenarioHost;
    }

    public Preparesc00060Serializator(ScenarioHost scenarioHost) {
        assert (null != scenarioHost) : "'scenarioHost' must be non-null reference";
        this.serializationTarget = null;
        this.host = scenarioHost;
    }

    public static String getNodeName() {
        return "Preparesc00060";
    }

    public static void serializeXML(preparesc00060 value, PrintStream stream) {
        actorveh banditCar;
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("alreadyCalled", value.isAlreadyCalled());
        Helper.addAttribute("checkecondition", value.isCheckecondition(), attributes);
        Helper.addAttribute("counterlimit2Succeded", value.isCounterTriggered(), attributes);
        Helper.addAttribute("counterTurnedOn", value.isCounterTurnedOn(), attributes);
        Helper.printOpenNodeWithAttributes(stream, Preparesc00060Serializator.getNodeName(), attributes);
        CoreTime time = value.getQuestFailureTime();
        if (null != time) {
            Helper.openNode(stream, "quest_failure_time");
            CoreTimeSerialization.serializeXML(time, stream);
            Helper.closeNode(stream, "quest_failure_time");
        }
        if (null != (time = value.getQuestStartTime())) {
            Helper.openNode(stream, "quest_start_time");
            CoreTimeSerialization.serializeXML(time, stream);
            Helper.closeNode(stream, "quest_start_time");
        }
        if (null != (banditCar = value.getBanditsCar())) {
            Helper.openNode(stream, "banditsCar");
            ActorVehSerializator.serializeXML(banditCar, stream);
            Helper.closeNode(stream, "banditsCar");
        }
        Helper.closeNode(stream, Preparesc00060Serializator.getNodeName());
    }

    private static preparesc00060 deserializeXML(Node node, ScenarioHost host) {
        Node itemNode;
        Node actorVehNode;
        String alreadyCalledString = node.getAttribute("alreadyCalled");
        String checkConditionString = node.getAttribute("checkecondition");
        String counterLimitSucceededString = node.getAttribute("counterlimit2Succeded");
        String isCounterTurnedOnString = node.getAttribute("counterTurnedOn");
        boolean alreadyCalledValue = Helper.ConvertToBooleanAndWarn(alreadyCalledString, "alreadyCalled", "Preparesc00060Serializator on deserializeXML ");
        boolean checkConditionValue = Helper.ConvertToBooleanAndWarn(checkConditionString, "checkecondition", "Preparesc00060Serializator on deserializeXML ");
        boolean counterLimitSucceededValue = Helper.ConvertToBooleanAndWarn(counterLimitSucceededString, "counterlimit2Succeded", "Preparesc00060Serializator on deserializeXML ");
        boolean isCounterTurnedOnValue = Helper.ConvertToBooleanAndWarn(isCounterTurnedOnString, "counterTurnedOn", "Preparesc00060Serializator on deserializeXML ");
        actorveh banditCar = null;
        Node banditCarNode = node.getNamedChild("banditsCar");
        if (null != banditCarNode && null != (actorVehNode = banditCarNode.getNamedChild(ActorVehSerializator.getNodeName()))) {
            banditCar = ActorVehSerializator.deserializeXML(actorVehNode);
        }
        CoreTime questFailureTime = null;
        CoreTime questStartTime = null;
        Node failureTimeNode = node.getNamedChild("quest_failure_time");
        Node startTimeNode = node.getNamedChild("quest_start_time");
        if (null != failureTimeNode) {
            itemNode = failureTimeNode.getNamedChild(CoreTimeSerialization.getNodeName());
            if (null != itemNode) {
                questFailureTime = CoreTimeSerialization.deserializeXML(itemNode);
            } else {
                Log.error("ERRORR. Preparesc00060Serializator in deserializeXML has no node " + CoreTimeSerialization.getNodeName() + " in node " + "quest_failure_time");
            }
        }
        questStartTime = null != startTimeNode ? (null == (itemNode = startTimeNode.getNamedChild(CoreTimeSerialization.getNodeName())) ? new CoreTime() : CoreTimeSerialization.deserializeXML(itemNode)) : new CoreTime();
        if (isCounterTurnedOnValue && null == questFailureTime) {
            questFailureTime = new CoreTime();
        }
        preparesc00060 result = new preparesc00060(host, true);
        result.setAlreadyCalled(alreadyCalledValue);
        result.setBanditsCar(banditCar);
        result.setCheckecondition(checkConditionValue);
        result.setQuestFailureTime(questFailureTime);
        result.setQuestStartTime(questStartTime);
        result.setCounterTriggered(counterLimitSucceededValue);
        result.setCounterTurnedOn(isCounterTurnedOnValue);
        result.start();
        return result;
    }

    public void deSerialize(Node node) {
        Preparesc00060Serializator.deserializeXML(node, this.host);
    }

    public void serialize(PrintStream stream) {
        Preparesc00060Serializator.serializeXML(this.serializationTarget, stream);
    }
}

