package rickroll.core;

//Decompiled with: CFR 0.152
//Class Version: 5

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class XmlFilter {
 private NodeList nodes = null;
 private int currentIndex = 0;

 public XmlFilter(NodeList nodeList) {
     if (null == nodeList) {
         throw new IllegalArgumentException("nodes must be non-null reference");
     }
     this.nodes = nodeList;
 }

 private Node filterNoNodeTypeNext(int n) {
     while (this.currentIndex < this.nodes.getLength() && n == this.nodes.item(this.currentIndex).getNodeType()) {
         ++this.currentIndex;
     }
     if (this.currentIndex < this.nodes.getLength()) {
         return this.nodes.item(this.currentIndex);
     }
     return null;
 }

 private Node nodeTypeNext(int n) {
     while (this.currentIndex < this.nodes.getLength()) {
         if (n == this.nodes.item(this.currentIndex).getNodeType()) {
             ++this.currentIndex;
             return this.nodes.item(this.currentIndex - 1);
         }
         ++this.currentIndex;
     }
     return null;
 }

 public void reset(NodeList nodeList) {
     if (null == nodeList) {
         throw new IllegalArgumentException("nodes must be non-null reference");
     }
     this.nodes = nodeList;
     this.currentIndex = 0;
 }

 public XmlFilter goOnStart() {
     this.currentIndex = 0;
     return this;
 }

 public Node nodeNameNext(String string) {
     if (null == string) {
         throw new IllegalArgumentException("name must be non-null");
     }
     while (this.currentIndex < this.nodes.getLength()) {
         String string2 = this.nodes.item(this.currentIndex).getNodeName();
         if (0 == string2.compareToIgnoreCase(string)) {
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

 public static boolean textContentExists(Node node) {
     return null != node && null != node.getTextContent() && 0 < node.getTextContent().length();
 }
}

