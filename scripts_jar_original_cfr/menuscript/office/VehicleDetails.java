/*
 * Decompiled with CFR 0.151.
 */
package menuscript.office;

import gameobj.CarInfo;
import java.util.ArrayList;
import menu.Common;
import menu.KeyPair;
import menu.MENUText_field;
import menu.MENUTruckview;
import menu.MENU_ranger;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.TextScroller;
import menu.TruckView;
import menu.menues;
import menuscript.Converts;
import menuscript.VehicleLoader;
import menuscript.office.IVehicleDetailesListener;
import menuscript.office.ManageFlitManager;
import rnrcore.Log;

public class VehicleDetails {
    private static final String XML = "..\\data\\config\\menu\\menu_office.xml";
    private static final String GROUP = "MF - Specifications - SHOW - Vehicle Details";
    private static final String TRUCK_VIEW = "Vehicle Details - Truckview";
    private static final String CONTROL_GROUP_INTERIOR = "Interior LEGENDS - Mouse";
    private static final String CONTROL_GROUP_EXTERIOR = "Exterior LEGENDS - Mouse";
    private static final String[] DATA_FIELDS = new String[]{"Vehicle Details VALUE - License Plate", "Vehicle Details VALUE - Type", "Vehicle Details VALUE - Condition", "Vehicle Details VALUE - Wear", "Vehicle Details VALUE - Mileage", "Vehicle Details VALUE - Speed", "Vehicle Details VALUE - Load Safety", "Vehicle Details VALUE - Suspension", "Vehicle Details VALUE - Horse Power", "Vehicle Details VALUE - Color"};
    Common common;
    private static final String UPGRADES_TEXTFIELD = "Vehicle Details VALUE - Upgrades";
    private static final String UPGRADES_RANGER = "Vehicle Details - Upgrades - Tableranger";
    TextScroller scroller = null;
    private long id_upgrade_text = 0L;
    private long id_upgrade_scroller = 0L;
    private static final int LICENCE = 0;
    private static final int TYPE = 1;
    private static final int CONDITION = 2;
    private static final int WEAR = 3;
    private static final int MILIAGE = 4;
    private static final int SPEED = 5;
    private static final int LOADSAFTY = 6;
    private static final int SUSPESION = 7;
    private static final int HORSE = 8;
    private static final int COLOR = 9;
    private static final String[] HEAD = new String[]{"Vehicle Details - BOTTOM TITLE", "Vehicle Details - TOP TITLE"};
    private static final String MACRO_MODEL = "MODEL";
    private static final String BRIEF = "Vehicle Details - BRIEFLY - TITLE";
    private long[] data_fields = new long[DATA_FIELDS.length];
    private String[] data_fields_strings = new String[DATA_FIELDS.length];
    private static final String[] ACTIONS = new String[]{"BUTTON - Vehicle Details - OK", "BUTTON - Vehicle Details - EXTERIOR", "BUTTON - Vehicle Details - INTERIOR"};
    private static final String[] METHODS = new String[]{"onOk", "onExterior", "onInterior"};
    private ArrayList<IVehicleDetailesListener> listeners = new ArrayList();
    private long[] controls;
    private TruckView view = null;
    private CarInfo current = null;
    private ManageFlitManager.VehicleId idcurrent = null;
    private boolean isVehicleOur = false;
    private String[] valuesHEAD;
    private long[] controlsHEAD;
    private String valueBRIEF;
    private long controlBRIEF;
    private long interiorControls;
    private long exteriorControls;

