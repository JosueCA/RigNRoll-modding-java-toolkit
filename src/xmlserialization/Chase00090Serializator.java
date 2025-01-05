/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import players.aiplayer;
import rnrcore.IXMLSerializable;
import rnrcore.eng;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.ScenarioHost;
import rnrscenario.controllers.chase00090;
import xmlserialization.AIPlayerSerializator;
import xmlserialization.Helper;
import xmlserialization.nxs.SerializatorOfAnnotated;
import xmlutils.Node;

@ScenarioClass(scenarioStage=1)
public class Chase00090Serializator
implements IXMLSerializable {
    private chase00090 serializationTarget = null;
    private static ScenarioHost host;

    public static void setHost(ScenarioHost host) {
        Chase00090Serializator.host = host;
    }

    public Chase00090Serializator(chase00090 value) {
        this.serializationTarget = value;
    }

    public Chase00090Serializator() {
        this.serializationTarget = null;
    }

    public static String getNodeName() {
        return "chase00090";
    }

    public static void serializeXML(chase00090 value, PrintStream stream) {
        Helper.openNode(stream, Chase00090Serializator.getNodeName());
        SerializatorOfAnnotated.getInstance().saveState(stream, value);
        aiplayer dorothy = chase00090.getDorothy();
        if (null != dorothy) {
            Helper.openNode(stream, "Dorothy");
            AIPlayerSerializator.serializeXML(dorothy, stream);
            Helper.closeNode(stream, "Dorothy");
        }
        Helper.closeNode(stream, Chase00090Serializator.getNodeName());
    }

    public static void deserializeXML(Node node) {
        chase00090 ware;
        Node itemNode;
        aiplayer dorothy = null;
        Node dorothyNode = node.getNamedChild("Dorothy");
        if (null != dorothyNode && null != (itemNode = dorothyNode.getNamedChild(AIPlayerSerializator.getNodeName()))) {
            dorothy = AIPlayerSerializator.deserializeXML(itemNode);
        }
        if (null == (ware = (chase00090)SerializatorOfAnnotated.getInstance().loadStateOrNull(node.getNamedChild("chase00090")))) {
            eng.err("Error while loading saved game: failed to load instance of chase00090");
        } else {
            ware.setHost(host);
        }
        chase00090.setDorothy(dorothy);
    }

    public void deSerialize(Node node) {
        Chase00090Serializator.deserializeXML(node);
    }

    public void serialize(PrintStream stream) {
        Chase00090Serializator.serializeXML(this.serializationTarget, stream);
    }
}

