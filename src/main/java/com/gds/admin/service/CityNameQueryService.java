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

import com.gds.admin.domain.CityName;
import com.gds.admin.domain.*; // for static metamodels
import com.gds.admin.repository.CityNameRepository;
import com.gds.admin.repository.search.CityNameSearchRepository;
import com.gds.admin.service.dto.CityNameCriteria;

/**
 * Service for executing complex queries for CityName entities in the database.
 * The main input is a {@link CityNameCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CityName} or a {@link Page} of {@link CityName} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CityNameQueryService extends QueryService<CityName> {

    private final Logger log = LoggerFactory.getLogger(CityNameQueryService.class);

    private final CityNameRepository cityNameRepository;

    private final CityNameSearchRepository cityNameSearchRepository;

    public CityNameQueryService(CityNameRepository cityNameRepository, CityNameSearchRepository cityNameSearchRepository) {
        this.cityNameRepository = cityNameRepository;
        this.cityNameSearchRepository = cityNameSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CityName} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CityName> findByCriteria(CityNameCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CityName> specification = createSpecification(criteria);
        return cityNameRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CityName} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CityName> findByCriteria(CityNameCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CityName> specification = createSpecification(criteria);
        return cityNameRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CityNameCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CityName> specification = createSpecification(criteria);
        return cityNameRepository.count(specification);
    }

    /**
     * Function to convert CityNameCriteria to a {@link Specification}
     */
    private Specification<CityName> createSpecification(CityNameCriteria criteria) {
        Specification<CityName> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CityName_.id));
            }
            if (criteria.getCityNameId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCityNameId(), CityName_.cityNameId));
            }
            if (criteria.getCityCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCityCode(), CityName_.cityCode));
            }
            if (criteria.getCityName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCityName(), CityName_.cityName));
            }
            if (criteria.getLanguage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLanguage(), CityName_.language));
            }
        }
        return specification;
    }
}
