/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.Common;
import menu.MENUsimplebutton_field;
import menu.menues;
import menu.xmlcontrols;
import menuscript.ConfirmDialog;
import menuscript.IresolutionChanged;
import menuscript.WindowParentMenu;
import menuscript.mainmenu.RaplayGameOptionsESC;
import menuscript.mainmenu.SettingsAudioESC;
import menuscript.mainmenu.SettingsControlsESC;
import menuscript.mainmenu.SettingsVideoESC;
import menuscript.mainmenu.SinglePlayerGameOptionsESC;

public class EscapeOptions
extends WindowParentMenu
implements IresolutionChanged {
    private static final String FILE = "..\\data\\config\\menu\\menu_esc.xml";
    private static final String MENU = "OPTIONS";
    private static final int VIDEOSETTINGS = 2;
    private static final int GAME_OPTIONS = 3;
    private static final String[] SINGLE_TABNAMES = new String[]{"Tab0 - CONTROLS", "Tab0 - AUDIO", "Tab0 - VIDEO", "Tab0 - GAME PLAY"};
    private static final String[] REPLAY_TABNAMES = new String[]{"Tab0 - CONTROLS", "Tab0 - AUDIO", "Tab0 - VIDEO", "Tab0 - REPLAY OPTIONS"};
    private static boolean f_resolution_changed = false;
    private static int game_type = 0;
    private ConfirmDialog m_cdialog;
    private String m_textemptywarn;
    public String m_inSoundCardName;
    private long tab_to_hide = 0L;

    public void addVideoSettingsListener(IresolutionChanged listener) {
        SettingsVideoESC video = (SettingsVideoESC)this.settings.get(2);
        video.addListener(listener);
    }

    public EscapeOptions(long _menu, Common common, ConfirmDialog dialog, int _game_type) {
        super(_menu, FILE, MENU);
        this.uiTools = common;
        this.m_cdialog = dialog;
        game_type = _game_type;
        this.setSaveBetweenTabs();
    }

    public String GetCustomText() {
        return this.m_textemptywarn;
    }

    public void InitMenu(long _menu) {
        this.settings.add(SettingsControlsESC.create(_menu, menues.FindFieldInMenu(_menu, "BUTT - CONTROLS - CANCEL"), menues.FindFieldInMenu(_menu, "BUTT - CONTROLS - DEFAULT"), menues.FindFieldInMenu(_menu, "BUTT - CONTROLS - OK"), menues.FindFieldInMenu(_menu, "BUTT - CONTROLS - APPLY")));
        this.settings.add(SettingsAudioESC.create(_menu, menues.FindFieldInMenu(_menu, "BUTT - AUDIO - CANCEL"), menues.FindFieldInMenu(_menu, "BUTT - AUDIO - DEFAULT"), menues.FindFieldInMenu(_menu, "BUTT - AUDIO - OK"), menues.FindFieldInMenu(_menu, "BUTT - AUDIO - APPLY")));
        this.settings.add(SettingsVideoESC.create(_menu, menues.FindFieldInMenu(_menu, "BUTT - VIDEO - CANCEL"), menues.FindFieldInMenu(_menu, "BUTT - VIDEO - DEFAULT"), menues.FindFieldInMenu(_menu, "BUTT - VIDEO - OK"), menues.FindFieldInMenu(_menu, "BUTT - VIDEO - APPLY")));
        if (game_type != 3) {
            this.settings.add(SinglePlayerGameOptionsESC.create(_menu, menues.FindFieldInMenu(_menu, "BUTT - GAMEPLAY - CANCEL"), menues.FindFieldInMenu(_menu, "BUTT - GAMEPLAY - DEFAULT"), menues.FindFieldInMenu(_menu, "BUTT - GAMEPLAY - OK"), menues.FindFieldInMenu(_menu, "BUTT - GAMEPLAY - APPLY"), game_type));
        } else {
            this.settings.add(RaplayGameOptionsESC.create(_menu, menues.FindFieldInMenu(_menu, "BUTT - REPLAY OPTIONS - CANCEL"), menues.FindFieldInMenu(_menu, "BUTT - REPLAY OPTIONS - DEFAULT"), menues.FindFieldInMenu(_menu, "BUTT - REPLAY OPTIONS - OK"), menues.FindFieldInMenu(_menu, "BUTT - REPLAY OPTIONS - APPLY")));
        }
        if (game_type != 3) {
            for (int i = 0; i < SINGLE_TABNAMES.length; ++i) {
                if (i == 3) {
                    this.tab_to_hide = menues.FindFieldInMenu(_menu, REPLAY_TABNAMES[i]);
                    menues.SetShowField(this.tab_to_hide, false);
                }
                long single_tab = menues.FindFieldInMenu(_menu, SINGLE_TABNAMES[i]);
                xmlcontrols.MENUCustomStuff Tab = (xmlcontrols.MENUCustomStuff)menues.ConvertMenuFields(single_tab);
                this.AddTab(_menu, Tab);
            }
        } else {
            for (int i = 0; i < REPLAY_TABNAMES.length; ++i) {
                if (i == 3) {
                    this.tab_to_hide = menues.FindFieldInMenu(_menu, SINGLE_TABNAMES[i]);
                    menues.SetShowField(this.tab_to_hide, false);
                }
                long single_tab = menues.FindFieldInMenu(_menu, REPLAY_TABNAMES[i]);
                xmlcontrols.MENUCustomStuff Tab = (xmlcontrols.MENUCustomStuff)menues.ConvertMenuFields(single_tab);
                this.AddTab(_menu, Tab);
            }
        }
    }

    void NeedToConfirm(String text) {
        this.m_cdialog.AskUser(this, text);
    }

    void OnOk(long _menu, MENUsimplebutton_field button) {
        super.OnOk(_menu, button);
    }

    public void changed() {
        f_resolution_changed = true;
    }

    public void Activate() {
        if (this.tab_to_hide != 0L) {
            menues.SetShowField(this.tab_to_hide, false);
        }
        if (f_resolution_changed) {
            this.activateTab(2);
        }
        super.Activate();
    }
}

