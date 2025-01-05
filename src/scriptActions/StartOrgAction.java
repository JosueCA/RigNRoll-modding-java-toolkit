/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import java.util.logging.Level;
import rnrloggers.ScenarioLogger;
import rnrorg.IStoreorgelement;
import scriptActions.OrgAction;

public class StartOrgAction
extends OrgAction {
    static final long serialVersionUID = 0L;

    public StartOrgAction() {
    }

    public StartOrgAction(String org) {
        super(org);
    }

    public void act() {
        IStoreorgelement org = this.getOrg();
        if (null != org) {
            org.start();
            ScenarioLogger.getInstance().machineLog(Level.INFO, "action performed " + this.getClass().getName());
        } else {
            ScenarioLogger.getInstance().machineWarning("StartOrgAction: failed to get org with name " + this.name);
        }
    }
}

