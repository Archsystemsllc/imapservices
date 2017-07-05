package com.archsystemsinc.pqrs.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archsystemsinc.pqrs.model.ProviderHypothesis;
import com.archsystemsinc.pqrs.service.ProviderHypothesisService;

@RestController
@RequestMapping("/api")
public class ProviderHypothesisRestController {
	@Autowired
	private ProviderHypothesisService providerHypothesisService;
	
	@RequestMapping("/barChart/dataanalysis/{dataanalysis}/subdataanalysis/{subdataanalysis}/year/{year}/reportingOption/{reportingOption}")
	public Map barChartDisplay(@PathVariable("dataanalysis") String dataAnalysisName, @PathVariable("subdataanalysis") String subdataAnalysisName, @PathVariable("year") String year, @PathVariable("reportingOption") String reportingOption) {
		String dataAvailable = "NO";
		Map barChartDataMap = new HashMap();
		final List<ProviderHypothesis> providerHypothesisList = providerHypothesisService.findByDataAnalysisAndSubDataAnalysisAndYearLookupAndReportingOptionLookup(dataAnalysisName, subdataAnalysisName, year, reportingOption);
		// Preparing Parameter String Array
		List<String> parameters = new ArrayList<String>();
		List<Double> yesPercents = new ArrayList<Double>();
		List<Double> noPercents = new ArrayList<Double>();
		List<String> yesCountValues = new ArrayList<String>();
		List<String> noCountValues = new ArrayList<String>();
		
		for (ProviderHypothesis providerHypothesis : providerHypothesisList){
			parameters.add(providerHypothesis.getParameterLookup().getParameterName());
			yesPercents.add(providerHypothesis.getYesPercent());
			noPercents.add(providerHypothesis.getNoPercent());
			yesCountValues.add(providerHypothesis.getYesCount()+"");
			noCountValues.add(providerHypothesis.getNoCount()+"");
			dataAvailable = "YES";
		}
		
		// Setting barChartData in the Map to be returned back to View....
		barChartDataMap.put("parameters", parameters);
		barChartDataMap.put("yesPercents", yesPercents);
		barChartDataMap.put("noPercents", noPercents);
		barChartDataMap.put("dataAvailable", dataAvailable);
		barChartDataMap.put("yesCountValues",yesCountValues);
		barChartDataMap.put("noCountValues",noCountValues);
        return barChartDataMap;
    }
	
	
	@RequestMapping("/lineChart/dataanalysis/{dataanalysis}/subdataanalysis/{subdataanalysis}/parameter/{parameter}")
	public Map lineChartDisplay(@PathVariable("dataanalysis") String dataAnalysisName, @PathVariable("subdataanalysis") String subdataAnalysisName, @PathVariable("parameter") String parameterName) {
		Map lineChartDataMap = new HashMap();
		String dataAvailable = "NO";
		final List<ProviderHypothesis> providerHypothesisList = providerHypothesisService.findByDataAnalysisAndSubDataAnalysisAndParameterLookup(dataAnalysisName, subdataAnalysisName, parameterName);
		if (providerHypothesisList != null && providerHypothesisList.size()>0){
			dataAvailable = "YES";
		}
		List<String> uniqueYears = providerHypothesisService.getUniqueYearsForLineChart();
		List<Double> claimsPercents = new ArrayList<Double>();
		List<Double> ehrPercents = new ArrayList<Double>();
		List<Double> registryPercents = new ArrayList<Double>();
		List<Double> gprowiPercents = new ArrayList<Double>();
		List<Double> qcdrPercents = new ArrayList<Double>();
		
		providerHypothesisService.setRPPercentValue(providerHypothesisList, claimsPercents, ehrPercents, registryPercents, gprowiPercents, qcdrPercents);
		
		lineChartDataMap.put("uniqueYears", uniqueYears);
		lineChartDataMap.put("claimsPercents", claimsPercents);
		lineChartDataMap.put("ehrPercents", ehrPercents);
		lineChartDataMap.put("registryPercents", registryPercents);
		lineChartDataMap.put("gprowiPercents", gprowiPercents);
		lineChartDataMap.put("qcdrPercents", qcdrPercents);
		lineChartDataMap.put("dataAvailable", dataAvailable);
		
		return lineChartDataMap;
    }

	
	@RequestMapping("/barChart/year/{year}/reportingOption/{reportingOption}")
    public Map zipcodes(@PathVariable("year") String year, @PathVariable("reportingOption") String reportingOption) {
		Map<String,List<?>> barChartMapData = new LinkedHashMap<>();		
		final List<ProviderHypothesis> providerHypothesisList = providerHypothesisService.findByYearLookupAndReportingOptionLookup(year, reportingOption);		
		List<String> parameters = new ArrayList<>();
		List<Double> yesPercents = new ArrayList<>();
		List<Double> noPercents = new ArrayList<>();
		for (ProviderHypothesis providerHypothesis : providerHypothesisList){
			parameters.add("\""+providerHypothesis.getParameterLookup().getParameterName()+"\"");
			yesPercents.add(providerHypothesis.getYesPercent());
			noPercents.add(providerHypothesis.getNoPercent());
		}
		barChartMapData.put("parameters", parameters);
		barChartMapData.put("yesPercents", yesPercents);
		barChartMapData.put("noPercents", noPercents);
        return barChartMapData;
    }
	
	@RequestMapping("/lineChart/parameter/{parameter}")
    public Map lineChart(@PathVariable("parameter") String parameterName) {
		Map<String,List<?>> lineChartMapData = new LinkedHashMap<>();
		final List<ProviderHypothesis> providerHypothesisList = providerHypothesisService.findByParameterLookup(parameterName);
		List<String> uniqueYears = providerHypothesisService.getUniqueYearsForLineChart();
		List<Double> claimsPercents = new ArrayList<Double>();
		List<Double> ehrPercents = new ArrayList<Double>();
		List<Double> registryPercents = new ArrayList<Double>();
		List<Double> gprowiPercents = new ArrayList<Double>();
		List<Double> qcdrPercents = new ArrayList<Double>();
		providerHypothesisService.setRPPercentValue(providerHypothesisList, claimsPercents, ehrPercents, registryPercents, gprowiPercents, qcdrPercents);
		lineChartMapData.put("uniqueYears", uniqueYears);
		lineChartMapData.put("claimsPercents", claimsPercents);
		lineChartMapData.put("ehrPercents", ehrPercents);
		lineChartMapData.put("registryPercents", registryPercents);
		lineChartMapData.put("gprowiPercents", gprowiPercents);
		lineChartMapData.put("qcdrPercents", qcdrPercents);
	    return lineChartMapData;
    }

}
