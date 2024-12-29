// Decompiled with: CFR 0.152
// Class Version: 5
package xmlutils;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.NamedNodeMap;
import scenarioUtils.Pair;
import xmlutils.NodeList;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Node {
    private org.w3c.dom.Node node = null;

    public Node(org.w3c.dom.Node node) {
        this.node = node;
    }

    public org.w3c.dom.Node getNode() {
        return this.node;
    }

    public final String getName() {
        return this.node.getNodeName();
    }

    public final boolean isName(String name) {
        return 0 == this.node.getNodeName().compareTo(name);
    }

    private Node getFirstElement(NodeList res) {
        if (res.isEmpty()) {
            return null;
        }
        return (Node)res.get(0);
    }

    public final String getValue() {
        org.w3c.dom.NodeList list = this.node.getChildNodes();
        for (int i = 0; i < list.getLength(); ++i) {
            if (list.item(i).getNodeName().compareTo("#text") != 0) continue;
            return list.item(i).getNodeValue();
        }
        return null;
    }

    public final String getAttribute(String attrinuteName) {
        NamedNodeMap map = this.node.getAttributes();
        org.w3c.dom.Node nd = map.getNamedItem(attrinuteName);
        if (null == nd) {
            return null;
        }
        return nd.getNodeValue();
    }

    public final List<Pair<String, String>> getAllAttributes() {
        NamedNodeMap map = this.node.getAttributes();
        ArrayList<Pair<String, String>> result = new ArrayList<Pair<String, String>>();
        for (int i = 0; i < map.getLength(); ++i) {
            org.w3c.dom.Node nd = map.item(i);
            result.add(new Pair<String, String>(nd.getNodeName(), nd.getNodeValue()));
        }
        return result;
    }

    public final boolean hasAttribute(String attrinuteName) {
        NamedNodeMap map = this.node.getAttributes();
        return null != map.getNamedItem(attrinuteName);
    }

    public final NodeList getChildren() {
        NodeList res = new NodeList();
        org.w3c.dom.NodeList list = this.node.getChildNodes();
        for (int i = 0; i < list.getLength(); ++i) {
            String check = list.item(i).getNodeName();
            if (check.compareTo("#text") == 0) continue;
            res.add(new Node(list.item(i)));
        }
        return res;
    }

    public final Node getChild() {
        NodeList res = this.getChildren();
        return this.getFirstElement(res);
    }

    public final NodeList getNamedChildren(String name) {
        NodeList res = new NodeList();
        org.w3c.dom.NodeList list = this.node.getChildNodes();
        for (int i = 0; i < list.getLength(); ++i) {
            Node nd = new Node(list.item(i));
            if (!nd.isName(name)) continue;
            res.add(nd);
        }
        return res;
    }

    public final Node getNamedChild(String name) {
        NodeList res = this.getNamedChildren(name);
        return this.getFirstElement(res);
    }
}
