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

import com.gds.admin.domain.CountryName;
import com.gds.admin.domain.*; // for static metamodels
import com.gds.admin.repository.CountryNameRepository;
import com.gds.admin.repository.search.CountryNameSearchRepository;
import com.gds.admin.service.dto.CountryNameCriteria;

/**
 * Service for executing complex queries for CountryName entities in the database.
 * The main input is a {@link CountryNameCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CountryName} or a {@link Page} of {@link CountryName} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CountryNameQueryService extends QueryService<CountryName> {

    private final Logger log = LoggerFactory.getLogger(CountryNameQueryService.class);

    private final CountryNameRepository countryNameRepository;

    private final CountryNameSearchRepository countryNameSearchRepository;

    public CountryNameQueryService(CountryNameRepository countryNameRepository, CountryNameSearchRepository countryNameSearchRepository) {
        this.countryNameRepository = countryNameRepository;
        this.countryNameSearchRepository = countryNameSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CountryName} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CountryName> findByCriteria(CountryNameCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CountryName> specification = createSpecification(criteria);
        return countryNameRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CountryName} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CountryName> findByCriteria(CountryNameCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CountryName> specification = createSpecification(criteria);
        return countryNameRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CountryNameCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CountryName> specification = createSpecification(criteria);
        return countryNameRepository.count(specification);
    }

    /**
     * Function to convert CountryNameCriteria to a {@link Specification}
     */
    private Specification<CountryName> createSpecification(CountryNameCriteria criteria) {
        Specification<CountryName> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CountryName_.id));
            }
            if (criteria.getCountryNameId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCountryNameId(), CountryName_.countryNameId));
            }
            if (criteria.getCountryCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryCode(), CountryName_.countryCode));
            }
            if (criteria.getCountryName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryName(), CountryName_.countryName));
            }
            if (criteria.getLanguage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLanguage(), CountryName_.language));
            }
        }
        return specification;
    }
}
