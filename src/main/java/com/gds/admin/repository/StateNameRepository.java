package com.gds.admin.repository;

import com.gds.admin.domain.StateName;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StateName entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StateNameRepository extends JpaRepository<StateName, Long> {

}
