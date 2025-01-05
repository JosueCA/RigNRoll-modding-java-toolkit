/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import gameobj.CarInfo;
import gameobj.DriverInfo;
import gameobj.OfficeInfo;
import java.util.Vector;
import menu.Common;
import menu.MENUText_field;
import menu.TableCmp;
import menu.TableNode;
import menu.TableSelect;
import menu.menues;

public abstract class OfficeTab {
    public static final double FADE_DUR = 1.0;
    public static final int MALE = 0;
    public static final int FEMALE = 1;
    public static final int MODEL = 1;
    public static final int MAKE = 2;
    public static final int MAKEMODEL = 3;
    public static final int CONDITION = 4;
    public static final int LICENSE = 5;
    public static final int CONFIGURATION = 6;
    public static final int DRIVESYSTEM = 7;
    public static final int CAB = 8;
    public static final int HORSEPOWER = 9;
    public static final int GVWR = 10;
    public static final int NEWUSED = 11;
    public static final int OFFICE = 12;
    public static final int CAR_PHOTO = 14;
    public static final int DEALER = 15;
    public static final int PRICE = 16;
    public static final int FIRSTNAME = 17;
    public static final int LASTNAME = 18;
    public static final int FULLNAME = 19;
    public static final int WAGERATE = 20;
    public static final int GENDER = 21;
    public static final int ACCIDENTS = 22;
    public static final int HASFELONY = 23;
    public static final int EXPERIENCE = 24;
    public static final int AGE = 25;
    public static final int TICKETS = 26;
    public static final int RATING = 27;
    public static final int RATING_PIC = 28;
    public static final int DRIVER_PHOTO = 29;
    public static final int RECRUITER = 30;
    public static final int ADVANCE = 31;
    public static final int COMISSION = 32;
    public static final int NAMEAGE_STRING = 33;
    public static final int CARNAME_2ROW = 34;
    public static final int TOTAL = 35;
    public static final int MENU_DEFAULTCOLOR = 0;
    public static final int MENU_HIGHLIGHTCOLOR = -65536;
    public static final int RESTYLE_MAINCOLOR = 0;
    public static final int RESTYLE_LEATHER = 1;
    public static final int RESTYLE_CLOTH = 2;

    public static void SetupRestyleTexcoords(menues.ctexcoord_multylayer layer, int type, int index) {
        float shift = 0.0f;
        switch (type) {
            case 0: {
                shift = 0.125f;
                break;
            }
            case 1: {
                shift = 0.125f;
                break;
            }
            case 2: {
                shift = 0.0625f;
            }
        }
        layer.t0x = 0.0f;
        layer.t0y = (float)index * shift;
        layer.t1x = 1.0f;
        layer.t1y = (float)index * shift;
        layer.t2x = 1.0f;
        layer.t2y = (float)(index + 1) * shift;
        layer.t3x = 0.0f;
        layer.t3y = (float)(index + 1) * shift;
    }

    public static void FillRestylePicture(MENUText_field picture, int type, int index) {
        FillRestyleHelper h = new FillRestyleHelper();
        h.SetupPicture(picture, type, index);
    }

    public void SetupFadeAnimation(long control, int animationid) {
        menues.SetScriptObjectAnimation(control, animationid, this, "FadeAnimation");
    }

    public int compare(Object o1, Object o2) {
        return 0;
    }

    public class FadeTable
    implements TableSelect {
        FadeAnimation anim = new FadeAnimation();

        public void Select(TableNode node, int userid, long control) {
            this.anim.Start(control);
        }

        public void Deselect(TableNode node, int userid, long control) {
            this.anim.Finish(control);
        }
    }

    public static class FadeAnimationTextField {
        int color;
        int animationid;
        int type;
        MENUText_field field;
        double fadedur;

        public FadeAnimationTextField(MENUText_field _field) {
            this.field = _field;
            this.fadedur = 1.0;
            this.animationid = Common.GetID();
        }

        public FadeAnimationTextField(MENUText_field _field, double _fadedur) {
            this.field = _field;
            this.fadedur = _fadedur;
            this.animationid = Common.GetID();
        }

