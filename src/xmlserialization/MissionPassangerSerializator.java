/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import org.w3c.dom.Node;
import players.actorveh;
import players.aiplayer;
import rnrcore.XmlSerializable;
import rnrscr.MissionPassanger;
import xmlserialization.AIPlayerSerializator;
import xmlserialization.ActorVehSerializator;
import xmlserialization.Helper;

public class MissionPassangerSerializator
implements XmlSerializable {
    private static MissionPassangerSerializator instance = new MissionPassangerSerializator();

    public static MissionPassangerSerializator getInstance() {
        return instance;
    }

    private MissionPassangerSerializator() {
    }

    public String getRootNodeName() {
        return "mission_passanger";
    }

    public static String getNodeNamePack() {
        return "aipack";
    }

    public void loadFromNode(Node node) {
        this.deserializeXML(new xmlutils.Node(node));
    }

    public void yourNodeWasNotFound() {
    }

    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        this.serializeXML(MissionPassanger.getInstance(), stream);
    }

    private void serializeXML(MissionPassanger missionPassanger, PrintStream stream) {
        aiplayer pack;
        aiplayer player;
        Helper.openNode(stream, this.getRootNodeName());
        actorveh car = missionPassanger.getCar();
        if (car != null) {
            ActorVehSerializator.serializeXML(car, stream);
        }
        if ((player = missionPassanger.getNpc()) != null) {
            AIPlayerSerializator.serializeXML(player, stream);
        }
        if ((pack = missionPassanger.getPack()) != null) {
            Helper.openNode(stream, MissionPassangerSerializator.getNodeNamePack());
            AIPlayerSerializator.serializeXML(pack, stream);
            Helper.closeNode(stream, MissionPassangerSerializator.getNodeNamePack());
        }
        Helper.closeNode(stream, this.getRootNodeName());
    }

    private void deserializeXML(xmlutils.Node node) {
        xmlutils.Node pNode;
        xmlutils.Node carNode = node.getNamedChild(ActorVehSerializator.getNodeName());
        if (carNode != null) {
            actorveh car = ActorVehSerializator.deserializeXML(carNode);
            MissionPassanger.getInstance().setCar(car);
        }
        xmlutils.Node playerNode = node.getNamedChild(AIPlayerSerializator.getNodeName());
        aiplayer player = null;
        if (playerNode != null) {
            player = AIPlayerSerializator.deserializeXML(playerNode);
        }
        if ((pNode = node.getNamedChild(MissionPassangerSerializator.getNodeNamePack())) != null) {
            aiplayer pack;
            xmlutils.Node packNode;
            if (player != null) {
                MissionPassanger.getInstance().setNpc(player);
            }
            if ((packNode = pNode.getNamedChild(AIPlayerSerializator.getNodeName())) != null && (pack = AIPlayerSerializator.deserializeXML(packNode)) != null) {
                MissionPassanger.getInstance().setPack(pack);
            }
        } else if (player != null) {
            if (player.gModelname().contains("package")) {
                MissionPassanger.getInstance().setPack(player);
            } else {
                MissionPassanger.getInstance().setNpc(player);
            }
        }
    }
}

