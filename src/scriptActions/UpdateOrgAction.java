/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import rnrscenario.scenarioscript;
import scriptActions.ScenarioAction;

public class UpdateOrgAction
extends ScenarioAction {
    public String orgname = "Undefined";
    public boolean update_status = false;
    public boolean update_expired = false;
    public boolean expired = false;
    public int status = -1;

    UpdateOrgAction(scenarioscript scenario) {
        super(scenario);
        this.update_status = true;
        this.update_expired = true;
    }

    UpdateOrgAction(String orgname, Integer status, Boolean expired, scenarioscript scenario) {
        super(scenario);
        this.orgname = orgname;
        this.status = status;
        this.update_status = true;
        this.expired = expired;
        this.update_expired = true;
    }

    UpdateOrgAction(String orgname, Integer status, scenarioscript scenario) {
        super(scenario);
        this.orgname = orgname;
        this.status = status;
        this.update_status = true;
    }

    UpdateOrgAction(String orgname, Boolean expired, scenarioscript scenario) {
        super(scenario);
        this.orgname = orgname;
        this.expired = expired;
        this.update_expired = true;
    }

    public void act() {
    }
}

