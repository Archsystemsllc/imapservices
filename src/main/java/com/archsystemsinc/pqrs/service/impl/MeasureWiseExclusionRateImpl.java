package com.archsystemsinc.pqrs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.pqrs.constant.ReportingOptionEnum;
import com.archsystemsinc.pqrs.constant.YearNameEnum;
import com.archsystemsinc.pqrs.model.DataAnalysis;
import com.archsystemsinc.pqrs.model.MeasureLookup;
import com.archsystemsinc.pqrs.model.MeasureWiseExclusionRate;
import com.archsystemsinc.pqrs.model.ProviderHypothesis;
import com.archsystemsinc.pqrs.model.SubDataAnalysis;
import com.archsystemsinc.pqrs.repository.MeasureWiseExclusionRateRepository;
import com.archsystemsinc.pqrs.repository.impl.Hypothesis5Repository;
import com.archsystemsinc.pqrs.service.DataAnalysisService;
import com.archsystemsinc.pqrs.service.MeasureLookupService;
import com.archsystemsinc.pqrs.service.MeasureWiseExclusionRateService;
import com.archsystemsinc.pqrs.service.SubDataAnalysisService;

/**
 * @author Grmahun Redda
 *
 */
@Service
public class MeasureWiseExclusionRateImpl implements MeasureWiseExclusionRateService{

	@Autowired
	private MeasureWiseExclusionRateRepository measureWiseExclusionRateRepository;
	
	@Autowired
	private MeasureLookupService measureLookupService;
	
	@Autowired
	private DataAnalysisService dataAnalysisService;
	
	@Autowired
	private SubDataAnalysisService subDataAnalysisService;
	
	@Autowired
	private Hypothesis5Repository hypothesis5Repository;
	
	@Override
	public List<MeasureWiseExclusionRate> findAll() {		
		return measureWiseExclusionRateRepository.findAll();
	}

	@Override
	public MeasureWiseExclusionRate findById(int id) {		
		return measureWiseExclusionRateRepository.findById(id);
	}

	@Override
	public List<MeasureWiseExclusionRate> findByMeasureLookupAndDataAnalysisAndSubDataAnalysis(
			MeasureLookup measureLookup, DataAnalysis dataAnalysis, SubDataAnalysis SubDataAnalysis) {
		
		return measureWiseExclusionRateRepository.findByMeasureLookupAndDataAnalysisAndSubDataAnalysis(measureLookup, dataAnalysis, SubDataAnalysis);
	}
	
