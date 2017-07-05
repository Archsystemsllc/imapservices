package com.archsystemsinc.pqrs.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.pqrs.constant.ReportingOptionEnum;
import com.archsystemsinc.pqrs.constant.YearNameEnum;
import com.archsystemsinc.pqrs.model.DataAnalysis;
import com.archsystemsinc.pqrs.model.ParameterLookup;
import com.archsystemsinc.pqrs.model.ProviderHypothesis;
import com.archsystemsinc.pqrs.model.ReportingOptionLookup;
import com.archsystemsinc.pqrs.model.SubDataAnalysis;
import com.archsystemsinc.pqrs.model.YearLookup;
import com.archsystemsinc.pqrs.repository.DataAnalaysisRepository;
import com.archsystemsinc.pqrs.repository.ParameterLookUpRepository;
import com.archsystemsinc.pqrs.repository.ProviderHypothesisRepository;
import com.archsystemsinc.pqrs.repository.ReportingOptionLookupRepository;
import com.archsystemsinc.pqrs.repository.SubDataAnalysisRepository;
import com.archsystemsinc.pqrs.repository.YearLookUpRepository;
import com.archsystemsinc.pqrs.restcontroller.TestService;
import com.archsystemsinc.pqrs.service.ProviderHypothesisService;

/**
 * This is the implementation class of Service interface for provider_hypothesis database table.
 * 
 * @author Murugaraj Kandaswamy, Grmahun Redda
 * @since 6/21/2017
 * 
 */
@Service
public class ProviderHypothesisServiceImpl implements ProviderHypothesisService {

	private static final Logger log = Logger.getLogger(ProviderHypothesisServiceImpl.class);
	
	@Autowired
	private ProviderHypothesisRepository providerHypothesisRepository;
	
	@Autowired
	private YearLookUpRepository yearLookUpRepository;
	
	@Autowired
	private ReportingOptionLookupRepository reportingOptionLookupRepository;
	
	@Autowired
	private ParameterLookUpRepository parameterLookUpRepository;
	
	@Autowired
	private DataAnalaysisRepository dataAnalaysisRepository;
	
	@Autowired
	private SubDataAnalysisRepository subDataAnalysisRepository;
	
	@Override
	public List<ProviderHypothesis> findByYearLookupAndReportingOptionLookup(String year, String reportingOption) {
		return providerHypothesisRepository.findByYearLookupAndReportingOptionLookup(yearLookUpRepository.findByYearName(year), reportingOptionLookupRepository.findByReportingOptionName(reportingOption));
	}

	
	@Override
	public List<ProviderHypothesis> findByParameterLookup(String parameterName) {
		return providerHypothesisRepository.findByParameterLookup(parameterLookUpRepository.findByParameterName(parameterName));
	}

	
	@Override
	public ProviderHypothesis create(ProviderHypothesis providerHypothesis) {		
		return providerHypothesisRepository.saveAndFlush(providerHypothesis);
	}



	@Override
	public void update(Long id) {		
		providerHypothesisRepository.saveAndFlush(findById(id));
	}



	@Override
	public ProviderHypothesis findById(Long id) {
		// TODO Auto-generated method stub
		return providerHypothesisRepository.getOne(id);
	}



	@Override
	public void deleteById(Long id) {
		providerHypothesisRepository.delete(id);
		
	}



	@Override
	public List<ProviderHypothesis> findAll() {
		// TODO Auto-generated method stub
		return providerHypothesisRepository.findAll();
	}



	@Override
	public List<ProviderHypothesis> findAllByUserId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<String> getUniqueYearsForLineChart() {
		List<String> uniqueYears = new ArrayList<String>();
		
		// TODO:: If Required Un Commented the below reading from DB
/*		List<YearLookup> YearLookUps = yearLookUpRepository.findAll();
		for (YearLookup yearLookup : YearLookUps) {
			if (yearLookup.getYearName().equalsIgnoreCase(YearNameEnum.ALL.getYearName())) continue;
			uniqueYears.add(yearLookup.getYearName());
		}*/
		
		uniqueYears.add(YearNameEnum.BASE_YEAR.getYearName()+"");
		uniqueYears.add(YearNameEnum.OPTIONAL_YEAR_1.getYearName()+"");
		uniqueYears.add(YearNameEnum.OPTIONAL_YEAR_2.getYearName()+"");
		uniqueYears.add(YearNameEnum.OPTIONAL_YEAR_3.getYearName()+"");
		
		return uniqueYears;
		
	}
	
