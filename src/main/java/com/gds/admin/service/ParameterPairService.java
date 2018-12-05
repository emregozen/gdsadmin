package com.gds.admin.service;

import com.gds.admin.domain.ParameterPair;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ParameterPair.
 */
public interface ParameterPairService {

    /**
     * Save a parameterPair.
     *
     * @param parameterPair the entity to save
     * @return the persisted entity
     */
    ParameterPair save(ParameterPair parameterPair);

    /**
     * Get all the parameterPairs.
     *
     * @return the list of entities
     */
    List<ParameterPair> findAll();


    /**
     * Get the "id" parameterPair.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ParameterPair> findOne(Long id);

    /**
     * Delete the "id" parameterPair.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the parameterPair corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ParameterPair> search(String query);
}
