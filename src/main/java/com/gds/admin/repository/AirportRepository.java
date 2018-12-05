package com.gds.admin.repository;

import com.gds.admin.domain.Airport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Airport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

}
