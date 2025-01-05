/*
 * Decompiled with CFR 0.151.
 */
package rnr.menu.bigrace;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import menu.KeyPair;
import menu.MENUText_field;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.TableCallbacksAdapter;
import menu.TableNode;
import menu.TimeData;
import menuscript.Converts;
import rnr.menu.bigrace.BigRaceMenuInvalidDataException;
import rnr.menu.bigrace.MenuBigRace;
import rnr.menu.bigrace.PanelMenuBigRace;
import rnr.menu.bigrace.PopupMenuBigRace;
import rnr.menu.bigrace.data.PlayerStatisticsForWinPanel;
import rnr.menu.bigrace.data.RaceParticipantStatistics;
import rnr.menu.filling.MenuFilling;
import rnr.menu.filling.custom.bigrace.FillerBigRaceLogo;
import rnr.menu.filling.general.ControlsRenamer;
import rnr.menu.filling.general.FillerButtonClickCallback;
import rnr.menu.filling.general.FillerCloseMenuOnButtonClick;
import rnr.menu.filling.general.FillerHideTextField;
import rnr.menu.filling.general.FillerSetFocusOnButton;
import rnr.menu.filling.general.FillerTable;
import rnr.menu.filling.general.FillerTextField;
import rnr.menu.filling.general.FillerTextFieldMultipleSubstitution;
import rnr.menu.filling.general.FillerTextFieldSubstitution;
import rnr.menu.filling.general.FillerTextFieldWithTime;
import rnr.tech.Code0;
import rnr.tech.Code1;
import rnr.tech.Code2;
import rnr.tech.Function0;
import rnrcore.eng;
import rnrcore.loc;
import scenarioUtils.Pair;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class PanelMenuFactoryBigRaceSuccess {
    private static final String ROOT_MENU_CONTROLS_SUFFIX = "rootMenu";
    private static final String UI_ELEMENT_NAME_MAX_SPEED = "Top Speed - VALUE";
    private static final String UI_ELEMENT_NAME_AVG_SPEED = "Average Speed - VALUE";
    private static final String UI_ELEMENT_NAME_TIME = "Total time - VALUE";
    private static final String UI_ELEMENT_NAME_MONEY_PRIZE = "Prize Money - VALUE";
    private static final String UI_ELEMENT_MOVED_TO_NEXT_STAGE_ANNOUNCE = "GO NEXT BIGRACE";
    private static final int PRIZE_WINNING_PLACES_COUNT = 3;
    private static final Logger logger = Logger.getLogger("rnr.menu.bigrace");
    private static final String[] L10N_ID_PART_LEAGUE = new String[]{"Monsters League", "Champion League", "Premiere League", "First League", "Second League"};
    private static final String[] L10N_ID_PART_STAGE = new String[]{"Qualify", "Semifinal", "Final"};
    private static final int COMMON_PRECISION = 2;
    private static final int RACE_STATISTICS_TABLE_ROW_HEIGHT = 38;
    private static final int RACE_STATISTICS_TABLE_ROWS_COUNT = 12;

    private PanelMenuFactoryBigRaceSuccess() {
    }

    private static String rootMenuControlName(String baseName) {
        return null == baseName ? ROOT_MENU_CONTROLS_SUFFIX : baseName + ROOT_MENU_CONTROLS_SUFFIX;
    }

    private static String getTextFieldForRatingName(int leagueId) {
        return "Rating - VALUE - League" + Converts.newBigRaceSuffixes(leagueId);
    }

    private static long createMenuPanelSuccess(MenuBigRace.ControlGroupTemplate uiControlGroup, MenuBigRace.MenuIdTemplate uiMenu, RaceInfo raceInfo2, PlayerStatisticsForWinPanel playerStatistics, RaceParticipantStatistics[] participantsStatistics, Code1<MenuFilling> fillingCustomization) {
        assert (null != uiControlGroup) : "'uiControlGroup' must be non-null reference";
        assert (null != uiMenu) : "'uiMenu' must be non-null reference";
        assert (null != raceInfo2) : "'raceInfo' must be non-null reference";
        try {
            MenuBigRace.checkLeagueId(raceInfo2.getLeagueId());
            MenuBigRace.checkStageNumber(raceInfo2.getStageNumber());
            MenuBigRace.checkNotNull(raceInfo2.getRaceName());
            MenuBigRace.checkNotNull(playerStatistics);
            MenuBigRace.checkNotNull(participantsStatistics);
            String stageLocalizedName = loc.getMENUString(MenuBigRace.STAGES_L10N_IDS[raceInfo2.getStageNumber()]);
            MenuFilling filling = new MenuFilling();
            final PanelMenuBigRace menu = new PanelMenuBigRace(raceInfo2.getLeagueId(), uiControlGroup, uiMenu, filling);
            filling.addFillerToBeCalledOnMenuInit(new ControlsRenamer(ROOT_MENU_CONTROLS_SUFFIX, menu.getControlGroupName(), new Function0<long[]>(){

                @Override
                public long[] execute() {
                    return menu.getControlsHandles();
                }
            }));
            filling.addFillerToBeCalledOnMenuInit(new FillerTextField(PanelMenuFactoryBigRaceSuccess.rootMenuControlName("Race NAME - TITLE"), loc.getBigraceShortName(raceInfo2.getRaceName())));
            filling.addFillerToBeCalledOnMenuInitCompletion(new FillerBigRaceLogo(PanelMenuFactoryBigRaceSuccess.rootMenuControlName("THE RACE LOGOTYPE"), raceInfo2.getRaceName()));
            filling.addFillerToBeCalledOnMenuInit(new FillerTextField(PanelMenuFactoryBigRaceSuccess.rootMenuControlName("RaceStage - SYMBOL"), stageLocalizedName));
            filling.addFillerToBeCalledOnMenuInit(new FillerTextFieldSubstitution(PanelMenuFactoryBigRaceSuccess.rootMenuControlName(UI_ELEMENT_NAME_MAX_SPEED), "TOP_SPEED", Converts.ConvertDouble(playerStatistics.getMaxSpeed(), 2)));
            filling.addFillerToBeCalledOnMenuInit(new FillerTextFieldSubstitution(PanelMenuFactoryBigRaceSuccess.rootMenuControlName(UI_ELEMENT_NAME_AVG_SPEED), "AVERAGE_SPEED", Converts.ConvertDouble(playerStatistics.getAvgSpeed(), 2)));
            filling.addFillerToBeCalledOnMenuInit(new FillerTextFieldWithTime(PanelMenuFactoryBigRaceSuccess.rootMenuControlName(UI_ELEMENT_NAME_TIME), "TOTAL_TIME", playerStatistics.getRaceTime()));
            double ratingDelta = playerStatistics.getRatingDelta();
            double rating = playerStatistics.getRating();
            String textFieldWithRatingName = PanelMenuFactoryBigRaceSuccess.rootMenuControlName(PanelMenuFactoryBigRaceSuccess.getTextFieldForRatingName(eng.visualLeague()));
            filling.addFillerToBeCalledOnMenuInit(new FillerTextFieldMultipleSubstitution(textFieldWithRatingName, new Pair<String, String>("RATING", Converts.ConvertRating(eng.visualRating(ratingDelta))), new Pair<String, String>("RATING_ABSOLUT", Converts.ConvertRating(eng.visualRating(rating))), new Pair<String, String>("SIGN", Character.toString((char)(0.0 == ratingDelta ? 32 : (0.0 < ratingDelta ? 43 : 45))))));
            final ArrayList<FillerHideTextField> unnecessaryTextFieldHiders = new ArrayList<FillerHideTextField>();
            for (int i = 0; i < 5; ++i) {
                if (i == eng.visualLeague()) continue;
                FillerHideTextField hider = new FillerHideTextField(PanelMenuFactoryBigRaceSuccess.rootMenuControlName(PanelMenuFactoryBigRaceSuccess.getTextFieldForRatingName(i)));
                unnecessaryTextFieldHiders.add(hider);
                filling.addFillerToBeCalledOnMenuInitCompletion(hider);
            }
            if ((0 != raceInfo2.getLeagueId() || L10N_ID_PART_STAGE.length - 1 != raceInfo2.getStageNumber()) && playerStatistics.isPlayerMovedToTheNextStage()) {
                String l10nIdPartLeague = L10N_ID_PART_LEAGUE[raceInfo2.getLeagueId() - (raceInfo2.getStageNumber() + 1) / L10N_ID_PART_STAGE.length];
                String l10nIdPartStage = L10N_ID_PART_STAGE[(raceInfo2.getStageNumber() + 1) % L10N_ID_PART_STAGE.length];
                String gameAdvanceMessageL10nId = String.format("common\\BigRaceMessage - %s - %s", l10nIdPartLeague, l10nIdPartStage);
                filling.addFillerToBeCalledOnMenuInit(new FillerTextField(PanelMenuFactoryBigRaceSuccess.rootMenuControlName(UI_ELEMENT_MOVED_TO_NEXT_STAGE_ANNOUNCE), loc.getMENUString(gameAdvanceMessageL10nId)));
            } else {
                FillerHideTextField field = new FillerHideTextField(PanelMenuFactoryBigRaceSuccess.rootMenuControlName(UI_ELEMENT_MOVED_TO_NEXT_STAGE_ANNOUNCE));
                filling.addFillerToBeCalledOnMenuInitCompletion(field);
                unnecessaryTextFieldHiders.add(field);
            }
            final PopupMenuBigRace popup = PanelMenuFactoryBigRaceSuccess.createMenuPopupRaceStatistics(raceInfo2.getLeagueId(), raceInfo2.getStageNumber(), raceInfo2.getRaceName(), participantsStatistics, new Code0(){

                public void execute() {
                    menu.show();
                    for (FillerHideTextField hider : unnecessaryTextFieldHiders) {
                        hider.fillFieldOfMenu(menu.getHandle());
                    }
                }
            });
            filling.addFillerToBeCalledOnMenuInit(new FillerButtonClickCallback(PanelMenuFactoryBigRaceSuccess.rootMenuControlName("BUTTON - RACE SUMMARY"), new Code2<Long, MENUsimplebutton_field>(){

                @Override
                public void execute(Long menuHandle, MENUsimplebutton_field sender) {
                    menu.hide();
                    popup.show();
                }
            }));
            filling.addFillerToBeCalledOnMenuInit(new FillerCloseMenuOnButtonClick(PanelMenuFactoryBigRaceSuccess.rootMenuControlName("BUTTON - OK")));
            filling.addFillerToBeCalledOnMenuInitCompletion(new FillerSetFocusOnButton(PanelMenuFactoryBigRaceSuccess.rootMenuControlName("BUTTON - OK")));
            fillingCustomization.execute(filling);
            menu.addCreationListener(popup);
            return menu.getHandle();
        }
        catch (BigRaceMenuInvalidDataException e) {
            logger.severe("BigRacePanelMenuFactory.createMenuPanelWin: " + e.getMessage());
            return 0L;
        }
    }

    public static long createMenuPanelWin(int playerPlace, int leagueId, int stageNumber, String raceName, final PlayerStatisticsForWinPanel playerStatistics, List<RaceParticipantStatistics> participantsStatistics) {
        if (null == playerStatistics || null == participantsStatistics) {
            logger.severe("BigRacePanelMenuFactory.createMenuPanelWin: 'playerStatistics' or 'participantsStatistics' is null");
            return 0L;
        }
        if (1 > playerPlace || 3 < playerPlace) {
            logger.severe("BigRacePanelMenuFactory.createMenuPanelWin: " + playerPlace + " is not valid Big Race prize-winning place");
            return 0L;
        }
        int uiControlGroupIndex = MenuBigRace.ControlGroupTemplate.GROUP_FINISHED_1ST.ordinal() + --playerPlace;
        MenuBigRace.ControlGroupTemplate uiControlGroup = MenuBigRace.ControlGroupTemplate.values()[uiControlGroupIndex];
        int uiMenuIndex = MenuBigRace.MenuIdTemplate.MENU_GOLD_MEDAL.ordinal() + playerPlace;
        MenuBigRace.MenuIdTemplate uiMenu = MenuBigRace.MenuIdTemplate.values()[uiMenuIndex];
        RaceInfo raceInfo2 = new RaceInfo(leagueId, stageNumber, raceName);
        RaceParticipantStatistics[] array = new RaceParticipantStatistics[participantsStatistics.size()];
        return PanelMenuFactoryBigRaceSuccess.createMenuPanelSuccess(uiControlGroup, uiMenu, raceInfo2, playerStatistics, participantsStatistics.toArray(array), new Code1<MenuFilling>(){

            @Override
            public void execute(MenuFilling filling) {
                filling.addFillerToBeCalledOnMenuInit(new FillerTextFieldSubstitution(PanelMenuFactoryBigRaceSuccess.rootMenuControlName(PanelMenuFactoryBigRaceSuccess.UI_ELEMENT_NAME_MONEY_PRIZE), "MONEY_GRAND_PRIX", Converts.ConvertNumeric(playerStatistics.getMoneyPrize())));
            }
        });
    }

    public static long createMenuPanelFinished(final int playerPlace, int leagueId, int stageNumber, String raceName, PlayerStatisticsForWinPanel playerStatistics, List<RaceParticipantStatistics> participantsStatistics) {
        if (null == playerStatistics || null == participantsStatistics) {
            logger.severe("BigRacePanelMenuFactory.createMenuPanelWin: 'playerStatistics' or 'participantsStatistics' is null");
            return 0L;
        }
        RaceInfo raceInfo2 = new RaceInfo(leagueId, stageNumber, raceName);
        RaceParticipantStatistics[] array = new RaceParticipantStatistics[participantsStatistics.size()];
        return PanelMenuFactoryBigRaceSuccess.createMenuPanelSuccess(MenuBigRace.ControlGroupTemplate.GROUP_FINISHED, MenuBigRace.MenuIdTemplate.MENU_FINISHED, raceInfo2, playerStatistics, participantsStatistics.toArray(array), new Code1<MenuFilling>(){

            @Override
            public void execute(MenuFilling filling) {
                filling.addFillerToBeCalledOnMenuInit(new FillerTextField(PanelMenuFactoryBigRaceSuccess.rootMenuControlName("Place - VALUE"), Integer.toString(playerPlace)));
            }
        });
    }

    private static PopupMenuBigRace createMenuPopupRaceStatistics(int leagueId, int stageNumber, String raceName, RaceParticipantStatistics[] participantsStatistics, final Code0 callOnExit) {
        try {
            MenuBigRace.checkLeagueId(leagueId);
            MenuBigRace.checkStageNumber(stageNumber);
            MenuBigRace.checkNotNull(raceName);
            MenuBigRace.checkNotNull(participantsStatistics);
            MenuFilling filling = new MenuFilling();
            final PopupMenuBigRace menu = new PopupMenuBigRace(leagueId, MenuBigRace.ControlGroupTemplate.GROUP_RACE_SUMMARY, MenuBigRace.MenuIdTemplate.MENU_RACE_SUMMARY, filling);
            filling.addFillerToBeCalledOnMenuInitCompletion(new FillerTextField("Race NAME - TITLE", loc.getBigraceShortName(raceName)));
            filling.addFillerToBeCalledOnMenuInitCompletion(new FillerBigRaceLogo(raceName));
            String stageLocalizedName = loc.getMENUString(MenuBigRace.STAGES_L10N_IDS[stageNumber]);
            filling.addFillerToBeCalledOnMenuInitCompletion(new FillerTextField("RaceStage - SYMBOL", stageLocalizedName));
            filling.addFillerToBeCalledOnMenuInitCompletion(new FillerButtonClickCallback("BUTTON - OK", new Code2<Long, MENUsimplebutton_field>(){

                @Override
                public void execute(Long menuHandle, MENUsimplebutton_field sender) {
                    menu.hide();
                    if (null != callOnExit) {
                        callOnExit.execute();
                    }
                }
            }));
            filling.addFillerToBeCalledOnMenuInitCompletion(new FillerSetFocusOnButton(PanelMenuFactoryBigRaceSuccess.rootMenuControlName("BUTTON - OK")));
            FillerTable participantsTableFiller = new FillerTable("Summary", "TABLEGROUP - SUMMARY REPORT - 12 38", String.format("Panel - Race%s - SUMMARY REPORT - TABLE", Converts.newBigRaceSuffixes(leagueId)), menu.getXmlFileWithMenu(), 38, 12, new Pair<String, Integer>("Driver - VALUE", RaceStaticticsColumns.COLUMN_NAME.ordinal()), new Pair<String, Integer>(PanelMenuFactoryBigRaceSuccess.getTextFieldForRatingName(eng.visualLeague()), RaceStaticticsColumns.COLUMN_RATING.ordinal()), new Pair<String, Integer>("Place - VALUE", RaceStaticticsColumns.COLUMN_PLACE.ordinal()), new Pair<String, Integer>("Total Time - VALUE", RaceStaticticsColumns.COLUMN_TIME.ordinal()), new Pair<String, Integer>(UI_ELEMENT_NAME_AVG_SPEED, RaceStaticticsColumns.COLUMN_AVG_SPEED.ordinal()), new Pair<String, Integer>(UI_ELEMENT_NAME_MAX_SPEED, RaceStaticticsColumns.COLUMN_MAX_SPEED.ordinal()));
            participantsTableFiller.setTableItems(participantsStatistics);
            participantsTableFiller.setTableDataFiller(new TableCallbacksAdapter(){

                public void SetupLineInTable(TableNode node, MENUText_field text) {
                    RaceParticipantStatistics statictics = (RaceParticipantStatistics)node.item;
                    RaceStaticticsColumns column = RaceStaticticsColumns.values()[text.userid];
                    switch (column) {
                        case COLUMN_PLACE: {
                            text.text = Integer.toString(statictics.getPlaceOnFinish() + 1);
                            break;
                        }
                        case COLUMN_NAME: {
                            text.text = statictics.getName();
                            break;
                        }
                        case COLUMN_TIME: {
                            TimeData time = statictics.getTime();
                            text.text = Converts.ConverTime3Plus2Total(text.text, time.hours, time.minutes, time.seconds);
                            break;
                        }
                        case COLUMN_MAX_SPEED: {
                            text.text = MacroKit.Parse(text.text, new KeyPair[]{new KeyPair("TOP_SPEED", Converts.ConvertDouble(statictics.getMaxSpeed(), 0))});
                            break;
                        }
                        case COLUMN_AVG_SPEED: {
                            text.text = MacroKit.Parse(text.text, new KeyPair[]{new KeyPair("AVERAGE_SPEED", Converts.ConvertDouble(statictics.getAvgSpeed(), 0))});
                            break;
                        }
                        case COLUMN_RATING: {
                            double rating = statictics.getRating();
                            text.text = MacroKit.Parse(text.text, new KeyPair[]{new KeyPair("RATING", Converts.ConvertRating(eng.visualRating(rating)))});
                        }
                    }
                }
            });
            filling.addFillerToBeCalledOnMenuInit(participantsTableFiller);
            return menu;
        }
        catch (BigRaceMenuInvalidDataException e) {
            logger.severe("BigRacePanelMenuFactory.createMenuPanelRaceStatistics: " + e.getMessage());
            return null;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static enum RaceStaticticsColumns {
        COLUMN_PLACE,
        COLUMN_NAME,
        COLUMN_TIME,
        COLUMN_MAX_SPEED,
        COLUMN_AVG_SPEED,
        COLUMN_RATING;

    }

    private static final class RaceInfo {
        private final int leagueId;
        private final int stageNumber;
        private final String raceName;

        private RaceInfo(int leagueId, int stageNumber, String raceName) {
            this.leagueId = leagueId;
            this.stageNumber = stageNumber;
            this.raceName = raceName;
        }

        public int getLeagueId() {
            return this.leagueId;
        }

        public int getStageNumber() {
            return this.stageNumber;
        }

        public String getRaceName() {
            return this.raceName;
        }
    }
}

