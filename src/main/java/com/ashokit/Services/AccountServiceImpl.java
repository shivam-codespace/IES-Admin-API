package com.ashokit.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.Binding.UnlockAccForm;
import com.ashokit.Binding.UserAccountForm;
import com.ashokit.Entity.UserEntity;
import com.ashokit.IesAdminModuleApplication;
import com.ashokit.Repository.UserRepo;
import com.ashokit.Utils.EmailUtils;


@Service
public class AccountServiceImpl implements AccountService{

  
	
	
	@Autowired
	private  UserRepo userRepo;
	
	@Autowired
	private EmailUtils emailUtils;

	
	@Override
	public boolean createUserAccount(UserAccountForm accForm) {
		
		UserEntity entity = new UserEntity();
		BeanUtils.copyProperties(accForm, entity);
		
		//set random password
		entity.setPwd(genratePwd());
		
		//set account status
		entity.setAccStatus("LOCKED");
		
		entity.setActiveSw("Y");
		
		userRepo.save(entity);
		
		//send email
		
		String  subject="";
		String  body = "";
		
		return emailUtils.sendEmail(subject, body, accForm.getEmail());
		
	
	}

	
	
	
	
	
	
	
	
	
	@Override
	public List<UserAccountForm> fetchUserAccount() {
		
		List<UserEntity> userEntities = userRepo.findAll();
		
		List<UserAccountForm> users = new ArrayList<UserAccountForm>();
		
		for (UserEntity userEntity : userEntities){
			UserAccountForm user = new UserAccountForm();
			BeanUtils.copyProperties(userEntity, user);
			users.add(user);
		}
		
		return users;
	}

	
	
	//for update user details 
	@Override
	public UserAccountForm getUserAccountById(Integer accId) {
		
		Optional<UserEntity> optional = userRepo.findById(accId);
		
		if(optional.isPresent()) {
		
			UserEntity userEntity = optional.get();
			
			UserAccountForm user = new UserAccountForm();
			
			BeanUtils.copyProperties(userEntity, user);
			
			return user;
		}
		return null;
	}

	
	
	
	//user status --> Active or inactive
	@Override
	public String changeAccountStatus(Integer userId, String status){
		
		int cnt = userRepo.updateAccStatus(userId, status);
		
		if(cnt>0) {
			return "Status Changed";
		}
		
		return "Failed to changed";
	}
	
	@Override
	public String unlockUserAccount (UnlockAccForm unlockAccForm){
		
		UserEntity entity = userRepo.findByEmail(unlockAccForm.getEmail());
		
		entity.setPwd(unlockAccForm.getNewPwd());
		entity.setAccStatus("UNLOCKED");
		
		userRepo.save(entity);
		
		return "ACCOUNT UNLOCKED";
	}
	
	
	
	
	
	private String genratePwd() {
		String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
	    String numbers = "0123456789";

	    // combine all strings
	    String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

	    // create random string builder
	    StringBuilder sb = new StringBuilder();

	    // create an object of Random class
	    Random random = new Random();

	    // specify length of random string
	    int length = 6;

	    for(int i = 0; i < length; i++) {

	      // generate random index number
	      int index = random.nextInt(alphaNumeric.length());

	      // get character specified by index
	      // from the string
	      char randomChar = alphaNumeric.charAt(index);

	      // append the character to string builder
	      sb.append(randomChar);
	    }

	    String randomString = sb.toString();
	    return randomString;
	}
	
	

}