    public VehicleDetails(long _menu) {
        int i;
        this.controls = menues.InitXml(_menu, XML, GROUP);
        if (this.controls == null) {
            Log.menu("ERRORR. VehicleDetails. Xml ..\\data\\config\\menu\\menu_office.xml does not contain control group MF - Specifications - SHOW - Vehicle Details");
            return;
        }
        if (this.controls.length == 0) {
            Log.menu("ERRORR. VehicleDetails. In xml ..\\data\\config\\menu\\menu_office.xml control group MF - Specifications - SHOW - Vehicle Details contains no elements.");
            return;
        }
        for (i = 0; i < ACTIONS.length; ++i) {
            long cont = menues.FindFieldInMenu(_menu, ACTIONS[i]);
            if (0L == cont) {
                Log.menu("ERRORR. VehicleDetails. In xml ..\\data\\config\\menu\\menu_office.xml control group MF - Specifications - SHOW - Vehicle Details contains no button " + ACTIONS[i]);
                return;
            }
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(cont), this, METHODS[i], 4L);
        }
        for (i = 0; i < DATA_FIELDS.length; ++i) {
            this.data_fields[i] = menues.FindFieldInMenu(_menu, DATA_FIELDS[i]);
        }
        long truckView = menues.FindFieldInMenu(_menu, TRUCK_VIEW);
        if (0L == truckView) {
            Log.menu("ERRORR. VehicleDetails. In xml ..\\data\\config\\menu\\menu_office.xml control group MF - Specifications - SHOW - Vehicle Details contains no truck view Vehicle Details - Truckview");
            return;
        }
        this.view = new TruckView((MENUTruckview)menues.ConvertMenuFields(truckView));
        this.valuesHEAD = new String[HEAD.length];
        this.controlsHEAD = new long[HEAD.length];
        for (int i2 = 0; i2 < HEAD.length; ++i2) {
            this.controlsHEAD[i2] = menues.FindFieldInMenu(_menu, HEAD[i2]);
            if (0L == this.controlsHEAD[i2]) {
                Log.menu("ERRORR. VehicleDetails. In xml ..\\data\\config\\menu\\menu_office.xml control group MF - Specifications - SHOW - Vehicle Details contains no HEAD control " + HEAD[i2]);
                continue;
            }
            this.valuesHEAD[i2] = menues.GetFieldText(this.controlsHEAD[i2]);
        }
        this.controlBRIEF = menues.FindFieldInMenu(_menu, BRIEF);
        if (0L == this.controlBRIEF) {
            Log.menu("ERRORR. VehicleDetails. In xml ..\\data\\config\\menu\\menu_office.xml control group MF - Specifications - SHOW - Vehicle Details contains no BRIEF control Vehicle Details - BRIEFLY - TITLE");
        } else {
            this.valueBRIEF = menues.GetFieldText(this.controlBRIEF);
        }
        this.id_upgrade_text = menues.FindFieldInMenu(_menu, UPGRADES_TEXTFIELD);
        this.id_upgrade_scroller = menues.FindFieldInMenu(_menu, UPGRADES_RANGER);
        this.interiorControls = menues.FindFieldInMenu(_menu, CONTROL_GROUP_INTERIOR);
        this.exteriorControls = menues.FindFieldInMenu(_menu, CONTROL_GROUP_EXTERIOR);
        this.common = new Common(_menu);
    }

    public void afterInit() {
        for (int i = 0; i < DATA_FIELDS.length; ++i) {
            this.data_fields_strings[i] = this.data_fields[i] != 0L ? menues.GetFieldText(this.data_fields[i]) : null;
        }
    }

    public void DeInit() {
        if (this.scroller != null) {
            this.scroller.Deinit();
        }
    }

    public void show(CarInfo info, ManageFlitManager.VehicleId id, boolean owrvehicle) {
        this.current = info;
        this.idcurrent = id;
        this.isVehicleOur = owrvehicle;
        VehicleLoader.BindExterior(this.view, this.current);
        if (this.interiorControls != 0L) {
            menues.SetShowField(this.interiorControls, false);
        }
        if (this.exteriorControls != 0L) {
            menues.SetShowField(this.exteriorControls, true);
        }
        menues.SetShowField(this.controls[0], true);
        this.update();
    }

    public void hide() {
        menues.SetShowField(this.controls[0], false);
        for (IVehicleDetailesListener item : this.listeners) {
            item.onClose();
        }
    }

    private void update() {
        ManageFlitManager.FullMyVehicleInfo info1 = null;
        ManageFlitManager.FullDealerVehicleInfo info2 = null;
        if (this.isVehicleOur) {
            info1 = ManageFlitManager.getManageFlitManager().GetMyVehiclesInfoWithUpgrades(this.idcurrent);
        } else {
            info2 = ManageFlitManager.getManageFlitManager().GetDealerVehiclesInfoWithUpgrades(this.idcurrent);
        }
        KeyPair[] keys = new KeyPair[1];
        for (int i = 0; i < this.data_fields.length; ++i) {
            switch (i) {
                case 0: {
                    if (null != info1) {
                        menues.SetFieldText(this.data_fields[i], info1.license_plate);
                        break;
                    }
                    if (null == info2) break;
                    menues.SetFieldText(this.data_fields[i], "-");
                    break;
                }
                case 1: {
                    if (null != info1) {
                        menues.SetFieldText(this.data_fields[i], info1.type);
                        break;
                    }
                    if (null == info2) break;
                    menues.SetFieldText(this.data_fields[i], info2.type);
                    break;
                }
                case 2: {
                    if (null != info1) {
                        keys[0] = new KeyPair("VALUE", "" + (int)(100.0 * (double)info1.condition));
                    } else if (null != info2) {
                        keys[0] = new KeyPair("VALUE", "" + (int)(100.0 * (double)info2.condition));
                    }
                    menues.SetFieldText(this.data_fields[i], MacroKit.Parse(this.data_fields_strings[i], keys));
                    break;
                }
                case 3: {
                    if (null != info1) {
                        keys[0] = new KeyPair("VALUE", "" + (int)(100.0 * (double)info1.wear));
                        menues.SetFieldText(this.data_fields[i], MacroKit.Parse(this.data_fields_strings[i], keys));
                        break;
                    }
                    if (null == info2) break;
                    menues.SetFieldText(this.data_fields[i], "-");
                    break;
                }
                case 4: {
                    if (null != info1) {
                        keys[0] = new KeyPair("VALUE", "" + (int)info1.mileage);
                    } else if (null != info2) {
                        keys[0] = new KeyPair("VALUE", "" + (int)info2.mileage);
                    }
                    menues.SetFieldText(this.data_fields[i], MacroKit.Parse(this.data_fields_strings[i], keys));
                    break;
                }
                case 5: {
                    if (null != info1) {
                        keys[0] = new KeyPair("VALUE", "" + (int)(100.0 * (double)info1.speed));
                        menues.SetFieldText(this.data_fields[i], MacroKit.Parse(this.data_fields_strings[i], keys));
                        break;
                    }
                    if (null == info2) break;
                    menues.SetFieldText(this.data_fields[i], "-");
                    break;
                }
                case 6: {
                    if (null != info1) {
                        keys[0] = new KeyPair("VALUE", "" + (int)(100.0 * (double)info1.load_safety));
                        menues.SetFieldText(this.data_fields[i], MacroKit.Parse(this.data_fields_strings[i], keys));
                        break;
                    }
                    if (null == info2) break;
                    menues.SetFieldText(this.data_fields[i], "-");
                    break;
                }
                case 7: {
                    if (null != info1) {
                        menues.SetFieldText(this.data_fields[i], info1.suspension);
                        break;
                    }
                    if (null == info2) break;
                    menues.SetFieldText(this.data_fields[i], info2.suspension);
                    break;
                }
                case 8: {
                    if (null != info1) {
                        keys[0] = new KeyPair("VALUE", "" + (int)info1.horse_power);
                    } else if (null != info2) {
                        keys[0] = new KeyPair("VALUE", "" + (int)info2.horsepower);
                    }
                    menues.SetFieldText(this.data_fields[i], MacroKit.Parse(this.data_fields_strings[i], keys));
                    break;
                }
                case 9: {
                    if (null != info1) {
                        menues.SetFieldText(this.data_fields[i], info1.color);
                        break;
                    }
                    if (null == info2) break;
                    menues.SetFieldText(this.data_fields[i], info2.color);
                }
            }
            menues.UpdateMenuField(menues.ConvertMenuFields(this.data_fields[i]));
        }
        MENU_ranger ranger = (MENU_ranger)menues.ConvertMenuFields(this.id_upgrade_scroller);
        MENUText_field text = (MENUText_field)menues.ConvertMenuFields(this.id_upgrade_text);
        if (ranger != null && text != null) {
            if (this.id_upgrade_text != 0L) {
                menues.SetShowField(this.id_upgrade_text, true);
            }
            String upgrade_text = "NONE";
            if (null != info1) {
                upgrade_text = info1.upgrades_info_string;
            } else if (null != info2) {
                upgrade_text = info2.upgrades_info_string;
            }
            text.text = upgrade_text;
            menues.UpdateField(text);
            int texh = menues.GetTextLineHeight(text.nativePointer);
            int startbase = menues.GetBaseLine(text.nativePointer);
            int linescreen = Converts.HeightToLines(text.leny, startbase, texh);
            int linecounter = Converts.HeightToLines(menues.GetTextHeight(text.nativePointer, upgrade_text), startbase, texh);
            if (this.scroller != null) {
                this.scroller.Deinit();
            }
            this.scroller = new TextScroller(this.common, ranger, linecounter, linescreen, texh, startbase, false, UPGRADES_TEXTFIELD);
            this.scroller.AddTextControl(text);
        }
        String modelname = null != info1 ? info1.vehicle_name : (null != info2 ? info2.vehicle_name : "");
        for (int i = 0; i < this.controlsHEAD.length; ++i) {
            KeyPair[] keyss = new KeyPair[]{new KeyPair(MACRO_MODEL, modelname)};
            String res = MacroKit.Parse(this.valuesHEAD[i], keyss);
            menues.SetFieldText(this.controlsHEAD[i], res.toUpperCase());
            menues.UpdateMenuField(menues.ConvertMenuFields(this.controlsHEAD[i]));
        }
        KeyPair[] keys1 = new KeyPair[]{new KeyPair("VEHICLE_MODEL", modelname)};
        menues.SetFieldText(this.controlBRIEF, MacroKit.Parse(this.valueBRIEF, keys1));
        menues.UpdateMenuField(menues.ConvertMenuFields(this.controlBRIEF));
    }

    public void onOk(long _menu, MENUsimplebutton_field button) {
        VehicleLoader.Unload();
        this.hide();
    }

    public void onExterior(long _menu, MENUsimplebutton_field button) {
        VehicleLoader.BindExterior(this.view, this.current);
        if (this.interiorControls != 0L) {
            menues.SetShowField(this.interiorControls, false);
        }
        if (this.exteriorControls != 0L) {
            menues.SetShowField(this.exteriorControls, true);
        }
    }

    public void onInterior(long _menu, MENUsimplebutton_field button) {
        VehicleLoader.BindInterior(this.view, this.current);
        if (this.interiorControls != 0L) {
            menues.SetShowField(this.interiorControls, true);
        }
        if (this.exteriorControls != 0L) {
            menues.SetShowField(this.exteriorControls, false);
        }
    }

    public void addListener(IVehicleDetailesListener lst) {
        this.listeners.add(lst);
    }
}

