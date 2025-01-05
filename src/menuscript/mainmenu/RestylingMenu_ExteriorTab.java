/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import java.util.ArrayList;
import java.util.Vector;
import menu.MENUText_field;
import menu.MENU_ranger;
import menu.MENUsimplebutton_field;
import menu.menues;
import menuscript.PoPUpMenu;
import menuscript.mainmenu.RestylingMenu_ExteriorTabImplementation;
import menuscript.mainmenu.VehicleDLCTextureInfo;
import menuscript.table.Table;
import menuscript.tablewrapper.TableLine;
import menuscript.tablewrapper.TableWrapped;
import rnrcore.loc;

public class RestylingMenu_ExteriorTab {
    private static final int MIN_LINES_NUMBER = 4;
    private static final int MAX_LINES_NUMBER = 19;
    private static final int LINE_HEIGTH = 38;
    private static final String POPUP_BACK = "RestyleVEHICLE - MainColor - DLC - BACK";
    private static final String POPUP_RANGER_BACK = "RestyleVEHICLE - MainColor - DLC - BACK tableranger SCROLLER";
    private static final String POPUP_RANGER_BUTTON_DOWN = "RestyleVEHICLE - MainColor - DLC - BACK tableranger DOWN";
    private static final String POPUP_RANGER = "Tableranger - RestyleVEHICLE - MainColor - DLC";
    private static String TABLE_GROUP;
    private static String TABLE_RANGER;
    private static String TABLE_LINE;
    private static String[] TABLE_LINE_ELEMENTS;
    private static String XML_PATH;
    VehicleTexturesTableWrapped vehicleTexturesTableWrapped;
    private ExteriorMenuState exteriorMenuState;
    private static final String[] MenuTitleVariants;
    private static final String DefaultSkinTitle;
    private long MenuTitleTextBox;
    private long menuTitleLeftButtonId;
    private long menuTitleRightButtonId;
    private long chooseSkinButtonId;
    private long baseSkinButtonId;
    private long baseSkinPopUpId;
    private long baseSkinChameleonButtonId;
    private long dlcSkinButtonId;
    private long dlcSkinChameleonPictureId;
    private PoPUpMenu dlcSkinPopUp;
    private final RestylingMenu_ExteriorTabImplementation menuImplementation;

    public RestylingMenu_ExteriorTab(CalledFromMenu calledFromMenu, RestylingMenu_ExteriorTabImplementation implementation) {
        this.menuImplementation = implementation;
        switch (calledFromMenu) {
            case QUICK_RACE_MENU: {
                TABLE_GROUP = "TABLEGROUP MenuMain RestyleVEHICLE MainColor DLC - 4 38";
                TABLE_LINE = "TABLEGROUP MenuMain RestyleVEHICLE MainColor DLC";
                XML_PATH = "..\\data\\config\\menu\\menu_MAIN.xml";
                break;
            }
            case STO_MENU: {
                TABLE_GROUP = "TABLEGROUP MenuRepair RestyleVEHICLE MainColor DLC - 4 38";
                TABLE_LINE = "TABLEGROUP MenuRepair RestyleVEHICLE MainColor DLC";
                XML_PATH = "..\\data\\config\\menu\\menu_repair.xml";
            }
        }
        TABLE_RANGER = POPUP_RANGER;
        TABLE_LINE_ELEMENTS = new String[]{"MainColor_DLC_DefaultSkin", "MainColor_DLC_CustomSkin", "MainColor_DLC_Empty"};
    }

