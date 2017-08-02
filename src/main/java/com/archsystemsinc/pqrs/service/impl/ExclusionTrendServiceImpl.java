/**
 * 
 */
package com.archsystemsinc.pqrs.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.pqrs.constant.ReportingOptionEnum;
import com.archsystemsinc.pqrs.constant.YearNameEnum;
import com.archsystemsinc.pqrs.model.DataAnalysis;
import com.archsystemsinc.pqrs.model.ExclusionTrend;
import com.archsystemsinc.pqrs.model.ReportingOptionLookup;
import com.archsystemsinc.pqrs.model.SubDataAnalysis;
import com.archsystemsinc.pqrs.repository.DataAnalaysisRepository;
import com.archsystemsinc.pqrs.repository.ExclusionTrendRepository;
import com.archsystemsinc.pqrs.repository.ReportingOptionLookupRepository;
import com.archsystemsinc.pqrs.repository.SubDataAnalysisRepository;
import com.archsystemsinc.pqrs.service.ExclusionTrendService;

/**
 * @author Murugaraj Kandaswamy
 *
 */
@Service
public class ExclusionTrendServiceImpl implements ExclusionTrendService {
	
	@Autowired
	private ExclusionTrendRepository exclusionTrendRepository;
	
	@Autowired
	private DataAnalaysisRepository dataAnalaysisRepository;
	
	@Autowired
	private SubDataAnalysisRepository subDataAnalysisRepository;
	
	@Autowired
	private ReportingOptionLookupRepository reportingOptionLookupRepository;
	
	/**
	 * 
	 * @param dataAnalysisId
	 * @param subDataAnalysisId
	 * @param parameterId
	 * @return
	 */
	@Override
	public List<ExclusionTrend> findByDataAnalysisAndSubDataAnalysis(int dataAnalysisId, int subDataAnalysisId) {
		
		DataAnalysis dataAnalysis = dataAnalaysisRepository.findById(dataAnalysisId);
		SubDataAnalysis subDataAnalysis = subDataAnalysisRepository.findById(subDataAnalysisId);
		
		return exclusionTrendRepository.findByDataAnalysisAndSubDataAnalysis(dataAnalysis, subDataAnalysis);

	}
	
	/**
	 * 
	 */
	@Override
	public List<String> getUniqueYearsForLineChart() {
		List<String> uniqueYears = new ArrayList<String>();
		
		uniqueYears.add(YearNameEnum.BASE_YEAR.getYearName()+"");
		uniqueYears.add(YearNameEnum.OPTIONAL_YEAR_1.getYearName()+"");
		uniqueYears.add(YearNameEnum.OPTIONAL_YEAR_2.getYearName()+"");
		uniqueYears.add(YearNameEnum.OPTIONAL_YEAR_3.getYearName()+"");
		
		return uniqueYears;
		
	}
	
