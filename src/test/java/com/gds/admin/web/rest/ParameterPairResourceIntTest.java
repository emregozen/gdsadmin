package com.gds.admin.web.rest;

import com.gds.admin.GdsadminApp;

import com.gds.admin.domain.ParameterPair;
import com.gds.admin.repository.ParameterPairRepository;
import com.gds.admin.repository.search.ParameterPairSearchRepository;
import com.gds.admin.service.ParameterPairService;
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
 * Test class for the ParameterPairResource REST controller.
 *
 * @see ParameterPairResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GdsadminApp.class)
public class ParameterPairResourceIntTest {

    private static final Long DEFAULT_PARAMETER_PAIR_ID = 1L;
    private static final Long UPDATED_PARAMETER_PAIR_ID = 2L;

    private static final String DEFAULT_PARAMETER_KEY = "AAAAAAAAAA";
    private static final String UPDATED_PARAMETER_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_PARAMETER_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PARAMETER_VALUE = "BBBBBBBBBB";

    @Autowired
    private ParameterPairRepository parameterPairRepository;

    @Autowired
    private ParameterPairService parameterPairService;

    /**
     * This repository is mocked in the com.gds.admin.repository.search test package.
     *
     * @see com.gds.admin.repository.search.ParameterPairSearchRepositoryMockConfiguration
     */
    @Autowired
    private ParameterPairSearchRepository mockParameterPairSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restParameterPairMockMvc;

    private ParameterPair parameterPair;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParameterPairResource parameterPairResource = new ParameterPairResource(parameterPairService);
        this.restParameterPairMockMvc = MockMvcBuilders.standaloneSetup(parameterPairResource)
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
    public static ParameterPair createEntity(EntityManager em) {
        ParameterPair parameterPair = new ParameterPair()
            .parameterPairId(DEFAULT_PARAMETER_PAIR_ID)
            .parameterKey(DEFAULT_PARAMETER_KEY)
            .parameterValue(DEFAULT_PARAMETER_VALUE);
        return parameterPair;
    }

    @Before
    public void initTest() {
        parameterPair = createEntity(em);
    }

