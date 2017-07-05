package com.archsystemsinc.pqrs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.pqrs.model.DataAnalysis;
import com.archsystemsinc.pqrs.repository.DataAnalaysisRepository;
import com.archsystemsinc.pqrs.service.DataAnalysisService;

@Service
public class DataAnalysisServiceImpl implements DataAnalysisService {
	
	@Autowired
	private DataAnalaysisRepository dataAnalaysisRepository;

	@Override
	public List<DataAnalysis> findAll() {
		return dataAnalaysisRepository.findAll();
	}

	@Override
	public DataAnalysis findById(int id) {
		// TODO Auto-generated method stub
		//return dataAnalaysisRepository.findOne(Long.valueOf(id));
		return dataAnalaysisRepository.findById(id);
	}

//	@Override
//	public List<DataAnalysis> findAllByHypoId(Long id) {
//		// TODO Auto-generated method stub
//		return dataAnalaysisRepository.findAll(example);
//	}

}