	/**
	 * 
	 */
	@Override
	public boolean setMeanExclusionRatePercentValue(List<ExclusionTrend> exclusionTrendList, List<Double> claimsPercents, List<Double> ehrPercents,
			List<Double> registryPercents, List<Double> gprowiPercents, List<Double> qcdrPercents) {
		
		Map<String, Double> claimsPercentMap = new HashMap<String, Double>();
		Map<String, Double> ehrPercentMap = new HashMap<String, Double>();
		Map<String, Double> registryPercentMap = new HashMap<String, Double>();
		Map<String, Double> gprowiPercentMap = new HashMap<String, Double>();
		Map<String, Double> qcdrPercentMap = new HashMap<String, Double>();
		
		for (ExclusionTrend exclusionTrend : exclusionTrendList){
			
			if (exclusionTrend.getYearLookup().getYearName().equalsIgnoreCase(YearNameEnum.ALL.getYearName())) continue;
			
			if (exclusionTrend.getReportingOptionLookup().getReportingOptionName().equalsIgnoreCase(ReportingOptionEnum.CLAIMS.getReportingOptionName())) {
				
				if (exclusionTrend.getMeanExclusionRatePercent() == null) {
					claimsPercentMap.put(exclusionTrend.getYearLookup().getYearName(), null);
				} else {
					claimsPercentMap.put(exclusionTrend.getYearLookup().getYearName(), exclusionTrend.getMeanExclusionRatePercent()==0.0 ? null : exclusionTrend.getMeanExclusionRatePercent());
				}
				
			} else if (exclusionTrend.getReportingOptionLookup().getReportingOptionName().equalsIgnoreCase(ReportingOptionEnum.EHR.getReportingOptionName())) {
				
				if (exclusionTrend.getMeanExclusionRatePercent() == null) {
					ehrPercentMap.put(exclusionTrend.getYearLookup().getYearName(), null);
				} else {
					ehrPercentMap.put(exclusionTrend.getYearLookup().getYearName(), exclusionTrend.getMeanExclusionRatePercent()==0.0 ? null : exclusionTrend.getMeanExclusionRatePercent());
				}
				
			}else if (exclusionTrend.getReportingOptionLookup().getReportingOptionName().equalsIgnoreCase(ReportingOptionEnum.REGISTRY.getReportingOptionName())) {
				
				if (exclusionTrend.getMeanExclusionRatePercent() == null) {
					registryPercentMap.put(exclusionTrend.getYearLookup().getYearName(), null);
				} else {
					registryPercentMap.put(exclusionTrend.getYearLookup().getYearName(), exclusionTrend.getMeanExclusionRatePercent()==0.0 ? null : exclusionTrend.getMeanExclusionRatePercent());
				}
				
			}else if (exclusionTrend.getReportingOptionLookup().getReportingOptionName().equalsIgnoreCase(ReportingOptionEnum.GPROWI.getReportingOptionName())) {
				
				if (exclusionTrend.getMeanExclusionRatePercent() == null) {
					gprowiPercentMap.put(exclusionTrend.getYearLookup().getYearName(), null);
				} else {
					gprowiPercentMap.put(exclusionTrend.getYearLookup().getYearName(), exclusionTrend.getMeanExclusionRatePercent()==0.0 ? null : exclusionTrend.getMeanExclusionRatePercent());
				}
				
			} else if (exclusionTrend.getReportingOptionLookup().getReportingOptionName().equalsIgnoreCase(ReportingOptionEnum.QCDR.getReportingOptionName())) {
				
				if (exclusionTrend.getMeanExclusionRatePercent() == null) {
					qcdrPercentMap.put(exclusionTrend.getYearLookup().getYearName(), null);
				} else {
					qcdrPercentMap.put(exclusionTrend.getYearLookup().getYearName(), exclusionTrend.getMeanExclusionRatePercent()==0.0 ? null : exclusionTrend.getMeanExclusionRatePercent());
				}
				
			} 
		
		}
		
		sortRPPercentByYear(claimsPercentMap, claimsPercents);
		sortRPPercentByYear(ehrPercentMap, ehrPercents);
		sortRPPercentByYear(registryPercentMap, registryPercents);
		sortRPPercentByYear(gprowiPercentMap, gprowiPercents);
		sortRPPercentByYear(qcdrPercentMap, qcdrPercents);
		
		return true;
	}
	
	/**
	 * 
	 * @param rpPercentMap
	 * @param rpPercents
	 */
	private void sortRPPercentByYear(Map<String, Double> rpPercentMap, List<Double> rpPercents) {
		rpPercents.add(rpPercentMap.get(YearNameEnum.BASE_YEAR.getYearName()));
		rpPercents.add(rpPercentMap.get(YearNameEnum.OPTIONAL_YEAR_1.getYearName()));
		rpPercents.add(rpPercentMap.get(YearNameEnum.OPTIONAL_YEAR_2.getYearName()));
		rpPercents.add(rpPercentMap.get(YearNameEnum.OPTIONAL_YEAR_3.getYearName()));
	}

	@Override
	public List<ExclusionTrend> findByDataAnalysisAndSubDataAnalysisAndReportingOptionLookup(int dataAnalysisId,
			int subDataAnalysisId, int reportingOptionId) {
		DataAnalysis dataAnalysis = dataAnalaysisRepository.findById(dataAnalysisId);
		SubDataAnalysis subDataAnalysis = subDataAnalysisRepository.findById(subDataAnalysisId);
		ReportingOptionLookup reportingOptionLookup = reportingOptionLookupRepository.findById(reportingOptionId);
	return exclusionTrendRepository.findByDataAnalysisAndSubDataAnalysisAndReportingOptionLookup(dataAnalysis, subDataAnalysis, reportingOptionLookup);
	}

}
