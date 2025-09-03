package com.ies.service;

import com.ies.bindings.PlanForm;
import com.ies.constants.AppConstants;
import com.ies.entities.PlansEntity;
import com.ies.repositories.PlanRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PlanServiceIml implements PlanService {

    private static final Logger logger = Logger.getLogger(PlanServiceIml.class.getName());

    @Autowired
    private PlanRepository planRepo;

    @Override
    public boolean createPlan(PlanForm planForm) {
        logger.info("Creating new plan with details: " + planForm);

        PlansEntity entity = new PlansEntity();
        BeanUtils.copyProperties(planForm, entity);

        try {
            planRepo.save(entity);
            logger.info("Plan created successfully with ID: " + entity.getPlanId());
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while creating plan", e);
            return false;
        }
    }

    @Override
    public List<PlanForm> fetchPlans() {
        logger.info("Fetching all plans from database...");
        List<PlansEntity> planEntity = planRepo.findAll();

        List<PlanForm> plans = new ArrayList<>();
        for (PlansEntity plan : planEntity) {
            PlanForm form = new PlanForm();
            BeanUtils.copyProperties(plan, form);
            plans.add(form);
        }

        logger.info("Total plans fetched: " + plans.size());
        return plans;
    }

    @Override
    public PlanForm getPlanById(Integer planId) {
        logger.info("Fetching plan by ID: " + planId);
        Optional<PlansEntity> optional = planRepo.findById(planId);

        if (optional.isPresent()) {
            PlansEntity planEntity = optional.get();
            PlanForm plan = new PlanForm();
            BeanUtils.copyProperties(planEntity, plan);
            logger.info("Plan found: " + plan);
            return plan;
        } else {
            logger.warning("No plan found with ID: " + planId);
            return null;
        }
    }

    @Override
    public String changePlanStatus(Integer planId, String status) {
        logger.info("Changing status for plan ID " + planId + " to " + status);

        try {
            Integer cn = planRepo.changePStatus(planId, status);
            if (cn > 0) {
                logger.info("Plan status updated successfully for ID: " + planId);
                return AppConstants.PLAN_STATUS_SUCCESS;
            } else {
                logger.warning("Failed to update plan status for ID: " + planId);
                return AppConstants.PLAN_STATUS_FAILD;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while updating plan status for ID: " + planId, e);
            return AppConstants.PLAN_STATUS_FAILD;
        }
    }
}
