/*
 * Decompiled with CFR 0.151.
 */
package scenarioXml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import scenarioXml.XmlNodeDataProcessor;

public final class XmlFilter {
    private NodeList nodes = null;
    private int currentIndex = 0;

    public XmlFilter(NodeList nodes) {
        if (null == nodes) {
            throw new IllegalArgumentException("nodes must be non-null reference");
        }
        this.nodes = nodes;
    }

    private Node filterNoNodeTypeNext(int nodeTypeToFilter) {
        while (this.currentIndex < this.nodes.getLength() && nodeTypeToFilter == this.nodes.item(this.currentIndex).getNodeType()) {
            ++this.currentIndex;
        }
        if (this.currentIndex < this.nodes.getLength()) {
            return this.nodes.item(this.currentIndex);
        }
        return null;
    }

    private Node nodeTypeNext(int nodeTypeToFind) {
        while (this.currentIndex < this.nodes.getLength()) {
            if (nodeTypeToFind == this.nodes.item(this.currentIndex).getNodeType()) {
                ++this.currentIndex;
                return this.nodes.item(this.currentIndex - 1);
            }
            ++this.currentIndex;
        }
        return null;
    }

    public void reset(NodeList nodes) {
        if (null == nodes) {
            throw new IllegalArgumentException("nodes must be non-null reference");
        }
        this.nodes = nodes;
        this.currentIndex = 0;
    }

    public XmlFilter goOnStart() {
        this.currentIndex = 0;
        return this;
    }

    public Node nodeNameNext(String name) {
        if (null == name) {
            throw new IllegalArgumentException("name must be non-null");
        }
        while (this.currentIndex < this.nodes.getLength()) {
            String name_item = this.nodes.item(this.currentIndex).getNodeName();
            if (0 == name_item.compareToIgnoreCase(name)) {
                ++this.currentIndex;
                return this.nodes.item(this.currentIndex - 1);
            }
            ++this.currentIndex;
        }
        return null;
    }

    public Node filterNoCommentsNext() {
        return this.filterNoNodeTypeNext(8);
    }

    public Node filterNoTextNext() {
        return this.filterNoNodeTypeNext(3);
    }

    public Node nextElement() {
        return this.nodeTypeNext(1);
    }

    public void visitAllNodes(String name, XmlNodeDataProcessor dataProcessor, Object param) {
        this.goOnStart();
        Node current = this.nodeNameNext(name);
        while (null != current) {
            dataProcessor.process(current, param);
            current = this.nodeNameNext(name);
        }
    }

    public static boolean textContentExists(Node where) {
        return null != where && null != where.getTextContent() && 0 < where.getTextContent().length();
    }
}

