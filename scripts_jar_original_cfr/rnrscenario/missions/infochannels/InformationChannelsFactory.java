/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.infochannels;

import java.util.HashMap;
import java.util.Map;
import rnrscenario.missions.infochannels.ArticleChannel;
import rnrscenario.missions.infochannels.InfoChannelEventCallback;
import rnrscenario.missions.infochannels.InformationChannel;
import rnrscenario.missions.infochannels.NoSuchChannelException;
import rnrscr.smi.IArticleCreator;

public class InformationChannelsFactory {
    private static InformationChannelsFactory ourInstance = new InformationChannelsFactory();
    private static final int DEFAULT_CHANNELS_CAPACITY = 16;
    private final Map<String, InformationChannel> channelsPrototypes = new HashMap<String, InformationChannel>(16);
    private final Map<String, ChannelParameters> channelsTypeImmediate = new HashMap<String, ChannelParameters>(16);

    public static InformationChannelsFactory getInstance() {
        return ourInstance;
    }

    public static void deinit() {
        if (ourInstance != null) {
            InformationChannelsFactory.ourInstance.channelsPrototypes.clear();
            InformationChannelsFactory.ourInstance.channelsTypeImmediate.clear();
        }
        ourInstance = new InformationChannelsFactory();
    }

    private InformationChannelsFactory() {
    }

    public void addChannelPrototype(String uid, InformationChannel prototype, boolean is_immediate, boolean is_bounded, boolean is_dialog, InfoChannelEventCallback.ChannelClose closeChannelInfo) {
        assert (null != prototype) : "target to add must be non-null reference";
        this.channelsPrototypes.put(uid, prototype);
        this.channelsTypeImmediate.put(uid, new ChannelParameters(is_immediate, is_bounded, is_dialog, closeChannelInfo));
    }

    public boolean isImmediateChannel(String channelName) {
        if (!this.channelsTypeImmediate.containsKey(channelName)) {
            return false;
        }
        return this.channelsTypeImmediate.get(channelName).isImmidiate();
    }

    public boolean isBoundedChannel(String channelName) {
        if (!this.channelsTypeImmediate.containsKey(channelName)) {
            return false;
        }
        return this.channelsTypeImmediate.get(channelName).isBounded();
    }

    public boolean isDialogChannel(String channelName) {
        if (!this.channelsTypeImmediate.containsKey(channelName)) {
            return false;
        }
        return this.channelsTypeImmediate.get(channelName).isDialog();
    }

    public InfoChannelEventCallback.ChannelClose getCloseChannelInfo(String channelName) {
        if (!this.channelsTypeImmediate.containsKey(channelName)) {
            return InfoChannelEventCallback.ChannelClose.DIALOG;
        }
        return this.channelsTypeImmediate.get(channelName).getCloseChannelInfo();
    }

    public InformationChannel construct(String name, Object data_for_creation) throws NoSuchChannelException {
        assert (null != name) : "name must be non-null reference";
        InformationChannel prototype = this.channelsPrototypes.get(name);
        if (null != prototype) {
            InformationChannel res = prototype.clone();
            if (res instanceof ArticleChannel && data_for_creation instanceof IArticleCreator) {
                ((ArticleChannel)res).setArticle((IArticleCreator)data_for_creation);
            }
            return res;
        }
        throw new NoSuchChannelException(name);
    }

    private static final class ChannelParameters {
        private boolean immidiate = false;
        private boolean bounded = false;
        private boolean dialog = false;
        private InfoChannelEventCallback.ChannelClose closeChannelInfo = InfoChannelEventCallback.ChannelClose.DIALOG;

        public boolean isImmidiate() {
            return this.immidiate;
        }

        public boolean isBounded() {
            return this.bounded;
        }

        public boolean isDialog() {
            return this.dialog;
        }

        public ChannelParameters(boolean immidiate, boolean bounded, boolean dialog, InfoChannelEventCallback.ChannelClose closeChannelInfo) {
            this.immidiate = immidiate;
            this.bounded = bounded;
            this.dialog = dialog;
            this.closeChannelInfo = closeChannelInfo;
        }

        public InfoChannelEventCallback.ChannelClose getCloseChannelInfo() {
            return this.closeChannelInfo;
        }
    }
}

