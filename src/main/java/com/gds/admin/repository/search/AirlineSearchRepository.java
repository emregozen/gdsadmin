package com.gds.admin.repository.search;

import com.gds.admin.domain.Airline;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Airline entity.
 */
public interface AirlineSearchRepository extends ElasticsearchRepository<Airline, Long> {
}
