package com.example.igiagante.thegarden.core.usecase;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * @author Ignacio Giagante on 15/4/16.
 * Param Use case input
 */
public abstract class UseCase<Param> {

    /**
     * Determine the order of repository
     */
    @IntDef({LOCAL_REPOSITORY, REMOTE_REPOSITORY})
    public @interface RepositoryOrder {}
    public static final int LOCAL_REPOSITORY = 0;
    public static final int REMOTE_REPOSITORY = 1;

    protected List<Integer> repositoryOrder = new ArrayList<>();

    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Subscription subscription = Subscriptions.empty();

    protected UseCase(ThreadExecutor threadExecutor,
                      PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    protected List<Integer> getRepositoryOrder() {
        return repositoryOrder;
    }

    protected void setRepositoryOrder() {}

    /**
     * Builds an {@link rx.Observable} which will be used when executing the current {@link UseCase}.
     */
    protected abstract Observable buildUseCaseObservable(Param param);

    /**
     * Executes the current use case.
     * @param param Parameter input
     * @param UseCaseSubscriber The guy who will be listen to the observable build
     * with {@link #buildUseCaseObservable(Param)}.
     */
    @SuppressWarnings("unchecked")
    public void execute(@Nullable Param param, Subscriber UseCaseSubscriber) {
        this.subscription = this.buildUseCaseObservable(param)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(UseCaseSubscriber);
    }

    /**
     * Unsubscribes from current {@link rx.Subscription}.
     */
    public void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
