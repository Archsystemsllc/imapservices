/**
 * 
 */
package com.archsystemsinc.pqrs.service;

import java.util.List;

import com.archsystemsinc.pqrs.model.DataAnalysis;
import com.archsystemsinc.pqrs.model.SubDataAnalysis;
import com.archsystemsinc.pqrs.model.TemplateFile;

/**
 * @author MurugarajKandaswam
 *
 */
public interface DataAnalysisService {

	List<DataAnalysis> findAll();
	DataAnalysis findById(final int id); 
}
