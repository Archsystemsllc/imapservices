package com.archsystemsinc.pqrs.restcontroller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archsystemsinc.pqrs.model.CategoryLookup;
import com.archsystemsinc.pqrs.model.MeasureWiseExclusionRate;
import com.archsystemsinc.pqrs.model.MeasureWisePerformanceAndReportingRate;
import com.archsystemsinc.pqrs.repository.MeasureWisePerformanceAndReportingRateRepository;
import com.archsystemsinc.pqrs.service.DataAnalysisService;
import com.archsystemsinc.pqrs.service.MeasureLookupService;
import com.archsystemsinc.pqrs.service.MeasureWiseExclusionRateService;
import com.archsystemsinc.pqrs.service.MeasureWisePerformanceAndReportingRateService;
import com.archsystemsinc.pqrs.service.ProviderHypothesisService;
import com.archsystemsinc.pqrs.service.SubDataAnalysisService;

/**
 * @author Grmahun Redda
 *
 */

@RestController
@RequestMapping("/api")
public class MeasureWiseExclusionRateRestController {
	private static final Logger log = Logger.getLogger(MeasureWiseExclusionRateRestController.class);
	
	@Autowired
	private MeasureWiseExclusionRateService measureWiseExclusionRateService;
	
	@Autowired
	private MeasureWisePerformanceAndReportingRateService measureWisePerformanceAndReportingRateService;
	
	@Autowired
	private MeasureLookupService measureLookupService;
	
	@Autowired
	private DataAnalysisService dataAnalysisService;
	
	@Autowired
	private SubDataAnalysisService subDataAnalysisService;
	
	@Autowired
	private ProviderHypothesisService providerHypothesisService;
	
	//Edit
	@RequestMapping(value = "/measureExclusionRate/all", method = RequestMethod.GET)
	public List<MeasureWiseExclusionRate> getMeasureExclusionRates(HttpServletRequest request, Principal currentUser){
		log.debug("--> getMeasureExclusionRates");
		final List<MeasureWiseExclusionRate> measureExclusionRateList = measureWiseExclusionRateService.findAll();
		log.debug("<-- getMeasureExclusionRates");
        return measureExclusionRateList;
	}
	
	//Edit
	@RequestMapping(value = "/measureExclusionRate/{measureExclusionRateId}", method = RequestMethod.GET)
	public MeasureWiseExclusionRate getCategoryById(@PathVariable int measureExclusionRateId, HttpServletRequest request, Principal currentUser, final Model model){		
		return measureWiseExclusionRateService.findById(measureExclusionRateId);
	}
	
	//Edit
	@RequestMapping(value = "/measureExclusionRate/dataAnalysisId/{dataAnalysisId}/subDataAnalysisId/{subdataAnalysisId}/measure/{params}", method = RequestMethod.GET)
	public Map getMeasureExclusions(@PathVariable int dataAnalysisId,
			@PathVariable int subdataAnalysisId, @PathVariable List<Integer> params, HttpServletRequest request, 
			Principal currentUser, final Model model){	
		
		Map lineChartDataMap = new HashMap();
		String dataAvailable = "NO";
		List<Integer> measureLookupIdList = params;
		List<String> measureIdList = new ArrayList<>();
		List<String> allowableExclusionsList = new ArrayList<>();
		List<String> reportingOptionsList = new ArrayList<>();
		
		List<Double> measureData1 = null;
		List<Double> measureData2 = null;
		List<Double> measureData3 = null;
		List<Double> measureData4 = null;
		
		for(int i = 0; i < measureLookupIdList.size(); i++){
			if(i == 0) measureData1 = new ArrayList<>();
			if(i == 1) measureData2 = new ArrayList<>();
			if(i == 2) measureData3 = new ArrayList<>();
			if(i == 3) measureData4 = new ArrayList<>();
			
			measureIdList.add(measureLookupService.findById(measureLookupIdList.get(i)).getMeasureId());
		}
		
		List<String> uniqueYears = providerHypothesisService.getUniqueYearsForLineChart();
		measureWiseExclusionRateService.setExclusionValue(dataAnalysisId, subdataAnalysisId, measureLookupIdList, 
				allowableExclusionsList, reportingOptionsList, measureData1, measureData2, 
				measureData3, measureData4);
		
		lineChartDataMap.put("uniqueYears", uniqueYears);
		if(measureLookupIdList.size() > 0) lineChartDataMap.put("measureData1", measureData1);
		if(measureLookupIdList.size() > 1) lineChartDataMap.put("measureData2", measureData2);
		if(measureLookupIdList.size() > 2) lineChartDataMap.put("measureData3", measureData3);
		if(measureLookupIdList.size() > 3) lineChartDataMap.put("measureData4", measureData4);
		
		
		lineChartDataMap.put("measureLookupIdList", measureLookupIdList);
		lineChartDataMap.put("measureIdList", measureIdList);
		lineChartDataMap.put("allowableExclusionsList", allowableExclusionsList);
		lineChartDataMap.put("reportingOptionsList", reportingOptionsList);
		
		if (measureData1 != null && measureData1.size()>0){
			for (Double data : measureData1) {
				if (data != null) {
					dataAvailable = "YES";
					break;
				}
			}
		}
		System.out.println("test Data for Line Chart Data(AVAILABLE)::"+dataAvailable);
		lineChartDataMap.put("dataAvailable", dataAvailable);
		
		return lineChartDataMap;
		
	}
	
