package com.ashokit.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="ELIG_DTLS")
public class EligEntity {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer edgTraceId;

	private String planStatus;
	
	private Double benefitAmt;

	
	public Integer getEdgTraceId() {
		return edgTraceId;
	}

	public void setEdgTraceId(Integer edgTraceId) {
		this.edgTraceId = edgTraceId;
	}
	
	public Double getBenefitAmt() {
		return benefitAmt;
	}

	public void setBenefitAmt(Double benefitAmt) {
		this.benefitAmt = benefitAmt;
	}

	public String getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}
	
	

}
