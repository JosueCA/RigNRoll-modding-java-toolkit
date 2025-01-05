/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import java.util.ArrayList;
import menu.BalanceUpdater;
import menu.BaseMenu;
import menu.Common;
import menu.KeyPair;
import menu.MENUText_field;
import menu.MENU_ranger;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.TextScroller;
import menu.TimeData;
import menu.menues;
import menuscript.Converts;
import menuscript.IPoPUpMenuListener;
import menuscript.OfficeTab;
import rnrconfig.IconMappings;
import rnrcore.eng;
import rnrcore.loc;

public class BigRaceQualifyPopUp
extends BaseMenu {
    private static final String XML = "..\\data\\config\\menu\\menu_race";
    private static final double FADE_DUR = 1.0;
    String s_race_name;
    String s_race_id;
    String s_race_logo_id;
    String s_controlgroup;
    int s_money;
    String s_finish;
    double s_ratingreq;
    double s_rating_prize;
    TimeData s_timeallowed;
    String s_drivername;
    double s_rating;
    int s_startposition;
    int s_stage;
    long m_balance_control = 0L;
    static TextScroller m_scroller = null;
    OfficeTab.FadeAnimationTextField m_fadeText = null;
    OfficeTab.FadeAnimation_ControlColor m_fadeColor = null;
    ArrayList<IPoPUpMenuListener> m_listeners = new ArrayList();
    long[] m_controls = null;
    String m_xmlname = null;
    String m_controlgroup_name = null;
    String m_post_fix = null;
    String m_rating_field_req = null;
    String m_rating_field = null;

    public BigRaceQualifyPopUp(int race_id) {
        this.m_controlgroup_name = "Panel - Race" + Converts.newBigRaceSuffixes(race_id) + " - QUALIFY";
        this.m_post_fix = Converts.newBigRaceSuffixes(race_id);
        this.m_xmlname = XML + this.m_post_fix + ".xml";
    }

    private void Clear() {
        this.s_race_id = null;
        this.s_race_name = null;
        this.s_race_logo_id = null;
        this.s_money = -1;
        this.s_finish = null;
        this.s_ratingreq = -1.0;
        this.s_rating_prize = -1.0;
        this.s_timeallowed = null;
        this.s_drivername = null;
        this.s_rating = -1.0;
        this.s_startposition = -1;
        this.s_stage = -1;
        this.m_rating_field_req = null;
        this.m_rating_field = null;
    }

    public void InitMenu(long _menu) {
        this.Clear();
        this.uiTools = new Common(_menu);
        this.m_controls = menues.InitXml(_menu, this.m_xmlname, this.m_controlgroup_name);
        for (int i = 1; i < this.m_controls.length; ++i) {
            menues.SetFieldName(_menu, this.m_controls[i], menues.GetFieldName(this.m_controls[i]) + this.m_post_fix);
            String parent_name = menues.GetFieldParentName(this.m_controls[i]);
            if (parent_name.compareTo(this.m_controlgroup_name) == 0) continue;
            menues.SetFieldParentName(this.m_controls[i], parent_name + this.m_post_fix);
        }
    }

    public void Init(String raceId, String raceName, int money_prize, double rating_prize, double ratingreq, String finish, TimeData timeallowed, String drivername, double driverrating, int driverstartposition, int stage2) {
        this.s_race_name = loc.getBigraceShortName(raceName);
        this.s_race_id = raceName;
        this.s_race_logo_id = raceName;
        this.s_money = money_prize;
        this.s_finish = finish;
        this.s_ratingreq = ratingreq;
        this.s_rating_prize = rating_prize;
        this.s_timeallowed = timeallowed;
        this.s_drivername = drivername;
        this.s_rating = driverrating;
        this.s_startposition = driverstartposition;
        this.s_stage = stage2;
        this.m_rating_field_req = "Required Rating - League" + Converts.newBigRaceSuffixes(eng.visualLeague());
        this.m_rating_field = "Rating - League" + Converts.newBigRaceSuffixes(eng.visualLeague());
        this.FillData(this.uiTools.s_menu);
    }

    public void AfterInitMenu(long _menu) {
        MENUText_field text = this.uiTools.FindTextField("START MESSAGE - COUNTER" + this.m_post_fix);
        if (text != null) {
            this.m_fadeText = new OfficeTab.FadeAnimationTextField(text, 1.0);
            this.m_fadeText.Start();
        }
        if ((text = this.uiTools.FindTextField("START PICTURE - COUNTER" + this.m_post_fix)) != null) {
            this.m_fadeColor = new OfficeTab.FadeAnimation_ControlColor(0, 1.0, false);
            this.m_fadeColor.AddControl(text.nativePointer);
            this.m_fadeColor.Start();
        }
        this.uiTools.SetScriptOnButton("BUTTON - YES" + this.m_post_fix, this, "OnYes");
        this.uiTools.SetScriptOnButton("BUTTON - NO" + this.m_post_fix, this, "OnNo");
        this.setShow(false);
    }

    private void setShow(boolean value) {
        if (null == this.m_controls) {
            return;
        }
        if (value) {
            for (int i = 0; i < this.m_controls.length; ++i) {
                String name = menues.GetFieldName(this.m_controls[i]);
                if (name != null && name.contains("Rating - League")) {
                    if (this.m_rating_field != null && name.compareTo(this.m_rating_field + this.m_post_fix) == 0) {
                        menues.SetShowField(this.m_controls[i], true);
                    }
                    if (this.m_rating_field_req == null || name.compareTo(this.m_rating_field_req + this.m_post_fix) != 0) continue;
                    menues.SetShowField(this.m_controls[i], true);
                    continue;
                }
                menues.SetShowField(this.m_controls[i], true);
            }
        } else {
            for (int i = 0; i < this.m_controls.length; ++i) {
                menues.SetShowField(this.m_controls[i], false);
            }
        }
    }

    public void show() {
        this.setShow(true);
    }

    public void hide() {
        this.setShow(false);
    }

    private void FillData(long _menu) {
        MENUText_field field;
        long control;
        long race_logo;
        Object object;
        KeyPair[] macro;
        MENUText_field field2;
        long control2;
        if (this.s_race_name != null && (control2 = menues.FindFieldInMenu(_menu, "Race NAME - TITLE" + this.m_post_fix)) != 0L && (field2 = menues.ConvertTextFields(control2)) != null) {
            field2.text = this.s_race_name;
            menues.UpdateField(field2);
        }
        if (this.s_money != -1 && (control2 = menues.FindFieldInMenu(_menu, "GRAN PRIX" + this.m_post_fix)) != 0L && (field2 = menues.ConvertTextFields(control2)) != null) {
            macro = new KeyPair[]{new KeyPair("MONEY_GRAN_PRIX", Converts.ConvertNumeric(this.s_money))};
            MacroKit.ApplyToTextfield(field2, macro);
        }
        if (this.s_money != -1 && (control2 = menues.FindFieldInMenu(_menu, "Prize Money - VALUE" + this.m_post_fix)) != 0L && (field2 = menues.ConvertTextFields(control2)) != null) {
            macro = new KeyPair[]{new KeyPair("MONEY_GRAN_PRIX", Converts.ConvertNumeric(this.s_money))};
            MacroKit.ApplyToTextfield(field2, macro);
        }
        if (this.s_finish != null && (control2 = menues.FindFieldInMenu(_menu, "FINISH" + this.m_post_fix)) != 0L && (field2 = menues.ConvertTextFields(control2)) != null) {
            macro = new KeyPair[]{new KeyPair("FINISH_CITY", this.s_finish)};
            MacroKit.ApplyToTextfield(field2, macro);
        }
        if (this.s_ratingreq != -1.0 && this.m_rating_field_req != null && (control2 = menues.FindFieldInMenu(_menu, this.m_rating_field_req + this.m_post_fix)) != 0L && (field2 = menues.ConvertTextFields(control2)) != null) {
            macro = new KeyPair[]{new KeyPair("PARTICIPATION_RATING", Converts.ConvertRating(this.s_ratingreq))};
            MacroKit.ApplyToTextfield(field2, macro);
        }
        if (this.s_money != -1 && this.s_rating_prize != -1.0 && this.s_race_id != null && loc.getBigraceDescription(this.s_race_id) != null && (control2 = menues.FindFieldInMenu(_menu, "Message" + this.m_post_fix)) != 0L && (field2 = menues.ConvertTextFields(control2)) != null) {
            macro = new KeyPair[]{new KeyPair("RATING_GRAND_PRIX", Converts.ConvertRating(this.s_rating_prize)), new KeyPair("MONEY_GRAN_PRIX", Converts.ConvertNumeric(this.s_money))};
            field2.text = MacroKit.Parse(loc.getBigraceDescription(this.s_race_id), macro);
            menues.UpdateField(field2);
        }
        if (this.s_timeallowed != null && (control2 = menues.FindFieldInMenu(_menu, "Time allowed" + this.m_post_fix)) != 0L && (object = menues.ConvertMenuFields(control2)) != null) {
            Converts.ConvertTimeAllowed((MENUText_field)object, this.s_timeallowed.hours, this.s_timeallowed.minutes, this.s_timeallowed.seconds);
            macro = new KeyPair[]{new KeyPair("NAME", this.s_drivername)};
        }
        if (this.s_drivername != null && (control2 = menues.FindFieldInMenu(_menu, "Name" + this.m_post_fix)) != 0L && (field2 = menues.ConvertTextFields(control2)) != null) {
            macro = new KeyPair[]{new KeyPair("NAME", this.s_drivername)};
            MacroKit.ApplyToTextfield(field2, macro);
        }
        if (this.m_rating_field != null && this.s_rating != -1.0 && (control2 = menues.FindFieldInMenu(_menu, this.m_rating_field + this.m_post_fix)) != 0L && (field2 = menues.ConvertTextFields(control2)) != null) {
            macro = new KeyPair[]{new KeyPair("RATING", Converts.ConvertRating(this.s_rating))};
            MacroKit.ApplyToTextfield(field2, macro);
        }
        if (this.s_startposition != -1 && (control2 = menues.FindFieldInMenu(_menu, "Your position" + this.m_post_fix)) != 0L && (field2 = menues.ConvertTextFields(control2)) != null) {
            macro = new KeyPair[]{new KeyPair("START_POSITION", "" + this.s_startposition)};
            MacroKit.ApplyToTextfield(field2, macro);
        }
        this.m_balance_control = menues.FindFieldInMenu(_menu, "Your balance - VALUE" + this.m_post_fix);
        if (this.m_balance_control != 0L) {
            BalanceUpdater.AddBalanceControl(this.m_balance_control);
        }
        if ((race_logo = menues.FindFieldInMenu(_menu, "THE RACE LOGOTYPE" + this.m_post_fix)) != 0L) {
            if (this.s_race_logo_id != null) {
                menues.SetShowField(race_logo, true);
                IconMappings.remapRaceLogos(this.s_race_logo_id, race_logo);
            } else {
                menues.SetShowField(race_logo, false);
            }
        }
        if (this.s_stage != -1 && (control = menues.FindFieldInMenu(_menu, "RaceStage - SYMBOL" + this.m_post_fix)) != 0L && (field = menues.ConvertTextFields(control)) != null) {
            switch (this.s_stage) {
                case 0: {
                    field.text = loc.getMENUString("common\\BigRaceStage - STAGE I - SYMBOL");
                    break;
                }
                case 1: {
                    field.text = loc.getMENUString("common\\BigRaceStage - STAGE II - SYMBOL");
                    break;
                }
                case 2: {
                    field.text = loc.getMENUString("common\\BigRaceStage - STAGE III - SYMBOL");
                    break;
                }
                default: {
                    field.text = loc.getMENUString("common\\BigRaceStage - STAGE I - SYMBOL");
                }
            }
            menues.UpdateField(field);
        }
        if (this.s_stage != -1 && (control = menues.FindFieldInMenu(_menu, "RaceStage - TEXT" + this.m_post_fix)) != 0L && (field = menues.ConvertTextFields(control)) != null) {
            switch (this.s_stage) {
                case 0: {
                    field.text = loc.getMENUString("common\\BigRaceStage - STAGE I - TEXT");
                    break;
                }
                case 1: {
                    field.text = loc.getMENUString("common\\BigRaceStage - STAGE II - TEXT");
                    break;
                }
                case 2: {
                    field.text = loc.getMENUString("common\\BigRaceStage - STAGE III - TEXT");
                    break;
                }
                default: {
                    field.text = loc.getMENUString("common\\BigRaceStage - STAGE I - TEXT");
                }
            }
            menues.UpdateField(field);
        }
        long scroller_id = menues.FindFieldInMenu(_menu, "WHrace - BIGRUN - Info - QUALIFY - tableranger" + this.m_post_fix);
        long scroller_group = menues.FindFieldInMenu(_menu, "WHrace - BIGRUN - Info - QUALIFY - tableranger GROUP" + this.m_post_fix);
        long text_id = menues.FindFieldInMenu(_menu, "Message" + this.m_post_fix);
        if (text_id != 0L && scroller_id != 0L && scroller_group != 0L) {
            MENU_ranger ranger = (MENU_ranger)menues.ConvertMenuFields(scroller_id);
            MENUText_field text = (MENUText_field)menues.ConvertMenuFields(text_id);
            if (ranger != null && text != null) {
                int texh = menues.GetTextLineHeight(text.nativePointer);
                int startbase = menues.GetBaseLine(text.nativePointer);
                int linescreen = Converts.HeightToLines(text.leny, startbase, texh);
                int linecounter = Converts.HeightToLines(menues.GetTextHeight(text.nativePointer, text.text), startbase, texh);
                m_scroller = new TextScroller(_menu, ranger, linecounter, linescreen, texh, startbase, scroller_group, true, "WHrace - BIGRUN - Info - QUALIFY - tableranger GROUP");
                m_scroller.AddTextControl(text);
            }
        }
    }

    public void DeInit() {
        if (m_scroller != null) {
            m_scroller.Deinit();
        }
        BalanceUpdater.RemoveBalanceControl(this.m_balance_control);
        if (this.m_fadeColor != null) {
            this.m_fadeColor.Finish();
        }
        this.m_fadeColor = null;
        if (this.m_fadeText != null) {
            this.m_fadeText.Finish();
        }
        this.m_fadeText = null;
        this.Clear();
    }

    public void addListener(IPoPUpMenuListener lst) {
        this.m_listeners.add(lst);
    }

    public void OnYes(long _menu, MENUsimplebutton_field button) {
        for (IPoPUpMenuListener listener : this.m_listeners) {
            listener.onAgreeclose();
        }
        this.setShow(false);
    }

    public void OnNo(long _menu, MENUsimplebutton_field button) {
        for (IPoPUpMenuListener listener : this.m_listeners) {
            listener.onCancel();
        }
        this.setShow(false);
    }
}