        public void Finish() {
            this.field.textColor = this.color | 0xFF000000;
            menues.UpdateField(this.field);
            menues.StopScriptAnimation(this.animationid);
        }

        public void Start() {
            this.color = this.field.textColor & 0xFFFFFF;
            menues.SetScriptObjectAnimation(0L, this.animationid, this, "Animate");
        }

        public void Animate(long control, double time) {
            int outcolor;
            double t = (time - (double)((int)(time / this.fadedur)) * this.fadedur) / this.fadedur;
            double fade = t < 0.5 ? t * 2.0 : (1.0 - t) * 2.0;
            int alpha = (int)(fade * 255.0);
            this.field.textColor = outcolor = this.color | alpha << 24;
            menues.UpdateField(this.field);
        }
    }

    public static class FadeAnimation_ControlColor {
        int animationid;
        int state;
        double fadedur;
        Vector controls;
        boolean ison;
        int startedstate;
        boolean isradio;

        public FadeAnimation_ControlColor(int _state, double _fadedur, boolean isradio) {
            this.Init(_state, _fadedur, isradio);
        }

        public FadeAnimation_ControlColor(int _state, double _fadedur) {
            this.Init(_state, _fadedur, true);
        }

        public void Init(int _state, double _fadedur, boolean isradio) {
            this.animationid = Common.GetID();
            this.state = _state;
            this.fadedur = _fadedur;
            this.isradio = isradio;
            this.controls = new Vector();
        }

        void ChangeState(int _state) {
            this.state = _state;
        }

        public void AddControl(long control) {
            this.controls.add(new Long(control));
        }

        public void Finish() {
            if (!this.ison) {
                return;
            }
            for (int i = 0; i < this.controls.size(); ++i) {
                long control = (Long)this.controls.get(i);
                menues.SetControlColor(control, false, false, this.startedstate, -1);
                menues.SetControlColor(control, true, false, this.startedstate, -1);
                menues.SetControlColor(control, true, true, this.startedstate, -1);
            }
            menues.StopScriptAnimation(this.animationid);
            this.ison = false;
        }

        boolean IsOn() {
            return this.ison;
        }

        public void Start() {
            if (this.ison) {
                return;
            }
            menues.SetScriptObjectAnimation(0L, this.animationid, this, "Animate");
            this.startedstate = this.state;
            this.ison = true;
        }

        public void Animate(long nothing, double time) {
            double t = (time - (double)((int)(time / this.fadedur)) * this.fadedur) / this.fadedur;
            double fade = t < 0.5 ? t * 2.0 : (1.0 - t) * 2.0;
            int alpha = (int)(fade * 255.0);
            int outcolor = 0xFFFFFF | alpha << 24;
            for (int i = 0; i < this.controls.size(); ++i) {
                long control = (Long)this.controls.get(i);
                if (this.isradio) {
                    menues.SetControlColor(control, false, false, this.startedstate, outcolor);
                    menues.SetControlColor(control, true, false, this.startedstate, outcolor);
                    menues.SetControlColor(control, true, true, this.startedstate, outcolor);
                    continue;
                }
                menues.SetControlColor(control, false, this.startedstate, outcolor);
                menues.SetControlColor(control, true, this.startedstate, outcolor);
            }
        }
    }

    public static class FadeAnimation {
        int color;
        int color1;
        int animationid = Common.GetID();
        int type;

        public void Finish(long control) {
            menues.SetTextColor(control, true, 1, this.color | 0xFF000000);
            menues.SetTextColor(control, false, 1, this.color1 | 0xFF000000);
            menues.StopScriptAnimation(this.animationid);
        }

        public void Start(long control) {
            this.color = menues.GetTextColor(control, true, 1) & 0xFFFFFF;
            this.color1 = menues.GetTextColor(control, false, 1) & 0xFFFFFF;
            menues.SetScriptObjectAnimation(control, this.animationid, this, "Animate");
        }

