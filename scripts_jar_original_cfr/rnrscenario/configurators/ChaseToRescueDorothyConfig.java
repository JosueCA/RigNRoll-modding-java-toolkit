/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.configurators;

import org.w3c.dom.Node;
import rnrscenario.config.Config;
import scenarioXml.XmlFilter;

public final class ChaseToRescueDorothyConfig
extends Config {
    private static final String MY_NODE = "chase_rescue_dorothy";
    private final int[] timeToRescue = new int[3];
    private final double[] distanceToShoot = new double[]{100.0, 100.0, 100.0};
    private final double[] distanceToRescue = new double[]{300.0, 300.0, 300.0};
    private final double[] distanceToKill = new double[]{10.0, 10.0, 10.0};
    private int gameDifficultyLevel = 0;

    public ChaseToRescueDorothyConfig() {
        super("chases", MY_NODE, 2);
    }

    protected void loadFromNode(Node source) {
        Node distancesNode;
        String[] timings;
        if (null == source) {
            return;
        }
        XmlFilter traversal = new XmlFilter(source.getChildNodes());
        Node timingsNode = traversal.nodeNameNext("timings");
        if (null != timingsNode && null != (timings = Config.loadTripleValuesOrNull(new XmlFilter(timingsNode.getChildNodes()).nodeNameNext("time_to_rescue")))) {
            try {
                assert (timings.length == this.timeToRescue.length);
                int[] temporaryTimings = new int[timings.length];
                for (int i = 0; i < timings.length; ++i) {
                    temporaryTimings[i] = Integer.parseInt(timings[i]);
                }
                System.arraycopy(temporaryTimings, 0, this.timeToRescue, 0, timings.length);
            }
            catch (NumberFormatException e) {
                System.err.println("illigal data in scenario config file");
            }
        }
        if (null != (distancesNode = traversal.goOnStart().nodeNameNext("distances"))) {
            XmlFilter distancesNodesTraversal = new XmlFilter(distancesNode.getChildNodes());
            Config.fillTripleValues(distancesNodesTraversal.goOnStart().nodeNameNext("to_shoot"), this.distanceToShoot);
            Config.fillTripleValues(distancesNodesTraversal.goOnStart().nodeNameNext("to_rescue"), this.distanceToRescue);
            Config.fillTripleValues(distancesNodesTraversal.goOnStart().nodeNameNext("to_kill"), this.distanceToKill);
        }
    }

    protected void setGameLevel(int gameLevel) {
        this.reloadData();
        this.gameDifficultyLevel = gameLevel;
    }

    public int getTimeToRescue() {
        this.reloadData();
        return this.timeToRescue[this.gameDifficultyLevel];
    }

    public double getDistanceToRescue() {
        this.reloadData();
        return this.distanceToRescue[this.gameDifficultyLevel];
    }

    public double getDistanceToKill() {
        this.reloadData();
        return this.distanceToKill[this.gameDifficultyLevel];
    }

    public double getDistanceToShoot() {
        this.reloadData();
        return this.distanceToShoot[this.gameDifficultyLevel];
    }
}

