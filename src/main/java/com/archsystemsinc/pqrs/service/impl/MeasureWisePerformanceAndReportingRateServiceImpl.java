package com.archsystemsinc.pqrs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.pqrs.constant.YearNameEnum;
import com.archsystemsinc.pqrs.model.DataAnalysis;
import com.archsystemsinc.pqrs.model.MeasureLookup;
import com.archsystemsinc.pqrs.model.MeasureWisePerformanceAndReportingRate;
import com.archsystemsinc.pqrs.model.ReportingOptionLookup;
import com.archsystemsinc.pqrs.model.SubDataAnalysis;
import com.archsystemsinc.pqrs.repository.MeasureWisePerformanceAndReportingRateRepository;
import com.archsystemsinc.pqrs.service.DataAnalysisService;
import com.archsystemsinc.pqrs.service.MeasureLookupService;
import com.archsystemsinc.pqrs.service.MeasureWisePerformanceAndReportingRateService;
import com.archsystemsinc.pqrs.service.ReportingOptionLookUpService;
import com.archsystemsinc.pqrs.service.SubDataAnalysisService;

@Service
public class MeasureWisePerformanceAndReportingRateServiceImpl implements MeasureWisePerformanceAndReportingRateService{

	@Autowired
	private MeasureWisePerformanceAndReportingRateRepository measureWisePerformanceAndReportingRateRepository;
	
	@Autowired
	private MeasureLookupService measureLookupService;
	
	@Autowired
	private DataAnalysisService dataAnalysisService;
	
	@Autowired
	private SubDataAnalysisService subDataAnalysisService;
	
	@Autowired
	private ReportingOptionLookUpService reportingOptionLookUpService;
	
	@Override
	public MeasureWisePerformanceAndReportingRate findById(int id) {		
		return measureWisePerformanceAndReportingRateRepository.findById(id);
	}

	@Override
	public List<MeasureWisePerformanceAndReportingRate> findAll() {		
		return measureWisePerformanceAndReportingRateRepository.findAll();
	}

	@Override
	public List<MeasureWisePerformanceAndReportingRate> findByDataAnalysisAndSubDataAnalysis(DataAnalysis dataAnalysis,
			SubDataAnalysis SubDataAnalysis) {		
		return measureWisePerformanceAndReportingRateRepository.findByDataAnalysisAndSubDataAnalysis(dataAnalysis, SubDataAnalysis);
	}

	@Override
	public List<MeasureWisePerformanceAndReportingRate> findByMeasureLookupAndDataAnalysisAndSubDataAnalysisAndReportingOptionLookup(
			MeasureLookup measureLookup, DataAnalysis dataAnalysis, SubDataAnalysis SubDataAnalysis,
			ReportingOptionLookup reportingOptionLookup) {		
		return measureWisePerformanceAndReportingRateRepository.findByMeasureLookupAndDataAnalysisAndSubDataAnalysisAndReportingOptionLookup(measureLookup, 
				dataAnalysis, SubDataAnalysis, reportingOptionLookup);
	}

