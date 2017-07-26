/**
 * 
 */
package com.archsystemsinc.pqrs.service;

import java.util.List;

import com.archsystemsinc.pqrs.model.ExclusionTrend;

/**
 * This is the Service interface for exclusion_trend database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 7/20/2017
 * 
 */
public interface ExclusionTrendService {
	
	/**
	 * 
	 * @param dataAnalysisId
	 * @param subDataAnalysisId
	 * @param parameterId
	 * @return
	 */
	public List<ExclusionTrend> findByDataAnalysisAndSubDataAnalysis(int dataAnalysisId, int subDataAnalysisId);

	public List<String> getUniqueYearsForLineChart();
	
	public boolean setMeanExclusionRatePercentValue(List<ExclusionTrend> exclusionTrendList, List<Double> claimsPercents, List<Double> ehrPercents,
			List<Double> registryPercents, List<Double> gprowiPercents, List<Double> qcdrPercents);
	
}
