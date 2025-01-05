/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.requirements;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.logging.Level;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import rnrloggers.MissionsLogger;
import rnrscenario.missions.PriorityTable;
import rnrscenario.missions.requirements.AndRequirement;
import rnrscenario.missions.requirements.MissionsLog;
import rnrscenario.missions.requirements.OrRequirement;
import rnrscenario.missions.requirements.Requirement;
import rnrscenario.missions.requirements.RequirementList;
import rnrscenario.missions.requirements.RequirementsCreationException;
import scenarioUtils.Pair;
import scenarioXml.XmlFilter;

public final class RequirementsFactory {
    private static final String MISSION_NAME_ATTRIBUTE = "mission";
    private static final String FLAG_NAME_ATTRIBUTE = "name";
    private static final String UPPER_BOUND_ATTRIBUTE = "upperBound";
    private static final String LOWER_BOUND_ATTRIBUTE = "lowerBound";
    private static final String REASON_ATTRIBUTE = "reason";
    private static final String ANY_ATTRIBUTE = "any";
    private static PriorityTable priorityTable = null;

    public static void deinit() {
        priorityTable = null;
    }

    public static void setPriorityTable(PriorityTable value) {
        priorityTable = value;
    }

    private static Requirement constructFromOneString(Constructor creator, Node argumentNode) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        assert (null != creator) : "creator must be non-null reference";
        if (XmlFilter.textContentExists(argumentNode)) {
            return (Requirement)creator.newInstance(argumentNode.getTextContent());
        }
        throw new InstantiationException("no argument to constructor found in node");
    }

    private static Requirement constructFromtTwoStrings(Constructor creator, Node missionNameNode, Node reasonNode) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        assert (null != creator) : "creator must be non-null reference";
        if (XmlFilter.textContentExists(missionNameNode) && XmlFilter.textContentExists(reasonNode)) {
            String reason = reasonNode.getTextContent();
            if (0 == ANY_ATTRIBUTE.compareToIgnoreCase(reason)) {
                return (Requirement)creator.newInstance(missionNameNode.getTextContent(), null);
            }
            for (MissionsLog.Event reasonType : MissionsLog.Event.values()) {
                if (0 != reasonType.name().compareTo(reason)) continue;
                return (Requirement)creator.newInstance(missionNameNode.getTextContent(), reason);
            }
            throw new InstantiationException("invalid reason: " + reason);
        }
        throw new InstantiationException("no argument to constructor found in node");
    }

    private static Requirement constructFromtTwoDoubles(Constructor creator, Node lowerBoundNode, Node upperBoundNode) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        assert (null != creator) : "creator must be non-null reference";
        try {
            if (!XmlFilter.textContentExists(upperBoundNode) && !XmlFilter.textContentExists(lowerBoundNode)) {
                MissionsLogger.getInstance().doLog("found requirement node withoud bounds: set upper and lower to 0", Level.WARNING);
                return (Requirement)creator.newInstance(0, 0);
            }
            double firstArgument = Double.NEGATIVE_INFINITY;
            double secondArgument = Double.POSITIVE_INFINITY;
            if (XmlFilter.textContentExists(lowerBoundNode)) {
                firstArgument = Double.parseDouble(lowerBoundNode.getTextContent());
            }
            if (XmlFilter.textContentExists(upperBoundNode)) {
                secondArgument = Double.parseDouble(upperBoundNode.getTextContent());
            }
            return (Requirement)creator.newInstance(firstArgument, secondArgument);
        }
        catch (NumberFormatException e) {
            throw new InstantiationException("invalid value: " + e.getLocalizedMessage());
        }
    }

    private static Requirement makeWare(Node xmlNode) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        assert (null != xmlNode) : "xmlNode must be non-null reference";
        Class<?> requirementClass = Class.forName("rnrscenario.missions.requirements." + xmlNode.getNodeName());
        block5: for (Constructor<?> constructor : requirementClass.getDeclaredConstructors()) {
            switch (constructor.getParameterTypes().length) {
                case 0: {
                    return (Requirement)constructor.newInstance(new Object[0]);
                }
                case 1: {
                    if (constructor.getParameterTypes()[0] == String.class) {
                        Node nd = xmlNode.getAttributes().getNamedItem(MISSION_NAME_ATTRIBUTE);
                        if (nd == null) {
                            nd = xmlNode.getAttributes().getNamedItem(FLAG_NAME_ATTRIBUTE);
                        }
                        return RequirementsFactory.constructFromOneString(constructor, nd);
                    }
                    throw new InstantiationException("support 1-argument constructors only for String type");
                }
                case 2: {
                    Class<?> firstArgumentClass = constructor.getParameterTypes()[0];
                    Class<?> secondArgumentClass = constructor.getParameterTypes()[1];
                    if (firstArgumentClass == Double.TYPE && secondArgumentClass == Double.TYPE) {
                        Node upperBoundNode = xmlNode.getAttributes().getNamedItem(UPPER_BOUND_ATTRIBUTE);
                        Node lowerBoundNode = xmlNode.getAttributes().getNamedItem(LOWER_BOUND_ATTRIBUTE);
                        return RequirementsFactory.constructFromtTwoDoubles(constructor, lowerBoundNode, upperBoundNode);
                    }
                    if (firstArgumentClass != String.class || secondArgumentClass != String.class) continue block5;
                    Node missionNameNode = xmlNode.getAttributes().getNamedItem(MISSION_NAME_ATTRIBUTE);
                    Node reasonNode = xmlNode.getAttributes().getNamedItem(REASON_ATTRIBUTE);
                    return RequirementsFactory.constructFromtTwoStrings(constructor, missionNameNode, reasonNode);
                }
            }
        }
        throw new InstantiationException("no supported constructors found");
    }

    public static Requirement makeOrRequirement(Node root) throws RequirementsCreationException {
        assert (null != root) : "root must be non-null reference";
        try {
            if (0 >= root.getChildNodes().getLength()) {
                throw new IllegalArgumentException("root must have at least 1 child nodes");
            }
            NodeList orList = root.getChildNodes();
            LinkedList<Pair<OrRequirement, NodeList>> requirementsStack = new LinkedList<Pair<OrRequirement, NodeList>>();
            OrRequirement result = new OrRequirement(priorityTable);
            requirementsStack.add(new Pair<OrRequirement, NodeList>(result, orList));
            while (!requirementsStack.isEmpty()) {
                Pair alternation = (Pair)requirementsStack.removeFirst();
                XmlFilter visitor = new XmlFilter((NodeList)alternation.getSecond());
                Node conditionNode = visitor.nextElement();
                while (null != conditionNode) {
                    Requirement ware = RequirementsFactory.makeWare(conditionNode);
                    ware.setPriorityTable(priorityTable);
                    if (0 != conditionNode.getChildNodes().getLength()) {
                        AndRequirement and = new AndRequirement(priorityTable);
                        OrRequirement or = new OrRequirement(priorityTable);
                        and.addRequirement(ware);
                        and.addRequirement(or);
                        requirementsStack.add(new Pair<OrRequirement, NodeList>(or, conditionNode.getChildNodes()));
                        ((RequirementList)alternation.getFirst()).addRequirement(and);
                    } else {
                        ((RequirementList)alternation.getFirst()).addRequirement(ware);
                    }
                    conditionNode = visitor.nextElement();
                }
            }
            return result;
        }
        catch (IllegalAccessException e) {
            throw new RequirementsCreationException(e);
        }
        catch (InvocationTargetException e) {
            throw new RequirementsCreationException(e);
        }
        catch (InstantiationException e) {
            throw new RequirementsCreationException(e);
        }
        catch (ClassNotFoundException e) {
            throw new RequirementsCreationException(e);
        }
    }
}

