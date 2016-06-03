package com.example.igiagante.thegarden.core.repository.managers;

import com.example.igiagante.thegarden.core.repository.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignacio Giagante, on 3/6/16.
 */
public abstract class RepositoryManager<Repo extends Repository<?>> {

    /**
     * List of repositories
     */
    protected List<Repo> mRepositories = new ArrayList<>();

    protected List<Integer> repositoriesOrder = new ArrayList<>();

    public List<Repo> getRepositories() {
        return mRepositories;
    }

    public void setRepositories(List<Repo> mRepositories) {
        this.mRepositories = mRepositories;
    }

    public List<Integer> getRepositoriesOrder() {
        return repositoriesOrder;
    }

    public void setRepositoriesOrder(List<Integer> repositoriesOrder) {
        this.repositoriesOrder = repositoriesOrder;
    }
}
