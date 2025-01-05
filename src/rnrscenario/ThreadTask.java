/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import rnrcore.eng;
import rnrscenario.Ithreadprocedure;

public class ThreadTask
implements Runnable {
    private final Ithreadprocedure proc;
    private final Semaphore semaphore = new Semaphore(0);
    private static final Executor threadPool = Executors.newSingleThreadExecutor();

    public ThreadTask(Ithreadprocedure proc) {
        this.proc = proc;
    }

    public void run() {
        this.proc.take(this);
        this.proc.call();
    }

    public static void create(Ithreadprocedure proc) {
        threadPool.execute(new ThreadTask(proc));
    }

    public void _resum() {
        if (0 >= this.semaphore.availablePermits()) {
            this.semaphore.release();
        } else {
            eng.fatal("ThreadTask._resum called twice!");
        }
    }

    public void _susp() {
        try {
            this.semaphore.acquire();
        }
        catch (InterruptedException e) {
            System.err.print(e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    public void sleep(long milleseconds) {
        try {
            Thread.sleep(milleseconds);
        }
        catch (InterruptedException e) {
            System.err.print(e.getMessage());
            e.printStackTrace(System.err);
        }
    }
}

