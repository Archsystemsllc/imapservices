package com.archsystemsinc.pqrs.service;

import java.util.List;

import com.archsystemsinc.pqrs.model.DataAnalysis;
import com.archsystemsinc.pqrs.model.MeasureLookup;
//import com.archsystemsinc.pqrs.model.MeasureWiseExclusionRate;
import com.archsystemsinc.pqrs.model.MeasureWisePerformanceAndReportingRate;
import com.archsystemsinc.pqrs.model.ReportingOptionLookup;
import com.archsystemsinc.pqrs.model.SubDataAnalysis;

public interface MeasureWisePerformanceAndReportingRateService {
	MeasureWisePerformanceAndReportingRate findById(final int id);
	List<MeasureWisePerformanceAndReportingRate> findAll();
	List<MeasureWisePerformanceAndReportingRate> findByDataAnalysisAndSubDataAnalysis(DataAnalysis dataAnalysis, SubDataAnalysis SubDataAnalysis);
	List<MeasureWisePerformanceAndReportingRate> findByMeasureLookupAndDataAnalysisAndSubDataAnalysisAndReportingOptionLookup(MeasureLookup measureLookup, DataAnalysis dataAnalysis, 
			SubDataAnalysis SubDataAnalysis, ReportingOptionLookup reportingOptionLookup);
	boolean setExclusionValue(int dataAnalysisId, int subdataAnalysisId, List<Integer> measureLookupIdList,  
			int reportingOptionId, List<Double> measure1Data,  List<Double> measure2Data, List<Double> measure3Data,  List<Double> measure4Data);
	boolean setFreqValue(int dataAnalysisId, int subdataAnalysisId, List<Integer> measureLookupIdList, 
			int reportingOptionId, List<Integer> measure1Data,  List<Integer> measure2Data,	List<Integer> measure3Data,  List<Integer> measure4Data);
	MeasureWisePerformanceAndReportingRate create(final MeasureWisePerformanceAndReportingRate measureWisePerformanceAndReportingRate);
}
