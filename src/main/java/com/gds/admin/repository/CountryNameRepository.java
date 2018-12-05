package com.gds.admin.repository;

import com.gds.admin.domain.CountryName;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CountryName entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountryNameRepository extends JpaRepository<CountryName, Long>, JpaSpecificationExecutor<CountryName> {

}
