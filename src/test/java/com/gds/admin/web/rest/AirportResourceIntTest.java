package com.gds.admin.web.rest;

import com.gds.admin.GdsadminApp;

import com.gds.admin.domain.Airport;
import com.gds.admin.repository.AirportRepository;
import com.gds.admin.repository.search.AirportSearchRepository;
import com.gds.admin.service.AirportService;
import com.gds.admin.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.gds.admin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AirportResource REST controller.
 *
 * @see AirportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GdsadminApp.class)
public class AirportResourceIntTest {

    private static final Long DEFAULT_AIRPORT_ID = 1L;
    private static final Long UPDATED_AIRPORT_ID = 2L;

    private static final String DEFAULT_AIRPORT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_AIRPORT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CITY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DOMESTIC = false;
    private static final Boolean UPDATED_IS_DOMESTIC = true;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private AirportService airportService;

    /**
     * This repository is mocked in the com.gds.admin.repository.search test package.
     *
     * @see com.gds.admin.repository.search.AirportSearchRepositoryMockConfiguration
     */
    @Autowired
    private AirportSearchRepository mockAirportSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAirportMockMvc;

    private Airport airport;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AirportResource airportResource = new AirportResource(airportService);
        this.restAirportMockMvc = MockMvcBuilders.standaloneSetup(airportResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Airport createEntity(EntityManager em) {
        Airport airport = new Airport()
            .airportId(DEFAULT_AIRPORT_ID)
            .airportCode(DEFAULT_AIRPORT_CODE)
            .cityCode(DEFAULT_CITY_CODE)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .isDomestic(DEFAULT_IS_DOMESTIC);
        return airport;
    }

    @Before
    public void initTest() {
        airport = createEntity(em);
    }

    @Test
    @Transactional
    public void createAirport() throws Exception {
        int databaseSizeBeforeCreate = airportRepository.findAll().size();

        // Create the Airport
        restAirportMockMvc.perform(post("/api/airports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airport)))
            .andExpect(status().isCreated());

        // Validate the Airport in the database
        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeCreate + 1);
        Airport testAirport = airportList.get(airportList.size() - 1);
        assertThat(testAirport.getAirportId()).isEqualTo(DEFAULT_AIRPORT_ID);
        assertThat(testAirport.getAirportCode()).isEqualTo(DEFAULT_AIRPORT_CODE);
        assertThat(testAirport.getCityCode()).isEqualTo(DEFAULT_CITY_CODE);
        assertThat(testAirport.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testAirport.isIsDomestic()).isEqualTo(DEFAULT_IS_DOMESTIC);

        // Validate the Airport in Elasticsearch
        verify(mockAirportSearchRepository, times(1)).save(testAirport);
    }

    @Test
    @Transactional
    public void createAirportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = airportRepository.findAll().size();

        // Create the Airport with an existing ID
        airport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAirportMockMvc.perform(post("/api/airports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airport)))
            .andExpect(status().isBadRequest());

        // Validate the Airport in the database
        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeCreate);

        // Validate the Airport in Elasticsearch
        verify(mockAirportSearchRepository, times(0)).save(airport);
    }

    @Test
    @Transactional
    public void getAllAirports() throws Exception {
        // Initialize the database
        airportRepository.saveAndFlush(airport);

        // Get all the airportList
        restAirportMockMvc.perform(get("/api/airports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(airport.getId().intValue())))
            .andExpect(jsonPath("$.[*].airportId").value(hasItem(DEFAULT_AIRPORT_ID.intValue())))
            .andExpect(jsonPath("$.[*].airportCode").value(hasItem(DEFAULT_AIRPORT_CODE.toString())))
            .andExpect(jsonPath("$.[*].cityCode").value(hasItem(DEFAULT_CITY_CODE.toString())))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE.toString())))
            .andExpect(jsonPath("$.[*].isDomestic").value(hasItem(DEFAULT_IS_DOMESTIC.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAirport() throws Exception {
        // Initialize the database
        airportRepository.saveAndFlush(airport);

        // Get the airport
        restAirportMockMvc.perform(get("/api/airports/{id}", airport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(airport.getId().intValue()))
            .andExpect(jsonPath("$.airportId").value(DEFAULT_AIRPORT_ID.intValue()))
            .andExpect(jsonPath("$.airportCode").value(DEFAULT_AIRPORT_CODE.toString()))
            .andExpect(jsonPath("$.cityCode").value(DEFAULT_CITY_CODE.toString()))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE.toString()))
            .andExpect(jsonPath("$.isDomestic").value(DEFAULT_IS_DOMESTIC.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAirport() throws Exception {
        // Get the airport
        restAirportMockMvc.perform(get("/api/airports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAirport() throws Exception {
        // Initialize the database
        airportService.save(airport);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockAirportSearchRepository);

        int databaseSizeBeforeUpdate = airportRepository.findAll().size();

        // Update the airport
        Airport updatedAirport = airportRepository.findById(airport.getId()).get();
        // Disconnect from session so that the updates on updatedAirport are not directly saved in db
        em.detach(updatedAirport);
        updatedAirport
            .airportId(UPDATED_AIRPORT_ID)
            .airportCode(UPDATED_AIRPORT_CODE)
            .cityCode(UPDATED_CITY_CODE)
            .countryCode(UPDATED_COUNTRY_CODE)
            .isDomestic(UPDATED_IS_DOMESTIC);

        restAirportMockMvc.perform(put("/api/airports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAirport)))
            .andExpect(status().isOk());

        // Validate the Airport in the database
        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeUpdate);
        Airport testAirport = airportList.get(airportList.size() - 1);
        assertThat(testAirport.getAirportId()).isEqualTo(UPDATED_AIRPORT_ID);
        assertThat(testAirport.getAirportCode()).isEqualTo(UPDATED_AIRPORT_CODE);
        assertThat(testAirport.getCityCode()).isEqualTo(UPDATED_CITY_CODE);
        assertThat(testAirport.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testAirport.isIsDomestic()).isEqualTo(UPDATED_IS_DOMESTIC);

        // Validate the Airport in Elasticsearch
        verify(mockAirportSearchRepository, times(1)).save(testAirport);
    }

    @Test
    @Transactional
    public void updateNonExistingAirport() throws Exception {
        int databaseSizeBeforeUpdate = airportRepository.findAll().size();

        // Create the Airport

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAirportMockMvc.perform(put("/api/airports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airport)))
            .andExpect(status().isBadRequest());

        // Validate the Airport in the database
        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Airport in Elasticsearch
        verify(mockAirportSearchRepository, times(0)).save(airport);
    }

    @Test
    @Transactional
    public void deleteAirport() throws Exception {
        // Initialize the database
        airportService.save(airport);

        int databaseSizeBeforeDelete = airportRepository.findAll().size();

        // Get the airport
        restAirportMockMvc.perform(delete("/api/airports/{id}", airport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Airport in Elasticsearch
        verify(mockAirportSearchRepository, times(1)).deleteById(airport.getId());
    }

    @Test
    @Transactional
    public void searchAirport() throws Exception {
        // Initialize the database
        airportService.save(airport);
        when(mockAirportSearchRepository.search(queryStringQuery("id:" + airport.getId())))
            .thenReturn(Collections.singletonList(airport));
        // Search the airport
        restAirportMockMvc.perform(get("/api/_search/airports?query=id:" + airport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(airport.getId().intValue())))
            .andExpect(jsonPath("$.[*].airportId").value(hasItem(DEFAULT_AIRPORT_ID.intValue())))
            .andExpect(jsonPath("$.[*].airportCode").value(hasItem(DEFAULT_AIRPORT_CODE)))
            .andExpect(jsonPath("$.[*].cityCode").value(hasItem(DEFAULT_CITY_CODE)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].isDomestic").value(hasItem(DEFAULT_IS_DOMESTIC.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Airport.class);
        Airport airport1 = new Airport();
        airport1.setId(1L);
        Airport airport2 = new Airport();
        airport2.setId(airport1.getId());
        assertThat(airport1).isEqualTo(airport2);
        airport2.setId(2L);
        assertThat(airport1).isNotEqualTo(airport2);
        airport1.setId(null);
        assertThat(airport1).isNotEqualTo(airport2);
    }
}
