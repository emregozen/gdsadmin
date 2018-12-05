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

import com.gds.admin.domain.Airline;
import com.gds.admin.domain.*; // for static metamodels
import com.gds.admin.repository.AirlineRepository;
import com.gds.admin.repository.search.AirlineSearchRepository;
import com.gds.admin.service.dto.AirlineCriteria;

/**
 * Service for executing complex queries for Airline entities in the database.
 * The main input is a {@link AirlineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Airline} or a {@link Page} of {@link Airline} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AirlineQueryService extends QueryService<Airline> {

    private final Logger log = LoggerFactory.getLogger(AirlineQueryService.class);

    private final AirlineRepository airlineRepository;

    private final AirlineSearchRepository airlineSearchRepository;

    public AirlineQueryService(AirlineRepository airlineRepository, AirlineSearchRepository airlineSearchRepository) {
        this.airlineRepository = airlineRepository;
        this.airlineSearchRepository = airlineSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Airline} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Airline> findByCriteria(AirlineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Airline> specification = createSpecification(criteria);
        return airlineRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Airline} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Airline> findByCriteria(AirlineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Airline> specification = createSpecification(criteria);
        return airlineRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AirlineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Airline> specification = createSpecification(criteria);
        return airlineRepository.count(specification);
    }

    /**
     * Function to convert AirlineCriteria to a {@link Specification}
     */
    private Specification<Airline> createSpecification(AirlineCriteria criteria) {
        Specification<Airline> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Airline_.id));
            }
            if (criteria.getAirlineId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAirlineId(), Airline_.airlineId));
            }
            if (criteria.getAirlineCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAirlineCode(), Airline_.airlineCode));
            }
            if (criteria.getAirlineName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAirlineName(), Airline_.airlineName));
            }
        }
        return specification;
    }
}
