package org.frc1410.framework.scheduler.task.observer;

import org.frc1410.framework.scheduler.task.LifecycleHandler;

public abstract class Observer {

    protected final LifecycleHandler lifecycle;

    public Observer(LifecycleHandler lifecycle) {
        this.lifecycle = lifecycle;
    }

    public abstract void tick();
}