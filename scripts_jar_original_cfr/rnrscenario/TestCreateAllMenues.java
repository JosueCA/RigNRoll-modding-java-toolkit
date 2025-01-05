/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import java.lang.reflect.Method;
import menu.menues;
import rnrcore.TypicalAnm;
import rnrcore.eng;
import rnrcore.event;
import rnrscenario.CreateAllMenues;

public class TestCreateAllMenues
extends TypicalAnm {
    private static final double COOLDOWN = 2.0;
    CreateAllMenues men = null;
    Method[] meths = null;
    int position = 0;
    private double lastT = 0.0;

    public boolean animaterun(double dt) {
        if (this.meths == null || this.position >= this.meths.length) {
            TestCreateAllMenues.log("TestCreateAllMenues finished");
            event.Setevent(8001);
            return true;
        }
        if (dt - this.lastT > 2.0) {
            this.lastT = dt;
            if (this.position > 0 && this.men.lastMenuCreated != 0L) {
                menues.CallMenuCallBack_ExitMenu(this.men.lastMenuCreated);
                this.men.lastMenuCreated = 0L;
            }
            Method m = this.meths[this.position++];
            TestCreateAllMenues.log(m);
            try {
                m.invoke((Object)this.men, new Object[0]);
            }
            catch (Exception e) {
                eng.fatal(e.getMessage());
            }
        }
        return false;
    }

    private static void log(Method meth) {
        System.err.print("\ntry " + meth.getName());
        System.err.flush();
    }

    private static void log(String text) {
        System.err.print("\n" + text + "\n");
        System.err.flush();
    }

    TestCreateAllMenues() {
        eng.noNative = true;
        eng.CreateInfinitScriptAnimation(this);
        try {
            Class<?> cl = Class.forName("rnrscenario.CreateAllMenues");
            this.meths = cl.getDeclaredMethods();
            this.men = new CreateAllMenues();
        }
        catch (Exception e) {
            Object var1_3 = null;
        }
    }

    static void runAll() {
        new TestCreateAllMenues();
    }

    public static void main(String[] args) {
        TestCreateAllMenues.runAll();
    }
}

