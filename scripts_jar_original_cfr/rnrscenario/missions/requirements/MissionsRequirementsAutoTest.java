/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.requirements;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import rnrscenario.missions.requirements.MissionsLog;
import rnrscenario.missions.requirements.Requirement;
import rnrscenario.missions.requirements.RequirementsFactory;
import rnrscenario.missions.requirements.ScalarInIntervalRequirement;
import scenarioUtils.Pair;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class MissionsRequirementsAutoTest {
    private static final int MAX_DEEP = 5;
    private static final int MAX_ALTERNATION_LENGTH = 5;
    private static final int MAX_ALTERNATION_COUNT = 20;

    public static void main(String[] args) {
        try {
            ScalarInIntervalRequirement.nativeLibraryExists = false;
            MissionsLog log = MissionsLog.getInstance();
            log.eventHappen("m1", MissionsLog.Event.PLAYER_DECLINED_MISSION);
            log.eventHappen("m2", MissionsLog.Event.PLAYER_INFORMED_ABOUT_MISSION);
            log.eventHappen("m2", MissionsLog.Event.MISSION_ACCEPTED);
            log.eventHappen("m2", MissionsLog.Event.MISSION_COMPLETE);
            log.eventHappen("m3", MissionsLog.Event.PLAYER_INFORMED_ABOUT_MISSION);
            log.eventHappen("m3", MissionsLog.Event.MISSION_ACCEPTED);
            log.eventHappen("m3", MissionsLog.Event.FREIGHT_LOADING_EXPIRED);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document conditionTree = builder.newDocument();
            Element requirementRoot = conditionTree.createElement("req");
            ArrayList<Pair<Node, Boolean>> statements = new ArrayList<Pair<Node, Boolean>>();
            MissionsRequirementsAutoTest.createRequirementsOnMissionLog(conditionTree, statements);
            MissionsRequirementsAutoTest.createScalarRequirements(conditionTree, statements, "TimeRequirement");
            MissionsRequirementsAutoTest.createScalarRequirements(conditionTree, statements, "RankRequirement");
            MissionsRequirementsAutoTest.createScalarRequirements(conditionTree, statements, "RatingRequirement");
            MissionsRequirementsAutoTest.createScalarRequirements(conditionTree, statements, "MoneyRequirement");
            Random random = new Random(System.nanoTime());
            for (int level = 0; level < 5; ++level) {
                LinkedList topAlternations = new LinkedList();
                int alternationsCount = random.nextInt(20) + 1;
                for (int i = 0; i < alternationsCount; ++i) {
                    boolean alternationValue = false;
                    LinkedList<Node> alternation = new LinkedList<Node>();
                    int n = random.nextInt(5) + 1;
                    for (int j = 0; j < n; ++j) {
                        int statementNomber = random.nextInt(statements.size());
                        alternationValue |= ((Boolean)((Pair)statements.get(statementNomber)).getSecond()).booleanValue();
                        alternation.add(((Node)((Pair)statements.get(statementNomber)).getFirst()).cloneNode(false));
                    }
                    topAlternations.add(new Pair(alternation, alternationValue));
                }
                for (int levelNomber = 0; levelNomber < level; ++levelNomber) {
                    LinkedList nextTopAlternations = new LinkedList();
                    while (!topAlternations.isEmpty()) {
                        int alternationsToSplit = random.nextInt(topAlternations.size()) + 1;
                        LinkedList<Node> linkedList = new LinkedList<Node>();
                        boolean alternationValue = false;
                        for (int i = 0; i < alternationsToSplit; ++i) {
                            Pair alternation = (Pair)topAlternations.remove(random.nextInt(topAlternations.size()));
                            Pair toConjugateWith = (Pair)statements.get(random.nextInt(statements.size()));
                            Node toAddInNewAlternation = ((Node)toConjugateWith.getFirst()).cloneNode(false);
                            for (Node statement : (List)alternation.getFirst()) {
                                toAddInNewAlternation.appendChild(statement);
                            }
                            alternationValue |= (Boolean)toConjugateWith.getSecond() != false && (Boolean)alternation.getSecond() != false;
                            linkedList.add(toAddInNewAlternation);
                        }
                        nextTopAlternations.add(new Pair(linkedList, alternationValue));
                    }
                    topAlternations = nextTopAlternations;
                }
                boolean requirementValue = false;
                Node root = requirementRoot.cloneNode(false);
                for (Pair pair : topAlternations) {
                    requirementValue |= ((Boolean)pair.getSecond()).booleanValue();
                    for (Node statement : (List)pair.getFirst()) {
                        root.appendChild(statement);
                    }
                }
                Requirement req = RequirementsFactory.makeOrRequirement(root);
                if (requirementValue == req.check()) continue;
                throw new Exception("TEST FAILED!");
            }
        }
        catch (Exception e) {
            System.err.println("TEST FAILED: " + e.getLocalizedMessage());
            return;
        }
        System.out.println("\nTEST SUCCEDED");
    }

    private static void createScalarRequirements(Document conditionTree, List<Pair<Node, Boolean>> statements, String className) {
        Element requirement = conditionTree.createElement(className);
        requirement.setAttribute("upperBound", "2.0");
        requirement.setAttribute("lowerBound", "3.0");
        statements.add(new Pair<Element, Boolean>(requirement, false));
        requirement = conditionTree.createElement(className);
        requirement.setAttribute("upperBound", "1");
        requirement.setAttribute("lowerBound", "0");
        statements.add(new Pair<Element, Boolean>(requirement, true));
        requirement = conditionTree.createElement(className);
        requirement.setAttribute("upperBound", "0.3");
        statements.add(new Pair<Element, Boolean>(requirement, false));
        requirement = conditionTree.createElement(className);
        requirement.setAttribute("lowerBound", "0.6");
        statements.add(new Pair<Element, Boolean>(requirement, false));
    }

    private static void createRequirementsOnMissionLog(Document conditionTree, List<Pair<Node, Boolean>> statements) {
        assert (null != conditionTree) : "conditionTree must be non-null reference";
        assert (null != statements) : "statements must be non-null reference";
        Element requirement = conditionTree.createElement("MissionAcceptedRequirement");
        requirement.setAttribute("mission", "m1");
        statements.add(new Pair<Element, Boolean>(requirement, false));
        requirement = conditionTree.createElement("MissionAcceptedRequirement");
        requirement.setAttribute("mission", "m2");
        statements.add(new Pair<Element, Boolean>(requirement, true));
        requirement = conditionTree.createElement("MissionCompleteRequirement");
        requirement.setAttribute("mission", "m2");
        statements.add(new Pair<Element, Boolean>(requirement, true));
        requirement = conditionTree.createElement("MissionCompleteRequirement");
        requirement.setAttribute("mission", "m3");
        statements.add(new Pair<Element, Boolean>(requirement, false));
        requirement = conditionTree.createElement("MissionDeclinedRequirement");
        requirement.setAttribute("mission", "m1");
        statements.add(new Pair<Element, Boolean>(requirement, true));
        requirement = conditionTree.createElement("MissionDeclinedRequirement");
        requirement.setAttribute("mission", "m2");
        statements.add(new Pair<Element, Boolean>(requirement, false));
        requirement = conditionTree.createElement("MissionFailureRequirement");
        requirement.setAttribute("mission", "m1");
        requirement.setAttribute("reason", "any");
        statements.add(new Pair<Element, Boolean>(requirement, false));
        requirement = conditionTree.createElement("MissionFailureRequirement");
        requirement.setAttribute("mission", "m3");
        requirement.setAttribute("reason", "FREIGHT_LOADING_EXPIRED");
        statements.add(new Pair<Element, Boolean>(requirement, true));
        requirement = conditionTree.createElement("MissionFinishedRequirement");
        requirement.setAttribute("mission", "m1");
        statements.add(new Pair<Element, Boolean>(requirement, false));
        requirement = conditionTree.createElement("MissionFinishedRequirement");
        requirement.setAttribute("mission", "m2");
        statements.add(new Pair<Element, Boolean>(requirement, true));
        requirement = conditionTree.createElement("MissionFinishedRequirement");
        requirement.setAttribute("mission", "m3");
        statements.add(new Pair<Element, Boolean>(requirement, true));
        requirement = conditionTree.createElement("PlayerInformedAboutMissionRequirement");
        requirement.setAttribute("mission", "m1");
        statements.add(new Pair<Element, Boolean>(requirement, false));
        requirement = conditionTree.createElement("PlayerInformedAboutMissionRequirement");
        requirement.setAttribute("mission", "m2");
        statements.add(new Pair<Element, Boolean>(requirement, true));
        requirement = conditionTree.createElement("PlayerInformedAboutMissionRequirement");
        requirement.setAttribute("mission", "m3");
        statements.add(new Pair<Element, Boolean>(requirement, true));
    }
}

