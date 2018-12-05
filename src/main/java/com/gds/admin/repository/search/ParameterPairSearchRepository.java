package com.gds.admin.repository.search;

import com.gds.admin.domain.ParameterPair;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ParameterPair entity.
 */
public interface ParameterPairSearchRepository extends ElasticsearchRepository<ParameterPair, Long> {
}
