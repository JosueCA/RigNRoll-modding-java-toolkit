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
import menuscript.IPoPUpMenuListener;
import menuscript.PoPUpMenu;
import menuscript.mainmenu.Panel;
import menuscript.mainmenu.PanelDialog;
import menuscript.mainmenu.ProfileManagement;
import menuscript.mainmenu.ProfileNewProfile;
import menuscript.mainmenu.StartMenu;
import menuscript.table.ISelectLineListener;
import menuscript.table.ISetupLine;
import menuscript.table.Table;
import rnrcore.Log;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ProfileSelectProfile
extends PanelDialog
implements ISetupLine,
ISelectLineListener {
    private static final String[] BUTTONS = new String[]{"PROFILE - SELECT PROFILE SELECT", "PROFILE - SELECT PROFILE DELETE"};
    private static final String[] METHODS = new String[]{"onSelect", "onDelete"};
    private static final String TABLET = "PROFILE - SELECT PROFILE - THE PLATE";
    private static final String TABLET_LICENCE = "PROFILE - SELECT PROFILE - THE DRIVER";
    private static final String[] TABLET_LICENCETEXT = new String[]{"PROFILE - SELECT PROFILE - LICENSE PLATE - TEXT LIGHT", "PROFILE - SELECT PROFILE - LICENSE PLATE - TEXT DARK"};
    private static final int TEXT_LIGHT = 0;
    private static final int TEXT_DARK = 1;
    private static final String TABLE = "TABLEGROUP - PROFILE - SELECT PROFILE - 8 70";
    private static final String RANGER = "Tableranger - PROFILE - SELECT PROFILE";
    private static final String XML_NAME = "..\\data\\config\\menu\\menu_MAIN.xml";
    private static final String LINES = "Tablegroup - ELEMENTS - Profile Lines";
    private static final String[] ELEMENTS = new String[]{"PROFILE - SELECT PROFILE - DriverName", "PROFILE - SELECT PROFILE - DriverName Edit"};
    private static final String WARNING_XML = "..\\data\\config\\menu\\menu_MAIN.xml";
    private static final String DELETE_GROUP = "Tablegroup - PROFILE -  SELECT PROFILE - CONFIRM DELETE";
    private static final String DELETE_WINDOW = "PROFILE -  SELECT PROFILE - CONFIRM DELETE";
    private static final String REPLACE_GROUP = "Tablegroup - PROFILE -  NEW PROFILE - CONFIRM REPLACE";
    private static final String REPLACE_WINDOW = "PROFILE -  NEW PROFILE - CONFIRM REPLACE";
    private static final String CANNOTDELETE_GROUP = "Tablegroup - PROFILE -  INFO";
    private static final String CANNOTDELETE_WINDOW = "PROFILE -  INFO";
    private static final String REPLACE_TEXT = "Profile - REPLACE";
    private static final String REPLACE_TEXT_KEY = "PROFILENAME";
    private long _menu = 0L;
    private Table table = null;
    private long tablet = 0L;
    private long tablet_licence = 0L;
    private long[] tablet_licence_text;
    private PoPUpMenu warning_delete = null;
    private PoPUpMenu warning_replace = null;
    private PoPUpMenu info_cannotdelete = null;
    private long replace_text = 0L;
    private String replace_text_store;
    private String profile_under_warning = null;
    private line_data line_data_under_warning = null;
    private String newProfilename_under_warning = null;
    private table_data TABLE_DATA = new table_data();
    EditName editName = new EditName();

    public ProfileSelectProfile(long _menu, long[] controls, long window, long exitButton, long defaultButton, long okButton, Panel parent) {
        super(_menu, window, controls, exitButton, defaultButton, okButton, 0L, parent);
        int i;
        this._menu = _menu;
        this.tablet = menues.FindFieldInMenu(_menu, TABLET);
        this.tablet_licence = menues.FindFieldInMenu(_menu, TABLET_LICENCE);
        this.tablet_licence_text = new long[TABLET_LICENCETEXT.length];
        for (i = 0; i < TABLET_LICENCETEXT.length; ++i) {
            this.tablet_licence_text[i] = menues.FindFieldInMenu(_menu, TABLET_LICENCETEXT[i]);
        }
        for (i = 0; i < BUTTONS.length; ++i) {
            Object field = menues.ConvertMenuFields(menues.FindFieldInMenu(_menu, BUTTONS[i]));
            menues.SetScriptOnControl(_menu, field, this, METHODS[i], 4L);
        }
        this.table = new Table(_menu, TABLE, RANGER);
        this.table.fillWithLines("..\\data\\config\\menu\\menu_MAIN.xml", LINES, ELEMENTS);
        this.table.takeSetuperForAllLines(this);
        this.reciveTableData();
        this.build_tree_data();
        this.table.initLinesSelection(ELEMENTS[0]);
        this.table.addListener(this);
        this.warning_delete = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_MAIN.xml", DELETE_GROUP, DELETE_WINDOW);
        this.warning_replace = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_MAIN.xml", REPLACE_GROUP, REPLACE_WINDOW);
        this.info_cannotdelete = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_MAIN.xml", CANNOTDELETE_GROUP, CANNOTDELETE_WINDOW);
        this.warning_delete.addListener(new InWarning(2));
        this.warning_replace.addListener(new InWarning(4));
        this.info_cannotdelete.addListener(new InWarning(1));
        this.replace_text = this.warning_replace.getField(REPLACE_TEXT);
        if (this.replace_text != 0L) {
            this.replace_text_store = menues.GetFieldText(this.replace_text);
        }
    }

    private Cmenu_TTI convertTableData() {
        Cmenu_TTI root = new Cmenu_TTI();
        for (int i = 0; i < this.TABLE_DATA.all_profiles.size(); ++i) {
            Cmenu_TTI ch = new Cmenu_TTI();
            ch.toshow = true;
            ch.ontop = i == 0;
            ch.item = this.TABLE_DATA.all_profiles.get(i);
            root.children.add(ch);
        }
        return root;
    }

    private void buildvoidcells() {
        block4: {
            block3: {
                if (this.TABLE_DATA.all_profiles.size() >= this.table.getNumRows()) break block3;
                int dif = this.table.getNumRows() - this.TABLE_DATA.all_profiles.size();
                for (int i = 0; i < dif; ++i) {
                    line_data data = new line_data();
                    data.wheather_show = false;
                    this.TABLE_DATA.all_profiles.add(data);
                }
                break block4;
            }
            int count_good_data = 0;
            Iterator<line_data> iter = this.TABLE_DATA.all_profiles.iterator();
            while (iter.hasNext() && iter.next().wheather_show) {
                ++count_good_data;
            }
            if (count_good_data < this.table.getNumRows() || count_good_data >= this.TABLE_DATA.all_profiles.size()) break block4;
            for (int i = this.TABLE_DATA.all_profiles.size() - 1; i >= count_good_data; --i) {
                this.TABLE_DATA.all_profiles.remove(i);
            }
        }
    }

    // @Override
    public void afterInit() {
        super.afterInit();
        this.table.afterInit();
        this.editName.init();
        if (this.tablet != 0L) {
            menues.SetBlindess(this.tablet, true);
            menues.SetIgnoreEvents(this.tablet, true);
        }
        this.table.select_line(this.TABLE_DATA.current_profile);
        this.warning_delete.afterInit();
        this.warning_replace.afterInit();
        this.info_cannotdelete.afterInit();
    }

    private void build_tree_data() {
        this.table.reciveTreeData(this.convertTableData());
    }

    public void onSelect(long _menu, MENUsimplebutton_field button) {
        this.TABLE_DATA.current_profile = this.table.getSelected();
        ProfileManagement.getProfileManager().SetPrifile(this.TABLE_DATA.all_profiles.get((int)this.TABLE_DATA.current_profile).profile_name);
        StartMenu.getCurrentObject().update_profile_name();
        StartMenu.getCurrentObject().restoreProfileValuesToMenu();
        this.exitDialog();
    }

    public void onDelete(long _menu, MENUsimplebutton_field button) {
        if (this.table.getSelected() >= this.TABLE_DATA.all_profiles.size()) {
            Log.menu("ERRORR onDelete has wrong behaovoir.");
            return;
        }
        int selected = this.table.getSelected();
        this.profile_under_warning = this.TABLE_DATA.all_profiles.get((int)selected).profile_name;
        if (ProfileManagement.getProfileManager().GetCurrentProfileName().compareToIgnoreCase(this.profile_under_warning) == 0) {
            this.info_cannotdelete.show();
        } else {
            this.warning_delete.show();
        }
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
                obj.text = line.profile_name;
                menues.UpdateMenuField(obj);
                menues.SetBlindess(obj.nativePointer, false);
                menues.SetIgnoreEvents(obj.nativePointer, false);
            }
        }
        menues.SetShowField(obj.nativePointer, true);
    }

    // @Override
    public void SetupLineInTable(MENUEditBox obj, Cmenu_TTI table_node) {
        menues.SetBlindess(obj.nativePointer, true);
        menues.SetIgnoreEvents(obj.nativePointer, true);
        menues.SetShowField(obj.nativePointer, false);
    }

    // @Override
    public void SetupLineInTable(MENUsimplebutton_field obj, Cmenu_TTI table_node) {
        line_data line = (line_data)table_node.item;
        menues.SetFieldText(obj.nativePointer, line.profile_name);
    }

    // @Override
    public void SetupLineInTable(MENUText_field obj, Cmenu_TTI table_node) {
        line_data line = (line_data)table_node.item;
        menues.SetFieldText(obj.nativePointer, line.profile_name);
    }

    // @Override
    public void SetupLineInTable(MENUTruckview obj, Cmenu_TTI table_node) {
        line_data line = (line_data)table_node.item;
        menues.SetFieldText(obj.nativePointer, line.profile_name);
    }

    // @Override
    public void SetupLineInTable(MENU_ranger obj, Cmenu_TTI table_node) {
        line_data line = (line_data)table_node.item;
        menues.SetFieldText(obj.nativePointer, line.profile_name);
    }

    // @Override
    public void selectLineEvent(Table table, int line) {
        this.editName.selectLine(line);
        line_data data = (line_data)table.getItemOnLine((int)line).item;
        if (this.tablet != 0L) {
            menues.SetBlindess(this.tablet, true);
            menues.SetIgnoreEvents(this.tablet, true);
            menues.SetFieldState(this.tablet, data.logo_state);
            menues.SetFieldText(this.tablet_licence, data.profile_name);
            menues.UpdateMenuField(menues.ConvertMenuFields(this.tablet_licence));
            boolean is_light = ProfileNewProfile.isLightPlate(data.logo_state);
            for (int i = 0; i < this.tablet_licence_text.length; ++i) {
                menues.SetShowField(this.tablet_licence_text[i], is_light && i == 0 || !is_light && i == 1);
                menues.SetFieldText(this.tablet_licence_text[i], data.logo_name);
                menues.UpdateMenuField(menues.ConvertMenuFields(this.tablet_licence_text[i]));
            }
        }
    }

    // @Override
    public void update() {
        this.editName.from_update = true;
        super.update();
        this.reciveTableData();
        this.table.updateTreeData(this.convertTableData());
        this.table.refresh();
        this.table.select_line(this.TABLE_DATA.current_profile);
        this.editName.from_update = false;
        if (this.tablet != 0L) {
            menues.SetBlindess(this.tablet, true);
            menues.SetIgnoreEvents(this.tablet, true);
        }
    }

    private void reciveTableData() {
        ProfileManagement pro = ProfileManagement.getProfileManager();
        Vector profiles = pro.GetExistsProfiles();
        Iterator iter = profiles.iterator();
        int count_iteration = 0;
        this.TABLE_DATA.current_profile = 0;
        this.TABLE_DATA.all_profiles.clear();
        while (iter.hasNext()) {
            line_data data = new line_data();
            data.profile_name = (String)iter.next();
            data.logo_state = pro.GetProfileLogo(data.profile_name);
            data.logo_name = pro.GetProfileLicensePlateString(data.profile_name).toUpperCase() + pro.GetDefaultLicensePlateSuffix();
            this.TABLE_DATA.all_profiles.add(data);
            ++count_iteration;
        }
        if (-1 == this.TABLE_DATA.current_profile) {
            Log.menu("ERRORR. Current profile is absent above all profiles.");
        }
        this.buildvoidcells();
    }

    // @Override
    public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {
    }

    // @Override
    protected void onShow(boolean value) {
        if (this.tablet != 0L) {
            line_data data;
            menues.SetBlindess(this.tablet, true);
            menues.SetIgnoreEvents(this.tablet, true);
            if (value && this.table != null && (data = (line_data)this.table.getSelectedData().item) != null) {
                menues.SetFieldState(this.tablet, data.logo_state);
                menues.SetFieldText(this.tablet_licence, data.profile_name);
                menues.UpdateMenuField(menues.ConvertMenuFields(this.tablet_licence));
                boolean is_light = ProfileNewProfile.isLightPlate(data.logo_state);
                for (int i = 0; i < this.tablet_licence_text.length; ++i) {
                    menues.SetShowField(this.tablet_licence_text[i], is_light && i == 0 || !is_light && i == 1);
                    menues.SetFieldText(this.tablet_licence_text[i], data.logo_name);
                    menues.UpdateMenuField(menues.ConvertMenuFields(this.tablet_licence_text[i]));
                }
            }
        }
    }

    // @Override
    protected void onFreeze(boolean value) {
        if (this.tablet != 0L) {
            menues.SetBlindess(this.tablet, true);
            menues.SetIgnoreEvents(this.tablet, true);
        }
    }

    // @Override
    public void exitMenu() {
        super.exitMenu();
        this.table.deinit();
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
                    ProfileManagement.getProfileManager().DeleteProfile(ProfileSelectProfile.this.profile_under_warning);
                    ProfileSelectProfile.this.update();
                    ProfileSelectProfile.this.table.select_line(0);
                    StartMenu.getCurrentObject().update_profile_name();
                    break;
                }
                case 4: {
                    if (ProfileManagement.getProfileManager().DeleteProfile(ProfileSelectProfile.this.newProfilename_under_warning)) {
                        String oldprofile = ((ProfileSelectProfile)ProfileSelectProfile.this).line_data_under_warning.profile_name;
                        if (ProfileManagement.getProfileManager().RenameProfile(oldprofile, ProfileSelectProfile.this.newProfilename_under_warning)) {
                            ((ProfileSelectProfile)ProfileSelectProfile.this).line_data_under_warning.profile_name = ProfileSelectProfile.this.newProfilename_under_warning;
                        }
                    }
                    ProfileSelectProfile.this.update();
                    ProfileSelectProfile.this.newProfilename_under_warning = null;
                    ProfileSelectProfile.this.line_data_under_warning = null;
                }
            }
        }

        public void onClose() {
            switch (this.state) {
                case 4: {
                    ProfileSelectProfile.this.table.refresh();
                    ProfileSelectProfile.this.newProfilename_under_warning = null;
                    ProfileSelectProfile.this.line_data_under_warning = null;
                }
            }
        }

        public void onOpen() {
        }

        public void onCancel() {
        }
    }

    class EditName {
        private final String METHOD_CHANGENAME = "changeProfileName";
        private final String METHOD_DISSMIS = "dissmisProfileName";
        private final int BACKFIELD = 0;
        private final int EDITFIELD = 1;
        private int lastline = -1;
        private long[] controls;
        private long[] controls_back;
        boolean from_update = false;

        EditName() {
        }

        void selectLine(int line) {
            if (this.from_update) {
                return;
            }
            if (this.lastline == line) {
                if (line < 0 || line >= this.controls.length) {
                    Log.menu("ERRORR.ProfileSelectProfile EditName selectLine - bad value " + line + " with controls.length " + this.controls.length);
                    return;
                }
                line_data data = (line_data)((ProfileSelectProfile)ProfileSelectProfile.this).table.getItemOnLine((int)line).item;
                menues.setfocuscontrolonmenu(ProfileSelectProfile.this._menu, this.controls[line]);
                menues.SetShowField(this.controls[line], true);
                menues.SetBlindess(this.controls[line], false);
                menues.SetIgnoreEvents(this.controls[line], false);
                menues.SetFieldText(this.controls[line], data.profile_name);
                menues.SetFieldText(this.controls_back[line], "");
                menues.SetShowField(this.controls_back[line], false);
                menues.UpdateMenuField(menues.ConvertMenuFields(this.controls[line]));
                menues.UpdateMenuField(menues.ConvertMenuFields(this.controls_back[line]));
            }
            this.lastline = line;
        }

        void init() {
            this.controls_back = ProfileSelectProfile.this.table.getLineStatistics_controls(ELEMENTS[0]);
            this.controls = ProfileSelectProfile.this.table.getLineStatistics_controls(ELEMENTS[1]);
            for (int i = 0; i < this.controls.length; ++i) {
                menues.SetScriptOnControl(ProfileSelectProfile.this._menu, menues.ConvertMenuFields(this.controls[i]), this, "changeProfileName", 16L);
                menues.SetScriptOnControl(ProfileSelectProfile.this._menu, menues.ConvertMenuFields(this.controls[i]), this, "dissmisProfileName", 19L);
            }
        }

        void dissmisProfileName(long _menu, MENUEditBox obj) {
            this.from_update = true;
            ProfileSelectProfile.this.table.refreshLine(this.lastline);
            this.from_update = false;
        }

        void changeProfileName(long _menu, MENUEditBox obj) {
            menues.ConvertMenuFields(obj.nativePointer);
            String text = menues.GetFieldText(obj.nativePointer);
            if (text.compareToIgnoreCase("") == 0) {
                ProfileSelectProfile.this.table.refresh();
                return;
            }
            line_data data = (line_data)((ProfileSelectProfile)ProfileSelectProfile.this).table.getItemOnLine((int)this.lastline).item;
            String oldprofile = data.profile_name;
            if (oldprofile.compareToIgnoreCase(text) != 0) {
                if (ProfileManagement.getProfileManager().IsProfileExists(text)) {
                    ProfileSelectProfile.this.newProfilename_under_warning = text;
                    ProfileSelectProfile.this.line_data_under_warning = data;
                    if (ProfileSelectProfile.this.replace_text != 0L) {
                        KeyPair[] keys = new KeyPair[]{new KeyPair(ProfileSelectProfile.REPLACE_TEXT_KEY, text)};
                        menues.SetFieldText(ProfileSelectProfile.this.replace_text, MacroKit.Parse(ProfileSelectProfile.this.replace_text_store, keys));
                        menues.UpdateMenuField(menues.ConvertMenuFields(ProfileSelectProfile.this.replace_text));
                    }
                    ProfileSelectProfile.this.warning_replace.show();
                } else {
                    data.profile_name = text;
                    ProfileManagement.getProfileManager().RenameProfile(oldprofile, text);
                    ProfileSelectProfile.this.table.refresh();
                    StartMenu.getCurrentObject().update_profile_name();
                }
            }
        }
    }

    static class table_data {
        Vector<line_data> all_profiles = new Vector();
        int current_profile = 1;

        table_data() {
        }
    }

    static class line_data {
        boolean wheather_show = true;
        int logo_state;
        String logo_name = "RIPP";
        String profile_name;

        line_data() {
        }
    }
}

