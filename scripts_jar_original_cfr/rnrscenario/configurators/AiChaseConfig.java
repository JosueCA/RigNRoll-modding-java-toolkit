/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.configurators;

import java.util.HashMap;
import org.w3c.dom.Node;
import players.Chase;
import rnrscenario.config.Config;
import scenarioXml.XmlFilter;

public class AiChaseConfig
extends Config {
    public static final int relative = 1;
    public static final int absolute = 2;
    private static final String MY_NODE_NAME1 = "ai_chase_parameters";
    private static final String MY_NODE_NAME2 = "all_parameters";
    private final HashMap<String, ChaseParameters> chase_parameters = new HashMap();
    private final ChaseParameters default_parameters = new ChaseParameters("default");
    private int gameDifficultyLevel = 0;

    public AiChaseConfig() {
        super(MY_NODE_NAME1, MY_NODE_NAME2, 3);
    }

    protected void loadFromNode(Node source) {
        if (null == source) {
            return;
        }
        this.chase_parameters.clear();
        this.chase_parameters.put("default", this.default_parameters);
        XmlFilter traversal = new XmlFilter(source.getChildNodes());
        Node node = traversal.nextElement();
        while (null != node) {
            Node node_name;
            String name = null;
            if (node.getAttributes() != null && (node_name = node.getAttributes().getNamedItem("name")) != null) {
                name = node_name.getTextContent();
            }
            if (name != null && !this.chase_parameters.containsKey(name)) {
                XmlFilter localtraverse;
                Node mode_name;
                ChaseParameters new_parameters = new ChaseParameters(name);
                if (node.getAttributes() != null && (mode_name = node.getAttributes().getNamedItem("mode")) != null && mode_name.getTextContent() != null && "relative".compareToIgnoreCase(mode_name.getTextContent()) == 0) {
                    new_parameters.mode = 1;
                }
                if ((localtraverse = new XmlFilter(node.getChildNodes())) != null) {
                    Config.fillTripleValues(localtraverse.goOnStart().nodeNameNext("dist_ahead0"), new_parameters.dist_ahead0);
                    Config.fillTripleValues(localtraverse.goOnStart().nodeNameNext("dist_ahead2"), new_parameters.dist_ahead2);
                    Config.fillTripleValues(localtraverse.goOnStart().nodeNameNext("dist_behind0"), new_parameters.dist_behind0);
                    Config.fillTripleValues(localtraverse.goOnStart().nodeNameNext("dist_behind2"), new_parameters.dist_behind2);
                    Config.fillTripleValues(localtraverse.goOnStart().nodeNameNext("minvel"), new_parameters.minvel);
                    Config.fillTripleValues(localtraverse.goOnStart().nodeNameNext("maxvel"), new_parameters.maxvel);
                    this.chase_parameters.put(name, new_parameters);
                }
            }
            node = traversal.nextElement();
        }
    }

    protected void setGameLevel(int gameLevel) {
        this.reloadData();
        assert (gameLevel < 3);
        this.gameDifficultyLevel = gameLevel;
    }

    public void FillParameters(String name, Chase chase) {
        assert (chase != null);
        this.reloadData();
        assert (name != null);
        ChaseParameters new_parameters = this.chase_parameters.get(name);
        if (new_parameters == null) {
            new_parameters = this.default_parameters;
        }
        assert (new_parameters != null);
        chase.setMode(new_parameters.mode);
        chase.setDistAhead2(new_parameters.dist_ahead2[this.gameDifficultyLevel]);
        chase.setDistAhead0(new_parameters.dist_ahead0[this.gameDifficultyLevel]);
        chase.setDistBehind0(new_parameters.dist_behind0[this.gameDifficultyLevel]);
        chase.setDistBehind2(new_parameters.dist_behind2[this.gameDifficultyLevel]);
        chase.setMinVel(new_parameters.minvel[this.gameDifficultyLevel]);
        chase.setMaxVel(new_parameters.maxvel[this.gameDifficultyLevel]);
    }

    private static final class ChaseParameters {
        String name = null;
        public int mode = 2;
        public double[] dist_ahead0 = new double[]{10.0, 10.0, 10.0};
        public double[] dist_ahead2 = new double[]{11.0, 11.0, 11.0};
        public double[] dist_behind0 = new double[]{-10.0, -10.0, -10.0};
        public double[] dist_behind2 = new double[]{-11.0, -11.0, -11.0};
        public double[] minvel = new double[]{20.0, 30.0, 40.0};
        public double[] maxvel = new double[]{40.0, 50.0, 70.0};

        ChaseParameters(String name) {
            this.name = name;
        }
    }
}

