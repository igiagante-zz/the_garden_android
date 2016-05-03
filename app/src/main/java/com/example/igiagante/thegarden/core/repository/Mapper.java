package com.example.igiagante.thegarden.core.repository;

/**
 * Created by igiagante on 26/4/16.
 */
public interface Mapper<From, To> {
    To map(From from);
    To copy(From from, To to);
}
