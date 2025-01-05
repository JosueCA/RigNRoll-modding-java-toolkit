/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.Vector;
import java.util.logging.Level;
import rnrcore.scenetrack;
import rnrloggers.MissionsLogger;
import rnrscr.AdvancedRandom;
import rnrscr.camscripts;

public class SceneClipRandomizer {
    private static final String[] METHODS = new String[]{"setClipActive", "setClipPassive"};
    private static final int ACTIVE = 0;
    private static final int PASSIVE = 1;
    private String m_sceneName;
    private Clips[] m_clips;

    SceneClipRandomizer(String sceneName, Clips[] clips) {
        this.m_sceneName = sceneName;
        this.m_clips = clips;
    }

    public long createScene(int flags, Vector pool, Object preset2) {
        long scene = scenetrack.CreateSceneXMLPool(this.m_sceneName, flags, pool, preset2);
        if (scene == 0L) {
            MissionsLogger.getInstance().doLog("SceneClipRandomizer, createScene " + this.m_sceneName, Level.INFO);
        }
        for (Clips clip : this.m_clips) {
            if (clip.m_clipNames == null || clip.m_clipNames.length == 0) continue;
            int indexActive = AdvancedRandom.RandFromInreval(0, clip.m_clipNames.length - 1);
            for (int i = 0; i < clip.m_clipNames.length; ++i) {
                scenetrack.ChangeClipParam(scene, clip.m_actorName, clip.m_clipNames[i], this, indexActive == i ? METHODS[0] : METHODS[1]);
            }
        }
        return scene;
    }

    public void setClipActive(camscripts.trackclipparams pars) {
        pars.track_mute = false;
    }

    public void setClipPassive(camscripts.trackclipparams pars) {
        pars.track_mute = true;
    }

    public static class Clips {
        String m_actorName;
        String[] m_clipNames;

        Clips(String actorName, String[] clipNames) {
            this.m_actorName = actorName;
            this.m_clipNames = clipNames;
        }
    }
}

