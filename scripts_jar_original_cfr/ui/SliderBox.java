/*
 * Decompiled with CFR 0.151.
 */
package ui;

import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.NumberFormat;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import ui.SliderVariableDouble;
import ui.ValueChanged;

public class SliderBox
extends JPanel
implements ValueChanged {
    static final long serialVersionUID = 1L;
    private static final String str_descr_min = "MIN";
    private static final String str_descr_max = "MAX";
    private static final String str_descr_value = "value ";
    private ValueChanged callback = null;
    private JTextField descr_min;
    private JTextField descr_max;
    private JTextField descr_value;
    private JTextField txt_min;
    private JTextField txt_max;
    private JTextField txt_value;
    private SliderVariableDouble slider;
    protected double min_value;
    protected double max_value;
    protected int steps;

    public SliderBox(double minvalue, double maxvalue, int steps, double currentvalue, String name) {
        this.min_value = minvalue;
        this.max_value = maxvalue;
        this.steps = steps;
        this.setBorder(BorderFactory.createBevelBorder(0));
        this.setLayout(new GridLayout(4, 1));
        JPanel minpanel = new JPanel();
        minpanel.setLayout(new GridLayout(1, 2));
        JPanel maxpanel = new JPanel();
        maxpanel.setLayout(new GridLayout(1, 2));
        JPanel valuepanel = new JPanel();
        valuepanel.setLayout(new GridLayout(1, 2));
        this.descr_min = new JTextField(str_descr_min);
        this.descr_min.setEditable(false);
        this.txt_min = new JTextField(this.format(minvalue));
        this.txt_min.setEditable(true);
        this.descr_max = new JTextField(str_descr_max);
        this.descr_max.setEditable(false);
        this.txt_max = new JTextField(this.format(maxvalue));
        this.txt_max.setEditable(true);
        this.descr_value = new JTextField(str_descr_value + name);
        this.descr_value.setEditable(false);
        this.txt_value = new JTextField(this.format(currentvalue));
        this.txt_value.setEditable(true);
        this.slider = new SliderVariableDouble(this.min_value, this.max_value, steps, currentvalue);
        this.slider.assignChangeListener(this);
        FocusAdapter lisner = new FocusAdapter(){

            public void focusLost(FocusEvent e) {
                JTextField field = (JTextField)e.getSource();
                SliderBox.this.min_value = Double.parseDouble(field.getText());
                if (SliderBox.this.min_value > SliderBox.this.max_value) {
                    SliderBox.this.min_value = SliderBox.this.max_value;
                    field.setText(SliderBox.this.format(SliderBox.this.min_value));
                }
                SliderBox.this.updateSliderLimits();
            }
        };
        this.txt_min.addFocusListener(lisner);
        lisner = new FocusAdapter(){

            public void focusLost(FocusEvent e) {
                JTextField field = (JTextField)e.getSource();
                SliderBox.this.max_value = Double.parseDouble(field.getText());
                if (SliderBox.this.min_value > SliderBox.this.max_value) {
                    SliderBox.this.max_value = SliderBox.this.min_value;
                    field.setText(SliderBox.this.format(SliderBox.this.max_value));
                }
                SliderBox.this.updateSliderLimits();
            }
        };
        this.txt_max.addFocusListener(lisner);
        lisner = new FocusAdapter(){

            public void focusLost(FocusEvent e) {
                JTextField field = (JTextField)e.getSource();
                double currvalue = Double.parseDouble(field.getText());
                SliderBox.this.setSliderValue(currvalue);
            }
        };
        this.txt_value.addFocusListener(lisner);
        minpanel.add(this.descr_min);
        minpanel.add(this.txt_min);
        maxpanel.add(this.descr_max);
        maxpanel.add(this.txt_max);
        valuepanel.add(this.descr_value);
        valuepanel.add(this.txt_value);
        this.add(minpanel);
        this.add(maxpanel);
        this.add(this.slider);
        this.add(valuepanel);
    }

    protected void updateSliderLimits() {
        this.slider.changeLimits(this.min_value, this.max_value, this.steps);
    }

    protected String format(double value) {
        NumberFormat n = NumberFormat.getInstance();
        n.setMaximumFractionDigits(3);
        return n.format(value);
    }

    public void recieveChange() {
        this.txt_value.setText(this.format(this.slider.getVariableValue()));
        if (this.callback != null) {
            this.callback.recieveChange();
        }
    }

    public void setSliderValue(double value) {
        this.slider.setVariableValue(value);
        this.recieveChange();
    }

    public double getSliderValue() {
        return this.slider.getVariableValue();
    }

    public void assignChangeListener(ValueChanged callback) {
        this.callback = callback;
    }
}

