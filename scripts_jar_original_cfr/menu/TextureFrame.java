/*
 * Decompiled with CFR 0.151.
 */
package menu;

import menu.MENUText_field;
import menu.menues;

public class TextureFrame {
    float m_maxx;
    float m_maxy;
    int x;
    int y;
    int texsx;
    int texsy;
    String name;

    public void Init(int maxx, int maxy) {
        this.m_maxx = maxx;
        this.m_maxy = maxy;
    }

    public void Init(int maxx, int maxy, int _texsx, int _texsy) {
        this.m_maxx = maxx;
        this.m_maxy = maxy;
        this.texsx = _texsx;
        this.texsy = _texsy;
    }

    public void ApplyToPicture(MENUText_field picture, int _x, int _y) {
        this.x = _x;
        this.y = _y;
        this.name = null;
        menues.CallMappingModifications(picture.nativePointer, this, "Mapping");
    }

    public void ApplyToPatch(long pointer, int _x, int _y, String _name) {
        this.x = _x;
        this.y = _y;
        this.name = _name;
        menues.CallMappingModifications(pointer, this, "Mapping");
    }

    void Mapping(int sizex, int sizey, menues.CMaterial_whithmapping[] stuff) {
        for (int i = 0; i < stuff.length; ++i) {
            if (this.name == null ? stuff[i].usepatch : !stuff[i].usepatch || !this.name.equals(stuff[i]._patch.tip)) continue;
            menues.ctexcoord_multylayer layer = (menues.ctexcoord_multylayer)stuff[i].tex.get(0);
            if (this.texsx != 0 && this.texsy != 0) {
                layer.t0x = (float)this.x / this.m_maxx + 2.0f / (float)this.texsx;
                layer.t0y = (float)this.y / this.m_maxy + 2.0f / (float)this.texsy;
                layer.t1x = (float)(this.x + 1) / this.m_maxx - 2.0f / (float)this.texsx;
                layer.t1y = (float)this.y / this.m_maxy + 2.0f / (float)this.texsy;
                layer.t2x = (float)(this.x + 1) / this.m_maxx - 2.0f / (float)this.texsx;
                layer.t2y = (float)(this.y + 1) / this.m_maxy - 2.0f / (float)this.texsy;
                layer.t3x = (float)this.x / this.m_maxx + 2.0f / (float)this.texsx;
                layer.t3y = (float)(this.y + 1) / this.m_maxy - 2.0f / (float)this.texsy;
                continue;
            }
            layer.t0x = (float)this.x / this.m_maxx;
            layer.t0y = (float)this.y / this.m_maxy;
            layer.t1x = (float)(this.x + 1) / this.m_maxx;
            layer.t1y = (float)this.y / this.m_maxy;
            layer.t2x = (float)(this.x + 1) / this.m_maxx;
            layer.t2y = (float)(this.y + 1) / this.m_maxy;
            layer.t3x = (float)this.x / this.m_maxx;
            layer.t3y = (float)(this.y + 1) / this.m_maxy;
        }
    }
}

