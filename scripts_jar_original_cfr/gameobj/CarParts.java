/*
 * Decompiled with CFR 0.151.
 */
package gameobj;

import java.lang.reflect.Field;
import java.util.Vector;
import menu.CondTable;
import menu.Item;
import menu.TableNode;
import rnrcore.eng;
import rnrcore.loc;
import xmlutils.Node;
import xmlutils.NodeList;
import xmlutils.XmlUtils;

public class CarParts {
    public static final int COND_GREEN = 0;
    public static final int COND_YELLOW = 1;
    public static final int COND_RED = 2;
    public static final int MAX_W = 8;
    public static final int VP_ENGINE_BEGIN = 0;
    public static final int VP_ENGINE_MOUNT = 0;
    public static final int VP_ENGINE_CRANKSHAFT = 1;
    public static final int VP_ENGINE_BLOCK = 2;
    public static final int VP_ENGINE_BRAKE = 3;
    public static final int VP_ENGINE_OIL = 4;
    public static final int VP_ENGINE_OILPUMP = 5;
    public static final int VP_ENGINE_OILFILTER = 6;
    public static final int VP_ENGINE_OILPIPELINE = 7;
    public static final int VP_ENGINE_OILCOOLER = 8;
    public static final int VP_ENGINE_OILPAN = 9;
    public static final int VP_ENGINE_END = 9;
    public static final int VP_ENGINE_MAX = 10;
    public static final int VP_COOLING_BEGIN = 10;
    public static final int VP_COOLING_COOLANT = 10;
    public static final int VP_COOLING_HOSES = 11;
    public static final int VP_COOLING_RADIATOR = 12;
    public static final int VP_COOLING_WATERPUMP = 13;
    public static final int VP_COOLING_THERMOSTAT = 14;
    public static final int VP_COOLING_FAN = 15;
    public static final int VP_COOLING_END = 15;
    public static final int VP_COOLING_MAX = 6;
    public static final int VP_STEERING_BEGIN = 16;
    public static final int VP_STEERING_SHAFT = 16;
    public static final int VP_STEERING_FLUID = 17;
    public static final int VP_STEERING_BELT = 18;
    public static final int VP_STEERING_GEAR = 19;
    public static final int VP_STEERING_PUMP = 20;
    public static final int VP_STEERING_RODS = 21;
    public static final int VP_STEERING_END = 21;
    public static final int VP_STEERING_MAX = 6;
    public static final int VP_BRAKING_BEGIN = 22;
    public static final int VP_BRAKING_DRUMS = 22;
    public static final int VP_BRAKING_SHOES = 23;
    public static final int VP_BRAKING_COMPRESSOR = 24;
    public static final int VP_BRAKING_GOUVERNOR = 25;
    public static final int VP_BRAKING_RESERVOIRS = 26;
    public static final int VP_BRAKING_CHAMBERS = 27;
    public static final int VP_BRAKING_VALVE = 28;
    public static final int VP_BRAKING_PARKING = 29;
    public static final int VP_BRAKING_NOSES = 30;
    public static final int VP_BRAKING_ABS = 31;
    public static final int VP_BRAKING_END = 31;
    public static final int VP_BRAKING_MAX = 10;
    public static final int VP_TRANSMISSION_BEGIN = 32;
    public static final int VP_TRANSMISSION_CLUTCH = 32;
    public static final int VP_TRANSMISSION_OIL = 33;
    public static final int VP_TRANSMISSION_GEAR = 34;
    public static final int VP_TRANSMISSION_AXLES = 35;
    public static final int VP_TRANSMISSION_DRIVESHAFT = 36;
    public static final int VP_TRANSMISSION_INTERSHAFT = 37;
    public static final int VP_TRANSMISSION_HUBS = 38;
    public static final int VP_TRANSMISSION_END = 38;
    public static final int VP_TRANSMISSION_MAX = 7;
    public static final int VP_ELECTRICAL_BEGIN = 39;
    public static final int VP_ELECTRICAL_BATTERIES = 39;
    public static final int VP_ELECTRICAL_ALTERNATOR = 40;
    public static final int VP_ELECTRICAL_STARTER = 41;
    public static final int VP_ELECTRICAL_IGNITION = 42;
    public static final int VP_ELECTRICAL_LIGHTSSOUND = 43;
    public static final int VP_ELECTRICAL_WIPERS = 44;
    public static final int VP_ELECTRICAL_WARES = 45;
    public static final int VP_ELECTRICAL_END = 45;
    public static final int VP_ELECTRICAL_MAX = 7;
    public static final int VP_SUSPENSION_BEGIN = 46;
    public static final int VP_SUSPENSION_LEAFSPRINGS = 46;
    public static final int VP_SUSPENSION_ABSORBERS = 47;
    public static final int VP_SUSPENSION_AIRBAGS = 48;
    public static final int VP_SUSPENSION_COMPRESSOR = 49;
    public static final int VP_SUSPENSION_AIRPIPELINE = 50;
    public static final int VP_SUSPENSION_GOVERNORS = 51;
    public static final int VP_SUSPENSION_ARMSSTABILIZERS = 52;
    public static final int VP_SUSPENSION_END = 52;
    public static final int VP_SUSPENSION_MAX = 7;
    public static final int VP_WHEELS_BEGIN = 53;
    public static final int VP_WHEELS_RIM_0 = 53;
    public static final int VP_WHEELS_TIRE_0 = 61;
    public static final int VP_WHEELS_END = 68;
    public static final int VP_WHEELS_MAX = 16;
    public static final int VP_FUEL_BEGIN = 69;
    public static final int VP_FUEL_INJECTORS = 69;
    public static final int VP_FUEL_PUMP = 70;
    public static final int VP_FUEL_TANK = 71;
    public static final int VP_FUEL_FILTER = 72;
    public static final int VP_FUEL_AIRFILTER = 73;
    public static final int VP_FUEL_TURBOCHARGER = 74;
    public static final int VP_FUEL_INTAKEMANIFOLD = 75;
    public static final int VP_FUEL_INTAKEVALVES = 76;
    public static final int VP_FUEL_END = 76;
    public static final int VP_FUEL_MAX = 8;
    public static final int VP_EXHAUST_BEGIN = 77;
    public static final int VP_EXHAUST_VALVES = 77;
    public static final int VP_EXHAUST_MANIFOLD = 78;
    public static final int VP_EXHAUST_PIPES = 79;
    public static final int VP_EXHAUST_MUFFLERS = 80;
    public static final int VP_EXHAUST_END = 80;
    public static final int VP_EXHAUST_MAX = 4;
    public static final int VP_COUPLING_BEGIN = 81;
    public static final int VP_COUPLING_LOCKMECHANISM = 81;
    public static final int VP_COUPLING_5THWHEEL = 82;
    public static final int VP_COUPLING_KINGPIN = 83;
    public static final int VP_COUPLING_END = 83;
    public static final int VP_COUPLING_MAX = 3;
    public static final int MAX_CARV_DAMAGE = 84;
    public static final int VP_BODY_AND_FRAME_BEGIN = 84;
    public static final int VP_BODY = 84;
    public static final int VP_FRAME = 85;
    public static final int VP_GLASES = 86;
    public static final int VP_BODY_AND_FRAME_END = 86;
    public static final int VP_BODY_AND_FRAME_MAX = 3;
    public static final int VP_CARVSTATE_0 = 87;
    public static final int VP_CARVSTATE_odometer = 87;
    public static final int VP_CARVSTATE_engine_power = 88;
    public static final int VP_CARVSTATE_fuel = 89;
    public static final int VP_CARVSTATE_tire = 90;
    public static final int VP_CARVSTATE_brake = 91;
    public static final int VP_CARVSTATE_brake_burn = 92;
    public static final int VP_CARVSTATE_oil_additive = 93;
    public static final int VP_CARVSTATE_engine_brake = 94;
    public static final int MAX_VP_STATE = 95;
    public static final int VP_VEHICLE = 96;
    public static final int VP_ENGINE = 97;
    public static final int VP_COOLINGSYS = 98;
    public static final int VP_STEERINGSYS = 99;
    public static final int VP_BRAKINGSYS = 100;
    public static final int VP_TRANSMISSIONSYS = 101;
    public static final int VP_ELECTRICALSYS = 102;
    public static final int VP_SUSPENSIONSYS = 103;
    public static final int VP_WHEELSYS = 104;
    public static final int VP_FUELSYS = 105;
    public static final int VP_EXHAUSTSYS = 106;
    public static final int VP_COUPLINGSYS = 107;
    public static final int VP_BODYNFRAME = 108;
    public static final int VP_REALMAX = 109;
    private static CarPartsNode m_root = null;
    Item[] m_Parts = new Item[109];

