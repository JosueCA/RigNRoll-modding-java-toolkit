/*
 * Decompiled with CFR 0.151.
 */
package ui;

import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RadioChanged
implements ChangeListener {
    private boolean selected = false;
    protected boolean changed = false;

    public RadioChanged(JRadioButton button) {
        this.selected = button.isSelected();
    }

    public void stateChanged(ChangeEvent e) {
        JRadioButton button = (JRadioButton)e.getSource();
        boolean newstate = button.isSelected();
        this.changed = false;
        if (newstate != this.selected) {
            this.selected = newstate;
            this.changed = true;
        }
    }
}

