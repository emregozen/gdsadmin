package com.gds.admin.service.impl;

import com.gds.admin.service.ParameterPairService;
import com.gds.admin.domain.ParameterPair;
import com.gds.admin.repository.ParameterPairRepository;
import com.gds.admin.repository.search.ParameterPairSearchRepository;
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
 * Service Implementation for managing ParameterPair.
 */
@Service
@Transactional
public class ParameterPairServiceImpl implements ParameterPairService {

    private final Logger log = LoggerFactory.getLogger(ParameterPairServiceImpl.class);

    private final ParameterPairRepository parameterPairRepository;

    private final ParameterPairSearchRepository parameterPairSearchRepository;

    public ParameterPairServiceImpl(ParameterPairRepository parameterPairRepository, ParameterPairSearchRepository parameterPairSearchRepository) {
        this.parameterPairRepository = parameterPairRepository;
        this.parameterPairSearchRepository = parameterPairSearchRepository;
    }

    /**
     * Save a parameterPair.
     *
     * @param parameterPair the entity to save
     * @return the persisted entity
     */
    @Override
    public ParameterPair save(ParameterPair parameterPair) {
        log.debug("Request to save ParameterPair : {}", parameterPair);
        ParameterPair result = parameterPairRepository.save(parameterPair);
        parameterPairSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the parameterPairs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ParameterPair> findAll() {
        log.debug("Request to get all ParameterPairs");
        return parameterPairRepository.findAll();
    }


    /**
     * Get one parameterPair by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParameterPair> findOne(Long id) {
        log.debug("Request to get ParameterPair : {}", id);
        return parameterPairRepository.findById(id);
    }

    /**
     * Delete the parameterPair by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParameterPair : {}", id);
        parameterPairRepository.deleteById(id);
        parameterPairSearchRepository.deleteById(id);
    }

    /**
     * Search for the parameterPair corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ParameterPair> search(String query) {
        log.debug("Request to search ParameterPairs for query {}", query);
        return StreamSupport
            .stream(parameterPairSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
