/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import java.util.Vector;
import menuscript.mainmenu.VehicleDLCTextureInfo;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface RestylingMenu_ExteriorTabImplementation {
    public int getTextureId();

    public void onLeftPressed();

    public void onRightPressed();

    public void selectDLCTexture(int var1);

    public Vector<VehicleDLCTextureInfo> getUserTextureInfoVec();
}

