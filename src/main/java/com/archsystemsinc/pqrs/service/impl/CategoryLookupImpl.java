package com.archsystemsinc.pqrs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.pqrs.model.CategoryLookup;
import com.archsystemsinc.pqrs.repository.CategoryLookupRepository;
import com.archsystemsinc.pqrs.service.CategoryLookupService;

/**
 * @author Grmahun Redda
 *
 */
@Service
public class CategoryLookupImpl implements CategoryLookupService{
	
	@Autowired
	private CategoryLookupRepository categoryLookupRepository;

	@Override
	public List<CategoryLookup> findAll() {		
		return categoryLookupRepository.findAll();
	}

	@Override
	public CategoryLookup findById(int id) {		
		return categoryLookupRepository.findById(id);
	}
}
