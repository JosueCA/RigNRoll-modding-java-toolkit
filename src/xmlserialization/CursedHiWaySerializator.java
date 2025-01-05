/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.List;
import rnrcore.IXMLSerializable;
import rnrcore.vectorJ;
import rnrscenario.CursedHiWay;
import scenarioUtils.Pair;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlserialization.Vector3dSerializator;
import xmlutils.Node;

public class CursedHiWaySerializator
implements IXMLSerializable {
    CursedHiWay m_object = null;

    public CursedHiWaySerializator(CursedHiWay value) {
        this.m_object = value;
    }

    public CursedHiWaySerializator() {
        this.m_object = null;
    }

    public static String getNodeName() {
        return "cursedhiway";
    }

    public static void serializeXML(CursedHiWay value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("dead_from_mist", value.isDead_from_mist());
        Helper.addAttribute("last_coef", value.getLast_coef(), attributes);
        Helper.addAttribute("on_animation", value.isOn_animation(), attributes);
        Helper.addAttribute("release_animation", value.isRelease_animation(), attributes);
        Helper.addAttribute("stop_animation", value.isStop_animation(), attributes);
        Helper.printOpenNodeWithAttributes(stream, CursedHiWaySerializator.getNodeName(), attributes);
        vectorJ posInfluence = value.getPositionInfluenceZone();
        if (null != posInfluence) {
            Helper.openNode(stream, "positionInfluenceZone");
            Vector3dSerializator.serializeXML(posInfluence, stream);
            Helper.closeNode(stream, "positionInfluenceZone");
        }
        Helper.closeNode(stream, CursedHiWaySerializator.getNodeName());
    }

    public static void deserializeXML(Node node) {
        String errorMessage = "CursedHiWaySerializator in deserializeXML ";
        String deadFromMistString = node.getAttribute("dead_from_mist");
        String lastCoefString = node.getAttribute("last_coef");
        String onAnimationString = node.getAttribute("on_animation");
        String releaseAnimationString = node.getAttribute("release_animation");
        String stopAnimationString = node.getAttribute("stop_animation");
        boolean deadFromMistValue = Helper.ConvertToBooleanAndWarn(deadFromMistString, "dead_from_mist", errorMessage);
        double lastCoefValue = Helper.ConvertToDoubleAndWarn(lastCoefString, "last_coef", errorMessage);
        boolean onAnimationValue = Helper.ConvertToBooleanAndWarn(onAnimationString, "on_animation", errorMessage);
        boolean releaseAnimationValue = Helper.ConvertToBooleanAndWarn(releaseAnimationString, "release_animation", errorMessage);
        boolean startAnimationValue = Helper.ConvertToBooleanAndWarn(stopAnimationString, "stop_animation", errorMessage);
        Node positionInfluenceNode = node.getNamedChild("positionInfluenceZone");
        vectorJ posInfluence = null;
        if (null != positionInfluenceNode) {
            Node vectorNode = positionInfluenceNode.getNamedChild(Vector3dSerializator.getNodeName());
            if (null == vectorNode) {
                Log.error(errorMessage + " has no child node named " + Vector3dSerializator.getNodeName() + " in node named " + "positionInfluenceZone");
            } else {
                posInfluence = Vector3dSerializator.deserializeXML(vectorNode);
            }
        }
        CursedHiWay result = new CursedHiWay();
        result.setDead_from_mist(deadFromMistValue);
        result.setLast_coef(lastCoefValue);
        result.setOn_animation(onAnimationValue);
        result.setPositionInfluenceZone(posInfluence);
        result.setRelease_animation(releaseAnimationValue);
        result.setStop_animation(startAnimationValue);
    }

    public void deSerialize(Node node) {
        CursedHiWaySerializator.deserializeXML(node);
    }

    public void serialize(PrintStream stream) {
        CursedHiWaySerializator.serializeXML(this.m_object, stream);
    }
}

