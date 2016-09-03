package com.example.igiagante.thegarden.home.plants.usecase;

import android.content.Context;

import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.realm.PlagueRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.specification.plague.PlagueSpecification;
import com.example.igiagante.thegarden.core.repository.sqlite.FlavorDao;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * @author ignaciogiagante, on 9/2/16.
 */

@Singleton
public class SetLocalPathsUseCase extends UseCase<List<String>> {

    private static final String TAG = SetLocalPathsUseCase.class.getSimpleName();

    private final FlavorDao flavorDao;
    private final PlagueRealmRepository plagueRealmRepository;

    private ArrayList<String> plaguesUrls = new ArrayList<>();
    private ArrayList<String> flavorsUrls = new ArrayList<>();

    @Inject
    public SetLocalPathsUseCase(Context context, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);

        this.flavorDao = new FlavorDao(context);
        this.plagueRealmRepository = new PlagueRealmRepository(context);
    }

    private void createUrlsLists(List<String> urls) {
        for (String url : urls) {
            if (url.contains("flavors")) {
                flavorsUrls.add(url);
            } else {
                plaguesUrls.add(url);
            }
        }
    }

    @Override
    protected Observable buildUseCaseObservable(List<String> urls) {

        createUrlsLists(urls);

        List<Flavor> flavors = flavorDao.getFlavors();

        if (!flavors.isEmpty()) {
            for (Flavor flavor : flavors) {
                for (String url : flavorsUrls) {
                    if (url.contains(flavor.getName())) {
                        flavor.setLocalPath(url);
                    }
                }
            }
        }

        flavorDao.deleteAll();

        flavorDao.add(flavors);

        PlagueSpecification plagueSpecification = new PlagueSpecification();
        Observable<List<Plague>> plaguesObservable = plagueRealmRepository.query(plagueSpecification);

        ArrayList<Plague> plaguesFromDB = new ArrayList<>();
        plaguesObservable.subscribe(list -> plaguesFromDB.addAll(list));

        if (!plaguesFromDB.isEmpty()) {
            for (Plague plague : plaguesFromDB) {
                for (String url : plaguesUrls) {
                    if (url.contains(plague.getName().toLowerCase().trim())) {
                        plague.setLocalPath(url);
                    }
                }
            }
        }

        for(Plague plague : plaguesFromDB) {
            plagueRealmRepository.update(plague);
        }

        return Observable.just("OK");
    }
}
