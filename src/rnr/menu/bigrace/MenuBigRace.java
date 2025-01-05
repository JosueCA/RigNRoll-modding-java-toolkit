/*
 * Decompiled with CFR 0.151.
 */
package rnr.menu.bigrace;

import menu.BalanceUpdater;
import menu.menues;
import menuscript.Converts;
import rnr.menu.bigrace.BigRaceMenuInvalidDataException;
import rnr.menu.filling.MenuFilling;

final class MenuBigRace {
    static final String UI_ELEMENT_NAME_RACE_NAME = "Race NAME - TITLE";
    static final String UI_ELEMENT_NAME_RACE_STAGE = "RaceStage - SYMBOL";
    static final String UI_ELEMENT_YES_BUTTON = "BUTTON - YES";
    static final String UI_ELEMENT_OK_BUTTON = "BUTTON - OK";
    static final String UI_ELEMENT_NO_BUTTON = "BUTTON - NO";
    static final int LEAGUES_COUNT = 5;
    static final String[] STAGES_L10N_IDS = new String[]{"common\\BigRaceStage - STAGE I - SYMBOL", "common\\BigRaceStage - STAGE II - SYMBOL", "common\\BigRaceStage - STAGE III - SYMBOL"};
    private static final String MENU_LAYOUT_FILES_PREFIX = "..\\data\\config\\menu\\menu_race";
    private final String menuId;
    private final String controlGroup;
    private final String xmlFileWithMenu;
    private long balanceControlHandle = 0L;
    private long[] allControlsHandles = null;
    private final MenuFilling menuFilling;

    static void checkLeagueId(int raceId) throws BigRaceMenuInvalidDataException {
        if (0 > raceId || 5 <= raceId) {
            throw new BigRaceMenuInvalidDataException(raceId + " is not valid race uid");
        }
    }

    static void checkStageNumber(int stageNumber) throws BigRaceMenuInvalidDataException {
        if (0 > stageNumber || STAGES_L10N_IDS.length <= stageNumber) {
            throw new BigRaceMenuInvalidDataException(stageNumber + " is not valid stage number");
        }
    }

    static void checkNotNull(Object reference) throws BigRaceMenuInvalidDataException {
        if (null == reference) {
            throw new BigRaceMenuInvalidDataException("one of arguments is null");
        }
    }

    MenuBigRace(int raceId, ControlGroupTemplate controlGroupTemplate, MenuIdTemplate menuIdTemplate, MenuFilling dataForMenu) {
        assert (null != controlGroupTemplate) : "'controlGroupTemplate' must be non-null reference";
        assert (null != menuIdTemplate) : "'menuIdTemplate' must be non-null reference";
        assert (null != dataForMenu) : "'dataForMenu' must be non-null reference";
        String bigRaceInfix = Converts.newBigRaceSuffixes(raceId);
        this.menuFilling = dataForMenu;
        this.controlGroup = controlGroupTemplate.instantiate(bigRaceInfix);
        this.menuId = menuIdTemplate.instantiate(bigRaceInfix);
        this.xmlFileWithMenu = MENU_LAYOUT_FILES_PREFIX + bigRaceInfix + ".xml";
    }

    String getXmlFileWithMenu() {
        return this.xmlFileWithMenu;
    }

    String getControlGroupName() {
        return this.controlGroup;
    }

    void create(long menuHandle) {
        this.allControlsHandles = menues.InitXml(menuHandle, this.xmlFileWithMenu, this.controlGroup);
        this.balanceControlHandle = menues.FindFieldInMenu(menuHandle, "Your balance - VALUE");
        BalanceUpdater.AddBalanceControl(this.balanceControlHandle);
        this.menuFilling.menuInitializing(menuHandle);
    }

    void finalizeCreation(long menuHandle) {
        this.menuFilling.menuInitialized(menuHandle);
    }

    void exit() {
        this.menuFilling.menuClosed();
        BalanceUpdater.RemoveBalanceControl(this.balanceControlHandle);
    }

    void changeControlsVisibility(boolean visible) {
        if (null != this.allControlsHandles) {
            for (long controlHandle : this.allControlsHandles) {
                menues.SetShowField(controlHandle, visible);
            }
        }
    }

    public long[] getAllControlsHandles() {
        return this.allControlsHandles;
    }

    String getMenuId() {
        return this.menuId;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static enum MenuIdTemplate {
        MENU_DEFAULTED("raceDefaulted%sMENU"),
        MENU_TOWED_AND_DEFAULTED("raceTowedDefaulted%sMENU"),
        MENU_TOWED_AND_DEFAULTED_ON_WAREHOUSE("raceTowedDefaultedIn%sMENU"),
        MENU_CANCELED("raceCanceled%sMENU"),
        MENU_DROP_OR_CONTINUE("raceDropOrContinue%sMENU"),
        MENU_SEMITRAILER_LOST("raceSemitrailerLost%sMENU"),
        MENU_GOLD_MEDAL("raceWinGold%sMENU"),
        MENU_SILVER_MEDAL("raceWinSilver%sMENU"),
        MENU_BRONZE_MEDAL("raceWinBronze%sMENU"),
        MENU_FINISHED("raceFinish%sMENU"),
        MENU_RACE_SUMMARY("raceSummary%sMENU");

        private final String menuIdTemplate;

        private MenuIdTemplate(String menuIdTemplate) {
            this.menuIdTemplate = menuIdTemplate;
        }

        String instantiate(String infix) {
            return String.format(this.menuIdTemplate, infix);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static enum ControlGroupTemplate {
        GROUP_DEFAULTED("Panel - Race%s - DEFAULTED"),
        GROUP_TOWED_AND_DEFAULTED("Panel - Race%s - TOWED AND DEFAULTED"),
        GROUP_DAMAGED("Panel - Race%s - DAMAGED - OUT"),
        GROUP_CANCELED("Panel - Race%s - CANCELLED - OUT"),
        GROUP_CANCELED_ON_WAREHOUSE("Panel - Race%s - CANCELLED"),
        GROUP_TOWED_AND_DEFAULTED_ON_WAREHOUSE("Panel - RaceXX - TOWED AND DEFAULTED - IN"),
        GROUP_DROP_OR_CONTINUE("Panel - Race%s - DROP OR CONTINUE"),
        GROUP_SEMITRAILER_LOST("Panel - Race%s - SEMITRAILER LOST"),
        GROUP_FINISHED_1ST("Panel - Race%s - GRAND PRIX"),
        GROUP_FINISHED_2ND("Panel - Race%s - SILVER CUP"),
        GROUP_FINISHED_3RD("Panel - Race%s - BRONZE CUP"),
        GROUP_FINISHED("Panel - Race%s - FINISHED"),
        GROUP_RACE_SUMMARY("Panel - Race%s - SUMMARY REPORT");

        private final String controlGroupTemplate;

        private ControlGroupTemplate(String controlGroupTemplate) {
            this.controlGroupTemplate = controlGroupTemplate;
        }

        String instantiate(String infix) {
            return String.format(this.controlGroupTemplate, infix);
        }
    }
}

