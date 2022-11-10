package org.frc1410.framework.scheduler.task;

public class LifecycleHandler {

    public final BoundTask wrappedTask;
    public TaskState state = TaskState.FLAGGED_EXECUTION;

    public LifecycleHandler(BoundTask wrappedTask) {
        this.wrappedTask = wrappedTask;
    }

    public void requestExecution() {
        if (!state.isExecuting()) {
            state = TaskState.EXECUTING;
        }
    }

    public void requestTermination() {
        state = TaskState.FLAGGED_TERMINATION;
    }

    public void requestInterruption() {
        if (!state.isInactive()) {
            state = TaskState.FLAGGED_SUSPENSION;
        }
    }
}