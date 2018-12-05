package com.gds.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gds.admin.domain.CountryName;
import com.gds.admin.service.CountryNameService;
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
 * REST controller for managing CountryName.
 */
@RestController
@RequestMapping("/api")
public class CountryNameResource {

    private final Logger log = LoggerFactory.getLogger(CountryNameResource.class);

    private static final String ENTITY_NAME = "countryName";

    private final CountryNameService countryNameService;

    public CountryNameResource(CountryNameService countryNameService) {
        this.countryNameService = countryNameService;
    }

    /**
     * POST  /country-names : Create a new countryName.
     *
     * @param countryName the countryName to create
     * @return the ResponseEntity with status 201 (Created) and with body the new countryName, or with status 400 (Bad Request) if the countryName has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/country-names")
    @Timed
    public ResponseEntity<CountryName> createCountryName(@RequestBody CountryName countryName) throws URISyntaxException {
        log.debug("REST request to save CountryName : {}", countryName);
        if (countryName.getId() != null) {
            throw new BadRequestAlertException("A new countryName cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CountryName result = countryNameService.save(countryName);
        return ResponseEntity.created(new URI("/api/country-names/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /country-names : Updates an existing countryName.
     *
     * @param countryName the countryName to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated countryName,
     * or with status 400 (Bad Request) if the countryName is not valid,
     * or with status 500 (Internal Server Error) if the countryName couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/country-names")
    @Timed
    public ResponseEntity<CountryName> updateCountryName(@RequestBody CountryName countryName) throws URISyntaxException {
        log.debug("REST request to update CountryName : {}", countryName);
        if (countryName.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CountryName result = countryNameService.save(countryName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, countryName.getId().toString()))
            .body(result);
    }

    /**
     * GET  /country-names : get all the countryNames.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of countryNames in body
     */
    @GetMapping("/country-names")
    @Timed
    public List<CountryName> getAllCountryNames() {
        log.debug("REST request to get all CountryNames");
        return countryNameService.findAll();
    }

    /**
     * GET  /country-names/:id : get the "id" countryName.
     *
     * @param id the id of the countryName to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the countryName, or with status 404 (Not Found)
     */
    @GetMapping("/country-names/{id}")
    @Timed
    public ResponseEntity<CountryName> getCountryName(@PathVariable Long id) {
        log.debug("REST request to get CountryName : {}", id);
        Optional<CountryName> countryName = countryNameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(countryName);
    }

    /**
     * DELETE  /country-names/:id : delete the "id" countryName.
     *
     * @param id the id of the countryName to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/country-names/{id}")
    @Timed
    public ResponseEntity<Void> deleteCountryName(@PathVariable Long id) {
        log.debug("REST request to delete CountryName : {}", id);
        countryNameService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/country-names?query=:query : search for the countryName corresponding
     * to the query.
     *
     * @param query the query of the countryName search
     * @return the result of the search
     */
    @GetMapping("/_search/country-names")
    @Timed
    public List<CountryName> searchCountryNames(@RequestParam String query) {
        log.debug("REST request to search CountryNames for query {}", query);
        return countryNameService.search(query);
    }

}
