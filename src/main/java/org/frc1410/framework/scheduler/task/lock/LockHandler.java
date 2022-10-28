package org.frc1410.framework.scheduler.task.lock;

import org.frc1410.framework.scheduler.task.Task;

import java.util.HashMap;
import java.util.Map;

/**
 * Developers will often encounter an issue where two commands (which are backed by tasks internally)
 * depend on the same shared resource, generally in the form of a subsystem. In order to prevent race
 * conditions and unpredictable behavior, tasks can acquire locks. When a task acquires a lock, the
 * task with the lowest priority will be suspended. Once the task that claimed possession of a lock
 * finishes (whether by cancellation or interruption), execution of the other task that laid claim
 * on the lock is rescheduled for execution.
 */
public class LockHandler {

    private final Map<TaskLock, Task> locks = new HashMap<>();

    
}
