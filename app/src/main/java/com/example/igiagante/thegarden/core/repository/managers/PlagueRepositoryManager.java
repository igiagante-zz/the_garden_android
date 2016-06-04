package com.example.igiagante.thegarden.core.repository.managers;

import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.restAPI.RestApiPlagueRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 6/4/16.
 */
public class PlagueRepositoryManager  extends RepositoryManager<Repository<Plague>> {

    @Inject
    public PlagueRepositoryManager() {
        mRepositories.add(new RestApiPlagueRepository());
    }

    /**
     * Return an observable a list of resources.
     * @param specification {@link Specification}
     * @return Observable
     */
    public Observable query(Specification specification) {

        /*
        Observable observable;

        for(Repository<Attribute> repository : mRepositories) {
            observable = repository.query(specification);
        }*/

        return mRepositories.get(0).query(specification);
    }
}