	@Override
	public boolean setExclusionValue(int dataAnalysisId, int subdataAnalysisId, List<Integer> measureLookupIdList,
			int reportingOptionId, List<Double> measure1Data, List<Double> measure2Data,
			List<Double> measure3Data, List<Double> measure4Data) {
		Map<String, Double> measure1Map = null;
		Map<String, Double> measure2Map = null;
		Map<String, Double> measure3Map = null;
		Map<String, Double> measure4Map = null;		
		List<MeasureWisePerformanceAndReportingRate>  measureWisePerformanceAndReportingRateList = null;
		
		for(int i = 0; i < measureLookupIdList.size(); i++){
			if(i == 0) measure1Map = new HashMap<>();
			if(i == 1) measure2Map = new HashMap<>();
			if(i == 2) measure3Map = new HashMap<>();
			if(i == 3) measure4Map = new HashMap<>();
		}
		
		for (Integer measureLookupId : measureLookupIdList){
			
			measureWisePerformanceAndReportingRateList = measureWisePerformanceAndReportingRateRepository.findByMeasureLookupAndDataAnalysisAndSubDataAnalysisAndReportingOptionLookup(measureLookupService.findById(measureLookupId), 
					dataAnalysisService.findById(dataAnalysisId), subDataAnalysisService.findById(subdataAnalysisId), reportingOptionLookUpService.findById(reportingOptionId));

			for (MeasureWisePerformanceAndReportingRate measureWisePerformanceAndReportingRate : measureWisePerformanceAndReportingRateList){
			    if (measureWisePerformanceAndReportingRate.getYearLookup().getYearName().equalsIgnoreCase(YearNameEnum.ALL.getYearName())) continue;
			    
				if(measureWisePerformanceAndReportingRate.getMeasureLookup().getId() == measureLookupIdList.get(0)){
					measure1Map.put(measureWisePerformanceAndReportingRate.getYearLookup().getYearName(), measureWisePerformanceAndReportingRate.getMeanExclusionRate() == 0.0 ? null : measureWisePerformanceAndReportingRate.getMeanExclusionRate());					
				}else if(measureWisePerformanceAndReportingRate.getMeasureLookup().getId() == measureLookupIdList.get(1)){
					measure2Map.put(measureWisePerformanceAndReportingRate.getYearLookup().getYearName(), measureWisePerformanceAndReportingRate.getMeanExclusionRate() == 0.0 ? null : measureWisePerformanceAndReportingRate.getMeanExclusionRate());
				}else if(measureWisePerformanceAndReportingRate.getMeasureLookup().getId() == measureLookupIdList.get(2)){
					measure3Map.put(measureWisePerformanceAndReportingRate.getYearLookup().getYearName(), measureWisePerformanceAndReportingRate.getMeanExclusionRate() == 0.0 ? null : measureWisePerformanceAndReportingRate.getMeanExclusionRate());
				}else if(measureWisePerformanceAndReportingRate.getMeasureLookup().getId() == measureLookupIdList.get(3)){
					measure4Map.put(measureWisePerformanceAndReportingRate.getYearLookup().getYearName(), measureWisePerformanceAndReportingRate.getMeanExclusionRate() == 0.0 ? null : measureWisePerformanceAndReportingRate.getMeanExclusionRate());
				}	
			}

		}
		
		if(measureLookupIdList.size() > 0) sortExclusionDataByYear(measure1Map, measure1Data);
		if(measureLookupIdList.size() > 1) sortExclusionDataByYear(measure2Map, measure2Data);
		if(measureLookupIdList.size() > 2) sortExclusionDataByYear(measure3Map, measure3Data);
		if(measureLookupIdList.size() > 3) sortExclusionDataByYear(measure4Map, measure4Data);
		
		return true;
	}

