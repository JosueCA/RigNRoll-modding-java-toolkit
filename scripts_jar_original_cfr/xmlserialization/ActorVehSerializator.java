/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import players.actorveh;
import rnrcore.IXMLSerializable;
import xmlserialization.Helper;
import xmlutils.Node;

public class ActorVehSerializator
implements IXMLSerializable {
    private actorveh m_car;

    public ActorVehSerializator(actorveh car) {
        this.m_car = car;
    }

    public ActorVehSerializator() {
        this.m_car = null;
    }

    public static String getNodeName() {
        return "actorveh";
    }

    public static void serializeXML(actorveh car, PrintStream stream) {
        if (car == null) {
            Helper.printClosedNodeWithAttributes(stream, ActorVehSerializator.getNodeName(), null);
        } else {
            Helper.printClosedNodeWithAttributes(stream, "actorveh", Helper.createSingleAttribute("uid", car.getUid()));
        }
    }

    public static actorveh deserializeXML(Node node) {
        String value = node.getAttribute("uid");
        if (null == value) {
            return null;
        }
        return actorveh.createScriptRef(Integer.parseInt(value));
    }

    public void deSerialize(Node node) {
        ActorVehSerializator.deserializeXML(node);
    }

    public void serialize(PrintStream stream) {
        ActorVehSerializator.serializeXML(this.m_car, stream);
    }
}

