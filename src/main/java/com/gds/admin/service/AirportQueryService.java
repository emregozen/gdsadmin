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

import com.gds.admin.domain.Airport;
import com.gds.admin.domain.*; // for static metamodels
import com.gds.admin.repository.AirportRepository;
import com.gds.admin.repository.search.AirportSearchRepository;
import com.gds.admin.service.dto.AirportCriteria;

/**
 * Service for executing complex queries for Airport entities in the database.
 * The main input is a {@link AirportCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Airport} or a {@link Page} of {@link Airport} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AirportQueryService extends QueryService<Airport> {

    private final Logger log = LoggerFactory.getLogger(AirportQueryService.class);

    private final AirportRepository airportRepository;

    private final AirportSearchRepository airportSearchRepository;

    public AirportQueryService(AirportRepository airportRepository, AirportSearchRepository airportSearchRepository) {
        this.airportRepository = airportRepository;
        this.airportSearchRepository = airportSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Airport} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Airport> findByCriteria(AirportCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Airport> specification = createSpecification(criteria);
        return airportRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Airport} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Airport> findByCriteria(AirportCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Airport> specification = createSpecification(criteria);
        return airportRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AirportCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Airport> specification = createSpecification(criteria);
        return airportRepository.count(specification);
    }

    /**
     * Function to convert AirportCriteria to a {@link Specification}
     */
    private Specification<Airport> createSpecification(AirportCriteria criteria) {
        Specification<Airport> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Airport_.id));
            }
            if (criteria.getAirportId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAirportId(), Airport_.airportId));
            }
            if (criteria.getAirportCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAirportCode(), Airport_.airportCode));
            }
            if (criteria.getCityCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCityCode(), Airport_.cityCode));
            }
            if (criteria.getCountryCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryCode(), Airport_.countryCode));
            }
            if (criteria.getIsDomestic() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDomestic(), Airport_.isDomestic));
            }
        }
        return specification;
    }
}
