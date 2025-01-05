/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import menu.JavaEvents;

public class WorldState {
    public String world;
    public String state;
    public boolean result;

    WorldState(String world) {
        this.world = world;
        this.state = "";
        this.result = false;
    }

    WorldState(String world, String state) {
        this.world = world;
        this.state = state;
        this.result = false;
    }

    public static boolean checkStateWorld_GameWorld() {
        return WorldState.checkStateWorld("game");
    }

    public static boolean checkStateWorld_VehicleChanged() {
        return WorldState.checkStateState("changevehicle");
    }

    public static boolean checkStateWorld(String world) {
        WorldState res = new WorldState(world);
        JavaEvents.SendEvent(42, 0, res);
        return res.result;
    }

    public static boolean checkStateWorld(String world, String state) {
        WorldState res = new WorldState(world, state);
        JavaEvents.SendEvent(42, 0, res);
        return res.result;
    }

    public static boolean checkStateState(String state) {
        WorldState res = new WorldState("", state);
        JavaEvents.SendEvent(42, 0, res);
        return res.result;
    }
}

