/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.List;
import players.DriversModelsPool;
import scenarioUtils.Pair;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlutils.Node;

public class NickNameSerializator {
    public static String getNodeName() {
        return "nickname";
    }

    public static void serializeXML(DriversModelsPool.NickName value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("identitie", value.getIdentitie());
        Helper.addAttribute("nickname", value.getNickName(), attributes);
        Helper.printClosedNodeWithAttributes(stream, NickNameSerializator.getNodeName(), attributes);
    }

    public static DriversModelsPool.NickName deserializeXML(Node node) {
        String nickName = node.getAttribute("nickname");
        String identitie = node.getAttribute("identitie");
        if (null == nickName) {
            Log.error("NickNameSerializator in deserializeXML has no attribute nickname");
        }
        if (null == identitie) {
            Log.error("NickNameSerializator in deserializeXML has no attribute identitie");
        }
        return new DriversModelsPool.NickName(nickName, identitie);
    }
}

