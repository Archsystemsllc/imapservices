package com.archsystemsinc.pqrs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.archsystemsinc.pqrs.model.DataAnalysis;
import com.archsystemsinc.pqrs.model.MeasureLookup;
import com.archsystemsinc.pqrs.model.MeasureWisePerformanceAndReportingRate;
import com.archsystemsinc.pqrs.model.ReportingOptionLookup;
import com.archsystemsinc.pqrs.model.SubDataAnalysis;

public interface MeasureWisePerformanceAndReportingRateRepository extends JpaRepository<MeasureWisePerformanceAndReportingRate, Long>{
	MeasureWisePerformanceAndReportingRate findById(final int id);
	List<MeasureWisePerformanceAndReportingRate> findAll();
	List<MeasureWisePerformanceAndReportingRate> findByDataAnalysisAndSubDataAnalysis(DataAnalysis dataAnalysis, SubDataAnalysis SubDataAnalysis);
	List<MeasureWisePerformanceAndReportingRate> findByMeasureLookupAndDataAnalysisAndSubDataAnalysisAndReportingOptionLookup(MeasureLookup measureLookup, DataAnalysis dataAnalysis, 
			SubDataAnalysis SubDataAnalysis, ReportingOptionLookup reportingOptionLookup);
}
