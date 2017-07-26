/**
 * 
 */
package com.archsystemsinc.pqrs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.archsystemsinc.pqrs.model.DataAnalysis;
import com.archsystemsinc.pqrs.model.ExclusionTrend;
import com.archsystemsinc.pqrs.model.SubDataAnalysis;

/**
 * @author Murugaraj Kandaswamy
 * @since 7/20/2017
 *
 */
public interface ExclusionTrendRepository extends JpaRepository<ExclusionTrend, Long> {
	
	/**
	 * 
	 * @param dataAnalysis
	 * @param subDataAnalysis
	 * @param parameterLookup
	 * @return
	 */
	List<ExclusionTrend> findByDataAnalysisAndSubDataAnalysis(DataAnalysis dataAnalysis, SubDataAnalysis subDataAnalysis);


}