    private void FillData() {
        for (int i = 0; i < 109; ++i) {
            this.m_Parts[i] = new Item(0, 0, 0, "no name");
            this.m_Parts[i].id = i;
        }
    }

    public CarParts() {
        this.FillData();
        this.SetupHierarchy();
    }

    public Item GetItem(int ID) {
        return this.m_Parts[ID];
    }

    public void SetItemInfo(int ID, int price, int condition) {
        if (this.m_Parts[ID] == null) {
            return;
        }
        this.m_Parts[ID].price = price;
        this.m_Parts[ID].condition = condition;
    }

    private TableNode fillCondTable(CondTable table, TableNode pap, CarPartsNode node) {
        if (node.item.id != 86 && node.item.id != 43) {
            TableNode res = table.AddItem(pap, node.item);
            for (int i = 0; i < node.childs.size(); ++i) {
                this.fillCondTable(table, res, (CarPartsNode)node.childs.get(i));
            }
            return res;
        }
        return null;
    }

    public TableNode FillCondTable(CondTable table) {
        return this.fillCondTable(table, null, m_root);
    }

    public void CloneData(CarParts parts) {
        for (int i = 0; i < 109; ++i) {
            if (this.m_Parts[i] == null) continue;
            this.m_Parts[i].auth = parts.m_Parts[i].auth;
            this.m_Parts[i].price = parts.m_Parts[i].price;
            this.m_Parts[i].condition = parts.m_Parts[i].condition;
            this.m_Parts[i].name = parts.m_Parts[i].name;
        }
    }

