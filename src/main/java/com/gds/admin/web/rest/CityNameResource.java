package com.gds.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gds.admin.domain.CityName;
import com.gds.admin.service.CityNameService;
import com.gds.admin.web.rest.errors.BadRequestAlertException;
import com.gds.admin.web.rest.util.HeaderUtil;
import com.gds.admin.service.dto.CityNameCriteria;
import com.gds.admin.service.CityNameQueryService;
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
 * REST controller for managing CityName.
 */
@RestController
@RequestMapping("/api")
public class CityNameResource {

    private final Logger log = LoggerFactory.getLogger(CityNameResource.class);

    private static final String ENTITY_NAME = "cityName";

    private final CityNameService cityNameService;

    private final CityNameQueryService cityNameQueryService;

    public CityNameResource(CityNameService cityNameService, CityNameQueryService cityNameQueryService) {
        this.cityNameService = cityNameService;
        this.cityNameQueryService = cityNameQueryService;
    }

    /**
     * POST  /city-names : Create a new cityName.
     *
     * @param cityName the cityName to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cityName, or with status 400 (Bad Request) if the cityName has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/city-names")
    @Timed
    public ResponseEntity<CityName> createCityName(@RequestBody CityName cityName) throws URISyntaxException {
        log.debug("REST request to save CityName : {}", cityName);
        if (cityName.getId() != null) {
            throw new BadRequestAlertException("A new cityName cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CityName result = cityNameService.save(cityName);
        return ResponseEntity.created(new URI("/api/city-names/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /city-names : Updates an existing cityName.
     *
     * @param cityName the cityName to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cityName,
     * or with status 400 (Bad Request) if the cityName is not valid,
     * or with status 500 (Internal Server Error) if the cityName couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/city-names")
    @Timed
    public ResponseEntity<CityName> updateCityName(@RequestBody CityName cityName) throws URISyntaxException {
        log.debug("REST request to update CityName : {}", cityName);
        if (cityName.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CityName result = cityNameService.save(cityName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cityName.getId().toString()))
            .body(result);
    }

    /**
     * GET  /city-names : get all the cityNames.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of cityNames in body
     */
    @GetMapping("/city-names")
    @Timed
    public ResponseEntity<List<CityName>> getAllCityNames(CityNameCriteria criteria) {
        log.debug("REST request to get CityNames by criteria: {}", criteria);
        List<CityName> entityList = cityNameQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /city-names/count : count all the cityNames.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/city-names/count")
    @Timed
    public ResponseEntity<Long> countCityNames(CityNameCriteria criteria) {
        log.debug("REST request to count CityNames by criteria: {}", criteria);
        return ResponseEntity.ok().body(cityNameQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /city-names/:id : get the "id" cityName.
     *
     * @param id the id of the cityName to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cityName, or with status 404 (Not Found)
     */
    @GetMapping("/city-names/{id}")
    @Timed
    public ResponseEntity<CityName> getCityName(@PathVariable Long id) {
        log.debug("REST request to get CityName : {}", id);
        Optional<CityName> cityName = cityNameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cityName);
    }

    /**
     * DELETE  /city-names/:id : delete the "id" cityName.
     *
     * @param id the id of the cityName to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/city-names/{id}")
    @Timed
    public ResponseEntity<Void> deleteCityName(@PathVariable Long id) {
        log.debug("REST request to delete CityName : {}", id);
        cityNameService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/city-names?query=:query : search for the cityName corresponding
     * to the query.
     *
     * @param query the query of the cityName search
     * @return the result of the search
     */
    @GetMapping("/_search/city-names")
    @Timed
    public List<CityName> searchCityNames(@RequestParam String query) {
        log.debug("REST request to search CityNames for query {}", query);
        return cityNameService.search(query);
    }

}
