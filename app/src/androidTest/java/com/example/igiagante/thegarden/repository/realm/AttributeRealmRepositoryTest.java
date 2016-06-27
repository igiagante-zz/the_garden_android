package com.example.igiagante.thegarden.repository.realm;

import android.test.AndroidTestCase;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.repository.realm.AttributeRealmRepository;

import junit.framework.Assert;

import java.util.ArrayList;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 6/6/16.
 */
public class AttributeRealmRepositoryTest extends AndroidTestCase {

    private AttributeRealmRepository repository;
    private final String ID = "1";
    private final String NAME = "euphoric";
    private final String TYPE = "effects";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        repository = new AttributeRealmRepository(getContext());
        repository.removeAll();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        repository.removeAll();
    }

    public void testGetById() {
        // setup
        Attribute attribute = createAttribute(ID, NAME, TYPE);
        repository.add(attribute);

        // verify
        repository.getById(ID).subscribe(
                item -> Assert.assertEquals(item.getName(), NAME)
        );
    }

    public void testPersistOneAttribute() {

        Attribute attribute = createAttribute(ID, NAME, TYPE);

        repository.add(attribute);

        repository.getById(ID).subscribe(attributeFromDB -> Assert.assertEquals(attributeFromDB.getName(), NAME));
    }

    /**
     * Test add(final Iterable<Attribute> attributes)
     */
    public void testPersistAttributes() {

        // setup
        // create three attributes
        ArrayList<Attribute> attributes = createAttributes();

        // when
        Observable<Integer> result = repository.add(attributes);

        // assertions
        result.subscribe(count -> Assert.assertEquals(3, count.intValue()));
    }

    public void testRemoveOneAttribute() {

        // setup
        Attribute attribute = createAttribute(ID, NAME, TYPE);

        repository.add(attribute);

        repository.getById(ID).subscribe(plagueFromDB -> Assert.assertEquals(plagueFromDB.getName(), NAME));

        // when
        Observable<Integer> result = repository.remove(attribute.getId());

        // assertions
        result.subscribe(count -> Assert.assertEquals(1, count.intValue()));
    }

    private Attribute createAttribute(String id, String name, String type) {

        Attribute attribute = new Attribute();

        attribute.setId(id);
        attribute.setName(name);
        attribute.setType(type);

        return attribute;
    }

    /**
     * Create a list of attributes
     *
     * @return attributes
     */
    private ArrayList<Attribute> createAttributes() {

        ArrayList<Attribute> attributes = new ArrayList<>();

        Attribute attributeOne = createAttribute(ID, NAME, TYPE);
        Attribute attributeTwo = createAttribute("2", "Insomnia", "medicinal");
        Attribute attributeThree = createAttribute("3", "symptoms", "headache");

        attributes.add(attributeOne);
        attributes.add(attributeTwo);
        attributes.add(attributeThree);

        return attributes;
    }
}
