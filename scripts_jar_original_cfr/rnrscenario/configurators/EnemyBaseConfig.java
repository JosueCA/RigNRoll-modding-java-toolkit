/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.configurators;

import java.util.logging.Level;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Node;
import rnrloggers.ScriptsLogger;
import rnrscenario.config.Config;
import xmlutils.XmlUtils;

public final class EnemyBaseConfig
extends Config {
    private static final String MY_NODE_NAME = "enemy_base";
    public static final int TIMING_BREAK_ROPE = 0;
    private static final int TOTAL_TIMINGS = 1;
    private static final double[] DEFAULT_TIMINGS = new double[1];
    private static final String[] GAME_LEVEL_ATTRIBUTES = new String[3];
    private static final XPathExpression[][] TIMINGS_XPATH_QUERIES = new XPathExpression[3][1];
    private final double[][] timingsForGameLevels = new double[3][1];
    private final XPathExpression BREAKING_DISTANCE_XPATH_QUERY;
    private double[] timings = this.timingsForGameLevels[0];
    private double breakingDistanceAfterRopeBreaking;

    public EnemyBaseConfig() {
        super("locations", MY_NODE_NAME, 0);
        EnemyBaseConfig.DEFAULT_TIMINGS[0] = 50.0;
        EnemyBaseConfig.GAME_LEVEL_ATTRIBUTES[0] = "game_level_easy";
        EnemyBaseConfig.GAME_LEVEL_ATTRIBUTES[1] = "game_level_medium";
        EnemyBaseConfig.GAME_LEVEL_ATTRIBUTES[2] = "game_level_hard";
        for (int i = 0; i < 3; ++i) {
            String xPath = String.format("./timings/time_to_break_rope/@%s", GAME_LEVEL_ATTRIBUTES[i]);
            EnemyBaseConfig.TIMINGS_XPATH_QUERIES[i][0] = XmlUtils.makeXpath(xPath);
            if (null != TIMINGS_XPATH_QUERIES[i][0]) continue;
            throw new IllegalStateException("check xPath syntax in EnemyBaseConfig static constructor");
        }
        this.BREAKING_DISTANCE_XPATH_QUERY = XmlUtils.makeXpath("./distances/drive_after_broken_rope/@length");
        for (double[] timingsForGameLevel : this.timingsForGameLevels) {
            System.arraycopy(DEFAULT_TIMINGS, 0, timingsForGameLevel, 0, timingsForGameLevel.length);
        }
    }

    public double getAfterRopeBreakingDistance() {
        this.reloadData();
        return this.breakingDistanceAfterRopeBreaking;
    }

    public double getTiming(int timingId) {
        this.reloadData();
        if (0 <= timingId && this.timings.length > timingId) {
            return this.timings[timingId];
        }
        return 0.0;
    }

    protected void loadFromNode(Node source) {
        if (null != source) {
            try {
                String breakingDistanceText = (String)this.BREAKING_DISTANCE_XPATH_QUERY.evaluate(source, XPathConstants.STRING);
                this.breakingDistanceAfterRopeBreaking = Double.parseDouble(breakingDistanceText);
            }
            catch (NumberFormatException e) {
                ScriptsLogger.getInstance().log(Level.WARNING, 4, "error loading enemy base distances config: " + e.getMessage());
            }
            catch (XPathExpressionException e) {
                ScriptsLogger.getInstance().log(Level.WARNING, 4, "error loading enemy base distances config: " + e.getMessage());
            }
            for (int gameLevel = 0; gameLevel < 3; ++gameLevel) {
                try {
                    Node timingNode = (Node)TIMINGS_XPATH_QUERIES[gameLevel][0].evaluate(source, XPathConstants.NODE);
                    if (null == timingNode) continue;
                    this.timingsForGameLevels[gameLevel][0] = Double.parseDouble(timingNode.getTextContent());
                    continue;
                }
                catch (XPathExpressionException e) {
                    ScriptsLogger.getInstance().log(Level.WARNING, 4, "error loading enemy base timings config: " + e.getMessage());
                    continue;
                }
                catch (NumberFormatException e) {
                    ScriptsLogger.getInstance().log(Level.WARNING, 4, "error loading enemy base timings config: " + e.getMessage());
                }
            }
        }
    }

    protected void setGameLevel(int gameLevel) {
        assert (0 <= gameLevel && this.timingsForGameLevels.length > gameLevel);
        this.timings = this.timingsForGameLevels[gameLevel];
    }
}