    public void initMenu(long _menu) {
        this.exteriorMenuState = ExteriorMenuState.BASE;
        this.MenuTitleTextBox = menues.FindFieldInMenu(_menu, "BodyPaintOptions TITLE");
        this.menuTitleLeftButtonId = menues.FindFieldInMenu(_menu, "BodyPaintOptions - BUTTON LEFT");
        this.menuTitleRightButtonId = menues.FindFieldInMenu(_menu, "BodyPaintOptions - BUTTON RIGHT");
        this.chooseSkinButtonId = menues.FindFieldInMenu(_menu, "MAIN - PopUP button");
        this.baseSkinButtonId = menues.FindFieldInMenu(_menu, "Restyle - MainColor");
        this.baseSkinPopUpId = menues.FindFieldInMenu(_menu, "Border - MainColor - JustForTest 8 positions");
        this.baseSkinChameleonButtonId = menues.FindFieldInMenu(_menu, "CHAMELEON Check BOX");
        if (!this.idIsCorrect(this.baseSkinChameleonButtonId)) {
            this.baseSkinChameleonButtonId = menues.FindFieldInMenu(_menu, "CHAMELEON - Check BOX");
        }
        this.dlcSkinButtonId = menues.FindFieldInMenu(_menu, "Restyle - MainColor - DLC");
        this.dlcSkinChameleonPictureId = menues.FindFieldInMenu(_menu, "CHAMELEON Check BOX - GRAY");
        if (!this.idIsCorrect(this.dlcSkinChameleonPictureId)) {
            this.dlcSkinChameleonPictureId = menues.FindFieldInMenu(_menu, "CHAMELEON - Check BOX - GRAY");
        }
        this.dlcSkinPopUp = new PoPUpMenu(_menu, XML_PATH, TABLE_GROUP, TABLE_GROUP);
        int linesNum = this.menuImplementation.getUserTextureInfoVec().size();
        linesNum = linesNum < 4 ? 4 : linesNum;
        linesNum = linesNum > 19 ? 19 : linesNum;
        this.vehicleTexturesTableWrapped = new VehicleTexturesTableWrapped(_menu, this.dlcSkinPopUp, linesNum);
        this.afterInitPopUp(linesNum);
    }

