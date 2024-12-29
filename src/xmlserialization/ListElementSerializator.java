// Decompiled with: CFR 0.152
// Class Version: 5
package xmlserialization;

import java.io.PrintStream;
import xmlserialization.Helper;

public class ListElementSerializator {
    public static String getNodeName() {
        return "element";
    }

    public static void serializeXMLListelementOpen(PrintStream stream) {
        Helper.openNode(stream, ListElementSerializator.getNodeName());
    }

    public static void serializeXMLListelementClose(PrintStream stream) {
        Helper.closeNode(stream, ListElementSerializator.getNodeName());
    }
}
