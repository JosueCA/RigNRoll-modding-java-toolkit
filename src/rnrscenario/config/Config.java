/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.config;

import java.util.logging.Level;
import org.w3c.dom.Node;
import rnrloggers.ScriptsLogger;
import rnrscenario.config.DataReloader;

public abstract class Config {
    public static final int GAME_LEVEL_HARD = 2;
    public static final int GAME_LEVEL_MEDIUM = 1;
    public static final int GAME_LEVEL_EASY = 0;
    private boolean needReloadOnQuery = true;
    public static final int GAME_LEVELS_COUNT = 3;
    private final String nodeNameToLoadFrom;
    private final String nodesGroup;
    private final int uid;
    private DataReloader dataReloader;
    public static final String GROUP_LOCATIONS = "locations";
    public static final String GROUP_CHASES = "chases";

    public static boolean isGameLevelDifficultyValid(int value) {
        return 0 == value || 2 == value || 1 == value;
    }

    protected Config(String groupName, String nodeToLoadFrom, int uid) {
        assert (null != nodeToLoadFrom);
        this.nodesGroup = groupName;
        this.nodeNameToLoadFrom = nodeToLoadFrom;
        this.uid = uid;
    }

    void setDataReloader(DataReloader reloader) {
        this.dataReloader = reloader;
    }

    protected final void reloadData() {
        if (this.needReloadOnQuery && null != this.dataReloader) {
            this.dataReloader.perform(this.uid);
        }
    }

    protected abstract void loadFromNode(Node var1);

    protected abstract void setGameLevel(int var1);

    public final int getUid() {
        return this.uid;
    }

    public final String getNodesGroup() {
        return this.nodesGroup;
    }

    public final String getNodeNameToLoadFrom() {
        return this.nodeNameToLoadFrom;
    }

    public boolean isNeedReloadOnQuery() {
        return this.needReloadOnQuery;
    }

    public void setNeedReloadOnQuery(boolean needReloadOnQuery) {
        this.needReloadOnQuery = needReloadOnQuery;
    }

    protected static String[] loadTripleValuesOrNull(Node source) {
        if (null == source) {
            return null;
        }
        Node easyLevelValueNode = source.getAttributes().getNamedItem("game_level_easy");
        Node mediumLevelValueNode = source.getAttributes().getNamedItem("game_level_medium");
        Node hardLevelValueNode = source.getAttributes().getNamedItem("game_level_hard");
        if (null == easyLevelValueNode || null == mediumLevelValueNode || null == hardLevelValueNode) {
            return null;
        }
        String[] result = new String[]{easyLevelValueNode.getTextContent(), mediumLevelValueNode.getTextContent(), hardLevelValueNode.getTextContent()};
        return result;
    }

    protected static void fillTripleValues(Node source, double[] values) {
        if (null == source) {
            return;
        }
        assert (null != values);
        assert (3 == values.length);
        String[] rawValues = Config.loadTripleValuesOrNull(source);
        if (null == rawValues) {
            return;
        }
        double[] result = new double[rawValues.length];
        try {
            for (int i = 0; i < rawValues.length; ++i) {
                result[i] = Double.parseDouble(rawValues[i]);
            }
        }
        catch (NumberFormatException e) {
            System.err.println("bad data in scenario config file");
            return;
        }
        System.arraycopy(result, 0, values, 0, values.length);
    }

    public static double parseDouble(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        }
        catch (NumberFormatException e) {
            ScriptsLogger.getInstance().log(Level.WARNING, 4, "error loading config: " + e.getMessage());
            return defaultValue;
        }
    }

    public static int parseInteger(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            ScriptsLogger.getInstance().log(Level.WARNING, 4, "error loading config: " + e.getMessage());
            return defaultValue;
        }
    }
}

