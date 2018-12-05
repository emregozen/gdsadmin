package com.gds.admin.repository;

import com.gds.admin.domain.ParameterPair;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ParameterPair entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParameterPairRepository extends JpaRepository<ParameterPair, Long>, JpaSpecificationExecutor<ParameterPair> {

}
