package com.gds.admin.repository.search;

import com.gds.admin.domain.CountryName;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CountryName entity.
 */
public interface CountryNameSearchRepository extends ElasticsearchRepository<CountryName, Long> {
}
