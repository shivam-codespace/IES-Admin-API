package com.ashokit.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.Binding.PlanForm;
import com.ashokit.Binding.UserAccountForm;
import com.ashokit.Entity.PlanEntity;
import com.ashokit.Entity.UserEntity;
import com.ashokit.Repository.PlanRepo;


@Service
public class PlanServiceImpl implements PlanService{
	
	@Autowired
	private PlanRepo planRepo;

	
	
	
	//creating plans 
	@Override
	public boolean createPlan(PlanForm planForm) {
		
		PlanEntity planEntity = new PlanEntity();
		
		BeanUtils.copyProperties(planForm, planEntity);
		
		return pla;
	}
	
	
	
	//for showing plans
	@Override
	public List<PlanForm> fetchPlan() {
		List<PlanEntity> planEntities = planRepo.findAll();
		
		List<PlanForm> plan = new ArrayList<PlanForm>();
		
		forEach(PlanEntity planEntity : planEntities){
			UserAccountForm user = new UserAccountForm();
			BeanUtils.copyProperties(planEntity,user);
			plan.add(user);
		}
		
		return plan;
	}

	
	
	//for updating plan
	@Override
	public PlanForm getPlanById(Integer planId) {
		
		Optional<PlanEntity> optional=planRepo.findById(planId);
		if(optional.isPresent()) {
			PlanEntity planEntity = optional.get();
			PlanForm planForm = new PlanForm();
			BeanUtils.copyProperties(planEntity, planForm);
			
			return planForm;
		
		}

		return null;
	}
	
	
	
	
	
	// plan status --> Active or inactive 
	@Override
	public String changePlanStatus(Integer planId, String status) {
		
		int cnt =planRepo.updatePlanStatus(planId,status);
		
		if(cnt>0) {
			return "Plan changed";
		}
	
		return "Failed to Changed";
	}

}
