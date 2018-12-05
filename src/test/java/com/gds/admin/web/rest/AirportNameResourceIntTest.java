package com.gds.admin.web.rest;

import com.gds.admin.GdsadminApp;

import com.gds.admin.domain.AirportName;
import com.gds.admin.repository.AirportNameRepository;
import com.gds.admin.repository.search.AirportNameSearchRepository;
import com.gds.admin.service.AirportNameService;
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
 * Test class for the AirportNameResource REST controller.
 *
 * @see AirportNameResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GdsadminApp.class)
public class AirportNameResourceIntTest {

    private static final Long DEFAULT_AIRPORT_NAME_ID = 1L;
    private static final Long UPDATED_AIRPORT_NAME_ID = 2L;

    private static final String DEFAULT_AIRPORT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_AIRPORT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    @Autowired
    private AirportNameRepository airportNameRepository;

    @Autowired
    private AirportNameService airportNameService;

    /**
     * This repository is mocked in the com.gds.admin.repository.search test package.
     *
     * @see com.gds.admin.repository.search.AirportNameSearchRepositoryMockConfiguration
     */
    @Autowired
    private AirportNameSearchRepository mockAirportNameSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAirportNameMockMvc;

    private AirportName airportName;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AirportNameResource airportNameResource = new AirportNameResource(airportNameService);
        this.restAirportNameMockMvc = MockMvcBuilders.standaloneSetup(airportNameResource)
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
    public static AirportName createEntity(EntityManager em) {
        AirportName airportName = new AirportName()
            .airportNameId(DEFAULT_AIRPORT_NAME_ID)
            .airportCode(DEFAULT_AIRPORT_CODE)
            .language(DEFAULT_LANGUAGE);
        return airportName;
    }

    @Before
    public void initTest() {
        airportName = createEntity(em);
    }

