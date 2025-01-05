/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.Vector;
import rnrcore.Suspender;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.scenetrack;
import rnrscenario.ThreadTask;

public class trackscripts {
    private Suspender suspender = null;

    public void setMonitor(Object synchronizationObject) {
        this.suspender = new Suspender(synchronizationObject);
    }

    public trackscripts(Object synchronizationMonitor) {
        this.suspender = new Suspender(synchronizationMonitor);
    }

    public trackscripts() {
        this.suspender = null;
    }

    public long CreateSceneXML(String Scenename) {
        return scenetrack.CreateSceneXML(Scenename, 25, null);
    }

    public static long CreateSceneXML(String Scenename, Object preset2) {
        return scenetrack.CreateSceneXML(Scenename, 25, preset2);
    }

    public static long CreateSceneXML(String Scenename, Vector pool, Object preset2) {
        return scenetrack.CreateSceneXMLPool(Scenename, 25, pool, preset2);
    }

    public static long CreateSceneXMLCycle(String Scenename, Vector pool, Object preset2) {
        return scenetrack.CreateSceneXMLPool(Scenename, 5, pool, preset2);
    }

    public void PlaySceneXMLThreaded(String Scenename, boolean order_to_wait_other_scene_creation) {
        long sc = scenetrack.CreateSceneXML(Scenename, 1, null);
        if (order_to_wait_other_scene_creation) {
            scenetrack.Set_waittrackcreation(order_to_wait_other_scene_creation);
        }
        this.suspender.suspend();
        scenetrack.DeleteScene(sc);
    }

    public void PlaySceneXMLThreaded(String Scenename, boolean order_to_wait_other_scene_creation, Object preset2) {
        if (eng.noNative) {
            return;
        }
        long sc = scenetrack.CreateSceneXML(Scenename, 1, preset2);
        if (order_to_wait_other_scene_creation) {
            scenetrack.Set_waittrackcreation(order_to_wait_other_scene_creation);
        }
        this.suspender.suspend();
        scenetrack.DeleteScene(sc);
    }

    public void PlaySceneXMLThreaded(String Scenename, boolean order_to_wait_other_scene_creation, Vector pool, Object preset2) {
        long sc = scenetrack.CreateSceneXMLPool(Scenename, 1, pool, preset2);
        if (order_to_wait_other_scene_creation) {
            scenetrack.Set_waittrackcreation(order_to_wait_other_scene_creation);
        }
        this.suspender.suspend();
        scenetrack.DeleteScene(sc);
    }

    public long createSceneXML(String Scenename, Vector pool, Object preset2) {
        return scenetrack.CreateSceneXMLPool(Scenename, 8, pool, preset2);
    }

    public void PlaySceneXMLThreaded(long sc, boolean order_to_wait_other_scene_creation) {
        scenetrack.UpdateSceneFlags(sc, 1);
        if (order_to_wait_other_scene_creation) {
            scenetrack.Set_waittrackcreation(order_to_wait_other_scene_creation);
        }
        this.suspender.suspend();
        scenetrack.DeleteScene(sc);
    }

    public void PlaySceneXMLThreaded(String Scenename, boolean order_to_wait_other_scene_creation, ThreadTask safe) {
        eng.lock();
        long sc = scenetrack.CreateSceneXML(Scenename, 9, null);
        event.eventObject((int)sc, safe, "_resum");
        if (order_to_wait_other_scene_creation) {
            scenetrack.Set_waittrackcreation(order_to_wait_other_scene_creation);
        }
        eng.unlock();
        safe._susp();
        scenetrack.DeleteScene(sc);
    }

    public void PlaySceneXMLThreaded(String Scenename, boolean order_to_wait_other_scene_creation, Vector pool, Object preset2, ThreadTask safe) {
        eng.lock();
        long sc = scenetrack.CreateSceneXMLPool(Scenename, 9, pool, preset2);
        event.eventObject((int)sc, safe, "_resum");
        if (order_to_wait_other_scene_creation) {
            scenetrack.Set_waittrackcreation(order_to_wait_other_scene_creation);
        }
        eng.unlock();
        safe._susp();
        scenetrack.DeleteScene(sc);
    }

    public long PlaySceneXMLThreaded_nGet(String Scenename, boolean order_to_wait_other_scene_creation) {
        long sc = scenetrack.CreateSceneXML(Scenename, 9, null);
        if (order_to_wait_other_scene_creation) {
            scenetrack.Set_waittrackcreation(order_to_wait_other_scene_creation);
        }
        this.suspender.suspend();
        return sc;
    }

    public long PlaySceneXML_nGet(String Scenename, boolean order_to_wait_other_scene_creation) {
        long sc = scenetrack.CreateSceneXML(Scenename, 9, null);
        if (order_to_wait_other_scene_creation) {
            scenetrack.Set_waittrackcreation(order_to_wait_other_scene_creation);
        }
        return sc;
    }

    public void playSceneThreaded() {
        this.suspender.suspend();
    }

    public static long initSceneXML(String Scenename, Vector pool, Object preset2) {
        eng.lock();
        long sc = scenetrack.CreateSceneXMLPool(Scenename, 256, pool, preset2);
        eng.unlock();
        return sc;
    }

    public static long initSceneXMLsuspended(String Scenename, Vector pool, Object preset2) {
        eng.lock();
        long sc = scenetrack.CreateSceneXMLPool(Scenename, 258, pool, preset2);
        eng.unlock();
        return sc;
    }

    public static void playInitedSceneThreaded(long scene, ThreadTask safe) {
        scenetrack.UpdateSceneFlags(scene, 1);
        event.eventObject((int)scene, safe, "_resum");
        safe._susp();
    }
}

