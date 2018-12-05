package com.gds.admin.web.rest;

import com.gds.admin.GdsadminApp;

import com.gds.admin.domain.CountryName;
import com.gds.admin.repository.CountryNameRepository;
import com.gds.admin.repository.search.CountryNameSearchRepository;
import com.gds.admin.service.CountryNameService;
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
 * Test class for the CountryNameResource REST controller.
 *
 * @see CountryNameResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GdsadminApp.class)
public class CountryNameResourceIntTest {

    private static final Long DEFAULT_COUNTRY_NAME_ID = 1L;
    private static final Long UPDATED_COUNTRY_NAME_ID = 2L;

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    @Autowired
    private CountryNameRepository countryNameRepository;

    @Autowired
    private CountryNameService countryNameService;

    /**
     * This repository is mocked in the com.gds.admin.repository.search test package.
     *
     * @see com.gds.admin.repository.search.CountryNameSearchRepositoryMockConfiguration
     */
    @Autowired
    private CountryNameSearchRepository mockCountryNameSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCountryNameMockMvc;

    private CountryName countryName;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CountryNameResource countryNameResource = new CountryNameResource(countryNameService);
        this.restCountryNameMockMvc = MockMvcBuilders.standaloneSetup(countryNameResource)
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
    public static CountryName createEntity(EntityManager em) {
        CountryName countryName = new CountryName()
            .countryNameId(DEFAULT_COUNTRY_NAME_ID)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .countryName(DEFAULT_COUNTRY_NAME)
            .language(DEFAULT_LANGUAGE);
        return countryName;
    }

    @Before
    public void initTest() {
        countryName = createEntity(em);
    }

