/*
 * Decompiled with CFR 0.151.
 */
package scenarioXml;

import java.io.IOException;
import scenarioMachine.FiniteStateMachine;
import scenarioMachine.FiniteStatesSet;
import scenarioXml.CbvEventListenerBuilder;
import scenarioXml.FiniteStatesSetBuilder;
import scenarioXml.InternalScenarioRepresentation;
import scenarioXml.IsoQuest;
import scenarioXml.ParsingOut;
import scenarioXml.PhasedQuest;
import scenarioXml.XmlScenarioParser;

public final class XmlScenarioMachineBuilder {
    private XmlScenarioMachineBuilder() {
    }

    private static InternalScenarioRepresentation buildFromExternal(ParsingOut externalScenarioRepresentation, boolean needSupportForAnalysis) {
        FiniteStateMachine machine = new FiniteStateMachine(needSupportForAnalysis);
        FiniteStatesSet set = new FiniteStatesSet();
        FiniteStatesSetBuilder statesBuilder = new FiniteStatesSetBuilder();
        for (PhasedQuest phasedQuest : externalScenarioRepresentation.getPhasedQuests()) {
            statesBuilder.buildStateSet(phasedQuest, machine);
        }
        statesBuilder.pourOut(set);
        for (IsoQuest isoQuest : externalScenarioRepresentation.getIsoQuests()) {
            statesBuilder.buildStateSet(isoQuest, machine);
        }
        statesBuilder.pourOut(set);
        machine.setStatesPool(set);
        return new InternalScenarioRepresentation(set, machine);
    }

    private static void reloadFromExternal(FiniteStateMachine machine, ParsingOut externalScenarioRepresentation) {
        machine.clear();
        FiniteStatesSet set = new FiniteStatesSet();
        FiniteStatesSetBuilder statesBuilder = new FiniteStatesSetBuilder();
        for (PhasedQuest phasedQuest : externalScenarioRepresentation.getPhasedQuests()) {
            statesBuilder.buildStateSet(phasedQuest, machine);
        }
        statesBuilder.pourOut(set);
        for (IsoQuest isoQuest : externalScenarioRepresentation.getIsoQuests()) {
            statesBuilder.buildStateSet(isoQuest, machine);
        }
        statesBuilder.pourOut(set);
        machine.setStatesPool(set);
    }

    public static InternalScenarioRepresentation getScenarioMachine(String pathToXmlDataStorage, boolean needSupportForAnalysis) throws IOException {
        ParsingOut external = XmlScenarioParser.parseQuests(pathToXmlDataStorage);
        if (null == external) {
            return null;
        }
        return XmlScenarioMachineBuilder.buildFromExternal(external, needSupportForAnalysis);
    }

    public static void reloadMachine(FiniteStateMachine macihne, String pathToXmlDataStorage) throws IOException {
        ParsingOut external = XmlScenarioParser.parseQuests(pathToXmlDataStorage);
        if (null != external) {
            XmlScenarioMachineBuilder.reloadFromExternal(macihne, external);
        }
    }

    public static void main(String[] args) {
        try {
            ParsingOut external = XmlScenarioParser.parseQuests("demoQuests.xml");
            InternalScenarioRepresentation internal = XmlScenarioMachineBuilder.buildFromExternal(external, false);
            new CbvEventListenerBuilder("demoCbvideocalls.xml", internal.getStatesMachine());
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}

