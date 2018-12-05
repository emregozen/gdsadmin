package com.gds.admin.repository;

import com.gds.admin.domain.AirportName;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AirportName entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AirportNameRepository extends JpaRepository<AirportName, Long> {

}
