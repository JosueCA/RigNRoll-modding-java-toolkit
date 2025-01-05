/*
 * Decompiled with CFR 0.151.
 */
package players;

import players.actorveh;
import rnrscenario.config.Config;
import rnrscenario.config.ConfigManager;
import rnrscenario.configurators.AiChaseConfig;

public class Chase {
    public static final int relative = 1;
    public static final int absolute = 2;
    public int mode = 2;
    public double dist_ahead0;
    public double dist_ahead2;
    public double dist_behind0;
    public double dist_behind2;
    public double minvel;
    public double maxvel;

    public Chase() {
        this.setParameters("default");
    }

    public void setMode(int value) {
        this.mode = value;
    }

    public void setDistAhead0(double value) {
        this.dist_ahead0 = value;
    }

    public void setDistAhead2(double value) {
        this.dist_ahead2 = value;
    }

    public void setDistBehind0(double value) {
        this.dist_behind0 = value;
    }

    public void setDistBehind2(double value) {
        this.dist_behind2 = value;
    }

    public void setMinVel(double value) {
        this.minvel = value;
    }

    public void setMaxVel(double value) {
        this.maxvel = value;
    }

    public void setParameters(String name) {
        Config config = ConfigManager.getGlobal().getConfig(3);
        assert (config instanceof AiChaseConfig) : "illegal type of config";
        ((AiChaseConfig)config).FillParameters(name, this);
    }

    public void paramMadracing() {
        this.setParameters("madRacing");
    }

    public void paramMadChasing() {
        this.setParameters("madChasing");
    }

    public void paramModerateChasing() {
        this.setParameters("moderateChasing");
    }

    public void paramWeeekChasing() {
        this.setParameters("weekChasing");
    }

    public native void makechase(actorveh var1, actorveh var2);

    public native void be_ahead(actorveh var1, actorveh var2);

    public native void be_behind(actorveh var1, actorveh var2);
}

