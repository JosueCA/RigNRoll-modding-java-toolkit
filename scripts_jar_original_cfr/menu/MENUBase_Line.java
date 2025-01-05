/*
 * Decompiled with CFR 0.151.
 */
package menu;

import menu.menues;

public class MENUBase_Line {
    int[] base_line_active = null;
    int[] base_line_passive = null;
    int[] base_line_pressed = null;
    int num_states = 0;

    public MENUBase_Line(long field) {
        int num_states;
        int n = num_states = field != 0L ? menues.GetNumStates(field) : 0;
        if (num_states != 0) {
            this.base_line_active = new int[num_states];
            this.base_line_passive = new int[num_states];
            this.base_line_pressed = new int[num_states];
            for (int i = 0; i < num_states; ++i) {
                this.base_line_active[i] = menues.GetBaseLine(field, true, i);
                this.base_line_passive[i] = menues.GetBaseLine(field, false, i);
                this.base_line_pressed[i] = menues.GetBaseLinePressed(field, i);
            }
        }
    }

    public void MoveBaseLine(long field, int moveTo) {
        int num_states;
        int n = num_states = field != 0L ? menues.GetNumStates(field) : 0;
        if (this.base_line_active != null && this.base_line_passive != null && this.base_line_pressed != null) {
            for (int i = 0; i < Math.min(num_states, this.base_line_active.length); ++i) {
                menues.SetBaseLine(field, this.base_line_active[i] + moveTo, true, i);
                menues.SetBaseLine(field, this.base_line_passive[i] + moveTo, false, i);
                menues.SetBaseLinePressed(field, this.base_line_pressed[i] + moveTo, i);
            }
            menues.UpdateMenuField(menues.ConvertMenuFields(field));
        }
    }

    public int GetMinBaseLine() {
        if (this.base_line_active != null && this.base_line_passive != null && this.base_line_pressed != null) {
            int min = 1000000000;
            for (int i = 0; i < this.base_line_active.length; ++i) {
                min = Math.min(this.base_line_active[i], min);
                min = Math.min(this.base_line_passive[i], min);
                min = Math.min(this.base_line_pressed[i], min);
            }
            return min;
        }
        return 0;
    }
}

