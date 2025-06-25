package com.ashokit.Services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.Binding.DasboardCards;
import com.ashokit.Binding.LoginForm;
import com.ashokit.Binding.UserAccountForm;
import com.ashokit.Constants.AppConstants;
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
			
			return AppConstants.INVALID_CRED;
		}
		
		if(AppConstants.Y_STR.equals(entity.getActiveSw()) && AppConstants.UNLOCKED.equals(entity.getAccStatus())) {
			return AppConstants.SUCCESS;
		}else {
			return AppConstants.ACC_BLOCKED_INACTIVE;
		}
	}

	@Override
	public boolean recoverPwd(String email) {
		UserEntity userEntity = userRepo.findByEmail(email);
		
		if(null == userEntity) {
			return  false;
		}else {
			
			String subject =AppConstants.RECOVER_PWD;
			
			String body =readEmailBody(AppConstants.FORGOT_PWD_FILE , userEntity);
			
			return emailUtils.sendEmail(subject, body, email);
		}
		
	}

	@Override
	public DasboardCards fetchDashboardInfo() {
		
		long planCount = planRepo.count();
		
		List<EligEntity> eligList = eligRepo.findAll();
		
		Long approvedCnt = eligList.stream().filter(ed ->ed.getPlanStatus().equals(AppConstants.AP)).count();
		
		Long deniedCnt = eligList.stream().filter(ed ->ed.getPlanStatus().equals(AppConstants.DN)).count();
		
		Double total = eligList.stream().mapToDouble(ed ->ed.getBenefitAmt()).sum();

	
		DasboardCards card = new DasboardCards();
		
		card.setPlansCnt(planCount);
		card.setApprovedCnt(approvedCnt);
		card.setDeniedCnt(deniedCnt);
		card.setBeniftAmtGiven(total);
		
		return card;
	}
	
	@Override
	public UserAccountForm getUserByEmail(String email) {
		
		UserEntity userEntity = userRepo.findByEmail(email);
		
		UserAccountForm user = new UserAccountForm();
		
		BeanUtils.copyProperties(userEntity, user);
		
		return user;
	}
	
	
	private String readEmailBody (String filename,UserEntity user) {
		
		StringBuilder sb = new StringBuilder();
		
		try (Stream<String> lines = Files.lines(Paths.get(filename))){
		
			lines.forEach(line -> {
			
				line = line.replace(AppConstants.FNAME, user.getFullName());
				
				line = line.replace(AppConstants.PWD, user.getPwd());
				
				line = line.replace(AppConstants.EMAIL, user.getEmail());
				
				sb.append(line);
												
			});
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return sb.toString();
		
		
		
	}

}
