package com.archsystemsinc.pqrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.archsystemsinc.pqrs.model.MeasureLookup;

/**
 * @author Grmahun Redda
 *
 */
public interface MeasureLookupRepository extends JpaRepository<MeasureLookup,Long>{
	MeasureLookup findById(final int id);
}
