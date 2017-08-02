package com.archsystemsinc.pqrs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.archsystemsinc.pqrs.model.MeasureLookup;

/**
 * @author Grmahun Redda
 *
 */
public interface MeasureLookupRepository extends JpaRepository<MeasureLookup,Long>{
	MeasureLookup findById(final int id);
	
	@Query("select m from MeasureLookup m where m.measureId like %:idOrName% or m.measureName like %:idOrName% ")
	public List<MeasureLookup> findByIdOrName(@Param("idOrName") String idOrName);
}
