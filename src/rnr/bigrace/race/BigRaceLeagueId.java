/*
 * Decompiled with CFR 0.151.
 */
package rnr.bigrace.race;

import rnrcore.loc;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public enum BigRaceLeagueId {
    SECOND_LEAGUE(2),
    FIRST_LEAGUE(1),
    PREMIER_LEAGUE(10),
    CHAMPIONS_LEAGUE(100),
    MONSTER_LEAGUE(1000);

    private int m_leagueNumber;

    private BigRaceLeagueId(int leagueNumber) {
        this.m_leagueNumber = leagueNumber;
    }

    public int getId() {
        return this.m_leagueNumber;
    }

    public String getLocId() {
        if (this.getId() == MONSTER_LEAGUE.getId()) {
            return loc.getMENUString("common\\BigRacewLeague - MONSTER CUP");
        }
        if (this.getId() == CHAMPIONS_LEAGUE.getId()) {
            return loc.getMENUString("common\\BigRacewLeague - CHAMPION LEAGUE");
        }
        if (this.getId() == PREMIER_LEAGUE.getId()) {
            return loc.getMENUString("common\\BigRacewLeague - PREMIERE LEAGUE");
        }
        if (this.getId() == FIRST_LEAGUE.getId()) {
            return loc.getMENUString("common\\BigRacewLeague - FIRST LEAGUE");
        }
        return loc.getMENUString("common\\BigRacewLeague - SECOND LEAGUE");
    }
}

