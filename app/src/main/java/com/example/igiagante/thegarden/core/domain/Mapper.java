package com.example.igiagante.thegarden.core.domain;

/**
 * Created by igiagante on 18/4/16.
 */
public interface Mapper<From, To> {
    To map(From from);
}
