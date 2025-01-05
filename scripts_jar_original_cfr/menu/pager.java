/*
 * Decompiled with CFR 0.151.
 */
package menu;

import menu.MENUText_field;
import menu.MENUsimplebutton_field;
import menu.MenuAfterInitNarrator;
import menu.menucreation;
import menu.menues;
import menuscript.Converts;

public class pager
implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\specmenu.xml";
    private static final String GROUP = "pager";
    private static final String GROUP_FM = "pager_FM";
    private static final int BACKGROUND = 0;
    private static final int PAGER_BACK_1 = 1;
    private static final int FIELD_GRADIENT = 2;
    private static final int PAGER_BACK_2 = 3;
    private static final int PAGER_TEXT = 4;
    private static final String[] CONTROLS = new String[]{"background", "PAGER - BACK", "Field GRADIENT dark", "PAGER - BACK2", "pager - text"};
    private static final String[] CONTROLS_FM = new String[]{"background_FM", "PAGER - BACK - FM", "Field GRADIENT dark - FM", "PAGER - BACK2 - FM", "pager - text - FM"};
    private static int[] start_values = null;
    private static int[] start_values_FM = null;
    private long[] controls = null;
    private long[] controls_fm = null;
    private long[] group = null;
    private long[] group_fm = null;

    public void restartMenu(long _menu) {
    }

    public void InitMenu(long _menu) {
        int i;
        this.group = menues.InitXml(_menu, XML, GROUP);
        this.group_fm = menues.InitXml(_menu, XML, GROUP_FM);
        this.controls = new long[CONTROLS.length];
        for (i = 0; i < CONTROLS.length; ++i) {
            this.controls[i] = menues.FindFieldInMenu(_menu, CONTROLS[i]);
        }
        this.controls_fm = new long[CONTROLS_FM.length];
        for (i = 0; i < CONTROLS_FM.length; ++i) {
            this.controls_fm[i] = menues.FindFieldInMenu(_menu, CONTROLS_FM[i]);
        }
    }

    public void AfterInitMenu(long _menu) {
        Object field;
        int i;
        start_values = new int[CONTROLS.length];
        block10: for (i = 0; i < CONTROLS.length; ++i) {
            if (this.controls[i] == 0L || (field = menues.ConvertMenuFields(this.controls[i])) == null) continue;
            switch (i) {
                case 0: {
                    pager.start_values[i] = ((MENUText_field)field).poy;
                    continue block10;
                }
                case 1: {
                    pager.start_values[i] = ((MENUsimplebutton_field)field).leny;
                    continue block10;
                }
                case 2: 
                case 3: 
                case 4: {
                    pager.start_values[i] = ((MENUText_field)field).leny;
                }
            }
        }
        start_values_FM = new int[CONTROLS_FM.length];
        block11: for (i = 0; i < CONTROLS_FM.length; ++i) {
            if (this.controls_fm[i] == 0L || (field = menues.ConvertMenuFields(this.controls_fm[i])) == null) continue;
            switch (i) {
                case 0: {
                    pager.start_values_FM[i] = ((MENUText_field)field).poy;
                    continue block11;
                }
                case 1: {
                    pager.start_values_FM[i] = ((MENUText_field)field).leny;
                    continue block11;
                }
                case 2: 
                case 3: 
                case 4: {
                    pager.start_values_FM[i] = ((MENUText_field)field).leny;
                }
            }
        }
        for (i = 0; i < this.group.length; ++i) {
            menues.SetBlindess(this.group[i], true);
            menues.setFocusOnControl(this.group[i], false);
            menues.SetIgnoreEvents(this.group[i], true);
        }
        for (i = 0; i < this.group_fm.length; ++i) {
            menues.SetBlindess(this.group_fm[i], true);
            menues.setFocusOnControl(this.group_fm[i], false);
            menues.SetIgnoreEvents(this.group_fm[i], true);
        }
        long window = menues.GetBackMenu(_menu);
        if (window != 0L) {
            menues.SetBlindess(window, true);
            menues.setFocusOnControl(window, false);
            menues.SetIgnoreEvents(window, true);
        }
        MenuAfterInitNarrator.justShow(_menu);
    }

    public void exitMenu(long _menu) {
    }

    public String getMenuId() {
        return "pagerMENU";
    }

    void setShow(boolean value) {
    }

    void setText(String text, boolean isFMPager) {
        Object field;
        int i;
        int add_lines;
        long text_control;
        long l = text_control = isFMPager ? this.controls_fm[4] : this.controls_fm[4];
        if (text_control == 0L || text == null) {
            return;
        }
        MENUText_field text_field = menues.ConvertTextFields(text_control);
        if (text_field == null) {
            return;
        }
        int texth = menues.GetTextHeight(text_field.nativePointer, text);
        int bl = menues.GetBaseLine(text_field.nativePointer);
        int tlh = menues.GetTextLineHeight(text_field.nativePointer);
        int lines = Converts.HeightToLines(texth, bl, tlh);
        if (!isFMPager) {
            add_lines = lines > 2 ? lines - 2 : 0;
            for (i = 0; i < CONTROLS.length; ++i) {
                if (this.controls[i] == 0L || (field = menues.ConvertMenuFields(this.controls[i])) == null) continue;
                switch (i) {
                    case 0: {
                        ((MENUText_field)field).poy = start_values[i] - 25 * add_lines;
                        break;
                    }
                    case 1: {
                        ((MENUsimplebutton_field)field).leny = start_values[i] + 25 * add_lines;
                        break;
                    }
                    case 2: 
                    case 3: 
                    case 4: {
                        ((MENUText_field)field).leny = start_values[i] + 25 * add_lines;
                    }
                }
                menues.UpdateMenuField(field);
            }
        }
        if (isFMPager) {
            add_lines = lines > 1 ? lines - 1 : 0;
            for (i = 0; i < CONTROLS_FM.length; ++i) {
                if (this.controls_fm[i] == 0L || (field = menues.ConvertMenuFields(this.controls_fm[i])) == null) continue;
                switch (i) {
                    case 0: {
                        ((MENUText_field)field).poy = start_values_FM[i] - 25 * add_lines;
                        break;
                    }
                    case 1: {
                        ((MENUText_field)field).leny = start_values_FM[i] + 25 * add_lines;
                        break;
                    }
                    case 2: 
                    case 3: 
                    case 4: {
                        ((MENUText_field)field).leny = start_values_FM[i] + 25 * add_lines;
                    }
                }
                menues.UpdateMenuField(field);
            }
        }
        if (!isFMPager) {
            menues.SetFieldText(this.controls[4], text);
            menues.SetShowField(this.controls_fm[0], false);
            menues.SetShowField(this.controls[0], true);
        } else {
            menues.SetFieldText(this.controls_fm[4], text);
            menues.SetShowField(this.controls_fm[0], true);
            menues.SetShowField(this.controls[0], false);
        }
    }
}

