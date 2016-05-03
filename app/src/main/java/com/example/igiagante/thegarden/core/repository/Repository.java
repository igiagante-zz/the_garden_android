package com.example.igiagante.thegarden.core.repository;

import java.util.List;

import rx.Observable;

/**
 * Created by igiagante on 15/4/16.
 */
public interface Repository<T> {

    Observable<T> getById(String id);

    String add(T item);

    int add(Iterable<T> items);

    Observable<T> update(T item);

    int remove(T item);

    int remove(Specification specification);

    void removeAll();

    Observable<List<T>> query(Specification specification);
}