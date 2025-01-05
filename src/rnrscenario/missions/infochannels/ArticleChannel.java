/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.infochannels;

import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.infochannels.NewspaperChannel;
import rnrscr.IMissionInformation;
import rnrscr.MissionDialogs;
import rnrscr.smi.IArticleCreator;
import rnrscr.smi.Newspapers;

public class ArticleChannel
extends NewspaperChannel {
    static final long serialVersionUID = 0L;
    private IArticleCreator ready_artuicle = null;
    private String resource_hold = null;

    public void setArticle(IArticleCreator article) {
        this.ready_artuicle = article;
    }

    public void postStartMissionInfo(MissionInfo info, String resource) {
        this.resource_hold = resource;
        IMissionInformation mission_info = MissionDialogs.getMissionInfo(resource);
        Newspapers.addMissionNewsReadyArticle(this.ready_artuicle.create(resource, mission_info));
    }

    public void realInfoPost(String mission_name, String resource) {
        this.resource_hold = resource;
        IMissionInformation mission_info = MissionDialogs.getMissionInfo(resource);
        Newspapers.addMissionNewsReadyArticle(this.ready_artuicle.create(resource, mission_info));
    }

    public ArticleChannel clone() {
        return new ArticleChannel();
    }

    void clearResources() {
        if (null != this.resource_hold) {
            MissionDialogs.RemoveDialog(this.resource_hold);
            Newspapers.removeMissionNews(this.resource_hold);
        }
    }
}

