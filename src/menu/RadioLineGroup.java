/*
 * Decompiled with CFR 0.151.
 */
package menu;

import java.util.Iterator;
import menu.IRadioChangeListener;
import menu.MENUbutton_field;
import menu.RadioGroup;
import menu.menues;

public class RadioLineGroup
extends RadioGroup {
    public RadioLineGroup(long _menu, MENUbutton_field[] buttons) {
        super(_menu, buttons);
    }

    public void onSelect(long _menu, MENUbutton_field button) {
        if (this.undersession || this.f_deselecting) {
            return;
        }
        this.beginsession();
        boolean hit = false;
        for (int i = 0; i < this.buttons.length; ++i) {
            menues.SetFieldState(this.buttons[i].nativePointer, hit ? 0 : 1);
            if (this.buttons[i].nativePointer != button.nativePointer) continue;
            this.focus_member = i;
            hit = true;
        }
        this.endsession();
        Iterator iter = this.listeners.iterator();
        while (iter.hasNext()) {
            ((IRadioChangeListener)iter.next()).controlSelected(button, this.focus_member);
        }
    }
}

