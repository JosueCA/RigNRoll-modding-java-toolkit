/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import rnrscenario.QuestItems;
import rnrscenario.scenarioscript;
import scriptActions.ScriptAction;

public class RemoveQuestItem
extends ScriptAction {
    static final long serialVersionUID = 0L;
    private static final String NONAME = "no name";
    String name = "no name";

    public RemoveQuestItem(scenarioscript scenario) {
        super(8);
    }

    public boolean validate() {
        return 0 != this.name.compareTo(NONAME);
    }

    public void act() {
        QuestItems.removeQuestItem(this.name);
    }
}

