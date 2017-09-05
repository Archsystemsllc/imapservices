package com.archsystemsinc.pqrs.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import com.archsystemsinc.pqrs.model.MeasureWisePerformanceAndReportingRate;

/**
 * @author Grmahun Redda
 *
 */
@Repository
public class Hypothesis4and6Repository {
	@PersistenceContext
    private EntityManager em;
	
	/**
	 * 
	 * @param mId
	 * @param dId
	 * @param sId
	 * @param rId
	 * @return
	 * 
	 * */
	
	public List<MeasureWisePerformanceAndReportingRate> getMeasureWisePerformanceAndReportingRateList(final int mId, final int dId, final int sId, final int rId){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MeasureWisePerformanceAndReportingRate> criteriaQuery = cb.createQuery(MeasureWisePerformanceAndReportingRate.class);
		Root<MeasureWisePerformanceAndReportingRate> studentRoot = criteriaQuery.from(MeasureWisePerformanceAndReportingRate.class);
		criteriaQuery.select(studentRoot);
		Predicate p1 = cb.equal(studentRoot.get("measureLookup").get("id"), mId);
		Predicate p2 = cb.equal(studentRoot.get("dataAnalysis").get("id"), dId);
		Predicate p3 = cb.equal(studentRoot.get("subDataAnalysis").get("id"), sId);
		Predicate p4 = cb.equal(studentRoot.get("reportingOptionLookup").get("id"), rId);
		Predicate criteria =cb.and(p1);
		criteria =cb.and(criteria,p2);
		criteria =cb.and(criteria,p3);
		criteria =cb.and(criteria,p4);
		
		criteriaQuery.where(criteria);
		List<MeasureWisePerformanceAndReportingRate> result = em.createQuery(criteriaQuery).getResultList();
		return result;
	}

}
