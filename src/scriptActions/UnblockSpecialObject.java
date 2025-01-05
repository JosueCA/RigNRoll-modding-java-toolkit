/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import rnrcore.eng;
import rnrloggers.ScenarioLogger;
import rnrscenario.scenarioscript;
import scenarioUtils.StringToSOTypeConverter;
import scriptActions.ScenarioAction;

public class UnblockSpecialObject
extends ScenarioAction {
    public String name = null;
    private String type = null;
    int tip = 0;

    public UnblockSpecialObject(scenarioscript scenario) {
        super(scenario);
    }

    public void act() {
        if (!this.validate()) {
            ScenarioLogger.getInstance().machineWarning("UnblockSpecialObject wasn't correctly initialized");
            return;
        }
        this.tip = StringToSOTypeConverter.convert(this.type);
        if (!eng.noNative) {
            if (null != this.name) {
                eng.unblockNamedSO(this.tip, this.name);
            } else {
                eng.unblockSO(this.tip);
            }
        }
    }

    public boolean validate() {
        return null != this.type;
    }
}

