/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import java.util.logging.Level;
import rnrloggers.ScenarioLogger;
import rnrorg.IStoreorgelement;
import scriptActions.OrgAction;

public class FinishOrgAction
extends OrgAction {
    static final long serialVersionUID = 0L;

    public FinishOrgAction() {
    }

    public FinishOrgAction(String org) {
        super(org);
    }

    public void act() {
        IStoreorgelement org = this.getOrg();
        if (null != org) {
            org.finish();
            ScenarioLogger.getInstance().machineLog(Level.INFO, "action performed " + this.getClass().getName());
        } else {
            ScenarioLogger.getInstance().machineWarning("FinishOrgAction instance wasn't correctly initialized");
        }
    }
}

