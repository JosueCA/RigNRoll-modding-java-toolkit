/*
 * Decompiled with CFR 0.151.
 */
package ui;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import ui.ValueChanged;

public class SliderVariableDouble
extends JSlider
implements ChangeListener {
    static final long serialVersionUID = 1L;
    private double min_value = 0.0;
    private double max_value = 0.0;
    private double cur_value = 0.0;
    private int max_scale_value = 0;
    private ValueChanged callback = null;

    public SliderVariableDouble(double min_value, double max_value, int steps, double initialvalue) {
        super(0, 0, 1, 0);
        this.cur_value = initialvalue;
        this.updateLimits(min_value, max_value, steps);
        this.setMaximum(this.max_scale_value);
        this.setValue(this.getValueInt(this.cur_value));
        this.addChangeListener(this);
    }

    public SliderVariableDouble(double min_value, double max_value, int steps) {
        super(0, 0, 1, 0);
        this.cur_value = min_value;
        this.updateLimits(min_value, max_value, steps);
        this.setMaximum(this.max_scale_value);
        this.setValue(this.getValueInt(this.cur_value));
        this.addChangeListener(this);
    }

    private void updateLimits(double min_value, double max_value, int steps) {
        this.min_value = min_value;
        this.max_value = max_value;
        this.max_scale_value = steps;
    }

    private int getValueInt(double value) {
        if (value < this.min_value) {
            return 0;
        }
        if (value > this.max_value) {
            return this.max_scale_value;
        }
        return (int)((double)this.max_scale_value * (value - this.min_value) / (this.max_value - this.min_value));
    }

    private double getValueDouble(int value) {
        if (value == 0) {
            return this.min_value;
        }
        if (value == this.max_scale_value) {
            return this.max_value;
        }
        return this.min_value + (this.max_value - this.min_value) * (double)value / (double)this.max_scale_value;
    }

    public void stateChanged(ChangeEvent e) {
        double last_cur_value = this.cur_value;
        this.cur_value = this.getValueDouble(this.getValue());
        if (last_cur_value != this.cur_value && this.callback != null) {
            this.callback.recieveChange();
        }
    }

    public void assignChangeListener(ValueChanged cb) {
        this.callback = cb;
    }

    public double getVariableValue() {
        return this.cur_value;
    }

    public void setVariableValue(double value) {
        this.cur_value = value;
        this.setValue(this.getValueInt(this.cur_value));
    }

    public void changeLimits(double min_value, double max_value, int steps) {
        this.updateLimits(min_value, max_value, steps);
        this.setMaximum(this.max_scale_value);
        this.setValue(this.getValueInt(this.cur_value));
    }
}

