/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.Controls;
import menu.KeyPair;
import menu.MENUText_field;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.MenuAfterInitNarrator;
import menu.Titres;
import menu.menucreation;
import menu.menues;
import rnrcore.IXMLSerializable;
import rnrcore.ScriptRef;
import rnrcore.anm;
import rnrcore.eng;
import rnrscr.IDialogListener;

public class AnswerMenu
implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\menu_bar.xml";
    private static final String GROUP = "Dialog - Two Answers";
    private static final String PROGRESS_BAR = "Dialog - Two Answers - ProgressBar";
    private static final String PROGRESS_BORDER = "Dialog - Two Answers - ProgressBar Border";
    private static final String PROGRESS_FIELD = "Dialog - Two Answers - WARNING";
    private static final String[] BUTTONS = new String[]{"Answer01", "Answer02"};
    private static final String[] ACTIONS = new String[]{"onYes", "onNo"};
    private static final String[] POSTFIXES = new String[]{" (Y)", " (N)"};
    private String[] texts;
    private long _menu;
    private long[] buttons;
    private long progress_bar = 0L;
    private long progress_border = 0L;
    private long progress_field = 0L;
    private IDialogListener listener;
    private boolean finished = false;

    public static long createAnswerMenu(String[] texts, IDialogListener listener) {
        Titres.clearTitres();
        return menues.createSimpleMenu(new AnswerMenu(texts, listener));
    }

    private AnswerMenu(String[] texts, IDialogListener listener) {
        this.texts = texts;
        this.listener = listener;
    }

    public void InitMenu(long _menu) {
        this._menu = _menu;
        menues.InitXml(_menu, XML, GROUP);
        this.buttons = new long[BUTTONS.length];
        for (int i = 0; i < BUTTONS.length; ++i) {
            this.buttons[i] = menues.FindFieldInMenu(_menu, BUTTONS[i]);
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.buttons[i]), this, ACTIONS[i], 4L);
            menues.SetFieldText(this.buttons[i], this.texts[i] + POSTFIXES[i]);
        }
        this.progress_bar = menues.FindFieldInMenu(_menu, PROGRESS_BAR);
        this.progress_border = menues.FindFieldInMenu(_menu, PROGRESS_BORDER);
        this.progress_field = menues.FindFieldInMenu(_menu, PROGRESS_FIELD);
    }

    public void AfterInitMenu(long _menu) {
        if (this.progress_bar != 0L) {
            menues.SetShowField(this.progress_bar, false);
        }
        if (this.progress_border != 0L) {
            menues.SetShowField(this.progress_border, false);
        }
        MenuAfterInitNarrator.justShowWithCursor(_menu);
        eng.CreateInfinitScriptAnimation(new WaitQnswer());
    }

    public void exitMenu(long _menu) {
        this.finished = true;
    }

    public void restartMenu(long _menu) {
    }

    public String getMenuId() {
        return "answerMENU";
    }

    public void onYes(long _menu, MENUsimplebutton_field button) {
        this.sayYes();
    }

    public void onNo(long _menu, MENUsimplebutton_field button) {
        this.sayNo();
    }

    private void sayNo() {
        this.finished = true;
        if (null != this.listener) {
            this.listener.onNo("");
        }
        menues.CallMenuCallBack_ExitMenu(this._menu);
    }

    private void sayYes() {
        this.finished = true;
        if (null != this.listener) {
            this.listener.onYes("");
        }
        menues.CallMenuCallBack_ExitMenu(this._menu);
    }

    class WaitQnswer
    implements anm {
        private ScriptRef uid = new ScriptRef();
        private final double time_count_down = 10.0;

        WaitQnswer() {
        }

        public void updateNative(int nativePointer) {
        }

        public boolean animaterun(double dt) {
            MENUText_field tf;
            if (AnswerMenu.this.finished) {
                return true;
            }
            if (Controls.isNoPressed()) {
                AnswerMenu.this.sayNo();
                return true;
            }
            if (Controls.isYesPressed()) {
                AnswerMenu.this.sayYes();
                return true;
            }
            if (dt > 10.0) {
                AnswerMenu.this.sayNo();
                return true;
            }
            if (AnswerMenu.this.progress_bar != 0L) {
                menues.SetShowField(AnswerMenu.this.progress_bar, false);
            }
            if (AnswerMenu.this.progress_border != 0L) {
                menues.SetShowField(AnswerMenu.this.progress_border, false);
            }
            if (AnswerMenu.this.progress_field != 0L && null != (tf = menues.ConvertTextFields(AnswerMenu.this.progress_field))) {
                int show_time = Math.max(0, (int)Math.ceil(10.0 - dt));
                KeyPair[] keys = new KeyPair[]{new KeyPair("SEC", "" + show_time)};
                MacroKit.ApplyToTextfield(tf, keys);
            }
            return false;
        }

        public void setUid(int value) {
            this.uid.setUid(value);
        }

        public void removeRef() {
            this.uid.removeRef(this);
        }

        public int getUid() {
            return this.uid.getUid(this);
        }

        public IXMLSerializable getXmlSerializator() {
            return null;
        }
    }
}

