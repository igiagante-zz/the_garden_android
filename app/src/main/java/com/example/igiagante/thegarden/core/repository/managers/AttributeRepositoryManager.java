package com.example.igiagante.thegarden.core.repository.managers;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.restAPI.RestApiAttributeRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 3/6/16.
 */
public class AttributeRepositoryManager extends RepositoryManager<Repository<Attribute>> {

    @Inject
    public AttributeRepositoryManager() {
        mRepositories.add(new RestApiAttributeRepository());
    }

    /**
     * Return an observable a list of resources.
     * @param specification {@link Specification}
     * @return Observable
     */
    public Observable query(Specification specification) {
        return  mRepositories.get(0).query(specification);
    }
}