	/**
	 * 
	 */
	@Override
	public boolean setExclusionValue(int dataAnalysisId, int subdataAnalysisId,List<Integer> measureLookupIdList, List<String> allowableExclusionsList, 
			List<String> reportingOptionsList, List<Double> measure1Data, List<Double> measure2Data, List<Double> measure3Data, List<Double> measure4Data) {
		
		Map<String, Double> measure1Map = null;
		Map<String, Double> measure2Map = null;
		Map<String, Double> measure3Map = null;
		Map<String, Double> measure4Map = null;		
		List<MeasureWiseExclusionRate>  measureWiseExclusionRateList = null;
		
		for(int i = 0; i < measureLookupIdList.size(); i++){
			if(i == 0) measure1Map = new HashMap<>();
			if(i == 1) measure2Map = new HashMap<>();
			if(i == 2) measure3Map = new HashMap<>();
			if(i == 3) measure4Map = new HashMap<>();
		}
		
		for (Integer measureLookupId : measureLookupIdList){
			
			//measureWiseExclusionRateList = measureWiseExclusionRateRepository.findByMeasureLookupAndDataAnalysisAndSubDataAnalysis(measureLookupService.findById(measureLookupId), 
					//dataAnalysisService.findById(dataAnalysisId), subDataAnalysisService.findById(subdataAnalysisId));
			
			measureWiseExclusionRateList = hypothesis5Repository.getMeasureWiseExclusionRateList(measureLookupId, dataAnalysisId, subdataAnalysisId);
			
			if(!measureWiseExclusionRateList.isEmpty()){ 
				allowableExclusionsList.add(measureWiseExclusionRateList.get(0).getExclusionDecisions());
				reportingOptionsList.add(measureWiseExclusionRateList.get(0).getReportingOptions());
			}else{
				allowableExclusionsList.add(null);
				reportingOptionsList.add(null);
			}
			for (MeasureWiseExclusionRate measureWiseExclusionRate : measureWiseExclusionRateList){
			    if (measureWiseExclusionRate.getYearLookup().getYearName().equalsIgnoreCase(YearNameEnum.ALL.getYearName())) continue;
			    
				if(measureWiseExclusionRate.getMeasureLookup().getId() == measureLookupIdList.get(0)){
					measure1Map.put(measureWiseExclusionRate.getYearLookup().getYearName(), measureWiseExclusionRate.getMeanExclusionRate() == 0.0 ? null : measureWiseExclusionRate.getMeanExclusionRate());					
				}else if(measureWiseExclusionRate.getMeasureLookup().getId() == measureLookupIdList.get(1)){
					measure2Map.put(measureWiseExclusionRate.getYearLookup().getYearName(), measureWiseExclusionRate.getMeanExclusionRate() == 0.0 ? null : measureWiseExclusionRate.getMeanExclusionRate());
				}else if(measureWiseExclusionRate.getMeasureLookup().getId() == measureLookupIdList.get(2)){
					measure3Map.put(measureWiseExclusionRate.getYearLookup().getYearName(), measureWiseExclusionRate.getMeanExclusionRate() == 0.0 ? null : measureWiseExclusionRate.getMeanExclusionRate());
				}else if(measureWiseExclusionRate.getMeasureLookup().getId() == measureLookupIdList.get(3)){
					measure4Map.put(measureWiseExclusionRate.getYearLookup().getYearName(), measureWiseExclusionRate.getMeanExclusionRate() == 0.0 ? null : measureWiseExclusionRate.getMeanExclusionRate());
				}	
			}

		}
		
		if(measureLookupIdList.size() > 0) sortDataByYear(measure1Map, measure1Data);
		if(measureLookupIdList.size() > 1) sortDataByYear(measure2Map, measure2Data);
		if(measureLookupIdList.size() > 2) sortDataByYear(measure3Map, measure3Data);
		if(measureLookupIdList.size() > 3) sortDataByYear(measure4Map, measure4Data);
		translateReportingOptionsList(reportingOptionsList);
		
		return true;
	}
	
	@Override
	public boolean setFreqValue(int dataAnalysisId, int subdataAnalysisId, List<Integer> measureLookupIdList, List<String> allowableExclusionsList, List<String> reportingOptionsList,  List<Integer> measure1Data, List<Integer> measure2Data,
			List<Integer> measure3Data, List<Integer> measure4Data) {
		
		Map<String, Integer> measure1Map = null;
		Map<String, Integer> measure2Map = null;
		Map<String, Integer> measure3Map = null;
		Map<String, Integer> measure4Map = null;		
		List<MeasureWiseExclusionRate>  measureWiseExclusionRateList = null;
		
		for(int i = 0; i < measureLookupIdList.size(); i++){
			if(i == 0) measure1Map = new HashMap<>(); 
			if(i == 1) measure2Map = new HashMap<>();
			if(i == 2) measure3Map = new HashMap<>();
			if(i == 3) measure4Map = new HashMap<>();
		}
		
		for (Integer measureLookupId : measureLookupIdList){
			
			//measureWiseExclusionRateList = measureWiseExclusionRateRepository.findByMeasureLookupAndDataAnalysisAndSubDataAnalysis(measureLookupService.findById(measureLookupId), 
					//dataAnalysisService.findById(dataAnalysisId), subDataAnalysisService.findById(subdataAnalysisId));
			
			measureWiseExclusionRateList = hypothesis5Repository.getMeasureWiseExclusionRateList(measureLookupId, dataAnalysisId, subdataAnalysisId);
			
			if(!measureWiseExclusionRateList.isEmpty()){ 
				allowableExclusionsList.add(measureWiseExclusionRateList.get(0).getExclusionDecisions());
				reportingOptionsList.add(measureWiseExclusionRateList.get(0).getReportingOptions());
			}else{
				allowableExclusionsList.add(null);
				reportingOptionsList.add(null);
				//allowableExclusionsList.add("");
				//reportingOptionsList.add("");
			}
			for (MeasureWiseExclusionRate measureWiseExclusionRate : measureWiseExclusionRateList){
			    if (measureWiseExclusionRate.getYearLookup().getYearName().equalsIgnoreCase(YearNameEnum.ALL.getYearName())) continue;
			    
				if(measureWiseExclusionRate.getMeasureLookup().getId() == measureLookupIdList.get(0)){
					measure1Map.put(measureWiseExclusionRate.getYearLookup().getYearName(), measureWiseExclusionRate.getFrequencies() == 0 ? null : measureWiseExclusionRate.getFrequencies());					
				}else if(measureWiseExclusionRate.getMeasureLookup().getId() == measureLookupIdList.get(1)){
					measure2Map.put(measureWiseExclusionRate.getYearLookup().getYearName(), measureWiseExclusionRate.getFrequencies() == 0 ? null : measureWiseExclusionRate.getFrequencies());
				}else if(measureWiseExclusionRate.getMeasureLookup().getId() == measureLookupIdList.get(2)){
					measure3Map.put(measureWiseExclusionRate.getYearLookup().getYearName(), measureWiseExclusionRate.getFrequencies() == 0 ? null : measureWiseExclusionRate.getFrequencies());
				}else if(measureWiseExclusionRate.getMeasureLookup().getId() == measureLookupIdList.get(3)){
					measure4Map.put(measureWiseExclusionRate.getYearLookup().getYearName(), measureWiseExclusionRate.getFrequencies() == 0 ? null : measureWiseExclusionRate.getFrequencies());
				}	
			}

		}
		
		if(measureLookupIdList.size() > 0) sortFreqDataByYear(measure1Map, measure1Data);
		if(measureLookupIdList.size() > 1) sortFreqDataByYear(measure2Map, measure2Data);
		if(measureLookupIdList.size() > 2) sortFreqDataByYear(measure3Map, measure3Data);
		if(measureLookupIdList.size() > 3) sortFreqDataByYear(measure4Map, measure4Data);
		translateReportingOptionsList(reportingOptionsList);
		
		return true;		
	}
	


