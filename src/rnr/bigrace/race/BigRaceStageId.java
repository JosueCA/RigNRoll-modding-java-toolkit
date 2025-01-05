/*
 * Decompiled with CFR 0.151.
 */
package rnr.bigrace.race;

import rnrcore.loc;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public enum BigRaceStageId {
    QUALIFYING_STAGE(1),
    SEMIFINAL_STAGE(2),
    FINAL_STAGE(3);

    private int m_stageId;

    private BigRaceStageId(int stageId) {
        this.m_stageId = stageId;
    }

    public int getId() {
        return this.m_stageId;
    }

    public String getLocId() {
        if (this.getId() == FINAL_STAGE.getId()) {
            return loc.getMENUString("common\\BigRaceStage - STAGE III - TEXT - CaseSmall - Short");
        }
        if (this.getId() == SEMIFINAL_STAGE.getId()) {
            return loc.getMENUString("common\\BigRaceStage - STAGE II - TEXT - CaseSmall - Short");
        }
        return loc.getMENUString("common\\BigRaceStage - STAGE I - TEXT - CaseSmall - Short");
    }
}

