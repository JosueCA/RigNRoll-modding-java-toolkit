/*
 * Decompiled with CFR 0.151.
 */
package rnrscr.smi;

import java.util.Random;
import rnrcore.loc;
import rnrorg.ActiveJournalListeners;
import rnrorg.JournalActiveListener;
import rnrscr.IMissionInformation;
import rnrscr.MissionDialogs;
import rnrscr.smi.Article;

public class NewspaperQuest
extends Article {
    private IMissionInformation mission_info;
    private String name;
    private String loc_name;
    private String loc_header_name;
    private String textureName;
    static Random rnd = new Random();

    public NewspaperQuest(int type, String name, IMissionInformation mission_info) {
        super(type);
        this.name = name;
        this.mission_info = mission_info;
        this.loc_name = name;
        this.loc_header_name = this.loc_name + " header";
        int num = (int)(rnd.nextDouble() * 7.0);
        switch (num) {
            case 0: {
                this.textureName = "tex_menu_News01";
                break;
            }
            case 1: {
                this.textureName = "tex_menu_News02";
                break;
            }
            case 2: {
                this.textureName = "tex_menu_News03";
                break;
            }
            case 3: {
                this.textureName = "tex_menu_News04";
                break;
            }
            case 4: {
                this.textureName = "tex_menu_News05";
                break;
            }
            case 5: {
                this.textureName = "tex_menu_News06";
                break;
            }
            case 6: {
                this.textureName = "tex_menu_News07";
            }
        }
    }

    public NewspaperQuest(String name, IMissionInformation mission_info) {
        this.name = name;
        this.mission_info = mission_info;
        this.loc_name = name;
        this.loc_header_name = this.loc_name + " header";
        int num = (int)(rnd.nextDouble() * 7.0);
        switch (num) {
            case 0: {
                this.textureName = "tex_menu_News01";
                break;
            }
            case 1: {
                this.textureName = "tex_menu_News02";
                break;
            }
            case 2: {
                this.textureName = "tex_menu_News03";
                break;
            }
            case 3: {
                this.textureName = "tex_menu_News04";
                break;
            }
            case 4: {
                this.textureName = "tex_menu_News05";
                break;
            }
            case 5: {
                this.textureName = "tex_menu_News06";
                break;
            }
            case 6: {
                this.textureName = "tex_menu_News07";
            }
        }
    }

    public void readArticle() {
        ActiveJournalListeners.startActiveJournals(new JournalActiveListener(this.name));
        MissionDialogs.sayAppear(this.name);
        ActiveJournalListeners.endActiveJournals();
        MissionDialogs.sayEnd(this.name);
    }

    public String getHeader() {
        return loc.getNewspaperString(this.loc_header_name);
    }

    public String getBody() {
        return loc.getNewspaperString(this.loc_name);
    }

    public IMissionInformation getMissionInfo() {
        return this.mission_info;
    }

    public NewspaperQuest isQuest() {
        return this;
    }

    public String getTexture() {
        return this.textureName;
    }

    public int getPriority() {
        return 20;
    }
}

