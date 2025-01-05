/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import org.w3c.dom.Node;
import rnrcore.IScriptRef;
import rnrcore.IXMLSerializable;
import rnrcore.ScriptRefStorage;
import rnrcore.XmlSerializable;
import rnrcore.XmlSerializationFabric;
import xmlserialization.Helper;
import xmlserialization.ListElementSerializator;
import xmlserialization.Log;
import xmlutils.NodeList;

public class ScriptRefXmlSerialization
implements XmlSerializable {
    private static ScriptRefXmlSerialization instance = new ScriptRefXmlSerialization();

    public static ScriptRefXmlSerialization getInstance() {
        return instance;
    }

    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        Map<Integer, IScriptRef> refTable = ScriptRefStorage.getRefferaceTable();
        if (null == refTable || refTable.isEmpty()) {
            return;
        }
        Set<Map.Entry<Integer, IScriptRef>> set = refTable.entrySet();
        ArrayList<IXMLSerializable> setSerialization = new ArrayList<IXMLSerializable>();
        for (Map.Entry<Integer, IScriptRef> entry : set) {
            IXMLSerializable xmlInterface = entry.getValue().getXmlSerializator();
            if (null == xmlInterface) continue;
            setSerialization.add(xmlInterface);
        }
        Helper.openNode(stream, this.getRootNodeName());
        for (IXMLSerializable pair : setSerialization) {
            ListElementSerializator.serializeXMLListelementOpen(stream);
            Helper.openNode(stream, "interface");
            pair.serialize(stream);
            Helper.closeNode(stream, "interface");
            ListElementSerializator.serializeXMLListelementClose(stream);
        }
        Helper.closeNode(stream, this.getRootNodeName());
    }

    public void loadFromNode(Node nodeDom) {
        ScriptRefStorage.clearRefferaceTable();
        xmlutils.Node node = new xmlutils.Node(nodeDom);
        NodeList list = node.getNamedChildren(ListElementSerializator.getNodeName());
        for (xmlutils.Node element : list) {
            xmlutils.Node interfaceNode = element.getNamedChild("interface");
            if (null == interfaceNode) continue;
            xmlutils.Node searchInterfaceNode = interfaceNode.getChild();
            IXMLSerializable serializationInterface = XmlSerializationFabric.getDeSerializationInterface(searchInterfaceNode.getName());
            if (null != serializationInterface) {
                serializationInterface.deSerialize(searchInterfaceNode);
                continue;
            }
            Log.error("ScriptRefXmlSerialization in loadFromNode couldnt find serialization interface for node " + searchInterfaceNode.getName());
        }
    }

    public void yourNodeWasNotFound() {
    }

    public String getRootNodeName() {
        return "scriptref";
    }
}

