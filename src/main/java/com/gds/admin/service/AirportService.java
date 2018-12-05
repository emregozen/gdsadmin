package com.gds.admin.service;

import com.gds.admin.domain.Airport;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Airport.
 */
public interface AirportService {

    /**
     * Save a airport.
     *
     * @param airport the entity to save
     * @return the persisted entity
     */
    Airport save(Airport airport);

    /**
     * Get all the airports.
     *
     * @return the list of entities
     */
    List<Airport> findAll();


    /**
     * Get the "id" airport.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Airport> findOne(Long id);

    /**
     * Delete the "id" airport.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the airport corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Airport> search(String query);
}
