package com.gds.admin.service;

import com.gds.admin.domain.CityName;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CityName.
 */
public interface CityNameService {

    /**
     * Save a cityName.
     *
     * @param cityName the entity to save
     * @return the persisted entity
     */
    CityName save(CityName cityName);

    /**
     * Get all the cityNames.
     *
     * @return the list of entities
     */
    List<CityName> findAll();


    /**
     * Get the "id" cityName.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CityName> findOne(Long id);

    /**
     * Delete the "id" cityName.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the cityName corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<CityName> search(String query);
}
