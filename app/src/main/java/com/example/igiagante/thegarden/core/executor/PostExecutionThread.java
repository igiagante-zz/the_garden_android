package com.example.igiagante.thegarden.core.executor;

import rx.Scheduler;

/**
 * Created by igiagante on 18/4/16.
 */
public interface PostExecutionThread {
    Scheduler getScheduler();
}
