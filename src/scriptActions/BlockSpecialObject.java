/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import java.util.logging.Level;
import rnrloggers.ScenarioLogger;
import rnrscenario.scenarioscript;
import scenarioUtils.StringToSOTypeConverter;
import scriptActions.ScenarioAction;

public final class BlockSpecialObject
extends ScenarioAction {
    boolean use_name = false;
    public String name = "no name";
    private String type = null;
    int tip = 0;
    boolean timed = false;
    static final int years = 65536;
    static final int monthes = 4096;
    static final int days = 256;
    static final int hours = 16;
    static final int minuts = 1;
    int timeflag = 256;
    int num_years = 0;
    int num_monthes = 0;
    int num_days = 0;
    int num_hours = 0;
    int num_minuts = 0;

    public BlockSpecialObject(scenarioscript scenario) {
        super(scenario);
    }

    public void act() {
        if (!this.validate()) {
            System.err.println("BlockSpecialObject wasn't correctly initialized");
            return;
        }
        this.tip = StringToSOTypeConverter.convert(this.type);
        if (this.timed) {
            if (0 >= (this.timeflag & 0x100) - 256) {
                if (this.use_name) {
                    this.getScript().getSoBlocker().addTimeBlocker_DAYS(this.tip, this.name, this.num_days);
                } else {
                    this.getScript().getSoBlocker().addTimeBlocker_DAYS(this.tip, this.num_days);
                }
            }
        } else if (this.use_name) {
            this.getScript().getSoBlocker().addBlocker(this.tip, this.name);
        } else {
            this.getScript().getSoBlocker().addBlocker(this.tip);
        }
        ScenarioLogger.getInstance().machineLog(Level.INFO, "action performed " + this.getClass().getName() + " name == " + this.name);
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean validate() {
        return null != this.type && null != this.name;
    }
}

