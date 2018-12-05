package com.gds.admin.service.impl;

import com.gds.admin.service.StateNameService;
import com.gds.admin.domain.StateName;
import com.gds.admin.repository.StateNameRepository;
import com.gds.admin.repository.search.StateNameSearchRepository;
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
 * Service Implementation for managing StateName.
 */
@Service
@Transactional
public class StateNameServiceImpl implements StateNameService {

    private final Logger log = LoggerFactory.getLogger(StateNameServiceImpl.class);

    private final StateNameRepository stateNameRepository;

    private final StateNameSearchRepository stateNameSearchRepository;

    public StateNameServiceImpl(StateNameRepository stateNameRepository, StateNameSearchRepository stateNameSearchRepository) {
        this.stateNameRepository = stateNameRepository;
        this.stateNameSearchRepository = stateNameSearchRepository;
    }

    /**
     * Save a stateName.
     *
     * @param stateName the entity to save
     * @return the persisted entity
     */
    @Override
    public StateName save(StateName stateName) {
        log.debug("Request to save StateName : {}", stateName);
        StateName result = stateNameRepository.save(stateName);
        stateNameSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the stateNames.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<StateName> findAll() {
        log.debug("Request to get all StateNames");
        return stateNameRepository.findAll();
    }


    /**
     * Get one stateName by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StateName> findOne(Long id) {
        log.debug("Request to get StateName : {}", id);
        return stateNameRepository.findById(id);
    }

    /**
     * Delete the stateName by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StateName : {}", id);
        stateNameRepository.deleteById(id);
        stateNameSearchRepository.deleteById(id);
    }

    /**
     * Search for the stateName corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<StateName> search(String query) {
        log.debug("Request to search StateNames for query {}", query);
        return StreamSupport
            .stream(stateNameSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
