package com.archsystemsinc.pqrs.service;

import java.util.List;

import com.archsystemsinc.pqrs.model.MeasureLookup;

/**
 * @author Grmahun Redda
 *
 */
public interface MeasureLookupService {
	
	List<MeasureLookup> findAll();
	MeasureLookup findById(final int id);
}
