package com.archsystemsinc.pqrs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.pqrs.model.MeasureWiseExclusionRate;
import com.archsystemsinc.pqrs.repository.MeasureWiseExclusionRateRepository;
import com.archsystemsinc.pqrs.service.MeasureWiseExclusionRateService;

/**
 * @author Grmahun Redda
 *
 */
@Service
public class MeasureWiseExclusionRateImpl implements MeasureWiseExclusionRateService{

	@Autowired
	private MeasureWiseExclusionRateRepository measureWiseExclusionRateRepository;
	
	@Override
	public List<MeasureWiseExclusionRate> findAll() {		
		return measureWiseExclusionRateRepository.findAll();
	}

	@Override
	public MeasureWiseExclusionRate findById(int id) {		
		return measureWiseExclusionRateRepository.findById(id);
	}

}
