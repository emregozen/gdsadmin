package com.gds.admin.web.rest;

import com.gds.admin.GdsadminApp;

import com.gds.admin.domain.CityName;
import com.gds.admin.repository.CityNameRepository;
import com.gds.admin.repository.search.CityNameSearchRepository;
import com.gds.admin.service.CityNameService;
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
 * Test class for the CityNameResource REST controller.
 *
 * @see CityNameResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GdsadminApp.class)
public class CityNameResourceIntTest {

    private static final Long DEFAULT_CITY_NAME_ID = 1L;
    private static final Long UPDATED_CITY_NAME_ID = 2L;

    private static final String DEFAULT_CITY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CITY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    @Autowired
    private CityNameRepository cityNameRepository;

    @Autowired
    private CityNameService cityNameService;

    /**
     * This repository is mocked in the com.gds.admin.repository.search test package.
     *
     * @see com.gds.admin.repository.search.CityNameSearchRepositoryMockConfiguration
     */
    @Autowired
    private CityNameSearchRepository mockCityNameSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCityNameMockMvc;

    private CityName cityName;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CityNameResource cityNameResource = new CityNameResource(cityNameService);
        this.restCityNameMockMvc = MockMvcBuilders.standaloneSetup(cityNameResource)
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
    public static CityName createEntity(EntityManager em) {
        CityName cityName = new CityName()
            .cityNameId(DEFAULT_CITY_NAME_ID)
            .cityCode(DEFAULT_CITY_CODE)
            .cityName(DEFAULT_CITY_NAME)
            .language(DEFAULT_LANGUAGE);
        return cityName;
    }

    @Before
    public void initTest() {
        cityName = createEntity(em);
    }

