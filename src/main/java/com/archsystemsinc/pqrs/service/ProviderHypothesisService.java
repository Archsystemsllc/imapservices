package com.archsystemsinc.pqrs.service;

import java.util.List;

import com.archsystemsinc.pqrs.model.ProviderHypothesis;

/**
 * This is the Service interface for provider_hypothesis database table.
 * 
 * @author Murugaraj Kandaswamy, Grmahun Redda
 * @since 6/21/2017
 * 
 */
public interface ProviderHypothesisService {
	
	List<ProviderHypothesis> findByYearLookupAndReportingOptionLookup(String year, String reportingOption);
	
	List<ProviderHypothesis> findByParameterLookup(String parameterName);
	
	ProviderHypothesis create(final ProviderHypothesis providerHypothesis);	
	
	void update(final Long id);
	
	ProviderHypothesis findById(final Long id);
	
	void deleteById(final Long id);
	
	List<ProviderHypothesis> findAll();    
    
	List<ProviderHypothesis> findAllByUserId(Long id);  
	
	List<String> getUniqueYearsForLineChart();
	
	boolean setRPPercentValue(List<ProviderHypothesis> providerHypothesisList, List<Double> claimsPercents, List<Double> ehrPercents, List<Double> registryPercents, List<Double> gprowiPercents, List<Double> qcdrPercents);

	List<ProviderHypothesis> findByDataAnalysisAndSubDataAnalysisAndYearLookupAndReportingOptionLookup(String dataAnalysis, String subDataAnalysis, String year, String reportingOption);
	
	List<ProviderHypothesis> findByDataAnalysisAndSubDataAnalysisAndParameterLookup(String dataAnalysis, String subDataAnalysis, String parameterName);
	
}
