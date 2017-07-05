/**
 * 
 */
package com.archsystemsinc.pqrs.service;

import java.util.List;

import com.archsystemsinc.pqrs.model.DataAnalysis;
import com.archsystemsinc.pqrs.model.SubDataAnalysis;

/**
 * @author MurugarajKandaswam
 *
 */
public interface SubDataAnalysisService {
	
	List<SubDataAnalysis> findAll();
	
	List<SubDataAnalysis> findByDataAnalysis(DataAnalysis dataAnalysis);
	SubDataAnalysis findById(final int id); 	
}