    @Test
    @Transactional
    public void createCityName() throws Exception {
        int databaseSizeBeforeCreate = cityNameRepository.findAll().size();

        // Create the CityName
        restCityNameMockMvc.perform(post("/api/city-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cityName)))
            .andExpect(status().isCreated());

        // Validate the CityName in the database
        List<CityName> cityNameList = cityNameRepository.findAll();
        assertThat(cityNameList).hasSize(databaseSizeBeforeCreate + 1);
        CityName testCityName = cityNameList.get(cityNameList.size() - 1);
        assertThat(testCityName.getCityNameId()).isEqualTo(DEFAULT_CITY_NAME_ID);
        assertThat(testCityName.getCityCode()).isEqualTo(DEFAULT_CITY_CODE);
        assertThat(testCityName.getCityName()).isEqualTo(DEFAULT_CITY_NAME);
        assertThat(testCityName.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);

        // Validate the CityName in Elasticsearch
        verify(mockCityNameSearchRepository, times(1)).save(testCityName);
    }

    @Test
    @Transactional
    public void createCityNameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cityNameRepository.findAll().size();

        // Create the CityName with an existing ID
        cityName.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCityNameMockMvc.perform(post("/api/city-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cityName)))
            .andExpect(status().isBadRequest());

        // Validate the CityName in the database
        List<CityName> cityNameList = cityNameRepository.findAll();
        assertThat(cityNameList).hasSize(databaseSizeBeforeCreate);

        // Validate the CityName in Elasticsearch
        verify(mockCityNameSearchRepository, times(0)).save(cityName);
    }

    @Test
    @Transactional
    public void getAllCityNames() throws Exception {
        // Initialize the database
        cityNameRepository.saveAndFlush(cityName);

        // Get all the cityNameList
        restCityNameMockMvc.perform(get("/api/city-names?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cityName.getId().intValue())))
            .andExpect(jsonPath("$.[*].cityNameId").value(hasItem(DEFAULT_CITY_NAME_ID.intValue())))
            .andExpect(jsonPath("$.[*].cityCode").value(hasItem(DEFAULT_CITY_CODE.toString())))
            .andExpect(jsonPath("$.[*].cityName").value(hasItem(DEFAULT_CITY_NAME.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }
    
    @Test
    @Transactional
    public void getCityName() throws Exception {
        // Initialize the database
        cityNameRepository.saveAndFlush(cityName);

        // Get the cityName
        restCityNameMockMvc.perform(get("/api/city-names/{id}", cityName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cityName.getId().intValue()))
            .andExpect(jsonPath("$.cityNameId").value(DEFAULT_CITY_NAME_ID.intValue()))
            .andExpect(jsonPath("$.cityCode").value(DEFAULT_CITY_CODE.toString()))
            .andExpect(jsonPath("$.cityName").value(DEFAULT_CITY_NAME.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCityName() throws Exception {
        // Get the cityName
        restCityNameMockMvc.perform(get("/api/city-names/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCityName() throws Exception {
        // Initialize the database
        cityNameService.save(cityName);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockCityNameSearchRepository);

        int databaseSizeBeforeUpdate = cityNameRepository.findAll().size();

        // Update the cityName
        CityName updatedCityName = cityNameRepository.findById(cityName.getId()).get();
        // Disconnect from session so that the updates on updatedCityName are not directly saved in db
        em.detach(updatedCityName);
        updatedCityName
            .cityNameId(UPDATED_CITY_NAME_ID)
            .cityCode(UPDATED_CITY_CODE)
            .cityName(UPDATED_CITY_NAME)
            .language(UPDATED_LANGUAGE);

        restCityNameMockMvc.perform(put("/api/city-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCityName)))
            .andExpect(status().isOk());

        // Validate the CityName in the database
        List<CityName> cityNameList = cityNameRepository.findAll();
        assertThat(cityNameList).hasSize(databaseSizeBeforeUpdate);
        CityName testCityName = cityNameList.get(cityNameList.size() - 1);
        assertThat(testCityName.getCityNameId()).isEqualTo(UPDATED_CITY_NAME_ID);
        assertThat(testCityName.getCityCode()).isEqualTo(UPDATED_CITY_CODE);
        assertThat(testCityName.getCityName()).isEqualTo(UPDATED_CITY_NAME);
        assertThat(testCityName.getLanguage()).isEqualTo(UPDATED_LANGUAGE);

        // Validate the CityName in Elasticsearch
        verify(mockCityNameSearchRepository, times(1)).save(testCityName);
    }

    @Test
    @Transactional
    public void updateNonExistingCityName() throws Exception {
        int databaseSizeBeforeUpdate = cityNameRepository.findAll().size();

        // Create the CityName

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCityNameMockMvc.perform(put("/api/city-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cityName)))
            .andExpect(status().isBadRequest());

        // Validate the CityName in the database
        List<CityName> cityNameList = cityNameRepository.findAll();
        assertThat(cityNameList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CityName in Elasticsearch
        verify(mockCityNameSearchRepository, times(0)).save(cityName);
    }

    @Test
    @Transactional
    public void deleteCityName() throws Exception {
        // Initialize the database
        cityNameService.save(cityName);

        int databaseSizeBeforeDelete = cityNameRepository.findAll().size();

        // Get the cityName
        restCityNameMockMvc.perform(delete("/api/city-names/{id}", cityName.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CityName> cityNameList = cityNameRepository.findAll();
        assertThat(cityNameList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CityName in Elasticsearch
        verify(mockCityNameSearchRepository, times(1)).deleteById(cityName.getId());
    }

    @Test
    @Transactional
    public void searchCityName() throws Exception {
        // Initialize the database
        cityNameService.save(cityName);
        when(mockCityNameSearchRepository.search(queryStringQuery("id:" + cityName.getId())))
            .thenReturn(Collections.singletonList(cityName));
        // Search the cityName
        restCityNameMockMvc.perform(get("/api/_search/city-names?query=id:" + cityName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cityName.getId().intValue())))
            .andExpect(jsonPath("$.[*].cityNameId").value(hasItem(DEFAULT_CITY_NAME_ID.intValue())))
            .andExpect(jsonPath("$.[*].cityCode").value(hasItem(DEFAULT_CITY_CODE)))
            .andExpect(jsonPath("$.[*].cityName").value(hasItem(DEFAULT_CITY_NAME)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CityName.class);
        CityName cityName1 = new CityName();
        cityName1.setId(1L);
        CityName cityName2 = new CityName();
        cityName2.setId(cityName1.getId());
        assertThat(cityName1).isEqualTo(cityName2);
        cityName2.setId(2L);
        assertThat(cityName1).isNotEqualTo(cityName2);
        cityName1.setId(null);
        assertThat(cityName1).isNotEqualTo(cityName2);
    }
}
