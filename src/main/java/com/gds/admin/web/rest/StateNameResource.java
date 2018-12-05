package com.gds.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gds.admin.domain.StateName;
import com.gds.admin.service.StateNameService;
import com.gds.admin.web.rest.errors.BadRequestAlertException;
import com.gds.admin.web.rest.util.HeaderUtil;
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
 * REST controller for managing StateName.
 */
@RestController
@RequestMapping("/api")
public class StateNameResource {

    private final Logger log = LoggerFactory.getLogger(StateNameResource.class);

    private static final String ENTITY_NAME = "stateName";

    private final StateNameService stateNameService;

    public StateNameResource(StateNameService stateNameService) {
        this.stateNameService = stateNameService;
    }

    /**
     * POST  /state-names : Create a new stateName.
     *
     * @param stateName the stateName to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stateName, or with status 400 (Bad Request) if the stateName has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/state-names")
    @Timed
    public ResponseEntity<StateName> createStateName(@RequestBody StateName stateName) throws URISyntaxException {
        log.debug("REST request to save StateName : {}", stateName);
        if (stateName.getId() != null) {
            throw new BadRequestAlertException("A new stateName cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StateName result = stateNameService.save(stateName);
        return ResponseEntity.created(new URI("/api/state-names/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /state-names : Updates an existing stateName.
     *
     * @param stateName the stateName to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stateName,
     * or with status 400 (Bad Request) if the stateName is not valid,
     * or with status 500 (Internal Server Error) if the stateName couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/state-names")
    @Timed
    public ResponseEntity<StateName> updateStateName(@RequestBody StateName stateName) throws URISyntaxException {
        log.debug("REST request to update StateName : {}", stateName);
        if (stateName.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StateName result = stateNameService.save(stateName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stateName.getId().toString()))
            .body(result);
    }

    /**
     * GET  /state-names : get all the stateNames.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stateNames in body
     */
    @GetMapping("/state-names")
    @Timed
    public List<StateName> getAllStateNames() {
        log.debug("REST request to get all StateNames");
        return stateNameService.findAll();
    }

    /**
     * GET  /state-names/:id : get the "id" stateName.
     *
     * @param id the id of the stateName to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stateName, or with status 404 (Not Found)
     */
    @GetMapping("/state-names/{id}")
    @Timed
    public ResponseEntity<StateName> getStateName(@PathVariable Long id) {
        log.debug("REST request to get StateName : {}", id);
        Optional<StateName> stateName = stateNameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stateName);
    }

    /**
     * DELETE  /state-names/:id : delete the "id" stateName.
     *
     * @param id the id of the stateName to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/state-names/{id}")
    @Timed
    public ResponseEntity<Void> deleteStateName(@PathVariable Long id) {
        log.debug("REST request to delete StateName : {}", id);
        stateNameService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/state-names?query=:query : search for the stateName corresponding
     * to the query.
     *
     * @param query the query of the stateName search
     * @return the result of the search
     */
    @GetMapping("/_search/state-names")
    @Timed
    public List<StateName> searchStateNames(@RequestParam String query) {
        log.debug("REST request to search StateNames for query {}", query);
        return stateNameService.search(query);
    }

}
