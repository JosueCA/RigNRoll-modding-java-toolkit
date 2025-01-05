/*
 * Decompiled with CFR 0.151.
 */
package menu;

import menu.JavaEvents;

public class Controls {
    public static boolean isControlPressed() {
        Stumb stumb = new Stumb();
        JavaEvents.SendEvent(31, 0, stumb);
        return stumb.value;
    }

    public static boolean isShiftPressed() {
        Stumb stumb = new Stumb();
        JavaEvents.SendEvent(30, 0, stumb);
        return stumb.value;
    }

    public static boolean isYesPressed() {
        Stumb stumb = new Stumb();
        JavaEvents.SendEvent(53, 0, stumb);
        return stumb.value;
    }

    public static boolean isNoPressed() {
        Stumb stumb = new Stumb();
        JavaEvents.SendEvent(54, 0, stumb);
        return stumb.value;
    }

    public static boolean isEnterPressed() {
        Stumb stumb = new Stumb();
        JavaEvents.SendEvent(82, 0, stumb);
        return stumb.value;
    }

    static class Stumb {
        boolean value = false;

        Stumb() {
        }
    }
}

