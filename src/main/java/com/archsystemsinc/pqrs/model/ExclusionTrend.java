package com.archsystemsinc.pqrs.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;


/**
 * The persistent class for the exclusion_trends database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 7/18/2017
 */
@Entity
@Table(name="exclusion_trends")
@NamedQuery(name="ExclusionTrend.findAll", query="SELECT e FROM ExclusionTrend e")
public class ExclusionTrend implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="mean_exclusion_rate_percent")
	private Double meanExclusionRatePercent;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="updated_date")
	private Date updatedDate;

	//bi-directional many-to-one association to DataAnalysi
	@ManyToOne
	@JoinColumn(name="data_analysis_id")
	private DataAnalysis dataAnalysis;

	//bi-directional many-to-one association to ReportingOptionLookup
	@ManyToOne
	@JoinColumn(name="reporting_option_id")
	private ReportingOptionLookup reportingOptionLookup;

	//bi-directional many-to-one association to SubDataAnalysi
	@ManyToOne
	@JoinColumn(name="sub_data_analysis_id")
	private SubDataAnalysis subDataAnalysis;

	//bi-directional many-to-one association to YearLookup
	@ManyToOne
	@JoinColumn(name="year_id")
	private YearLookup yearLookup;

	public ExclusionTrend() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Double getMeanExclusionRatePercent() {
		return this.meanExclusionRatePercent;
	}

	public void setMeanExclusionRatePercent(Double meanExclusionRatePercent) {
		this.meanExclusionRatePercent = meanExclusionRatePercent;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@JsonIgnore
	public DataAnalysis getDataAnalysis() {
		return this.dataAnalysis;
	}

	public void setDataAnalysis(DataAnalysis dataAnalysis) {
		this.dataAnalysis = dataAnalysis;
	}

	@JsonIgnore
	public ReportingOptionLookup getReportingOptionLookup() {
		return this.reportingOptionLookup;
	}

	public void setReportingOptionLookup(ReportingOptionLookup reportingOptionLookup) {
		this.reportingOptionLookup = reportingOptionLookup;
	}

	@JsonIgnore
	public SubDataAnalysis getSubDataAnalysis() {
		return this.subDataAnalysis;
	}

	public void setSubDataAnalysis(SubDataAnalysis subDataAnalysis) {
		this.subDataAnalysis = subDataAnalysis;
	}

	@JsonIgnore
	public YearLookup getYearLookup() {
		return this.yearLookup;
	}

	public void setYearLookup(YearLookup yearLookup) {
		this.yearLookup = yearLookup;
	}

}