    public void AfterInitMenu(long _menu) {
        if (this.idIsCorrect(this.menuTitleLeftButtonId)) {
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.menuTitleLeftButtonId), this, "onLeftPressed", 4L);
        }
        if (this.idIsCorrect(this.menuTitleRightButtonId)) {
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.menuTitleRightButtonId), this, "onRightPressed", 4L);
        }
        if (this.idIsCorrect(this.chooseSkinButtonId)) {
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.chooseSkinButtonId), this, "onChooseSkinButtonPressed", 4L);
        }
        if (this.idIsCorrect(this.dlcSkinButtonId)) {
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.dlcSkinButtonId), this, "onDLCSkinButtonPressed", 4L);
        }
        this.updateExteriorMenu(_menu);
        this.dlcSkinPopUp.afterInit();
        this.vehicleTexturesTableWrapped.afterInit();
    }

    public void deinitMenu() {
        this.vehicleTexturesTableWrapped.deinit();
    }

    public void onLeftPressed(long _menu, MENUsimplebutton_field button) {
        if (this.exteriorMenuState == ExteriorMenuState.DLC) {
            this.exteriorMenuState = ExteriorMenuState.BASE;
            this.updateExteriorMenu(_menu);
            this.menuImplementation.onLeftPressed();
        }
    }

    public void onRightPressed(long _menu, MENUsimplebutton_field button) {
        if (this.exteriorMenuState == ExteriorMenuState.BASE) {
            this.exteriorMenuState = ExteriorMenuState.DLC;
            this.updateExteriorMenu(_menu);
            this.menuImplementation.onRightPressed();
            this.vehicleTexturesTableWrapped.setTexture(this.menuImplementation.getTextureId());
        }
    }

    public void onChooseSkinButtonPressed(long _menu, MENUsimplebutton_field button) {
        switch (this.exteriorMenuState) {
            case BASE: {
                if (this.idIsCorrect(this.baseSkinPopUpId)) {
                    menues.SetShowField(this.baseSkinPopUpId, true);
                }
                this.dlcSkinPopUp.hide();
                break;
            }
            case DLC: {
                if (this.idIsCorrect(this.baseSkinPopUpId)) {
                    menues.SetShowField(this.baseSkinPopUpId, false);
                }
                this.dlcSkinPopUp.show();
            }
        }
    }

    public void onDLCSkinButtonPressed(long _menu, MENUsimplebutton_field button) {
        TableLine currentLine = this.vehicleTexturesTableWrapped.getSelected();
        TableLine nextLine = this.vehicleTexturesTableWrapped.getNextTableLine(currentLine);
        this.vehicleTexturesTableWrapped.selectLineByData(nextLine);
    }

    private void afterInitPopUp(int rowsNum) {
        if (rowsNum > 4) {
            this.dlcSkinPopUp.MoveByFromOrigin(0, -(rowsNum -= 4) * 38);
            this.dlcSkinPopUp.resize(0, rowsNum * 38);
            long backId = this.dlcSkinPopUp.getField(POPUP_BACK);
            MENUText_field backField = menues.ConvertTextFields(backId);
            backField.leny += rowsNum * 38;
            menues.UpdateField(backField);
            long rangerBackId = this.dlcSkinPopUp.getField(POPUP_RANGER_BACK);
            MENUText_field rangerBackField = menues.ConvertTextFields(rangerBackId);
            rangerBackField.leny += rowsNum * 38;
            menues.UpdateField(rangerBackField);
            long rangerButtonDownId = this.dlcSkinPopUp.getField(POPUP_RANGER_BUTTON_DOWN);
            MENUText_field rangerButtonDownField = menues.ConvertTextFields(rangerButtonDownId);
            rangerButtonDownField.poy += rowsNum * 38;
            menues.UpdateField(rangerButtonDownField);
            long rangerId = this.dlcSkinPopUp.getField(POPUP_RANGER);
            MENU_ranger rangerField = menues.ConvertRanger(rangerId);
            rangerField.leny += rowsNum * 38;
            menues.UpdateField(rangerField);
        }
    }

    private void updateExteriorMenu(long _menu) {
        MENUText_field exteriorTextButtonField;
        switch (this.exteriorMenuState) {
            case BASE: {
                if (this.idIsCorrect(this.MenuTitleTextBox)) {
                    menues.SetFieldText(this.MenuTitleTextBox, MenuTitleVariants[0]);
                }
                if (this.idIsCorrect(this.baseSkinButtonId)) {
                    menues.SetShowField(this.baseSkinButtonId, true);
                }
                if (this.idIsCorrect(this.baseSkinChameleonButtonId)) {
                    menues.SetShowField(this.baseSkinChameleonButtonId, true);
                }
                if (this.idIsCorrect(this.baseSkinPopUpId)) {
                    menues.SetShowField(this.baseSkinPopUpId, false);
                }
                if (this.idIsCorrect(this.dlcSkinButtonId)) {
                    menues.SetShowField(this.dlcSkinButtonId, false);
                }
                if (!this.idIsCorrect(this.dlcSkinChameleonPictureId)) break;
                menues.SetShowField(this.dlcSkinChameleonPictureId, false);
                break;
            }
            case DLC: {
                if (this.idIsCorrect(this.MenuTitleTextBox)) {
                    menues.SetFieldText(this.MenuTitleTextBox, MenuTitleVariants[1]);
                }
                if (this.idIsCorrect(this.baseSkinButtonId)) {
                    menues.SetShowField(this.baseSkinButtonId, false);
                }
                if (this.idIsCorrect(this.baseSkinChameleonButtonId)) {
                    menues.SetShowField(this.baseSkinChameleonButtonId, false);
                }
                if (this.idIsCorrect(this.baseSkinPopUpId)) {
                    menues.SetShowField(this.baseSkinPopUpId, false);
                }
                if (this.idIsCorrect(this.dlcSkinButtonId)) {
                    menues.SetShowField(this.dlcSkinButtonId, true);
                }
                if (!this.idIsCorrect(this.dlcSkinChameleonPictureId)) break;
                menues.SetShowField(this.dlcSkinChameleonPictureId, true);
            }
        }
        if (this.idIsCorrect(this.MenuTitleTextBox) && null != (exteriorTextButtonField = menues.ConvertTextFields(this.MenuTitleTextBox))) {
            menues.UpdateField(exteriorTextButtonField);
        }
    }

    private boolean idIsCorrect(long menuObjectId) {
        return 0L != menuObjectId;
    }

    static {
        MenuTitleVariants = new String[]{loc.getMENUString("common\\Body Paint (Base)"), loc.getMENUString("common\\Body Paint (DLC)")};
        DefaultSkinTitle = loc.getMENUString("common\\Default Skin");
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    class VehicleTexturesTableWrapped
    extends TableWrapped {
        private PoPUpMenu dlcSkinPopUp;
        private ArrayList<DLCTextureLine> dlcTextureLines;

        VehicleTexturesTableWrapped(long _menu, PoPUpMenu parentPopUp, int linesNum) {
            super(_menu, 1, false, XML_PATH, TABLE_GROUP, TABLE_RANGER, TABLE_LINE, TABLE_LINE_ELEMENTS, null, null, linesNum);
            this.dlcSkinPopUp = parentPopUp;
            this.deselectOnLooseFocus(true);
        }

        public void deinit() {
            this.table.deinit();
        }

        @Override
        public void onSort(long _menu, MENUsimplebutton_field button) {
        }

        @Override
        public void SetupLineInTable(long button, int position, TableLine table_node) {
            DLCTextureLine line = (DLCTextureLine)table_node;
            switch (position) {
                case 0: {
                    menues.SetShowField(button, false);
                    break;
                }
                case 1: {
                    menues.SetFieldText(button, line.getTextTitle());
                    menues.SetShowField(button, !line.isFree);
                    break;
                }
                case 2: {
                    menues.SetShowField(button, line.isFree);
                }
            }
            menues.UpdateMenuField(menues.ConvertMenuFields(button));
        }

        @Override
        public void updateSelectedInfo(TableLine linedata) {
            if (RestylingMenu_ExteriorTab.this.exteriorMenuState == ExteriorMenuState.DLC) {
                this.dlcSkinPopUp.hide();
                DLCTextureLine textureLine = (DLCTextureLine)linedata;
                if (!textureLine.isFree) {
                    RestylingMenu_ExteriorTab.this.menuImplementation.selectDLCTexture(textureLine.textureId);
                    if (RestylingMenu_ExteriorTab.this.idIsCorrect(RestylingMenu_ExteriorTab.this.dlcSkinButtonId)) {
                        menues.SetFieldText(RestylingMenu_ExteriorTab.this.dlcSkinButtonId, textureLine.getTextTitle());
                        menues.UpdateMenuField(menues.ConvertMenuFields(RestylingMenu_ExteriorTab.this.dlcSkinButtonId));
                    }
                }
            }
        }

        @Override
        protected void reciveTableData() {
            this.TABLE_DATA.all_lines.addAll(this.getDLCTextureLines());
        }

        // @Override
        public void enterFocus(Table table) {
        }

        // @Override
        public void leaveFocus(Table table) {
        }

        public void setTexture(int textureId) {
            if (RestylingMenu_ExteriorTab.this.exteriorMenuState == ExteriorMenuState.DLC) {
                int indexOfLine = this.getIndexOfTexture(textureId);
                if (-1 == indexOfLine) {
                    this.selectLineByData(this.getDefaultDLCTextureLine());
                    return;
                }
                TableLine tableLine = this.TABLE_DATA.all_lines.get(indexOfLine);
                this.selectLineByData(tableLine);
            }
        }

        public TableLine getNextTableLine(TableLine currentTableLine) {
            int size;
            DLCTextureLine currentTextureLine;
            if (null == this.dlcTextureLines) {
                this.dlcTextureLines = this.receiveDLCTextureLines();
            }
            if (null == (currentTextureLine = (DLCTextureLine)currentTableLine) || currentTextureLine.isFree) {
                return this.getDefaultDLCTextureLine();
            }
            int indexOfCurrentLine = this.TABLE_DATA.all_lines.indexOf(currentTableLine);
            if (indexOfCurrentLine >= (size = this.dlcTextureLines.size()) - 1) {
                return this.getFirstDLCTextureLine();
            }
            return this.TABLE_DATA.all_lines.elementAt(indexOfCurrentLine + 1);
        }

        public int getLinesNumber() {
            return this.TABLE_DATA.all_lines.size();
        }

        private ArrayList<DLCTextureLine> getDLCTextureLines() {
            if (null == this.dlcTextureLines) {
                this.dlcTextureLines = this.receiveDLCTextureLines();
            }
            return this.dlcTextureLines;
        }

        private int getIndexOfTexture(int textureId) {
            for (TableLine tableLine : this.TABLE_DATA.all_lines) {
                DLCTextureLine textureLine = (DLCTextureLine)tableLine;
                if (textureLine.textureId != textureId) continue;
                return this.TABLE_DATA.all_lines.indexOf(tableLine);
            }
            return -1;
        }

        private TableLine getDefaultDLCTextureLine() {
            TableLine result = null;
            for (TableLine tableLine : this.TABLE_DATA.all_lines) {
                DLCTextureLine textureLine = (DLCTextureLine)tableLine;
                if (!textureLine.isDefault) continue;
                result = tableLine;
                break;
            }
            assert (null != result) : "List of DLC textures must contain default skin texture";
            return result;
        }

        private TableLine getFirstDLCTextureLine() {
            DLCTextureLine result = null;
            for (TableLine tableLine : this.TABLE_DATA.all_lines) {
                DLCTextureLine textureLine = (DLCTextureLine)tableLine;
                if (textureLine.isFree) continue;
                result = textureLine;
                break;
            }
            assert (null != result) : "List of DLC textures must contain default skin texture";
            return this.getDefaultDLCTextureLine();
        }

        private ArrayList<DLCTextureLine> receiveDLCTextureLines() {
            ArrayList<DLCTextureLine> result = new ArrayList<DLCTextureLine>();
            Vector<VehicleDLCTextureInfo> userTexturesInfoVec = RestylingMenu_ExteriorTab.this.menuImplementation.getUserTextureInfoVec();
            if (userTexturesInfoVec.size() < 4) {
                int additionalLinesNum = 4 - userTexturesInfoVec.size();
                for (int i = 0; i < additionalLinesNum; ++i) {
                    DLCTextureLine texture = new DLCTextureLine();
                    texture.isFree = true;
                    texture.isDefault = false;
                    result.add(texture);
                }
            }
            boolean isDefaultSkin = true;
            for (VehicleDLCTextureInfo info : userTexturesInfoVec) {
                DLCTextureLine texture = new DLCTextureLine();
                if (isDefaultSkin) {
                    texture.isDefault = true;
                    isDefaultSkin = false;
                } else {
                    texture.isDefault = false;
                }
                texture.isFree = false;
                texture.textureId = info.textureId;
                texture.textureName = info.textureName;
                result.add(texture);
            }
            return result;
        }
    }

    static class DLCTextureLine
    extends TableLine {
        boolean isFree;
        boolean isDefault;
        int textureId;
        String textureName;

        DLCTextureLine() {
        }

        public String getTextTitle() {
            if (this.isDefault) {
                return DefaultSkinTitle;
            }
            if (this.isFree) {
                return "";
            }
            return this.textureName;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static enum ExteriorMenuState {
        BASE,
        DLC;

    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum CalledFromMenu {
        QUICK_RACE_MENU,
        STO_MENU;

    }
}

