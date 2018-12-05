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

import com.gds.admin.domain.ParameterPair;
import com.gds.admin.domain.*; // for static metamodels
import com.gds.admin.repository.ParameterPairRepository;
import com.gds.admin.repository.search.ParameterPairSearchRepository;
import com.gds.admin.service.dto.ParameterPairCriteria;

/**
 * Service for executing complex queries for ParameterPair entities in the database.
 * The main input is a {@link ParameterPairCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ParameterPair} or a {@link Page} of {@link ParameterPair} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParameterPairQueryService extends QueryService<ParameterPair> {

    private final Logger log = LoggerFactory.getLogger(ParameterPairQueryService.class);

    private final ParameterPairRepository parameterPairRepository;

    private final ParameterPairSearchRepository parameterPairSearchRepository;

    public ParameterPairQueryService(ParameterPairRepository parameterPairRepository, ParameterPairSearchRepository parameterPairSearchRepository) {
        this.parameterPairRepository = parameterPairRepository;
        this.parameterPairSearchRepository = parameterPairSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ParameterPair} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ParameterPair> findByCriteria(ParameterPairCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ParameterPair> specification = createSpecification(criteria);
        return parameterPairRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ParameterPair} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ParameterPair> findByCriteria(ParameterPairCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ParameterPair> specification = createSpecification(criteria);
        return parameterPairRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ParameterPairCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ParameterPair> specification = createSpecification(criteria);
        return parameterPairRepository.count(specification);
    }

    /**
     * Function to convert ParameterPairCriteria to a {@link Specification}
     */
    private Specification<ParameterPair> createSpecification(ParameterPairCriteria criteria) {
        Specification<ParameterPair> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ParameterPair_.id));
            }
            if (criteria.getParameterPairId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getParameterPairId(), ParameterPair_.parameterPairId));
            }
            if (criteria.getParameterKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParameterKey(), ParameterPair_.parameterKey));
            }
            if (criteria.getParameterValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParameterValue(), ParameterPair_.parameterValue));
            }
        }
        return specification;
    }
}
