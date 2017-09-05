package com.archsystemsinc.pqrs.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import com.archsystemsinc.pqrs.model.MeasureWiseExclusionRate;

/**
 * @author Grmahun Redda
 *
 */
@Repository
public class Hypothesis5Repository {
	@PersistenceContext
    private EntityManager em;
	
	/**
	 * 
	 * @param mId
	 * @param dId
	 * @param sId
	 * @return
	 */
	public List<MeasureWiseExclusionRate> getMeasureWiseExclusionRateList(final int mId, final int dId, final int sId){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MeasureWiseExclusionRate> criteriaQuery = cb.createQuery(MeasureWiseExclusionRate.class);
		Root<MeasureWiseExclusionRate> studentRoot = criteriaQuery.from(MeasureWiseExclusionRate.class);
		criteriaQuery.select(studentRoot);
		Predicate p1 = cb.equal(studentRoot.get("measureLookup").get("id"), mId);
		Predicate p2 = cb.equal(studentRoot.get("dataAnalysis").get("id"), dId);
		Predicate p3 = cb.equal(studentRoot.get("subDataAnalysis").get("id"), sId);
		Predicate criteria =cb.and(p1);
		criteria =cb.and(criteria,p2);
		criteria =cb.and(criteria,p3);
		
		criteriaQuery.where(criteria);
		List<MeasureWiseExclusionRate> result = em.createQuery(criteriaQuery).getResultList();
		return result;
	}

}
