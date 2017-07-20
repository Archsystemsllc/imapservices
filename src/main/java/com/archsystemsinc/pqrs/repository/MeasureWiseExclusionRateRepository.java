package com.archsystemsinc.pqrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.archsystemsinc.pqrs.model.MeasureWiseExclusionRate;

/**
 * @author Grmahun Redda
 *
 */
public interface MeasureWiseExclusionRateRepository extends JpaRepository<MeasureWiseExclusionRate, Long>{
	MeasureWiseExclusionRate findById(final int id);
}
