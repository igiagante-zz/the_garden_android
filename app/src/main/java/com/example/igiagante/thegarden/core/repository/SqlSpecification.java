package com.example.igiagante.thegarden.core.repository;

/**
 * Created by igiagante on 15/4/16.
 */
public interface SqlSpecification extends Specification {
    String toSqlQuery();
}
