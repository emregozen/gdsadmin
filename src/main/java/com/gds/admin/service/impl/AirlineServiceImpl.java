package com.gds.admin.service.impl;

import com.gds.admin.service.AirlineService;
import com.gds.admin.domain.Airline;
import com.gds.admin.repository.AirlineRepository;
import com.gds.admin.repository.search.AirlineSearchRepository;
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
 * Service Implementation for managing Airline.
 */
@Service
@Transactional
public class AirlineServiceImpl implements AirlineService {

    private final Logger log = LoggerFactory.getLogger(AirlineServiceImpl.class);

    private final AirlineRepository airlineRepository;

    private final AirlineSearchRepository airlineSearchRepository;

    public AirlineServiceImpl(AirlineRepository airlineRepository, AirlineSearchRepository airlineSearchRepository) {
        this.airlineRepository = airlineRepository;
        this.airlineSearchRepository = airlineSearchRepository;
    }

    /**
     * Save a airline.
     *
     * @param airline the entity to save
     * @return the persisted entity
     */
    @Override
    public Airline save(Airline airline) {
        log.debug("Request to save Airline : {}", airline);
        Airline result = airlineRepository.save(airline);
        airlineSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the airlines.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Airline> findAll() {
        log.debug("Request to get all Airlines");
        return airlineRepository.findAll();
    }


    /**
     * Get one airline by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Airline> findOne(Long id) {
        log.debug("Request to get Airline : {}", id);
        return airlineRepository.findById(id);
    }

    /**
     * Delete the airline by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Airline : {}", id);
        airlineRepository.deleteById(id);
        airlineSearchRepository.deleteById(id);
    }

    /**
     * Search for the airline corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Airline> search(String query) {
        log.debug("Request to search Airlines for query {}", query);
        return StreamSupport
            .stream(airlineSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
