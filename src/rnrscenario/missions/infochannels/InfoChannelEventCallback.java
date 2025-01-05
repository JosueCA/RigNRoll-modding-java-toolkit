/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.infochannels;

import java.util.ArrayList;
import java.util.List;
import scriptActions.ScriptAction;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class InfoChannelEventCallback {
    private List<ScriptAction> onAcceptActions = null;
    private List<ScriptAction> onAppearActions = null;
    private List<ScriptAction> onDeclineActions = null;
    private List<ScriptAction> onEndActions = null;
    private boolean channelWasClosed = false;
    private ChannelClose closeInformation = ChannelClose.DIALOG;
    private String acceptPalce = null;
    private String appearPalce = null;
    private String declinePalce = null;

    boolean channelClosed() {
        return this.channelWasClosed;
    }

    public boolean hasAcceptAction() {
        return !this.onAcceptActions.isEmpty();
    }

    public void useCallbacks(List<ScriptAction> onAcceptActions, List<ScriptAction> onAppearActions, List<ScriptAction> onDeclineActions, List<ScriptAction> onEndActions, ChannelClose closeInformation) {
        this.onAcceptActions = new ArrayList<ScriptAction>(onAcceptActions);
        this.onAppearActions = new ArrayList<ScriptAction>(onAppearActions);
        this.onDeclineActions = new ArrayList<ScriptAction>(onDeclineActions);
        this.onEndActions = new ArrayList<ScriptAction>(onEndActions);
        this.closeInformation = closeInformation;
    }

    private static void execute(List<ScriptAction> actionList) {
        assert (null != actionList) : "actionList must be non-null reference";
        for (ScriptAction action : actionList) {
            action.act();
        }
        actionList.clear();
    }

    void callBackAppear(String where) {
        this.appearPalce = where;
        InfoChannelEventCallback.execute(this.onAppearActions);
        this.channelWasClosed = this.closeInformation.isCloseOnAppear();
    }

    void callBackAccept(String where) {
        this.acceptPalce = where;
        InfoChannelEventCallback.execute(this.onAcceptActions);
        this.channelWasClosed = this.closeInformation.isCloseOnYes();
    }

    void callBackDecline(String where) {
        this.declinePalce = where;
        InfoChannelEventCallback.execute(this.onDeclineActions);
        this.channelWasClosed = this.closeInformation.isCloseOnNo();
    }

    void callBackEnd(String where) {
        InfoChannelEventCallback.execute(this.onEndActions);
        this.channelWasClosed = this.closeInformation.isCloseOnEnd();
    }

    String whereAccepted() {
        return this.acceptPalce;
    }

    String whereDeclined() {
        return this.declinePalce;
    }

    String whereAppeared() {
        return this.appearPalce;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum ChannelClose {
        DIALOG(false, false, false, true),
        DELAYED_ANSWERS(false, true, true, false);

        private final boolean closeOnAppear;
        private final boolean closeOnYes;
        private final boolean closeOnNo;
        private final boolean closeOnEnd;

        private ChannelClose(boolean closeOnAppear, boolean closeOnYes, boolean closeOnNo, boolean closeOnEnd) {
            this.closeOnAppear = closeOnAppear;
            this.closeOnYes = closeOnYes;
            this.closeOnNo = closeOnNo;
            this.closeOnEnd = closeOnEnd;
        }

        public boolean isCloseOnAppear() {
            return this.closeOnAppear;
        }

        public boolean isCloseOnEnd() {
            return this.closeOnEnd;
        }

        public boolean isCloseOnNo() {
            return this.closeOnNo;
        }

        public boolean isCloseOnYes() {
            return this.closeOnYes;
        }
    }
}

