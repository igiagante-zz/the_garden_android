package com.example.igiagante.thegarden.core.repository;

import java.util.List;

import rx.Observable;

/**
 * Created by igiagante on 15/4/16.
 */
public interface Repository<T> {

    void add(T item);

    void add(Iterable<T> items);

    void update(T item);

    void remove(T item);

    void remove(Specification specification);

    void removeAll();

    Observable<List<T>> query(Specification specification);
}