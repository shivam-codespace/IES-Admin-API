package com.ies.rest;

import java.util.List;

import com.ies.bindings.PlanForm;
import com.ies.constants.AppConstants;
import com.ies.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlanRestController {

	@Autowired
	private PlanService planService;


	@PostMapping("/savePlan")
	public ResponseEntity<String> savePlan(PlanForm form) {

		boolean status = planService.createPlan(form);
		if (status) {
			return new ResponseEntity<>(AppConstants.PLAN_CREATED, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(AppConstants.PLAN_CREATION_FAILD, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/plans")
	public ResponseEntity<List<PlanForm>> fetchAllPlan() {

		List<PlanForm> fetchPlans = planService.fetchPlans();
		return new ResponseEntity<>(fetchPlans, HttpStatus.OK);
	}

	@GetMapping("/plan/{planId}")
	public ResponseEntity<PlanForm> getByPlanId(@PathVariable("planId") Integer planId) {
		PlanForm planById = planService.getPlanById(planId);
		return new ResponseEntity<>(planById, HttpStatus.OK);
	}
	
	@GetMapping("/user/{planId}/{status}")
	public ResponseEntity<String> changeStatus(@PathVariable("planId") Integer planId,@PathVariable("status") String status) {
		
		String planStatus = planService.changePlanStatus(planId, status);
		return new ResponseEntity<>(planStatus, HttpStatus.OK);
	}
}
