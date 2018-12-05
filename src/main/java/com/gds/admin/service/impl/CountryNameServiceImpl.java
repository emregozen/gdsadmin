package com.gds.admin.service.impl;

import com.gds.admin.service.CountryNameService;
import com.gds.admin.domain.CountryName;
import com.gds.admin.repository.CountryNameRepository;
import com.gds.admin.repository.search.CountryNameSearchRepository;
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
 * Service Implementation for managing CountryName.
 */
@Service
@Transactional
public class CountryNameServiceImpl implements CountryNameService {

    private final Logger log = LoggerFactory.getLogger(CountryNameServiceImpl.class);

    private final CountryNameRepository countryNameRepository;

    private final CountryNameSearchRepository countryNameSearchRepository;

    public CountryNameServiceImpl(CountryNameRepository countryNameRepository, CountryNameSearchRepository countryNameSearchRepository) {
        this.countryNameRepository = countryNameRepository;
        this.countryNameSearchRepository = countryNameSearchRepository;
    }

    /**
     * Save a countryName.
     *
     * @param countryName the entity to save
     * @return the persisted entity
     */
    @Override
    public CountryName save(CountryName countryName) {
        log.debug("Request to save CountryName : {}", countryName);
        CountryName result = countryNameRepository.save(countryName);
        countryNameSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the countryNames.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CountryName> findAll() {
        log.debug("Request to get all CountryNames");
        return countryNameRepository.findAll();
    }


    /**
     * Get one countryName by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CountryName> findOne(Long id) {
        log.debug("Request to get CountryName : {}", id);
        return countryNameRepository.findById(id);
    }

    /**
     * Delete the countryName by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CountryName : {}", id);
        countryNameRepository.deleteById(id);
        countryNameSearchRepository.deleteById(id);
    }

    /**
     * Search for the countryName corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CountryName> search(String query) {
        log.debug("Request to search CountryNames for query {}", query);
        return StreamSupport
            .stream(countryNameSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