    @Test
    @Transactional
    public void createCountryName() throws Exception {
        int databaseSizeBeforeCreate = countryNameRepository.findAll().size();

        // Create the CountryName
        restCountryNameMockMvc.perform(post("/api/country-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryName)))
            .andExpect(status().isCreated());

        // Validate the CountryName in the database
        List<CountryName> countryNameList = countryNameRepository.findAll();
        assertThat(countryNameList).hasSize(databaseSizeBeforeCreate + 1);
        CountryName testCountryName = countryNameList.get(countryNameList.size() - 1);
        assertThat(testCountryName.getCountryNameId()).isEqualTo(DEFAULT_COUNTRY_NAME_ID);
        assertThat(testCountryName.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testCountryName.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testCountryName.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);

        // Validate the CountryName in Elasticsearch
        verify(mockCountryNameSearchRepository, times(1)).save(testCountryName);
    }

    @Test
    @Transactional
    public void createCountryNameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = countryNameRepository.findAll().size();

        // Create the CountryName with an existing ID
        countryName.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountryNameMockMvc.perform(post("/api/country-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryName)))
            .andExpect(status().isBadRequest());

        // Validate the CountryName in the database
        List<CountryName> countryNameList = countryNameRepository.findAll();
        assertThat(countryNameList).hasSize(databaseSizeBeforeCreate);

        // Validate the CountryName in Elasticsearch
        verify(mockCountryNameSearchRepository, times(0)).save(countryName);
    }

    @Test
    @Transactional
    public void getAllCountryNames() throws Exception {
        // Initialize the database
        countryNameRepository.saveAndFlush(countryName);

        // Get all the countryNameList
        restCountryNameMockMvc.perform(get("/api/country-names?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countryName.getId().intValue())))
            .andExpect(jsonPath("$.[*].countryNameId").value(hasItem(DEFAULT_COUNTRY_NAME_ID.intValue())))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE.toString())))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }
    
    @Test
    @Transactional
    public void getCountryName() throws Exception {
        // Initialize the database
        countryNameRepository.saveAndFlush(countryName);

        // Get the countryName
        restCountryNameMockMvc.perform(get("/api/country-names/{id}", countryName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(countryName.getId().intValue()))
            .andExpect(jsonPath("$.countryNameId").value(DEFAULT_COUNTRY_NAME_ID.intValue()))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE.toString()))
            .andExpect(jsonPath("$.countryName").value(DEFAULT_COUNTRY_NAME.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCountryName() throws Exception {
        // Get the countryName
        restCountryNameMockMvc.perform(get("/api/country-names/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCountryName() throws Exception {
        // Initialize the database
        countryNameService.save(countryName);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockCountryNameSearchRepository);

        int databaseSizeBeforeUpdate = countryNameRepository.findAll().size();

        // Update the countryName
        CountryName updatedCountryName = countryNameRepository.findById(countryName.getId()).get();
        // Disconnect from session so that the updates on updatedCountryName are not directly saved in db
        em.detach(updatedCountryName);
        updatedCountryName
            .countryNameId(UPDATED_COUNTRY_NAME_ID)
            .countryCode(UPDATED_COUNTRY_CODE)
            .countryName(UPDATED_COUNTRY_NAME)
            .language(UPDATED_LANGUAGE);

        restCountryNameMockMvc.perform(put("/api/country-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCountryName)))
            .andExpect(status().isOk());

        // Validate the CountryName in the database
        List<CountryName> countryNameList = countryNameRepository.findAll();
        assertThat(countryNameList).hasSize(databaseSizeBeforeUpdate);
        CountryName testCountryName = countryNameList.get(countryNameList.size() - 1);
        assertThat(testCountryName.getCountryNameId()).isEqualTo(UPDATED_COUNTRY_NAME_ID);
        assertThat(testCountryName.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testCountryName.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testCountryName.getLanguage()).isEqualTo(UPDATED_LANGUAGE);

        // Validate the CountryName in Elasticsearch
        verify(mockCountryNameSearchRepository, times(1)).save(testCountryName);
    }

    @Test
    @Transactional
    public void updateNonExistingCountryName() throws Exception {
        int databaseSizeBeforeUpdate = countryNameRepository.findAll().size();

        // Create the CountryName

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryNameMockMvc.perform(put("/api/country-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryName)))
            .andExpect(status().isBadRequest());

        // Validate the CountryName in the database
        List<CountryName> countryNameList = countryNameRepository.findAll();
        assertThat(countryNameList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CountryName in Elasticsearch
        verify(mockCountryNameSearchRepository, times(0)).save(countryName);
    }

    @Test
    @Transactional
    public void deleteCountryName() throws Exception {
        // Initialize the database
        countryNameService.save(countryName);

        int databaseSizeBeforeDelete = countryNameRepository.findAll().size();

        // Get the countryName
        restCountryNameMockMvc.perform(delete("/api/country-names/{id}", countryName.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CountryName> countryNameList = countryNameRepository.findAll();
        assertThat(countryNameList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CountryName in Elasticsearch
        verify(mockCountryNameSearchRepository, times(1)).deleteById(countryName.getId());
    }

    @Test
    @Transactional
    public void searchCountryName() throws Exception {
        // Initialize the database
        countryNameService.save(countryName);
        when(mockCountryNameSearchRepository.search(queryStringQuery("id:" + countryName.getId())))
            .thenReturn(Collections.singletonList(countryName));
        // Search the countryName
        restCountryNameMockMvc.perform(get("/api/_search/country-names?query=id:" + countryName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countryName.getId().intValue())))
            .andExpect(jsonPath("$.[*].countryNameId").value(hasItem(DEFAULT_COUNTRY_NAME_ID.intValue())))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountryName.class);
        CountryName countryName1 = new CountryName();
        countryName1.setId(1L);
        CountryName countryName2 = new CountryName();
        countryName2.setId(countryName1.getId());
        assertThat(countryName1).isEqualTo(countryName2);
        countryName2.setId(2L);
        assertThat(countryName1).isNotEqualTo(countryName2);
        countryName1.setId(null);
        assertThat(countryName1).isNotEqualTo(countryName2);
    }
}
