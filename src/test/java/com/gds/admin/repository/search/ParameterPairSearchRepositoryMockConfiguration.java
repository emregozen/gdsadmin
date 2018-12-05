package com.gds.admin.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ParameterPairSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ParameterPairSearchRepositoryMockConfiguration {

    @MockBean
    private ParameterPairSearchRepository mockParameterPairSearchRepository;

}
