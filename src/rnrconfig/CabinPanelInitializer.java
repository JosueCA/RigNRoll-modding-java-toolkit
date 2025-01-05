/*
 * Decompiled with CFR 0.151.
 */
package rnrconfig;

import auxil.DInputStream2;
import auxil.XInputStreamCreate;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import rnrconfig.Cabin;
import rnrconfig.GaugeTypes;
import rnrconfig.LightTypes;
import rnrcore.eng;

public class CabinPanelInitializer {
    private static final String _pathToTrucks = new String("..\\Trucks\\");
    private static final String _cabinPanelDescriptionFileName = new String("CabinPanel.xml");
    private Document _document;
    private static CabinPanelInitializer _instance = null;

    public CabinPanelInitializer() {
        try {
            this._document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new DInputStream2(XInputStreamCreate.open(_pathToTrucks + _cabinPanelDescriptionFileName)));
        }
        catch (Exception e) {
            eng.err("CabinPanelInitializer. Parsing error: " + e.toString());
        }
    }

    public static void InitializeVehicleInsideOrOutsideStatic(long cabin_np, String vehicleName, boolean inside) {
        try {
            CabinPanelInitializer.GetInstance().InitializeVehicleInsideOrOutside(cabin_np, vehicleName, inside);
        }
        catch (Exception e) {
            eng.err("CabinPanelInitializer. Initializing error: " + e.toString());
        }
    }

    public void InitializeVehicleInsideOrOutside(long cabin_np, String vehicleName, boolean inside) throws Exception {
        Node vehicleNode = this.FindVehicleNodeByName(this._document, vehicleName);
        Node insideNode = this.FindInsideOrOutsideNode(vehicleNode, inside);
        CabinPanelInitializer.InitializeAllNodes(cabin_np, insideNode);
    }

    private Node FindInsideOrOutsideNode(Node vehicleNode, boolean inside) throws Exception {
        String outsideOrInside = inside ? "INSIDE" : "OUTSIDE";
        NodeList nl = vehicleNode.getChildNodes();
        for (int i = 0; i < nl.getLength(); ++i) {
            Node n = nl.item(i);
            if (n.getNodeType() != 1 || !n.getNodeName().equals(outsideOrInside)) continue;
            return n;
        }
        throw new Exception("FindInsideOrOutsideNode: there isn't tag <" + outsideOrInside + "> in the <VEHICLE> section with name = " + vehicleNode.getAttributes().getNamedItem("name").getNodeValue() + " in CabinPanel.xml.");
    }

    private Node FindVehicleNodeByName(Document cabinPanelDocument, String vehicleName) throws Exception {
        NodeList nl = cabinPanelDocument.getElementsByTagName("VEHICLE");
        for (int i = 0; i < nl.getLength(); ++i) {
            Node n = nl.item(i);
            NamedNodeMap nnm = n.getAttributes();
            String value = nnm.getNamedItem("name").getNodeValue();
            if (!value.equals(vehicleName)) continue;
            return n;
        }
        throw new Exception("FindVehicleNodeByName: there isn't tag <VEHICLE> in CabinPanel.xml with name = " + vehicleName + ".");
    }

    private static void InitializeAllNodes(long cabin_np, Node insideOrOutsideNode) throws Exception {
        NodeList nl = insideOrOutsideNode.getChildNodes();
        for (int i = 0; i < nl.getLength(); ++i) {
            Node n = nl.item(i);
            if (n.getNodeType() != 1) continue;
            String tagName = n.getNodeName();
            if (tagName.equals("LIGHT")) {
                CabinPanelInitializer.ProcessLightTag(cabin_np, n);
                continue;
            }
            if (tagName.equals("GAUGE")) {
                CabinPanelInitializer.ProcessGaugeTag(cabin_np, n);
                continue;
            }
            throw new Exception("InitializeAllNodes: there is inappropriate tag (name = " + tagName + ") was met in CabinPanel.xml");
        }
    }

    private static void ProcessLightTag(long cabin_np, Node lightNode) {
        NamedNodeMap nnm = lightNode.getAttributes();
        String value1 = nnm.getNamedItem("type").getNodeValue();
        String value2 = nnm.getNamedItem("dword_name").getNodeValue();
        String value3 = nnm.getNamedItem("work_with_ignition_only").getNodeValue();
        int type = LightTypes.valueOf(value1).GetNumber();
        String name = value2;
        boolean checkEngine = Boolean.parseBoolean(value3);
        Cabin.CreateLight(cabin_np, name, type, checkEngine);
    }

    private static void ProcessGaugeTag(long cabin_np, Node gaugeNode) throws Exception {
        NamedNodeMap nnm = gaugeNode.getAttributes();
        String value1 = nnm.getNamedItem("type").getNodeValue();
        String value2 = nnm.getNamedItem("space_name").getNodeValue();
        String value3 = nnm.getNamedItem("work_with_ignition_only").getNodeValue();
        String value4 = nnm.getNamedItem("min_value").getNodeValue();
        String value5 = nnm.getNamedItem("max_value").getNodeValue();
        String value6 = nnm.getNamedItem("min_angle").getNodeValue();
        String value7 = nnm.getNamedItem("max_angle").getNodeValue();
        String value8 = nnm.getNamedItem("scale_factor").getNodeValue();
        int type = GaugeTypes.valueOf(value1).GetNumber();
        String name = value2;
        boolean checkEngine = Boolean.parseBoolean(value3);
        double minvalue = Double.parseDouble(value4);
        double maxvalue = Double.parseDouble(value5);
        double minangle = Double.parseDouble(value6);
        double maxangle = Double.parseDouble(value7);
        double scale = Double.parseDouble(value8);
        boolean gaugeHasControlLight = false;
        String nameForControlLight = new String("");
        double minvalueForControlLight = 0.0;
        double maxvalueForControlLight = 0.0;
        NodeList nl = gaugeNode.getChildNodes();
        for (int i = 0; i < nl.getLength(); ++i) {
            String clValue3;
            Node n = nl.item(i);
            if (n.getNodeType() != 1) continue;
            if (!n.getNodeName().equals("CONTROL_LIGHT")) {
                throw new Exception("ProcessGaugeTag: the <GAUGE> section includes inappropriate tag (name" + n.getNodeName() + ").");
            }
            gaugeHasControlLight = true;
            NamedNodeMap nnm2 = n.getAttributes();
            String clValue1 = nnm2.getNamedItem("min_value").getNodeValue();
            String clValue2 = nnm2.getNamedItem("max_value").getNodeValue();
            nameForControlLight = clValue3 = nnm2.getNamedItem("dword_name").getNodeValue();
            minvalueForControlLight = Double.parseDouble(clValue1);
            maxvalueForControlLight = Double.parseDouble(clValue2);
        }
        long pGauge = Cabin.CreateGAUGE(cabin_np, name, type, checkEngine);
        Cabin.TuneGAUGE(pGauge, minvalue, maxvalue, minangle, maxangle, scale);
        if (gaugeHasControlLight) {
            Cabin.AddControlLight(pGauge, nameForControlLight, minvalueForControlLight, maxvalueForControlLight);
        }
    }

    public static CabinPanelInitializer GetInstance() {
        if (_instance == null) {
            _instance = new CabinPanelInitializer();
        }
        return _instance;
    }
}

