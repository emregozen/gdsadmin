package com.gds.admin.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.gds.admin.domain.City;
import com.gds.admin.domain.*; // for static metamodels
import com.gds.admin.repository.CityRepository;
import com.gds.admin.repository.search.CitySearchRepository;
import com.gds.admin.service.dto.CityCriteria;

/**
 * Service for executing complex queries for City entities in the database.
 * The main input is a {@link CityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link City} or a {@link Page} of {@link City} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CityQueryService extends QueryService<City> {

    private final Logger log = LoggerFactory.getLogger(CityQueryService.class);

    private final CityRepository cityRepository;

    private final CitySearchRepository citySearchRepository;

    public CityQueryService(CityRepository cityRepository, CitySearchRepository citySearchRepository) {
        this.cityRepository = cityRepository;
        this.citySearchRepository = citySearchRepository;
    }

    /**
     * Return a {@link List} of {@link City} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<City> findByCriteria(CityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<City> specification = createSpecification(criteria);
        return cityRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link City} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<City> findByCriteria(CityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<City> specification = createSpecification(criteria);
        return cityRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<City> specification = createSpecification(criteria);
        return cityRepository.count(specification);
    }

    /**
     * Function to convert CityCriteria to a {@link Specification}
     */
    private Specification<City> createSpecification(CityCriteria criteria) {
        Specification<City> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), City_.id));
            }
            if (criteria.getCityId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCityId(), City_.cityId));
            }
            if (criteria.getCityCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCityCode(), City_.cityCode));
            }
            if (criteria.getCountryCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryCode(), City_.countryCode));
            }
        }
        return specification;
    }
}
