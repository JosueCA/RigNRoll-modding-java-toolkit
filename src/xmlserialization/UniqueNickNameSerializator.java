/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import players.NickNamesUniqueName;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlutils.Node;

public class UniqueNickNameSerializator {
    public static String getNodeName() {
        return "uniqnickname";
    }

    public static void serializeXML(NickNamesUniqueName value, PrintStream stream) {
        Helper.printClosedNodeWithAttributes(stream, UniqueNickNameSerializator.getNodeName(), Helper.createSingleAttribute("counter", value.getCount_nick_names()));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static NickNamesUniqueName deserializeXML(Node node) {
        NickNamesUniqueName result = new NickNamesUniqueName();
        String attributCounter = node.getAttribute("counter");
        int attributeValue = 0;
        if (null == attributCounter) {
            Log.error("UniqueNickNameSerializator in deserializeXML has no attribute counter");
        }
        try {
            attributeValue = Integer.parseInt(attributCounter);
        }
        catch (NumberFormatException e) {
            Log.error("UniqueNickNameSerializator in deserializeXML acnnot convert attribute counter with value " + attributCounter + " to integer");
        }
        finally {
            result.setCount_nick_names(attributeValue);
        }
        return result;
    }
}

