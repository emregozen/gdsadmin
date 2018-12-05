package com.gds.admin.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of CityNameSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CityNameSearchRepositoryMockConfiguration {

    @MockBean
    private CityNameSearchRepository mockCityNameSearchRepository;

}