package com.gds.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gds.admin.domain.ParameterPair;
import com.gds.admin.service.ParameterPairService;
import com.gds.admin.web.rest.errors.BadRequestAlertException;
import com.gds.admin.web.rest.util.HeaderUtil;
import com.gds.admin.service.dto.ParameterPairCriteria;
import com.gds.admin.service.ParameterPairQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ParameterPair.
 */
@RestController
@RequestMapping("/api")
public class ParameterPairResource {

    private final Logger log = LoggerFactory.getLogger(ParameterPairResource.class);

    private static final String ENTITY_NAME = "parameterPair";

    private final ParameterPairService parameterPairService;

    private final ParameterPairQueryService parameterPairQueryService;

    public ParameterPairResource(ParameterPairService parameterPairService, ParameterPairQueryService parameterPairQueryService) {
        this.parameterPairService = parameterPairService;
        this.parameterPairQueryService = parameterPairQueryService;
    }

    /**
     * POST  /parameter-pairs : Create a new parameterPair.
     *
     * @param parameterPair the parameterPair to create
     * @return the ResponseEntity with status 201 (Created) and with body the new parameterPair, or with status 400 (Bad Request) if the parameterPair has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parameter-pairs")
    @Timed
    public ResponseEntity<ParameterPair> createParameterPair(@RequestBody ParameterPair parameterPair) throws URISyntaxException {
        log.debug("REST request to save ParameterPair : {}", parameterPair);
        if (parameterPair.getId() != null) {
            throw new BadRequestAlertException("A new parameterPair cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParameterPair result = parameterPairService.save(parameterPair);
        return ResponseEntity.created(new URI("/api/parameter-pairs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parameter-pairs : Updates an existing parameterPair.
     *
     * @param parameterPair the parameterPair to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated parameterPair,
     * or with status 400 (Bad Request) if the parameterPair is not valid,
     * or with status 500 (Internal Server Error) if the parameterPair couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parameter-pairs")
    @Timed
    public ResponseEntity<ParameterPair> updateParameterPair(@RequestBody ParameterPair parameterPair) throws URISyntaxException {
        log.debug("REST request to update ParameterPair : {}", parameterPair);
        if (parameterPair.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParameterPair result = parameterPairService.save(parameterPair);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, parameterPair.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parameter-pairs : get all the parameterPairs.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of parameterPairs in body
     */
    @GetMapping("/parameter-pairs")
    @Timed
    public ResponseEntity<List<ParameterPair>> getAllParameterPairs(ParameterPairCriteria criteria) {
        log.debug("REST request to get ParameterPairs by criteria: {}", criteria);
        List<ParameterPair> entityList = parameterPairQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /parameter-pairs/count : count all the parameterPairs.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/parameter-pairs/count")
    @Timed
    public ResponseEntity<Long> countParameterPairs(ParameterPairCriteria criteria) {
        log.debug("REST request to count ParameterPairs by criteria: {}", criteria);
        return ResponseEntity.ok().body(parameterPairQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /parameter-pairs/:id : get the "id" parameterPair.
     *
     * @param id the id of the parameterPair to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parameterPair, or with status 404 (Not Found)
     */
    @GetMapping("/parameter-pairs/{id}")
    @Timed
    public ResponseEntity<ParameterPair> getParameterPair(@PathVariable Long id) {
        log.debug("REST request to get ParameterPair : {}", id);
        Optional<ParameterPair> parameterPair = parameterPairService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parameterPair);
    }

    /**
     * DELETE  /parameter-pairs/:id : delete the "id" parameterPair.
     *
     * @param id the id of the parameterPair to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parameter-pairs/{id}")
    @Timed
    public ResponseEntity<Void> deleteParameterPair(@PathVariable Long id) {
        log.debug("REST request to delete ParameterPair : {}", id);
        parameterPairService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/parameter-pairs?query=:query : search for the parameterPair corresponding
     * to the query.
     *
     * @param query the query of the parameterPair search
     * @return the result of the search
     */
    @GetMapping("/_search/parameter-pairs")
    @Timed
    public List<ParameterPair> searchParameterPairs(@RequestParam String query) {
        log.debug("REST request to search ParameterPairs for query {}", query);
        return parameterPairService.search(query);
    }

}
