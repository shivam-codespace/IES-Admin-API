package com.ashokit.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.Binding.DasboardCards;
import com.ashokit.Binding.LoginForm;
import com.ashokit.Entity.EligEntity;
import com.ashokit.Entity.UserEntity;
import com.ashokit.Repository.PlanRepo;
import com.ashokit.Repository.UserRepo;
import com.ashokit.Utils.EmailUtils;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private PlanRepo planRepo;
	
	@Autowired
	private EligEntity eligRepo;
	
	@Override
	public String login(LoginForm loginForm) {
		UserEntity entity =userRepo.findByEmailAndPwd(loginForm.getEmail(),loginForm.getPwd());
		
		if (entity == null) {
			
			return "Invalid Credentials";
		}
		
		if("Y".equals(entity.getActiveSw()) && "UNLOCKED".equals(entity.getAccStatus())) {
			return "success";
		}else {
			return "Account Locked/In-Active";
		}
	}

	@Override
	public boolean recoverPwd(String email) {
		UserEntity userEntity = userRepo.findByEmail(email);
		
		if(null == userEntity) {
			return  false;
		}else {
			
			String subject ="";
			String body ="";
			return emailUtils.sendEmail(subject, body, email);
		}
		
	}

	@Override
	public DasboardCards fetchDashboardInfo() {
		
		long planCount = planRepo.count();
		
		List<EligEntity> eligList = eligRepo.findAll();
		
		Long approvedCnt = eligList.stream().filter(ed ->ed.getPlanStatus().equals("AP")).count();
		
		Long deniedCnt = eligList.stream().filter(ed ->ed.getPlanStatus().equals("DN")).count();
		
		Double total = eligList.stream().mapToDouble(ed ->ed.getBenefitAmt()).sum();

		
		
		DasboardCards card = new DasboardCards();
		
		
		
		
		card.setPlansCnt(planCount);
		card.setApprovedCnt(approvedCnt);
		card.setDeniedCnt(deniedCnt);
		card.setBeniftAmtGiven(total);
		
		return card;
	}

}
