package com.gds.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gds.admin.domain.Airport;
import com.gds.admin.service.AirportService;
import com.gds.admin.web.rest.errors.BadRequestAlertException;
import com.gds.admin.web.rest.util.HeaderUtil;
import com.gds.admin.service.dto.AirportCriteria;
import com.gds.admin.service.AirportQueryService;
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
 * REST controller for managing Airport.
 */
@RestController
@RequestMapping("/api")
public class AirportResource {

    private final Logger log = LoggerFactory.getLogger(AirportResource.class);

    private static final String ENTITY_NAME = "airport";

    private final AirportService airportService;

    private final AirportQueryService airportQueryService;

    public AirportResource(AirportService airportService, AirportQueryService airportQueryService) {
        this.airportService = airportService;
        this.airportQueryService = airportQueryService;
    }

    /**
     * POST  /airports : Create a new airport.
     *
     * @param airport the airport to create
     * @return the ResponseEntity with status 201 (Created) and with body the new airport, or with status 400 (Bad Request) if the airport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/airports")
    @Timed
    public ResponseEntity<Airport> createAirport(@RequestBody Airport airport) throws URISyntaxException {
        log.debug("REST request to save Airport : {}", airport);
        if (airport.getId() != null) {
            throw new BadRequestAlertException("A new airport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Airport result = airportService.save(airport);
        return ResponseEntity.created(new URI("/api/airports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /airports : Updates an existing airport.
     *
     * @param airport the airport to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated airport,
     * or with status 400 (Bad Request) if the airport is not valid,
     * or with status 500 (Internal Server Error) if the airport couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/airports")
    @Timed
    public ResponseEntity<Airport> updateAirport(@RequestBody Airport airport) throws URISyntaxException {
        log.debug("REST request to update Airport : {}", airport);
        if (airport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Airport result = airportService.save(airport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, airport.getId().toString()))
            .body(result);
    }

    /**
     * GET  /airports : get all the airports.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of airports in body
     */
    @GetMapping("/airports")
    @Timed
    public ResponseEntity<List<Airport>> getAllAirports(AirportCriteria criteria) {
        log.debug("REST request to get Airports by criteria: {}", criteria);
        List<Airport> entityList = airportQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /airports/count : count all the airports.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/airports/count")
    @Timed
    public ResponseEntity<Long> countAirports(AirportCriteria criteria) {
        log.debug("REST request to count Airports by criteria: {}", criteria);
        return ResponseEntity.ok().body(airportQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /airports/:id : get the "id" airport.
     *
     * @param id the id of the airport to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the airport, or with status 404 (Not Found)
     */
    @GetMapping("/airports/{id}")
    @Timed
    public ResponseEntity<Airport> getAirport(@PathVariable Long id) {
        log.debug("REST request to get Airport : {}", id);
        Optional<Airport> airport = airportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(airport);
    }

    /**
     * DELETE  /airports/:id : delete the "id" airport.
     *
     * @param id the id of the airport to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/airports/{id}")
    @Timed
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
        log.debug("REST request to delete Airport : {}", id);
        airportService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/airports?query=:query : search for the airport corresponding
     * to the query.
     *
     * @param query the query of the airport search
     * @return the result of the search
     */
    @GetMapping("/_search/airports")
    @Timed
    public List<Airport> searchAirports(@RequestParam String query) {
        log.debug("REST request to search Airports for query {}", query);
        return airportService.search(query);
    }

}
