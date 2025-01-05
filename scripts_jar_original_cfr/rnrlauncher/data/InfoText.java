/*
 * Decompiled with CFR 0.151.
 */
package rnrlauncher.data;

public final class InfoText {
    private final String name;
    private final String userSystemInfo;
    private final String requiredSystemInfo;
    private final String resulutionInfo;
    private final int status;

    public InfoText(String userSystemInfo, String requiredSystemInfo, String resulutionInfo, int status, String name) {
        this.userSystemInfo = userSystemInfo;
        this.requiredSystemInfo = requiredSystemInfo;
        this.resulutionInfo = resulutionInfo;
        this.status = status;
        this.name = name;
    }

    public String getUserSystemInfo() {
        return this.userSystemInfo;
    }

    public String getRequiredSystemInfo() {
        return this.requiredSystemInfo;
    }

    public String getResulutionInfo() {
        return this.resulutionInfo;
    }

    public int getStatus() {
        return this.status;
    }

    public String getName() {
        return this.name;
    }
}

