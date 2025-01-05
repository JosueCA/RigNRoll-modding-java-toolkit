/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import java.util.logging.Level;
import rnrloggers.ScenarioLogger;
import rnrorg.IStoreorgelement;
import scriptActions.OrgAction;

public class FailOrgAction
extends OrgAction {
    static final long serialVersionUID = 0L;
    public int type_fail = 0;

    public FailOrgAction() {
    }

    public FailOrgAction(String org) {
        super(org);
    }

    public void act() {
        IStoreorgelement org = this.getOrg();
        if (null != org) {
            org.fail(this.type_fail);
            ScenarioLogger.getInstance().machineLog(Level.INFO, "action performed " + this.getClass().getName());
        } else {
            ScenarioLogger.getInstance().machineWarning("FailOrgAction instance wasn't correctly initialized");
        }
    }
}