	@Override
	public boolean setRPPercentValue(List<ProviderHypothesis> providerHypothesisList, List<Double> claimsPercents, List<Double> ehrPercents,
			List<Double> registryPercents, List<Double> gprowiPercents, List<Double> qcdrPercents) {
		
		Map<String, Double> claimsPercentMap = new HashMap<String, Double>();
		Map<String, Double> ehrPercentMap = new HashMap<String, Double>();
		Map<String, Double> registryPercentMap = new HashMap<String, Double>();
		Map<String, Double> gprowiPercentMap = new HashMap<String, Double>();
		Map<String, Double> qcdrPercentMap = new HashMap<String, Double>();
		
		for (ProviderHypothesis providerHypothesis : providerHypothesisList){
			
			if (providerHypothesis.getYearLookup().getYearName().equalsIgnoreCase(YearNameEnum.ALL.getYearName())) continue;
			
			//uniqueYears.add("\""+providerHypothesis.getYearLookup().getYearName()+"\"");
			
			if (providerHypothesis.getReportingOptionLookup().getReportingOptionName().equalsIgnoreCase(ReportingOptionEnum.CLAIMS.getReportingOptionName())) {
				claimsPercentMap.put(providerHypothesis.getYearLookup().getYearName(), providerHypothesis.getRpPercent()==0.0 ? null : providerHypothesis.getRpPercent());
			} else if (providerHypothesis.getReportingOptionLookup().getReportingOptionName().equalsIgnoreCase(ReportingOptionEnum.EHR.getReportingOptionName())) {
				ehrPercentMap.put(providerHypothesis.getYearLookup().getYearName(), providerHypothesis.getRpPercent()==0.0 ? null : providerHypothesis.getRpPercent());
			}else if (providerHypothesis.getReportingOptionLookup().getReportingOptionName().equalsIgnoreCase(ReportingOptionEnum.REGISTRY.getReportingOptionName())) {
				registryPercentMap.put(providerHypothesis.getYearLookup().getYearName(), providerHypothesis.getRpPercent()==0.0 ? null : providerHypothesis.getRpPercent());
			}else if (providerHypothesis.getReportingOptionLookup().getReportingOptionName().equalsIgnoreCase(ReportingOptionEnum.GPROWI.getReportingOptionName())) {
				gprowiPercentMap.put(providerHypothesis.getYearLookup().getYearName(), providerHypothesis.getRpPercent()==0.0 ? null : providerHypothesis.getRpPercent());
			} else if (providerHypothesis.getReportingOptionLookup().getReportingOptionName().equalsIgnoreCase(ReportingOptionEnum.QCDR.getReportingOptionName())) {
				qcdrPercentMap.put(providerHypothesis.getYearLookup().getYearName(), providerHypothesis.getRpPercent()==0.0 ? null : providerHypothesis.getRpPercent());
			} else {
				// TODO
			}
		
		}
		
		sortRPPercentByYear(claimsPercentMap, claimsPercents);
		sortRPPercentByYear(ehrPercentMap, ehrPercents);
		sortRPPercentByYear(registryPercentMap, registryPercents);
		sortRPPercentByYear(gprowiPercentMap, gprowiPercents);
		sortRPPercentByYear(qcdrPercentMap, qcdrPercents);
		
		return true;
	}

	private void sortRPPercentByYear(Map<String, Double> rpPercentMap, List<Double> rpPercents) {
		rpPercents.add(rpPercentMap.get(YearNameEnum.BASE_YEAR.getYearName()));
		rpPercents.add(rpPercentMap.get(YearNameEnum.OPTIONAL_YEAR_1.getYearName()));
		rpPercents.add(rpPercentMap.get(YearNameEnum.OPTIONAL_YEAR_2.getYearName()));
		rpPercents.add(rpPercentMap.get(YearNameEnum.OPTIONAL_YEAR_3.getYearName()));
	}

	@Override
	public List<ProviderHypothesis> findByDataAnalysisAndSubDataAnalysisAndYearLookupAndReportingOptionLookup(
			String dataAnalysisName, String subDataAnalysisName, String year, String reportingOption) {
		log.debug("--> findByDataAnalysisAndSubDataAnalysisAndYearLookupAndReportingOptionLookup");
		DataAnalysis dataAnalysis = dataAnalaysisRepository.findByDataAnalysisName(dataAnalysisName);
		log.debug("dataAnalysis:"+dataAnalysis);
		SubDataAnalysis subDataAnalysis = subDataAnalysisRepository.findByDataAnalysisAndSubDataAnalysisName(dataAnalysis, subDataAnalysisName);
		log.debug("subDataAnalysis:"+subDataAnalysis);
		YearLookup yearLookup = yearLookUpRepository.findByYearName(year);
		log.debug("yearLookup:"+yearLookup);
		ReportingOptionLookup reportingOptionLookup = reportingOptionLookupRepository.findByReportingOptionName(reportingOption);
		log.debug("reportingOptionLookup:"+reportingOptionLookup);
		log.debug("<-- findByDataAnalysisAndSubDataAnalysisAndYearLookupAndReportingOptionLookup");
		return providerHypothesisRepository.findByDataAnalysisAndSubDataAnalysisAndYearLookupAndReportingOptionLookup(dataAnalysis, subDataAnalysis, yearLookup, reportingOptionLookup);
	}


	@Override
	public List<ProviderHypothesis> findByDataAnalysisAndSubDataAnalysisAndParameterLookup(String dataAnalysisName,
			String subDataAnalysisName, String parameterName) {
		
		DataAnalysis dataAnalysis = dataAnalaysisRepository.findByDataAnalysisName(dataAnalysisName);
		SubDataAnalysis subDataAnalysis = subDataAnalysisRepository.findByDataAnalysisAndSubDataAnalysisName(dataAnalysis, subDataAnalysisName);
		ParameterLookup parameterLookup = parameterLookUpRepository.findByParameterName(parameterName);
		
		return providerHypothesisRepository.findByDataAnalysisAndSubDataAnalysisAndParameterLookup(dataAnalysis, subDataAnalysis, parameterLookup);

	}

}