	@Override
	public boolean setFreqValue(int dataAnalysisId, int subdataAnalysisId, List<Integer> measureLookupIdList,
			int reportingOptionId, List<Integer> measure1Data, List<Integer> measure2Data,
			List<Integer> measure3Data, List<Integer> measure4Data) {
		Map<String, Integer> measure1Map = null;
		Map<String, Integer> measure2Map = null;
		Map<String, Integer> measure3Map = null;
		Map<String, Integer> measure4Map = null;		
		List<MeasureWisePerformanceAndReportingRate>  measureWisePerformanceAndReportingRateList = null;
		
		for(int i = 0; i < measureLookupIdList.size(); i++){
			if(i == 0) measure1Map = new HashMap<>(); 
			if(i == 1) measure2Map = new HashMap<>();
			if(i == 2) measure3Map = new HashMap<>();
			if(i == 3) measure4Map = new HashMap<>();
		}
		
		for (Integer measureLookupId : measureLookupIdList){
			
			measureWisePerformanceAndReportingRateList = measureWisePerformanceAndReportingRateRepository.findByMeasureLookupAndDataAnalysisAndSubDataAnalysisAndReportingOptionLookup(measureLookupService.findById(measureLookupId), 
					dataAnalysisService.findById(dataAnalysisId), subDataAnalysisService.findById(subdataAnalysisId), reportingOptionLookUpService.findById(reportingOptionId));

			for (MeasureWisePerformanceAndReportingRate measureWisePerformanceAndReportingRate : measureWisePerformanceAndReportingRateList){
			    if (measureWisePerformanceAndReportingRate.getYearLookup().getYearName().equalsIgnoreCase(YearNameEnum.ALL.getYearName())) continue;
			    
				if(measureWisePerformanceAndReportingRate.getMeasureLookup().getId() == measureLookupIdList.get(0)){
					measure1Map.put(measureWisePerformanceAndReportingRate.getYearLookup().getYearName(), measureWisePerformanceAndReportingRate.getFrequencies() == 0 ? null : measureWisePerformanceAndReportingRate.getFrequencies());					
				}else if(measureWisePerformanceAndReportingRate.getMeasureLookup().getId() == measureLookupIdList.get(1)){
					measure2Map.put(measureWisePerformanceAndReportingRate.getYearLookup().getYearName(), measureWisePerformanceAndReportingRate.getFrequencies() == 0 ? null : measureWisePerformanceAndReportingRate.getFrequencies());
				}else if(measureWisePerformanceAndReportingRate.getMeasureLookup().getId() == measureLookupIdList.get(2)){
					measure3Map.put(measureWisePerformanceAndReportingRate.getYearLookup().getYearName(), measureWisePerformanceAndReportingRate.getFrequencies() == 0 ? null : measureWisePerformanceAndReportingRate.getFrequencies());
				}else if(measureWisePerformanceAndReportingRate.getMeasureLookup().getId() == measureLookupIdList.get(3)){
					measure4Map.put(measureWisePerformanceAndReportingRate.getYearLookup().getYearName(), measureWisePerformanceAndReportingRate.getFrequencies() == 0 ? null : measureWisePerformanceAndReportingRate.getFrequencies());
				}	
			}

		}
		
		if(measureLookupIdList.size() > 0) sortFreqDataByYear(measure1Map, measure1Data);
		if(measureLookupIdList.size() > 1) sortFreqDataByYear(measure2Map, measure2Data);
		if(measureLookupIdList.size() > 2) sortFreqDataByYear(measure3Map, measure3Data);
		if(measureLookupIdList.size() > 3) sortFreqDataByYear(measure4Map, measure4Data);
				
		return true;		
	}

	@Override
	public MeasureWisePerformanceAndReportingRate create(
			MeasureWisePerformanceAndReportingRate measureWisePerformanceAndReportingRate) {
		return measureWisePerformanceAndReportingRateRepository.saveAndFlush(measureWisePerformanceAndReportingRate);
	}
	
	private void sortExclusionDataByYear(Map<String, Double> dataMap, List<Double> dataList) {
		dataList.add(dataMap.get(YearNameEnum.BASE_YEAR.getYearName()));
		dataList.add(dataMap.get(YearNameEnum.OPTIONAL_YEAR_1.getYearName()));
		dataList.add(dataMap.get(YearNameEnum.OPTIONAL_YEAR_2.getYearName()));
		dataList.add(dataMap.get(YearNameEnum.OPTIONAL_YEAR_3.getYearName()));
	}
	
	private void sortFreqDataByYear(Map<String, Integer> dataMap, List<Integer> dataList) {
		dataList.add(dataMap.get(YearNameEnum.BASE_YEAR.getYearName()));
		dataList.add(dataMap.get(YearNameEnum.OPTIONAL_YEAR_1.getYearName()));
		dataList.add(dataMap.get(YearNameEnum.OPTIONAL_YEAR_2.getYearName()));
		dataList.add(dataMap.get(YearNameEnum.OPTIONAL_YEAR_3.getYearName()));
	}

}
