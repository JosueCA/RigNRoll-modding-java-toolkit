/*
 * Decompiled with CFR 0.151.
 */
package menuscript.org;

import java.util.ArrayList;
import java.util.HashMap;
import menu.MENUBase_Line;
import menu.MENU_ranger;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.menues;
import menuscript.Converts;
import menuscript.IYesNoCancelMenuListener;
import menuscript.YesNoCancelMenu;
import menuscript.org.IOrgTab;
import menuscript.org.OrganiserMenu;
import menuscript.table.IRangerListener;
import menuscript.table.Table;
import menuscript.tablewrapper.TableLine;
import menuscript.tablewrapper.TableWrapped;
import rnrcore.CoreTime;
import rnrorg.journable;
import rnrorg.journal;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class JournalPane
implements IOrgTab {
    private boolean DEBUG = OrganiserMenu.DEBUG;
    OrganiserMenu parent = null;
    private static final String XML = "..\\data\\config\\menu\\menu_com.xml";
    private static final String TABLE = "TABLEGROUP JOURNAL - 6 112";
    private static final String RANGER = "Tableranger - JOURNAL - list";
    private static final String LINE = "Tablegroup - ELEMENTS - JOURNAL";
    private static final String[] LINE_ELEMENTS = new String[]{"Journal - MessageDate READ", "Journal - Message READ", "Journal - Message READ - pic", "Journal - MessageDate UNREAD", "Journal - Message UNREAD", "Journal - Message UNREAD - pic", "Tableranger - Journal - Message - PIC FieldBlack", "Tableranger - Journal - Message - PIC BorderDark01", "Tableranger - Journal - Message - PIC BorderDark02", "Tableranger - Journal - Message - PIC BorderDark03", "Tableranger - Journal - Message"};
    private static final boolean[] LINE_ELEMENTS_RADIO = new boolean[]{true, true, true, true, true, true, false, false, false, false, false};
    private static final int LINE_READ_DATE = 0;
    private static final int LINE_READ_MESSAGE = 1;
    private static final int LINE_READ_PIC = 2;
    private static final int LINE_UNREAD_DATE = 3;
    private static final int LINE_UNREAD_MESSAGE = 4;
    private static final int LINE_UNREAD_PIC = 5;
    private static final int LINE_RANGER_GROUP0 = 6;
    private static final int LINE_RANGER_GROUP1 = 7;
    private static final int LINE_RANGER_GROUP2 = 8;
    private static final int LINE_RANGER_GROUP3 = 9;
    private static final int LINE_RANGER = 10;
    private static final String CALENDAR_TABLE = "TABLEGROUP JOURNAL DATE - 21 72";
    private static final String CALENDAR_LINE = "Tablegroup - ELEMENTS - JOURNAL DATE";
    private static final String[] CALENDAR_LINE_ELEMENTS = new String[]{"button - JOURNAL DATE - Messages NO", "button - JOURNAL DATE - Messages READ", "button - JOURNAL DATE - Messages UNREAD", "button - JOURNAL DATE - TODAY Messages NO", "button - JOURNAL DATE - TODAY Messages READ", "button - JOURNAL DATE - TODAY Messages UNREAD"};
    private static final int CALENDAR_LINE_NOMESSAGES = 0;
    private static final int CALENDAR_LINE_MESS_READ = 1;
    private static final int CALENDAR_LINE_MESS_UNREAD = 2;
    private static final int CALENDAR_LINE_TODAY_NO = 3;
    private static final int CALENDAR_LINE_TODAY_READ = 4;
    private static final int CALENDAR_LINE_TODAY_UNREAD = 5;
    private static final String[] MONTHES = new String[]{"Month01", "Month02", "Month03"};
    private final int[] decades = new int[]{0, 7, 14};
    private static final String[] ACTIONS_BUTTONS = new String[]{"JOURNAL DATE - Button - GO TO TODAY", "JOURNAL DATE - Button - LEFT", "JOURNAL DATE - Button - RIGHT"};
    private static final String[] ACTIONS_METHODS = new String[]{"onCurrent", "onLeft", "onRight"};
    private static final String[] POINTERS = new String[]{"Messages UNREAD - Pointer UP", "Messages UNREAD - Pointer DOWN"};
    private static final int POINTER_UP = 0;
    private static final int POINTER_DOWN = 1;
    private static final String QUESTION_GROUP = "MESSAGE - JOURNAL - Add to organizer";
    private static final String QUESTION_WINDOW = "MESSAGE - JOURNAL - Add to organizer";
    private JournalTable journal_table;
    private Calendar calendar_table;
    private boolean is_empty_table = true;
    private CoreTime startTime = this.DEBUG ? new CoreTime(2007, 6, 10, 1, 1) : new CoreTime();
    private CoreTime selectedTime = this.DEBUG ? new CoreTime(2007, 7, 7, 1, 1) : new CoreTime();
    private CoreTime currentTime = this.DEBUG ? new CoreTime(2007, 7, 7, 1, 1) : new CoreTime();
    private boolean can_select = true;
    private long[] pointers = null;
    private YesNoCancelMenu question;
    private JournalLine underQuestion;
    private boolean f_onEnteringFocus = false;
    private boolean f_onFocus = false;
    int linescreen_unread;
    int line_height_unread;
    int linescreen_read;
    int line_height_read;
    MENU_ranger[] rangers;
    long[] id_rangers_control_group;
    long[] id_textFieldUnread;
    MENUBase_Line baseline_unread;
    long[] id_textFieldRead;
    MENUBase_Line baseline_read;
    long _menu;

    private boolean canShowQuestion() {
        return !this.f_onEnteringFocus && this.f_onFocus;
    }

    public JournalPane(long _menu, OrganiserMenu parent) {
        int i;
        this.parent = parent;
        this.journal_table = new JournalTable(_menu);
        this.calendar_table = new Calendar(_menu);
        for (i = 0; i < ACTIONS_BUTTONS.length; ++i) {
            long button = menues.FindFieldInMenu(_menu, ACTIONS_BUTTONS[i]);
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(button), this, ACTIONS_METHODS[i], 4L);
        }
        this.pointers = new long[POINTERS.length];
        for (i = 0; i < POINTERS.length; ++i) {
            this.pointers[i] = menues.FindFieldInMenu(_menu, POINTERS[i]);
        }
        this.question = new YesNoCancelMenu(_menu, XML, "MESSAGE - JOURNAL - Add to organizer", "MESSAGE - JOURNAL - Add to organizer");
        this.question.addListener(new ListenQuestionAnswer());
        long control = menues.FindFieldInMenu(_menu, "CALL COMMUNICATOR HELP - JOURNAL");
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control), this, "ShowHelp", 4L);
    }

    @Override
    public void afterInit() {
        this.question.afterInit();
        for (long p : this.pointers) {
            menues.SetShowField(p, false);
        }
        this.journal_table.afterInit();
        this.calendar_table.afterInit();
        this.calendar_table.updateMonthes();
    }

    @Override
    public void exitMenu() {
        this.journal_table.deinit();
        this.calendar_table.deinit();
    }

    @Override
    public void enterFocus() {
        this.f_onEnteringFocus = true;
        this.journal_table.update();
        this.calendar_table.update();
        this.f_onEnteringFocus = false;
        this.f_onFocus = true;
    }

    @Override
    public void leaveFocus() {
        this.f_onFocus = false;
    }

    public void onCurrent(long _menu, MENUsimplebutton_field button) {
        this.selectedTime = this.currentTime;
        this.journal_table.selectDate();
    }

    public void onLeft(long _menu, MENUsimplebutton_field button) {
        this.calendar_table.moveCurrentOnDays(-7);
        this.calendar_table.update();
    }

    public void onRight(long _menu, MENUsimplebutton_field button) {
        this.calendar_table.moveCurrentOnDays(7);
        this.calendar_table.update();
    }

    private ArrayList<JournalLine> getAllLines() {
        ArrayList<JournalLine> res = new ArrayList<JournalLine>();
        int size = journal.getInstance().journalSize();
        for (int i = 0; i < size; ++i) {
            journable jou = journal.getInstance().get(i);
            JournalLine elem = new JournalLine();
            elem.jou = jou;
            elem.message = jou.description();
            elem.date = jou.getTime();
            elem.is_read = !jou.isQuestion();
            elem.text_high = 0;
            elem.text_high = elem.is_read ? (this.id_textFieldRead != null && this.id_textFieldRead[0] != 0L ? Converts.HeightToLines(menues.GetTextHeight(this.id_textFieldRead[0], elem.message), this.baseline_read.GetMinBaseLine(), this.line_height_read) : 0) : (this.id_textFieldUnread != null && this.id_textFieldUnread[0] != 0L ? Converts.HeightToLines(menues.GetTextHeight(this.id_textFieldUnread[0], elem.message), this.baseline_unread.GetMinBaseLine(), this.line_height_unread) : 0);
            elem.ranger_cur_value = 0;
            res.add(elem);
        }
        return res;
    }

    private ArrayList<CalendarLine> getCalendar() {
        ArrayList<CalendarLine> res = new ArrayList<CalendarLine>();
        for (int i = 0; i < 21; ++i) {
            CalendarLine line = new CalendarLine();
            line.date = new CoreTime(this.startTime);
            line.date.plus_days(i);
            JournalCalendarFilling data_fill = this.journal_table.findCalendarFilling(line.date);
            boolean bl = data_fill == null ? false : (line.has_messages = data_fill.line != null);
            line.is_read = data_fill != null && data_fill.line != null ? !data_fill.has_unread_messages : true;
            line.is_today = CoreTime.isSameDate(this.currentTime, line.date);
            res.add(line);
        }
        return res;
    }

    public void ShowHelp(long _menu, MENUsimplebutton_field button) {
        if (this.parent != null) {
            this.parent.ShowTabHelp(1, 0);
        }
    }

    class Calendar
    extends TableWrapped {
        private long[] decadeFields;
        private String[] decadeFields_text;
        private CoreTime[] monthes;
        private CalendarLine selected_line;
        private ArrayList<CalendarLine> data_calendar;

        Calendar(long _menu) {
            super(_menu, 1, 0, false, JournalPane.XML, JournalPane.CALENDAR_TABLE, null, JournalPane.CALENDAR_LINE, CALENDAR_LINE_ELEMENTS, null);
            this.decadeFields = null;
            this.decadeFields_text = null;
            this.selected_line = null;
            this.decadeFields = new long[MONTHES.length];
            this.decadeFields_text = new String[MONTHES.length];
            for (int i = 0; i < this.decadeFields.length; ++i) {
                this.decadeFields[i] = menues.FindFieldInMenu(_menu, MONTHES[i]);
                this.decadeFields_text[i] = null;
            }
            if (null == this.monthes) {
                this.monthes = new CoreTime[JournalPane.this.decades.length];
            }
        }

        private void updateMonth(int num_field, CoreTime date) {
            if (this.decadeFields_text[num_field] == null) {
                this.decadeFields_text[num_field] = menues.GetFieldText(this.decadeFields[num_field]);
            }
            menues.SetFieldText(this.decadeFields[num_field], Converts.ConvertDate(this.decadeFields_text[num_field], date.gMonth(), date.gYear()));
            menues.UpdateMenuField(menues.ConvertMenuFields(this.decadeFields[num_field]));
        }

        public void onSort(long _menu, MENUsimplebutton_field button) {
        }

        protected void reciveTableData() {
            this.data_calendar = JournalPane.this.getCalendar();
            this.selected_line = null;
            for (CalendarLine line : this.data_calendar) {
                if (!CoreTime.isSameDate(line.date, JournalPane.this.selectedTime)) continue;
                this.selected_line = line;
            }
            if (null == this.monthes) {
                this.monthes = new CoreTime[JournalPane.this.decades.length];
            }
            for (int i = 0; i < JournalPane.this.decades.length; ++i) {
                this.monthes[i] = this.data_calendar.get((int)((JournalPane)JournalPane.this).decades[i]).date;
            }
            this.TABLE_DATA.all_lines.addAll(this.data_calendar);
        }

        public void SetupLineInTable(long button, int position, TableLine table_node) {
            CalendarLine line = (CalendarLine)table_node;
            switch (position) {
                case 0: {
                    menues.SetFieldText(button, "" + line.date.gDate());
                    menues.SetShowField(button, !line.is_today && !line.has_messages);
                    break;
                }
                case 1: {
                    menues.SetShowField(button, !line.is_today && line.has_messages && line.is_read);
                    menues.SetFieldText(button, "" + line.date.gDate());
                    break;
                }
                case 2: {
                    menues.SetShowField(button, !line.is_today && line.has_messages && !line.is_read);
                    menues.SetFieldText(button, "" + line.date.gDate());
                    break;
                }
                case 3: {
                    menues.SetShowField(button, line.is_today && !line.has_messages);
                    menues.SetFieldText(button, "" + line.date.gDate());
                    break;
                }
                case 4: {
                    menues.SetShowField(button, line.is_today && line.has_messages && line.is_read);
                    menues.SetFieldText(button, "" + line.date.gDate());
                    break;
                }
                case 5: {
                    menues.SetShowField(button, line.is_today && line.has_messages && !line.is_read);
                    menues.SetFieldText(button, "" + line.date.gDate());
                }
            }
            menues.UpdateMenuField(menues.ConvertMenuFields(button));
        }

        public void updateSelectedInfo(TableLine linedata) {
            if (!JournalPane.this.can_select) {
                return;
            }
            JournalPane.this.can_select = false;
            CalendarLine line = (CalendarLine)linedata;
            JournalCalendarFilling data_fill = JournalPane.this.journal_table.findCalendarFilling(line.date);
            if (null == data_fill || null == data_fill.line) {
                JournalPane.this.can_select = true;
                return;
            }
            JournalPane.this.journal_table.selectLine(data_fill.line);
            JournalPane.this.can_select = true;
        }

        public void enterFocus(Table table) {
        }

        public void leaveFocus(Table table) {
        }

        public void updateMonthes() {
            for (int i = 0; i < this.monthes.length; ++i) {
                this.updateMonth(i, this.monthes[i]);
            }
        }

        public void moveCurrentOnDays(int days) {
            if (days > 0) {
                JournalPane.this.startTime.plus_days(days);
            } else {
                JournalPane.this.startTime.minusDays(-days);
            }
        }

        public void moveToSelectedTime() {
            if (JournalPane.this.startTime.moreThan(JournalPane.this.selectedTime) == 0) {
                return;
            }
            if (JournalPane.this.startTime.moreThan(JournalPane.this.selectedTime) > 0) {
                this.moveCurrentOnDays(-7);
                this.moveToSelectedTime();
            } else if (CoreTime.CompareByDays(JournalPane.this.selectedTime, JournalPane.this.startTime) >= 21) {
                this.moveCurrentOnDays(7);
                this.moveToSelectedTime();
            }
        }

        public void update() {
            this.updateTable();
            this.updateMonthes();
            if (null != this.selected_line) {
                this.table.select_line_by_data(this.selected_line);
            } else {
                this.table.deselectAll();
            }
        }

        public void redrawTable() {
            for (CalendarLine line : this.data_calendar) {
                JournalCalendarFilling data_fill = JournalPane.this.journal_table.findCalendarFilling(line.date);
                boolean bl = data_fill == null ? false : (line.has_messages = data_fill.line != null);
                line.is_read = data_fill != null && data_fill.line != null ? !data_fill.has_unread_messages : true;
            }
            super.redrawTable();
        }

        public void deinit() {
            this.table.deinit();
        }
    }

    class JournalTable
    extends TableWrapped
    implements IRangerListener {
        private JournalCalendarYearFilling calendarFilling;
        private JournalPointers make_pointers;
        String LINE_READ_DATE_text;
        String LINE_UNREAD_DATE_text;

        JournalTable(long __menu) {
            super(__menu, 1, false, JournalPane.XML, JournalPane.TABLE, JournalPane.RANGER, JournalPane.LINE, LINE_ELEMENTS, LINE_ELEMENTS_RADIO, null);
            this.LINE_READ_DATE_text = null;
            this.LINE_UNREAD_DATE_text = null;
            JournalPane.this._menu = __menu;
            this.table.addRangerListener(this);
            long[] id_rangers = this.table.getLineStatistics_controls("Tableranger - Journal - Message");
            JournalPane.this.rangers = new MENU_ranger[id_rangers.length];
            for (int i = 0; i < id_rangers.length; ++i) {
                JournalPane.this.rangers[i] = (MENU_ranger)menues.ConvertMenuFields(id_rangers[i]);
                JournalPane.this.rangers[i].userid = i;
                menues.SetScriptOnControl(JournalPane.this._menu, JournalPane.this.rangers[i], this, "OnRanger", 1L);
                menues.UpdateMenuField(JournalPane.this.rangers[i]);
            }
        }

        private void make_sync_group() {
            long[] ids0 = this.table.getLineStatistics_controls(LINE_ELEMENTS[0]);
            long[] ids1 = this.table.getLineStatistics_controls(LINE_ELEMENTS[1]);
            long[] ids2 = this.table.getLineStatistics_controls(LINE_ELEMENTS[2]);
            long[] ids3 = this.table.getLineStatistics_controls(LINE_ELEMENTS[3]);
            long[] ids4 = this.table.getLineStatistics_controls(LINE_ELEMENTS[4]);
            long[] ids5 = this.table.getLineStatistics_controls(LINE_ELEMENTS[5]);
            int size = Math.min(ids0.length, ids1.length);
            size = Math.min(ids2.length, size);
            size = Math.min(ids3.length, size);
            size = Math.min(ids4.length, size);
            size = Math.min(ids5.length, size);
            for (int i = 0; i < size; ++i) {
                menues.SetSyncControlActive(JournalPane.this._menu, i, ids0[i]);
                menues.SetSyncControlState(JournalPane.this._menu, i, ids0[i]);
                menues.SetSyncControlActive(JournalPane.this._menu, i, ids1[i]);
                menues.SetSyncControlState(JournalPane.this._menu, i, ids1[i]);
                menues.SetSyncControlActive(JournalPane.this._menu, i, ids2[i]);
                menues.SetSyncControlState(JournalPane.this._menu, i, ids2[i]);
                menues.SetSyncControlActive(JournalPane.this._menu, i, ids3[i]);
                menues.SetSyncControlState(JournalPane.this._menu, i, ids3[i]);
                menues.SetSyncControlActive(JournalPane.this._menu, i, ids4[i]);
                menues.SetSyncControlState(JournalPane.this._menu, i, ids4[i]);
                menues.SetSyncControlActive(JournalPane.this._menu, i, ids5[i]);
                menues.SetSyncControlState(JournalPane.this._menu, i, ids5[i]);
            }
        }

        public void onSort(long _menu, MENUsimplebutton_field button) {
        }

        private int getNumRows() {
            return this.table.getNumRows();
        }

        void OnRanger(long _menu, MENU_ranger ranger) {
            if (ranger.userid >= this.TABLE_DATA.all_lines.size() || this.getLineItem((int)ranger.userid).wheather_show) {
                JournalLine line = (JournalLine)this.getLineItem(ranger.userid);
                if (line.is_read) {
                    if (JournalPane.this.id_textFieldRead != null && JournalPane.this.id_textFieldRead[ranger.userid] != 0L) {
                        line.ranger_cur_value = ranger.current_value;
                        JournalPane.this.baseline_read.MoveBaseLine(JournalPane.this.id_textFieldRead[ranger.userid], -ranger.current_value * JournalPane.this.line_height_read);
                    }
                } else if (JournalPane.this.id_textFieldUnread != null && JournalPane.this.id_textFieldUnread[ranger.userid] != 0L) {
                    line.ranger_cur_value = ranger.current_value;
                    JournalPane.this.baseline_unread.MoveBaseLine(JournalPane.this.id_textFieldUnread[ranger.userid], -ranger.current_value * JournalPane.this.line_height_unread);
                }
            }
        }

        protected void reciveTableData() {
            ArrayList all = JournalPane.this.getAllLines();
            this.calendarFilling = new JournalCalendarYearFilling(all);
            this.make_pointers = new JournalPointers();
            for (JournalLine line : all) {
                if (line.is_read) continue;
                this.make_pointers.add(line.num);
            }
            JournalPane.this.is_empty_table = all == null || all.isEmpty();
            this.TABLE_DATA.all_lines.addAll(all);
        }

        public void SetupLineInTable(long button, int position, TableLine table_node) {
            JournalLine line = (JournalLine)table_node;
            if (position == 10 || position == 6 || position == 7 || position == 8 || position == 9) {
                if (line.is_read) {
                    if (line.text_high <= JournalPane.this.linescreen_read) {
                        menues.SetShowField(button, false);
                    } else {
                        menues.SetShowField(button, true);
                    }
                } else if (line.text_high <= JournalPane.this.linescreen_unread) {
                    menues.SetShowField(button, false);
                } else {
                    menues.SetShowField(button, true);
                }
            }
            switch (position) {
                case 0: {
                    if (line.is_read) {
                        if (this.LINE_READ_DATE_text == null) {
                            this.LINE_READ_DATE_text = menues.GetFieldText(button);
                        }
                        menues.SetFieldText(button, Converts.ConvertDateAbsolute(this.LINE_READ_DATE_text, line.date.gMonth(), line.date.gDate(), line.date.gYear(), line.date.gHour(), line.date.gMinute()));
                        menues.SetShowField(button, true);
                        break;
                    }
                    menues.SetShowField(button, false);
                    break;
                }
                case 1: {
                    if (line.is_read) {
                        menues.SetFieldText(button, line.message);
                        menues.SetShowField(button, true);
                        if (JournalPane.this.baseline_unread == null) break;
                        JournalPane.this.baseline_unread.MoveBaseLine(button, -line.ranger_cur_value * JournalPane.this.line_height_unread);
                        break;
                    }
                    menues.SetShowField(button, false);
                    break;
                }
                case 2: {
                    if (line.is_read) {
                        menues.SetShowField(button, true);
                        break;
                    }
                    menues.SetShowField(button, false);
                    break;
                }
                case 3: {
                    if (line.is_read) {
                        menues.SetShowField(button, false);
                        break;
                    }
                    if (this.LINE_UNREAD_DATE_text == null) {
                        this.LINE_UNREAD_DATE_text = menues.GetFieldText(button);
                    }
                    menues.SetFieldText(button, Converts.ConvertDateAbsolute(this.LINE_UNREAD_DATE_text, line.date.gMonth(), line.date.gDate(), line.date.gYear(), line.date.gHour(), line.date.gMinute()));
                    menues.SetShowField(button, true);
                    break;
                }
                case 4: {
                    if (line.is_read) {
                        menues.SetShowField(button, false);
                        break;
                    }
                    menues.SetFieldText(button, line.message);
                    menues.SetShowField(button, true);
                    if (JournalPane.this.baseline_unread == null) break;
                    JournalPane.this.baseline_unread.MoveBaseLine(button, -line.ranger_cur_value * JournalPane.this.line_height_unread);
                    break;
                }
                case 5: {
                    if (line.is_read) {
                        menues.SetShowField(button, false);
                        break;
                    }
                    menues.SetShowField(button, true);
                    break;
                }
                case 10: {
                    MENU_ranger ranger = menues.ConvertRanger(button);
                    if (line.is_read) {
                        if (line.text_high > JournalPane.this.linescreen_read) {
                            ranger.min_value = 0;
                            ranger.max_value = line.text_high - JournalPane.this.linescreen_read;
                            ranger.page = JournalPane.this.linescreen_read;
                            ranger.current_value = line.ranger_cur_value;
                        } else {
                            ranger.min_value = 0;
                            ranger.current_value = 0;
                            ranger.max_value = 0;
                            ranger.page = JournalPane.this.linescreen_read;
                        }
                    } else if (line.text_high > JournalPane.this.linescreen_unread) {
                        ranger.min_value = 0;
                        ranger.max_value = line.text_high - JournalPane.this.linescreen_unread;
                        ranger.page = JournalPane.this.linescreen_unread;
                        ranger.current_value = line.ranger_cur_value;
                    } else {
                        ranger.min_value = 0;
                        ranger.current_value = 0;
                        ranger.max_value = 0;
                        ranger.page = JournalPane.this.linescreen_unread;
                    }
                    menues.UpdateMenuField(ranger);
                }
            }
            menues.UpdateMenuField(menues.ConvertMenuFields(button));
        }

        public void updateSelectedInfo(TableLine linedata) {
            if (JournalPane.this.is_empty_table) {
                return;
            }
            JournalLine line = (JournalLine)linedata;
            JournalPane.this.selectedTime = line.date;
            if (!JournalPane.this.can_select) {
                return;
            }
            if (JournalPane.this.canShowQuestion() && line != null && !line.is_read) {
                JournalPane.this.underQuestion = line;
                journable jou = line.jou;
                if (jou != null) {
                    if (jou.needMenu()) {
                        JournalPane.this.question.show();
                    } else {
                        JournalPane.this.question.callonYesClose();
                    }
                }
            }
            JournalPane.this.can_select = false;
            this.selectDate();
            JournalPane.this.can_select = true;
        }

        public void enterFocus(Table table) {
        }

        public void leaveFocus(Table table) {
        }

        public void deinit() {
            this.table.deinit();
        }

        public void selectDate() {
            JournalPane.this.calendar_table.moveToSelectedTime();
            JournalPane.this.calendar_table.update();
        }

        private JournalCalendarFilling findCalendarFilling(CoreTime time) {
            if (null == this.calendarFilling) {
                return null;
            }
            return this.calendarFilling.find(time);
        }

        private void selectLine(JournalLine line) {
            this.table.select_line_by_data(line);
        }

        private void updatePointers() {
            if (this.make_pointers == null) {
                return;
            }
            menues.SetShowField(JournalPane.this.pointers[0], this.make_pointers.hasUpperPointer());
            menues.SetShowField(JournalPane.this.pointers[1], this.make_pointers.hasDownPointer());
        }

        public void update() {
            this.updateTable();
            this.updatePointers();
        }

        public void rangerMoved() {
            this.updatePointers();
        }

        public void afterInit() {
            super.afterInit();
            JournalPane.this.id_textFieldUnread = this.table.getLineStatistics_controls("Journal - Message UNREAD");
            JournalPane.this.id_textFieldRead = this.table.getLineStatistics_controls("Journal - Message READ");
            MENUbutton_field unread_text = (MENUbutton_field)menues.ConvertMenuFields(JournalPane.this.id_textFieldUnread[0]);
            MENUbutton_field read_text = (MENUbutton_field)menues.ConvertMenuFields(JournalPane.this.id_textFieldRead[0]);
            JournalPane.this.line_height_unread = menues.GetTextLineHeight(JournalPane.this.id_textFieldUnread[0]);
            JournalPane.this.line_height_read = menues.GetTextLineHeight(JournalPane.this.id_textFieldRead[0]);
            JournalPane.this.baseline_unread = new MENUBase_Line(JournalPane.this.id_textFieldUnread[0]);
            JournalPane.this.baseline_read = new MENUBase_Line(JournalPane.this.id_textFieldRead[0]);
            JournalPane.this.linescreen_unread = Converts.HeightToLines(unread_text.leny, JournalPane.this.baseline_unread.GetMinBaseLine(), JournalPane.this.line_height_unread);
            JournalPane.this.linescreen_read = Converts.HeightToLines(read_text.leny, JournalPane.this.baseline_read.GetMinBaseLine(), JournalPane.this.line_height_read);
            JournalPane.this.id_rangers_control_group = this.table.getLineStatistics_controls("Journal - Message UNREAD");
            for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                if (!this.TABLE_DATA.all_lines.elementAt((int)i).wheather_show) continue;
                JournalLine elem = (JournalLine)this.TABLE_DATA.all_lines.elementAt(i);
                elem.text_high = elem.is_read ? (JournalPane.this.id_textFieldRead != null && JournalPane.this.id_textFieldRead[0] != 0L ? Converts.HeightToLines(menues.GetTextHeight(JournalPane.this.id_textFieldRead[0], elem.message), JournalPane.this.baseline_read.GetMinBaseLine(), JournalPane.this.line_height_read) : 0) : (JournalPane.this.id_textFieldUnread != null && JournalPane.this.id_textFieldUnread[0] != 0L ? Converts.HeightToLines(menues.GetTextHeight(JournalPane.this.id_textFieldUnread[0], elem.message), JournalPane.this.baseline_unread.GetMinBaseLine(), JournalPane.this.line_height_unread) : 0);
            }
            this.make_sync_group();
        }
    }

    static class CalendarLine
    extends TableLine {
        boolean has_messages = false;
        boolean is_read = false;
        boolean is_today = false;
        CoreTime date = null;

        CalendarLine() {
        }
    }

    static class JournalLine
    extends TableLine {
        static int serial_number_next = 0;
        int num = serial_number_next++;
        boolean is_read = false;
        CoreTime date = null;
        String message = null;
        journable jou = null;
        int ranger_cur_value = 0;
        int text_high = 0;

        JournalLine() {
        }

        private void SayYes() {
            this.is_read = true;
            if (this.jou != null) {
                this.jou.answerYES();
            }
        }

        private void SayNo() {
            this.is_read = true;
            if (this.jou != null) {
                this.jou.answerNO();
            }
        }
    }

    class ListenQuestionAnswer
    implements IYesNoCancelMenuListener {
        ListenQuestionAnswer() {
        }

        private void cleanAnswered() {
            JournalCalendarFilling fill_data = JournalPane.this.journal_table.findCalendarFilling(((JournalPane)JournalPane.this).underQuestion.date);
            fill_data.refresh();
            JournalPane.this.underQuestion = null;
            JournalPane.this.calendar_table.redrawTable();
            journal.getInstance().updateActiveNotes();
        }

        public void onCancelClose() {
            JournalPane.this.underQuestion = null;
        }

        public void onClose() {
            JournalPane.this.underQuestion = null;
        }

        public void onNoClose() {
            JournalPane.this.underQuestion.SayNo();
            JournalPane.this.journal_table.update();
            JournalPane.this.calendar_table.update();
            JournalPane.this.journal_table.redrawTable();
            this.cleanAnswered();
        }

        public void onOpen() {
        }

        public void onYesClose() {
            JournalPane.this.underQuestion.SayYes();
            JournalPane.this.journal_table.update();
            JournalPane.this.calendar_table.update();
            JournalPane.this.journal_table.redrawTable();
            this.cleanAnswered();
        }
    }

    class JournalPointers {
        int num_first = -1;
        int num_last = -1;

        JournalPointers() {
        }

        void add(int num) {
            if (-1 == this.num_first) {
                this.num_first = num;
            }
            this.num_last = num;
        }

        boolean hasUpperPointer() {
            if (this.num_first == -1 || JournalPane.this.is_empty_table) {
                return false;
            }
            int num_current = ((JournalLine)((JournalPane)JournalPane.this).journal_table.getTop()).num;
            return num_current > this.num_first;
        }

        boolean hasDownPointer() {
            if (this.num_first == -1 || JournalPane.this.is_empty_table) {
                return false;
            }
            int num_current = ((JournalLine)((JournalPane)JournalPane.this).journal_table.getTop()).num + JournalPane.this.journal_table.getNumRows() - 1;
            return num_current < this.num_last;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class JournalCalendarYearFilling {
        HashMap<Integer, JournalCalendarMonthFilling> fill = new HashMap();

        JournalCalendarFilling find(CoreTime time) {
            if (!this.fill.containsKey(time.gYear())) {
                return null;
            }
            JournalCalendarMonthFilling month = this.fill.get(time.gYear());
            return month.find(time);
        }

        void add(JournalLine data) {
            JournalCalendarMonthFilling month = null;
            if (!this.fill.containsKey(data.date.gYear())) {
                month = new JournalCalendarMonthFilling();
                this.fill.put(data.date.gYear(), month);
            } else {
                month = this.fill.get(data.date.gYear());
            }
            month.add(data);
        }

        JournalCalendarYearFilling(ArrayList<JournalLine> lines) {
            if (null == lines) {
                return;
            }
            for (JournalLine line : lines) {
                this.add(line);
            }
        }
    }

    static class JournalCalendarMonthFilling {
        HashMap<Integer, JournalCalendarDateFilling> fill = new HashMap();

        JournalCalendarMonthFilling() {
        }

        JournalCalendarFilling find(CoreTime time) {
            if (!this.fill.containsKey(time.gMonth())) {
                return null;
            }
            JournalCalendarDateFilling date = this.fill.get(time.gMonth());
            return date.find(time);
        }

        void add(JournalLine data) {
            JournalCalendarDateFilling date = null;
            if (!this.fill.containsKey(data.date.gMonth())) {
                date = new JournalCalendarDateFilling();
                this.fill.put(data.date.gMonth(), date);
            } else {
                date = this.fill.get(data.date.gMonth());
            }
            date.add(data);
        }
    }

    static class JournalCalendarDateFilling {
        HashMap<Integer, JournalCalendarFilling> fill = new HashMap();

        JournalCalendarDateFilling() {
        }

        JournalCalendarFilling find(CoreTime time) {
            if (!this.fill.containsKey(time.gDate())) {
                return null;
            }
            JournalCalendarFilling data = this.fill.get(time.gDate());
            return data;
        }

        void add(JournalLine data) {
            JournalCalendarFilling date = null;
            if (!this.fill.containsKey(data.date.gDate())) {
                date = new JournalCalendarFilling();
                this.fill.put(data.date.gDate(), date);
            } else {
                date = this.fill.get(data.date.gDate());
            }
            date.add(data);
        }
    }

    static class JournalCalendarFilling {
        boolean has_unread_messages = false;
        JournalLine line;
        ArrayList<JournalLine> all_data = new ArrayList();

        JournalCalendarFilling() {
        }

        void add(JournalLine data) {
            this.all_data.add(data);
            boolean bl = this.has_unread_messages = this.has_unread_messages || !data.is_read;
            if (this.line == null) {
                this.line = data;
            } else if (this.line.date.moreThan(data.date) > 0) {
                this.line = data;
            }
        }

        private void refresh() {
            this.has_unread_messages = false;
            for (JournalLine line : this.all_data) {
                if (line.is_read) continue;
                this.has_unread_messages = true;
                return;
            }
        }
    }
}

