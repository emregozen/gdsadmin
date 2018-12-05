package com.gds.admin.web.rest;

import com.gds.admin.GdsadminApp;

import com.gds.admin.domain.StateName;
import com.gds.admin.repository.StateNameRepository;
import com.gds.admin.repository.search.StateNameSearchRepository;
import com.gds.admin.service.StateNameService;
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
 * Test class for the StateNameResource REST controller.
 *
 * @see StateNameResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GdsadminApp.class)
public class StateNameResourceIntTest {

    private static final Long DEFAULT_STATE_NAME_ID = 1L;
    private static final Long UPDATED_STATE_NAME_ID = 2L;

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STATE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    @Autowired
    private StateNameRepository stateNameRepository;

    @Autowired
    private StateNameService stateNameService;

    /**
     * This repository is mocked in the com.gds.admin.repository.search test package.
     *
     * @see com.gds.admin.repository.search.StateNameSearchRepositoryMockConfiguration
     */
    @Autowired
    private StateNameSearchRepository mockStateNameSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStateNameMockMvc;

    private StateName stateName;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StateNameResource stateNameResource = new StateNameResource(stateNameService);
        this.restStateNameMockMvc = MockMvcBuilders.standaloneSetup(stateNameResource)
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
    public static StateName createEntity(EntityManager em) {
        StateName stateName = new StateName()
            .stateNameId(DEFAULT_STATE_NAME_ID)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .stateCode(DEFAULT_STATE_CODE)
            .stateName(DEFAULT_STATE_NAME)
            .language(DEFAULT_LANGUAGE);
        return stateName;
    }

    @Before
    public void initTest() {
        stateName = createEntity(em);
    }

