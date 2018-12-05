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

import com.gds.admin.domain.StateName;
import com.gds.admin.domain.*; // for static metamodels
import com.gds.admin.repository.StateNameRepository;
import com.gds.admin.repository.search.StateNameSearchRepository;
import com.gds.admin.service.dto.StateNameCriteria;

/**
 * Service for executing complex queries for StateName entities in the database.
 * The main input is a {@link StateNameCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StateName} or a {@link Page} of {@link StateName} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StateNameQueryService extends QueryService<StateName> {

    private final Logger log = LoggerFactory.getLogger(StateNameQueryService.class);

    private final StateNameRepository stateNameRepository;

    private final StateNameSearchRepository stateNameSearchRepository;

    public StateNameQueryService(StateNameRepository stateNameRepository, StateNameSearchRepository stateNameSearchRepository) {
        this.stateNameRepository = stateNameRepository;
        this.stateNameSearchRepository = stateNameSearchRepository;
    }

    /**
     * Return a {@link List} of {@link StateName} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StateName> findByCriteria(StateNameCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StateName> specification = createSpecification(criteria);
        return stateNameRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link StateName} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StateName> findByCriteria(StateNameCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StateName> specification = createSpecification(criteria);
        return stateNameRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StateNameCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StateName> specification = createSpecification(criteria);
        return stateNameRepository.count(specification);
    }

    /**
     * Function to convert StateNameCriteria to a {@link Specification}
     */
    private Specification<StateName> createSpecification(StateNameCriteria criteria) {
        Specification<StateName> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StateName_.id));
            }
            if (criteria.getStateNameId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStateNameId(), StateName_.stateNameId));
            }
            if (criteria.getCountryCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryCode(), StateName_.countryCode));
            }
            if (criteria.getStateCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStateCode(), StateName_.stateCode));
            }
            if (criteria.getStateName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStateName(), StateName_.stateName));
            }
            if (criteria.getLanguage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLanguage(), StateName_.language));
            }
        }
        return specification;
    }
}
