/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.ArrayList;
import org.w3c.dom.Node;
import rnrcore.XmlSerializable;
import rnrscr.cSpecObjects;
import rnrscr.specobjects;
import xmlserialization.Helper;
import xmlserialization.SpecObjectSerialization;
import xmlutils.NodeList;

public class SoSerialization
implements XmlSerializable {
    private static SoSerialization instance = new SoSerialization();

    public static SoSerialization getInstance() {
        return instance;
    }

    public String getRootNodeName() {
        return SoSerialization.getNodeName();
    }

    public void loadFromNode(Node node) {
        SoSerialization.deserializeXML(new xmlutils.Node(node));
    }

    public void yourNodeWasNotFound() {
    }

    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        SoSerialization.serializeXML(specobjects.getInstance(), stream);
    }

    public static String getNodeName() {
        return "so";
    }

    public static void serializeXML(specobjects value, PrintStream stream) {
        Helper.openNode(stream, SoSerialization.getNodeName());
        ArrayList<cSpecObjects> cachedObjects = value.getAllLoadedObjCash();
        if (null != cachedObjects) {
            Helper.openNode(stream, "socach");
            for (cSpecObjects object : cachedObjects) {
                SpecObjectSerialization.serializeXML(object, stream);
            }
            Helper.closeNode(stream, "socach");
        }
        Helper.closeNode(stream, SoSerialization.getNodeName());
    }

    public static void deserializeXML(xmlutils.Node node) {
        xmlutils.Node cachSpecobjectsNode = node.getNamedChild("socach");
        if (null != cachSpecobjectsNode) {
            NodeList specObjectsList = cachSpecobjectsNode.getNamedChildren(SpecObjectSerialization.getNodeName());
            ArrayList<cSpecObjects> cachedObjects = new ArrayList<cSpecObjects>();
            for (xmlutils.Node specobject : specObjectsList) {
                cSpecObjects newObject = SpecObjectSerialization.deserializeXML(specobject);
                cachedObjects.add(newObject);
            }
            specobjects.getInstance().setAllLoadedObjCash(cachedObjects);
        }
    }
}

