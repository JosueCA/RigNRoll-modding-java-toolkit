/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rnrcore.SCRuniperson;
import rnrcore.vectorJ;
import rnrscr.AdvancedRandom;
import rnrscr.BarPeopleSceneAnimation;
import rnrscr.ModelForBarScene;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BarPeopleScene {
    private static BarPeopleSceneAnimation[] manAnimations = new BarPeopleSceneAnimation[]{new BarPeopleSceneAnimation(new vectorJ(0.0, 0.27, 0.0), new String[]{"plate", "fork"}, new String[]{"Plate1", "Fork1"}, "table_m1_bar")};
    private static BarPeopleSceneAnimation[] womanAnimations = new BarPeopleSceneAnimation[]{new BarPeopleSceneAnimation(new vectorJ(0.0, 0.18, 0.0), new String[]{"coca"}, new String[]{"Coca1"}, "table_w1_bar"), new BarPeopleSceneAnimation(new vectorJ(0.0, 0.24, 0.0), new String[]{"cup", "plate"}, new String[]{"Cup1", "Plate2"}, "table_w2_bar"), new BarPeopleSceneAnimation(new vectorJ(0.0, 0.2, 0.0), new String[]{"cup", "plate", "spoon"}, new String[]{"Cup1", "Plate2", "Spoonl1"}, "table_w3_bar"), new BarPeopleSceneAnimation(new vectorJ(0.0, 0.2, 0.0), new String[]{"plate1", "plate2", "cup", "fork"}, new String[]{"Plate1", "Plate2", "Cup1", "Fork1"}, "table_w4_bar")};
    private static BarPeopleSceneAnimation[] manBarStandAnimations = new BarPeopleSceneAnimation[]{new BarPeopleSceneAnimation(new vectorJ(0.0, 0.12, 0.0), new String[]{"glass"}, new String[]{"GlassSmall"}, "barstand_m1_bar"), new BarPeopleSceneAnimation(new vectorJ(0.0, 0.12, 0.0), new String[]{"glass"}, new String[]{"GlassSmall"}, "barstand_m2_bar")};
    private static BarPeopleSceneAnimation[] womanBarStandAnimations = new BarPeopleSceneAnimation[]{new BarPeopleSceneAnimation(new vectorJ(0.0, 0.14, 0.0), new String[]{"glass"}, new String[]{"Coca1"}, "barstand_w1_bar"), new BarPeopleSceneAnimation(new vectorJ(-0.03, 0.06, 0.0), new String[0], new String[0], "barstand_w2_bar")};
    private static BarPeopleSceneAnimation[] barmanAnimations = new BarPeopleSceneAnimation[]{new BarPeopleSceneAnimation(new vectorJ(), new String[]{"wine", "cloth"}, new String[]{"Wine1", "BarmenCloth"}, "barman_bar")};
    private static Map<Long, List<SCRuniperson>> scenePersons = new HashMap<Long, List<SCRuniperson>>();

    static List<SCRuniperson> getScenePersons(long scene) {
        if (scenePersons.containsKey(scene)) {
            return scenePersons.get(scene);
        }
        return new ArrayList<SCRuniperson>();
    }

    static void forgetScenePersons(long scene) {
        if (scenePersons.containsKey(scene)) {
            scenePersons.remove(scene);
        }
    }

    public static long[] createTableScenes(vectorJ[] poss, vectorJ[] dirs, List<ModelForBarScene> models) {
        assert (poss != null && dirs != null && poss.length == dirs.length);
        int size = models.size() > poss.length ? poss.length : models.size();
        int[] indexes = BarPeopleScene.shiffleIndexes(poss.length);
        assert (indexes.length >= size);
        long[] scenes = new long[size];
        for (int i = 0; i < size; ++i) {
            ModelForBarScene model = models.get(i);
            scenes[i] = BarPeopleScene.createRandomScene(model.isMan() ? manAnimations : womanAnimations, model.getModelName(), poss[indexes[i]], dirs[indexes[i]]);
        }
        return scenes;
    }

    public static long[] createBarStandScenes(vectorJ[] poss, vectorJ[] dirs, List<ModelForBarScene> models) {
        assert (poss != null && dirs != null && poss.length == dirs.length);
        int size = models.size() > poss.length ? poss.length : models.size();
        int[] indexes = BarPeopleScene.shiffleIndexes(poss.length);
        assert (indexes.length >= size);
        long[] scenes = new long[size];
        for (int i = 0; i < size; ++i) {
            ModelForBarScene model = models.get(i);
            scenes[i] = BarPeopleScene.createRandomScene(model.isMan() ? manBarStandAnimations : womanBarStandAnimations, model.getModelName(), poss[indexes[i]], dirs[indexes[i]]);
        }
        return scenes;
    }

    public static long[] createBarmanScenes(vectorJ[] poss, vectorJ[] dirs, List<ModelForBarScene> models) {
        assert (poss != null && dirs != null && poss.length == dirs.length);
        int size = models.size() > poss.length ? poss.length : models.size();
        int[] indexes = BarPeopleScene.shiffleIndexes(poss.length);
        assert (indexes.length >= size);
        long[] scenes = new long[size];
        for (int i = 0; i < size; ++i) {
            ModelForBarScene model = models.get(i);
            scenes[i] = BarPeopleScene.createRandomScene(barmanAnimations, model.getModelName(), poss[indexes[i]], dirs[indexes[i]]);
        }
        return scenes;
    }

    private static int[] shiffleIndexes(int size) {
        int i;
        int[] indexes = new int[size];
        for (i = 0; i < indexes.length; ++i) {
            indexes[i] = i;
        }
        for (i = 0; i < indexes.length; ++i) {
            int index1 = AdvancedRandom.RandFromInreval(0, indexes.length - 1);
            int index2 = AdvancedRandom.RandFromInreval(0, indexes.length - 1);
            if (!AdvancedRandom.probability(0.9)) continue;
            int valueHold = indexes[index1];
            indexes[index1] = indexes[index2];
            indexes[index2] = valueHold;
        }
        return indexes;
    }

    private static long createRandomScene(BarPeopleSceneAnimation[] animation2, String modelName, vectorJ position, vectorJ direction) {
        int indexAnimation = AdvancedRandom.RandFromInreval(0, animation2.length - 1);
        ArrayList<SCRuniperson> listPersonsPerScene = new ArrayList<SCRuniperson>();
        long scene = animation2[indexAnimation].createScene(position, direction, modelName, listPersonsPerScene);
        scenePersons.put(scene, listPersonsPerScene);
        return scene;
    }
}

