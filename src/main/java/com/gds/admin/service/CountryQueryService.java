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

import com.gds.admin.domain.Country;
import com.gds.admin.domain.*; // for static metamodels
import com.gds.admin.repository.CountryRepository;
import com.gds.admin.repository.search.CountrySearchRepository;
import com.gds.admin.service.dto.CountryCriteria;

/**
 * Service for executing complex queries for Country entities in the database.
 * The main input is a {@link CountryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Country} or a {@link Page} of {@link Country} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CountryQueryService extends QueryService<Country> {

    private final Logger log = LoggerFactory.getLogger(CountryQueryService.class);

    private final CountryRepository countryRepository;

    private final CountrySearchRepository countrySearchRepository;

    public CountryQueryService(CountryRepository countryRepository, CountrySearchRepository countrySearchRepository) {
        this.countryRepository = countryRepository;
        this.countrySearchRepository = countrySearchRepository;
    }

    /**
     * Return a {@link List} of {@link Country} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Country> findByCriteria(CountryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Country> specification = createSpecification(criteria);
        return countryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Country} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Country> findByCriteria(CountryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Country> specification = createSpecification(criteria);
        return countryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CountryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Country> specification = createSpecification(criteria);
        return countryRepository.count(specification);
    }

    /**
     * Function to convert CountryCriteria to a {@link Specification}
     */
    private Specification<Country> createSpecification(CountryCriteria criteria) {
        Specification<Country> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Country_.id));
            }
            if (criteria.getCountryId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCountryId(), Country_.countryId));
            }
            if (criteria.getCountryCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryCode(), Country_.countryCode));
            }
            if (criteria.getCountry3Code() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry3Code(), Country_.country3Code));
            }
            if (criteria.getPhoneCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneCode(), Country_.phoneCode));
            }
            if (criteria.getNumericCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumericCode(), Country_.numericCode));
            }
        }
        return specification;
    }
}
