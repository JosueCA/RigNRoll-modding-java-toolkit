/*
 * Decompiled with CFR 0.151.
 */
package menuscript.cbvideo;

import menu.JavaEvents;
import menu.KeyPair;
import menu.MENUText_field;
import menu.MENUTruckview;
import menu.MacroKit;
import menu.MenuAfterInitNarrator;
import menu.MenuControls;
import menu.menucreation;
import menu.menues;
import menuscript.cbvideo.Dialogitem;
import rnrcore.Log;
import rnrcore.TypicalAnm;
import rnrcore.eng;
import rnrscr.CBVideoAnimation;

public class MenuCall
implements menucreation {
    private static final String FILE = "..\\data\\config\\menu\\menu_com.xml";
    private static final String GROUP = "CB RADIO - CALL";
    private static final String CONTROL_PHOTO = "PHOTO";
    private static final String CONTROL_TEXT = "CALLING - 2strings";
    private static final String CONTROL_ANSWER1_GAME = "ANSWER 1 - 2strings";
    private static final String CONTROL_ANSWER2_GAME = "ANSWER 2 - 2strings";
    private static final String PROGRESS_BAR_BORDER = "CB RADIO - DIALOG BOX - ProgressBar Border";
    private static final String PROGRESS_BAR = "CB RADIO - DIALOG BOX - ProgressBar";
    private static final String PROGRESS_FIELD = "ANSWER - warning";
    private MenuControls controls = null;
    private long control_photo = 0L;
    private long control_text = 0L;
    private long control_answer1_game = 0L;
    private long control_answer2_game = 0L;
    private long control_progress_bar_border = 0L;
    private long control_progress_bar = 0L;
    private long control_progress_field = 0L;
    private boolean afterInit_passed = false;
    private CBVideoAnimation animation = null;
    private Dialogitem item = null;
    private long _menu = 0L;
    private double time_animate = 0.0;
    private boolean isAnimationRun = false;
    private boolean isAnimationFreezed = false;
    ProgressAnim anims = null;

    public void restartMenu(long _menu) {
    }

    public void InitMenu(long _menu) {
        this._menu = _menu;
        this.controls = new MenuControls(_menu, FILE, GROUP, true);
        this.control_photo = this.controls.findControl(CONTROL_PHOTO);
        if (this.control_photo == 0L) {
            Log.menu("ERRORR. MenuCall. Cannot find control PHOTOin xml ..\\data\\config\\menu\\menu_com.xml in group CB RADIO - CALL");
        }
        this.control_text = this.controls.findControl(CONTROL_TEXT);
        if (this.control_text == 0L) {
            Log.menu("ERRORR. MenuCall. Cannot find control CALLING - 2stringsin xml ..\\data\\config\\menu\\menu_com.xml in group CB RADIO - CALL");
        }
        this.control_answer1_game = this.controls.findControl(CONTROL_ANSWER1_GAME);
        if (this.control_answer1_game == 0L) {
            Log.menu("ERRORR. MenuCall. Cannot find control ANSWER 1 - 2stringsin xml ..\\data\\config\\menu\\menu_com.xml in group CB RADIO - CALL");
        }
        this.control_answer2_game = this.controls.findControl(CONTROL_ANSWER2_GAME);
        if (this.control_answer2_game == 0L) {
            Log.menu("ERRORR. MenuCall. Cannot find control ANSWER 2 - 2stringsin xml ..\\data\\config\\menu\\menu_com.xml in group CB RADIO - CALL");
        }
        this.control_progress_bar_border = this.controls.findControl(PROGRESS_BAR_BORDER);
        if (this.control_progress_bar_border == 0L) {
            Log.menu("ERRORR. MenuCall. Cannot find control CB RADIO - DIALOG BOX - ProgressBar Borderin xml ..\\data\\config\\menu\\menu_com.xml in group CB RADIO - CALL");
        }
        this.control_progress_bar = this.controls.findControl(PROGRESS_BAR);
        if (this.control_progress_bar == 0L) {
            Log.menu("ERRORR. MenuCall. Cannot find control CB RADIO - DIALOG BOX - ProgressBarin xml ..\\data\\config\\menu\\menu_com.xml in group CB RADIO - CALL");
        }
        this.control_progress_field = this.controls.findControl(PROGRESS_FIELD);
        if (this.control_progress_field == 0L) {
            Log.menu("ERRORR. MenuCall. Cannot find control ANSWER - warningin xml ..\\data\\config\\menu\\menu_com.xml in group CB RADIO - CALL");
        }
    }

    public void AfterInitMenu(long _menu) {
        this.controls.setNoFocusOnControls();
        this.afterInit_passed = true;
        if (this.control_progress_bar != 0L) {
            menues.SetShowField(this.control_progress_bar, false);
        }
        if (this.control_progress_bar_border != 0L) {
            menues.SetShowField(this.control_progress_bar_border, false);
        }
        JavaEvents.SendEvent(71, 15, this);
        this.anims = new ProgressAnim();
        this.update();
        menues.SetIgnoreEvents(menues.GetBackMenu(_menu), true);
        MenuAfterInitNarrator.justShow(_menu);
    }

    public void exitMenu(long _menu) {
        if (this.animation != null) {
            this.animation.finish();
        }
        if (this.anims != null) {
            this.anims.Exit();
        }
    }

    public String getMenuId() {
        return "cbvcallMENU";
    }

    public void SetTimeToAnswer(double time) {
        this.time_animate = time;
    }

    public void setItem(Dialogitem item) {
        this.item = item;
        this.update();
    }

    private void update() {
        if (!this.afterInit_passed || this.item == null) {
            return;
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
            menues.SetShowField(this.control_progress_field, this.isAnimationRun);
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
                    if (this.control_answer1_game != 0L) {
                        menues.SetShowField(this.control_answer1_game, true);
                        menues.SetFieldText(this.control_answer1_game, this.item.alts[0]);
                        menues.SetFieldState(this.control_answer1_game, 0);
                        menues.UpdateMenuField(menues.ConvertMenuFields(this.control_answer1_game));
                    }
                    if (this.control_answer2_game != 0L) {
                        menues.SetShowField(this.control_answer2_game, true);
                        menues.SetFieldText(this.control_answer2_game, this.item.alts[1]);
                        menues.SetFieldState(this.control_answer2_game, 1);
                        menues.UpdateMenuField(menues.ConvertMenuFields(this.control_answer2_game));
                    }
                }
            }
        } else if (this.control_text != 0L) {
            menues.SetShowField(this.control_text, true);
            menues.SetFieldText(this.control_text, this.item.text);
            menues.UpdateMenuField(menues.ConvertMenuFields(this.control_text));
        }
    }

    public void close() {
        menues.CallMenuCallBack_ExitMenu(this._menu);
    }

    public long getMenuDescriptor() {
        return this._menu;
    }

    public void hide() {
        this.controls.hide();
        this.isAnimationFreezed = true;
    }

    public void show() {
        this.controls.show();
        this.update();
        this.isAnimationFreezed = false;
    }

    private MenuCall() {
    }

    public static MenuCall create() {
        MenuCall mn = new MenuCall();
        mn._menu = menues.createSimpleMenu((menucreation)mn, 3);
        return mn;
    }

    public void startAnimation() {
        this.isAnimationRun = true;
    }

    public void stopAnimation() {
        this.isAnimationRun = false;
    }

    public boolean itAnimationRun() {
        return this.isAnimationRun;
    }

    class ProgressAnim
    extends TypicalAnm {
        double start_time = 0.0;
        double freez_time = 0.0;
        double last_time = 0.0;
        boolean menu_exited = false;

        ProgressAnim() {
            eng.CreateInfinitScriptAnimation(this);
        }

        public void Exit() {
            this.menu_exited = true;
        }

        public boolean animaterun(double _time) {
            if (this.menu_exited) {
                return true;
            }
            if (!MenuCall.this.isAnimationRun) {
                this.start_time = _time;
                this.freez_time = 0.0;
            } else if (!MenuCall.this.isAnimationFreezed) {
                MENUText_field tf;
                double time = _time - this.start_time - this.freez_time;
                if (time > MenuCall.this.time_animate || MenuCall.this.time_animate <= 0.0) {
                    MenuCall.this.isAnimationRun = false;
                } else if (MenuCall.this.control_progress_field != 0L && null != (tf = menues.ConvertTextFields(MenuCall.this.control_progress_field))) {
                    int show_time = Math.max(0, (int)Math.ceil(MenuCall.this.time_animate - time));
                    KeyPair[] keys = new KeyPair[]{new KeyPair("SEC", "" + show_time)};
                    MacroKit.ApplyToTextfield(tf, keys);
                }
            } else {
                this.freez_time += _time - this.last_time;
            }
            this.last_time = _time;
            return false;
        }
    }
}

