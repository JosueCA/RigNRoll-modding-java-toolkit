/*
 * Decompiled with CFR 0.151.
 */
package menu.resource;

public class MenuResourcesManager {
    private static String[] activatorIconsMenuIds = new String[]{"f2barMENU", "f2defaultMENU", "f2gasMENU", "f2motelMENU", "f2officeMENU", "f2policeMENU", "f2repairMENU", "missioniconTrailerAMENU", "missioniconTrailerNAMENU", "missioniconPackageAMENU", "missioniconPackageNAMENU", "missioniconPassangerAMENU", "missioniconPassangerNAMENU", "missioniconEndMENU", "missioniconTalkAMENU"};
    private static String[] bannerStallasMenuIds = new String[]{"banner01MENU", "banner02MENU", "banner03MENU", "banner04MENU", "stellaPreparingToOrdersMENU", "stellaPreparingToRaceMENU", "stellaWilcomMENU", "raceStartFinishWarehouse01MENU", "raceStartFinishWarehouse02MENU", "raceStartFinishWarehouse03MENU", "raceStartFinishWarehouse04MENU", "raceStartFinishWarehouse05MENU"};

    public static native void holdMenuResource(String var0);

    public static native void leaveMenuResource(String var0);

    public static void holdResources() {
        for (String menuid : activatorIconsMenuIds) {
            MenuResourcesManager.holdMenuResource(menuid);
        }
        for (String menuid : bannerStallasMenuIds) {
            MenuResourcesManager.holdMenuResource(menuid);
        }
    }

    public static void leaveResources() {
        for (String menuid : activatorIconsMenuIds) {
            MenuResourcesManager.leaveMenuResource(menuid);
        }
        for (String menuid : bannerStallasMenuIds) {
            MenuResourcesManager.leaveMenuResource(menuid);
        }
    }
}

