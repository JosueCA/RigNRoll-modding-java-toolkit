/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.ArrayList;
import rnrscr.ModelManager;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface SOPresets {
    public void AddModel(ArrayList<ModelManager.ModelPresets> var1);

    public void AddModel(ModelManager.ModelPresets var1);

    public void DelModel(boolean var1, int var2);

    public ArrayList<ModelManager.ModelPresets> getModels();
}

