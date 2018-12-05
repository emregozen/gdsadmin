package com.gds.admin.repository.search;

import com.gds.admin.domain.CityName;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CityName entity.
 */
public interface CityNameSearchRepository extends ElasticsearchRepository<CityName, Long> {
}
