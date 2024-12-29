// Decompiled with: CFR 0.152
// Class Version: 5
package xmlserialization;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.w3c.dom.Node;

import rickroll.log.RickRollLog;
import rnrcore.XmlSerializable;
import rnrorg.IStoreorgelement;
import rnrorg.MissionOrganiser;
import rnrorg.Organizers;
import rnrorg.Scorgelement;
import rnrorg.WarehouseOrder;
import rnrorg.organaiser;
import scenarioUtils.Pair;
import xmlserialization.Helper;
import xmlserialization.ListElementSerializator;
import xmlserialization.Log;
import xmlserialization.OrganizerNodeSerialization;
import xmlserialization.WarehouseOrganizerNodeSerialization;
import xmlutils.NodeList;

public class OrganizerSerialization
implements XmlSerializable {
    private static OrganizerSerialization instance = new OrganizerSerialization();

    public static OrganizerSerialization getInstance() {
        return instance;
    }

    public String getRootNodeName() {
        return OrganizerSerialization.getNodeName();
    }

    public void loadFromNode(Node node) {
        OrganizerSerialization.deserializeXML(new xmlutils.Node(node));
    }

    public void yourNodeWasNotFound() {
    }

    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        OrganizerSerialization.serializeXML(stream);
    }

    public static String getNodeName() {
        return "organiser";
    }

    public static void serializeXML(PrintStream stream) {
        List<Pair<String, String>> attributesMain = Helper.createSingleAttribute("numwarehouseorders", WarehouseOrder.getCountNumOrder());
        Helper.printOpenNodeWithAttributes(stream, OrganizerSerialization.getNodeName(), attributesMain);
        Helper.openNode(stream, "orgelements");
        HashMap<String, IStoreorgelement> specialElements = Organizers.getInstance().getOrganizerElementsSpecial();
        if (!specialElements.isEmpty()) {
            Set<Map.Entry<String, IStoreorgelement>> set = specialElements.entrySet();
            for (Map.Entry<String, IStoreorgelement> entry : set) {
                if (!(entry.getValue() instanceof Scorgelement)) continue;
                Helper.printOpenNodeWithAttributes(stream, "element", Helper.createSingleAttribute("name", entry.getKey()));
                OrganizerNodeSerialization.serializeXML((Scorgelement)entry.getValue(), stream);
                Helper.closeNode(stream, "element");
            }
        }
        Helper.closeNode(stream, "orgelements");
        Helper.openNode(stream, "items");
        Vector<IStoreorgelement> orgelements = organaiser.getInstance().getAllorgelements();
        IStoreorgelement currentElement = organaiser.getCurrent();
        IStoreorgelement currentWarehouseElement = organaiser.getInstance().getCurrentWarehouseOrder();
        if (null != orgelements && !orgelements.isEmpty()) {
            for (IStoreorgelement element : orgelements) {
                boolean isCurrent = currentElement != null && currentElement.equals(element);
                ListElementSerializator.serializeXMLListelementOpen(stream);
                if (element instanceof Scorgelement) {
                    OrganizerNodeSerialization.serializeXML((Scorgelement)element, stream);
                } else if (element instanceof WarehouseOrder) {
                    boolean isCurrentWarehouseOrder;
                    WarehouseOrganizerNodeSerialization.serializeXML((WarehouseOrder)element, stream);
                    boolean bl = isCurrentWarehouseOrder = currentWarehouseElement != null && currentWarehouseElement.equals(element);
                    if (isCurrentWarehouseOrder) {
                        Helper.printClosedNodeWithAttributes(stream, "current_warehouse_order", null);
                    }
                }
                if (isCurrent) {
                    Helper.printClosedNodeWithAttributes(stream, "current", null);
                }
                ListElementSerializator.serializeXMLListelementClose(stream);
            }
        }
        Helper.closeNode(stream, "items");
        Helper.openNode(stream, "missionitems");
        HashMap<String, String> missions = MissionOrganiser.getInstance().getMissions();
        if (null != missions && !missions.isEmpty()) {
            Set<Map.Entry<String, String>> set = missions.entrySet();
            for (Map.Entry<String, String> entry : set) {
                String missionName = entry.getKey();
                String organizerName = entry.getValue();
                List<Pair<String, String>> attributes = Helper.createSingleAttribute("missionname", missionName);
                Helper.addAttribute("orgname", organizerName, attributes);
                Helper.printClosedNodeWithAttributes(stream, "element", attributes);
            }
        }
        Helper.closeNode(stream, "missionitems");
        Helper.closeNode(stream, OrganizerSerialization.getNodeName());
    }

    public static void deserializeXML(xmlutils.Node node) {
        NodeList listElements;
        String numWarehousesOrdersString = node.getAttribute("numwarehouseorders");
        int numWarehousesOrders = Helper.ConvertToIntegerAndWarn(numWarehousesOrdersString, "numwarehouseorders", "OrganizerSerialization in deserializeXML ");
        WarehouseOrder.setCountNumOrder(numWarehousesOrders);
        xmlutils.Node organizersSpecialElements = node.getNamedChild("orgelements");
        if (null != organizersSpecialElements) {
            HashMap<String, IStoreorgelement> specialElements = new HashMap<String, IStoreorgelement>();
            NodeList listSpecialElements = organizersSpecialElements.getNamedChildren("element");
            if (null != listSpecialElements && !listSpecialElements.isEmpty()) {
                for (xmlutils.Node element : listSpecialElements) {
                    xmlutils.Node orgElementNode;
                    String name = element.getAttribute("name");
                    if (null == name) {
                        Log.error("OrganizerSerialization in deserializeXML has no attribute named name in node named element");
                    }
                    if (null == (orgElementNode = element.getNamedChild(OrganizerNodeSerialization.getNodeName()))) {
                        Log.error("OrganizerSerialization in deserializeXML has no node named " + OrganizerNodeSerialization.getNodeName() + " in node named " + "element");
                    }
                    Scorgelement _scorgelement = OrganizerNodeSerialization.deserializeXML(orgElementNode);
                    specialElements.put(name, _scorgelement);
                }
            }
            Organizers.getInstance().setOrganizerElementsSpecial(specialElements);
        }
        xmlutils.Node organizerNode = node.getNamedChild("items");
        xmlutils.Node missionOrganizerNode = node.getNamedChild("missionitems");
        if (null == organizerNode) {
            Log.error("OrganizerSerialization in deserializeXML has no named node items");
        } else {
            listElements = organizerNode.getNamedChildren(ListElementSerializator.getNodeName());
            if (listElements != null && !listElements.isEmpty()) {
                IStoreorgelement currentElem = null;
                WarehouseOrder currentWarehouseOrder = null;
                for (xmlutils.Node element : listElements) {
                    xmlutils.Node warehouseElement;
                    boolean isCurrent = element.getNamedChild("current") != null;
                    xmlutils.Node orgelement = element.getNamedChild(OrganizerNodeSerialization.getNodeName());
                    if (null != orgelement) {
                        Scorgelement scOrgElement = OrganizerNodeSerialization.deserializeXML(orgelement);
                        IStoreorgelement elementToAddToOrganizer = Organizers.getInstance().submitRestoredOrgElement(scOrgElement);
                        organaiser.getInstance().addOnRestore(elementToAddToOrganizer);
                        if (isCurrent) {
                            currentElem = elementToAddToOrganizer;
                        }
                    }
                    if (null == (warehouseElement = element.getNamedChild(WarehouseOrganizerNodeSerialization.getNodeName()))) continue;
                    boolean isCurrentWarehouseOrder = element.getNamedChild("current_warehouse_order") != null;
                    WarehouseOrder order = WarehouseOrganizerNodeSerialization.deserializeXML(warehouseElement);
                    organaiser.getInstance().addOnRestore(order);
                    if (isCurrent) {
                        currentElem = order;
                    }
                    if (!isCurrentWarehouseOrder) continue;
                    currentWarehouseOrder = order;
                }
                if (null != currentWarehouseOrder) {
                    organaiser.getInstance().setCurrentWarehouseOrder(currentWarehouseOrder);
                }
                if (null != currentElem) {
                    organaiser.choose(currentElem);
                }
            }
        }
        if (null == missionOrganizerNode) {
            Log.error("OrganizerSerialization in deserializeXML has no named node missionitems");
        } else {
            listElements = missionOrganizerNode.getNamedChildren("element");
            if (listElements != null && !listElements.isEmpty()) {
                for (xmlutils.Node element : listElements) {
                    String missionName = element.getAttribute("missionname");
                    String orgName = element.getAttribute("orgname");
                    if (null == missionName) {
                        Log.error("OrganizerSerialization in deserializeXML has attribute named missionname in node named element");
                        continue;
                    }
                    if (null == orgName) {
                        Log.error("OrganizerSerialization in deserializeXML has attribute named orgname in node named element");
                        continue;
                    }
                    MissionOrganiser.getInstance().addMission(missionName, orgName);
                }
            }
        }
    }
}
