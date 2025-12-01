package com.ttl.core.repository;

import com.tutv.epattern.coreservice.entities.GenerateSerial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GenerateSerialRepository extends JpaRepository<GenerateSerial, String>{

    @Modifying
    @Transactional
	@Query("UPDATE scserial s SET lastserial = ( CASE WHEN endwith IS NOT NULL THEN CASE WHEN lastserial + 1 > endwith THEN COALESCE(startwith, 0) ELSE lastserial + 1 END ELSE COALESCE(startwith, COALESCE(lastserial + 1, 0)) END ) WHERE serialno = :pSerialNo")
	void updateLastSerial(@Param("pSerialNo") String pSerialNo);
	@Query("select lastserial from scserial WHERE serialno = :pSerialNo")
	String getNextSerial(@Param("pSerialNo") String pSerialNo);
}
