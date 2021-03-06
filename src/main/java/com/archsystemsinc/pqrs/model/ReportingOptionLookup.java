package com.archsystemsinc.pqrs.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the reporting_option_lookup database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/19/2017
 * 
 */
@Entity
@Table(name="reporting_option_lookup")
@NamedQuery(name="ReportingOptionLookup.findAll", query="SELECT r FROM ReportingOptionLookup r")
public class ReportingOptionLookup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="reporting_option_name")
	private String reportingOptionName;

	//bi-directional many-to-one association to Provider_Hypothesis
	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "reportingOptionLookup", cascade = CascadeType.ALL)
	private List<ProviderHypothesis> providerHypothesis;
	
	//bi-directional many-to-one association to ExclusionTrend
	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, mappedBy="reportingOptionLookup" , cascade = CascadeType.ALL)
	private List<ExclusionTrend> exclusionTrends;

	public ReportingOptionLookup() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReportingOptionName() {
		return this.reportingOptionName;
	}

	public void setReportingOptionName(String reportingOptionName) {
		this.reportingOptionName = reportingOptionName;
	}
	@JsonIgnore
	public List<ProviderHypothesis> getProviderHypothesis() {
		return this.providerHypothesis;
	}

	public void setProviderHypothesis(List<ProviderHypothesis> providerHypothesis) {
		this.providerHypothesis = providerHypothesis;
	}

	public ProviderHypothesis addProviderHypothesi(ProviderHypothesis providerHypothesi) {
		getProviderHypothesis().add(providerHypothesi);
		providerHypothesi.setReportingOptionLookup(this);

		return providerHypothesi;
	}

	public ProviderHypothesis removeProviderHypothesi(ProviderHypothesis providerHypothesi) {
		getProviderHypothesis().remove(providerHypothesi);
		providerHypothesi.setReportingOptionLookup(null);

		return providerHypothesi;
	}
	
	public List<ExclusionTrend> getExclusionTrends() {
		return this.exclusionTrends;
	}

	public void setExclusionTrends(List<ExclusionTrend> exclusionTrends) {
		this.exclusionTrends = exclusionTrends;
	}

	public ExclusionTrend addExclusionTrend(ExclusionTrend exclusionTrend) {
		getExclusionTrends().add(exclusionTrend);
		exclusionTrend.setReportingOptionLookup(this);

		return exclusionTrend;
	}

	public ExclusionTrend removeExclusionTrend(ExclusionTrend exclusionTrend) {
		getExclusionTrends().remove(exclusionTrend);
		exclusionTrend.setReportingOptionLookup(null);

		return exclusionTrend;
	}

}