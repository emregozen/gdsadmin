package com.gds.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gds.admin.domain.AirportName;
import com.gds.admin.service.AirportNameService;
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
 * REST controller for managing AirportName.
 */
@RestController
@RequestMapping("/api")
public class AirportNameResource {

    private final Logger log = LoggerFactory.getLogger(AirportNameResource.class);

    private static final String ENTITY_NAME = "airportName";

    private final AirportNameService airportNameService;

    public AirportNameResource(AirportNameService airportNameService) {
        this.airportNameService = airportNameService;
    }

    /**
     * POST  /airport-names : Create a new airportName.
     *
     * @param airportName the airportName to create
     * @return the ResponseEntity with status 201 (Created) and with body the new airportName, or with status 400 (Bad Request) if the airportName has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/airport-names")
    @Timed
    public ResponseEntity<AirportName> createAirportName(@RequestBody AirportName airportName) throws URISyntaxException {
        log.debug("REST request to save AirportName : {}", airportName);
        if (airportName.getId() != null) {
            throw new BadRequestAlertException("A new airportName cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AirportName result = airportNameService.save(airportName);
        return ResponseEntity.created(new URI("/api/airport-names/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /airport-names : Updates an existing airportName.
     *
     * @param airportName the airportName to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated airportName,
     * or with status 400 (Bad Request) if the airportName is not valid,
     * or with status 500 (Internal Server Error) if the airportName couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/airport-names")
    @Timed
    public ResponseEntity<AirportName> updateAirportName(@RequestBody AirportName airportName) throws URISyntaxException {
        log.debug("REST request to update AirportName : {}", airportName);
        if (airportName.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AirportName result = airportNameService.save(airportName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, airportName.getId().toString()))
            .body(result);
    }

    /**
     * GET  /airport-names : get all the airportNames.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of airportNames in body
     */
    @GetMapping("/airport-names")
    @Timed
    public List<AirportName> getAllAirportNames() {
        log.debug("REST request to get all AirportNames");
        return airportNameService.findAll();
    }

    /**
     * GET  /airport-names/:id : get the "id" airportName.
     *
     * @param id the id of the airportName to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the airportName, or with status 404 (Not Found)
     */
    @GetMapping("/airport-names/{id}")
    @Timed
    public ResponseEntity<AirportName> getAirportName(@PathVariable Long id) {
        log.debug("REST request to get AirportName : {}", id);
        Optional<AirportName> airportName = airportNameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(airportName);
    }

    /**
     * DELETE  /airport-names/:id : delete the "id" airportName.
     *
     * @param id the id of the airportName to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/airport-names/{id}")
    @Timed
    public ResponseEntity<Void> deleteAirportName(@PathVariable Long id) {
        log.debug("REST request to delete AirportName : {}", id);
        airportNameService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/airport-names?query=:query : search for the airportName corresponding
     * to the query.
     *
     * @param query the query of the airportName search
     * @return the result of the search
     */
    @GetMapping("/_search/airport-names")
    @Timed
    public List<AirportName> searchAirportNames(@RequestParam String query) {
        log.debug("REST request to search AirportNames for query {}", query);
        return airportNameService.search(query);
    }

}
