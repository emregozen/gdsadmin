package com.gds.admin.service.impl;

import com.gds.admin.service.AirportNameService;
import com.gds.admin.domain.AirportName;
import com.gds.admin.repository.AirportNameRepository;
import com.gds.admin.repository.search.AirportNameSearchRepository;
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
 * Service Implementation for managing AirportName.
 */
@Service
@Transactional
public class AirportNameServiceImpl implements AirportNameService {

    private final Logger log = LoggerFactory.getLogger(AirportNameServiceImpl.class);

    private final AirportNameRepository airportNameRepository;

    private final AirportNameSearchRepository airportNameSearchRepository;

    public AirportNameServiceImpl(AirportNameRepository airportNameRepository, AirportNameSearchRepository airportNameSearchRepository) {
        this.airportNameRepository = airportNameRepository;
        this.airportNameSearchRepository = airportNameSearchRepository;
    }

    /**
     * Save a airportName.
     *
     * @param airportName the entity to save
     * @return the persisted entity
     */
    @Override
    public AirportName save(AirportName airportName) {
        log.debug("Request to save AirportName : {}", airportName);
        AirportName result = airportNameRepository.save(airportName);
        airportNameSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the airportNames.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AirportName> findAll() {
        log.debug("Request to get all AirportNames");
        return airportNameRepository.findAll();
    }


    /**
     * Get one airportName by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AirportName> findOne(Long id) {
        log.debug("Request to get AirportName : {}", id);
        return airportNameRepository.findById(id);
    }

    /**
     * Delete the airportName by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AirportName : {}", id);
        airportNameRepository.deleteById(id);
        airportNameSearchRepository.deleteById(id);
    }

    /**
     * Search for the airportName corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AirportName> search(String query) {
        log.debug("Request to search AirportNames for query {}", query);
        return StreamSupport
            .stream(airportNameSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
