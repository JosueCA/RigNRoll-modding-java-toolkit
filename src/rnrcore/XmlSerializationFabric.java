/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import java.util.HashMap;
import rnrcore.IXMLSerializable;

public class XmlSerializationFabric {
    private static HashMap<String, IXMLSerializable> deserializators = new HashMap();

    public static void addRegisterDeSerializationInterface(String nodeName, IXMLSerializable serializationInterface) {
        deserializators.put(nodeName, serializationInterface);
    }

    public static IXMLSerializable getDeSerializationInterface(String nodeName) {
        return deserializators.get(nodeName);
    }
}

