/*
 * Decompiled with CFR 0.151.
 */
package menuscript.cbvideo;

import menu.MENUText_field;
import menu.MENUTruckview;
import menu.MENUsimplebutton_field;
import menu.MenuControls;
import menu.menues;
import menuscript.cbvideo.Dialogitem;
import menuscript.cbvideo.MenuCallFullDialog;
import rnrcore.Log;
import rnrscr.CBVideoAnimation;

public class CBDialogBox {
    private static final String FILE = "..\\data\\config\\menu\\menu_com.xml";
    private static final String GROUP = "CB RADIO - DIALOG BOX";
    private static final String CONTROL_PHOTO = "PHOTO";
    private static final String CONTROL_TEXT = "CALLING - 4strings";
    private static final String CONTROL_ANSWER1_PAUSE = "ANSWER 1 - 2strings - for PAUSE";
    private static final String CONTROL_ANSWER2_PAUSE = "ANSWER 2 - 2strings - for PAUSE";
    private static final String CONTROL_ANSWER1_GAME = "ANSWER 1 - 2strings";
    private static final String CONTROL_ANSWER2_GAME = "ANSWER 2 - 2strings";
    private static final String PROGRESS_BAR_BORDER = "CB RADIO - DIALOG BOX - ProgressBar Border";
    private static final String PROGRESS_BAR = "CB RADIO - DIALOG BOX - ProgressBar";
    private static final String PROGRESS_FIELD = "ANSWER - warning";
    private MenuControls controls = null;
    private long top = 0L;
    private long control_photo = 0L;
    private long control_text = 0L;
    private long control_answer1_pause = 0L;
    private long control_answer2_pause = 0L;
    private long control_answer1_game = 0L;
    private long control_answer2_game = 0L;
    private long control_progress_bar_border = 0L;
    private long control_progress_bar = 0L;
    private long control_progress_field = 0L;
    private boolean afterInit_passed = false;
    private CBVideoAnimation animation = null;
    private int init_x;
    private int init_y;
    private Dialogitem item = null;
    private MenuCallFullDialog parent = null;
    private long _menu = 0L;

    public CBDialogBox(long _menu, MenuCallFullDialog parent) {
        this._menu = _menu;
        this.controls = new MenuControls(_menu, FILE, GROUP, true);
        this.parent = parent;
        this.top = this.controls.getTopControl();
        if (this.top == 0L) {
            Log.menu("ERRORR. MenuCall. Cannot find control TOP control in xml ..\\data\\config\\menu\\menu_com.xml in group CB RADIO - DIALOG BOX");
        }
        MENUText_field field = menues.ConvertTextFields(this.top);
        this.init_x = field.pox;
        this.init_y = field.poy;
        this.control_photo = this.controls.findControl(CONTROL_PHOTO);
        if (this.control_photo == 0L) {
            Log.menu("ERRORR. MenuCall. Cannot find control PHOTOin xml ..\\data\\config\\menu\\menu_com.xml in group CB RADIO - DIALOG BOX");
        }
        this.control_text = this.controls.findControl(CONTROL_TEXT);
        if (this.control_text == 0L) {
            Log.menu("ERRORR. MenuCall. Cannot find control CALLING - 4stringsin xml ..\\data\\config\\menu\\menu_com.xml in group CB RADIO - DIALOG BOX");
        }
        this.control_answer1_pause = this.controls.findControl(CONTROL_ANSWER1_PAUSE);
        if (this.control_answer1_pause == 0L) {
            Log.menu("ERRORR. MenuCall. Cannot find control ANSWER 1 - 2strings - for PAUSEin xml ..\\data\\config\\menu\\menu_com.xml in group CB RADIO - DIALOG BOX");
        }
        this.control_answer2_pause = this.controls.findControl(CONTROL_ANSWER2_PAUSE);
        if (this.control_answer2_pause == 0L) {
            Log.menu("ERRORR. MenuCall. Cannot find control ANSWER 2 - 2strings - for PAUSEin xml ..\\data\\config\\menu\\menu_com.xml in group CB RADIO - DIALOG BOX");
        }
        this.control_answer1_game = this.controls.findControl(CONTROL_ANSWER1_GAME);
        if (this.control_answer1_game == 0L) {
            Log.menu("ERRORR. MenuCall. Cannot find control ANSWER 1 - 2stringsin xml ..\\data\\config\\menu\\menu_com.xml in group CB RADIO - DIALOG BOX");
        }
        this.control_answer2_game = this.controls.findControl(CONTROL_ANSWER2_GAME);
        if (this.control_answer2_game == 0L) {
            Log.menu("ERRORR. MenuCall. Cannot find control ANSWER 2 - 2stringsin xml ..\\data\\config\\menu\\menu_com.xml in group CB RADIO - DIALOG BOX");
        }
        this.control_progress_bar_border = this.controls.findControl(PROGRESS_BAR_BORDER);
        if (this.control_progress_bar_border == 0L) {
            Log.menu("ERRORR. MenuCall. Cannot find control CB RADIO - DIALOG BOX - ProgressBar Borderin xml ..\\data\\config\\menu\\menu_com.xml in group CB RADIO - DIALOG BOX");
        }
        this.control_progress_bar = this.controls.findControl(PROGRESS_BAR);
        if (this.control_progress_bar == 0L) {
            Log.menu("ERRORR. MenuCall. Cannot find control CB RADIO - DIALOG BOX - ProgressBarin xml ..\\data\\config\\menu\\menu_com.xml in group CB RADIO - DIALOG BOX");
        }
        this.control_progress_field = this.controls.findControl(PROGRESS_FIELD);
        if (this.control_progress_field == 0L) {
            Log.menu("ERRORR. MenuCall. Cannot find control ANSWER - warningin xml ..\\data\\config\\menu\\menu_com.xml in group CB RADIO - DIALOG BOX");
        }
    }