	//Edit
	@RequestMapping(value = "/measureFrequency/dataAnalysisId/{dataAnalysisId}/subDataAnalysisId/{subdataAnalysisId}/measure/{params}", method = RequestMethod.GET)
	public Map getFrequency(@PathVariable int dataAnalysisId,
			@PathVariable int subdataAnalysisId, @PathVariable List<Integer> params, HttpServletRequest request, 
			Principal currentUser, final Model model){	
		
		Map lineChartDataMap = new HashMap();
		String dataAvailable = "NO";
		List<Integer> measureLookupIdList = params;
		List<String> measureIdList = new ArrayList<>();		
		List<String> allowableExclusionsList = new ArrayList<>();
		List<String> reportingOptionsList = new ArrayList<>();
		
		List<Integer> measureData1 = null;
		List<Integer> measureData2 = null;
		List<Integer> measureData3 = null;
		List<Integer> measureData4 = null;
		
		for(int i = 0; i < measureLookupIdList.size(); i++){
			if(i == 0) measureData1 = new ArrayList<>();
			if(i == 1) measureData2 = new ArrayList<>();
			if(i == 2) measureData3 = new ArrayList<>();
			if(i == 3) measureData4 = new ArrayList<>();
			
			measureIdList.add(measureLookupService.findById(measureLookupIdList.get(i)).getMeasureId());
		}
		
		List<String> uniqueYears = providerHypothesisService.getUniqueYearsForLineChart();	
			
		measureWiseExclusionRateService.setFreqValue(dataAnalysisId, subdataAnalysisId, 
				measureLookupIdList, allowableExclusionsList, reportingOptionsList, 
				measureData1, measureData2, measureData3, measureData4);		
		
		lineChartDataMap.put("uniqueYears", uniqueYears);
		if(measureLookupIdList.size() > 0) lineChartDataMap.put("measureData1", measureData1);
		if(measureLookupIdList.size() > 1) lineChartDataMap.put("measureData2", measureData2);
		if(measureLookupIdList.size() > 2) lineChartDataMap.put("measureData3", measureData3);
		if(measureLookupIdList.size() > 3) lineChartDataMap.put("measureData4", measureData4);
		
		
		lineChartDataMap.put("measureLookupIdList", measureLookupIdList);
		lineChartDataMap.put("measureIdList", measureIdList);
		lineChartDataMap.put("allowableExclusionsList", allowableExclusionsList);
		lineChartDataMap.put("reportingOptionsList", reportingOptionsList);
		
		if (measureData1 != null && measureData1.size()>0){
			for (Integer data : measureData1) {
				if (data != null) {
					dataAvailable = "YES";
					break;
				}
			}
		}
		System.out.println("Data for Line Chart Data(AVAILABLE)::"+dataAvailable);
		lineChartDataMap.put("dataAvailable", dataAvailable);
		
		return lineChartDataMap;
		
	}
	
