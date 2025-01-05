/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.Common;
import menu.RNRMap;
import menu.SelectCb;
import menu.menues;
import rnrcore.Log;

public class RNRMapWrapper {
    private RNRMap mapa;
    private String zoom_control;
    private String shift_control;
    private Common common;
    private String[] invisibles;

    public RNRMapWrapper(long _menu, String namecontrol, String controls_shift, String controls_zoom, SelectCb callback) {
        this.common = new Common(_menu);
        this.zoom_control = controls_zoom;
        this.shift_control = controls_shift;
        long mapfield = menues.FindFieldInMenu(_menu, namecontrol);
        if (0L == mapfield) {
            Log.menu("ERRORR. RNRMapWrapper. No rnr map named " + namecontrol);
            return;
        }
        this.mapa = menues.ConvertRNRMAPFields(mapfield);
        this.mapa.AttachCallback(this.common, callback);
    }

    public RNRMapWrapper(long _menu, String namecontrol, String controls_shift, String controls_zoom, String[] invisibles, SelectCb callback) {
        this.common = new Common(_menu);
        this.zoom_control = controls_zoom;
        this.shift_control = controls_shift;
        this.invisibles = invisibles;
        long mapfield = menues.FindFieldInMenu(_menu, namecontrol);
        if (0L == mapfield) {
            Log.menu("ERRORR. RNRMapWrapper. No rnr map named " + namecontrol);
            return;
        }
        this.mapa = menues.ConvertRNRMAPFields(mapfield);
        this.mapa.AttachCallback(this.common, callback);
    }

    public void afterInit() {
        this.mapa.AttachStandardControls(this.common, this.shift_control, this.zoom_control);
        if (this.invisibles != null) {
            for (String str : this.invisibles) {
                long field = menues.FindFieldInMenu(this.common.s_menu, str);
                menues.SetIgnoreEvents(field, true);
                menues.SetIgnoreEvents(field, true);
            }
        }
    }

    public void workWith(int type) {
        this.mapa.SetClickableGroup(type, true);
    }

    public void doNotWorkWith(int type) {
        this.mapa.SetClickableGroup(type, false);
    }

    public int addObject(int type, float x, float y, String name, Object sender) {
        return this.mapa.AddObject(type, sender, x, y, name, name);
    }

    public int addDestination(int type, int startID, int endID, String tips, Object sender) {
        return this.mapa.AddOrder(type, sender, startID, endID, tips);
    }

    public RNRMap.Priority createPriority() {
        return this.mapa.new RNRMap.Priority();
    }

    public void setPriority(int icon, RNRMap.Priority priority) {
        this.mapa.SetMapObjectTextPriority(icon, priority);
        this.mapa.SetMapObjectPicturePriority(icon, priority);
    }

    public void setPriority(int icon, RNRMap.Priority p_priority, RNRMap.Priority t_priority) {
        this.mapa.SetMapObjectTextPriority(icon, p_priority);
        this.mapa.SetMapObjectPicturePriority(icon, t_priority);
    }

    public void ClearData() {
        this.mapa.ClearObjects();
    }

    public void selectHighlight(int id, boolean state) {
        this.mapa.HighlightMapObject(id, state);
    }

    public void selectSelect(int id, boolean state) {
        this.mapa.SelectMapObject(id, state);
    }

    public void syncDirections(int id1, int id2) {
        this.mapa.makeDirectionsAsOne(id1, id2);
    }

    public void setActiveObject(int id, boolean value) {
        this.mapa.setActiveMapObject(id, value);
    }

    public void setPressedObject(int id, boolean value) {
        this.mapa.setPressedMapObject(id, value);
    }

    public void addListenerOnActive(Object listener, String methodname) {
        menues.SetScriptOnControl(this.common.s_menu, this.mapa, listener, methodname, 21L);
    }

    public void addListenerOnPressed(Object listener, String methodname) {
        menues.SetScriptOnControl(this.common.s_menu, this.mapa, listener, methodname, 20L);
    }
}

