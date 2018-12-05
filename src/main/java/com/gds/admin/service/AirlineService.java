package com.gds.admin.service;

import com.gds.admin.domain.Airline;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Airline.
 */
public interface AirlineService {

    /**
     * Save a airline.
     *
     * @param airline the entity to save
     * @return the persisted entity
     */
    Airline save(Airline airline);

    /**
     * Get all the airlines.
     *
     * @return the list of entities
     */
    List<Airline> findAll();


    /**
     * Get the "id" airline.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Airline> findOne(Long id);

    /**
     * Delete the "id" airline.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the airline corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Airline> search(String query);
}
