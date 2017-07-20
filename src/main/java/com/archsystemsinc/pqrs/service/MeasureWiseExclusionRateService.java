package com.archsystemsinc.pqrs.service;

import java.util.List;

import com.archsystemsinc.pqrs.model.MeasureWiseExclusionRate;

/**
 * @author Grmahun Redda
 *
 */
public interface MeasureWiseExclusionRateService {
	
	List<MeasureWiseExclusionRate> findAll();
	MeasureWiseExclusionRate findById(final int id);
}
