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

import com.gds.admin.domain.AirportName;
import com.gds.admin.domain.*; // for static metamodels
import com.gds.admin.repository.AirportNameRepository;
import com.gds.admin.repository.search.AirportNameSearchRepository;
import com.gds.admin.service.dto.AirportNameCriteria;

/**
 * Service for executing complex queries for AirportName entities in the database.
 * The main input is a {@link AirportNameCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AirportName} or a {@link Page} of {@link AirportName} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AirportNameQueryService extends QueryService<AirportName> {

    private final Logger log = LoggerFactory.getLogger(AirportNameQueryService.class);

    private final AirportNameRepository airportNameRepository;

    private final AirportNameSearchRepository airportNameSearchRepository;

    public AirportNameQueryService(AirportNameRepository airportNameRepository, AirportNameSearchRepository airportNameSearchRepository) {
        this.airportNameRepository = airportNameRepository;
        this.airportNameSearchRepository = airportNameSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AirportName} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AirportName> findByCriteria(AirportNameCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AirportName> specification = createSpecification(criteria);
        return airportNameRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link AirportName} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AirportName> findByCriteria(AirportNameCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AirportName> specification = createSpecification(criteria);
        return airportNameRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AirportNameCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AirportName> specification = createSpecification(criteria);
        return airportNameRepository.count(specification);
    }

    /**
     * Function to convert AirportNameCriteria to a {@link Specification}
     */
    private Specification<AirportName> createSpecification(AirportNameCriteria criteria) {
        Specification<AirportName> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AirportName_.id));
            }
            if (criteria.getAirportNameId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAirportNameId(), AirportName_.airportNameId));
            }
            if (criteria.getAirportCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAirportCode(), AirportName_.airportCode));
            }
            if (criteria.getLanguage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLanguage(), AirportName_.language));
            }
        }
        return specification;
    }
}
