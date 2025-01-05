/*
 * Decompiled with CFR 0.151.
 */
package rnr.menu.bigrace.data;

import menu.TimeData;

public final class PlayerStatisticsForWinPanel {
    private final int moneyPrize;
    private final double maxSpeed;
    private final double avgSpeed;
    private final double d_ratingDelta;
    private final double d_rating;
    private final boolean playerMovedToTheNextStage;
    private final TimeData raceTime;

    public PlayerStatisticsForWinPanel(int moneyPrize, double maxSpeed, double avgSpeed, double rating, double ratingDelta, TimeData raceTime, boolean playerMovedToTheNextStage) {
        this.moneyPrize = moneyPrize;
        this.maxSpeed = maxSpeed;
        this.avgSpeed = avgSpeed;
        this.d_ratingDelta = ratingDelta;
        this.d_rating = rating;
        this.raceTime = raceTime;
        this.playerMovedToTheNextStage = playerMovedToTheNextStage;
    }

    public static PlayerStatisticsForWinPanel create(int moneyPrize, double maxSpeed, double avgSpeed, double rating, double ratingDelta, TimeData raceTime, boolean playerMovedToTheNextStage) {
        return new PlayerStatisticsForWinPanel(moneyPrize, maxSpeed, avgSpeed, rating, ratingDelta, raceTime, playerMovedToTheNextStage);
    }

    public boolean isPlayerMovedToTheNextStage() {
        return this.playerMovedToTheNextStage;
    }

    public int getMoneyPrize() {
        return this.moneyPrize;
    }

    public double getMaxSpeed() {
        return this.maxSpeed;
    }

    public double getAvgSpeed() {
        return this.avgSpeed;
    }

    public double getRatingDelta() {
        return this.d_ratingDelta;
    }

    public double getRating() {
        return this.d_rating;
    }

    public TimeData getRaceTime() {
        return this.raceTime;
    }
}

