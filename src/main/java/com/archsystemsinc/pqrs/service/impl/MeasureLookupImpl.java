package com.archsystemsinc.pqrs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.pqrs.model.MeasureLookup;
import com.archsystemsinc.pqrs.repository.MeasureLookupRepository;
import com.archsystemsinc.pqrs.service.MeasureLookupService;

/**
 * @author Grmahun Redda
 *
 */
@Service
public class MeasureLookupImpl implements MeasureLookupService{
	
	@Autowired
	private MeasureLookupRepository measureLookupRepository;

	@Override
	public List<MeasureLookup> findAll() {		
		return measureLookupRepository.findAll();
	}

	@Override
	public MeasureLookup findById(int id) {		
		return measureLookupRepository.findById(id);
	}

}
