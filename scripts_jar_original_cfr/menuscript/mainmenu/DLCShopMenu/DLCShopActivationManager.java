/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu.DLCShopMenu;

import menu.KeyPair;
import menu.MacroKit;
import menu.menues;
import menuscript.DLCShopManager;
import menuscript.IPoPUpMenuListener;
import menuscript.PoPUpMenu;
import menuscript.mainmenu.DLCShopMenu.DLCShopMenuFilePath;
import menuscript.mainmenu.DLCShopMenu.DLCShopTableInterface;

public final class DLCShopActivationManager
implements IPoPUpMenuListener {
    public static String MAIN_XML_PATH = "";
    private static final String SUCCEED_TABLEGROUP = "Tablegroup - DLC - Activated successfully";
    private static final String SUCCEED_WINDOW = "DLC - Activated successfully";
    private static final String FAILED_NO_CONNECTION_TABLEGROUP = "Tablegroup - DLC - No connection";
    private static final String FAILED_NO_CONNECTION_WINDOW = "DLC - No connection";
    private static final String FAILED_ACTIVATION_CODE_TABLEGROUP = "Tablegroup - DLC - Activate code failed";
    private static final String FAILED_ACTIVATION_CODE_WINDOW = "DLC - Activate code failed";
    private static final String FAILED_WRONG_SERIAL_NUMBER_TABLEGROUP = "Tablegroup - DLC - Wrong serial number";
    private static final String FAILED_WRONG_SERIAL_NUMBER_WINDOW = "DLC - Wrong serial number";
    private static final String FAILED_ALREADY_ACTIVATED_TABLEGROUP = "Tablegroup - DLC - Serial number activated";
    private static final String FAILED_ALREADY_ACTIVATED_WINDOW = "DLC - Serial number activated";
    private static final String FAILED_ANOTHER_VERSION_TABLEGROUP = "Tablegroup - DLC - Serial number for another copy";
    private static final String FAILED_ANOTHER_NUMBER_WINDOW = "DLC - Serial number for another copy";
    private static final String FAILED_UNKNOWN_ITEM_TABLEGROUP = "Tablegroup - DLC - Unknown item";
    private static final String FAILED_UNKNOWN_ITEM_WINDOW = "DLC - Unknown item";
    private static final String FAILED_UNKNOWN_ERROR_TABLEGROUP = "Tablegroup - DLC - Activation error";
    private static final String FAILED_UNKNOWN_ERROR_WINDOW = "DLC - Activation error";
    private static final String ACTIVATION_ERROR = "ACTIVATION_ERROR";
    private PoPUpMenu m_successWindow;
    private PoPUpMenu m_noConnectionWindow;
    private PoPUpMenu m_failedActivationCodeWindow;
    private PoPUpMenu m_wrongSerialNumberWindow;
    private PoPUpMenu m_alreadyActivatedWindow;
    private PoPUpMenu m_anotherVersionWindow;
    private PoPUpMenu m_unknownItemWindow;
    private PoPUpMenu m_unknownErrorWindow;
    private long m_serialNumberTextBoxId;
    private long m_errorMessageTextBoxId;
    private int m_counter = 0;
    DLCShopTableInterface m_dlcShopTable;
    static int errorNumber = 0;

    public DLCShopActivationManager(long menu, DLCShopMenuFilePath.DLCShopMenuType menuType, DLCShopTableInterface dlcShopTable) {
        MAIN_XML_PATH = DLCShopMenuFilePath.getMenuPath(menuType);
        this.m_dlcShopTable = dlcShopTable;
        this.m_successWindow = new PoPUpMenu(menu, MAIN_XML_PATH, SUCCEED_TABLEGROUP, SUCCEED_WINDOW);
        this.m_noConnectionWindow = new PoPUpMenu(menu, MAIN_XML_PATH, FAILED_NO_CONNECTION_TABLEGROUP, FAILED_NO_CONNECTION_WINDOW);
        this.m_failedActivationCodeWindow = new PoPUpMenu(menu, MAIN_XML_PATH, FAILED_ACTIVATION_CODE_TABLEGROUP, FAILED_ACTIVATION_CODE_WINDOW);
        this.m_wrongSerialNumberWindow = new PoPUpMenu(menu, MAIN_XML_PATH, FAILED_WRONG_SERIAL_NUMBER_TABLEGROUP, FAILED_WRONG_SERIAL_NUMBER_WINDOW);
        this.m_alreadyActivatedWindow = new PoPUpMenu(menu, MAIN_XML_PATH, FAILED_ALREADY_ACTIVATED_TABLEGROUP, FAILED_ALREADY_ACTIVATED_WINDOW);
        this.m_anotherVersionWindow = new PoPUpMenu(menu, MAIN_XML_PATH, FAILED_ANOTHER_VERSION_TABLEGROUP, FAILED_ANOTHER_NUMBER_WINDOW);
        this.m_unknownItemWindow = new PoPUpMenu(menu, MAIN_XML_PATH, FAILED_UNKNOWN_ITEM_TABLEGROUP, FAILED_UNKNOWN_ITEM_WINDOW);
        this.m_unknownErrorWindow = new PoPUpMenu(menu, MAIN_XML_PATH, FAILED_UNKNOWN_ERROR_TABLEGROUP, FAILED_UNKNOWN_ERROR_WINDOW);
        this.m_serialNumberTextBoxId = menues.FindFieldInMenu(menu, "DLC - SERIAL NUMBER VALUE");
        this.m_errorMessageTextBoxId = menues.FindFieldInMenu(menu, "DLC - Activation error - TEXT");
        this.testActivate(false, "");
    }

    public void afterInit() {
        this.m_successWindow.afterInit();
        this.m_successWindow.addListener(this);
        this.m_noConnectionWindow.afterInit();
        this.m_failedActivationCodeWindow.afterInit();
        this.m_wrongSerialNumberWindow.afterInit();
        this.m_alreadyActivatedWindow.afterInit();
        this.m_anotherVersionWindow.afterInit();
        this.m_unknownItemWindow.afterInit();
        this.m_unknownErrorWindow.afterInit();
    }

    public void activate(String serialNumber) {
        int errorCode = DLCShopManager.getDLCShopManager().Activate(serialNumber);
        switch (errorCode) {
            case 0: {
                if (0L != this.m_serialNumberTextBoxId) {
                    menues.SetFieldText(this.m_serialNumberTextBoxId, serialNumber);
                    menues.UpdateMenuField(menues.ConvertMenuFields(this.m_serialNumberTextBoxId));
                }
                this.m_successWindow.show();
                break;
            }
            case 1: {
                this.m_noConnectionWindow.show();
                break;
            }
            case 2: {
                this.m_failedActivationCodeWindow.show();
                break;
            }
            case 3: {
                this.m_wrongSerialNumberWindow.show();
                break;
            }
            case 4: {
                this.m_alreadyActivatedWindow.show();
                break;
            }
            case 5: {
                this.m_anotherVersionWindow.show();
                break;
            }
            case 6: {
                this.m_unknownItemWindow.show();
                break;
            }
            default: {
                if (0L != this.m_errorMessageTextBoxId) {
                    KeyPair[] keys = new KeyPair[]{new KeyPair(ACTIVATION_ERROR, Integer.toHexString(errorCode))};
                    MacroKit.ApplyToTextfield(menues.ConvertTextFields(this.m_errorMessageTextBoxId), keys);
                }
                this.m_unknownErrorWindow.show();
            }
        }
    }

    public void hide() {
        this.m_successWindow.hide();
        this.m_noConnectionWindow.hide();
        this.m_failedActivationCodeWindow.hide();
        this.m_wrongSerialNumberWindow.hide();
        this.m_alreadyActivatedWindow.hide();
        this.m_anotherVersionWindow.hide();
        this.m_unknownItemWindow.hide();
        this.m_unknownErrorWindow.hide();
    }

    private void testActivate(boolean test2, String serialNumber) {
        if (!test2) {
            return;
        }
        if (this.m_counter > 7) {
            this.m_counter = 0;
        }
        switch (this.m_counter) {
            case 0: {
                if (0L != this.m_serialNumberTextBoxId) {
                    menues.SetFieldText(this.m_serialNumberTextBoxId, serialNumber);
                    menues.UpdateMenuField(menues.ConvertMenuFields(this.m_serialNumberTextBoxId));
                }
                this.m_successWindow.show();
                break;
            }
            case 1: {
                this.m_noConnectionWindow.show();
                break;
            }
            case 2: {
                this.m_failedActivationCodeWindow.show();
                break;
            }
            case 3: {
                this.m_wrongSerialNumberWindow.show();
                break;
            }
            case 4: {
                this.m_alreadyActivatedWindow.show();
                break;
            }
            case 5: {
                this.m_anotherVersionWindow.show();
                break;
            }
            case 6: {
                this.m_unknownItemWindow.show();
                break;
            }
            case 7: {
                ++errorNumber;
                if (0L != this.m_errorMessageTextBoxId) {
                    KeyPair[] keys = new KeyPair[]{new KeyPair(ACTIVATION_ERROR, Integer.toHexString(errorNumber))};
                    MacroKit.ApplyToTextfield(menues.ConvertTextFields(this.m_errorMessageTextBoxId), keys);
                }
                this.m_unknownErrorWindow.show();
                break;
            }
        }
        ++this.m_counter;
    }

    public void onAgreeclose() {
        this.m_dlcShopTable.updateTable();
    }

    public void onCancel() {
    }

    public void onClose() {
    }

    public void onOpen() {
    }
}

