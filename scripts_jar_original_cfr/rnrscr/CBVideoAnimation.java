/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.Stack;
import java.util.Vector;
import menu.MENUTruckview;
import rnrcore.SCRtalkingperson;
import rnrcore.SCRuniperson;
import rnrcore.SceneActorsPool;
import rnrcore.scenetrack;

public class CBVideoAnimation {
    private long animation = 0L;
    private SCRtalkingperson person = null;
    private MENUTruckview view = null;
    private String m_suffics = "";
    public boolean finished = false;
    private static Stack<String> contacterID = null;

    private CBVideoAnimation(MENUTruckview view) {
        this.view = view;
        this.m_suffics = CBVideoAnimation.suffics();
    }

    private static String suffics() {
        if (null == contacterID) {
            contacterID = new Stack();
            for (int i = 0; i < 100; ++i) {
                CBVideoAnimation.suffics("_cbv_contacter" + i);
            }
        }
        return contacterID.pop();
    }

    private static void suffics(String res) {
        contacterID.push(res);
    }

    public void finish() {
        scenetrack.DeleteScene(this.animation);
        this.animation = 0L;
        this.view.UnBind3DModel();
        this.view = null;
        this.person.stop();
        this.person = null;
        CBVideoAnimation.suffics(this.m_suffics);
        this.finished = true;
    }

    private static final String formeSceneName(String modelname) {
        if (modelname.contains("Woman")) {
            return "Woman";
        }
        if (modelname.contains("Man_")) {
            return "Man";
        }
        return modelname;
    }

    public static final CBVideoAnimation makeAnimation(MENUTruckview view, String modelName) {
        CBVideoAnimation res = new CBVideoAnimation(view);
        res.person = new SCRtalkingperson(SCRuniperson.createNamedCBVideoPerson(modelName, modelName + res.m_suffics, null));
        SCRuniperson unipers = res.person.getPerson();
        unipers.play();
        Vector<SceneActorsPool> v = new Vector<SceneActorsPool>(1);
        v.add(new SceneActorsPool("man", unipers));
        res.animation = scenetrack.CreateSceneXMLPool(CBVideoAnimation.formeSceneName(modelName), 5, v, new preset(modelName));
        res.view.BindPerson(res.animation, unipers, modelName, 2, 0);
        return res;
    }

    static class preset {
        public String modelname;

        preset(String modelname) {
            this.modelname = modelname;
        }
    }
}

