package com.ashokit.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ashokit.Entity.PlanEntity;

public interface PlanRepo extends JpaRepository <PlanEntity, Integer>{
	
	
	@Query("update PlanEntity set accStatus=:status where plandId:planId")
	public Integer updatePlanStatus(Integer planId,String status);

}
