/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import menu.JavaEvents;

public class MissionAuxilSounds {
    static void PlayMissionSuccessSound() {
        JavaEvents.SendEvent(71, 18, null);
    }

    static void PlayMissionFailSound() {
        JavaEvents.SendEvent(71, 19, null);
    }
}

