/*
 * Decompiled with CFR 0.151.
 */
package menu;

import java.util.Vector;
import menu.menues;
import rnrscr.animation;

public class xmlcontrols
extends animation {
    public static final int STATE_USER = 0;
    public static final int STATE_COLOR = 1;
    public static final int STATE_CAMERA = 2;
    public static final int VIEW_INTERIOR = 0;
    public static final int VIEW_EXTERIOR = 1;
    public static final int VIEW_MODEL = 2;
    public static final int VIEWFLAG_LOAD = 2;
    public static final int VIEWFLAG_CAMERALOAD = 4;

    public int GetIndex(boolean active, boolean pressed) {
        if (pressed) {
            return 2;
        }
        return active ? 1 : 0;
    }

    public void Complex3(int pox, int poy, int sizex, int sizey, menues.CMaterial_whithmapping[] stuff) {
        int activeindex;
        int i;
        int max = 0;
        for (int i2 = 0; i2 < stuff.length; ++i2) {
            if (!stuff[i2].usepatch || stuff[i2]._state <= max) continue;
            max = stuff[i2]._state;
        }
        int[][] leftwidths = new int[++max][];
        int[][] rightwidths = new int[max][];
        for (i = 0; i < max; ++i) {
            leftwidths[i] = new int[3];
            rightwidths[i] = new int[3];
        }
        for (i = 0; i < stuff.length; ++i) {
            if (!stuff[i].usepatch) continue;
            activeindex = this.GetIndex(stuff[i]._active, stuff[i].pressed);
            if (stuff[i]._patch.tip.equals("left")) {
                leftwidths[stuff[i]._state][activeindex] = stuff[i]._patch.sx;
            }
            if (!stuff[i]._patch.tip.equals("right")) continue;
            rightwidths[stuff[i]._state][activeindex] = stuff[i]._patch.sx;
        }
        for (i = 0; i < stuff.length; ++i) {
            if (!stuff[i].usepatch) continue;
            activeindex = this.GetIndex(stuff[i]._active, stuff[i].pressed);
            int leftwidth = leftwidths[stuff[i]._state][activeindex];
            int rightwidth = rightwidths[stuff[i]._state][activeindex];
            if (stuff[i]._patch.tip.equals("left")) {
                stuff[i]._patch.x = 0;
                stuff[i]._patch.y = 0;
                stuff[i]._patch.sx = leftwidth;
                stuff[i]._patch.sy = sizey;
            }
            if (stuff[i]._patch.tip.equals("center")) {
                stuff[i]._patch.x = leftwidth;
                stuff[i]._patch.y = 0;
                stuff[i]._patch.sx = sizex - leftwidth - rightwidth;
                stuff[i]._patch.sy = sizey;
            }
            if (!stuff[i]._patch.tip.equals("right")) continue;
            stuff[i]._patch.x = sizex - rightwidth;
            stuff[i]._patch.y = 0;
            stuff[i]._patch.sx = rightwidth;
            stuff[i]._patch.sy = sizey;
        }
    }

    public void Complex3Vert(int pox, int poy, int sizex, int sizey, menues.CMaterial_whithmapping[] stuff) {
        int activeindex;
        int i;
        int max = 0;
        for (int i2 = 0; i2 < stuff.length; ++i2) {
            if (!stuff[i2].usepatch || stuff[i2]._state <= max) continue;
            max = stuff[i2]._state;
        }
        int[][] upsize = new int[++max][];
        int[][] downsize = new int[max][];
        for (i = 0; i < max; ++i) {
            upsize[i] = new int[2];
            downsize[i] = new int[2];
        }
        for (i = 0; i < stuff.length; ++i) {
            if (!stuff[i].usepatch) continue;
            activeindex = stuff[i]._active ? 1 : 0;
            if (stuff[i]._patch.tip.equals("up")) {
                upsize[stuff[i]._state][activeindex] = stuff[i]._patch.sy;
            }
            if (!stuff[i]._patch.tip.equals("down")) continue;
            downsize[stuff[i]._state][activeindex] = stuff[i]._patch.sy;
        }
        for (i = 0; i < stuff.length; ++i) {
            if (!stuff[i].usepatch) continue;
            activeindex = stuff[i]._active ? 1 : 0;
            int up = upsize[stuff[i]._state][activeindex];
            int down = downsize[stuff[i]._state][activeindex];
            if (stuff[i]._patch.tip.equals("up")) {
                stuff[i]._patch.x = 0;
                stuff[i]._patch.y = 0;
                stuff[i]._patch.sx = sizex;
                stuff[i]._patch.sy = up;
            }
            if (stuff[i]._patch.tip.equals("center")) {
                stuff[i]._patch.x = 0;
                stuff[i]._patch.y = up;
                stuff[i]._patch.sx = sizex;
                stuff[i]._patch.sy = sizey - up - down;
            }
            if (!stuff[i]._patch.tip.equals("down")) continue;
            stuff[i]._patch.x = 0;
            stuff[i]._patch.y = sizey - down;
            stuff[i]._patch.sx = sizex;
            stuff[i]._patch.sy = down;
        }
    }

    public void Complex9(int pox, int poy, int sizex, int sizey, menues.CMaterial_whithmapping[] stuff) {
        int index;
        int i;
        int maxstate = -1;
        for (int i2 = 0; i2 < stuff.length; ++i2) {
            if (!stuff[i2].usepatch || stuff[i2]._state <= maxstate) continue;
            maxstate = stuff[i2]._state;
        }
        if (maxstate == -1) {
            return;
        }
        class PatchInfo {
            int up;
            int down;
            int left;
            int right;

            PatchInfo() {
            }
        }
        PatchInfo[][] data = new PatchInfo[++maxstate][];
        for (i = 0; i < maxstate; ++i) {
            data[i] = new PatchInfo[3];
            for (int index2 = 0; index2 < 3; ++index2) {
                data[i][index2] = new PatchInfo();
            }
        }
        for (i = 0; i < stuff.length; ++i) {
            menues.CMaterial_whithmapping patch = stuff[i];
            if (!patch.usepatch) continue;
            index = this.GetIndex(patch._active, patch.pressed);
            if (patch._patch.tip.equals("leftUP")) {
                data[patch._state][index].left = patch._patch.sx;
                data[patch._state][index].up = patch._patch.sy;
            }
            if (!patch._patch.tip.equals("rightDOWN")) continue;
            data[patch._state][index].right = patch._patch.sx;
            data[patch._state][index].down = patch._patch.sy;
        }
        for (i = 0; i < stuff.length; ++i) {
            menues.CMaterial_whithmapping patch = stuff[i];
            if (!patch.usepatch) continue;
            index = this.GetIndex(patch._active, patch.pressed);
            PatchInfo info = data[patch._state][index];
            if (patch._patch.tip.equals("leftUP")) {
                patch._patch.x = 0;
                patch._patch.y = 0;
                patch._patch.sx = info.left;
                patch._patch.sy = info.up;
            }
            if (patch._patch.tip.equals("centerUP")) {
                patch._patch.x = info.left;
                patch._patch.y = 0;
                patch._patch.sx = sizex - info.left - info.right;
                patch._patch.sy = info.up;
            }
            if (patch._patch.tip.equals("rightUP")) {
                patch._patch.x = sizex - info.right;
                patch._patch.y = 0;
                patch._patch.sx = info.right;
                patch._patch.sy = info.up;
            }
            if (patch._patch.tip.equals("leftCENTER")) {
                patch._patch.x = 0;
                patch._patch.y = info.up;
                patch._patch.sx = info.left;
                patch._patch.sy = sizey - info.up - info.down;
            }
            if (patch._patch.tip.equals("centerCENTER")) {
                patch._patch.x = info.left;
                patch._patch.y = info.up;
                patch._patch.sx = sizex - info.left - info.right;
                patch._patch.sy = sizey - info.up - info.down;
            }
            if (patch._patch.tip.equals("rightCENTER")) {
                patch._patch.x = sizex - info.right;
                patch._patch.y = info.up;
                patch._patch.sx = info.right;
                patch._patch.sy = sizey - info.up - info.down;
            }
            if (patch._patch.tip.equals("leftDOWN")) {
                patch._patch.x = 0;
                patch._patch.y = sizey - info.down;
                patch._patch.sx = info.left;
                patch._patch.sy = info.down;
            }
            if (patch._patch.tip.equals("centerDOWN")) {
                patch._patch.x = info.left;
                patch._patch.y = sizey - info.down;
                patch._patch.sx = sizex - info.left - info.right;
                patch._patch.sy = info.down;
            }
            if (!patch._patch.tip.equals("rightDOWN")) continue;
            patch._patch.x = sizex - info.right;
            patch._patch.y = sizey - info.down;
            patch._patch.sx = info.right;
            patch._patch.sy = info.down;
        }
    }

    public class MENUTabGroup {
        long nativePointer;
        String nameID;
        String text;
        int ID;
        int userid;
        int Xres;
        int Yres;
        int poy;
        int pox;
        int leny;
        int lenx;
        Vector textures;
        Vector materials;
        Vector callbacks;
        String parentName;
        String parentType;
    }

    public class MENUCustomStuff {
        public long nativePointer;
        public String nameID;
        public String text;
        public int ID;
        public int userid;
        public int Xres;
        public int Yres;
        public int poy;
        public int pox;
        public int leny;
        public int lenx;
        public Vector textures;
        public Vector materials;
        public Vector callbacks;
        public String parentName;
        public String parentType;
    }

    public class MENUComboboxStuff {
        long nativePointer;
        String nameID;
        String text;
        int ID;
        int userid;
        int Xres;
        int Yres;
        int poy;
        int pox;
        int leny;
        int lenx;
        Vector textures;
        Vector materials;
        Vector callbacks;
        String parentName;
        String parentType;
    }

    public class MENUCombobox {
        long nativePointer;
        String nameID;
        String text;
        int ID;
        int userid;
        int Xres;
        int Yres;
        int poy;
        int pox;
        int leny;
        int lenx;
        Vector textures;
        Vector materials;
        Vector callbacks;
        String parentName;
        String parentType;
    }
}

