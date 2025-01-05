/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import rnrcore.SCRuniperson;

public final class SODialogParams
implements Comparable {
    private SCRuniperson npcModel;
    private String description;
    private int id;
    private boolean played;
    private boolean isFinishmIssionDialog;
    private String identitie;
    private String pointName;
    private String missionName;

    public SODialogParams(String identitie, SCRuniperson npcModel, String description, int id, boolean played, boolean isFinishmIssionDialog, String pointName, String missionName) {
        this.npcModel = npcModel;
        this.description = description;
        this.id = id;
        this.played = played;
        this.isFinishmIssionDialog = isFinishmIssionDialog;
        this.identitie = identitie;
        this.pointName = pointName;
        this.missionName = missionName;
    }

    public SCRuniperson getNpcModel() {
        return this.npcModel;
    }

    public String getDescription() {
        return this.description;
    }

    public int compareTo(Object other) {
        int otherId = ((SODialogParams)other).id;
        if (this.id < otherId) {
            return -1;
        }
        if (this.id > otherId) {
            return 1;
        }
        return 0;
    }

    public boolean wasPlayed() {
        return this.played;
    }

    public void play() {
        this.played = true;
    }

    public boolean isfinishDialog() {
        return this.isFinishmIssionDialog;
    }

    public String getIdentitie() {
        return this.identitie;
    }

    public String getMissionName() {
        return this.missionName;
    }

    public String getPointName() {
        return this.pointName;
    }
}

