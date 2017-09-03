/**
 * 
 */
package com.archsystemsinc.pqrs.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.archsystemsinc.pqrs.model.ExclusionTrend;

/**
 * @author PrakashTotta
 *
 */
@Repository
public class TestRepo {
	@PersistenceContext
    private EntityManager em;
	
	/**
	 * 
	 * @param dId
	 * @param sId
	 * @param rId
	 * @return
	 */
	public List<ExclusionTrend> getExclusionTrendList(final int dId, final int sId, final int rId){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ExclusionTrend> criteriaQuery = cb.createQuery(ExclusionTrend.class);
		Root<ExclusionTrend> studentRoot = criteriaQuery.from(ExclusionTrend.class);
		criteriaQuery.select(studentRoot);
		Predicate p1 = cb.equal(studentRoot.get("dataAnalysis").get("id"), dId);
		Predicate p2 = cb.equal(studentRoot.get("subDataAnalysis").get("id"), sId);
		Predicate p3 = cb.equal(studentRoot.get("reportingOptionLookup").get("id"), rId);
		Predicate criteria =cb.and(p1);
		criteria =cb.and(criteria,p2);
		criteria =cb.and(criteria,p3);
		
		criteriaQuery.where(criteria);
		List<ExclusionTrend> result = em.createQuery(criteriaQuery).getResultList();
		return result;
	}
	

}
