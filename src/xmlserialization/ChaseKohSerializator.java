/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.List;
import players.actorveh;
import players.aiplayer;
import rnrcore.IXMLSerializable;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.ChaseKoh;
import rnrscenario.controllers.ScenarioHost;
import scenarioUtils.Pair;
import xmlserialization.AIPlayerSerializator;
import xmlserialization.ActorVehSerializator;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlutils.Node;

@ScenarioClass(scenarioStage=15)
public class ChaseKohSerializator
implements IXMLSerializable {
    private ChaseKoh slave = null;
    private final ScenarioHost host;

    public ChaseKohSerializator(ChaseKoh value) {
        this.slave = value;
        this.host = null;
    }

    public ChaseKohSerializator(ScenarioHost host) {
        this.host = host;
    }

    public static String getNodeName() {
        return "chasekoh";
    }

    public static void serializeXML(ChaseKoh value, PrintStream stream) {
        aiplayer kohPlayer;
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("toolongtime", value.getTooLong().getTime());
        Helper.addAttribute("_last_chance_failed", value.is_last_chance_failed(), attributes);
        Helper.addAttribute("chase_started", value.isChase_started(), attributes);
        Helper.addAttribute("haswantblowanimation", value.getWant_blow_car() != null, attributes);
        Helper.printOpenNodeWithAttributes(stream, ChaseKohSerializator.getNodeName(), attributes);
        actorveh carkoh = value.getCarkohForShootAnimationSerialization();
        if (null != carkoh) {
            Helper.openNode(stream, "kohcarforshootanimation");
            ActorVehSerializator.serializeXML(carkoh, stream);
            Helper.closeNode(stream, "kohcarforshootanimation");
        }
        if (null != (kohPlayer = value.getKoh_chase_player())) {
            Helper.openNode(stream, "koh_chase_player");
            AIPlayerSerializator.serializeXML(kohPlayer, stream);
            Helper.closeNode(stream, "koh_chase_player");
        }
        Helper.closeNode(stream, ChaseKohSerializator.getNodeName());
    }

    public static void deserializeXML(Node node, ScenarioHost host) {
        String errorMessage = "ChaseKohSerializator on deserializeXML ";
        String timeChaseString = node.getAttribute("toolongtime");
        String lastChanceFailedString = node.getAttribute("_last_chance_failed");
        String chaseStartedString = node.getAttribute("chase_started");
        String hasBlowAnimationString = node.getAttribute("haswantblowanimation");
        double timeChaseValue = Helper.ConvertToDoubleAndWarn(timeChaseString, "toolongtime", errorMessage);
        boolean lastChanceFailedValue = Helper.ConvertToBooleanAndWarn(lastChanceFailedString, "_last_chance_failed", errorMessage);
        boolean chaseStartedValue = Helper.ConvertToBooleanAndWarn(chaseStartedString, "chase_started", errorMessage);
        boolean hasBlowAnimationValue = Helper.ConvertToBooleanAndWarn(hasBlowAnimationString, "haswantblowanimation", errorMessage);
        aiplayer player = null;
        actorveh kohCar = null;
        Node kohPlayer = node.getNamedChild("koh_chase_player");
        Node kohCarNode = node.getNamedChild("kohcarforshootanimation");
        if (null != kohPlayer) {
            Node playerNode = kohPlayer.getNamedChild(AIPlayerSerializator.getNodeName());
            if (null == playerNode) {
                Log.error(errorMessage + "has no child node with name " + AIPlayerSerializator.getNodeName() + " in node named " + "koh_chase_player");
            } else {
                player = AIPlayerSerializator.deserializeXML(playerNode);
            }
        }
        if (null != kohCarNode) {
            Node carNode = kohCarNode.getNamedChild(ActorVehSerializator.getNodeName());
            if (null == carNode) {
                Log.error(errorMessage + "has no child node with name " + ActorVehSerializator.getNodeName() + " in node named " + "kohcarforshootanimation");
            } else {
                kohCar = ActorVehSerializator.deserializeXML(carNode);
            }
        }
        ChaseKoh result = new ChaseKoh(host, true);
        result.getTooLong().setTime(timeChaseValue);
        result.set_last_chance_failed(lastChanceFailedValue);
        result.setChase_started(chaseStartedValue);
        if (hasBlowAnimationValue) {
            result.setWant_blow_car(new ChaseKoh.WantBlowCar());
        }
        result.setKoh_chase_player(player);
        result.setCarkohForShootAnimationSerialization(kohCar);
    }

    public void deSerialize(Node node) {
        ChaseKohSerializator.deserializeXML(node, this.host);
    }

    public void serialize(PrintStream stream) {
        ChaseKohSerializator.serializeXML(this.slave, stream);
    }
}

