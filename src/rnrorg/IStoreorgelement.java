// Decompiled with: CFR 0.152
// Class Version: 5
package rnrorg;

import rnrcore.CoreTime;
import rnrcore.vectorJ;
import rnrorg.IDeclineOrgListener;
import rnrorg.INPC;
import rnrorg.Ireadelement;
import rnrorg.RewardForfeit;

public interface IStoreorgelement
extends Ireadelement {
    public static final int MONEY_REWARD = 1;
    public static final int RATING_REWARD = 4;
    public static final int INFORMATION_REWARD = 8;

    public Type getType();

    public Status getStatus();

    public boolean isImportant();

    public String getName();

    public String getDescription();

    public String getDescriptionRef();

    public String getRaceName();

    public String getLogoName();

    public int getStageID();

    public void setStageID(int var1);

    public String loadPoint();

    public String endPoint();

    public vectorJ pos_start();

    public vectorJ pos_load();

    public vectorJ pos_complete();

    public int getRewardFlag();

    public int getForfeitFlag();

    public RewardForfeit getReward();

    public RewardForfeit getForfeit();

    public INPC getCustomer();

    public CoreTime dateOfRequest();

    public CoreTime timeToPickUp();

    public CoreTime timeToComplete();

    public int getCargoFragility();

    public int get_minutes_toFail();

    public int get_seconds_toFail();

    public int getMissionState();

    public void start();

    public void finish();

    public void fail(int var1);

    public void updateTimeToComplete(int var1, int var2, int var3, String var4);

    public void updateStatus();

    public void decline();

    public void addDeclineListener(IDeclineOrgListener var1);

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum Type {
        notype(false),
        baseDelivery,
        trailerObjectDelivery,
        competition,
        tender,
        trailerClientDelivery,
        pakageDelivery,
        passangerDelivery,
        visit,
        bigrace0_announce(true),
        bigrace1_announce(true),
        bigrace2_announce(true),
        bigrace3_announce(true),
        bigrace4_announce(true),
        bigrace0_semi,
        bigrace1_semi,
        bigrace2_semi,
        bigrace3_semi,
        bigrace4_semi,
        bigrace0,
        bigrace1,
        bigrace2,
        bigrace3,
        bigrace4;

        private boolean mIsSpecialType = false;

        private Type() {
        }

        private Type(boolean isSpecialType) {
            this.mIsSpecialType = isSpecialType;
        }

        public boolean isSpecialType() {
            return this.mIsSpecialType;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum Status {
        nostatus,
        failedMission,
        executedMission,
        urgentMission,
        pendingMission;

    }
}
