/*
 * Decompiled with CFR 0.151.
 */
package scenarioAnalysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import rnrloggers.ScenarioAnalysisLogger;
import scenarioAnalysis.AdjacencyMatrix;
import scenarioUtils.Pair;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class StaticScenarioAnalyzer {
    private static final String NODE_NAME_PRIFIX = "\tnode name == ";
    private static final int DEFAULT_CAPACITY = 8;
    private Map<String, GraphVertex> scenarioGraph;

    public StaticScenarioAnalyzer(AdjacencyMatrix scenarioGraphMatrix) throws IllegalArgumentException {
        if (null == scenarioGraphMatrix) {
            throw new IllegalArgumentException("scenarioGraph must be non-null reference");
        }
        Collection<String> vertexNames = scenarioGraphMatrix.getGraphVertexesNames();
        this.scenarioGraph = new HashMap<String, GraphVertex>(vertexNames.size());
        for (String vertexName : vertexNames) {
            this.scenarioGraph.put(vertexName, new GraphVertex(vertexName));
        }
        for (GraphVertex currentVertex : this.scenarioGraph.values()) {
            Collection<String> neighbours = scenarioGraphMatrix.getOutputEdges(currentVertex.getName());
            for (String name : neighbours) {
                currentVertex.addNeighbour(this.scenarioGraph.get(name));
            }
        }
    }

    private void setAllVertexesColotToWhite() {
        for (GraphVertex currentVertex : this.scenarioGraph.values()) {
            currentVertex.setColorWhite();
        }
    }

    private List<String> analyzeAccessibility(String ... defaultStartNodes) throws IllegalArgumentException, NoSuchElementException {
        GraphVertex startVertex = this.scenarioGraph.get("ScenarioGraphRoot");
        ScenarioAnalysisLogger.getInstance().getLog().finest("Breadth-first search on scenario graph started on ScenarioGraphRoot");
        for (String defaultStartNode : defaultStartNodes) {
            GraphVertex vertexToAddAsNeighbourToTtartVertex = this.scenarioGraph.get(defaultStartNode);
            if (null != vertexToAddAsNeighbourToTtartVertex) {
                startVertex.addNeighbour(vertexToAddAsNeighbourToTtartVertex);
                continue;
            }
            ScenarioAnalysisLogger.getInstance().getLog().warning(defaultStartNode + " not found");
        }
        LinkedList<GraphVertex> greyVertexQueue = new LinkedList<GraphVertex>();
        this.setAllVertexesColotToWhite();
        HashMap<String, GraphVertex> whiteVertexes = new HashMap<String, GraphVertex>(this.scenarioGraph);
        whiteVertexes.remove(startVertex.getName());
        startVertex.advanceColor();
        greyVertexQueue.add(startVertex);
        int step = 0;
        while (!greyVertexQueue.isEmpty()) {
            GraphVertex vertex = (GraphVertex)greyVertexQueue.getFirst();
            ScenarioAnalysisLogger.getInstance().getLog().finest("step #" + step + " on vertex " + vertex.getName());
            for (GraphVertex neighbour : vertex.getAdjacentVertexes()) {
                if (GraphVertex.VertexColor.WHITE != neighbour.getColor()) continue;
                ScenarioAnalysisLogger.getInstance().getLog().finest("breadth-first search found " + neighbour.getName());
                neighbour.advanceColor();
                greyVertexQueue.addLast(neighbour);
                whiteVertexes.remove(neighbour.getName());
            }
            vertex.advanceColor();
            greyVertexQueue.removeFirst();
            ++step;
        }
        return new ArrayList<String>(whiteVertexes.keySet());
    }

    private void depthFirstSearchVisit(GraphVertex toProcess, Collection<List<String>> cyclesStorage, LinkedList<GraphVertex> searchStack) throws IllegalArgumentException {
        if (null == toProcess || null == cyclesStorage || null == searchStack) {
            throw new IllegalArgumentException("all arguments must be non-null references");
        }
        if (GraphVertex.VertexColor.WHITE != toProcess.getColor()) {
            throw new IllegalArgumentException("toProcess must have color white");
        }
        toProcess.advanceColor();
        searchStack.addLast(toProcess);
        ScenarioAnalysisLogger.getInstance().getLog().finest("depth-first search found " + toProcess.getName() + "; deep == " + searchStack.size());
        ScenarioAnalysisLogger.getInstance().getLog().finest("PROCESSING CHILDS " + toProcess.getName());
        for (GraphVertex neighbour : toProcess.getAdjacentVertexes()) {
            if (GraphVertex.VertexColor.GREY == neighbour.getColor()) {
                LinkedList<String> detectedCycle = new LinkedList<String>();
                ListIterator<GraphVertex> iter = searchStack.listIterator(searchStack.size());
                while (iter.hasPrevious()) {
                    GraphVertex stackElement = iter.previous();
                    detectedCycle.add(stackElement.getName());
                    if (stackElement != neighbour) continue;
                    break;
                }
                if (0 < detectedCycle.size()) {
                    cyclesStorage.add(detectedCycle);
                    continue;
                }
                ScenarioAnalysisLogger.getInstance().getLog().severe("depthFirstSearchVisit: detectedCycle.size() <= 0");
                continue;
            }
            if (GraphVertex.VertexColor.WHITE != neighbour.getColor()) continue;
            this.depthFirstSearchVisit(neighbour, cyclesStorage, searchStack);
        }
        ScenarioAnalysisLogger.getInstance().getLog().finest("PROCESSING CHILDS END: " + toProcess.getName());
        searchStack.removeLast();
        toProcess.advanceColor();
    }

    private Collection<List<String>> findCycles() throws IllegalArgumentException, NoSuchElementException {
        ScenarioAnalysisLogger.getInstance().getLog().finest("Depth-first search on scenario graph started on ScenarioGraphRoot");
        GraphVertex startVertex = this.scenarioGraph.get("ScenarioGraphRoot");
        this.setAllVertexesColotToWhite();
        LinkedList<List<String>> out = new LinkedList<List<String>>();
        LinkedList<GraphVertex> searchStack = new LinkedList<GraphVertex>();
        this.depthFirstSearchVisit(startVertex, out, searchStack);
        return out;
    }

    public boolean validate(String[] defaultStartedQuest) {
        Collection<List<String>> cycles = this.findCycles();
        String[] nodesNames = new String[defaultStartedQuest.length];
        for (int i = 0; i < nodesNames.length; ++i) {
            nodesNames[i] = defaultStartedQuest[i] + "_phase_" + 1;
        }
        List<String> unreachableNodes = this.analyzeAccessibility(nodesNames);
        Pair<Boolean, Boolean> out = new Pair<Boolean, Boolean>(true, true);
        boolean scenarioGraphIsValid = true;
        Iterator<String> unreachableNode = unreachableNodes.iterator();
        while (unreachableNode.hasNext()) {
            if (!unreachableNode.next().contains("_debug_phase_")) continue;
            unreachableNode.remove();
        }
        if (!unreachableNodes.isEmpty()) {
            Collections.sort(unreachableNodes);
            ScenarioAnalysisLogger.getInstance().getLog().info("STATIC ANLYSIS FOUND UNREACHABLE STATES");
            for (String stateName : unreachableNodes) {
                ScenarioAnalysisLogger.getInstance().getLog().info(NODE_NAME_PRIFIX + stateName);
            }
            out.setFirst(false);
            scenarioGraphIsValid = false;
        }
        if (!cycles.isEmpty()) {
            ScenarioAnalysisLogger.getInstance().getLog().info("STATIC ANLYSIS FOUND CYCLES IN SCENARIO");
            boolean wasOutput = false;
            for (List<String> cycle : cycles) {
                if (cycle.isEmpty()) continue;
                wasOutput = true;
                ScenarioAnalysisLogger.getInstance().getLog().info("\tCYCLE:");
                for (String cycleVertex : cycle) {
                    ScenarioAnalysisLogger.getInstance().getLog().info("\t\tnode name == " + cycleVertex);
                }
                ScenarioAnalysisLogger.getInstance().getLog().info("\t\tnode name == " + cycle.get(0));
            }
            scenarioGraphIsValid = false;
            if (!wasOutput) {
                ScenarioAnalysisLogger.getInstance().getLog().warning("static analyser found cycles, but all empty - check java code for bugs");
            }
        }
        return scenarioGraphIsValid;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static final class GraphVertex
    implements Comparable {
        private VertexColor color;
        private String name;
        private Set<GraphVertex> adjacentVertexes;

        GraphVertex(String name) {
            if (null == name) {
                throw new IllegalArgumentException("name must be non-null reference");
            }
            this.adjacentVertexes = new HashSet<GraphVertex>(8);
            this.name = name;
            this.color = VertexColor.WHITE;
        }

        void advanceColor() {
            if (VertexColor.WHITE == this.color) {
                this.color = VertexColor.GREY;
            } else if (VertexColor.GREY == this.color) {
                this.color = VertexColor.BLACK;
            } else {
                throw new IllegalStateException("color could not be advanced");
            }
        }

        void setColorWhite() {
            this.color = VertexColor.WHITE;
        }

        String getName() {
            return this.name;
        }

        VertexColor getColor() {
            return this.color;
        }

        Set<GraphVertex> getAdjacentVertexes() {
            return Collections.unmodifiableSet(this.adjacentVertexes);
        }

        void addNeighbour(GraphVertex neighbour) {
            if (null == neighbour) {
                throw new IllegalArgumentException("neighbour must be non-null reference");
            }
            this.adjacentVertexes.add(neighbour);
        }

        public int compareTo(Object o) {
            if (null == o) {
                throw new IllegalArgumentException("o must be non-null reference");
            }
            return this.name.compareTo(((GraphVertex)o).name);
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        static enum VertexColor {
            WHITE,
            GREY,
            BLACK;

        }
    }
}

