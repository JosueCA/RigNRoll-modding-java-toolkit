/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.List;
import players.actorveh;
import players.aiplayer;
import rnrcore.CoreTime;
import rnrcore.IXMLSerializable;
import rnrscenario.configurators.EnemyBaseConfig;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.EnemyBase;
import rnrscenario.controllers.ScenarioHost;
import scenarioUtils.Pair;
import xmlserialization.AIPlayerSerializator;
import xmlserialization.ActorVehSerializator;
import xmlserialization.CoreTimeSerialization;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlserialization.SimpleTypeSerializator;
import xmlutils.Node;
import xmlutils.NodeList;

@ScenarioClass(scenarioStage=14)
public class EnemyBaseSerializator
implements IXMLSerializable {
    private EnemyBase m_object = null;
    private EnemyBaseConfig m_objectConfig;
    private ScenarioHost host;

    public EnemyBaseSerializator(EnemyBase value) {
        this.m_object = value;
    }

    public EnemyBaseSerializator(EnemyBaseConfig config, ScenarioHost host) {
        this.m_objectConfig = config;
        this.host = host;
    }

    public static String getNodeName() {
        return "enemybase";
    }

    public static void serializeXML(EnemyBase value, PrintStream stream) {
        CoreTime time;
        Object timing;
        aiplayer monica;
        aiplayer dakota;
        actorveh[] assaultCars;
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("isAssaultCycleSceneCreated", value.isAssaultCycleSceneCreated());
        Helper.addAttribute("wasVehicleExchange", value.isWasVehicleExchange(), attributes);
        Helper.addAttribute("bad_conditions", value.isBad_conditions(), attributes);
        Helper.addAttribute("assault_started", value.isAssault_started(), attributes);
        Helper.addAttribute("assault_start_time", value.getAssault_start_time(), attributes);
        Helper.addAttribute("want_dakota_warning", value.isWant_dakota_warning(), attributes);
        Helper.addAttribute("dakota_warning_start_time", value.getDakota_warning_start_time(), attributes);
        Helper.addAttribute("want_dakota_warning_started", value.isWant_dakota_warning_started(), attributes);
        Helper.addAttribute("to_slow_down_gepard", value.isTo_slow_down_gepard(), attributes);
        Helper.addAttribute("slowdown_start", value.isSlowdown_start(), attributes);
        Helper.addAttribute("initialVelocity", value.getInitialVelocity(), attributes);
        Helper.addAttribute("SLOW_DOWN_ACCEL", value.getSlowDownAcceleration(), attributes);
        Helper.addAttribute("to_track_tunnel", value.isTo_track_tunnel(), attributes);
        Helper.addAttribute("was_near_base", value.isWas_near_base(), attributes);
        Helper.printOpenNodeWithAttributes(stream, EnemyBaseSerializator.getNodeName(), attributes);
        Helper.openNode(stream, "dwords");
        SimpleTypeSerializator.serializeXMLStringList(value.getCurrentDwords(), stream);
        Helper.closeNode(stream, "dwords");
        actorveh[] cars = value.getCars();
        if (null != cars && cars.length != 0) {
            Helper.openNode(stream, "cars");
            for (actorveh car : cars) {
                ActorVehSerializator.serializeXML(car, stream);
            }
            Helper.closeNode(stream, "cars");
        }
        if (null != (assaultCars = value.getCars_assault()) && assaultCars.length != 0) {
            Helper.openNode(stream, "cars_assault");
            for (actorveh car : assaultCars) {
                ActorVehSerializator.serializeXML(car, stream);
            }
            Helper.closeNode(stream, "cars_assault");
        }
        if (null != (dakota = value.getDakota())) {
            Helper.openNode(stream, "dakota");
            AIPlayerSerializator.serializeXML(dakota, stream);
            Helper.closeNode(stream, "dakota");
        }
        if (null != (monica = value.getMonica())) {
            Helper.openNode(stream, "monica");
            AIPlayerSerializator.serializeXML(monica, stream);
            Helper.closeNode(stream, "monica");
        }
        if (null != (timing = value.getTo_make_2020_call())) {
            Helper.openNode(stream, "to_make_2020_call");
            time = ((EnemyBase.Timing_BadCall)timing).getInterruptTime();
            CoreTimeSerialization.serializeXML(time, stream);
            Helper.closeNode(stream, "to_make_2020_call");
        }
        if (null != (timing = value.getTo_make_dakota_call())) {
            Helper.openNode(stream, "to_make_dakota_call");
            time = ((EnemyBase.Timing_DakotaCall)timing).getInterruptTime();
            CoreTimeSerialization.serializeXML(time, stream);
            Helper.closeNode(stream, "to_make_dakota_call");
        }
        if (null != (timing = value.getTo_make_discover_base())) {
            Helper.openNode(stream, "to_make_discover_base");
            time = ((EnemyBase.Timing_MonicaFindsOut)timing).getInterruptTime();
            CoreTimeSerialization.serializeXML(time, stream);
            Helper.closeNode(stream, "to_make_discover_base");
        }
        if (null != (timing = value.getTo_make_threat_call())) {
            Helper.openNode(stream, "to_make_threat_call");
            time = ((EnemyBase.Timing_ThreatenCall)timing).getFinishTime();
            CoreTimeSerialization.serializeXML(time, stream);
            Helper.closeNode(stream, "to_make_threat_call");
        }
        Helper.closeNode(stream, EnemyBaseSerializator.getNodeName());
    }

    public static void deserializeXML(Node node, EnemyBaseConfig config, ScenarioHost host) {
        Object timing;
        Node timeNode;
        Node playerNode;
        actorveh car;
        NodeList listCars;
        Node stringList;
        if (null != EnemyBase.getInstance()) {
            Log.error("Illegal state: EnemyBase already exists, maybe save before fixed #19463?");
            return;
        }
        String errorMessage = "EnemyBaseSerializator in deserializeXML ";
        String assault_start_timeString = node.getAttribute("assault_start_time");
        String assault_startedString = node.getAttribute("assault_started");
        String bad_conditionsString = node.getAttribute("bad_conditions");
        String dakota_warning_start_timeString = node.getAttribute("dakota_warning_start_time");
        String initialVelocityString = node.getAttribute("initialVelocity");
        String isAssaultCycleSceneCreatedString = node.getAttribute("isAssaultCycleSceneCreated");
        String SLOW_DOWN_ACCELString = node.getAttribute("SLOW_DOWN_ACCEL");
        String slowdown_startString = node.getAttribute("slowdown_start");
        String to_slow_down_gepardString = node.getAttribute("to_slow_down_gepard");
        String to_track_tunnelString = node.getAttribute("to_track_tunnel");
        String want_dakota_warningString = node.getAttribute("want_dakota_warning");
        String want_dakota_warning_startedString = node.getAttribute("want_dakota_warning_started");
        String was_near_baseString = node.getAttribute("was_near_base");
        String wasVehicleExchangeString = node.getAttribute("wasVehicleExchange");
        double assault_start_timeValue = Helper.ConvertToDoubleAndWarn(assault_start_timeString, "assault_start_time", errorMessage);
        boolean assault_startedValue = Helper.ConvertToBooleanAndWarn(assault_startedString, "assault_started", errorMessage);
        boolean bad_conditionsValue = Helper.ConvertToBooleanAndWarn(bad_conditionsString, "bad_conditions", errorMessage);
        double dakota_warning_start_timeValue = Helper.ConvertToDoubleAndWarn(dakota_warning_start_timeString, "dakota_warning_start_time", errorMessage);
        double initialVelocityValue = Helper.ConvertToDoubleAndWarn(initialVelocityString, "initialVelocity", errorMessage);
        boolean isAssaultCycleSceneCreatedValue = Helper.ConvertToBooleanAndWarn(isAssaultCycleSceneCreatedString, "isAssaultCycleSceneCreated", errorMessage);
        double SLOW_DOWN_ACCELValue = Helper.ConvertToDoubleAndWarn(SLOW_DOWN_ACCELString, "SLOW_DOWN_ACCEL", errorMessage);
        boolean slowdown_startValue = Helper.ConvertToBooleanAndWarn(slowdown_startString, "slowdown_start", errorMessage);
        boolean to_slow_down_gepardValue = Helper.ConvertToBooleanAndWarn(to_slow_down_gepardString, "to_slow_down_gepard", errorMessage);
        boolean to_track_tunnelValue = Helper.ConvertToBooleanAndWarn(to_track_tunnelString, "to_track_tunnel", errorMessage);
        boolean want_dakota_warningValue = Helper.ConvertToBooleanAndWarn(want_dakota_warningString, "want_dakota_warning", errorMessage);
        boolean want_dakota_warning_startedValue = Helper.ConvertToBooleanAndWarn(want_dakota_warning_startedString, "want_dakota_warning_started", errorMessage);
        boolean was_near_baseValue = Helper.ConvertToBooleanAndWarn(was_near_baseString, "was_near_base", errorMessage);
        boolean wasVehicleExchangeValue = Helper.ConvertToBooleanAndWarn(wasVehicleExchangeString, "wasVehicleExchange", errorMessage);
        actorveh[] cars = null;
        actorveh[] carsAssault = null;
        aiplayer dakota = null;
        aiplayer monica = null;
        CoreTime discoverBaseTime = null;
        CoreTime dakotaCallTime = null;
        CoreTime threatCallTime = null;
        CoreTime make2020CallTime = null;
        Node carsNode = node.getNamedChild("cars");
        Node carsAssaultNode = node.getNamedChild("cars_assault");
        Node dakotaNode = node.getNamedChild("dakota");
        Node monicaNode = node.getNamedChild("monica");
        Node discoverBaseTimeNode = node.getNamedChild("to_make_discover_base");
        Node dakotaCallTimeNode = node.getNamedChild("to_make_dakota_call");
        Node threatCallTimeNode = node.getNamedChild("to_make_threat_call");
        Node make2020CallTimeNode = node.getNamedChild("to_make_2020_call");
        Node dwords = node.getNamedChild("dwords");
        EnemyBase result = new EnemyBase(config, host);
        if (null != dwords && null != (stringList = dwords.getNamedChild("string_list"))) {
            List<String> activeDwords = SimpleTypeSerializator.deserializeXMLStringList(stringList);
            for (String activeDword : activeDwords) {
                result.activateDword(activeDword);
            }
        }
        if (null != carsNode) {
            listCars = carsNode.getNamedChildren(ActorVehSerializator.getNodeName());
            if (null == listCars || listCars.isEmpty()) {
                Log.error(errorMessage + " node with name " + "cars" + " has no child nodes with name " + ActorVehSerializator.getNodeName());
            } else {
                cars = new actorveh[listCars.size()];
                int size = 0;
                for (Node carNode : listCars) {
                    car = ActorVehSerializator.deserializeXML(carNode);
                    cars[size++] = car;
                }
            }
        }
        if (null != carsAssaultNode) {
            listCars = carsAssaultNode.getNamedChildren(ActorVehSerializator.getNodeName());
            if (listCars == null || listCars.isEmpty()) {
                Log.error(errorMessage + " node with name " + "cars_assault" + " has no child nodes with name " + ActorVehSerializator.getNodeName());
            } else {
                carsAssault = new actorveh[listCars.size()];
                int size = 0;
                for (Node carNode : listCars) {
                    car = ActorVehSerializator.deserializeXML(carNode);
                    carsAssault[size++] = car;
                }
            }
        }
        if (null != dakotaNode) {
            playerNode = dakotaNode.getNamedChild(AIPlayerSerializator.getNodeName());
            if (null == playerNode) {
                Log.error(errorMessage + " node with name " + "dakota" + " has no child nodes with name " + AIPlayerSerializator.getNodeName());
            } else {
                dakota = AIPlayerSerializator.deserializeXML(playerNode);
            }
        }
        if (null != monicaNode) {
            playerNode = monicaNode.getNamedChild(AIPlayerSerializator.getNodeName());
            if (null == playerNode) {
                Log.error(errorMessage + " node with name " + "monica" + " has no child nodes with name " + AIPlayerSerializator.getNodeName());
            } else {
                monica = AIPlayerSerializator.deserializeXML(playerNode);
            }
        }
        if (null != discoverBaseTimeNode) {
            timeNode = discoverBaseTimeNode.getNamedChild(CoreTimeSerialization.getNodeName());
            if (null == timeNode) {
                Log.error(errorMessage + " node with name " + "to_make_discover_base" + " has no child nodes with name " + CoreTimeSerialization.getNodeName());
            } else {
                discoverBaseTime = CoreTimeSerialization.deserializeXML(timeNode);
            }
        }
        if (null != dakotaCallTimeNode) {
            timeNode = dakotaCallTimeNode.getNamedChild(CoreTimeSerialization.getNodeName());
            if (null == timeNode) {
                Log.error(errorMessage + " node with name " + "to_make_dakota_call" + " has no child nodes with name " + CoreTimeSerialization.getNodeName());
            } else {
                dakotaCallTime = CoreTimeSerialization.deserializeXML(timeNode);
            }
        }
        if (null != threatCallTimeNode) {
            timeNode = threatCallTimeNode.getNamedChild(CoreTimeSerialization.getNodeName());
            if (null == timeNode) {
                Log.error(errorMessage + " node with name " + "to_make_threat_call" + " has no child nodes with name " + CoreTimeSerialization.getNodeName());
            } else {
                threatCallTime = CoreTimeSerialization.deserializeXML(timeNode);
            }
        }
        if (null != make2020CallTimeNode) {
            timeNode = make2020CallTimeNode.getNamedChild(CoreTimeSerialization.getNodeName());
            if (null == timeNode) {
                Log.error(errorMessage + " node with name " + "to_make_2020_call" + " has no child nodes with name " + CoreTimeSerialization.getNodeName());
            } else {
                make2020CallTime = CoreTimeSerialization.deserializeXML(timeNode);
            }
        }
        result.setAssault_start_time(assault_start_timeValue);
        result.setAssault_started(assault_startedValue);
        result.setBad_conditions(bad_conditionsValue);
        result.setDakota_warning_start_time(dakota_warning_start_timeValue);
        result.setInitialVelocity(initialVelocityValue);
        result.setAssaultCycleSceneCreated(isAssaultCycleSceneCreatedValue);
        result.setSlowDownAcceleration(SLOW_DOWN_ACCELValue);
        result.setSlowdown_start(slowdown_startValue);
        result.setTo_slow_down_gepard(to_slow_down_gepardValue);
        result.setTo_track_tunnel(to_track_tunnelValue);
        result.setWant_dakota_warning(want_dakota_warningValue);
        result.setWant_dakota_warning_started(want_dakota_warning_startedValue);
        result.setWas_near_base(was_near_baseValue);
        result.setWasVehicleExchange(wasVehicleExchangeValue);
        result.setCars(cars);
        result.setCars_assault(carsAssault);
        result.setDakota(dakota);
        result.setMonica(monica);
        if (null != discoverBaseTime) {
            timing = new EnemyBase.Timing_MonicaFindsOut();
            ((EnemyBase.Timing_MonicaFindsOut)timing).setInterruptTime(discoverBaseTime);
            result.setTo_make_discover_base((EnemyBase.Timing_MonicaFindsOut)timing);
        }
        if (null != dakotaCallTime) {
            timing = new EnemyBase.Timing_DakotaCall();
            ((EnemyBase.Timing_DakotaCall)timing).setInterruptTime(dakotaCallTime);
            result.setTo_make_dakota_call((EnemyBase.Timing_DakotaCall)timing);
        }
        if (null != threatCallTime) {
            timing = new EnemyBase.Timing_ThreatenCall();
            ((EnemyBase.Timing_ThreatenCall)timing).setFinishTime(threatCallTime);
            result.setTo_make_threat_call((EnemyBase.Timing_ThreatenCall)timing);
        }
        if (null != make2020CallTime) {
            timing = new EnemyBase.Timing_BadCall();
            ((EnemyBase.Timing_BadCall)timing).setInterruptTime(make2020CallTime);
            result.setTo_make_2020_call((EnemyBase.Timing_BadCall)timing);
        }
    }

    public void deSerialize(Node node) {
        EnemyBaseSerializator.deserializeXML(node, this.m_objectConfig, this.host);
    }

    public void serialize(PrintStream stream) {
        EnemyBaseSerializator.serializeXML(this.m_object, stream);
    }
}

