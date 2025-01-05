/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import gameobj.CarInfo;
import menu.Common;
import menu.ListenerManager;
import menu.SelectCb;
import menu.TruckView;
import menu.menues;

public class VehicleLoader
implements SelectCb {
    static CarInfo s_currentcar;
    static TruckView s_lasttruckview;
    static TruckView s_curview;
    static boolean s_isexterior;
    static boolean s_isanimon;
    static int s_animid;
    static boolean s_init;

    static void CheckInit() {
        if (!s_init) {
            ListenerManager.AddListener(105, new VehicleLoader());
            s_init = true;
        }
    }

    public static void BindExterior(TruckView view, CarInfo car) {
        VehicleLoader.CheckInit();
        if (s_isanimon) {
            return;
        }
        s_curview = view;
        if (car != s_currentcar) {
            s_isexterior = true;
            if (s_currentcar != null) {
                s_currentcar.UnloadVehicle();
                s_lasttruckview.UnbindVehicle();
            }
            if ((s_currentcar = car) != null) {
                s_currentcar.LoadVehicleModel();
                menues.SetScriptAnimation(0L, s_animid, "menuscript/VehicleLoader", "WaitSync");
                s_isanimon = true;
            }
        } else {
            s_isexterior = true;
            if (s_lasttruckview != s_curview) {
                if (null != s_lasttruckview) {
                    s_lasttruckview.UnbindVehicle();
                }
                s_lasttruckview = s_curview;
            }
            s_curview.BindVehicle(s_currentcar, 1, 8);
        }
    }

    public static void BindInterior(TruckView view, CarInfo car) {
        VehicleLoader.CheckInit();
        if (s_isanimon) {
            return;
        }
        s_curview = view;
        if (car != s_currentcar) {
            s_isexterior = false;
            if (s_currentcar != null) {
                s_currentcar.UnloadVehicle();
                s_lasttruckview.UnbindVehicle();
            }
            if ((s_currentcar = car) != null) {
                s_currentcar.LoadVehicleModel();
                menues.SetScriptAnimation(0L, s_animid, "menuscript/VehicleLoader", "WaitSync");
                s_isanimon = true;
            }
        } else {
            s_isexterior = false;
            if (s_lasttruckview != s_curview) {
                if (null != s_lasttruckview) {
                    s_lasttruckview.UnbindVehicle();
                }
                s_lasttruckview = s_curview;
            }
            s_curview.BindVehicle(s_currentcar, 0, 0);
        }
    }

    void WaitSync(long cookie, double time) {
        if (s_currentcar.IsVehicleLoaded()) {
            s_currentcar.LoadVehicleCabine();
            if (s_isexterior) {
                s_curview.BindVehicle(s_currentcar, 1, 8);
            } else {
                s_curview.BindVehicle(s_currentcar, 0, 0);
            }
            s_lasttruckview = s_curview;
            menues.StopScriptAnimation(s_animid);
            s_isanimon = false;
        }
    }

    public static void Unload() {
        if (s_isanimon) {
            menues.StopScriptAnimation(s_animid);
            s_isanimon = false;
        }
        if (s_currentcar != null) {
            s_currentcar.UnloadVehicle();
            s_currentcar = null;
            if (s_lasttruckview != null) {
                s_lasttruckview.UnbindVehicle();
                s_lasttruckview = null;
                s_curview = null;
            }
        }
    }

    public void OnSelect(int state, Object sender) {
        VehicleLoader.Unload();
        s_init = false;
    }

    static {
        s_isanimon = false;
        s_animid = Common.GetID();
        s_init = false;
    }
}

