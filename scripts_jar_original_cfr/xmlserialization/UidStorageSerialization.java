/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import org.w3c.dom.Node;
import rnrcore.UidStorage;
import rnrcore.XmlSerializable;
import xmlserialization.Helper;
import xmlserialization.SimpleTypeSerializator;
import xmlutils.NodeList;

public class UidStorageSerialization
implements XmlSerializable {
    private static UidStorageSerialization instance = new UidStorageSerialization();

    public static UidStorageSerialization getInstance() {
        return instance;
    }

    public String getRootNodeName() {
        return UidStorageSerialization.getNodeName();
    }

    public void loadFromNode(Node node) {
        UidStorageSerialization.deserializeXML(new xmlutils.Node(node));
    }

    public void yourNodeWasNotFound() {
    }

    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        UidStorageSerialization.serializeXML(UidStorage.getInstance(), stream);
    }

    public static String getNodeName() {
        return "uidstorage";
    }

    public static void serializeXML(UidStorage value, PrintStream stream) {
        Helper.printOpenNodeWithAttributes(stream, UidStorageSerialization.getNodeName(), Helper.createSingleAttribute("maxcreated", value.getMaxCreatedUid()));
        List<Integer> uids = value.getUids();
        for (Integer uidValue : uids) {
            SimpleTypeSerializator.serializeXMLInteger(uidValue, stream);
        }
        Helper.closeNode(stream, UidStorageSerialization.getNodeName());
    }

    public static void deserializeXML(xmlutils.Node node) {
        UidStorage value = UidStorage.getInstance();
        String maxCreatedString = node.getAttribute("maxcreated");
        int maxCreatedValue = Helper.ConvertToIntegerAndWarn(maxCreatedString, "maxcreated", "UidStorageSerialization on deserializeXML ");
        NodeList listUidsNode = node.getNamedChildren(SimpleTypeSerializator.getNodeNameInteger());
        LinkedList<Integer> listUids = new LinkedList<Integer>();
        if (null != listUidsNode) {
            for (xmlutils.Node uidValue : listUidsNode) {
                listUids.add(SimpleTypeSerializator.deserializeXMLInteger(uidValue));
            }
        }
        value.setUids(listUids);
        value.setMaxCreatedUid(maxCreatedValue);
    }
}

