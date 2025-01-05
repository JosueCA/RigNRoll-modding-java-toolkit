/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import rnrscenario.QuestItems;
import rnrscenario.QuestSemitrailer;
import rnrscenario.scenarioscript;
import scriptActions.ScriptAction;

public class CreateQISemitailer
extends ScriptAction {
    static final long serialVersionUID = 0L;
    private static final String NONAME = "no name";
    String model = "no name";
    String place = "no name";
    String name = "no name";

    public CreateQISemitailer(scenarioscript scenario) {
        super(8);
    }

    public boolean validate() {
        return 0 != this.model.compareTo(NONAME) && 0 != this.place.compareTo(NONAME) && 0 != this.name.compareTo(NONAME);
    }

    public void act() {
        QuestSemitrailer qust = new QuestSemitrailer(this.model, this.place);
        qust.create();
        QuestItems.addQuestItem(this.name, qust);
    }
}