    @Test
    @Transactional
    public void createAirportName() throws Exception {
        int databaseSizeBeforeCreate = airportNameRepository.findAll().size();

        // Create the AirportName
        restAirportNameMockMvc.perform(post("/api/airport-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airportName)))
            .andExpect(status().isCreated());

        // Validate the AirportName in the database
        List<AirportName> airportNameList = airportNameRepository.findAll();
        assertThat(airportNameList).hasSize(databaseSizeBeforeCreate + 1);
        AirportName testAirportName = airportNameList.get(airportNameList.size() - 1);
        assertThat(testAirportName.getAirportNameId()).isEqualTo(DEFAULT_AIRPORT_NAME_ID);
        assertThat(testAirportName.getAirportCode()).isEqualTo(DEFAULT_AIRPORT_CODE);
        assertThat(testAirportName.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);

        // Validate the AirportName in Elasticsearch
        verify(mockAirportNameSearchRepository, times(1)).save(testAirportName);
    }

    @Test
    @Transactional
    public void createAirportNameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = airportNameRepository.findAll().size();

        // Create the AirportName with an existing ID
        airportName.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAirportNameMockMvc.perform(post("/api/airport-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airportName)))
            .andExpect(status().isBadRequest());

        // Validate the AirportName in the database
        List<AirportName> airportNameList = airportNameRepository.findAll();
        assertThat(airportNameList).hasSize(databaseSizeBeforeCreate);

        // Validate the AirportName in Elasticsearch
        verify(mockAirportNameSearchRepository, times(0)).save(airportName);
    }

    @Test
    @Transactional
    public void getAllAirportNames() throws Exception {
        // Initialize the database
        airportNameRepository.saveAndFlush(airportName);

        // Get all the airportNameList
        restAirportNameMockMvc.perform(get("/api/airport-names?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(airportName.getId().intValue())))
            .andExpect(jsonPath("$.[*].airportNameId").value(hasItem(DEFAULT_AIRPORT_NAME_ID.intValue())))
            .andExpect(jsonPath("$.[*].airportCode").value(hasItem(DEFAULT_AIRPORT_CODE.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }
    
    @Test
    @Transactional
    public void getAirportName() throws Exception {
        // Initialize the database
        airportNameRepository.saveAndFlush(airportName);

        // Get the airportName
        restAirportNameMockMvc.perform(get("/api/airport-names/{id}", airportName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(airportName.getId().intValue()))
            .andExpect(jsonPath("$.airportNameId").value(DEFAULT_AIRPORT_NAME_ID.intValue()))
            .andExpect(jsonPath("$.airportCode").value(DEFAULT_AIRPORT_CODE.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAirportName() throws Exception {
        // Get the airportName
        restAirportNameMockMvc.perform(get("/api/airport-names/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAirportName() throws Exception {
        // Initialize the database
        airportNameService.save(airportName);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockAirportNameSearchRepository);

        int databaseSizeBeforeUpdate = airportNameRepository.findAll().size();

        // Update the airportName
        AirportName updatedAirportName = airportNameRepository.findById(airportName.getId()).get();
        // Disconnect from session so that the updates on updatedAirportName are not directly saved in db
        em.detach(updatedAirportName);
        updatedAirportName
            .airportNameId(UPDATED_AIRPORT_NAME_ID)
            .airportCode(UPDATED_AIRPORT_CODE)
            .language(UPDATED_LANGUAGE);

        restAirportNameMockMvc.perform(put("/api/airport-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAirportName)))
            .andExpect(status().isOk());

        // Validate the AirportName in the database
        List<AirportName> airportNameList = airportNameRepository.findAll();
        assertThat(airportNameList).hasSize(databaseSizeBeforeUpdate);
        AirportName testAirportName = airportNameList.get(airportNameList.size() - 1);
        assertThat(testAirportName.getAirportNameId()).isEqualTo(UPDATED_AIRPORT_NAME_ID);
        assertThat(testAirportName.getAirportCode()).isEqualTo(UPDATED_AIRPORT_CODE);
        assertThat(testAirportName.getLanguage()).isEqualTo(UPDATED_LANGUAGE);

        // Validate the AirportName in Elasticsearch
        verify(mockAirportNameSearchRepository, times(1)).save(testAirportName);
    }

    @Test
    @Transactional
    public void updateNonExistingAirportName() throws Exception {
        int databaseSizeBeforeUpdate = airportNameRepository.findAll().size();

        // Create the AirportName

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAirportNameMockMvc.perform(put("/api/airport-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airportName)))
            .andExpect(status().isBadRequest());

        // Validate the AirportName in the database
        List<AirportName> airportNameList = airportNameRepository.findAll();
        assertThat(airportNameList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AirportName in Elasticsearch
        verify(mockAirportNameSearchRepository, times(0)).save(airportName);
    }

    @Test
    @Transactional
    public void deleteAirportName() throws Exception {
        // Initialize the database
        airportNameService.save(airportName);

        int databaseSizeBeforeDelete = airportNameRepository.findAll().size();

        // Get the airportName
        restAirportNameMockMvc.perform(delete("/api/airport-names/{id}", airportName.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AirportName> airportNameList = airportNameRepository.findAll();
        assertThat(airportNameList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AirportName in Elasticsearch
        verify(mockAirportNameSearchRepository, times(1)).deleteById(airportName.getId());
    }

    @Test
    @Transactional
    public void searchAirportName() throws Exception {
        // Initialize the database
        airportNameService.save(airportName);
        when(mockAirportNameSearchRepository.search(queryStringQuery("id:" + airportName.getId())))
            .thenReturn(Collections.singletonList(airportName));
        // Search the airportName
        restAirportNameMockMvc.perform(get("/api/_search/airport-names?query=id:" + airportName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(airportName.getId().intValue())))
            .andExpect(jsonPath("$.[*].airportNameId").value(hasItem(DEFAULT_AIRPORT_NAME_ID.intValue())))
            .andExpect(jsonPath("$.[*].airportCode").value(hasItem(DEFAULT_AIRPORT_CODE)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AirportName.class);
        AirportName airportName1 = new AirportName();
        airportName1.setId(1L);
        AirportName airportName2 = new AirportName();
        airportName2.setId(airportName1.getId());
        assertThat(airportName1).isEqualTo(airportName2);
        airportName2.setId(2L);
        assertThat(airportName1).isNotEqualTo(airportName2);
        airportName1.setId(null);
        assertThat(airportName1).isNotEqualTo(airportName2);
    }
}
