package com.archsystemsinc.pqrs.service;

import java.util.List;

import com.archsystemsinc.pqrs.model.DataAnalysis;
import com.archsystemsinc.pqrs.model.MeasureLookup;
import com.archsystemsinc.pqrs.model.MeasureWiseExclusionRate;
import com.archsystemsinc.pqrs.model.SubDataAnalysis;

/**
 * @author Grmahun Redda
 *
 */
public interface MeasureWiseExclusionRateService {
	
	List<MeasureWiseExclusionRate> findAll();
	MeasureWiseExclusionRate findById(final int id);
	List<MeasureWiseExclusionRate> findByDataAnalysisAndSubDataAnalysis(DataAnalysis dataAnalysis, SubDataAnalysis SubDataAnalysis);
	List<MeasureWiseExclusionRate> findByMeasureLookupAndDataAnalysisAndSubDataAnalysis(MeasureLookup measureLookup, DataAnalysis dataAnalysis, SubDataAnalysis SubDataAnalysis);
	boolean setExclusionValue(int dataAnalysisId, int subdataAnalysisId, List<Integer> measureLookupIdList, List<String> allowableExclusionsList, 
			List<String> reportingOptionsList, List<Double> measure1Data,  List<Double> measure2Data, List<Double> measure3Data,  List<Double> measure4Data);
	boolean setFreqValue(int dataAnalysisId, int subdataAnalysisId, List<Integer> measureLookupIdList, List<String> allowableExclusionsList, 
			List<String> reportingOptionsList, List<Integer> measure1Data,  List<Integer> measure2Data,	List<Integer> measure3Data,  List<Integer> measure4Data);
	MeasureWiseExclusionRate create(final MeasureWiseExclusionRate measureWiseExclusionRate);
}
