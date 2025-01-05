/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import rnrscenario.missions.Disposable;
import rnrscenario.sctask;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class DelayedResourceDisposer
extends sctask {
    static final long serialVersionUID = 1L;
    private static final int UPDATE_FREQUENCY = 3;
    private static final int TASKS_CAPACITY = 128;
    private List<DisposeTask> tasks = new ArrayList<DisposeTask>(128);
    private static DelayedResourceDisposer instance = null;
    private boolean allToDispose = false;

    private DelayedResourceDisposer() {
        super(3, false);
        super.start();
    }

    public void setAllToDispose() {
        this.allToDispose = true;
    }

    public static DelayedResourceDisposer getInstance() {
        if (null == instance) {
            instance = new DelayedResourceDisposer();
        }
        return instance;
    }

    public static void deinit() {
        if (null != instance) {
            instance.finishImmediately();
        }
        instance = null;
    }

    public void addResourceToDispose(Disposable resource, String missionName, String channelUid, int time) {
        if (null != resource) {
            DisposeTask taskToInsert = new DisposeTask(time, resource, missionName, channelUid);
            int insertionIndex = Collections.binarySearch(this.tasks, taskToInsert);
            if (0 > insertionIndex) {
                insertionIndex = -(1 + insertionIndex);
            }
            this.tasks.add(insertionIndex, taskToInsert);
        }
    }

    public boolean removeResource(Disposable resource) {
        if (null != resource) {
            for (int i = 0; i < this.tasks.size(); ++i) {
                if (this.tasks.get(i).resource != resource) continue;
                this.tasks.remove(i);
                return true;
            }
        }
        return false;
    }

    // @Override
    public void run() {
        if (this.tasks.isEmpty()) {
            return;
        }
        int taskIndex = 0;
        for (; this.tasks.size() > taskIndex && !this.tasks.get(taskIndex).perform(3); ++taskIndex) {
        }
        for (int tasksToDelete = this.tasks.size() - taskIndex; 0 < tasksToDelete; --tasksToDelete) {
            this.tasks.remove(this.tasks.size() - 1).perform(3);
        }
        this.allToDispose = false;
    }

    public final boolean hasChannelsForMission(String missionName) {
        for (DisposeTask task : this.tasks) {
            if (missionName.compareTo(task.getMissionName()) != 0) continue;
            return true;
        }
        return false;
    }

    public List<DelayedDisposeChannelData> getDelayedDisposeChannelsData() {
        ArrayList<DelayedDisposeChannelData> result = new ArrayList<DelayedDisposeChannelData>();
        for (DisposeTask singleTask : this.tasks) {
            DelayedDisposeChannelData resultData = new DelayedDisposeChannelData(singleTask.missionName, singleTask.channelUid, singleTask.secondsRemain);
            result.add(resultData);
        }
        return result;
    }

    public class DelayedDisposeChannelData {
        public String missionName;
        public String channelUid;
        public int secondsRemained;

        public DelayedDisposeChannelData(String missionName, String channelUid, int secondsRemained) {
            this.missionName = missionName;
            this.channelUid = channelUid;
            this.secondsRemained = secondsRemained;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static final class DisposeTask
    implements Comparable<DisposeTask> {
        private int secondsRemain = 0;
        private boolean done = false;
        private Disposable resource = null;
        private String missionName = "";
        private String channelUid = "";

        DisposeTask(int secondsRemain, Disposable resource, String missionName, String channelUid) {
            assert (null != resource) : "resource must be non-null reference";
            this.secondsRemain = secondsRemain;
            this.resource = resource;
            this.missionName = missionName;
            this.channelUid = channelUid;
        }

        boolean perform(int timeLeft) {
            if (this.done) {
                return true;
            }
            this.secondsRemain -= timeLeft;
            if (0 >= this.secondsRemain || DelayedResourceDisposer.getInstance().allToDispose) {
                this.resource.dispose();
                this.done = true;
                return true;
            }
            return false;
        }

        // @Override
        public int compareTo(DisposeTask other) {
            if (this.secondsRemain < other.secondsRemain) {
                return 1;
            }
            if (this.secondsRemain > other.secondsRemain) {
                return -1;
            }
            return 0;
        }

        public String getMissionName() {
            return this.missionName;
        }

        public int getTime() {
            return this.secondsRemain;
        }
    }
}

