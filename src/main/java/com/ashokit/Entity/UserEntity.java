package com.ashokit.Entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;


 
@Entity
@Data
@Table(name="IES_USER")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private long userId;
	
	private String fullName;
	
	private String email;
	
	private String pwd;
	
	private Long mobileNo;
	
	private String gender;
	
	private LocalDate dob;
	
	private long ssn;
	
	private String activeSw;
	
	private String accStatus;
	
	private long roleId;
	 
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<PlanEntity> plans;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public long getSsn() {
		return ssn;
	}

	public void setSsn(long ssn) {
		this.ssn = ssn;
	}

	public String getActiveSw() {
		return activeSw;
	}

	public void setActiveSw(String activeSw) {
		this.activeSw = activeSw;
	}

	public String getAccStatus() {
		return accStatus;
	}

	public void setAccStatus(String accStatus) {
		this.accStatus = accStatus;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public List<PlanEntity> getPlans() {
		return plans;
	}

	public void setPlans(List<PlanEntity> plans) {
		this.plans = plans;
	}

	 	
	

}
