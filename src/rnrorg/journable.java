// Decompiled with: CFR 0.152
// Class Version: 5
package rnrorg;

import java.util.List;
import rnrcore.CoreTime;
import rnrcore.gameDate;
import rnrcore.listelement;
import rnrorg.IjournalSerialisable;
import rnrorg.JournalActiveListener;
import rnrorg.yesnoQuestion;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface journable
extends listelement,
gameDate,
yesnoQuestion,
IjournalSerialisable {
    public void setTime(CoreTime var1);

    public CoreTime getTime();

    public String description();

    public void start();

    public void makeQuestionFor(JournalActiveListener var1);

    public List<String> getListenersResources();

    public void setDeactivationTime(CoreTime var1);

    public CoreTime getDeactivationTime();

    public String getMissionName();

    public void decline();

    public boolean needMenu();
}
