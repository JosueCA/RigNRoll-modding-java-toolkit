/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import java.util.Vector;
import menu.IValueChanged;
import menu.JavaEvents;
import menu.ListOfAlternatives;
import menu.MENUsimplebutton_field;
import menu.SliderGroupRadioButtons;
import menu.TableOfElements;
import menuscript.mainmenu.CA;
import menuscript.mainmenu.Panel;
import menuscript.mainmenu.PanelDialog;
import menuscript.parametrs.IParametr;
import rnrcore.Modifier;
import rnrcore.loc;

public class SettingsAudio
extends PanelDialog
implements IValueChanged {
    static String TABLE_DEVICE = "TABLEGROUP - SETTINGS - AUDIO - 1 60";
    static String TABLE_SETTINGS = "TABLEGROUP - SETTINGS - AUDIO - 10 60";
    private static final String LISTOFALTERANTIVESGROUP = "Tablegroup - ELEMENTS - List Special";
    private static final String[] SETTINGSTITLES = new String[]{"SETTINGS SOUND QUALITY", "SETTINGS EFFECTS VOLUME", "SETTINGS VEHICLE VOLUME", "SETTINGS SPEECH VOLUME", "SETTINGS MUSIC VOLUME"};
    private static final TreeSet<String> orderedSettingsTitles = new TreeSet();
    private static final String TITLE;
    private static final String DEVICETITLE = "CURRENT AUDIO DEVICE";
    private static final int NUM_SETTINGS = 5;
    private TableOfElements table_device = null;
    private TableOfElements[] table_settings = null;
    private ListOfAlternatives devices = null;
    private SliderGroupRadioButtons[][] settings = null;
    Vector<String> devises_str_ascii = new Vector();
    Vector<String> devises_str_unicode = new Vector();
    private String currentDeviceName = null;
    private int current_device = 0;
    private boolean bSilent = false;

    public SettingsAudio(long _menu, long[] controls, long window, long exitButton, long defaultButton, long okButton, long applyButton, Panel parent) {
        super(_menu, window, controls, exitButton, defaultButton, okButton, applyButton, parent);
        JavaEvents.SendEvent(18, -1, this);
        String[] str_devises = this._getDevicesASCII();
        this.table_settings = new TableOfElements[str_devises.length];
        this.settings = new SliderGroupRadioButtons[str_devises.length][5];
        for (int dev = 0; dev < str_devises.length; ++dev) {
            int i;
            this.table_settings[dev] = new TableOfElements(_menu, TABLE_SETTINGS);
            for (i = 0; i < 5; ++i) {
                this.settings[dev][i] = CA.createSliderRadioButtons(loc.getMENUString(SETTINGSTITLES[i]), 0, 100, 0, true);
            }
            for (i = 0; i < 5; ++i) {
                this.settings[dev][i].load(_menu);
            }
            for (i = 0; i < 5; ++i) {
                this.table_settings[dev].insert(this.settings[dev][i]);
            }
            for (i = 0; i < 5; ++i) {
                this.param_values.addParametr(SETTINGSTITLES[i] + str_devises[dev], 0, 0, this.settings[dev][i]);
            }
        }
        this.table_device = new TableOfElements(_menu, TABLE_DEVICE);
        this.devices = new ListOfAlternatives(parent.menu.XML_FILE, LISTOFALTERANTIVESGROUP, TITLE, this._getDevicesUnicode(), false);
        this.devices.load(_menu);
        this.table_device.insert(this.devices);
        this.param_values.addParametr(DEVICETITLE, this.current_device, this.current_device, this.devices);
        this.devices.addListener(this);
        JavaEvents.SendEvent(18, 0, this.param_values);
    }

    public void exitMenu() {
        for (TableOfElements table_setting : this.table_settings) {
            table_setting.DeInit();
        }
        this.table_device.DeInit();
        super.exitMenu();
    }

    public void afterInit() {
        for (TableOfElements table_setting : this.table_settings) {
            table_setting.initTable();
        }
        this.table_device.initTable();
        super.afterInit();
        this.valueChanged();
        JavaEvents.SendEvent(19, 2, this);
        this.param_values.onUpdate();
        for (int dev = 0; dev < this.devises_str_ascii.size(); ++dev) {
            for (int i = 0; i < 5; ++i) {
                this.settings[dev][i].addListener(new ListenerParamsChanger());
            }
        }
    }

    public void OnExit(long _menu, MENUsimplebutton_field button) {
        JavaEvents.SendEvent(19, 4, this);
        this.readParamValues();
        super.OnExit(_menu, button);
    }

    public void OnOk(long _menu, MENUsimplebutton_field button) {
        super.OnOk(_menu, button);
        JavaEvents.SendEvent(19, 0, this.param_values);
        JavaEvents.SendEvent(19, 1, this);
        JavaEvents.SendEvent(19, 2, this);
    }

    public void OnApply(long _menu, MENUsimplebutton_field button) {
        super.OnApply(_menu, button);
        JavaEvents.SendEvent(19, 0, this.param_values);
        JavaEvents.SendEvent(19, 1, this);
        JavaEvents.SendEvent(19, 2, this);
    }

    private String[] _getDevicesASCII() {
        String[] res = new String[this.devises_str_ascii.size()];
        Iterator<String> iter = this.devises_str_ascii.iterator();
        int counter = 0;
        while (iter.hasNext()) {
            res[counter++] = iter.next();
        }
        return res;
    }

    private String[] _getDevicesUnicode() {
        String[] res = new String[this.devises_str_unicode.size()];
        Iterator<String> iter = this.devises_str_unicode.iterator();
        int counter = 0;
        while (iter.hasNext()) {
            res[counter++] = iter.next();
        }
        return res;
    }

    public void OnDefault(long _menu, MENUsimplebutton_field button) {
        this.param_values.visitAllParameters(new Modifier<Map.Entry<String, IParametr>>(){

            // @Override
            public void modify(Map.Entry<String, IParametr> victim) {
                int firstEntrance;
                if (null != victim && !SettingsAudio.DEVICETITLE.equals(victim.getKey()) && null != victim.getValue() && 0 < (firstEntrance = victim.getKey().indexOf(SettingsAudio.this.currentDeviceName)) && orderedSettingsTitles.contains(victim.getKey().substring(0, firstEntrance))) {
                    victim.getValue().makeDefault();
                }
            }
        });
    }

    public void valueChanged() {
        this.current_device = this.devices.getValue();
        this.currentDeviceName = this.devises_str_ascii.get(this.current_device);
        for (int i = 0; i < this.table_settings.length; ++i) {
            if (i == this.current_device) continue;
            this.table_settings[i].hideTable();
        }
        this.table_settings[this.current_device].showTable();
        if (!this.bSilent) {
            JavaEvents.SendEvent(19, 1, this);
            JavaEvents.SendEvent(19, 5, this);
        }
    }

    public void readParamValues() {
        JavaEvents.SendEvent(18, 1, this);
        JavaEvents.SendEvent(18, 2, this.param_values);
        this.bSilent = true;
        this.devices.setValue(this.current_device);
        this.param_values.setIntegerValue(DEVICETITLE, this.current_device);
        this.param_values.onUpdate();
        this.valueChanged();
        this.bSilent = false;
    }

    static {
        orderedSettingsTitles.addAll(Arrays.asList(SETTINGSTITLES));
        TITLE = loc.getMENUString("common\\SETTINGS FOR:");
    }

    class ListenerParamsChanger
    implements IValueChanged {
        ListenerParamsChanger() {
        }

        public void valueChanged() {
            if (!SettingsAudio.this.bSilent) {
                SettingsAudio.this.param_values.onOk();
                JavaEvents.SendEvent(19, 0, SettingsAudio.this.param_values);
                JavaEvents.SendEvent(19, 3, this);
            }
        }
    }
}