	//Edit
	@RequestMapping(value = "/measureExclusionRate/dataAnalysisId/{dataAnalysisId}/subDataAnalysisId/{subdataAnalysisId}/reportingOptionId/{reportingOptionId}/measure/{params}", method = RequestMethod.GET)
	public Map getMeasureExclusionsHy4Hy6(@PathVariable int dataAnalysisId,
			@PathVariable int subdataAnalysisId, @PathVariable int reportingOptionId, @PathVariable List<Integer> params, HttpServletRequest request, 
			Principal currentUser, final Model model){	
		
		Map lineChartDataMap = new HashMap();
		String dataAvailable = "NO";
		List<Integer> measureLookupIdList = params;
		List<String> measureIdList = new ArrayList<>();
		//List<String> allowableExclusionsList = new ArrayList<>();
		//List<String> reportingOptionsList = new ArrayList<>();
		
		List<Double> measureData1 = null;
		List<Double> measureData2 = null;
		List<Double> measureData3 = null;
		List<Double> measureData4 = null;
		
		for(int i = 0; i < measureLookupIdList.size(); i++){
			if(i == 0) measureData1 = new ArrayList<>();
			if(i == 1) measureData2 = new ArrayList<>();
			if(i == 2) measureData3 = new ArrayList<>();
			if(i == 3) measureData4 = new ArrayList<>();
			
			measureIdList.add(measureLookupService.findById(measureLookupIdList.get(i)).getMeasureId());
		}
		
		List<String> uniqueYears = providerHypothesisService.getUniqueYearsForLineChart();
		measureWisePerformanceAndReportingRateService.setExclusionValue(dataAnalysisId, subdataAnalysisId, measureLookupIdList, 
				reportingOptionId, measureData1, measureData2, measureData3, measureData4);
		
		lineChartDataMap.put("uniqueYears", uniqueYears);
		if(measureLookupIdList.size() > 0) lineChartDataMap.put("measureData1", measureData1);
		if(measureLookupIdList.size() > 1) lineChartDataMap.put("measureData2", measureData2);
		if(measureLookupIdList.size() > 2) lineChartDataMap.put("measureData3", measureData3);
		if(measureLookupIdList.size() > 3) lineChartDataMap.put("measureData4", measureData4);
		
		
		lineChartDataMap.put("measureLookupIdList", measureLookupIdList);
		lineChartDataMap.put("measureIdList", measureIdList);		
		lineChartDataMap.put("selectedReportingOptionId", reportingOptionId);
		
		if (measureData1 != null && measureData1.size()>0){
			for (Double data : measureData1) {
				if (data != null) {
					dataAvailable = "YES";
					break;
				}
			}
		}
		System.out.println("test Data for Line Chart Data(AVAILABLE)::"+dataAvailable);
		lineChartDataMap.put("dataAvailable", dataAvailable);
		
		return lineChartDataMap;
		
	}
	
	//Edit
	@RequestMapping(value = "/measureFrequency/dataAnalysisId/{dataAnalysisId}/subDataAnalysisId/{subdataAnalysisId}/reportingOptionId/{reportingOptionId}/measure/{params}", method = RequestMethod.GET)
	public Map getFrequencyHy4Hy6(@PathVariable int dataAnalysisId,
			@PathVariable int subdataAnalysisId, @PathVariable int reportingOptionId, @PathVariable List<Integer> params, HttpServletRequest request, 
			Principal currentUser, final Model model){	
		
		Map lineChartDataMap = new HashMap();
		String dataAvailable = "NO";
		List<Integer> measureLookupIdList = params;
		List<String> measureIdList = new ArrayList<>();	
		
		List<Integer> measureData1 = null;
		List<Integer> measureData2 = null;
		List<Integer> measureData3 = null;
		List<Integer> measureData4 = null;
		
		for(int i = 0; i < measureLookupIdList.size(); i++){
			if(i == 0) measureData1 = new ArrayList<>();
			if(i == 1) measureData2 = new ArrayList<>();
			if(i == 2) measureData3 = new ArrayList<>();
			if(i == 3) measureData4 = new ArrayList<>();
			
			measureIdList.add(measureLookupService.findById(measureLookupIdList.get(i)).getMeasureId());
		}
		
		List<String> uniqueYears = providerHypothesisService.getUniqueYearsForLineChart();	
			
		measureWisePerformanceAndReportingRateService.setFreqValue(dataAnalysisId, subdataAnalysisId, 
				measureLookupIdList, reportingOptionId, measureData1, measureData2, measureData3, measureData4);		
		
		lineChartDataMap.put("uniqueYears", uniqueYears);
		if(measureLookupIdList.size() > 0) lineChartDataMap.put("measureData1", measureData1);
		if(measureLookupIdList.size() > 1) lineChartDataMap.put("measureData2", measureData2);
		if(measureLookupIdList.size() > 2) lineChartDataMap.put("measureData3", measureData3);
		if(measureLookupIdList.size() > 3) lineChartDataMap.put("measureData4", measureData4);
		
		
		lineChartDataMap.put("measureLookupIdList", measureLookupIdList);
		lineChartDataMap.put("measureIdList", measureIdList);		
		lineChartDataMap.put("selectedReportingOptionId", reportingOptionId);
		
		if (measureData1 != null && measureData1.size()>0){
			for (Integer data : measureData1) {
				if (data != null) {
					dataAvailable = "YES";
					break;
				}
			}
		}
		System.out.println("Data for Line Chart Data(AVAILABLE)::"+dataAvailable);
		lineChartDataMap.put("dataAvailable", dataAvailable);
		
		return lineChartDataMap;
		
	}
	
}
