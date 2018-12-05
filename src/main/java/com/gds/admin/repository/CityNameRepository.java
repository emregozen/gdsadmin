package com.gds.admin.repository;

import com.gds.admin.domain.CityName;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CityName entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CityNameRepository extends JpaRepository<CityName, Long> {

}
