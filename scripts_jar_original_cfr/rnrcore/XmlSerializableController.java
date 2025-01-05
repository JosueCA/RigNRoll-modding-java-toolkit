/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.w3c.dom.Node;
import rnrcore.IXMLSerializable;
import rnrcore.ObjectXmlSerailizatorId;
import rnrcore.ObjectXmlSerializable;
import rnrcore.XmlSerializationFabric;
import rnrcore.eng;
import rnrscenario.consistency.ScenarioGarbageFinder;
import rnrscenario.consistency.ScenarioStage;
import rnrscenario.consistency.StageChangedListener;
import xmlutils.NodeList;

public class XmlSerializableController
implements StageChangedListener {
    private HashMap<ObjectXmlSerailizatorId, ObjectXmlSerializable> objectToSerialize = new HashMap();

    public void clearSerializators() {
        this.objectToSerialize.clear();
    }

    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        Set<Map.Entry<ObjectXmlSerailizatorId, ObjectXmlSerializable>> setObjectSerializators = this.objectToSerialize.entrySet();
        for (Map.Entry<ObjectXmlSerailizatorId, ObjectXmlSerializable> entry : setObjectSerializators) {
            ObjectXmlSerializable object = entry.getValue();
            IXMLSerializable serializator = object.getXmlSerializator();
            serializator.serialize(stream);
        }
    }

    public void loadFromNode(Node node) {
        if (null == node) {
            return;
        }
        xmlutils.Node utilNode = new xmlutils.Node(node);
        NodeList children = utilNode.getChildren();
        HashMap<String, Boolean> nodesWereDeserialized = new HashMap<String, Boolean>();
        for (xmlutils.Node serializationNode : children) {
            String nodeName = serializationNode.getName();
            if (nodesWereDeserialized.containsKey(nodeName)) continue;
            nodesWereDeserialized.put(nodeName, true);
            IXMLSerializable serializationInterface = XmlSerializationFabric.getDeSerializationInterface(serializationNode.getName());
            if (null == serializationInterface) continue;
            serializationInterface.deSerialize(serializationNode);
        }
    }

    public void registerObject(ObjectXmlSerializable object) {
        if (this.objectToSerialize.containsKey((Object)object.getSerializationId())) {
            eng.log("Execution ERRORR. ObjectXmlSerializator recieves registered object with class name " + object.getClass().getName() + " .");
            return;
        }
        this.objectToSerialize.put(object.getSerializationId(), object);
    }

    public void unRegisterObject(ObjectXmlSerializable object) {
        this.objectToSerialize.remove((Object)object.getSerializationId());
    }

    public void scenarioCheckPointReached(ScenarioStage scenarioStage) {
        ScenarioGarbageFinder.deleteOutOfDateScenarioObjects(this.getClass().getName(), this.objectToSerialize.values(), scenarioStage);
    }
}

