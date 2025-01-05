/*
 * Decompiled with CFR 0.151.
 */
package rnr.menu.bigrace;

import java.util.logging.Logger;
import menu.MENUsimplebutton_field;
import menu.menues;
import menuscript.Converts;
import rnr.menu.bigrace.BigRaceMenuInvalidDataException;
import rnr.menu.bigrace.MenuBigRace;
import rnr.menu.bigrace.PanelMenuBigRace;
import rnr.menu.filling.MenuFilling;
import rnr.menu.filling.custom.bigrace.FillerBigRaceLogo;
import rnr.menu.filling.general.FillerButtonClickCallback;
import rnr.menu.filling.general.FillerCloseMenuOnButtonClick;
import rnr.menu.filling.general.FillerHideTextField;
import rnr.menu.filling.general.FillerSetFocusOnButton;
import rnr.menu.filling.general.FillerTextField;
import rnr.menu.filling.general.FillerTextFieldSubstitution;
import rnr.tech.Code1;
import rnr.tech.Code2;
import rnr.tech.mutable.MutableInteger;
import rnrcore.loc;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class PanelMenuFactoryBigRaceFail {
    private static final String UI_ELEMENT_NAME_PARTICIPATION_FEE = "Participation Fee - VALUE";
    private static final String UI_ELEMENT_NAME_TOW_AWAY_FEE = "Tow-away fee - VALUE";
    private static final Logger logger = Logger.getLogger("rnr.menu.bigrace");

    private PanelMenuFactoryBigRaceFail() {
    }

    private static MenuFilling createCommonMenuFilling(String raceName, int stageNumber, int participationFee, boolean withoutSemitrailer) {
        MenuFilling filling = new MenuFilling();
        filling.addFillerToBeCalledOnMenuInit(new FillerTextField("Race NAME - TITLE", loc.getBigraceShortName(raceName)));
        filling.addFillerToBeCalledOnMenuInit(new FillerTextFieldSubstitution(UI_ELEMENT_NAME_PARTICIPATION_FEE, "PARTICIPATION_FEE", Converts.ConvertNumeric(participationFee)));
        filling.addFillerToBeCalledOnMenuInit(new FillerTextField("RaceStage - SYMBOL", loc.getMENUString(MenuBigRace.STAGES_L10N_IDS[stageNumber])));
        filling.addFillerToBeCalledOnMenuInit(new FillerCloseMenuOnButtonClick("BUTTON - OK"));
        filling.addFillerToBeCalledOnMenuInitCompletion(new FillerSetFocusOnButton("BUTTON - OK"));
        filling.addFillerToBeCalledOnMenuInitCompletion(new FillerBigRaceLogo(raceName));
        if (withoutSemitrailer) {
            filling.addFillerToBeCalledOnMenuInitCompletion(new FillerHideTextField("Deliver the semitrailer - 01"));
        }
        return filling;
    }

    private static long createRaceFailMenu(int leagueId, String raceName, int stageNumber, int participationFee, boolean withoutSemitrailer, MenuBigRace.ControlGroupTemplate controlGroupTemplate, MenuBigRace.MenuIdTemplate menuIdTemplate, Code1<MenuFilling> fillingCustomization) {
        try {
            MenuBigRace.checkLeagueId(leagueId);
            MenuBigRace.checkStageNumber(stageNumber);
            MenuBigRace.checkNotNull(raceName);
            MenuBigRace.checkNotNull((Object)controlGroupTemplate);
            MenuBigRace.checkNotNull((Object)menuIdTemplate);
            MenuFilling dataToBeShown = PanelMenuFactoryBigRaceFail.createCommonMenuFilling(raceName, stageNumber, participationFee, withoutSemitrailer);
            if (null != fillingCustomization) {
                fillingCustomization.execute(dataToBeShown);
            }
            PanelMenuBigRace menu = new PanelMenuBigRace(leagueId, controlGroupTemplate, menuIdTemplate, dataToBeShown);
            return menu.getHandle();
        }
        catch (BigRaceMenuInvalidDataException e) {
            logger.severe("BigRacePanelMenuFactory.createRaceFailMenu: " + e.getMessage());
            return 0L;
        }
    }

    public static long createMenuPanelDefaulted(int leagueId, int stageNumber, String raceName, int participationFee, boolean isPlayerWithoutSemitrailer) {
        return PanelMenuFactoryBigRaceFail.createRaceFailMenu(leagueId, raceName, stageNumber, participationFee, isPlayerWithoutSemitrailer, MenuBigRace.ControlGroupTemplate.GROUP_DEFAULTED, MenuBigRace.MenuIdTemplate.MENU_DEFAULTED, null);
    }

    public static long createMenuPanelDamaged(int leagueId, int stageNumber, String raceName, int participationFee, boolean isPlayerWithoutSemitrailer) {
        return PanelMenuFactoryBigRaceFail.createRaceFailMenu(leagueId, raceName, stageNumber, participationFee, isPlayerWithoutSemitrailer, MenuBigRace.ControlGroupTemplate.GROUP_DAMAGED, MenuBigRace.MenuIdTemplate.MENU_DEFAULTED, null);
    }

    public static long createMenuPanelCancelled(int leagueId, int stageNumber, String raceName, int participationFee, boolean isPlayerWithoutSemitrailer) {
        return PanelMenuFactoryBigRaceFail.createRaceFailMenu(leagueId, raceName, stageNumber, participationFee, isPlayerWithoutSemitrailer, MenuBigRace.ControlGroupTemplate.GROUP_CANCELED, MenuBigRace.MenuIdTemplate.MENU_CANCELED, null);
    }

    public static long createMenuPanelCancelledOnWarehouse(int leagueId, int stageNumber, String raceName, int participationFee, boolean isPlayerWithoutSemitrailer) {
        return PanelMenuFactoryBigRaceFail.createRaceFailMenu(leagueId, raceName, stageNumber, participationFee, isPlayerWithoutSemitrailer, MenuBigRace.ControlGroupTemplate.GROUP_CANCELED_ON_WAREHOUSE, MenuBigRace.MenuIdTemplate.MENU_CANCELED, null);
    }

    public static long createMenuPanelTowedAndDefaulted(int leagueId, int stageNumber, String raceName, int towingFee, int participationFee, boolean isPlayerWithoutSemitrailer) {
        return PanelMenuFactoryBigRaceFail.createMenuPanelTowedAndDefaulted(MenuBigRace.ControlGroupTemplate.GROUP_TOWED_AND_DEFAULTED, MenuBigRace.MenuIdTemplate.MENU_TOWED_AND_DEFAULTED, leagueId, stageNumber, raceName, towingFee, participationFee, isPlayerWithoutSemitrailer);
    }

    public static long createMenuPanelTowedAndDefaultedOnWarehouse(int leagueId, int stageNumber, String raceName, int towingFee, int participationFee, boolean isPlayerWithoutSemitrailer) {
        return PanelMenuFactoryBigRaceFail.createMenuPanelTowedAndDefaulted(MenuBigRace.ControlGroupTemplate.GROUP_TOWED_AND_DEFAULTED_ON_WAREHOUSE, MenuBigRace.MenuIdTemplate.MENU_TOWED_AND_DEFAULTED_ON_WAREHOUSE, leagueId, stageNumber, raceName, towingFee, participationFee, isPlayerWithoutSemitrailer);
    }

    private static long createMenuPanelTowedAndDefaulted(MenuBigRace.ControlGroupTemplate controlsGroup, MenuBigRace.MenuIdTemplate menu, int leagueId, int stageNumber, String raceName, final int towingFee, int participationFee, boolean isPlayerWithoutSemitrailer) {
        Code1<MenuFilling> menuFillingCustomization = new Code1<MenuFilling>(){

            @Override
            public void execute(MenuFilling argument) {
                String towRateFormatted = Converts.ConvertNumeric(towingFee >= 0 ? towingFee : -towingFee);
                argument.addFillerToBeCalledOnMenuInit(new FillerTextFieldSubstitution(PanelMenuFactoryBigRaceFail.UI_ELEMENT_NAME_TOW_AWAY_FEE, "TOW_AWAY_FEE", towRateFormatted));
            }
        };
        return PanelMenuFactoryBigRaceFail.createRaceFailMenu(leagueId, raceName, stageNumber, participationFee, isPlayerWithoutSemitrailer, controlsGroup, menu, menuFillingCustomization);
    }

    public static MutableInteger createMenuPanelDropOrContinue(int leagueId, int stageNumber, String raceName) {
        return PanelMenuFactoryBigRaceFail.createSimpleYesNoPanel(MenuBigRace.ControlGroupTemplate.GROUP_DROP_OR_CONTINUE, MenuBigRace.MenuIdTemplate.MENU_DROP_OR_CONTINUE, leagueId, MenuBigRace.STAGES_L10N_IDS[stageNumber], raceName);
    }

    public static MutableInteger createMenuPanelSemitrailerLost(int leagueId, int stageNumber, String raceName) {
        return PanelMenuFactoryBigRaceFail.createSimpleYesNoPanel(MenuBigRace.ControlGroupTemplate.GROUP_SEMITRAILER_LOST, MenuBigRace.MenuIdTemplate.MENU_SEMITRAILER_LOST, leagueId, MenuBigRace.STAGES_L10N_IDS[stageNumber], raceName);
    }

    private static MutableInteger createSimpleYesNoPanel(MenuBigRace.ControlGroupTemplate controlsGroup, MenuBigRace.MenuIdTemplate menuId, int leagueId, String stagesL10nId, String raceName) {
        MenuFilling filling = new MenuFilling();
        filling.addFillerToBeCalledOnMenuInit(new FillerTextField("Race NAME - TITLE", loc.getBigraceShortName(raceName)));
        filling.addFillerToBeCalledOnMenuInit(new FillerTextField("RaceStage - SYMBOL", loc.getMENUString(stagesL10nId)));
        final MutableInteger buttonChosen = new MutableInteger();
        filling.addFillerToBeCalledOnMenuInit(new FillerButtonClickCallback("BUTTON - YES", new Code2<Long, MENUsimplebutton_field>(){

            @Override
            public void execute(Long menuHandle, MENUsimplebutton_field sender) {
                buttonChosen.setValue(1);
                menues.CallMenuCallBack_ExitMenu(menuHandle);
            }
        }));
        filling.addFillerToBeCalledOnMenuInit(new FillerButtonClickCallback("BUTTON - NO", new Code2<Long, MENUsimplebutton_field>(){

            @Override
            public void execute(Long menuHandle, MENUsimplebutton_field sender) {
                buttonChosen.setValue(0);
                menues.CallMenuCallBack_ExitMenu(menuHandle);
            }
        }));
        new PanelMenuBigRace(leagueId, controlsGroup, menuId, filling);
        return buttonChosen;
    }
}

