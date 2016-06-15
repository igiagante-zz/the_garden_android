package com.example.igiagante.thegarden.core.executor;

import rx.Scheduler;

public interface PostExecutionThread {
    Scheduler getScheduler();
}
