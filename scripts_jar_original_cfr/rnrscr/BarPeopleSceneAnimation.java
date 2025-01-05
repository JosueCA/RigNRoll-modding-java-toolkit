/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.List;
import java.util.Vector;
import players.ImodelCreate;
import rnrcore.SCRuniperson;
import rnrcore.SceneActorsPool;
import rnrcore.matrixJ;
import rnrcore.scenetrack;
import rnrcore.vectorJ;
import rnrscr.AdvancedRandom;
import rnrscr.Bar;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BarPeopleSceneAnimation {
    static int counter = 0;
    String[] ids = null;
    String[] modelNames = null;
    String sceneName;
    vectorJ shift = new vectorJ();

    private void addModelName(String modelName) {
        this.modelNames[0] = modelName;
    }

    private Vector createModelsPool(List<SCRuniperson> createdPersons) {
        Vector<SceneActorsPool> vec = new Vector<SceneActorsPool>();
        for (int i = 0; i < this.ids.length; ++i) {
            ImodelCreate creator = Bar.getModelCreator();
            SCRuniperson person = creator.create(this.modelNames[i], "" + counter++, "bar_so_model");
            createdPersons.add(person);
            vec.add(new SceneActorsPool(this.ids[i], person));
        }
        return vec;
    }

    long createScene(vectorJ position, vectorJ direction, String modelName, List<SCRuniperson> createdPersons) {
        assert (createdPersons != null);
        this.addModelName(modelName);
        Vector vec = this.createModelsPool(createdPersons);
        matrixJ M = new matrixJ(direction, new vectorJ(0.0, 0.0, 1.0));
        vectorJ realPos = position.oPlusN(matrixJ.mult(M, this.shift));
        long scene = scenetrack.CreateSceneXMLPool(this.sceneName + Bar.barType, 5, vec, new Data(M, realPos));
        scenetrack.moveSceneTime(scene, AdvancedRandom.getRandomDouble() * 15.0);
        return scene;
    }

    BarPeopleSceneAnimation(String[] ids, String[] modelNames, String sceneName) {
        assert (ids.length == modelNames.length);
        this.ids = new String[ids.length + 1];
        this.modelNames = new String[modelNames.length + 1];
        for (int i = 0; i < ids.length; ++i) {
            this.ids[i + 1] = ids[i];
            this.modelNames[i + 1] = modelNames[i];
        }
        this.ids[0] = "model";
        this.sceneName = sceneName;
    }

    BarPeopleSceneAnimation(vectorJ shift, String[] ids, String[] modelNames, String sceneName) {
        assert (ids.length == modelNames.length);
        this.ids = new String[ids.length + 1];
        this.modelNames = new String[modelNames.length + 1];
        for (int i = 0; i < ids.length; ++i) {
            this.ids[i + 1] = ids[i];
            this.modelNames[i + 1] = modelNames[i];
        }
        this.ids[0] = "model";
        this.sceneName = sceneName;
        this.shift = shift;
    }

    static class Data {
        matrixJ M;
        vectorJ P;

        Data(matrixJ M, vectorJ P) {
            this.M = M;
            this.P = P;
        }
    }
}

