package com.gds.admin.service;

import com.gds.admin.domain.CountryName;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CountryName.
 */
public interface CountryNameService {

    /**
     * Save a countryName.
     *
     * @param countryName the entity to save
     * @return the persisted entity
     */
    CountryName save(CountryName countryName);

    /**
     * Get all the countryNames.
     *
     * @return the list of entities
     */
    List<CountryName> findAll();


    /**
     * Get the "id" countryName.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CountryName> findOne(Long id);

    /**
     * Delete the "id" countryName.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the countryName corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<CountryName> search(String query);
}
