/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ModelManager {
    ArrayList<ModelPresets> peopleModels = new ArrayList();

    private void sort() {
        Collections.sort(this.peopleModels, new Sortir());
    }

    public void AddModel(String name, int usageFlags, int tag, boolean isMan) {
        this.peopleModels.add(new ModelPresets(name, usageFlags, tag, isMan));
    }

    public ArrayList<ModelPresets> getModels(ArrayList<ModelPresets> avalablecollection, int sotip, int tag, int num) {
        this.sort();
        ArrayList<ModelPresets> res = new ArrayList<ModelPresets>();
        for (ModelPresets model : this.peopleModels) {
            Iterator<ModelPresets> hasthat = avalablecollection.iterator();
            boolean has_that = false;
            while (hasthat.hasNext()) {
                if (!model.equals(hasthat.next())) continue;
                has_that = true;
                break;
            }
            if (has_that || tag != model.getTag() && (model.getTag() & tag) == 0 || model.getPlaceFlags() != sotip) continue;
            res.add(model);
            if (0 != --num) continue;
            break;
        }
        return res;
    }

    public ArrayList<ModelPresets> getModels(ArrayList<ModelPresets> avalablecollection, int sotip, int tag, boolean isMan, int num) {
        if (0 == num) {
            return new ArrayList<ModelPresets>();
        }
        this.sort();
        ArrayList<ModelPresets> res = new ArrayList<ModelPresets>();
        for (ModelPresets model : this.peopleModels) {
            Iterator<ModelPresets> hasthat = avalablecollection.iterator();
            boolean has_that = false;
            while (hasthat.hasNext()) {
                if (!model.equals(hasthat.next())) continue;
                has_that = true;
                break;
            }
            if (has_that || tag != model.getTag() && (model.getTag() & tag) == 0 || (model.getPlaceFlags() & sotip) == 0 || model.is_man != isMan) continue;
            res.add(model);
            if (0 != --num) continue;
            break;
        }
        return res;
    }

    static class Sortir
    implements Comparator {
        Sortir() {
        }

        public int compare(Object arg0, Object arg1) {
            return ((ModelPresets)arg0).weigh - ((ModelPresets)arg1).weigh;
        }
    }

    public static class ModelPresets {
        String name;
        int placeFlags;
        private int tag;
        int weigh;
        boolean is_man;

        ModelPresets() {
            this.name = new String("unknown");
            this.placeFlags = 0;
            this.tag = 0;
            this.weigh = 0;
            this.is_man = true;
        }

        public ModelPresets(String model_name, int flags, int new_tag, boolean is_man) {
            this.name = new String(model_name);
            this.placeFlags = flags;
            this.tag = new_tag;
            this.weigh = 0;
            this.is_man = is_man;
        }

        public void useIt() {
            ++this.weigh;
        }

        public boolean isIs_man() {
            return this.is_man;
        }

        public void setIs_man(boolean is_man) {
            this.is_man = is_man;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPlaceFlags() {
            return this.placeFlags;
        }

        public void setPlaceFlags(int placeFlags) {
            this.placeFlags = placeFlags;
        }

        public int getTag() {
            return this.tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }

        public int getWeigh() {
            return this.weigh;
        }

        public void setWeigh(int weigh) {
            this.weigh = weigh;
        }
    }
}