    @Test
    @Transactional
    public void createStateName() throws Exception {
        int databaseSizeBeforeCreate = stateNameRepository.findAll().size();

        // Create the StateName
        restStateNameMockMvc.perform(post("/api/state-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateName)))
            .andExpect(status().isCreated());

        // Validate the StateName in the database
        List<StateName> stateNameList = stateNameRepository.findAll();
        assertThat(stateNameList).hasSize(databaseSizeBeforeCreate + 1);
        StateName testStateName = stateNameList.get(stateNameList.size() - 1);
        assertThat(testStateName.getStateNameId()).isEqualTo(DEFAULT_STATE_NAME_ID);
        assertThat(testStateName.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testStateName.getStateCode()).isEqualTo(DEFAULT_STATE_CODE);
        assertThat(testStateName.getStateName()).isEqualTo(DEFAULT_STATE_NAME);
        assertThat(testStateName.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);

        // Validate the StateName in Elasticsearch
        verify(mockStateNameSearchRepository, times(1)).save(testStateName);
    }

    @Test
    @Transactional
    public void createStateNameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stateNameRepository.findAll().size();

        // Create the StateName with an existing ID
        stateName.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStateNameMockMvc.perform(post("/api/state-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateName)))
            .andExpect(status().isBadRequest());

        // Validate the StateName in the database
        List<StateName> stateNameList = stateNameRepository.findAll();
        assertThat(stateNameList).hasSize(databaseSizeBeforeCreate);

        // Validate the StateName in Elasticsearch
        verify(mockStateNameSearchRepository, times(0)).save(stateName);
    }

    @Test
    @Transactional
    public void getAllStateNames() throws Exception {
        // Initialize the database
        stateNameRepository.saveAndFlush(stateName);

        // Get all the stateNameList
        restStateNameMockMvc.perform(get("/api/state-names?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stateName.getId().intValue())))
            .andExpect(jsonPath("$.[*].stateNameId").value(hasItem(DEFAULT_STATE_NAME_ID.intValue())))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE.toString())))
            .andExpect(jsonPath("$.[*].stateCode").value(hasItem(DEFAULT_STATE_CODE.toString())))
            .andExpect(jsonPath("$.[*].stateName").value(hasItem(DEFAULT_STATE_NAME.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }
    
    @Test
    @Transactional
    public void getStateName() throws Exception {
        // Initialize the database
        stateNameRepository.saveAndFlush(stateName);

        // Get the stateName
        restStateNameMockMvc.perform(get("/api/state-names/{id}", stateName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stateName.getId().intValue()))
            .andExpect(jsonPath("$.stateNameId").value(DEFAULT_STATE_NAME_ID.intValue()))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE.toString()))
            .andExpect(jsonPath("$.stateCode").value(DEFAULT_STATE_CODE.toString()))
            .andExpect(jsonPath("$.stateName").value(DEFAULT_STATE_NAME.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStateName() throws Exception {
        // Get the stateName
        restStateNameMockMvc.perform(get("/api/state-names/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStateName() throws Exception {
        // Initialize the database
        stateNameService.save(stateName);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockStateNameSearchRepository);

        int databaseSizeBeforeUpdate = stateNameRepository.findAll().size();

        // Update the stateName
        StateName updatedStateName = stateNameRepository.findById(stateName.getId()).get();
        // Disconnect from session so that the updates on updatedStateName are not directly saved in db
        em.detach(updatedStateName);
        updatedStateName
            .stateNameId(UPDATED_STATE_NAME_ID)
            .countryCode(UPDATED_COUNTRY_CODE)
            .stateCode(UPDATED_STATE_CODE)
            .stateName(UPDATED_STATE_NAME)
            .language(UPDATED_LANGUAGE);

        restStateNameMockMvc.perform(put("/api/state-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStateName)))
            .andExpect(status().isOk());

        // Validate the StateName in the database
        List<StateName> stateNameList = stateNameRepository.findAll();
        assertThat(stateNameList).hasSize(databaseSizeBeforeUpdate);
        StateName testStateName = stateNameList.get(stateNameList.size() - 1);
        assertThat(testStateName.getStateNameId()).isEqualTo(UPDATED_STATE_NAME_ID);
        assertThat(testStateName.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testStateName.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testStateName.getStateName()).isEqualTo(UPDATED_STATE_NAME);
        assertThat(testStateName.getLanguage()).isEqualTo(UPDATED_LANGUAGE);

        // Validate the StateName in Elasticsearch
        verify(mockStateNameSearchRepository, times(1)).save(testStateName);
    }

    @Test
    @Transactional
    public void updateNonExistingStateName() throws Exception {
        int databaseSizeBeforeUpdate = stateNameRepository.findAll().size();

        // Create the StateName

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStateNameMockMvc.perform(put("/api/state-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateName)))
            .andExpect(status().isBadRequest());

        // Validate the StateName in the database
        List<StateName> stateNameList = stateNameRepository.findAll();
        assertThat(stateNameList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StateName in Elasticsearch
        verify(mockStateNameSearchRepository, times(0)).save(stateName);
    }

    @Test
    @Transactional
    public void deleteStateName() throws Exception {
        // Initialize the database
        stateNameService.save(stateName);

        int databaseSizeBeforeDelete = stateNameRepository.findAll().size();

        // Get the stateName
        restStateNameMockMvc.perform(delete("/api/state-names/{id}", stateName.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StateName> stateNameList = stateNameRepository.findAll();
        assertThat(stateNameList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StateName in Elasticsearch
        verify(mockStateNameSearchRepository, times(1)).deleteById(stateName.getId());
    }

    @Test
    @Transactional
    public void searchStateName() throws Exception {
        // Initialize the database
        stateNameService.save(stateName);
        when(mockStateNameSearchRepository.search(queryStringQuery("id:" + stateName.getId())))
            .thenReturn(Collections.singletonList(stateName));
        // Search the stateName
        restStateNameMockMvc.perform(get("/api/_search/state-names?query=id:" + stateName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stateName.getId().intValue())))
            .andExpect(jsonPath("$.[*].stateNameId").value(hasItem(DEFAULT_STATE_NAME_ID.intValue())))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].stateCode").value(hasItem(DEFAULT_STATE_CODE)))
            .andExpect(jsonPath("$.[*].stateName").value(hasItem(DEFAULT_STATE_NAME)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StateName.class);
        StateName stateName1 = new StateName();
        stateName1.setId(1L);
        StateName stateName2 = new StateName();
        stateName2.setId(stateName1.getId());
        assertThat(stateName1).isEqualTo(stateName2);
        stateName2.setId(2L);
        assertThat(stateName1).isNotEqualTo(stateName2);
        stateName1.setId(null);
        assertThat(stateName1).isNotEqualTo(stateName2);
    }
}
