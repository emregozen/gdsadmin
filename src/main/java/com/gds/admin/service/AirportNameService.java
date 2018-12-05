package com.gds.admin.service;

import com.gds.admin.domain.AirportName;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing AirportName.
 */
public interface AirportNameService {

    /**
     * Save a airportName.
     *
     * @param airportName the entity to save
     * @return the persisted entity
     */
    AirportName save(AirportName airportName);

    /**
     * Get all the airportNames.
     *
     * @return the list of entities
     */
    List<AirportName> findAll();


    /**
     * Get the "id" airportName.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AirportName> findOne(Long id);

    /**
     * Delete the "id" airportName.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the airportName corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<AirportName> search(String query);
}
