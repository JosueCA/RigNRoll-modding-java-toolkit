/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.Random;
import rnrcore.SCRuniperson;
import rnrcore.SCRwarehousecrane;
import rnrcore.SCRwarehousoperator;
import rnrcore.vectorJ;
import rnrscr.animation;

public class warehouse
extends animation {
    private static warehouse instance = new warehouse();
    private long[] m_operators = null;
    private long[] m_cranes = null;
    static int NOMOPERATORMODELS = 5;
    protected boolean wereModelsLoaded = false;
    static SCRwarehousoperator WHOPER;
    static SCRwarehousecrane whCrane;

    public static warehouse getInstance() {
        return instance;
    }

    private warehouse() {
    }

    public long[] getOperators() {
        return this.m_operators;
    }

    public long[] getCranes() {
        return this.m_cranes;
    }

    public void PlacePerson(SCRuniperson PERSONAGE1, vectorJ possit, vectorJ dirrit) {
        PERSONAGE1.SetPosition(possit);
        PERSONAGE1.SetDirection(dirrit);
    }

    public void PlaceOperator(SCRwarehousoperator PERSONAGE1, vectorJ possit, vectorJ dirrit) {
        PERSONAGE1.SetPosition(possit);
        PERSONAGE1.SetDirection(dirrit);
    }

    public void PlaceCrane(SCRwarehousecrane carne, vectorJ possit, vectorJ dirrit) {
        carne.SetPosition(possit);
        carne.SetDirection(dirrit);
    }

    public long[] WHoperator(vectorJ[] poss, vectorJ[] dirs, vectorJ[] poss_crane, vectorJ[] dirs_crane, vectorJ[] poss_crane1, vectorJ[] dirs_crane1) {
        CreateAnimation animcreation = new CreateAnimation();
        int pos_sz = poss.length;
        this.m_operators = new long[2 * pos_sz];
        this.m_cranes = new long[2 * pos_sz];
        int[] dg = this.Shuffle(pos_sz, 1);
        long[] rs = new long[pos_sz];
        int iter = 0;
        for (int pers = 0; pers < pos_sz; ++pers) {
            animcreation.operator(pers);
            WHOPER.play();
            animcreation.crane();
            rs[pers] = warehouse.WHOPER.nativePointer;
            this.PlaceCrane(whCrane, poss_crane[dg[pers]], dirs_crane[dg[pers]]);
            this.PlaceOperator(WHOPER, poss[dg[pers]], dirs[dg[pers]]);
            whCrane.MoveMain(100.0, 25.0, "forward");
            this.m_operators[iter] = warehouse.WHOPER.nativePointer;
            this.m_cranes[iter] = warehouse.whCrane.nativePointer;
            animcreation.operator(pers);
            animcreation.crane();
            this.PlaceCrane(whCrane, poss_crane1[dg[pers]], dirs_crane1[dg[pers]]);
            this.PlaceOperator(WHOPER, poss[dg[pers]], dirs[dg[pers]]);
            whCrane.MoveMain(100.0, 25.0, "forward");
            this.m_operators[++iter] = warehouse.WHOPER.nativePointer;
            this.m_cranes[iter] = warehouse.whCrane.nativePointer;
            ++iter;
        }
        return rs;
    }

    public int[] Shuffle(int sz, int prohids) {
        int[] intArr = new int[sz];
        for (int i0 = 0; i0 < sz; ++i0) {
            intArr[i0] = i0;
        }
        Random rnd_ch = new Random();
        for (int pr = 0; pr < prohids; ++pr) {
            for (int i = 0; i < sz; ++i) {
                int replace = rnd_ch.nextInt(sz - i) + i;
                int prev = intArr[i];
                intArr[i] = intArr[replace];
                intArr[replace] = prev;
            }
        }
        return intArr;
    }

    public long testcrane(vectorJ vehpos, vectorJ vehdir) {
        SCRwarehousoperator ope = new SCRwarehousoperator();
        ope.nWarehousoperator("Man_001");
        SCRwarehousecrane cran = new SCRwarehousecrane();
        cran.nWarehousecrane("model_WarehouseCrane", "Space_Crane_Main");
        cran.FindCraneParts("Space_Crane");
        vectorJ dir0 = vehdir;
        vectorJ dir = new vectorJ(dir0);
        dir.x = -dir0.y;
        dir.y = dir0.x;
        vectorJ pos = dir0.normN();
        pos.x *= -20.0;
        pos.y *= -20.0;
        pos.z += 10.0;
        pos.oPlus(vehpos);
        this.PlaceCrane(cran, pos, dir);
        ope.TakeCrane(cran);
        return ope.nativePointer;
    }

    public class CreateAnimation {
        public void operator(int nomoper) {
            if (!warehouse.this.wereModelsLoaded) {
                warehouse.this.LoadModels(NOMOPERATORMODELS, 0);
            }
            WHOPER = new SCRwarehousoperator();
            WHOPER.nWarehousoperator(warehouse.this.getModelName(0, true));
            WHOPER.AddFreeAnimation("BasePult002", "BasePult004", 1);
            WHOPER.AddAnimationRandom("BasePult003", 1);
            WHOPER.AddPultPodhodAnimation("BasePult005Podhod", "BasePult006Othod");
            WHOPER.AddRuleAnimations("CraneOperator");
        }

        public void crane() {
            whCrane = SCRwarehousecrane.CreateCrane("model_WarehouseCrane", "Space_Crane_Main");
            whCrane.FindCraneParts("Space_Crane");
        }
    }
}