	/**
	 * 
	 * @param rpPercentMap
	 * @param rpPercents
	 */
	private void sortDataByYear(Map<String, Double> dataMap, List<Double> dataList) {
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
	
	private void translateReportingOptionsList(List<String> reportingOptionsList) {
		int index = 0;
		String temp = "";
		if(reportingOptionsList != null)
		System.out.println("Size:" + reportingOptionsList.size());
		
		for(String measureReportingOptions: reportingOptionsList){
			System.out.println("measureReportingOptions: " + measureReportingOptions);
			String[] reportingOptions = null;
			
			if(measureReportingOptions != null){
				reportingOptions = measureReportingOptions.split(",");			
			
				for(String reportingOption:reportingOptions){
					   if(reportingOption.equals("0")) break;
					   
						switch (reportingOption) {
				         case "1":
				        	 temp += "CLAIMS";
				             break;
				         case "2":
				        	 temp += ", EHR";
				             break;
				         case "3":
				        	 temp += ", REGISTRY";
				             break;
				         case "4":
				        	 temp += ", GPROWI";
				             break;
				         case "5":
				        	 temp += ", QCDR";
				             break;
				         case "6":
				        	 temp += ", GPRO Registry";
				             break;
				         case "7":
				        	 temp += ", GPRO EHR";
				             break;
				         case "8":
				        	 temp += ", ALL";
				             break;
				         case "9":
				        	 temp += ", ALL GPRO";
				             break;
				         case "10":
				        	 temp += ", ALL EPs & GPRO";
				             break;
				         default:
				        	 temp += ", None";
				     }
				}
			}
			reportingOptionsList.set(index, temp);
			temp = "";
			index++;
		}		
	}
	
	@Override
	public List<MeasureWiseExclusionRate> findByDataAnalysisAndSubDataAnalysis(DataAnalysis dataAnalysis,
			SubDataAnalysis SubDataAnalysis) {		
		return measureWiseExclusionRateRepository.findByDataAnalysisAndSubDataAnalysis(dataAnalysis, SubDataAnalysis);
	}

	@Override
	public MeasureWiseExclusionRate create(MeasureWiseExclusionRate measureWiseExclusionRate) {
		return measureWiseExclusionRateRepository.saveAndFlush(measureWiseExclusionRate);
	}

	

}
