package com.archsystemsinc.pqrs.service;

import java.util.List;

import com.archsystemsinc.pqrs.model.CategoryLookup;

/**
 * @author Grmahun Redda
 *
 */
public interface CategoryLookupService {
	
	List<CategoryLookup> findAll();
	CategoryLookup findById(final int id);
}
