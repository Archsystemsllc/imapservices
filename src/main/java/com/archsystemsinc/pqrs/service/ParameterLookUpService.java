/**
 * 
 */
package com.archsystemsinc.pqrs.service;

import java.util.List; 

import com.archsystemsinc.pqrs.model.ParameterLookup;

/**
 * This is the Service interface for parameter_lookup database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/21/2017
 * 
 */
public interface ParameterLookUpService { 

	ParameterLookup findByParameterName(final String parameterName);
	
	List<ParameterLookup> findAll();
	
}
