/**
 * 
 */
package com.archsystemsinc.pqrs.restcontroller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archsystemsinc.pqrs.model.ExclusionTrend;
import com.archsystemsinc.pqrs.service.ExclusionTrendService;

/**
 * @author MurugarajKandaswam
 *
 */
@RestController
@RequestMapping("/api")
public class ExclustionTrendRestController {
	
	@Autowired
	private ExclusionTrendService exclusionTrendService;

	
	/**
	 * 
	 * This method returns the JSON Object that has the details for Line Chart Display for Hypothesis 3.
	 * 
	 * @param dataAnalysisName
	 * @param subdataAnalysisName
	 * @param parameterName
	 * @param request
	 * @param currentUser
	 * @param model
	 * @return
	 */
	@RequestMapping("/h3/lineChart/dataAnalysisId/{dataAnalysisId}/subDataAnalysisId/{subDataAnalysisId}")
    public Map lineChartDisplay(@PathVariable("dataAnalysisId") int dataAnalysisId, @PathVariable("subDataAnalysisId") int subDataAnalysisId, HttpServletRequest request, Principal currentUser, Model model) {

		Map lineChartDataMap = new HashMap();
		String dataAvailable = "NO";
		
		final List<ExclusionTrend> exclusionTrendList = exclusionTrendService.findByDataAnalysisAndSubDataAnalysis(dataAnalysisId, subDataAnalysisId);
		
		List<String> uniqueYears = exclusionTrendService.getUniqueYearsForLineChart();
		List<Double> claimsPercents = new ArrayList<Double>();
		List<Double> ehrPercents = new ArrayList<Double>();
		List<Double> registryPercents = new ArrayList<Double>();
		List<Double> gprowiPercents = new ArrayList<Double>();
		List<Double> qcdrPercents = new ArrayList<Double>();
		
		exclusionTrendService.setMeanExclusionRatePercentValue(exclusionTrendList, claimsPercents, ehrPercents, registryPercents, gprowiPercents, qcdrPercents);
		
		lineChartDataMap.put("uniqueYears", uniqueYears);
		lineChartDataMap.put("claimsPercents", claimsPercents);
		lineChartDataMap.put("ehrPercents", ehrPercents);
		lineChartDataMap.put("registryPercents", registryPercents);
		lineChartDataMap.put("gprowiPercents", gprowiPercents);
		lineChartDataMap.put("qcdrPercents", qcdrPercents);
		
		if (claimsPercents != null && claimsPercents.size()>0){
			for (Double claimPercent : claimsPercents) {
				if (claimPercent != null) {
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
