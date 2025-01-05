/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.ArrayList;
import players.DriversModelsPool;
import players.NickNamesUniqueName;
import rnrconfig.GetTruckersIdentities;
import xmlserialization.Helper;
import xmlserialization.NickNameSerializator;
import xmlserialization.SimpleTypeSerializator;
import xmlserialization.UniqueNickNameSerializator;
import xmlutils.Node;
import xmlutils.NodeList;

public class ModelPoolSerializator {
    public static String getNodeName() {
        return "driversmodelspool";
    }

    public static void serializeXML(DriversModelsPool value, PrintStream stream) {
        Helper.openNode(stream, ModelPoolSerializator.getNodeName());
        ArrayList<String> cycleModels = value.getCycleListModelNames();
        Helper.openNode(stream, "CycleListModelNames");
        for (String item : cycleModels) {
            SimpleTypeSerializator.serializeXMLString(item, stream);
        }
        Helper.closeNode(stream, "CycleListModelNames");
        ArrayList<String> poolModels = value.getPool();
        Helper.openNode(stream, "pool");
        for (String item : poolModels) {
            SimpleTypeSerializator.serializeXMLString(item, stream);
        }
        Helper.closeNode(stream, "pool");
        ArrayList<String> exposingModels = value.getExposing();
        Helper.openNode(stream, "exposing");
        for (String item : exposingModels) {
            SimpleTypeSerializator.serializeXMLString(item, stream);
        }
        Helper.closeNode(stream, "exposing");
        ArrayList<DriversModelsPool.NickName> freeModels = value.getFreeNickNames();
        Helper.openNode(stream, "freenicknames");
        for (DriversModelsPool.NickName item : freeModels) {
            NickNameSerializator.serializeXML(item, stream);
        }
        Helper.closeNode(stream, "freenicknames");
        ArrayList<DriversModelsPool.NickName> bussyModels = value.getBussyNickNames();
        Helper.openNode(stream, "bussynicknames");
        for (DriversModelsPool.NickName item : bussyModels) {
            NickNameSerializator.serializeXML(item, stream);
        }
        Helper.closeNode(stream, "bussynicknames");
        NickNamesUniqueName uniqueNamer = value.getUniqueName();
        UniqueNickNameSerializator.serializeXML(uniqueNamer, stream);
        Helper.closeNode(stream, ModelPoolSerializator.getNodeName());
    }

    public static DriversModelsPool deserializeXML(Node node) {
        GetTruckersIdentities identities = new GetTruckersIdentities();
        DriversModelsPool result = DriversModelsPool.create(identities.get());
        Node cycleList = node.getNamedChild("CycleListModelNames");
        NodeList strings = cycleList.getNamedChildren("string");
        ArrayList<String> cycleModels = new ArrayList<String>();
        for (Node stringnode : strings) {
            cycleModels.add(SimpleTypeSerializator.deserializeXMLString(stringnode));
        }
        result.setCycleListModelNames(cycleModels);
        Node poolList = node.getNamedChild("pool");
        NodeList strings2 = poolList.getNamedChildren("string");
        ArrayList<String> poolModels = new ArrayList<String>();
        for (Node stringnode : strings2) {
            poolModels.add(SimpleTypeSerializator.deserializeXMLString(stringnode));
        }
        result.setPool(poolModels);
        Node exposingList = node.getNamedChild("exposing");
        NodeList strings3 = exposingList.getNamedChildren("string");
        ArrayList<String> exposingModels = new ArrayList<String>();
        for (Node stringnode : strings3) {
            exposingModels.add(SimpleTypeSerializator.deserializeXMLString(stringnode));
        }
        result.setExposing(exposingModels);
        Node freeList = node.getNamedChild("freenicknames");
        NodeList nicknamesList = freeList.getNamedChildren(NickNameSerializator.getNodeName());
        ArrayList<DriversModelsPool.NickName> freeModels = new ArrayList<DriversModelsPool.NickName>();
        for (Node nickNameNode : nicknamesList) {
            freeModels.add(NickNameSerializator.deserializeXML(nickNameNode));
        }
        result.setFreeNickNames(freeModels);
        Node bussyList = node.getNamedChild("bussynicknames");
        NodeList nicknamesList2 = bussyList.getNamedChildren(NickNameSerializator.getNodeName());
        ArrayList<DriversModelsPool.NickName> bussyModels = new ArrayList<DriversModelsPool.NickName>();
        for (Node nickNameNode : nicknamesList2) {
            bussyModels.add(NickNameSerializator.deserializeXML(nickNameNode));
        }
        result.setBussyNickNames(bussyModels);
        Node uniqueNameNode = node.getNamedChild(UniqueNickNameSerializator.getNodeName());
        result.setUniqueName(UniqueNickNameSerializator.deserializeXML(uniqueNameNode));
        return result;
    }
}

