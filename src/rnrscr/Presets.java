/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.ArrayList;
import java.util.Iterator;
import rnrscr.ModelManager;
import rnrscr.SOPresets;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Presets
implements SOPresets {
    private ArrayList<ModelManager.ModelPresets> peopleModels = new ArrayList();

    // @Override
    public void AddModel(ArrayList<ModelManager.ModelPresets> model) {
        Iterator<ModelManager.ModelPresets> iter = model.iterator();
        while (iter.hasNext()) {
            this.AddModel(iter.next());
        }
    }

    // @Override
    public void AddModel(ModelManager.ModelPresets model) {
        model.useIt();
        this.peopleModels.add(model);
    }

    // @Override
    public ArrayList<ModelManager.ModelPresets> getModels() {
        return new ArrayList<ModelManager.ModelPresets>(this.peopleModels);
    }

    public void setModels(ArrayList<ModelManager.ModelPresets> models) {
        this.peopleModels = models;
    }

    // @Override
    public void DelModel(boolean man, int num) {
        while (num > 0) {
            boolean wasremove = false;
            for (ModelManager.ModelPresets m : this.peopleModels) {
                if (man != m.is_man) continue;
                this.peopleModels.remove(m);
                wasremove = true;
                break;
            }
            if (!wasremove) {
                return;
            }
            if (--num != 0) continue;
            return;
        }
    }
}

