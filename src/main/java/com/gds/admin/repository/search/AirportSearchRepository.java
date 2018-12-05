package com.gds.admin.repository.search;

import com.gds.admin.domain.Airport;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Airport entity.
 */
public interface AirportSearchRepository extends ElasticsearchRepository<Airport, Long> {
}
