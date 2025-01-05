/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import players.LiveCarCreationController;
import players.actorveh;
import xmlserialization.ActorVehSerializator;
import xmlserialization.Helper;
import xmlutils.Node;

public class LiveCarCreationControllerSerialization {
    public static String getNodeName() {
        return "livecarcreationcontroller";
    }

    public static void serializeXML(LiveCarCreationController value, PrintStream stream) {
        Helper.openNode(stream, LiveCarCreationControllerSerialization.getNodeName());
        actorveh car = value.getLiveCar();
        if (null != car) {
            ActorVehSerializator.serializeXML(car, stream);
        }
        Helper.closeNode(stream, LiveCarCreationControllerSerialization.getNodeName());
    }

    public static LiveCarCreationController deserializeXML(Node node) {
        LiveCarCreationController controller = new LiveCarCreationController();
        Node listLiveCars = node.getNamedChild(ActorVehSerializator.getNodeName());
        if (null != listLiveCars) {
            actorveh car = ActorVehSerializator.deserializeXML(listLiveCars);
            controller.setLiveCar(car);
        }
        return controller;
    }
}

