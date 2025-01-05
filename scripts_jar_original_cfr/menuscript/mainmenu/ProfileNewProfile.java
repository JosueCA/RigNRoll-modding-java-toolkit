/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import menu.IValueChanged;
import menu.KeyPair;
import menu.MENUEditBox;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.RadioSmartSwitch;
import menu.TableOfElements;
import menu.menues;
import menuscript.EditFieldWrapper;
import menuscript.IEditFieldListener;
import menuscript.IPoPUpMenuListener;
import menuscript.PoPUpMenu;
import menuscript.mainmenu.Panel;
import menuscript.mainmenu.PanelDialog;
import menuscript.mainmenu.ProfileManagement;
import menuscript.mainmenu.StartMenu;
import rnrcore.loc;

public class ProfileNewProfile
extends PanelDialog
implements IValueChanged,
IPoPUpMenuListener,
IEditFieldListener {
    private static final String FIELD_PROFILENAME = "PROFILE NAME - VALUE";
    private static final String FIELD_PROFILE_LICENCEPLATE_LIGHT = "LICENSE PLATE - TEXT LIGHT";
    private static final String FIELD_PROFILE_LICENCEPLATE_DARK = "LICENSE PLATE - TEXT DARK";
    private static final String TABLE_NAME = "TABLEGROUP - PROFILE - NEW PROFILE - 2 70";
    private static final String PANEL_XML = "..\\data\\config\\menu\\menu_MAIN.xml";
    private static final String PANEL_CONTROLGROUP = "Tablegroup - ELEMENTS - Profile Panel";
    private static final String PANEL_TITLE = "LICENSE PLATE";
    private static final String WARNING_XML = "..\\data\\config\\menu\\menu_MAIN.xml";
    private static final String WARNING_GROUP = "Tablegroup - PROFILE -  NEW PROFILE - CONFIRM REPLACE";
    private static final String WARNING_WINDOW = "PROFILE -  NEW PROFILE - CONFIRM REPLACE";
    private static final String WARNING_TEXT = "Profile - REPLACE";
    private static final String WARNING_TEXT_KEY = "PROFILENAME";
    private static final String CANNOTDELETE_GROUP = "Tablegroup - PROFILE -  NEW PROFILE - CANNOT EDIT";
    private static final String CANNOTDELETE_WINDOW = "PROFILE -  NEW PROFILE - CANNOT EDIT";
    private static final int NUM_STATES = 7;
    private long _menu = 0L;
    private long profilename = 0L;
    private TableOfElements table = null;
    RadioSmartSwitch panel = null;
    private int logo_state = 0;
    private PoPUpMenu warning = null;
    private PoPUpMenu info_cannotdelete = null;
    private long warning_text = 0L;
    private String warning_text_store;
    private EditFieldWrapper licence_light = null;
    private EditFieldWrapper licence_dark = null;
    public String licenceText = "";

    public void exitMenu() {
        this.table.DeInit();
        super.exitMenu();
    }

    public ProfileNewProfile(long _menu, long[] controls, long window, long exitButton, long defaultButton, long okButton, Panel parent) {
        super(_menu, window, controls, exitButton, defaultButton, okButton, 0L, parent);
        this._menu = _menu;
        this.table = new TableOfElements(_menu, TABLE_NAME);
        this.logo_state = ProfileManagement.getProfileManager().GetCurrentProfileLogo();
        this.panel = new RadioSmartSwitch("..\\data\\config\\menu\\menu_MAIN.xml", PANEL_CONTROLGROUP, loc.getMENUString(PANEL_TITLE), this.logo_state, 7, false);
        this.panel.load(_menu);
        this.table.insert(this.panel);
        this.param_values.addParametr(PANEL_TITLE, 0, 0, this.panel);
        this.panel.addListener(this);
        this.warning = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_MAIN.xml", WARNING_GROUP, WARNING_WINDOW);
        this.warning_text = this.warning.getField(WARNING_TEXT);
        if (this.warning_text != 0L) {
            this.warning_text_store = menues.GetFieldText(this.warning_text);
        }
        this.warning.addListener(this);
        this.info_cannotdelete = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_MAIN.xml", CANNOTDELETE_GROUP, CANNOTDELETE_WINDOW);
        this.info_cannotdelete.addListener(this);
        this.licence_light = new EditFieldWrapper(_menu, FIELD_PROFILE_LICENCEPLATE_LIGHT);
        this.licence_dark = new EditFieldWrapper(_menu, FIELD_PROFILE_LICENCEPLATE_DARK);
        this.licence_light.addListener(this);
        this.licence_dark.addListener(this);
        this.licenceText = this.getDefaultLicenceName_prefix();
        this.licence_light.setText(this.licenceText);
        this.licence_light.setSuffix(this.getDefaultLicenceName_suffix());
        this.licence_dark.setText(this.licenceText);
        this.licence_dark.setSuffix(this.getDefaultLicenceName_suffix());
    }

    public void afterInit() {
        boolean is_light;
        this.table.initTable();
        this.profilename = this.parent.menu.findField(FIELD_PROFILENAME);
        menues.SetFieldText(this.profilename, ProfileManagement.getProfileManager().GetCurrentProfileName());
        menues.UpdateMenuField(menues.ConvertMenuFields(this.profilename));
        this.warning.afterInit();
        this.info_cannotdelete.afterInit();
        if (this.profilename != 0L) {
            new EditName(this.profilename);
        }
        if (is_light = ProfileNewProfile.isLightPlate(this.logo_state)) {
            this.licence_light.show();
            this.licence_dark.hide();
        } else {
            this.licence_dark.show();
            this.licence_light.hide();
        }
    }

    public void OnOk(long _menu, MENUsimplebutton_field button) {
        menues.ConvertMenuFields(this.profilename);
        String text = menues.GetFieldText(this.profilename);
        if (ProfileManagement.getProfileManager().GetCurrentProfileName().compareToIgnoreCase(text) == 0) {
            if (this.logo_state == ProfileManagement.getProfileManager().GetCurrentProfileLogo() && ProfileManagement.getProfileManager().GetCurrentProfileLicensePlateString().compareToIgnoreCase(this.licenceText) == 0) {
                this.exitDialog();
            } else {
                this.info_cannotdelete.show();
            }
        } else if (ProfileManagement.getProfileManager().IsProfileExists(text)) {
            if (this.warning_text != 0L) {
                KeyPair[] keys = new KeyPair[]{new KeyPair(WARNING_TEXT_KEY, text)};
                menues.SetFieldText(this.warning_text, MacroKit.Parse(this.warning_text_store, keys));
                menues.UpdateMenuField(menues.ConvertMenuFields(this.warning_text));
            }
            this.warning.show();
        } else {
            ProfileManagement.getProfileManager().CreateProfile(text, this.logo_state, this.licenceText);
            super.OnOk(_menu, null);
            StartMenu.getCurrentObject().update_profile_name();
            StartMenu.getCurrentObject().restoreProfileValuesToMenu();
            this.exitDialog();
        }
    }

    public void valueChanged() {
        this.logo_state = this.panel.changeValue();
        boolean is_light = ProfileNewProfile.isLightPlate(this.logo_state);
        if (is_light) {
            this.licence_light.show();
            this.licence_dark.hide();
        } else {
            this.licence_dark.show();
            this.licence_light.hide();
        }
    }

    protected void onShow(boolean value) {
        if (value) {
            if (this.profilename != 0L) {
                menues.SetFieldText(this.profilename, ProfileManagement.getProfileManager().GetCurrentProfileName());
                menues.UpdateMenuField(menues.ConvertMenuFields(this.profilename));
            }
            this.licenceText = this.getDefaultLicenceName_prefix();
            if (this.licence_light != null && this.licenceText != null) {
                this.licence_light.setText(this.licenceText);
                this.licence_light.setSuffix(this.getDefaultLicenceName_suffix());
            }
            if (this.licence_light != null && this.licenceText != null) {
                this.licence_dark.setText(this.licenceText);
                this.licence_dark.setSuffix(this.getDefaultLicenceName_suffix());
            }
            if (this.panel != null) {
                this.logo_state = ProfileManagement.getProfileManager().GetCurrentProfileLogo();
                this.panel.reciveValue(this.logo_state);
            }
        }
    }

    public void onAgreeclose() {
        menues.ConvertMenuFields(this.profilename);
        String text = menues.GetFieldText(this.profilename);
        if (ProfileManagement.getProfileManager().DeleteProfile(text)) {
            if (ProfileManagement.getProfileManager().CreateProfile(text, this.logo_state, this.licenceText)) {
                // empty if block
            }
            super.OnOk(this._menu, null);
            StartMenu.getCurrentObject().update_profile_name();
            StartMenu.getCurrentObject().restoreProfileValuesToMenu();
            this.exitDialog();
        }
    }

    public void onCancel() {
    }

    public void onClose() {
    }

    public void onOpen() {
    }

    static boolean isLightPlate(int logo_state) {
        return (logo_state & 4) != 0;
    }

    private String getDefaultLicenceName_prefix() {
        String res = ProfileManagement.getProfileManager().GetCurrentProfileLicensePlateString();
        if (null == res) {
            res = "none";
        }
        return res.toUpperCase();
    }

    private String getDefaultLicenceName_suffix() {
        String res = ProfileManagement.getProfileManager().GetDefaultLicensePlateSuffix();
        if (null == res) {
            res = "503";
        }
        return res;
    }

    public void textDismissed(String text) {
        this.licenceText = text;
        this.licence_light.setText(this.licenceText);
        this.licence_dark.setText(this.licenceText);
    }

    public void textEntered(String text) {
        this.licenceText = text;
        this.licence_light.setText(this.licenceText);
        this.licence_dark.setText(this.licenceText);
    }

    class EditName {
        private final String METHOD_CHANGENAME = "changeProfileName";
        private final String METHOD_DISSMIS = "dissmisProfileName";
        String lastProfileName;
        String pendProfileName;

        EditName(long control) {
            Object obj = menues.ConvertMenuFields(control);
            menues.SetScriptOnControl(ProfileNewProfile.this._menu, obj, this, "dissmisProfileName", 19L);
            menues.SetScriptOnControl(ProfileNewProfile.this._menu, obj, this, "changeProfileName", 16L);
            this.lastProfileName = menues.GetFieldText(control);
        }

        void dissmisProfileName(long _menu, MENUEditBox obj) {
            this.pendProfileName = menues.GetFieldText(obj.nativePointer);
            if (this.pendProfileName.compareTo("") == 0) {
                obj.text = this.lastProfileName;
                menues.UpdateMenuField(obj);
            }
        }

        void changeProfileName(long _menu, MENUEditBox obj) {
            menues.ConvertMenuFields(obj.nativePointer);
            this.pendProfileName = menues.GetFieldText(obj.nativePointer);
            if (this.pendProfileName.compareTo("") == 0) {
                menues.SetFieldText(obj.nativePointer, this.lastProfileName);
                obj.text = this.lastProfileName;
                menues.UpdateMenuField(obj);
            } else {
                this.lastProfileName = this.pendProfileName;
            }
        }
    }
}

