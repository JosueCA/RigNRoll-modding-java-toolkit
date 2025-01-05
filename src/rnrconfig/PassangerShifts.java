/*
 * Decompiled with CFR 0.151.
 */
package rnrconfig;

import java.util.HashMap;
import rnrcore.vectorJ;

public class PassangerShifts {
    private static PassangerShifts instance = new PassangerShifts();
    private HashMap<String, vectorJ> m_shifts = new HashMap();

    public static PassangerShifts getInstance() {
        return instance;
    }

    public vectorJ getShift(String prefix) {
        if (!this.m_shifts.containsKey(prefix)) {
            return new vectorJ(0.0, 0.0, -0.839);
        }
        return this.m_shifts.get(prefix);
    }

    private PassangerShifts() {
        this.m_shifts.put("Freight_Argosy_", new vectorJ(1.3447, 0.15, -0.839));
        this.m_shifts.put("Freight_Cor_", new vectorJ(1.0204, 0.22, -0.839));
        this.m_shifts.put("Freight_ClassicXL_", new vectorJ(0.857, 0.15, -0.839));
        this.m_shifts.put("Freight_Century_", new vectorJ(1.0191, 0.15, -0.839));
        this.m_shifts.put("Freight_Century_Sport_", new vectorJ(1.0191, 0.15, -0.839));
        this.m_shifts.put("Kenworth_T600_", new vectorJ(0.8148, 0.15, -0.839));
        this.m_shifts.put("Kenworth_T800_", new vectorJ(0.80955, 0.15, -0.839));
        this.m_shifts.put("Kenworth_W900_", new vectorJ(0.81113, 0.15, -0.839));
        this.m_shifts.put("Kenworth_Sport_", new vectorJ(0.81113, 0.15, -0.839));
        this.m_shifts.put("KenworthT2000_", new vectorJ(1.34486, 0.15, -0.8));
        this.m_shifts.put("Peterbilt_378_", new vectorJ(0.83, 0.15, -0.839));
        this.m_shifts.put("Peterbilt_379_", new vectorJ(0.851, 0.15, -0.839));
        this.m_shifts.put("Peterbilt_Sport_", new vectorJ(0.851, 0.15, -0.839));
        this.m_shifts.put("Peterbilt_387_", new vectorJ(1.14788, 0.15, -0.839));
        this.m_shifts.put("WesternStar_4900_", new vectorJ(1.04339, 0.25, -0.839));
        this.m_shifts.put("WesternStar_Sport_", new vectorJ(1.04339, 0.25, -0.839));
        this.m_shifts.put("Sterling_9500_", new vectorJ(0.917157, 0.15, -0.839));
        this.m_shifts.put("Sterling_Sport_", new vectorJ(0.917157, 0.15, -0.839));
        this.m_shifts.put("Gepard_", new vectorJ(0.0, 0.0, -0.839));
    }
}

