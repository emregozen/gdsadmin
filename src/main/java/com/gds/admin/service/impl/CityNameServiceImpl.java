package com.gds.admin.service.impl;

import com.gds.admin.service.CityNameService;
import com.gds.admin.domain.CityName;
import com.gds.admin.repository.CityNameRepository;
import com.gds.admin.repository.search.CityNameSearchRepository;
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
 * Service Implementation for managing CityName.
 */
@Service
@Transactional
public class CityNameServiceImpl implements CityNameService {

    private final Logger log = LoggerFactory.getLogger(CityNameServiceImpl.class);

    private final CityNameRepository cityNameRepository;

    private final CityNameSearchRepository cityNameSearchRepository;

    public CityNameServiceImpl(CityNameRepository cityNameRepository, CityNameSearchRepository cityNameSearchRepository) {
        this.cityNameRepository = cityNameRepository;
        this.cityNameSearchRepository = cityNameSearchRepository;
    }

    /**
     * Save a cityName.
     *
     * @param cityName the entity to save
     * @return the persisted entity
     */
    @Override
    public CityName save(CityName cityName) {
        log.debug("Request to save CityName : {}", cityName);
        CityName result = cityNameRepository.save(cityName);
        cityNameSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the cityNames.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CityName> findAll() {
        log.debug("Request to get all CityNames");
        return cityNameRepository.findAll();
    }


    /**
     * Get one cityName by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CityName> findOne(Long id) {
        log.debug("Request to get CityName : {}", id);
        return cityNameRepository.findById(id);
    }

    /**
     * Delete the cityName by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CityName : {}", id);
        cityNameRepository.deleteById(id);
        cityNameSearchRepository.deleteById(id);
    }

    /**
     * Search for the cityName corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CityName> search(String query) {
        log.debug("Request to search CityNames for query {}", query);
        return StreamSupport
            .stream(cityNameSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
