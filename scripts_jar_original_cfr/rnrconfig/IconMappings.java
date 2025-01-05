/*
 * Decompiled with CFR 0.151.
 */
package rnrconfig;

import menu.JavaEvents;
import menu.menues;

public class IconMappings {
    private static final String PERSON_ICONS_FILE = "people_icons.xml";
    private static final String RACE_SMALL_LOGOS_FILE = "race_logos_small.xml";
    private static final String VEHICLE_ICONS_FILE = "vehicle_icons.xml";
    private static final String RACE_LOGOS_FILE = "race_logos.xml";
    private static final String REMAP_METH = "remap";
    private static final int MAPPING_LAYER = 0;
    private texcoords data = null;
    private picturematerialParams cmparam = null;
    private static IconMappings instance = null;

    private static IconMappings gInstance() {
        if (null == instance) {
            instance = new IconMappings();
        }
        return instance;
    }

    public static void remapPersonIcon(String id, long control) {
        texcoords data = new texcoords(PERSON_ICONS_FILE, id);
        picturematerialParams cmparam = new picturematerialParams(PERSON_ICONS_FILE);
        JavaEvents.SendEvent(34, 0, cmparam);
        JavaEvents.SendEvent(34, 1, data);
        IconMappings.gInstance().data = data;
        IconMappings.gInstance().cmparam = cmparam;
        menues.CallMappingModifications(control, IconMappings.gInstance(), REMAP_METH);
        IconMappings.gInstance().data = null;
        IconMappings.gInstance().cmparam = null;
    }

    public static void remapRaceLogos(String id, long control) {
        texcoords data = new texcoords(RACE_LOGOS_FILE, id);
        picturematerialParams cmparam = new picturematerialParams(RACE_LOGOS_FILE);
        JavaEvents.SendEvent(34, 0, cmparam);
        JavaEvents.SendEvent(34, 1, data);
        IconMappings.gInstance().data = data;
        IconMappings.gInstance().cmparam = cmparam;
        menues.CallMappingModifications(control, IconMappings.gInstance(), REMAP_METH);
        IconMappings.gInstance().data = null;
        IconMappings.gInstance().cmparam = null;
    }

    public static void remapSmallRaceLogos(String id, long control) {
        texcoords data = new texcoords(RACE_SMALL_LOGOS_FILE, id);
        picturematerialParams cmparam = new picturematerialParams(RACE_SMALL_LOGOS_FILE);
        JavaEvents.SendEvent(34, 0, cmparam);
        JavaEvents.SendEvent(34, 1, data);
        IconMappings.gInstance().data = data;
        IconMappings.gInstance().cmparam = cmparam;
        menues.CallMappingModifications(control, IconMappings.gInstance(), REMAP_METH);
        IconMappings.gInstance().data = null;
        IconMappings.gInstance().cmparam = null;
    }

    public static void remapVehicleIcon(String id, long control) {
        texcoords data = new texcoords(VEHICLE_ICONS_FILE, id);
        picturematerialParams cmparam = new picturematerialParams(VEHICLE_ICONS_FILE);
        JavaEvents.SendEvent(34, 0, cmparam);
        JavaEvents.SendEvent(34, 1, data);
        IconMappings.gInstance().data = data;
        IconMappings.gInstance().cmparam = cmparam;
        menues.CallMappingModifications(control, IconMappings.gInstance(), REMAP_METH);
        IconMappings.gInstance().data = null;
        IconMappings.gInstance().cmparam = null;
    }

    public void remap(int sizex, int sizey, menues.CMaterial_whithmapping[] stuff) {
        for (menues.CMaterial_whithmapping tex : stuff) {
            if (tex.tex.size() == 0 || tex.usepatch) continue;
            for (menues.ctexcoord_multylayer layer : tex.tex) {
                if (layer.index != 0) continue;
                layer.t0x = this.data.x / this.cmparam.resx;
                layer.t0y = this.data.y / this.cmparam.resy;
                layer.t1x = (this.data.x + this.data.sx) / this.cmparam.resx;
                layer.t1y = this.data.y / this.cmparam.resy;
                layer.t2x = (this.data.x + this.data.sx) / this.cmparam.resx;
                layer.t2y = (this.data.y + this.data.sy) / this.cmparam.resy;
                layer.t3x = this.data.x / this.cmparam.resx;
                layer.t3y = (this.data.y + this.data.sy) / this.cmparam.resy;
            }
        }
    }

    static class picturematerialParams {
        String file;
        float resx;
        float resy;

        picturematerialParams(String file) {
            this.file = file;
        }
    }

    static class texcoords {
        String file;
        String id;
        float x;
        float y;
        float sx;
        float sy;

        texcoords(String file, String id) {
            this.file = file;
            this.id = id;
        }
    }
}

