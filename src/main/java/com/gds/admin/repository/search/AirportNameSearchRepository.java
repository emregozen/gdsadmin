package com.gds.admin.repository.search;

import com.gds.admin.domain.AirportName;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AirportName entity.
 */
public interface AirportNameSearchRepository extends ElasticsearchRepository<AirportName, Long> {
}
