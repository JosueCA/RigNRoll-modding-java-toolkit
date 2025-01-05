/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import java.io.PrintStream;
import xmlutils.Node;

public interface IXMLSerializable {
    public void serialize(PrintStream var1);

    public void deSerialize(Node var1);
}

