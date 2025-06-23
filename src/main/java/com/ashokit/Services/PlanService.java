package com.ashokit.Services;

import java.util.List;

import com.ashokit.Binding.PlanForm;

public interface PlanService {
	
	//create plan 
	public boolean createPlan(PlanForm planForm);
	
	//view plan
	public List<PlanForm> fetchPlan();
	
	//update plan
	public PlanForm getPlanById(Integer planId);
	
	//activate or deactivate plan
	public String changePlanStatus(Integer PlanId,String status);
}
