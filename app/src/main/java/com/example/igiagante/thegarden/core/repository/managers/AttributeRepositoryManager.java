package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.AttributeRealmRepository;
import com.example.igiagante.thegarden.core.repository.restAPI.RestApiAttributeRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 3/6/16.
 */
public class AttributeRepositoryManager extends RepositoryManager<Repository<Attribute>> {

    @Inject
    public AttributeRepositoryManager(Context context) {
        mRepositories.add(new AttributeRealmRepository(context));
        mRepositories.add(new RestApiAttributeRepository(context));
    }

    /**
     * Return an observable a list of resources.
     * @param specification {@link Specification}
     * @return Observable
     */
    public Observable query(Specification specification) {

        // TODO - This should be implemented the first time the app is installed
        /*
        Observable<List<Attribute>> query = mRepositories.get(0).query(specification);

        List<Attribute> list = new ArrayList<>();
        query.subscribe(attributes -> list.addAll(attributes));

        Observable<List<Attribute>> observable = Observable.just(list);

        return observable.map(v -> !v.isEmpty()).firstOrDefault(false)
                .flatMap(exists -> exists
                        ? observable
                        : mRepositories.get(1).query(null)); */

        return  mRepositories.get(0).query(specification);
    }
}