    private int resolveConstant(String name) {
        int result = -1;
        try {
            Class<?> cl = this.getClass();
            Field field = cl.getField(name);
            result = field.getInt(this);
        }
        catch (Exception e) {
            eng.err("Resolve constant error. " + e.toString());
        }
        return result;
    }

    private void SetupHierarchy() {
        if (m_root == null) {
            m_root = new XmlTable().parse();
        }
    }

    public void FixupHierarchy() {
        this.Fixup(m_root);
    }

    void Fixup(CarPartsNode node) {
        int i;
        if (node.childs.size() == 0) {
            if (node.item.condition == 0) {
                node.item.price = 0;
                node.item.auth = 0;
            }
            return;
        }
        for (i = 0; i < node.childs.size(); ++i) {
            this.Fixup((CarPartsNode)node.childs.get(i));
        }
        node.item.condition = 0;
        node.item.price = 0;
        node.item.auth = 0;
        for (i = 0; i < node.childs.size(); ++i) {
            CarPartsNode child = (CarPartsNode)node.childs.get(i);
            if (child.item.condition > node.item.condition) {
                node.item.condition = child.item.condition;
            }
            node.item.price += child.item.price;
            node.item.auth += child.item.auth;
        }
        node.item.price += 0;
    }

    class XmlTable {
        private static final String FILENAME = "..\\data\\config\\repairsystem.xml";
        private static final String ROOT = "carsystems";
        private static final String ALIASE = "alias";
        private static final String NAME = "name";

        XmlTable() {
        }

        public CarPartsNode parse() {
            Node top = XmlUtils.parse(FILENAME);
            NodeList root = top.getNamedChildren(ROOT);
            if (root.size() != 1) {
                eng.err("suspicious ..\\data\\config\\repairsystem.xml in XmlTable CarParts");
                return null;
            }
            NodeList vehicles = ((Node)root.get(0)).getChildren();
            if (vehicles.size() != 1) {
                eng.err("suspicious ..\\data\\config\\repairsystem.xml in XmlTable CarParts 2");
                return null;
            }
            return this.parse((Node)vehicles.get(0));
        }

        private CarPartsNode parse(Node node) {
            CarPartsNode res = new CarPartsNode(CarParts.this.m_Parts[CarParts.this.resolveConstant(node.getAttribute(ALIASE))]);
            if (res.item.id != 86 && res.item.id != 43) {
                res.item.name = this.resolveName(node.getAttribute(NAME));
            }
            NodeList childs = node.getChildren();
            for (int i = 0; i < childs.size(); ++i) {
                res.AddChild(this.parse((Node)childs.get(i)));
            }
            return res;
        }

        private String resolveName(String name) {
            return loc.getRepairTableString(name);
        }
    }

    public static class CarPartsNode {
        Vector childs = new Vector();
        Item item;

        CarPartsNode(Item _item) {
            this.item = _item;
        }

        public CarPartsNode AddChild(Item _item) {
            CarPartsNode child = new CarPartsNode(_item);
            this.childs.add(child);
            return child;
        }

        public void AddChild(CarPartsNode ch) {
            this.childs.add(ch);
        }
    }
}

