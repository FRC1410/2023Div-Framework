package org.frc1410.framework.scheduler.loop;

import org.frc1410.framework.scheduler.task.BoundTask;
import org.frc1410.framework.scheduler.task.LifecycleHandler;
import org.frc1410.framework.scheduler.task.Task;
import org.frc1410.framework.scheduler.task.TaskState;
import org.frc1410.framework.scheduler.task.lock.LockHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Loops hold bound tasks, and are ticked on their period. There is
 * also a default loop, that runs on the normal robot periodic tick
 * and is generally used for most tasks.
 *
 * <p>These loops are <i>not</i> the same as the loops found in 254's
 * code, for example, but they are similar. They do not get their own
 * thread, but they do handle execution of tasks.
 *
 * @see LoopStore#main
 */
public class Loop {

    private final Set<BoundTask> tasks = new HashSet<>();
    private final LockHandler lockHandler = new LockHandler();
    private final long period;


    Loop(long period) {
        this.period = period;
    }

    public long getPeriod() {
        return period;
    }

    public double getPeriodSeconds() {
        return getPeriod() / 1000d;
    }

    public void add(BoundTask task) {
        tasks.add(task);
    }

    public void tick() {
        tasks.forEach(this::process);
    }

    private void process(BoundTask task) {
        if (!lockHandler.ownsLock(task)) {
            return;
        }

        task.observer.tick(task.lifecycle);

        Task job = task.job;
        LifecycleHandler lifecycle = task.lifecycle;

        switch (lifecycle.state) {
            case FLAGGED_EXECUTION -> {
                job.init();
                lifecycle.state = TaskState.EXECUTING;
            }

            case EXECUTING -> {
                job.execute();
                if (job.isFinished()) {
                    lifecycle.state = TaskState.FLAGGED_COMPLETION;
                }
            }

            case FLAGGED_COMPLETION -> {
                job.end(false);
                lifecycle.state = TaskState.ENDED;

                lockHandler.releaseLock(task);
            }

            case FLAGGED_INTERRUPTION -> {
                job.end(true);
                lifecycle.state = TaskState.ENDED;

                lockHandler.releaseLock(task);
            }
        }
    }
}
