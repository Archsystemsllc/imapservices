package com.archsystemsinc.pqrs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.archsystemsinc.pqrs.model.DataAnalysis;
import com.archsystemsinc.pqrs.model.MeasureLookup;
import com.archsystemsinc.pqrs.model.MeasureWiseExclusionRate;
import com.archsystemsinc.pqrs.model.SubDataAnalysis;

/**
 * @author Grmahun Redda
 *
 */
public interface MeasureWiseExclusionRateRepository extends JpaRepository<MeasureWiseExclusionRate, Long>{
	MeasureWiseExclusionRate findById(final int id);
	List<MeasureWiseExclusionRate> findAll();
	List<MeasureWiseExclusionRate> findByDataAnalysisAndSubDataAnalysis(DataAnalysis dataAnalysis, SubDataAnalysis SubDataAnalysis);
	List<MeasureWiseExclusionRate> findByMeasureLookupAndDataAnalysisAndSubDataAnalysis(MeasureLookup measureLookup, DataAnalysis dataAnalysis, SubDataAnalysis SubDataAnalysis);
	
}
