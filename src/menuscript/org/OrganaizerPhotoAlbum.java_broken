/*
 * Decompiled with CFR 0.151.
 */
package menuscript.org;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;
import menu.Cmenu_TTI;
import menu.Common;
import menu.JavaEvents;
import menu.MENUEditBox;
import menu.MENUText_field;
import menu.MENUTruckview;
import menu.MENU_ranger;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.TextScroller;
import menu.menues;
import menuscript.Converts;
import menuscript.org.IOrgTab;
import menuscript.org.OrganiserMenu;
import menuscript.table.ISelectLineListener;
import menuscript.table.ISetupLine;
import menuscript.table.Table;
import rnrcore.CoreTime;
import rnrcore.Log;
import rnrorg.Album;

public class OrganaizerPhotoAlbum
implements IOrgTab {
    private static final String XML = "..\\data\\config\\menu\\menu_com.xml";
    OrganiserMenu parent = null;
    public sort out_photos_sort_mode = null;
    PhotoInfo current_info = null;
    Photos photos;
    private static final String TEXT_FIELD_MESSAGE = "PHOTO ALBUM - PHOTO - TEXT";
    private static final String TEXT_FIELD_DATE = "PHOTO ALBUM - PHOTO - DATE";
    private static final String TEXT_FIELD_MESSAGE_SCROLLER_GROUP = "GROUP Tableranger - PHOTO ALBUM - PHOTO - TEXT";
    private static final String TEXT_FIELD_MESSAGE_SCROLLER = "Tableranger - PHOTO ALBUM - PHOTO - TEXT";
    private static final String PIC_SIMPLE = "PHOTO ALBUM - PHOTO - PIC";
    private static final String PIC_BIGRACE = "PHOTO ALBUM - PHOTO - PIC - race summary";
    TextScroller scroller = null;
    long text_message_field = 0L;
    long text_message_field_scroller_group = 0L;
    long text_message_field_scroller = 0L;
    long date_field = 0L;
    long _menu = 0L;
    long pic_simple = 0L;
    long pic_bigrace = 0L;
    String date_initial_value = null;

    public OrganaizerPhotoAlbum(long _menu, OrganiserMenu parent) {
        this._menu = _menu;
        this.parent = parent;
        this.photos = new Photos(_menu, this);
        long control = menues.FindFieldInMenu(_menu, "CALL COMMUNICATOR HELP - PHOTO ALBUM");
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control), this, "ShowHelp", 4L);
    }

    public void exitMenu() {
        this.photos.deinit();
        if (this.scroller != null) {
            this.scroller.Deinit();
        }
    }

    public void afterInit() {
        this.date_field = menues.FindFieldInMenu(this._menu, TEXT_FIELD_DATE);
        this.text_message_field = menues.FindFieldInMenu(this._menu, TEXT_FIELD_MESSAGE);
        this.text_message_field_scroller_group = menues.FindFieldInMenu(this._menu, TEXT_FIELD_MESSAGE_SCROLLER_GROUP);
        this.text_message_field_scroller = menues.FindFieldInMenu(this._menu, TEXT_FIELD_MESSAGE_SCROLLER);
        this.pic_simple = menues.FindFieldInMenu(this._menu, PIC_SIMPLE);
        this.pic_bigrace = menues.FindFieldInMenu(this._menu, PIC_BIGRACE);
        if (this.date_field != 0L) {
            menues.SetShowField(this.date_field, false);
        }
        if (this.text_message_field != 0L) {
            menues.SetShowField(this.text_message_field, false);
        }
        if (this.text_message_field_scroller_group != 0L) {
            menues.SetShowField(this.text_message_field_scroller_group, false);
        }
        if (this.pic_bigrace != 0L) {
            menues.SetShowField(this.pic_bigrace, false);
        }
        if (this.pic_simple != 0L) {
            menues.SetShowField(this.pic_simple, true);
        }
        this.photos.afterInit();
    }

    void OnMissionSelect(String mission_text, CoreTime date, boolean bIsBigRace) {
        if (this.date_field != 0L && menues.ConvertMenuFields(this.date_field) != null) {
            menues.SetShowField(this.date_field, true);
            MENUText_field obj = (MENUText_field)menues.ConvertMenuFields(this.date_field);
            if (this.date_initial_value == null) {
                this.date_initial_value = obj.text;
            }
            obj.text = Converts.ConvertDateAbsolute(this.date_initial_value, date.gMonth(), date.gDate(), date.gYear(), date.gHour(), date.gMinute());
            menues.UpdateMenuField(obj);
        }
        if (this.text_message_field != 0L && menues.ConvertMenuFields(this.text_message_field) != null && this.text_message_field_scroller != 0L && menues.ConvertMenuFields(this.text_message_field_scroller) != null) {
            MENU_ranger ranger = (MENU_ranger)menues.ConvertMenuFields(this.text_message_field_scroller);
            MENUText_field text = (MENUText_field)menues.ConvertMenuFields(this.text_message_field);
            text.text = mission_text;
            menues.UpdateField(text);
            menues.SetShowField(this.text_message_field, true);
            int texh = menues.GetTextLineHeight(text.nativePointer);
            int startbase = menues.GetBaseLine(text.nativePointer);
            int linescreen = Converts.HeightToLines(text.leny, startbase, texh);
            int linecounter = Converts.HeightToLines(menues.GetTextHeight(text.nativePointer, mission_text), startbase, texh);
            if (this.scroller != null) {
                this.scroller.Deinit();
            }
            this.scroller = new TextScroller(this._menu, ranger, linecounter, linescreen, texh, startbase, this.text_message_field_scroller_group, true, TEXT_FIELD_MESSAGE_SCROLLER_GROUP);
            this.scroller.AddTextControl(text);
        }
        if (!bIsBigRace) {
            if (this.pic_bigrace != 0L) {
                menues.SetShowField(this.pic_bigrace, false);
            }
            if (this.pic_simple != 0L) {
                menues.SetShowField(this.pic_simple, true);
            }
        } else {
            if (this.pic_bigrace != 0L) {
                menues.SetShowField(this.pic_bigrace, true);
            }
            if (this.pic_simple != 0L) {
                menues.SetShowField(this.pic_simple, false);
            }
        }
    }

    public void enterFocus() {
        this.photos.updateTable();
    }

    public void leaveFocus() {
    }

    public void ShowHelp(long _menu, MENUsimplebutton_field button) {
        if (this.parent != null) {
            this.parent.ShowTabHelp(5, 0);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    class Photos
    implements ISetupLine,
    ISelectLineListener {
        private final String SORT_METHOD = "onSort";
        private static final int PHOTO_DATE = 0;
        private static final int PHOTO_NAME = 1;
        String initial_date_value = null;
        private photos_table_data TABLE_DATA = new photos_table_data();
        private long _menu = 0L;
        OrganaizerPhotoAlbum _parent = null;
        private final String[] SORT = new String[]{"BUTTON - PHOTO ALBUM - LIST - DATE", "BUTTON - PHOTO ALBUM - LIST - TASK"};
        private final String[] TABLE_ELEMENTS = new String[]{"PHOTO ALBUM - LIST - DATE - VALUE", "PHOTO ALBUM - LIST - TASK - VALUE"};
        Table table;
        ArrayList<Album.Item> items = Album.getInstance().getAll();

        Photos(long _menu, OrganaizerPhotoAlbum _parent) {
            this._menu = _menu;
            this._parent = _parent;
            OrganaizerPhotoAlbum.this.out_photos_sort_mode = new sort(0, true);
            this.table = new Table(_menu, "TABLEGROUP - PHOTO ALBUM - 18 38", "Tableranger - PHOTO ALBUM - list");
            this.table.setSelectionMode(1);
            this.table.fillWithLines(OrganaizerPhotoAlbum.XML, "Tablegroup - ELEMENTS - PHOTO ALBUM", this.TABLE_ELEMENTS);
            this.table.takeSetuperForAllLines(this);
            this.table.addListener(this);
            this.reciveTableData();
            this.build_tree_data();
            for (String name : this.TABLE_ELEMENTS) {
                this.table.initLinesSelection(name);
            }
            if (null != this.SORT) {
                int i = 0;
                while (i < this.SORT.length) {
                    long field = menues.FindFieldInMenu(_menu, this.SORT[i]);
                    MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);
                    buts.userid = i++;
                    menues.SetScriptOnControl(_menu, buts, this, "onSort", 4L);
                    menues.UpdateField(buts);
                }
            }
        }

        @Override
        public void SetupLineInTable(MENUEditBox obj, Cmenu_TTI table_node) {
        }

        @Override
        public void SetupLineInTable(MENUsimplebutton_field obj, Cmenu_TTI table_node) {
        }

        @Override
        public void SetupLineInTable(MENUText_field obj, Cmenu_TTI table_node) {
        }

        @Override
        public void SetupLineInTable(MENUTruckview obj, Cmenu_TTI table_node) {
        }

        @Override
        public void SetupLineInTable(MENU_ranger obj, Cmenu_TTI table_node) {
        }

        @Override
        public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
            PhotoInfo line = (PhotoInfo)table_node.item;
            if (!line.wheather_show) {
                menues.SetShowField(obj.nativePointer, false);
                return;
            }
            int control = this.table.getMarkedPosition(obj.nativePointer);
            switch (control) {
                case 0: {
                    if (this.initial_date_value == null) {
                        this.initial_date_value = obj.text;
                    }
                    obj.text = Converts.ConvertDateAbsolute(this.initial_date_value, line.date.gMonth(), line.date.gDate(), line.date.gYear(), line.date.gHour(), line.date.gMinute());
                    menues.UpdateMenuField(obj);
                    menues.SetBlindess(obj.nativePointer, false);
                    menues.SetIgnoreEvents(obj.nativePointer, false);
                    break;
                }
                case 1: {
                    obj.text = "" + line.mission_name;
                    menues.UpdateMenuField(obj);
                    menues.SetBlindess(obj.nativePointer, false);
                    menues.SetIgnoreEvents(obj.nativePointer, false);
                }
            }
            menues.SetShowField(obj.nativePointer, true);
        }

        private Cmenu_TTI convertTableData() {
            Cmenu_TTI root = new Cmenu_TTI();
            for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                Cmenu_TTI ch = new Cmenu_TTI();
                ch.toshow = true;
                ch.ontop = i == 0;
                ch.item = this.TABLE_DATA.all_lines.get(i);
                root.children.add(ch);
            }
            return root;
        }

        private void build_tree_data() {
            this.table.reciveTreeData(this.convertTableData());
        }

        private void reciveTableData() {
            this.TABLE_DATA.all_lines.clear();
            if (this.items != null) {
                for (int i = 0; i < this.items.size(); ++i) {
                    PhotoInfo info = new PhotoInfo();
                    info.date = this.items.get((int)i).date;
                    info.mission_name = this.items.get((int)i).locdesc;
                    info.photo_text = this.items.get((int)i).loctext;
                    info.texture_name = this.items.get((int)i).material;
                    info.isBigRace = this.items.get((int)i).is_bigrace_item;
                    this.TABLE_DATA.all_lines.add(info);
                }
            }
            PhotosComparator comp = null;
            if (OrganaizerPhotoAlbum.this.out_photos_sort_mode.type == 0) {
                comp = new SortByNameComparator();
                comp.SetOrder(OrganaizerPhotoAlbum.this.out_photos_sort_mode.up);
            } else if (OrganaizerPhotoAlbum.this.out_photos_sort_mode.type == 1) {
                comp = new SortByDateComparator();
                comp.SetOrder(OrganaizerPhotoAlbum.this.out_photos_sort_mode.up);
            } else {
                comp = new SortByNameComparator();
                comp.SetOrder(true);
            }
            Collections.sort(this.TABLE_DATA.all_lines, comp);
            this.buildvoidcells();
        }

        private void buildvoidcells() {
            block4: {
                block3: {
                    if (this.TABLE_DATA.all_lines.size() >= this.table.getNumRows()) break block3;
                    int dif = this.table.getNumRows() - this.TABLE_DATA.all_lines.size();
                    for (int i = 0; i < dif; ++i) {
                        PhotoInfo data = new PhotoInfo();
                        data.wheather_show = false;
                        this.TABLE_DATA.all_lines.add(data);
                    }
                    break block4;
                }
                int count_good_data = 0;
                Iterator<PhotoInfo> iter = this.TABLE_DATA.all_lines.iterator();
                while (iter.hasNext() && iter.next().wheather_show) {
                    ++count_good_data;
                }
                if (count_good_data < this.table.getNumRows() || count_good_data >= this.TABLE_DATA.all_lines.size()) break block4;
                for (int i = this.TABLE_DATA.all_lines.size() - 1; i >= count_good_data; --i) {
                    this.TABLE_DATA.all_lines.remove(i);
                }
            }
        }

        private void make_sync_group() {
            long[] contrls_name = null;
            if (0 < this.TABLE_ELEMENTS.length) {
                contrls_name = this.table.getLineStatistics_controls(this.TABLE_ELEMENTS[0]);
            }
            long[] contrls_power = null;
            if (1 < this.TABLE_ELEMENTS.length) {
                contrls_power = this.table.getLineStatistics_controls(this.TABLE_ELEMENTS[1]);
            }
            if (null == contrls_name || null == contrls_power) {
                return;
            }
            if (contrls_name.length != contrls_power.length) {
                Log.menu("ERRORR. make_sync_group has wrong behaivoir. contrls_name.length is " + contrls_name.length + " contrls_time.length is " + contrls_power.length);
                return;
            }
            for (int i = 0; i < contrls_name.length; ++i) {
                menues.SetSyncControlActive(this._menu, i, contrls_name[i]);
                menues.SetSyncControlState(this._menu, i, contrls_name[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_power[i]);
                menues.SetSyncControlState(this._menu, i, contrls_power[i]);
            }
        }

        public void updateTable() {
            this.reciveTableData();
            this.build_tree_data();
            this.table.refresh();
        }

        public void afterInit() {
            this.table.afterInit();
            this.make_sync_group();
        }

        public void deinit() {
            this.table.deinit();
        }

        public void onSort(long _menu, MENUsimplebutton_field button) {
            switch (button.userid) {
                case 0: {
                    OrganaizerPhotoAlbum.this.out_photos_sort_mode = new sort(0, OrganaizerPhotoAlbum.this.out_photos_sort_mode.type == 0 ? !OrganaizerPhotoAlbum.this.out_photos_sort_mode.up : true);
                    break;
                }
                case 1: {
                    OrganaizerPhotoAlbum.this.out_photos_sort_mode = new sort(1, OrganaizerPhotoAlbum.this.out_photos_sort_mode.type == 1 ? !OrganaizerPhotoAlbum.this.out_photos_sort_mode.up : true);
                }
            }
            PhotoInfo old_photo_info = OrganaizerPhotoAlbum.this.current_info;
            this.updateTable();
            if (old_photo_info != null) {
                for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                    if (!this.TABLE_DATA.all_lines.elementAt(i).equal(old_photo_info)) continue;
                    this.table.select_line_by_data(this.TABLE_DATA.all_lines.elementAt(i));
                }
            }
        }

        @Override
        public void selectLineEvent(Table table, int line) {
            PhotoInfo data = (PhotoInfo)table.getItemOnLine((int)line).item;
            if (data != null && data.wheather_show && data.date != null && data.mission_name != null && data.photo_text != null && data.texture_name != null) {
                OrganaizerPhotoAlbum.this.current_info = data;
                if (data.texture_name != null) {
                    JavaEvents.SendEvent(68, 1, data);
                }
                this._parent.OnMissionSelect(data.photo_text, data.date, data.isBigRace);
            }
        }

        @Override
        public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {
        }
    }

    public class SortByDateComparator
    implements PhotosComparator {
        int order;

        public void SetOrder(boolean isascending) {
            this.order = isascending ? 1 : -1;
        }

        public int compare(Object o1, Object o2) {
            PhotoInfo item1 = (PhotoInfo)o1;
            PhotoInfo item2 = (PhotoInfo)o2;
            if (this.order == 1) {
                return item1.date.moreThan(item2.date) > 0 ? 1 : -1;
            }
            if (this.order == -1) {
                return item1.date.moreThan(item2.date) < 0 ? 1 : -1;
            }
            return item1.date.moreThan(item2.date) > 0 ? 1 : -1;
        }
    }

    public class SortByNameComparator
    implements PhotosComparator {
        int order;

        public void SetOrder(boolean isascending) {
            this.order = isascending ? 1 : -1;
        }

        public int compare(Object o1, Object o2) {
            PhotoInfo item1 = (PhotoInfo)o1;
            PhotoInfo item2 = (PhotoInfo)o2;
            return Common.Compare(item1.mission_name, item2.mission_name, this.order == 1);
        }
    }

    public static interface PhotosComparator
    extends Comparator {
        public void SetOrder(boolean var1);
    }

    static class sort {
        int type;
        boolean up;

        sort(int type, boolean up) {
            this.type = type;
            this.up = up;
        }
    }

    static class photos_table_data {
        Vector<PhotoInfo> all_lines = new Vector();

        photos_table_data() {
        }
    }

    static class PhotoInfo {
        String mission_name;
        String photo_text;
        CoreTime date;
        String texture_name;
        boolean wheather_show = true;
        boolean isBigRace = false;

        PhotoInfo() {
        }

        public boolean equal(PhotoInfo object) {
            return this.mission_name == object.mission_name && this.photo_text == object.photo_text && this.date.moreThan(object.date) == 0;
        }
    }
}

