package com.ashokit.RestController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.Binding.PlanForm;
import com.ashokit.Constants.AppConstants;
import com.ashokit.Services.PlanService;

@RestController
public class PlanRestController {
	
	private Logger logger = LoggerFactory.getLogger(PlanRestController.class);
	
	
	
	@Autowired
	private PlanService planService;
	
	@PostMapping("/plan")
	public ResponseEntity<String> createPlan(@RequestBody PlanForm planForm){
		
		logger.debug("Plan Creatation Proccess started...");
		
		boolean status = planService.createPlan(planForm);
		logger.debug("Plan Creatation Proccess Completed...");
		
		if(status) {
			logger.debug("Plan Created SuccessFully...");
			
			return new ResponseEntity<>(AppConstants.PLAN_CREATED,HttpStatus.CREATED);
			
		}else {
			logger.debug("Plan Creatation Failed...");
			return new ResponseEntity<>(AppConstants.PLAN_CREATION_FAILED,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/plans")
	public ResponseEntity<List<PlanForm>> getPlans() {
		
		logger.debug("Fetching Plans, Proccess started...");
		
		List<PlanForm> planForm = planService.fetchPlan();
		
		logger.debug("Fetching Plans, Proccess Completed...");
		
		logger.debug("Plans Fetched Successfully...");
		
		return new ResponseEntity<>(planForm,HttpStatus.OK);
	}
	
	@GetMapping("/plan/{planId}")
	public ResponseEntity<PlanForm> getPlan(@PathVariable("planId") Integer planId){
		
		PlanForm planAccById = planService.getPlanById(planId);
		
		logger.debug("Fetching Plans Successfully...");
		
		return new ResponseEntity<>(planAccById,HttpStatus.OK);
	}
	
	@PutMapping("/plan/{planId}/{status}")
	public ResponseEntity<List<PlanForm>> updatePlan(@PathVariable("planId") Integer planId,@PathVariable("status") String status ){
		
		logger.debug("Plan Updation Proccess started...");
		
		planService.changePlanStatus(planId, status);
		
		logger.debug("Plan Updation Proccess Completed...");
		
		List<PlanForm> planAccForm = planService.fetchPlan();
		
		logger.debug("Plan Update Successfully...");
		
		return new ResponseEntity<>(planAccForm,HttpStatus.OK);
	}
	
	
	

}