    @Test
    @Transactional
    public void createParameterPair() throws Exception {
        int databaseSizeBeforeCreate = parameterPairRepository.findAll().size();

        // Create the ParameterPair
        restParameterPairMockMvc.perform(post("/api/parameter-pairs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parameterPair)))
            .andExpect(status().isCreated());

        // Validate the ParameterPair in the database
        List<ParameterPair> parameterPairList = parameterPairRepository.findAll();
        assertThat(parameterPairList).hasSize(databaseSizeBeforeCreate + 1);
        ParameterPair testParameterPair = parameterPairList.get(parameterPairList.size() - 1);
        assertThat(testParameterPair.getParameterPairId()).isEqualTo(DEFAULT_PARAMETER_PAIR_ID);
        assertThat(testParameterPair.getParameterKey()).isEqualTo(DEFAULT_PARAMETER_KEY);
        assertThat(testParameterPair.getParameterValue()).isEqualTo(DEFAULT_PARAMETER_VALUE);

        // Validate the ParameterPair in Elasticsearch
        verify(mockParameterPairSearchRepository, times(1)).save(testParameterPair);
    }

    @Test
    @Transactional
    public void createParameterPairWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parameterPairRepository.findAll().size();

        // Create the ParameterPair with an existing ID
        parameterPair.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParameterPairMockMvc.perform(post("/api/parameter-pairs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parameterPair)))
            .andExpect(status().isBadRequest());

        // Validate the ParameterPair in the database
        List<ParameterPair> parameterPairList = parameterPairRepository.findAll();
        assertThat(parameterPairList).hasSize(databaseSizeBeforeCreate);

        // Validate the ParameterPair in Elasticsearch
        verify(mockParameterPairSearchRepository, times(0)).save(parameterPair);
    }

    @Test
    @Transactional
    public void getAllParameterPairs() throws Exception {
        // Initialize the database
        parameterPairRepository.saveAndFlush(parameterPair);

        // Get all the parameterPairList
        restParameterPairMockMvc.perform(get("/api/parameter-pairs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parameterPair.getId().intValue())))
            .andExpect(jsonPath("$.[*].parameterPairId").value(hasItem(DEFAULT_PARAMETER_PAIR_ID.intValue())))
            .andExpect(jsonPath("$.[*].parameterKey").value(hasItem(DEFAULT_PARAMETER_KEY.toString())))
            .andExpect(jsonPath("$.[*].parameterValue").value(hasItem(DEFAULT_PARAMETER_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getParameterPair() throws Exception {
        // Initialize the database
        parameterPairRepository.saveAndFlush(parameterPair);

        // Get the parameterPair
        restParameterPairMockMvc.perform(get("/api/parameter-pairs/{id}", parameterPair.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parameterPair.getId().intValue()))
            .andExpect(jsonPath("$.parameterPairId").value(DEFAULT_PARAMETER_PAIR_ID.intValue()))
            .andExpect(jsonPath("$.parameterKey").value(DEFAULT_PARAMETER_KEY.toString()))
            .andExpect(jsonPath("$.parameterValue").value(DEFAULT_PARAMETER_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParameterPair() throws Exception {
        // Get the parameterPair
        restParameterPairMockMvc.perform(get("/api/parameter-pairs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParameterPair() throws Exception {
        // Initialize the database
        parameterPairService.save(parameterPair);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockParameterPairSearchRepository);

        int databaseSizeBeforeUpdate = parameterPairRepository.findAll().size();

        // Update the parameterPair
        ParameterPair updatedParameterPair = parameterPairRepository.findById(parameterPair.getId()).get();
        // Disconnect from session so that the updates on updatedParameterPair are not directly saved in db
        em.detach(updatedParameterPair);
        updatedParameterPair
            .parameterPairId(UPDATED_PARAMETER_PAIR_ID)
            .parameterKey(UPDATED_PARAMETER_KEY)
            .parameterValue(UPDATED_PARAMETER_VALUE);

        restParameterPairMockMvc.perform(put("/api/parameter-pairs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParameterPair)))
            .andExpect(status().isOk());

        // Validate the ParameterPair in the database
        List<ParameterPair> parameterPairList = parameterPairRepository.findAll();
        assertThat(parameterPairList).hasSize(databaseSizeBeforeUpdate);
        ParameterPair testParameterPair = parameterPairList.get(parameterPairList.size() - 1);
        assertThat(testParameterPair.getParameterPairId()).isEqualTo(UPDATED_PARAMETER_PAIR_ID);
        assertThat(testParameterPair.getParameterKey()).isEqualTo(UPDATED_PARAMETER_KEY);
        assertThat(testParameterPair.getParameterValue()).isEqualTo(UPDATED_PARAMETER_VALUE);

        // Validate the ParameterPair in Elasticsearch
        verify(mockParameterPairSearchRepository, times(1)).save(testParameterPair);
    }

    @Test
    @Transactional
    public void updateNonExistingParameterPair() throws Exception {
        int databaseSizeBeforeUpdate = parameterPairRepository.findAll().size();

        // Create the ParameterPair

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParameterPairMockMvc.perform(put("/api/parameter-pairs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parameterPair)))
            .andExpect(status().isBadRequest());

        // Validate the ParameterPair in the database
        List<ParameterPair> parameterPairList = parameterPairRepository.findAll();
        assertThat(parameterPairList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ParameterPair in Elasticsearch
        verify(mockParameterPairSearchRepository, times(0)).save(parameterPair);
    }

    @Test
    @Transactional
    public void deleteParameterPair() throws Exception {
        // Initialize the database
        parameterPairService.save(parameterPair);

        int databaseSizeBeforeDelete = parameterPairRepository.findAll().size();

        // Get the parameterPair
        restParameterPairMockMvc.perform(delete("/api/parameter-pairs/{id}", parameterPair.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParameterPair> parameterPairList = parameterPairRepository.findAll();
        assertThat(parameterPairList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ParameterPair in Elasticsearch
        verify(mockParameterPairSearchRepository, times(1)).deleteById(parameterPair.getId());
    }

    @Test
    @Transactional
    public void searchParameterPair() throws Exception {
        // Initialize the database
        parameterPairService.save(parameterPair);
        when(mockParameterPairSearchRepository.search(queryStringQuery("id:" + parameterPair.getId())))
            .thenReturn(Collections.singletonList(parameterPair));
        // Search the parameterPair
        restParameterPairMockMvc.perform(get("/api/_search/parameter-pairs?query=id:" + parameterPair.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parameterPair.getId().intValue())))
            .andExpect(jsonPath("$.[*].parameterPairId").value(hasItem(DEFAULT_PARAMETER_PAIR_ID.intValue())))
            .andExpect(jsonPath("$.[*].parameterKey").value(hasItem(DEFAULT_PARAMETER_KEY)))
            .andExpect(jsonPath("$.[*].parameterValue").value(hasItem(DEFAULT_PARAMETER_VALUE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParameterPair.class);
        ParameterPair parameterPair1 = new ParameterPair();
        parameterPair1.setId(1L);
        ParameterPair parameterPair2 = new ParameterPair();
        parameterPair2.setId(parameterPair1.getId());
        assertThat(parameterPair1).isEqualTo(parameterPair2);
        parameterPair2.setId(2L);
        assertThat(parameterPair1).isNotEqualTo(parameterPair2);
        parameterPair1.setId(null);
        assertThat(parameterPair1).isNotEqualTo(parameterPair2);
    }
}
