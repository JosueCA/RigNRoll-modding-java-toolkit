/*
 * Decompiled with CFR 0.151.
 */
package menu;

import menu.menucreation;
import menu.menues;

public class ProgressIndicatorMenu
implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\specmenu.xml";
    private static final String GROUP = "PROGRESS_INDICATOR";
    static String[] lamps_mames = new String[]{"Progress Ind - LAMP 00", "Progress Ind - LAMP 01", "Progress Ind - LAMP 02", "Progress Ind - LAMP 03", "Progress Ind - LAMP 04", "Progress Ind - LAMP 05", "Progress Ind - LAMP 06", "Progress Ind - LAMP 07", "Progress Ind - LAMP 08", "Progress Ind - LAMP 09", "Progress Ind - LAMP 10", "Progress Ind - LAMP 11", "Progress Ind - LAMP 12", "Progress Ind - LAMP 13", "Progress Ind - LAMP 14", "Progress Ind - LAMP 15", "Progress Ind - LAMP 16", "Progress Ind - LAMP 17", "Progress Ind - LAMP 18", "Progress Ind - LAMP 19", "Progress Ind - LAMP 20", "Progress Ind - LAMP 21", "Progress Ind - LAMP 22", "Progress Ind - LAMP 23", "Progress Ind - LAMP 24", "Progress Ind - LAMP 25", "Progress Ind - LAMP 26", "Progress Ind - LAMP 27", "Progress Ind - LAMP 28", "Progress Ind - LAMP 29", "Progress Ind - LAMP 30", "Progress Ind - LAMP 31"};
    static long[] lamps = null;
    static TextureCoordinatesHolder holder = null;
    static long sky = 0L;

    public void restartMenu(long _menu) {
    }

    public void InitMenu(long _menu) {
        menues.InitXml(_menu, XML, GROUP);
    }

    public void AfterInitMenu(long _menu) {
        lamps = new long[lamps_mames.length];
        for (int i = 0; i < lamps_mames.length; ++i) {
            ProgressIndicatorMenu.lamps[i] = menues.FindFieldInMenu(_menu, lamps_mames[i]);
            if (lamps[i] == 0L) continue;
            menues.SetBlindess(lamps[i], true);
            menues.SetIgnoreEvents(lamps[i], true);
            menues.SetFieldState(lamps[i], 0);
        }
        holder = new TextureCoordinatesHolder();
        sky = menues.FindFieldInMenu(_menu, "Progress Ind - SKY");
        if (sky != 0L && holder != null) {
            menues.CallMappingModifications(sky, holder, "StoreTextureCoordimates");
            menues.SetBlindess(sky, true);
            menues.SetIgnoreEvents(sky, true);
        }
        menues.WindowSet_ShowCursor(_menu, false);
        menues.setShowMenu(_menu, true);
    }

    public static void SetValue(double fraction) {
        int lmas_to_show = (int)((double)lamps_mames.length * fraction + 0.5);
        lmas_to_show = Math.min(lmas_to_show, lamps_mames.length);
        for (int i = 0; i < lmas_to_show; ++i) {
            if (lamps[i] == 0L) continue;
            menues.SetFieldState(lamps[i], 1);
        }
        if (sky != 0L && holder != null) {
            holder.SetValue((float)fraction);
            menues.CallMappingModifications(sky, holder, "AnimateTextureCoordiantes");
        }
    }

    public void exitMenu(long _menu) {
    }

    public String getMenuId() {
        return "ProgressIndicatorMENU";
    }

    public static long CreateProgressIndicatorMenu() {
        return menues.createSimpleMenu((menucreation)new ProgressIndicatorMenu(), 1);
    }

    class TextureCoordinatesHolder {
        float[] uleft__23 = null;
        float[] uright_01 = null;
        float value = 0.0f;

        TextureCoordinatesHolder() {
        }

        public void StoreTextureCoordimates(int sizex, int sizey, menues.CMaterial_whithmapping[] stuff) {
            this.uright_01 = new float[stuff.length];
            this.uleft__23 = new float[stuff.length];
            for (int i = 0; i < stuff.length; ++i) {
                if (stuff[i]._state != 0 || stuff[i]._active || stuff[i].pressed || stuff[i].tex.size() <= 0) continue;
                this.uleft__23[i] = ((menues.ctexcoord_multylayer)stuff[i].tex.elementAt((int)0)).t2x;
                this.uright_01[i] = ((menues.ctexcoord_multylayer)stuff[i].tex.elementAt((int)0)).t0x;
            }
        }

        public void AnimateTextureCoordiantes(int sizex, int sizey, menues.CMaterial_whithmapping[] stuff) {
            for (int i = 0; i < stuff.length; ++i) {
                if (stuff[i].tex.size() <= 0) continue;
                ((menues.ctexcoord_multylayer)stuff[i].tex.elementAt((int)0)).t2x = this.uleft__23[i] * (56.0f + this.value * 455.0f) / 56.0f;
                ((menues.ctexcoord_multylayer)stuff[i].tex.elementAt((int)0)).t3x = this.uleft__23[i] * (56.0f + this.value * 455.0f) / 56.0f;
                ((menues.ctexcoord_multylayer)stuff[i].tex.elementAt((int)0)).t0x = this.uright_01[i] * (57.0f + this.value * 454.0f) / 57.0f;
                ((menues.ctexcoord_multylayer)stuff[i].tex.elementAt((int)0)).t1x = this.uright_01[i] * (57.0f + this.value * 454.0f) / 57.0f;
            }
        }

        public void SetValue(float _value) {
            this.value = _value;
        }
    }
}

