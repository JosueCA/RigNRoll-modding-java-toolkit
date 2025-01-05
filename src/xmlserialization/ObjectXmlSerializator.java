/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import org.w3c.dom.Node;
import rnrcore.ObjectXmlSerializable;
import rnrcore.XmlSerializable;
import rnrcore.XmlSerializableController;
import xmlserialization.Helper;

public class ObjectXmlSerializator
implements XmlSerializable {
    private static ObjectXmlSerializator instance = new ObjectXmlSerializator();
    private XmlSerializableController controller = new XmlSerializableController();

    public static ObjectXmlSerializator getInstance() {
        return instance;
    }

    public String getRootNodeName() {
        return ObjectXmlSerializator.getNodeName();
    }

    public void loadFromNode(Node node) {
        this.controller.loadFromNode(node);
    }

    public void yourNodeWasNotFound() {
    }

    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        ObjectXmlSerializator.serializeXML(this.controller, stream);
    }

    public static String getNodeName() {
        return "objectserializator";
    }

    public static void serializeXML(XmlSerializableController value, PrintStream stream) {
        Helper.openNode(stream, ObjectXmlSerializator.getNodeName());
        value.saveToStreamAsSetOfXmlNodes(stream);
        Helper.closeNode(stream, ObjectXmlSerializator.getNodeName());
    }

    public void registerObject(ObjectXmlSerializable target) {
        this.controller.registerObject(target);
    }

    public void unRegisterObject(ObjectXmlSerializable target) {
        this.controller.unRegisterObject(target);
    }

    public void cleanUp() {
        this.controller.clearSerializators();
    }
}

