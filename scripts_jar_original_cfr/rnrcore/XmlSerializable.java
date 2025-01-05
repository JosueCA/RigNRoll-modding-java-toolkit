/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import java.io.PrintStream;
import org.w3c.dom.Node;

public interface XmlSerializable {
    public void saveToStreamAsSetOfXmlNodes(PrintStream var1);

    public void loadFromNode(Node var1);

    public void yourNodeWasNotFound();

    public String getRootNodeName();
}

