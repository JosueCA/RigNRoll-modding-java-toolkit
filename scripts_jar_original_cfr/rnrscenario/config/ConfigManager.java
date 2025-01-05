/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.config;

import java.util.HashMap;
import java.util.Map;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import rnrscenario.config.Config;
import rnrscenario.config.DataReloader;
import xmlutils.Node;
import xmlutils.XmlUtils;

public final class ConfigManager
implements DataReloader {
    private static ConfigManager global;
    private Map<Integer, Config> slaves = new HashMap<Integer, Config>();
    private Map<Integer, XPathExpression> xPathCache = new HashMap<Integer, XPathExpression>();
    private static String CONFIG_FILE;

    static void dropGlobal() {
        global = null;
    }

    public static ConfigManager getGlobal() {
        return global;
    }

    public static void setGlobal(ConfigManager global) {
        if (null != ConfigManager.global) {
            throw new IllegalStateException("Could not set global twice");
        }
        ConfigManager.global = global;
    }

    public static void setConfigFilePath(String path2) {
        if (null != path2) {
            CONFIG_FILE = path2;
        }
    }

    public void addConfig(Config newSlave) {
        if (null != newSlave) {
            newSlave.setDataReloader(this);
            this.slaves.put(newSlave.getUid(), newSlave);
        }
    }

    public Config getConfig(int configUid) {
        return this.slaves.get(configUid);
    }

    public void setGameLevel(int gameLevel) {
        if (Config.isGameLevelDifficultyValid(gameLevel)) {
            for (Config config : this.slaves.values()) {
                config.setGameLevel(gameLevel);
            }
        } else {
            System.err.print("AHTUNG!!! illegal game level!!!");
        }
    }

    public void reloadConfigsOnQuery(boolean doReload) {
        for (Config config : this.slaves.values()) {
            config.setNeedReloadOnQuery(doReload);
        }
    }

    private void reloadConfig(Config toReload, Node rootNode) {
        if (null == rootNode || null == toReload) {
            return;
        }
        XPathExpression xPath = this.xPathCache.get(toReload.getUid());
        if (null == xPath) {
            StringBuilder xPathQuery = new StringBuilder();
            xPathQuery.append("/scenario");
            if (null != toReload.getNodesGroup()) {
                xPathQuery.append('/');
                xPathQuery.append(toReload.getNodesGroup());
            }
            xPathQuery.append('/');
            xPathQuery.append(toReload.getNodeNameToLoadFrom());
            xPath = XmlUtils.makeXpath(xPathQuery.toString());
            this.xPathCache.put(toReload.getUid(), xPath);
        }
        try {
            org.w3c.dom.Node configNode = (org.w3c.dom.Node)xPath.evaluate(rootNode.getNode(), XPathConstants.NODE);
            toReload.loadFromNode(configNode);
        }
        catch (XPathExpressionException e) {
            System.err.print(e.getMessage());
        }
    }

    public void load() {
        Node root = XmlUtils.parse(CONFIG_FILE);
        for (Config config : this.slaves.values()) {
            this.reloadConfig(config, root);
        }
    }

    public void perform(int sourceUid) {
        this.reloadConfig(this.slaves.get(sourceUid), XmlUtils.parse(CONFIG_FILE));
    }

    static {
        CONFIG_FILE = "..\\Data\\config\\scenario_values.xml";
    }
}

