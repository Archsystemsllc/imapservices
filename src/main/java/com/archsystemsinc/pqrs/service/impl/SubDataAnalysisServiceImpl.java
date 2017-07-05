/**
 * 
 */
package com.archsystemsinc.pqrs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.pqrs.model.DataAnalysis;
import com.archsystemsinc.pqrs.model.SubDataAnalysis;
import com.archsystemsinc.pqrs.repository.SubDataAnalysisRepository;
import com.archsystemsinc.pqrs.service.SubDataAnalysisService;

/**
 * @author MurugarajKandaswam
 *
 */
@Service
public class SubDataAnalysisServiceImpl implements SubDataAnalysisService {

	@Autowired
	private SubDataAnalysisRepository subDataAnalysisRepository;
	
	/** (non-Javadoc)
	 * @see com.archsystemsinc.pqrs.service.SubDataAnalysisService#findAll()
	 */
	@Override
	public List<SubDataAnalysis> findAll() {
		return subDataAnalysisRepository.findAll();
	}

	@Override
	public List<SubDataAnalysis> findByDataAnalysis(DataAnalysis dataAnalysis) {
		return subDataAnalysisRepository.findByDataAnalysis(dataAnalysis);
	}

	@Override
	public SubDataAnalysis findById(int id) {
		// TODO Auto-generated method stub
		return subDataAnalysisRepository.findById(id);
	}

	

}
