/*
 * Decompiled with CFR 0.151.
 */
package menu;

import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.RadioLineGroup;
import menu.SmartArrowedGroup;
import menu.menues;
import menuscript.parametrs.IIntegerValueChanger;
import rnrcore.Log;

public class SliderGroupRadioButtons
extends SmartArrowedGroup
implements IIntegerValueChanger {
    private static final String SLIDERBUTTON = "LampGreen";
    private static final String LAMPS_INDICATOR = "LAMPS - ";
    private static final String SLIDER_ELEMENTS_SUFFICS = " Elements";
    private int indicator_num = 0;
    private int indicator_step = 0;
    private String indicatorsParent = "";
    private int value;
    private int min_value;
    private int max_value;
    private RadioLineGroup radioButtons = null;
    private MENUbutton_field[] indicators = null;
    private boolean params_were_read = false;
    private boolean deselected = false;

    public SliderGroupRadioButtons(String xml_file, String xml_node_group, String title, int minvalue, int maxvalue, int curvalue, boolean _bBlind) {
        super(xml_file, xml_node_group, title, _bBlind);
        this.min_value = minvalue;
        this.max_value = maxvalue;
        this.value = curvalue;
        if (minvalue < 0 || maxvalue < 0 || curvalue < 0) {
            Log.menu("SliderGroupRadioButtons constructor minvalue<0 || maxvalue<0 || curvalue <0. Values: " + minvalue + "\t" + maxvalue + "\t" + curvalue);
        } else if (minvalue >= maxvalue || curvalue > maxvalue || curvalue < minvalue) {
            Log.menu("SliderGroupRadioButtons constructor minvalue>=maxvalue || curvalue>maxvalue || curvalue<minvalue. Values: " + minvalue + "\t" + maxvalue + "\t" + curvalue);
        }
    }

    public void load(long _menu) {
        super.load(_menu);
        for (int i = 0; i < this.controls.length; ++i) {
            if (this.readIndicatorParams(_menu, this.controls[i])) continue;
        }
        this.copyIndicators(_menu);
        this.initRadioGroup(_menu);
    }

    public void init() {
        super.init();
        this.setupValue();
    }

    private String getIndicatorsTopName() {
        if (null == this.controls) {
            Log.menu("Slider Group. Trying to access not created controls field.");
            return "";
        }
        if (0 == this.controls.length) {
            Log.menu("Slider Group. controls field has 0 length.");
            return "";
        }
        return this.indicatorsParent;
    }

    private boolean readIndicatorParams(long _menu, long field) {
        String name = menues.GetFieldName(field);
        if (name.startsWith(LAMPS_INDICATOR)) {
            this.params_were_read = true;
            String suffix = name.substring(LAMPS_INDICATOR.length());
            String[] values = suffix.split(" ");
            if (values.length != 2) {
                Log.menu("Slider Group. Slider " + this.xml_node_group + " has wrong named " + LAMPS_INDICATOR);
            } else {
                this.indicator_num = Integer.decode(values[0]);
                this.indicator_step = Integer.decode(values[1]);
                this.indicators = new MENUbutton_field[this.indicator_num];
            }
            this.indicatorsParent = name + "SmartGroupTop" + NUMELEM++;
            menues.SetFieldName(_menu, field, this.indicatorsParent);
            return true;
        }
        return false;
    }

    private void copyIndicators(long _menu) {
        if (!this.params_were_read) {
            Log.menu("Slider Group. Slider initialisation has wrong order");
            return;
        }
        int i = 0;
        while (i < this.indicator_num) {
            long ctrl = menues.InitXmlControl(_menu, this.xml_file, this.xml_node_group + SLIDER_ELEMENTS_SUFFICS, SLIDERBUTTON);
            MENUbutton_field button = menues.ConvertButton(ctrl);
            button.pox += i * this.indicator_step;
            button.parentName = this.getIndicatorsTopName();
            button.userid = i++;
            menues.UpdateField(button);
            this.indicators[i] = button;
            menues.SetScriptOnControl(_menu, button, this, "OnButton", 2L);
        }
    }

    public void OnButton(long _menu, MENUbutton_field button) {
        this.radioButtons.select(button.userid);
    }

    private void initRadioGroup(long _menu) {
        if (null == this.indicators) {
            Log.menu("Slider Group. Cannot initialize radio group.");
            return;
        }
        this.radioButtons = new RadioLineGroup(_menu, this.indicators);
        this.radioButtons.addListener(this);
    }

    public void OnLeft(long _menu, MENUsimplebutton_field button) {
        int selected = this.radioButtons.getSelected();
        if (selected == 0) {
            if (this.nullValueExists()) {
                this.deselected = true;
                this.radioButtons.deselectall();
                this.updateValue();
            }
            return;
        }
        this.radioButtons.select(selected - 1);
        this.updateValue();
    }

    public void OnRight(long _menu, MENUsimplebutton_field button) {
        if (this.deselected) {
            this.deselected = false;
            this.radioButtons.select(0);
            this.updateValue();
            return;
        }
        int selected = this.radioButtons.getSelected();
        if (selected >= this.indicator_num - 1) {
            return;
        }
        this.radioButtons.select(selected + 1);
        this.updateValue();
    }

    public void controlSelected(MENUbutton_field button, int cs) {
        this.deselected = false;
        this.updateValue();
    }

    private boolean nullValueExists() {
        return this.min_value == 0;
    }

    private void updateValue() {
        if (this.deselected) {
            this.value = this.min_value;
            this.callValueChanged();
            this.debug_text();
            return;
        }
        int selected = this.radioButtons.getSelected();
        if (selected == this.indicator_num - 1) {
            this.value = this.max_value;
            this.debug_text();
            this.callValueChanged();
            return;
        }
        if (this.nullValueExists()) {
            ++selected;
        }
        int totalstates = this.nullValueExists() ? this.indicator_num : this.indicator_num - 1;
        this.value = this.min_value + (int)((double)(this.max_value - this.min_value) / (double)totalstates * (double)selected);
        this.callValueChanged();
        this.debug_text();
    }

    private void setupValue() {
        int totalstates = this.nullValueExists() ? this.indicator_num : this.indicator_num - 1;
        int selected = (int)((double)(this.value - this.min_value) / ((double)(this.max_value - this.min_value) / (double)totalstates));
        int n = selected = this.nullValueExists() ? --selected : selected;
        if (-1 != selected) {
            this.radioButtons.select(selected);
            this.updateValue();
        } else {
            this.radioButtons.deselectall();
            this.deselected = true;
        }
    }

    private void debug_text() {
    }

    public void hide() {
        super.hide();
        for (int i = 0; i < this.indicators.length; ++i) {
            menues.SetBlindess(this.indicators[i].nativePointer, true);
            menues.SetIgnoreEvents(this.indicators[i].nativePointer, true);
            menues.SetShowField(this.indicators[i].nativePointer, false);
        }
    }

    public void show() {
        super.show();
        for (int i = 0; i < this.indicators.length; ++i) {
            menues.SetBlindess(this.indicators[i].nativePointer, false);
            menues.SetIgnoreEvents(this.indicators[i].nativePointer, false);
            menues.SetShowField(this.indicators[i].nativePointer, true);
        }
    }

    public void setValue(int value) {
        this.value = value;
        this.setupValue();
    }

    public int getValue() {
        return this.value;
    }

    public int changeValue() {
        return this.getValue();
    }

    public void reciveValue(int value) {
        this.setValue(value);
    }
}