        public void Animate(long control, double time) {
            double t = (time - (double)((int)(time / 1.0)) * 1.0) / 1.0;
            double fade = t < 0.5 ? t * 2.0 : (1.0 - t) * 2.0;
            int alpha = (int)(fade * 255.0);
            int outcolor = this.color | alpha << 24;
            int outcolor1 = this.color1 | alpha << 24;
            menues.SetTextColor(control, true, 1, outcolor);
            menues.SetTextColor(control, false, 1, outcolor1);
        }
    }

    static class SortDriver
    extends TableCmp {
        DriverInfoWrapper wrap;
        int type;

        public SortDriver(DriverInfoWrapper wrapper, int _type) {
            this.wrap = wrapper;
            this.type = _type;
        }

        public int compare(Object o1, Object o2) {
            DriverInfo c1 = this.wrap.GetDriverInfo(o1);
            DriverInfo c2 = this.wrap.GetDriverInfo(o2);
            switch (this.type) {
                case 17: {
                    return Common.Compare(c1.firstname, c2.firstname, this.order);
                }
                case 18: {
                    return Common.Compare(c1.lastname, c2.lastname, this.order);
                }
                case 19: {
                    return Common.Compare(c1.lastname, c2.lastname, this.order);
                }
                case 27: {
                    return Common.Compare(c1.d_rating, c2.d_rating, this.order);
                }
                case 20: {
                    return Common.Compare(c1.wagerate, c2.wagerate, this.order);
                }
                case 21: {
                    return Common.Compare(c1.gender, c2.gender, this.order);
                }
                case 25: {
                    return Common.Compare(c1.age, c2.age, this.order);
                }
                case 26: {
                    return Common.Compare(c1.tickets, c2.tickets, this.order);
                }
                case 22: {
                    return Common.Compare(c1.accidents, c2.accidents, this.order);
                }
                case 23: {
                    return Common.Compare(c1.hasfelony, c2.hasfelony, this.order);
                }
                case 24: {
                    return Common.Compare(c1.experience, c2.experience, this.order);
                }
            }
            return 0;
        }
    }

    static class SortOffice
    extends TableCmp {
        OfficeInfoWrapper wrap;
        int type;

        public SortOffice(OfficeInfoWrapper wrapper, int _type) {
            this.wrap = wrapper;
            this.type = _type;
        }

        public int compare(Object o1, Object o2) {
            OfficeInfo c1 = this.wrap.GetOfficeInfo(o1);
            OfficeInfo c2 = this.wrap.GetOfficeInfo(o2);
            switch (this.type) {
                case 12: {
                    return Common.Compare(c1.name, c2.name, this.order);
                }
            }
            return 0;
        }
    }

    static interface DriverInfoWrapper {
        public DriverInfo GetDriverInfo(Object var1);
    }

    static interface OfficeInfoWrapper {
        public OfficeInfo GetOfficeInfo(Object var1);
    }

    static interface CarInfoWrapper {
        public CarInfo GetCarInfo(Object var1);
    }

    static class TrivialDriverAdapter
    implements DriverInfoWrapper {
        TrivialDriverAdapter() {
        }

        public DriverInfo GetDriverInfo(Object obj) {
            return (DriverInfo)obj;
        }
    }

    static class TrivialCarAdapter
    implements CarInfoWrapper {
        TrivialCarAdapter() {
        }

        public CarInfo GetCarInfo(Object obj) {
            return (CarInfo)obj;
        }
    }

    private static class FillRestyleHelper {
        MENUText_field m_picture;
        int m_type;
        int m_index;

        private FillRestyleHelper() {
        }

        void SetupPicture(MENUText_field picture, int type, int index) {
            this.m_picture = picture;
            this.m_type = type;
            this.m_index = index;
            menues.CallMappingModifications(picture.nativePointer, this, "PictureMapping");
        }

        void PictureMapping(int sizex, int sizey, menues.CMaterial_whithmapping[] stuff) {
            for (int i = 0; i < stuff.length; ++i) {
                if (stuff[i]._state < 0 || stuff[i].tex.size() == 0) continue;
                OfficeTab.SetupRestyleTexcoords((menues.ctexcoord_multylayer)stuff[i].tex.get(0), this.m_type, this.m_index);
            }
        }
    }
}

