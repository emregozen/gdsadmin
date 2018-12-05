package com.gds.admin.repository.search;

import com.gds.admin.domain.StateName;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the StateName entity.
 */
public interface StateNameSearchRepository extends ElasticsearchRepository<StateName, Long> {
}
