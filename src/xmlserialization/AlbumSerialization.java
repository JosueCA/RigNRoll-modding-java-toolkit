/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.ArrayList;
import org.w3c.dom.Node;
import rnrcore.XmlSerializable;
import rnrorg.Album;
import xmlserialization.Helper;
import xmlutils.NodeList;

public class AlbumSerialization
implements XmlSerializable {
    private static AlbumSerialization instance = new AlbumSerialization();

    public static AlbumSerialization getInstance() {
        return instance;
    }

    public String getRootNodeName() {
        return AlbumSerialization.getNodeName();
    }

    public void loadFromNode(Node node) {
        AlbumSerialization.deserializeXML(new xmlutils.Node(node));
    }

    public void yourNodeWasNotFound() {
    }

    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        AlbumSerialization.serializeXML(stream);
    }

    public static String getNodeName() {
        return "album";
    }

    public static void serializeXML(PrintStream stream) {
        Helper.openNode(stream, AlbumSerialization.getNodeName());
        ArrayList<Album.Item> elements = Album.getInstance().getAll();
        for (Album.Item element : elements) {
            element.serializeXML(stream);
        }
        Helper.closeNode(stream, AlbumSerialization.getNodeName());
    }

    public static void deserializeXML(xmlutils.Node node) {
        NodeList albumElements = node.getNamedChildren(Album.getNodeName());
        ArrayList<Album.Item> elements = new ArrayList<Album.Item>();
        if (null != albumElements && !albumElements.isEmpty()) {
            for (xmlutils.Node element : albumElements) {
                Album.Item it = Album.Item.deserializeXML(element);
                elements.add(it);
            }
        }
        Album.getInstance().setAll(elements);
    }
}

