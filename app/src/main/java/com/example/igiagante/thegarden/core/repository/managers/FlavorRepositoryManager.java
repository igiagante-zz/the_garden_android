package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;

import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.restAPI.RestApiFlavorRepository;
import com.example.igiagante.thegarden.core.repository.sqlite.FlavorDao;
import com.example.igiagante.thegarden.core.repository.sqlite.FlavorDaoRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 30/5/16.
 */
public class FlavorRepositoryManager extends RepositoryManager<Repository<Flavor>> {

    @Inject
    public FlavorRepositoryManager(Context context){
        mRepositories.add(new FlavorDaoRepository(new FlavorDao(context)));
    }

    /**
     * Return an observable a list of resources.
     * @param specification {@link Specification}
     * @return Observable
     */
    public Observable query(Specification specification) {
        return mRepositories.get(0).query(specification);
    }
}
