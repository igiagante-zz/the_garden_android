package com.example.igiagante.thegarden.core.repository;

/**
 * @author Ignacio Giagante, on 15/4/16.
 */
public interface SqlSpecification extends Specification {
    String toSqlQuery();
}