    public void AfterInitMenu() {
        MENUsimplebutton_field field;
        this.afterInit_passed = true;
        if (this.control_answer1_pause != 0L) {
            menues.setFocusOnControl(this.control_answer1_pause, false);
            field = menues.ConvertSimpleButton(this.control_answer1_pause);
            if (field != null) {
                menues.SetScriptOnControl(this._menu, field, this, "OnAnswer1", 4L);
            }
        }
        if (this.control_answer2_pause != 0L) {
            menues.setFocusOnControl(this.control_answer2_pause, false);
            field = menues.ConvertSimpleButton(this.control_answer2_pause);
            if (field != null) {
                menues.SetScriptOnControl(this._menu, field, this, "OnAnswer2", 4L);
            }
        }
        this.update();
    }

    public void exitMenu() {
        this.animation.finish();
    }

    public void setItem(Dialogitem item) {
        this.item = item;
        this.update();
    }

    private void update() {
        if (!this.afterInit_passed || this.item == null) {
            return;
        }
        if (this.control_answer1_pause != 0L) {
            menues.SetShowField(this.control_answer1_pause, false);
        }
        if (this.control_answer2_pause != 0L) {
            menues.SetShowField(this.control_answer2_pause, false);
        }
        if (this.control_text != 0L) {
            menues.SetShowField(this.control_text, false);
        }
        if (this.control_progress_bar_border != 0L) {
            menues.SetShowField(this.control_progress_bar_border, false);
        }
        if (this.control_progress_bar != 0L) {
            menues.SetShowField(this.control_progress_bar, false);
        }
        if (this.control_progress_field != 0L) {
            menues.SetShowField(this.control_progress_field, false);
        }
        if (this.control_answer1_game != 0L) {
            menues.SetShowField(this.control_answer1_game, false);
        }
        if (this.control_answer2_game != 0L) {
            menues.SetShowField(this.control_answer2_game, false);
        }
        if (this.animation != null) {
            this.animation.finish();
        }
        this.animation = this.item.contact != null ? CBVideoAnimation.makeAnimation((MENUTruckview)menues.ConvertMenuFields(this.control_photo), this.item.contact.gModelname()) : null;
        if (this.item.bIsAlternative) {
            if (this.item.alts != null && this.item.alts.length == 2) {
                if (this.item.answered >= 0 && this.item.answered <= 2) {
                    if (this.control_text != 0L) {
                        menues.SetShowField(this.control_text, true);
                        menues.SetFieldText(this.control_text, this.item.alts[this.item.answered]);
                        menues.UpdateMenuField(menues.ConvertMenuFields(this.control_text));
                    }
                } else {
                    if (this.control_answer1_pause != 0L) {
                        menues.SetShowField(this.control_answer1_pause, true);
                        menues.SetFieldText(this.control_answer1_pause, this.item.alts[0]);
                        menues.UpdateMenuField(menues.ConvertMenuFields(this.control_answer1_pause));
                    }
                    if (this.control_answer2_pause != 0L) {
                        menues.SetShowField(this.control_answer2_pause, true);
                        menues.SetFieldText(this.control_answer2_pause, this.item.alts[1]);
                        menues.UpdateMenuField(menues.ConvertMenuFields(this.control_answer2_pause));
                    }
                }
            }
        } else if (this.control_text != 0L) {
            menues.SetShowField(this.control_text, true);
            menues.SetFieldText(this.control_text, this.item.text);
            menues.UpdateMenuField(menues.ConvertMenuFields(this.control_text));
        }
    }

    public void OnAnswer1(long _menu, MENUsimplebutton_field button) {
        if (this.parent != null) {
            this.parent.CloseDilaogAnswer1();
        }
    }

    public void OnAnswer2(long _menu, MENUsimplebutton_field button) {
        if (this.parent != null) {
            this.parent.CloseDilaogAnswer2();
        }
    }

    public void move_x(int shift) {
        MENUText_field field = menues.ConvertTextFields(this.top);
        field.pox = this.init_x + shift;
        menues.UpdateField(field);
        menues.ConvertTextFields(this.top);
    }

    public void move_y(int shift) {
        MENUText_field field = menues.ConvertTextFields(this.top);
        field.poy = this.init_y + shift;
        menues.UpdateField(field);
        menues.ConvertTextFields(this.top);
    }

    public void show() {
        this.controls.show();
        this.update();
    }

    public void hide() {
        this.controls.hide();
    }
}

