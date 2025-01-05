/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import java.util.Iterator;
import java.util.Vector;
import menu.IValueChanged;
import menu.JavaEvents;
import menu.ListOfAlternatives;
import menu.MENUsimplebutton_field;
import menu.RadioGroupSmartSwitch;
import menu.SliderGroupRadioButtons;
import menu.TableOfElements;
import menu.menues;
import menuscript.IPoPUpMenuListener;
import menuscript.PoPUpMenu;
import menuscript.mainmenu.CA;
import menuscript.mainmenu.Panel;
import menuscript.mainmenu.PanelDialog;
import menuscript.mainmenu.PopUpSelfClose;
import menuscript.mainmenu.StartMenu;
import menuscript.parametrs.BlockMemo;
import rnrcore.loc;

public class SettingsVideo
extends PanelDialog
implements IValueChanged,
IPoPUpMenuListener {
    private static final String TITLE = loc.getMENUString("common\\SETTINGS FOR:");
    protected static String TABLE_DEVICE = "TABLEGROUP - SETTINGS - VIDEO - 1 60";
    protected static String TABLE_SETTINGS = "TABLEGROUP - SETTINGS - VIDEO - 10 60";
    protected static String RANGER = "Tableranger - SETTINGS - VIDEO";
    protected static String WARNING = "Tablegroup - SETTINGS - VIDEO - CONFIRM MESSAGE";
    protected static String WARNING_WND = "SETTINGS - VIDEO - CONFIRM MESSAGE";
    private static final String LISTOFALTERANTIVESGROUP = "Tablegroup - ELEMENTS - List Special";
    private static final String DEVICETITLE = "CURRENT VIDEO DEVICE";
    private static final int NUM_SETTINGS = 13;
    private static final int RESOLUTION_SETTING_NOM = 0;
    private static final int AA_SETTING_NOM = 1;
    private static final int tip_list_resolutions = 1;
    private static final int tip_slider = 2;
    private static final int tip_switch = 3;
    private static final int[] settings_tips = new int[]{1, 1, 2, 2, 3, 2, 2, 3, 2, 3, 2, 2, 3};
    private static final String[] settings_titles = new String[]{"SETTINGS OPTION RESOLUTION", "SETTINGS OPTION ANTIALIASING", "SETTINGS OPTION FILTERING", "SETTINGS OPTION SHADOWS", "SETTINGS OPTION 3DCLOUDS", "SETTINGS OPTION VISIBILITYRANGE", "SETTINGS OPTION VEHICLEDETAIL", "SETTINGS OPTION DROPS", "SETTINGS OPTION HDR", "SETTINGS OPTION MOTIONBLUR", "SETTINGS OPTION REFLECTIONDETAILS", "SETTINGS OPTION REFLECTIONUPDATERATE", "SETTINGS OPTION LIGHTMAPS"};
    public int[] settings_canchage = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    private TableOfElements table_settings = null;
    private TableOfElements table_device = null;
    private ListOfAlternatives devices = null;
    private ListOfAlternatives resolutionsList = null;
    private ListOfAlternatives aliasingList = null;
    protected PoPUpMenu warning = null;
    private long _menu = 0L;
    private boolean inEvent = true;
    Vector<String> devises_str = new Vector();
    int current_device = 0;
    Vector good_supportedResolutions = new Vector();
    resolution currentResolution = new resolution();
    resolution in_currentResolution = new resolution();
    resolution confirmedResolution = new resolution();
    resolution defaultResolution = new resolution();
    Vector good_supportedAntialiasing = new Vector();
    int currentAliasing = 0;
    int in_currentAliasing = 0;
    int confirmedAliasing = 0;
    int defaultAliasing = 0;
    protected boolean res_changed = false;
    protected boolean aliasing_changed = false;
    protected static boolean res_changed_menu = false;
    protected static resolution lastResolution = null;
    protected static int lastAliasing = 0;
    protected static BlockMemo lastParams = null;
    protected static boolean applyLastParams = false;

    public SettingsVideo(long _menu, long[] controls, long window, long exitButton, long defaultButton, long okButton, long applyButton, Panel parent) {
        super(_menu, window, controls, exitButton, defaultButton, okButton, applyButton, parent);
        this._menu = _menu;
        JavaEvents.SendEvent(20, -1, this);
        String[] str_devises = this.getDevices();
        this.res_changed = false;
        this.aliasing_changed = false;
        int dev = this.current_device;
        this.table_settings = new TableOfElements(_menu, TABLE_SETTINGS, RANGER);
        this.confirmedResolution = this.currentResolution;
        this.confirmedAliasing = this.currentAliasing;
        int curres = this.findResolution(this.currentResolution);
        int default_res = this.findResolution(this.defaultResolution);
        int cur_alias = this.currentAliasing;
        int default_alias = this.defaultAliasing;
        block5: for (int i = 0; i < 13; ++i) {
            switch (settings_tips[i]) {
                case 1: {
                    ListOfAlternatives lst;
                    if (i == 0) {
                        this.resolutionsList = lst = CA.createListOfAlternatives(loc.getMENUString(settings_titles[i]), this.getResolutionsList(), this.settings_canchage[i] == 1);
                        lst.load(_menu);
                        this.param_values.addParametr(settings_titles[i] + str_devises[dev], curres, default_res, lst);
                        this.table_settings.insert(lst);
                        lst.addListener(new ListenerResolutionChanger(this));
                        continue block5;
                    }
                    if (i != 1) continue block5;
                    this.aliasingList = lst = CA.createListOfAlternatives(loc.getMENUString(settings_titles[i]), this.getAntialiasingList(), this.settings_canchage[i] == 1);
                    lst.load(_menu);
                    this.param_values.addParametr(settings_titles[i] + str_devises[dev], cur_alias, default_alias, lst);
                    this.table_settings.insert(lst);
                    lst.addListener(new ListenerAliasingChanger(this));
                    continue block5;
                }
                case 2: {
                    SliderGroupRadioButtons sldr = CA.createSliderRadioButtons(loc.getMENUString(settings_titles[i]), 0, 100, 0, this.settings_canchage[i] == 1);
                    sldr.load(_menu);
                    this.param_values.addParametr(settings_titles[i] + str_devises[dev], 0, 0, sldr);
                    this.table_settings.insert(sldr);
                    continue block5;
                }
                case 3: {
                    RadioGroupSmartSwitch rgr = CA.createRadioGroupSmartSwitch(loc.getMENUString(settings_titles[i]), true, this.settings_canchage[i] == 1);
                    rgr.load(_menu);
                    this.param_values.addParametr(settings_titles[i] + str_devises[dev], false, false, rgr);
                    this.table_settings.insert(rgr);
                }
            }
        }
        this.table_device = new TableOfElements(_menu, TABLE_DEVICE);
        this.devices = new ListOfAlternatives(parent.menu.XML_FILE, LISTOFALTERANTIVESGROUP, TITLE, str_devises, false);
        this.devices.load(_menu);
        this.table_device.insert(this.devices);
        this.param_values.addParametr(DEVICETITLE, this.current_device, this.current_device, this.devices);
        this.devices.addListener(this);
        JavaEvents.SendEvent(20, 0, this.param_values);
        this.currentAliasing = this.param_values.getIntegerValue(settings_titles[1] + str_devises[dev]);
        this.warning = new PopUpSelfClose(_menu, parent.menu.XML_FILE, WARNING, WARNING_WND);
        this.warning.addListener(this);
    }

    public void exitMenu() {
        this.table_settings.DeInit();
        this.table_device.DeInit();
        super.exitMenu();
    }

    public void afterInit() {
        this.inEvent = true;
        this.table_settings.initTable();
        this.table_device.initTable();
        this.warning.afterInit();
        super.afterInit();
        if (res_changed_menu) {
            this.warning.show();
            res_changed_menu = false;
        }
        this.inEvent = false;
    }

    public void OnOk(long _menu, MENUsimplebutton_field button) {
        if (this.res_changed || this.aliasing_changed) {
            lastResolution = this.confirmedResolution;
            this.confirmedResolution = this.currentResolution;
            lastAliasing = this.confirmedAliasing;
            this.confirmedAliasing = this.currentAliasing;
            this.param_values.onOk();
            lastParams.recordChanges(this.param_values);
            JavaEvents.SendEvent(21, -1, this);
            JavaEvents.SendEvent(21, 0, this.param_values);
            if (this.res_changed) {
                StartMenu.menuNeedRestoreLastState();
                menues.CallMenuCallBack_ExitMenu(_menu);
            }
            res_changed_menu = this.res_changed;
            this.res_changed = false;
            this.aliasing_changed = false;
            return;
        }
        this.param_values.onOk();
        lastParams = null;
        JavaEvents.SendEvent(21, -1, this);
        JavaEvents.SendEvent(21, 0, this.param_values);
        this.exitDialog();
        this.res_changed = false;
    }

    public void OnApply(long _menu, MENUsimplebutton_field button) {
        if (this.res_changed || this.aliasing_changed) {
            lastResolution = this.confirmedResolution;
            this.confirmedResolution = this.currentResolution;
            lastAliasing = this.confirmedAliasing;
            this.confirmedAliasing = this.currentAliasing;
            this.param_values.onOk();
            lastParams.recordChanges(this.param_values);
            JavaEvents.SendEvent(21, -1, this);
            JavaEvents.SendEvent(21, 0, this.param_values);
            if (this.res_changed) {
                StartMenu.menuNeedRestoreLastState();
                menues.CallMenuCallBack_ExitMenu(_menu);
            }
            this.aliasing_changed = false;
            res_changed_menu = this.res_changed;
            this.res_changed = false;
            return;
        }
        this.param_values.onOk();
        lastParams = null;
        JavaEvents.SendEvent(21, -1, this);
        JavaEvents.SendEvent(21, 0, this.param_values);
        this.res_changed = false;
    }

    public void update() {
        this.inEvent = true;
        if (applyLastParams) {
            lastParams.restoreChanges(this.param_values);
            lastParams = null;
            applyLastParams = false;
            int dev = this.current_device;
            String[] str_devises = this.getDevices();
            int curres = this.findResolution(this.confirmedResolution);
            this.param_values.setIntegerValue(settings_titles[0] + str_devises[dev], curres);
            this.resolutionsList.setValue(curres);
            this.param_values.setIntegerValue(settings_titles[1] + str_devises[dev], this.confirmedAliasing);
            this.aliasingList.setValue(this.confirmedAliasing);
        } else {
            super.update();
        }
        if (null == lastParams) {
            lastParams = new BlockMemo(this.param_values);
        }
        this.inEvent = false;
    }

    private String[] getDevices() {
        String[] res = new String[this.devises_str.size()];
        Iterator<String> iter = this.devises_str.iterator();
        int counter = 0;
        while (iter.hasNext()) {
            res[counter++] = iter.next();
        }
        return res;
    }

    public void valueChanged() {
        this.current_device = this.devices.getValue();
        JavaEvents.SendEvent(22, 0, this.param_values);
    }

    private int findResolution(resolution res_to_find) {
        if (this.good_supportedResolutions.isEmpty()) {
            return -1;
        }
        int res = 0;
        Iterator iter = this.good_supportedResolutions.iterator();
        while (iter.hasNext()) {
            if (((resolution)iter.next()).isSame(res_to_find)) {
                return res;
            }
            ++res;
        }
        return -1;
    }

    private String[] getResolutionsList() {
        String[] res = new String[this.good_supportedResolutions.size()];
        if (this.good_supportedResolutions.isEmpty()) {
            return res;
        }
        int count = 0;
        Iterator iter = this.good_supportedResolutions.iterator();
        while (iter.hasNext()) {
            res[count++] = ((resolution)iter.next()).to_str();
        }
        return res;
    }

    private String[] getAntialiasingList() {
        String[] res = new String[this.good_supportedAntialiasing.size()];
        if (this.good_supportedAntialiasing.isEmpty()) {
            return res;
        }
        int count = 0;
        Iterator iter = this.good_supportedAntialiasing.iterator();
        while (iter.hasNext()) {
            res[count++] = ((antialisaing)iter.next()).to_str();
        }
        return res;
    }

    public void onAgreeclose() {
        lastResolution = null;
    }

    public void onClose() {
        this.res_changed = true;
        this.aliasing_changed = true;
        this.confirmedResolution = lastResolution;
        this.currentResolution = lastResolution;
        this.confirmedAliasing = lastAliasing;
        this.currentAliasing = lastAliasing;
        applyLastParams = true;
        lastParams.restore(this.param_values);
        JavaEvents.SendEvent(21, -1, this);
        JavaEvents.SendEvent(21, 0, this.param_values);
        StartMenu.menuNeedRestoreLastState();
        menues.CallMenuCallBack_ExitMenu(this._menu);
        this.res_changed = false;
        this.aliasing_changed = false;
    }

    public void onOpen() {
    }

    public void onCancel() {
    }

    static class antialisaing {
        int num_of_samples = 0;

        antialisaing() {
        }

        String to_str() {
            return loc.getMENUString("AA_" + this.num_of_samples + "x");
        }
    }

    static class resolution {
        int x;
        int y;
        int bits;
        String format;

        resolution() {
        }

        String to_str() {
            return "" + this.x + "x" + this.y + " (" + this.format + ")";
        }

        boolean isSame(resolution value) {
            return value.x == this.x && value.y == this.y && value.bits == this.bits;
        }
    }

    class ListenerResolutionChanger
    implements IValueChanged {
        SettingsVideo parent = null;

        ListenerResolutionChanger(SettingsVideo _parnet) {
            this.parent = _parnet;
        }

        public void valueChanged() {
            SettingsVideo.this.currentResolution = (resolution)SettingsVideo.this.good_supportedResolutions.get(SettingsVideo.this.resolutionsList.getValue());
            boolean bl = SettingsVideo.this.res_changed = !SettingsVideo.this.confirmedResolution.isSame(SettingsVideo.this.currentResolution);
            if (!SettingsVideo.this.inEvent) {
                SettingsVideo.this.inEvent = true;
                int dev = SettingsVideo.this.current_device;
                String[] str_devises = SettingsVideo.this.getDevices();
                SettingsVideo.this.in_currentAliasing = SettingsVideo.this.currentAliasing;
                JavaEvents.SendEvent(21, 2, this.parent);
                SettingsVideo.this.currentAliasing = SettingsVideo.this.in_currentAliasing;
                SettingsVideo.this.aliasing_changed = SettingsVideo.this.confirmedAliasing != SettingsVideo.this.currentAliasing;
                SettingsVideo.this.param_values.setIntegerValueChange(settings_titles[1] + str_devises[dev], SettingsVideo.this.currentAliasing);
                SettingsVideo.this.inEvent = false;
            }
        }
    }

    class ListenerAliasingChanger
    implements IValueChanged {
        SettingsVideo parent = null;

        ListenerAliasingChanger(SettingsVideo _parent) {
            this.parent = _parent;
        }

        public void valueChanged() {
            SettingsVideo.this.currentAliasing = SettingsVideo.this.aliasingList.getValue();
            boolean bl = SettingsVideo.this.aliasing_changed = SettingsVideo.this.confirmedAliasing != SettingsVideo.this.currentAliasing;
            if (!SettingsVideo.this.inEvent) {
                SettingsVideo.this.inEvent = true;
                int dev = SettingsVideo.this.current_device;
                String[] str_devises = SettingsVideo.this.getDevices();
                SettingsVideo.this.in_currentAliasing = SettingsVideo.this.currentAliasing;
                SettingsVideo.this.in_currentResolution.x = SettingsVideo.this.currentResolution.x;
                SettingsVideo.this.in_currentResolution.y = SettingsVideo.this.currentResolution.y;
                SettingsVideo.this.in_currentResolution.bits = SettingsVideo.this.currentResolution.bits;
                JavaEvents.SendEvent(21, 1, this.parent);
                int curres = SettingsVideo.this.findResolution(SettingsVideo.this.in_currentResolution);
                SettingsVideo.this.res_changed = !SettingsVideo.this.confirmedResolution.isSame(SettingsVideo.this.in_currentResolution);
                SettingsVideo.this.currentResolution = (resolution)SettingsVideo.this.good_supportedResolutions.get(curres);
                SettingsVideo.this.param_values.setIntegerValueChange(settings_titles[0] + str_devises[dev], curres);
                SettingsVideo.this.inEvent = false;
            }
        }
    }
}

