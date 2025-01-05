/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.List;
import players.CrewNamesManager;
import players.ImodelCreate;
import players.aiplayer;
import rnrcore.IXMLSerializable;
import scenarioUtils.Pair;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlutils.Node;

public class AIPlayerSerializator
implements IXMLSerializable {
    private aiplayer m_player;

    public AIPlayerSerializator(aiplayer player) {
        this.m_player = player;
    }

    public AIPlayerSerializator() {
        this.m_player = null;
    }

    public static String getNodeName() {
        return "aiplayer";
    }

    public static void serializeXML(aiplayer value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("identitie", value.getIdentitie());
        Helper.addAttribute("uid", value.getUid(), attributes);
        Helper.addAttribute("modelbasedmodel", value.getModelBased(), attributes);
        Helper.addAttribute("poolbasedmodel", value.getPoolBased(), attributes);
        Helper.addAttribute("poolref_name", value.getPoolRefName(), attributes);
        Helper.addAttribute("idForModelCreator", value.getIdForModelCreator(), attributes);
        Helper.printOpenNodeWithAttributes(stream, AIPlayerSerializator.getNodeName(), attributes);
        ImodelCreate creator = value.getModelCreator();
        if (creator != null) {
            Class<?> classCreator = creator.getClass();
            String className = classCreator.getName();
            Helper.printClosedNodeWithAttributes(stream, "modelcreator", Helper.createSingleAttribute("classname", className));
        }
        Helper.closeNode(stream, AIPlayerSerializator.getNodeName());
    }

    public static aiplayer deserializeXML(Node node) {
        String className;
        Node modelCreatorNode;
        String uid = node.getAttribute("uid");
        String identitie = node.getAttribute("identitie");
        int integerUid = Integer.parseInt(uid);
        aiplayer result = aiplayer.createScriptRef(identitie, integerUid);
        String modelBased = node.getAttribute("modelbasedmodel");
        String poolBased = node.getAttribute("poolbasedmodel");
        String poolRefName = node.getAttribute("poolref_name");
        String idForModelCreation = node.getAttribute("idForModelCreator");
        if (poolBased != null && Boolean.parseBoolean(poolBased)) {
            result.sPoolBased(poolRefName);
        }
        if (modelBased != null) {
            result.sModelBased(Boolean.parseBoolean(modelBased));
        }
        if (null != (modelCreatorNode = node.getNamedChild("modelcreator")) && null != (className = modelCreatorNode.getAttribute("classname"))) {
            try {
                Class<?> modelCreatorClass = Class.forName(className);
                if (null != modelCreatorClass) {
                    Object modelCreator = modelCreatorClass.newInstance();
                    result.setModelCreator((ImodelCreate)modelCreator, idForModelCreation);
                }
            }
            catch (Exception e) {
                Log.error("Exception occured of deserializeXML of AIPlayerSerializator. Message: " + e.toString());
            }
        }
        if ("SC_NICK".equals(identitie)) {
            CrewNamesManager.mainCharacterLoaded(result);
        }
        return result;
    }

    public void deSerialize(Node node) {
        AIPlayerSerializator.deserializeXML(node);
    }

    public void serialize(PrintStream stream) {
        AIPlayerSerializator.serializeXML(this.m_player, stream);
    }
}

