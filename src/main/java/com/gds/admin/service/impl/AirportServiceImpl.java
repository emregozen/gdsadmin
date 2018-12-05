package com.gds.admin.service.impl;

import com.gds.admin.service.AirportService;
import com.gds.admin.domain.Airport;
import com.gds.admin.repository.AirportRepository;
import com.gds.admin.repository.search.AirportSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Airport.
 */
@Service
@Transactional
public class AirportServiceImpl implements AirportService {

    private final Logger log = LoggerFactory.getLogger(AirportServiceImpl.class);

    private final AirportRepository airportRepository;

    private final AirportSearchRepository airportSearchRepository;

    public AirportServiceImpl(AirportRepository airportRepository, AirportSearchRepository airportSearchRepository) {
        this.airportRepository = airportRepository;
        this.airportSearchRepository = airportSearchRepository;
    }

    /**
     * Save a airport.
     *
     * @param airport the entity to save
     * @return the persisted entity
     */
    @Override
    public Airport save(Airport airport) {
        log.debug("Request to save Airport : {}", airport);
        Airport result = airportRepository.save(airport);
        airportSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the airports.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Airport> findAll() {
        log.debug("Request to get all Airports");
        return airportRepository.findAll();
    }


    /**
     * Get one airport by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Airport> findOne(Long id) {
        log.debug("Request to get Airport : {}", id);
        return airportRepository.findById(id);
    }

    /**
     * Delete the airport by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Airport : {}", id);
        airportRepository.deleteById(id);
        airportSearchRepository.deleteById(id);
    }

    /**
     * Search for the airport corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Airport> search(String query) {
        log.debug("Request to search Airports for query {}", query);
        return StreamSupport
            .stream(airportSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
