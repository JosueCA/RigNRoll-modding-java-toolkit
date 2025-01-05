/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import menu.ContainerTextTitleTextValue;
import menu.IValueChanged;
import menu.JavaEvents;
import menu.ListOfAlternatives;
import menu.MENUsimplebutton_field;
import menu.RadioGroupSmartSwitch;
import menu.SliderGroupRadioButtons;
import menu.TableOfElements;
import menuscript.PoPUpMenu;
import menuscript.keybind.Bind;
import menuscript.keybind.KeyBindingsData;
import menuscript.mainmenu.CA;
import menuscript.mainmenu.Panel;
import menuscript.mainmenu.PanelDialog;
import menuscript.mainmenu.RemapControlsTable;
import rnrcore.loc;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SettingsControls
extends PanelDialog
implements IValueChanged {
    private static final String SENCITIVITY_TITLE = "SETTINGS SENCITIVITY TITLE";
    private static final String FORCEFEEDBACK_TITLE = "SETTINGS FORCEFEEDBACK TITLE";
    private static final String CENTERINGFORCE_TITLE = "SETTINGS CENTERINGFORCE TITLE";
    private static final String TRANSMISSION_TITLE = "OPTION TRANSSMITION";
    private static final String INVERTMOUSE_TITLE = "OPTION INVERT MOUSE";
    private static final String[] TRANSMISSION_VALUES = new String[]{loc.getMENUString("OPTION TRANSSMITION MANUAL"), loc.getMENUString("OPTION TRANSSMITION AUTO")};
    private static final String TITLE = loc.getMENUString("common\\SETTINGS FOR:");
    private static final String LISTOFALTERANTIVESGROUP = "Tablegroup - ELEMENTS - List Special";
    protected static String POPUP = "Tablegroup - SETTINGS - CONTROLS - CONFIRM MESSAGE";
    protected static String POPUPWND = null;
    protected static String TABLE = "TABLEGROUP - SETTINGS - CONTROLS - 1 60";
    protected static String TABLE_SETTINGS = "TABLEGROUP - SETTINGS - CONTROLS - 4 60";
    private static final int tip_list_list = 1;
    private static final int tip_slider = 2;
    private static final int tip_switch = 3;
    private static final String[][] keyboard_alternatives = new String[][]{null, null};
    private static final String[][] joystic_alternatives = new String[][]{null, null};
    private static final String[][] common_alternatives = new String[][]{TRANSMISSION_VALUES, null};
    private static final int[] settings_keyboard_tips = new int[]{2, 2};
    private static final int[] settings_joystick_tips = new int[]{2, 2};
    private static final int[] settings_comon_tips = new int[]{1, 3};
    private static final String[] settings_titles_keyboard = new String[]{"SETTINGS SENCITIVITY TITLE", "SETTINGS CENTERINGFORCE TITLE"};
    private static final String[] settings_titles_joystick = new String[]{"SETTINGS SENCITIVITY TITLE", "SETTINGS FORCEFEEDBACK TITLE"};
    private static final String[] settings_titles_common = new String[]{"OPTION TRANSSMITION", "OPTION INVERT MOUSE"};
    private TableOfElements table = null;
    private ListOfAlternatives currentDevice = null;
    private TableOfElements[] table_settings = null;
    protected RemapControlsTable remap_table = null;
    Vector m_inputdevices = new Vector();
    Vector m_default_inputdevices = new Vector();
    PoPUpMenu warning = null;
    ArrayList[] defaulted_bindings = null;
    ArrayList[] current_bindings = null;

    private boolean needToDevideBetweenDevices(String name) {
        return SENCITIVITY_TITLE.compareTo(name) == 0 || FORCEFEEDBACK_TITLE.compareTo(name) == 0;
    }

    public SettingsControls(long _menu, long[] controls, long window, long exitButton, long defaultButton, long okButton, long applyButton, Panel parent) {
        super(_menu, window, controls, exitButton, defaultButton, okButton, applyButton, parent);
        JavaEvents.SendEvent(0, 0, this);
        String[] str_devises = this.getDevices();
        int num_devices = str_devises.length;
        this.table = new TableOfElements(_menu, TABLE);
        this.currentDevice = new ListOfAlternatives(parent.menu.XML_FILE, LISTOFALTERANTIVESGROUP, TITLE, str_devises, false);
        this.currentDevice.load(_menu);
        this.table.insert(this.currentDevice);
        this.table_settings = new TableOfElements[num_devices];
        block18: for (int i = 0; i < num_devices; ++i) {
            this.table_settings[i] = new TableOfElements(_menu, TABLE_SETTINGS);
            switch (i) {
                case 0: {
                    RadioGroupSmartSwitch sw;
                    SliderGroupRadioButtons sl;
                    ListOfAlternatives lst;
                    int t;
                    block19: for (t = 0; t < settings_keyboard_tips.length; ++t) {
                        switch (settings_keyboard_tips[t]) {
                            case 1: {
                                lst = CA.createListOfAlternatives(loc.getMENUString(settings_titles_keyboard[t]), keyboard_alternatives[t], true);
                                lst.load(_menu);
                                this.table_settings[i].insert(lst);
                                if (!this.needToDevideBetweenDevices(settings_titles_keyboard[t])) {
                                    this.param_values.addParametr(settings_titles_keyboard[t], 50, 50, lst);
                                    continue block19;
                                }
                                this.param_values.addParametr(settings_titles_keyboard[t] + str_devises[i], 50, 50, lst);
                                continue block19;
                            }
                            case 2: {
                                sl = CA.createSliderRadioButtons(loc.getMENUString(settings_titles_keyboard[t]), 0, 100, 50, true);
                                sl.load(_menu);
                                this.table_settings[i].insert(sl);
                                if (!this.needToDevideBetweenDevices(settings_titles_keyboard[t])) {
                                    this.param_values.addParametr(settings_titles_keyboard[t], 50, 50, sl);
                                    continue block19;
                                }
                                this.param_values.addParametr(settings_titles_keyboard[t] + str_devises[i], 50, 50, sl);
                                continue block19;
                            }
                            case 3: {
                                sw = CA.createRadioGroupSmartSwitch(loc.getMENUString(settings_titles_keyboard[t]), true, true);
                                sw.load(_menu);
                                this.table_settings[i].insert(sw);
                                if (!this.needToDevideBetweenDevices(settings_titles_keyboard[t])) {
                                    this.param_values.addParametr(settings_titles_keyboard[t], false, false, sw);
                                    continue block19;
                                }
                                this.param_values.addParametr(settings_titles_keyboard[t] + str_devises[i], false, false, sw);
                            }
                        }
                    }
                    continue block18;
                }
                default: {
                    RadioGroupSmartSwitch sw;
                    SliderGroupRadioButtons sl;
                    ListOfAlternatives lst;
                    int t;
                    block20: for (t = 0; t < settings_joystick_tips.length; ++t) {
                        switch (settings_joystick_tips[t]) {
                            case 1: {
                                lst = CA.createListOfAlternatives(loc.getMENUString(settings_titles_joystick[t]), joystic_alternatives[t], true);
                                lst.load(_menu);
                                this.table_settings[i].insert(lst);
                                if (!this.needToDevideBetweenDevices(settings_titles_joystick[t])) {
                                    this.param_values.addParametr(settings_titles_joystick[t], 50, 50, lst);
                                    continue block20;
                                }
                                this.param_values.addParametr(settings_titles_joystick[t] + str_devises[i], 50, 50, lst);
                                continue block20;
                            }
                            case 2: {
                                sl = CA.createSliderRadioButtons(loc.getMENUString(settings_titles_joystick[t]), 0, 100, 50, true);
                                sl.load(_menu);
                                this.table_settings[i].insert(sl);
                                if (!this.needToDevideBetweenDevices(settings_titles_joystick[t])) {
                                    this.param_values.addParametr(settings_titles_joystick[t], 50, 50, sl);
                                    continue block20;
                                }
                                this.param_values.addParametr(settings_titles_joystick[t] + str_devises[i], 50, 50, sl);
                                continue block20;
                            }
                            case 3: {
                                sw = CA.createRadioGroupSmartSwitch(loc.getMENUString(settings_titles_joystick[t]), true, true);
                                sw.load(_menu);
                                this.table_settings[i].insert(sw);
                                if (!this.needToDevideBetweenDevices(settings_titles_joystick[t])) {
                                    this.param_values.addParametr(settings_titles_joystick[t], false, false, sw);
                                    continue block20;
                                }
                                this.param_values.addParametr(settings_titles_joystick[t] + str_devises[i], false, false, sw);
                            }
                        }
                    }
                }
            }
        }
        block21: for (int t = 0; t < settings_comon_tips.length; ++t) {
            switch (settings_comon_tips[t]) {
                case 1: {
                    ListOfAlternatives lst = CA.createListOfAlternatives(loc.getMENUString(settings_titles_common[t]), common_alternatives[t], true);
                    lst.load(_menu);
                    for (int i = 0; i < num_devices; ++i) {
                        this.table_settings[i].insert(lst);
                    }
                    this.param_values.addParametr(settings_titles_common[t], 50, 50, lst);
                    continue block21;
                }
                case 2: {
                    SliderGroupRadioButtons sl = CA.createSliderRadioButtons(loc.getMENUString(settings_titles_common[t]), 0, 100, 50, true);
                    sl.load(_menu);
                    for (int i = 0; i < num_devices; ++i) {
                        this.table_settings[i].insert(sl);
                    }
                    this.param_values.addParametr(settings_titles_common[t], 50, 50, sl);
                    continue block21;
                }
                case 3: {
                    RadioGroupSmartSwitch sw = CA.createRadioGroupSmartSwitch(loc.getMENUString(settings_titles_common[t]), true, true);
                    sw.load(_menu);
                    for (int i = 0; i < num_devices; ++i) {
                        this.table_settings[i].insert(sw);
                    }
                    this.param_values.addParametr(settings_titles_common[t], false, false, sw);
                }
            }
        }
        JavaEvents.SendEvent(14, 0, this.param_values);
        this.currentDevice.addListener(this);
        this.remap_table = new RemapControlsTable(_menu, parent);
        this.warning = new PoPUpMenu(_menu, parent.menu.XML_FILE, POPUP, POPUPWND);
        this.warning.addListener(this.remap_table);
        this.remap_table.warning = this.warning;
    }

    @Override
    public void exitMenu() {
        for (int dev = 0; dev < this.table_settings.length; ++dev) {
            this.table_settings[dev].DeInit();
        }
        this.table.DeInit();
        this.remap_table.deinit();
        super.exitMenu();
    }

    @Override
    public void afterInit() {
        super.afterInit();
        this.warning.afterInit();
        this.table.initTable();
        for (int i = 0; i < this.table_settings.length; ++i) {
            this.table_settings[i].initTable();
        }
        this.remap_table.afterInit();
    }

    @Override
    public void update() {
        if (null != this.current_bindings) {
            for (int i = 0; i < this.current_bindings.length; ++i) {
                this.current_bindings[i] = null;
            }
        }
        JavaEvents.SendEvent(14, 0, this.param_values);
        this.param_values.onUpdate();
        int cur_device = this.currentDevice.getValue();
        this.remap_table.fillTable(this.formDeviceBindingsData(cur_device), cur_device);
        for (int i = 0; i < this.table_settings.length; ++i) {
            if (i == cur_device) continue;
            this.table_settings[i].hideTable();
        }
        this.table_settings[cur_device].showTable();
    }

    @Override
    public void readParamValues() {
        JavaEvents.SendEvent(0, 0, this);
        this.update();
    }

    private String[] getDevices() {
        String[] res = new String[this.m_inputdevices.size()];
        Iterator iter = this.m_inputdevices.iterator();
        int i = 0;
        while (iter.hasNext()) {
            res[i++] = ((KeyBindingsData)iter.next()).getDeviceName();
        }
        return res;
    }

    ArrayList<RemapControlsTable.WrapTextsAndKeycode> formDeviceBindingsData(int num_device) {
        if (null == this.current_bindings) {
            this.current_bindings = new ArrayList[this.m_inputdevices.size()];
        }
        if (null != this.current_bindings[num_device]) {
            return this.current_bindings[num_device];
        }
        ArrayList<RemapControlsTable.WrapTextsAndKeycode> ret = new ArrayList<RemapControlsTable.WrapTextsAndKeycode>();
        Object device = this.m_inputdevices.get(num_device);
        if (device == null) {
            return ret;
        }
        KeyBindingsData data = (KeyBindingsData)device;
        Iterator<Bind> iter = data.getIterator();
        while (iter.hasNext()) {
            Bind bind = iter.next();
            String actionName = bind.action.getActionname();
            String codeName = bind.key != null ? bind.key.getKeyname() : (bind.axe != null ? bind.axe.getAxename() : "");
            RemapControlsTable.WrapTextsAndKeycode item = new RemapControlsTable.WrapTextsAndKeycode();
            item.texts = new ContainerTextTitleTextValue(actionName, codeName);
            item.bind = bind;
            ret.add(item);
        }
        data.freeIterator();
        this.current_bindings[num_device] = ret;
        if (null == this.defaulted_bindings) {
            this.defaulted_bindings = new ArrayList[this.m_default_inputdevices.size()];
        }
        if (null != this.defaulted_bindings[num_device]) {
            return this.current_bindings[num_device];
        }
        ArrayList<RemapControlsTable.WrapTextsAndKeycode> res = new ArrayList<RemapControlsTable.WrapTextsAndKeycode>();
        Object device2 = this.m_default_inputdevices.get(num_device);
        if (device2 == null) {
            return res;
        }
        KeyBindingsData data2 = (KeyBindingsData)device2;
        Iterator<Bind> iter2 = data2.getIterator();
        while (iter2.hasNext()) {
            Bind bind = iter2.next();
            String actionName = bind.action.getActionname();
            String codeName = bind.key != null ? bind.key.getKeyname() : (bind.axe != null ? bind.axe.getAxename() : "");
            RemapControlsTable.WrapTextsAndKeycode item = new RemapControlsTable.WrapTextsAndKeycode();
            item.texts = new ContainerTextTitleTextValue(actionName, codeName);
            item.bind = bind;
            res.add(item);
        }
        data2.freeIterator();
        this.defaulted_bindings[num_device] = res;
        return ret;
    }

    private ArrayList<RemapControlsTable.WrapTextsAndKeycode> clone(ArrayList<RemapControlsTable.WrapTextsAndKeycode> data) {
        ArrayList<RemapControlsTable.WrapTextsAndKeycode> ndata = new ArrayList<RemapControlsTable.WrapTextsAndKeycode>();
        Iterator<RemapControlsTable.WrapTextsAndKeycode> iter = data.iterator();
        while (iter.hasNext()) {
            ndata.add(iter.next().clone());
        }
        return ndata;
    }

    void updateNewBindings(int num_device) {
        if (null == this.current_bindings || null == this.current_bindings[num_device]) {
            return;
        }
        Object device = this.m_inputdevices.get(num_device);
        KeyBindingsData data = (KeyBindingsData)device;
        ArrayList current_binds = this.current_bindings[num_device];
        for (RemapControlsTable.WrapTextsAndKeycode wrap : current_binds) {
            data.updateBind(wrap.bind);
        }
    }

    @Override
    public void OnOk(long menu, MENUsimplebutton_field button) {
        int sz = this.m_inputdevices.size();
        for (int i = 0; i < sz; ++i) {
            this.updateNewBindings(i);
        }
        this.param_values.onOk();
        JavaEvents.SendEvent(15, 0, this.param_values);
        JavaEvents.SendEvent(1, 0, this);
        this.exitDialog();
    }

    @Override
    public void OnApply(long menu, MENUsimplebutton_field button) {
        int sz = this.m_inputdevices.size();
        for (int i = 0; i < sz; ++i) {
            this.updateNewBindings(i);
        }
        this.param_values.onOk();
        JavaEvents.SendEvent(15, 0, this.param_values);
        JavaEvents.SendEvent(1, 0, this);
    }

    @Override
    public void OnDefault(long menu, MENUsimplebutton_field button) {
        for (int i = 0; i < this.defaulted_bindings.length; ++i) {
            if (null == this.defaulted_bindings[i]) continue;
            this.current_bindings[i] = this.clone(this.defaulted_bindings[i]);
        }
        this.param_values.onDefault();
        int cur_device = this.currentDevice.getValue();
        this.remap_table.fillTable(this.formDeviceBindingsData(cur_device), cur_device);
    }

    @Override
    public void valueChanged() {
        this.remap_table.leaveRemaping();
        int cur_device = this.currentDevice.getValue();
        this.remap_table.fillTable(this.formDeviceBindingsData(cur_device), cur_device);
        for (int i = 0; i < this.table_settings.length; ++i) {
            if (i == cur_device) continue;
            this.table_settings[i].hideTable();
        }
        this.table_settings[cur_device].showTable();
    }

    @Override
    public boolean areValuesChanged() {
        if (super.areValuesChanged()) {
            return true;
        }
        for (int i = 0; i < this.m_inputdevices.size(); ++i) {
            if (!this.keybindingsChanged(i)) continue;
            return true;
        }
        return false;
    }

    private boolean keybindingsChanged(int num_device) {
        return false;
    }
}

