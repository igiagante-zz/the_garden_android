package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.AttributeRealm;

/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class AttributeRealmToAttribute implements Mapper<AttributeRealm, Attribute> {

    @Override
    public Attribute map(AttributeRealm attributeRealm) {
        Attribute attribute = new Attribute();
        attribute.setId(attributeRealm.getId());
        return copy(attributeRealm, attribute);
    }

    @Override
    public Attribute copy(AttributeRealm attributeRealm, Attribute attribute) {

        attribute.setName(attributeRealm.getName());
        attribute.setType(attributeRealm.getType());
        attribute.setPercentage(attributeRealm.getPercentage());

        return attribute;
    }

    @Override
    public Attribute copy(AttributeRealm attributeRealm, Attribute attribute, boolean update) {
        return null;
    }
}
