/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import menu.Cmenu_TTI;
import menu.KeyPair;
import menu.MENUEditBox;
import menu.MENUText_field;
import menu.MENUTruckview;
import menu.MENU_ranger;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.menues;
import menuscript.Converts;
import menuscript.IPoPUpMenuListener;
import menuscript.PoPUpMenu;
import menuscript.mainmenu.INewSaveGameLine;
import menuscript.mainmenu.IWindowContext;
import menuscript.mainmenu.SaveLoadCommonManagement;
import menuscript.mainmenu.TopWindow;
import menuscript.table.ISelectLineListener;
import menuscript.table.ISetupLine;
import menuscript.table.Table;
import rnrcore.Log;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class LoadGameTable
implements ISetupLine,
ISelectLineListener {
    private String[] BUTTONS = null;
    private static final String[] METHODS = new String[]{"onLoad", "onDelete"};
    private static final int ACTION_LOAD = 0;
    private static final int ACTION_DELETE = 1;
    private static final String GRAY = " GRAY";
    private String TABLE = null;
    private String RANGER = null;
    private String XML_NAME = null;
    private String LINES = null;
    private String[] ELEMENTS = null;
    private String WARNING_XML = null;
    private static final String DELETE_GROUP = "Tablegroup - CONFIRM DELETE";
    private static final String DELETE_WINDOW = "CONFIRM DELETE";
    private static final String REPLACE_GROUP = "Tablegroup - CONFIRM REPLACE";
    private static final String REPLACE_WINDOW = "CONFIRM REPLACE";
    private static final String REPLACE_TEXT = "REPLACE";
    private static final String REPLACE_TEXT_KEY = "PROFILENAME";
    private static final int SAVENAME_ORDER = 0;
    private static final int SAVETIME_ORDER = 2;
    private static final int SAVENAME_QUICKRACE_ORDER = 3;
    private static final int SAVETIME_QUICKRACE_ORDER = 5;
    public static final int LOAD_FROM_MAIN_MENU = 0;
    public static final int LOAD_FROM_ESC_MENU = 1;
    protected int menu_load_type = 0;
    protected int type_game = 1;
    protected boolean bIsClips = false;
    private IWindowContext context = null;
    private long _menu = 0L;
    private Table table = null;
    private PoPUpMenu warning_delete = null;
    private PoPUpMenu warning_replace = null;
    private PoPUpMenu warning_not_compatible = null;
    private NotCompatible not_compatible_listener = new NotCompatible();
    SaveLoadCommonManagement.Media under_warning = null;
    private String newGameName_under_warning = null;
    private String replace_text_store;
    private long replace_text = 0L;
    private long[] gray_buttons = null;
    private table_data TABLE_DATA = new table_data();
    boolean sort_by_name = false;
    boolean date_sort_up = false;
    boolean names_sort_up = true;
    long current_game_name = 0L;
    EditName editName = new EditName();
    private String SAVETIME_ORDER_text = null;
    private String SAVETIME_ORDER_QR_text = null;

    public LoadGameTable(IWindowContext context, long _menu, String[] action_buttons, String table_name, String ranger_name, String xml_name, String lines_name, String[] lineselements, int type_game, int type_menu_load, boolean bIsClips, String sortByNameButtonName, String sortByDateButtonName, String gameNameFieldName) {
        this.menu_load_type = type_menu_load;
        this.context = context;
        this.BUTTONS = action_buttons;
        this.TABLE = table_name;
        this.RANGER = ranger_name;
        this.WARNING_XML = this.XML_NAME = xml_name;
        this.LINES = lines_name;
        this.ELEMENTS = lineselements;
        this.type_game = type_game;
        this.bIsClips = bIsClips;
        this._menu = _menu;
        this.gray_buttons = new long[this.BUTTONS.length];
        for (int i = 0; i < this.BUTTONS.length; ++i) {
            this.gray_buttons[i] = menues.FindFieldInMenu(_menu, this.BUTTONS[i] + GRAY);
            Object field = menues.ConvertMenuFields(menues.FindFieldInMenu(_menu, this.BUTTONS[i]));
            menues.SetScriptOnControl(_menu, field, this, METHODS[i], 4L);
        }
        this.table = new Table(_menu, this.TABLE, this.RANGER);
        this.table.fillWithLines(this.XML_NAME, this.LINES, this.ELEMENTS);
        this.table.takeSetuperForAllLines(this);
        this.reciveTableData();
        this.build_tree_data();
        if (0 < this.ELEMENTS.length) {
            this.table.initLinesSelection(this.ELEMENTS[0]);
        }
        if (2 < this.ELEMENTS.length) {
            this.table.initLinesSelection(this.ELEMENTS[2]);
        }
        if (bIsClips) {
            if (3 < this.ELEMENTS.length) {
                this.table.initLinesSelection(this.ELEMENTS[3]);
            }
            if (5 < this.ELEMENTS.length) {
                this.table.initLinesSelection(this.ELEMENTS[5]);
            }
        }
        this.table.addListener(this);
        this.warning_delete = new PoPUpMenu(_menu, this.WARNING_XML, DELETE_GROUP, DELETE_WINDOW);
        this.warning_replace = new PoPUpMenu(_menu, this.WARNING_XML, REPLACE_GROUP, REPLACE_WINDOW);
        switch (this.menu_load_type) {
            case 0: {
                this.warning_not_compatible = new PoPUpMenu(_menu, this.WARNING_XML, "Tablegroup - SINGLE PLAYER - LOAD GAME - DIFFERENT VERSION");
                break;
            }
            case 1: {
                this.warning_not_compatible = new PoPUpMenu(_menu, this.WARNING_XML, "Tablegroup - SAVELOAD - DIFFERENT VERSION");
                break;
            }
            default: {
                this.warning_not_compatible = null;
            }
        }
        this.warning_delete.addListener(new InWarning(2));
        this.warning_replace.addListener(new InWarning(4));
        this.warning_not_compatible.addListener(this.not_compatible_listener);
        this.replace_text = this.warning_replace.getField(REPLACE_TEXT);
        if (this.replace_text != 0L) {
            this.replace_text_store = menues.GetFieldText(this.replace_text);
        }
        if (sortByNameButtonName != null) {
            long sort_by_name = menues.FindFieldInMenu(_menu, sortByNameButtonName);
            Object sort_by_name_field = menues.ConvertMenuFields(sort_by_name);
            menues.SetScriptOnControl(_menu, sort_by_name_field, this, "SortByName", 4L);
        }
        if (sortByDateButtonName != null) {
            long sort_by_date = menues.FindFieldInMenu(_menu, sortByDateButtonName);
            Object sort_by_date_field = menues.ConvertMenuFields(sort_by_date);
            menues.SetScriptOnControl(_menu, sort_by_date_field, this, "SortByDate", 4L);
        }
        if (gameNameFieldName != null) {
            this.current_game_name = menues.FindFieldInMenu(_menu, gameNameFieldName);
        }
    }

    private void make_sync_group() {
        long[] contrls_name = null;
        if (0 < this.ELEMENTS.length) {
            contrls_name = this.table.getLineStatistics_controls(this.ELEMENTS[0]);
        }
        long[] contrls_time = null;
        if (2 < this.ELEMENTS.length) {
            contrls_time = this.table.getLineStatistics_controls(this.ELEMENTS[2]);
        }
        if (null == contrls_name || null == contrls_time) {
            return;
        }
        if (contrls_name.length != contrls_time.length) {
            Log.menu("ERRORR. make_sync_group has wrong behaivoir. contrls_name.length is " + contrls_name.length + " contrls_time.length is " + contrls_time.length);
            return;
        }
        long[] contrls_time_qr = null;
        long[] contrls_name_qr = null;
        if (this.bIsClips) {
            if (3 < this.ELEMENTS.length) {
                contrls_name_qr = this.table.getLineStatistics_controls(this.ELEMENTS[3]);
            }
            if (5 < this.ELEMENTS.length) {
                contrls_time_qr = this.table.getLineStatistics_controls(this.ELEMENTS[5]);
            }
        }
        for (int i = 0; i < contrls_name.length; ++i) {
            menues.SetSyncControlActive(this._menu, i, contrls_name[i]);
            menues.SetSyncControlState(this._menu, i, contrls_name[i]);
            menues.SetSyncControlActive(this._menu, i, contrls_time[i]);
            menues.SetSyncControlState(this._menu, i, contrls_time[i]);
            if (contrls_time_qr != null && i < contrls_time_qr.length && contrls_time_qr[i] != 0L) {
                menues.SetSyncControlActive(this._menu, i, contrls_time_qr[i]);
                menues.SetSyncControlState(this._menu, i, contrls_time_qr[i]);
            }
            if (contrls_name_qr == null || i >= contrls_name_qr.length || contrls_name_qr[i] == 0L) continue;
            menues.SetSyncControlActive(this._menu, i, contrls_name_qr[i]);
            menues.SetSyncControlState(this._menu, i, contrls_name_qr[i]);
        }
    }

    public void afterInit(IWindowContext context) {
        this.editName.init();
        this.table.afterInit();
        this.make_sync_group();
        if (!this.isEmpty()) {
            this.table.select_line(0);
        }
        this.warning_delete.afterInit();
        this.warning_replace.afterInit();
        if (this.warning_not_compatible != null) {
            this.warning_not_compatible.afterInit();
        }
        for (long gray : this.gray_buttons) {
            if (0L == gray) continue;
            menues.SetShowField(gray, false);
        }
    }

    public void readParamValues() {
        this.editName.from_update = true;
        this.reciveTableData();
        this.table.updateTreeData(this.convertTableData());
        this.table.refresh();
        this.editName.from_update = false;
    }

    public void update(IWindowContext context) {
        this.editName.from_update = true;
        this.reciveTableData();
        this.table.updateTreeData(this.convertTableData());
        this.table.refresh();
        this.editName.from_update = false;
        if (this.isEmpty()) {
            context.exitWindowContext();
        }
    }

    public void addTABLEDATAline(INewSaveGameLine cb, SaveLoadCommonManagement.Media game_media) {
        line_data data = new line_data();
        data.game_media = game_media;
        data.cb = cb;
        this.TABLE_DATA.all_lines.add(0, data);
    }

    private void reciveTableData() {
        Vector<SaveLoadCommonManagement.Media> data = SaveLoadCommonManagement.getSaveLoadCommonManager().GetExistsMedia(this.type_game, !this.bIsClips ? 7 : 8, this.sort_by_name ? 0 : 1, this.sort_by_name ? this.names_sort_up : this.date_sort_up);
        Iterator<SaveLoadCommonManagement.Media> iter = data.iterator();
        int i = 0;
        while (i < this.TABLE_DATA.all_lines.size()) {
            line_data check = this.TABLE_DATA.all_lines.get(i);
            if (null == check.cb || !check.cb.isPersistant()) {
                this.TABLE_DATA.all_lines.remove(i);
                continue;
            }
            ++i;
        }
        while (iter.hasNext()) {
            line_data item = new line_data();
            item.game_media = iter.next();
            this.TABLE_DATA.all_lines.add(item);
        }
        this.buildvoidcells();
    }

    private void buildvoidcells() {
        block4: {
            block3: {
                if (this.TABLE_DATA.all_lines.size() >= this.table.getNumRows()) break block3;
                int dif = this.table.getNumRows() - this.TABLE_DATA.all_lines.size();
                for (int i = 0; i < dif; ++i) {
                    line_data data = new line_data();
                    data.wheather_show = false;
                    this.TABLE_DATA.all_lines.add(data);
                }
                break block4;
            }
            int count_good_data = 0;
            Iterator<line_data> iter = this.TABLE_DATA.all_lines.iterator();
            while (iter.hasNext() && iter.next().wheather_show) {
                ++count_good_data;
            }
            if (count_good_data < this.table.getNumRows() || count_good_data >= this.TABLE_DATA.all_lines.size()) break block4;
            for (int i = this.TABLE_DATA.all_lines.size() - 1; i >= count_good_data; --i) {
                this.TABLE_DATA.all_lines.remove(i);
            }
        }
    }

    private boolean isEmpty() {
        Iterator<line_data> iter = this.TABLE_DATA.all_lines.iterator();
        while (iter.hasNext()) {
            if (!iter.next().wheather_show) continue;
            return false;
        }
        return true;
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

    public String GetSelectedMediaName() {
        if (this.table.getSelected() >= this.TABLE_DATA.all_lines.size()) {
            Log.menu("ERRORR onDelete has wrong behaovoir.");
            return null;
        }
        int selected = this.table.getSelected();
        line_data data = this.TABLE_DATA.all_lines.get(selected);
        SaveLoadCommonManagement.Media media = data.game_media;
        if (null != data.cb) {
            return null;
        }
        return media.media_name;
    }

    public void onLoad(long _menu, MENUsimplebutton_field button) {
        if (this.table.getSelected() >= this.TABLE_DATA.all_lines.size()) {
            Log.menu("ERRORR onDelete has wrong behaovoir.");
            return;
        }
        int selected = this.table.getSelected();
        line_data data = this.TABLE_DATA.all_lines.get(selected);
        SaveLoadCommonManagement.Media media = data.game_media;
        if (null != data.cb && !data.cb.canLoad(media)) {
            return;
        }
        if (null != media) {
            if (this.warning_not_compatible != null && !SaveLoadCommonManagement.getSaveLoadCommonManager().IsCompatibleGame(media.media_name, media.game_type, media.media_type)) {
                this.not_compatible_listener.Init(this.menu_load_type, media);
                this.warning_not_compatible.show();
                return;
            }
            switch (this.menu_load_type) {
                case 0: {
                    SaveLoadCommonManagement.getSaveLoadCommonManager().SetLoadGameFlagFromMainMenu(media.media_name, media.game_type, media.media_type);
                    break;
                }
                case 1: {
                    SaveLoadCommonManagement.getSaveLoadCommonManager().SetLoadGameFlagFromESCMenu(media.media_name, media.game_type, media.media_type);
                }
            }
            TopWindow.quitTopMenu();
        }
    }

    public void onDelete(long _menu, MENUsimplebutton_field button) {
        if (this.table.getSelected() >= this.TABLE_DATA.all_lines.size()) {
            Log.menu("ERRORR onDelete has wrong behaovoir.");
            return;
        }
        int selected = this.table.getSelected();
        line_data data = this.TABLE_DATA.all_lines.get(selected);
        SaveLoadCommonManagement.Media media = data.game_media;
        if (null != data.cb && !data.cb.canDelete(media)) {
            return;
        }
        this.under_warning = media;
        this.warning_delete.show();
    }

    // @Override
    public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
        line_data line = (line_data)table_node.item;
        if (!line.wheather_show) {
            menues.SetShowField(obj.nativePointer, false);
            return;
        }
        int control = this.table.getMarkedPosition(obj.nativePointer);
        switch (control) {
            case 0: {
                obj.text = line.game_media.media_name;
                menues.UpdateMenuField(obj);
                menues.SetBlindess(obj.nativePointer, false);
                menues.SetIgnoreEvents(obj.nativePointer, false);
                if (this.bIsClips && line.game_media.game_type != 1) {
                    menues.SetShowField(obj.nativePointer, false);
                    break;
                }
                menues.SetShowField(obj.nativePointer, true);
                break;
            }
            case 2: {
                if (this.SAVETIME_ORDER_text == null) {
                    this.SAVETIME_ORDER_text = obj.text;
                }
                obj.text = Converts.ConvertDateAbsolute(this.SAVETIME_ORDER_text, line.game_media.media_time.month, line.game_media.media_time.day, line.game_media.media_time.year, line.game_media.media_time.hour, line.game_media.media_time.min, line.game_media.media_time.sec);
                menues.UpdateMenuField(obj);
                menues.SetBlindess(obj.nativePointer, false);
                menues.SetIgnoreEvents(obj.nativePointer, false);
                if (this.bIsClips && line.game_media.game_type != 1) {
                    menues.SetShowField(obj.nativePointer, false);
                    break;
                }
                menues.SetShowField(obj.nativePointer, true);
                break;
            }
            case 3: {
                obj.text = line.game_media.media_name;
                menues.UpdateMenuField(obj);
                menues.SetBlindess(obj.nativePointer, false);
                menues.SetIgnoreEvents(obj.nativePointer, false);
                if (this.bIsClips && line.game_media.game_type != 1) {
                    menues.SetShowField(obj.nativePointer, true);
                    break;
                }
                menues.SetShowField(obj.nativePointer, false);
                break;
            }
            case 5: {
                if (this.SAVETIME_ORDER_QR_text == null) {
                    this.SAVETIME_ORDER_QR_text = obj.text;
                }
                obj.text = Converts.ConvertDateAbsolute(this.SAVETIME_ORDER_QR_text, line.game_media.media_time.month, line.game_media.media_time.day, line.game_media.media_time.year, line.game_media.media_time.hour, line.game_media.media_time.min, line.game_media.media_time.sec);
                menues.UpdateMenuField(obj);
                menues.SetBlindess(obj.nativePointer, false);
                menues.SetIgnoreEvents(obj.nativePointer, false);
                if (this.bIsClips && line.game_media.game_type != 1) {
                    menues.SetShowField(obj.nativePointer, true);
                    break;
                }
                menues.SetShowField(obj.nativePointer, false);
            }
        }
    }

    // @Override
    public void SetupLineInTable(MENUEditBox obj, Cmenu_TTI table_node) {
        menues.SetBlindess(obj.nativePointer, true);
        menues.SetIgnoreEvents(obj.nativePointer, true);
        menues.SetShowField(obj.nativePointer, false);
    }

    // @Override
    public void SetupLineInTable(MENUsimplebutton_field obj, Cmenu_TTI table_node) {
    }

    // @Override
    public void SetupLineInTable(MENUText_field obj, Cmenu_TTI table_node) {
    }

    // @Override
    public void SetupLineInTable(MENU_ranger obj, Cmenu_TTI table_node) {
    }

    // @Override
    public void SetupLineInTable(MENUTruckview obj, Cmenu_TTI table_node) {
    }

    // @Override
    public void selectLineEvent(Table table, int line) {
        this.editName.selectLine(line);
        line_data data = (line_data)table.getItemOnLine((int)line).item;
        if (null != data.cb) {
            this.setDeleteActionGray(!data.cb.canBeDeleted(data.game_media));
            this.setLoadActionGray(!data.cb.canBeLoaded(data.game_media));
        } else {
            this.setDeleteActionGray(false);
            this.setLoadActionGray(false);
        }
        if (data.game_media != null) {
            if (null != data.cb && data.cb.isMediaCurrent(data.game_media)) {
                SaveLoadCommonManagement.getSaveLoadCommonManager().UpdateShotByCurrent();
            } else {
                SaveLoadCommonManagement.getSaveLoadCommonManager().UpdateShot(data.game_media.media_name, data.game_media.game_type, data.game_media.media_type);
            }
            if (this.current_game_name != 0L) {
                menues.SetFieldText(this.current_game_name, data.game_media.media_name);
                menues.UpdateMenuField(menues.ConvertMenuFields(this.current_game_name));
            }
        }
    }

    static boolean isLoadgameListEmpty(int type, boolean isClips) {
        if (!isClips) {
            return SaveLoadCommonManagement.getSaveLoadCommonManager().GetExistsMedia(type, 7, 0, false).isEmpty();
        }
        return SaveLoadCommonManagement.getSaveLoadCommonManager().GetExistsMedia(type, 8, 0, false).isEmpty();
    }

    // @Override
    public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {
    }

    private void setLoadActionGray(boolean value) {
        if (0L != this.gray_buttons[0]) {
            menues.SetShowField(this.gray_buttons[0], value);
        }
    }

    private void setDeleteActionGray(boolean value) {
        if (0L != this.gray_buttons[1]) {
            menues.SetShowField(this.gray_buttons[1], value);
        }
    }

    public void deinit() {
        this.table.deinit();
    }

    public void SortByName(long _menu, MENUsimplebutton_field button) {
        this.sort_by_name = true;
        this.names_sort_up = !this.names_sort_up;
        this.editName.from_update = true;
        this.reciveTableData();
        this.table.updateTreeData(this.convertTableData());
        this.table.refresh();
        this.editName.from_update = false;
    }

    public void SortByDate(long _menu, MENUsimplebutton_field button) {
        this.sort_by_name = false;
        this.date_sort_up = !this.date_sort_up;
        this.editName.from_update = true;
        this.reciveTableData();
        this.table.updateTreeData(this.convertTableData());
        this.table.refresh();
        this.editName.from_update = false;
    }

    class InWarning
    implements IPoPUpMenuListener {
        static final int CANNOTDELETE = 1;
        static final int DELETE = 2;
        static final int REPLACE = 4;
        private int state;

        InWarning(int value) {
            this.state = value;
        }

        public void onAgreeclose() {
            switch (this.state) {
                case 2: {
                    SaveLoadCommonManagement.getSaveLoadCommonManager().DeleteExistsMedia(LoadGameTable.this.under_warning.media_name, LoadGameTable.this.under_warning.game_type, LoadGameTable.this.under_warning.media_type);
                    LoadGameTable.this.context.updateWindowContext();
                    if (LoadGameTable.this.isEmpty()) break;
                    LoadGameTable.this.table.select_line(0);
                    break;
                }
                case 4: {
                    SaveLoadCommonManagement.getSaveLoadCommonManager().DeleteExistsMedia(LoadGameTable.this.newGameName_under_warning, LoadGameTable.this.under_warning.game_type, LoadGameTable.this.under_warning.media_type);
                    SaveLoadCommonManagement.getSaveLoadCommonManager().RenameExistsMedia(LoadGameTable.this.under_warning.media_name, LoadGameTable.this.under_warning.game_type, LoadGameTable.this.under_warning.media_type, LoadGameTable.this.newGameName_under_warning);
                    if (LoadGameTable.this.current_game_name != 0L) {
                        menues.SetFieldText(LoadGameTable.this.current_game_name, LoadGameTable.this.newGameName_under_warning);
                        menues.UpdateMenuField(menues.ConvertMenuFields(LoadGameTable.this.current_game_name));
                    }
                    LoadGameTable.this.context.updateWindowContext();
                    LoadGameTable.this.newGameName_under_warning = null;
                    LoadGameTable.this.under_warning = null;
                }
            }
        }

        public void onClose() {
            switch (this.state) {
                case 4: {
                    LoadGameTable.this.table.refresh();
                    LoadGameTable.this.newGameName_under_warning = null;
                    LoadGameTable.this.under_warning = null;
                }
            }
        }

        public void onCancel() {
        }

        public void onOpen() {
        }
    }

    class NotCompatible
    implements IPoPUpMenuListener {
        int menu_load_type;
        SaveLoadCommonManagement.Media media;

        NotCompatible() {
        }

        public void Init(int _menu_load_type, SaveLoadCommonManagement.Media _media) {
            this.menu_load_type = _menu_load_type;
            this.media = _media;
        }

        public void onCancel() {
        }

        public void onOpen() {
        }

        public void onAgreeclose() {
            if (this.media != null) {
                switch (this.menu_load_type) {
                    case 0: {
                        SaveLoadCommonManagement.getSaveLoadCommonManager().SetLoadGameFlagFromMainMenu(this.media.media_name, this.media.game_type, this.media.media_type);
                        break;
                    }
                    case 1: {
                        SaveLoadCommonManagement.getSaveLoadCommonManager().SetLoadGameFlagFromESCMenu(this.media.media_name, this.media.game_type, this.media.media_type);
                    }
                }
                TopWindow.quitTopMenu();
            }
        }

        public void onClose() {
        }
    }

    class EditName {
        private final String METHOD_CHANGENAME = "changeSaveGameName";
        private final String METHOD_DISSMIS = "dissmissSaveGameName";
        private final int BACKFIELD = 0;
        private final int BACKFIELD_QR = 3;
        private final int EDITFIELD = 1;
        private final int EDITFIELD_QR = 4;
        private int lastline = -1;
        private long[] controls_sp;
        private long[] controls_back_sp;
        private long[] controls_qr;
        private long[] controls_back_qr;
        boolean from_update = false;

        EditName() {
        }

        void selectLine(int line) {
            if (this.from_update) {
                return;
            }
            if (this.lastline == line) {
                if (line < 0 || line >= this.controls_sp.length) {
                    Log.menu("ERRORR.ProfileSelectProfile EditName selectLine - bad value " + line + " with controls.length " + this.controls_sp.length);
                    return;
                }
                line_data data = (line_data)((LoadGameTable)LoadGameTable.this).table.getItemOnLine((int)line).item;
                if (null != data.cb && data.cb.clearOnEnterEdit(data.game_media)) {
                    data.game_media.media_name = "";
                }
                if (data.game_media != null) {
                    if (LoadGameTable.this.bIsClips && data.game_media.game_type != 1) {
                        menues.setfocuscontrolonmenu(LoadGameTable.this._menu, this.controls_qr[line]);
                        menues.SetShowField(this.controls_qr[line], true);
                        menues.SetBlindess(this.controls_qr[line], false);
                        menues.SetIgnoreEvents(this.controls_qr[line], false);
                        menues.SetFieldText(this.controls_qr[line], data.game_media.media_name);
                        menues.SetFieldText(this.controls_back_qr[line], "");
                        menues.SetShowField(this.controls_back_qr[line], false);
                        menues.UpdateMenuField(menues.ConvertMenuFields(this.controls_qr[line]));
                        menues.UpdateMenuField(menues.ConvertMenuFields(this.controls_back_qr[line]));
                    } else {
                        menues.setfocuscontrolonmenu(LoadGameTable.this._menu, this.controls_sp[line]);
                        menues.SetShowField(this.controls_sp[line], true);
                        menues.SetBlindess(this.controls_sp[line], false);
                        menues.SetIgnoreEvents(this.controls_sp[line], false);
                        menues.SetFieldText(this.controls_sp[line], data.game_media.media_name);
                        menues.SetFieldText(this.controls_back_sp[line], "");
                        menues.SetShowField(this.controls_back_sp[line], false);
                        menues.UpdateMenuField(menues.ConvertMenuFields(this.controls_sp[line]));
                        menues.UpdateMenuField(menues.ConvertMenuFields(this.controls_back_sp[line]));
                    }
                }
            }
            this.lastline = line;
        }

        void init() {
            this.controls_back_sp = LoadGameTable.this.table.getLineStatistics_controls(LoadGameTable.this.ELEMENTS[0]);
            if (null == this.controls_back_sp) {
                Log.menu("ERRORR. LoadGameTable EditName init(). Table contains no controls_back fields, named " + LoadGameTable.this.ELEMENTS[0]);
            }
            this.controls_sp = LoadGameTable.this.table.getLineStatistics_controls(LoadGameTable.this.ELEMENTS[1]);
            if (null == this.controls_sp) {
                Log.menu("ERRORR. LoadGameTable EditName init(). Table contains no controls fields, named " + LoadGameTable.this.ELEMENTS[1]);
            }
            if (LoadGameTable.this.bIsClips) {
                this.controls_back_qr = LoadGameTable.this.table.getLineStatistics_controls(LoadGameTable.this.ELEMENTS[3]);
                if (null == this.controls_back_sp) {
                    Log.menu("ERRORR. LoadGameTable EditName init(). Table contains no controls_back fields, named " + LoadGameTable.this.ELEMENTS[3]);
                }
                this.controls_qr = LoadGameTable.this.table.getLineStatistics_controls(LoadGameTable.this.ELEMENTS[4]);
                if (null == this.controls_qr) {
                    Log.menu("ERRORR. LoadGameTable EditName init(). Table contains no controls fields, named " + LoadGameTable.this.ELEMENTS[4]);
                }
            }
            for (int i = 0; i < this.controls_sp.length; ++i) {
                menues.SetScriptOnControl(LoadGameTable.this._menu, menues.ConvertMenuFields(this.controls_sp[i]), this, "changeSaveGameName", 16L);
                menues.SetScriptOnControl(LoadGameTable.this._menu, menues.ConvertMenuFields(this.controls_sp[i]), this, "dissmissSaveGameName", 19L);
                if (this.controls_qr == null || i >= this.controls_qr.length || this.controls_qr[i] == 0L) continue;
                menues.SetScriptOnControl(LoadGameTable.this._menu, menues.ConvertMenuFields(this.controls_qr[i]), this, "changeSaveGameName", 16L);
                menues.SetScriptOnControl(LoadGameTable.this._menu, menues.ConvertMenuFields(this.controls_qr[i]), this, "dissmissSaveGameName", 19L);
            }
        }

        void dissmissSaveGameName(long _menu, MENUEditBox obj) {
            line_data data = (line_data)((LoadGameTable)LoadGameTable.this).table.getItemOnLine((int)this.lastline).item;
            if (null != data.cb) {
                menues.ConvertMenuFields(obj.nativePointer);
                String text = menues.GetFieldText(obj.nativePointer);
                if (data.cb.dismiss(data.game_media, text)) {
                    this.dismiss_data();
                }
            } else {
                this.dismiss_data();
            }
        }

        void changeSaveGameName(long _menu, MENUEditBox obj) {
            menues.ConvertMenuFields(obj.nativePointer);
            String text = menues.GetFieldText(obj.nativePointer);
            line_data data = (line_data)((LoadGameTable)LoadGameTable.this).table.getItemOnLine((int)this.lastline).item;
            if (text.compareToIgnoreCase("") == 0) {
                if (null != data.cb) {
                    data.cb.dismiss(data.game_media, text);
                }
                this.dismiss_data();
                return;
            }
            String oldprofile = data.game_media.media_name;
            if (oldprofile.compareToIgnoreCase(text) != 0) {
                if (null != data.cb) {
                    if (!data.cb.rename(data.game_media, oldprofile, text)) {
                        this.dismiss_data();
                    }
                } else if (!SaveLoadCommonManagement.getSaveLoadCommonManager().RenameExistsMedia(data.game_media.media_name, data.game_media.game_type, data.game_media.media_type, text)) {
                    LoadGameTable.this.newGameName_under_warning = text;
                    LoadGameTable.this.under_warning = data.game_media;
                    if (LoadGameTable.this.replace_text != 0L) {
                        KeyPair[] keys = new KeyPair[]{new KeyPair(LoadGameTable.REPLACE_TEXT_KEY, text)};
                        menues.SetFieldText(LoadGameTable.this.replace_text, MacroKit.Parse(LoadGameTable.this.replace_text_store, keys));
                        menues.UpdateMenuField(menues.ConvertMenuFields(LoadGameTable.this.replace_text));
                    }
                    LoadGameTable.this.warning_replace.show();
                } else {
                    data.game_media.media_name = text;
                    if (LoadGameTable.this.current_game_name != 0L) {
                        menues.SetFieldText(LoadGameTable.this.current_game_name, data.game_media.media_name);
                        menues.UpdateMenuField(menues.ConvertMenuFields(LoadGameTable.this.current_game_name));
                    }
                    this.dismiss_data();
                }
            } else {
                this.dismiss_data();
            }
        }

        private void dismiss_data() {
            this.from_update = true;
            LoadGameTable.this.table.refreshLine(this.lastline);
            this.from_update = false;
        }
    }

    static class table_data {
        Vector<line_data> all_lines = new Vector();

        table_data() {
        }
    }

    static class line_data {
        boolean wheather_show = true;
        INewSaveGameLine cb = null;
        SaveLoadCommonManagement.Media game_media;

        line_data() {
        }
    }
}

