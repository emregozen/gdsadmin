package com.gds.admin.service;

import com.gds.admin.domain.StateName;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing StateName.
 */
public interface StateNameService {

    /**
     * Save a stateName.
     *
     * @param stateName the entity to save
     * @return the persisted entity
     */
    StateName save(StateName stateName);

    /**
     * Get all the stateNames.
     *
     * @return the list of entities
     */
    List<StateName> findAll();


    /**
     * Get the "id" stateName.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StateName> findOne(Long id);

    /**
     * Delete the "id" stateName.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the stateName corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<StateName> search(String query);
}
