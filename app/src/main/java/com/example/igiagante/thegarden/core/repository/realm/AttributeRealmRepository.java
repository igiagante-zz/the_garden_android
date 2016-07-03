package com.example.igiagante.thegarden.core.repository.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.mapper.AttributeRealmToAttribute;
import com.example.igiagante.thegarden.core.repository.realm.mapper.AttributeToAttributeRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.AttributeRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.PlantTable;
import com.example.igiagante.thegarden.core.repository.realm.specification.AttributeByIdSpecification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class AttributeRealmRepository implements Repository<Attribute> {

    private final Mapper<AttributeRealm, Attribute> toAttribute;
    private final Mapper<Attribute, AttributeRealm> toAttributeRealm;

    private Realm realm;
    private final RealmConfiguration realmConfiguration;

    public AttributeRealmRepository(@NonNull Context context) {

        this.realmConfiguration = new RealmConfiguration.Builder(context)
                .name("garden.realm")
                .deleteRealmIfMigrationNeeded()
                .build();

        this.realm = Realm.getInstance(realmConfiguration);

        this.toAttribute = new AttributeRealmToAttribute();
        this.toAttributeRealm = new AttributeToAttributeRealm(realm);
    }

    @Override
    public Observable<Attribute> getById(String id) {
        return query(new AttributeByIdSpecification(id)).flatMap(Observable::from);
    }

    @Override
    public Observable<Attribute> getByName(String name) {
        return null;
    }

    @Override
    public Observable<String> add(Attribute attribute) {
        final Realm realm = Realm.getInstance(realmConfiguration);
        realm.executeTransaction(realmParam -> realmParam.copyToRealmOrUpdate(toAttributeRealm.map(attribute)));
        realm.close();

        return Observable.just(attribute.getId());
    }

    @Override
    public Observable<Integer> add(Iterable<Attribute> attributes) {

        int size = 0;
        realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction(realmParam -> {
            for (Attribute attribute : attributes) {
                realmParam.copyToRealmOrUpdate(toAttributeRealm.map(attribute));
            }
        });

        realm.close();

        if (attributes instanceof Collection<?>) {
            size = ((Collection<?>) attributes).size();
        }

        return Observable.just(size);
    }

    @Override
    public Observable<Attribute> update(Attribute attribute) {
        realm = Realm.getInstance(realmConfiguration);

        AttributeRealm attributeRealm = realm.where(AttributeRealm.class).equalTo(PlantTable.ID, attribute.getId()).findFirst();

        realm.executeTransaction(realmParam -> {
            realmParam.copyToRealmOrUpdate(toAttributeRealm.copy(attribute, attributeRealm));
        });

        realm.close();

        return Observable.just(attribute);
    }

    @Override
    public Observable<Integer> remove(@NonNull String attributeId) {
        realm = Realm.getInstance(realmConfiguration);

        AttributeRealm attributeRealm = realm.where(AttributeRealm.class).equalTo(PlantTable.ID, attributeId).findFirst();
        realm.executeTransaction(realmParam -> attributeRealm.deleteFromRealm());

        realm.close();

        // if attributeRealm.isValid() is false, it is because the realm object was deleted
        return Observable.just(attributeRealm.isValid() ? 0 : 1);
    }

    @Override
    public Observable<Integer> remove(Specification specification) {
        realm = Realm.getInstance(realmConfiguration);

        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final AttributeRealm attributeRealm = (AttributeRealm) realmSpecification.toObjectRealm(realm);

        realm.executeTransaction(realmParam -> attributeRealm.deleteFromRealm());

        realm.close();

        // if attributeRealm.isValid() is false, the realm object was deleted
        return Observable.just(attributeRealm.isValid() ? 0 : 1);
    }

    @Override
    public void removeAll() {
        // Delete all
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    @Override
    public Observable<List<Attribute>> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;

        final Realm realm = Realm.getInstance(realmConfiguration);
        final Observable<RealmResults<AttributeRealm>> realmResults = realmSpecification.toObservableRealmResults(realm);

        // convert Observable<RealmResults<PlagueRealm>> into Observable<List<Plague>>
        List<Attribute> list = new ArrayList<>();

        realmResults.subscribe(attributeRealms -> {
            for (AttributeRealm attributeRealm : attributeRealms) {
                list.add(toAttribute.map(attributeRealm));
            }
        });

        return Observable.just(list);
    }
}
