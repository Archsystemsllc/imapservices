/**
 * 
 */
package com.archsystemsinc.pqrs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.pqrs.model.YearLookup;
import com.archsystemsinc.pqrs.repository.YearLookupRepository;
import com.archsystemsinc.pqrs.service.YearLookupService;

/**
 * This is the implementation class of the Service interface for year_lookup database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/19/2017
 * 
 */
@Service
public class YearLookupServiceImpl implements YearLookupService {

	
	@Autowired
	private YearLookupRepository yearLookupRepository;
	
	/** (non-Javadoc)
	 * @see com.archsystemsinc.pqrs.service.YearLookUpservice#findByYearName(java.lang.String)
	 */
	@Override
	public YearLookup findByYearName(String yearName) {
		return yearLookupRepository.findByYearName(yearName);
	}

	/** (non-Javadoc)
	 * @see com.archsystemsinc.pqrs.service.YearLookupService#findAll()
	 */
	@Override
	public List<YearLookup> findAll() {
		return yearLookupRepository.findAll();
	}

	@Override
	public YearLookup findById(int id) {		
		return yearLookupRepository.findById(id);
	}

}
