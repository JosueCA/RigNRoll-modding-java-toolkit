// Decompiled with: CFR 0.152
// Class Version: 5
package xmlutils;

import java.util.ArrayList;
import java.util.Iterator;
import xmlutils.Node;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class NodeList
extends ArrayList<Node> {
    public static final long serialVersionUID = 1L;

    public NodeList findNamedNodes(String name) {
        Iterator iter = this.iterator();
        NodeList res = new NodeList();
        while (iter.hasNext()) {
            Node nd = (Node)iter.next();
            if (!nd.isName(name)) continue;
            res.add(nd);
        }
        return res;
    }
}
