/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.HashMap;
import java.util.Vector;
import rnrcore.SCRuniperson;
import rnrcore.SceneActorsPool;
import rnrcore.matrixJ;
import rnrcore.scenetrack;
import rnrcore.vectorJ;
import rnrscr.AdvancedRandom;

public class AO_AnimatedModel {
    private static HashMap<Integer, Long> anmObjects = new HashMap();

    public static void Load(int id, SCRuniperson person, String sceneName) {
        Vector<SceneActorsPool> vec = new Vector<SceneActorsPool>();
        vec.add(new SceneActorsPool("model", person));
        long scene = scenetrack.CreateSceneXMLPool(sceneName, 517, vec, new preset(new matrixJ(), new vectorJ()));
        AO_AnimatedModel.changeTrackParams(sceneName, scene);
        anmObjects.put(id, scene);
    }

    public static void Unload(int id) {
        scenetrack.DeleteScene(anmObjects.get(id));
        anmObjects.remove(id);
    }

    private static void changeTrackParams(String model_name, long track) {
        AO_AnimatedModel.move_time(track, 5.0);
    }

    private static void move_time(long track, double max_time) {
        scenetrack.moveSceneTime(track, AdvancedRandom.getRandomDouble() * max_time);
    }

    static class preset {
        public matrixJ M;
        public vectorJ P;

        preset(matrixJ M, vectorJ P) {
            this.P = P;
            this.M = M;
        }
    }
}

