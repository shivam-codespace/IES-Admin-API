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

import com.ashokit.Binding.UserAccountForm;
import com.ashokit.Constants.AppConstants;
import com.ashokit.Services.AccountService;

@RestController
public class AccountRestController {
	
	private Logger logger = LoggerFactory.getLogger(AccountRestController.class);
	
	@Autowired
	private AccountService accService;
	
	@PostMapping("/user")
	public ResponseEntity<String> createAccount(@RequestBody UserAccountForm userAccForm){
		
		logger.debug("Account Creation Process Started...");
		
		boolean status = accService.createUserAccount(userAccForm);
		
		logger.debug("Account Creation Process Completed...");
		if(status) {
			logger.debug("Account Created Successfully...");
			
			return new ResponseEntity<>(AppConstants.ACC_CREATION_SUCCESS , HttpStatus.CREATED);//201
			
		}else {
			
			logger.debug("Account Creation Failed...");
			
			return new ResponseEntity<>(AppConstants.ACC_CREATION_FAILED,HttpStatus.INTERNAL_SERVER_ERROR);//500
		
		}
	}
	 
	
	@GetMapping("/users")
	public ResponseEntity<List<UserAccountForm>> getUsers() {
		
		logger.debug("Fetching User Account Process Started...");
		
		List<UserAccountForm> userAccForm = accService.fetchUserAccount();
		logger.debug("Fetching User Account Process Completed...");
		logger.debug("User Account Fetched Successfully...");
		
		return new ResponseEntity<>(userAccForm,HttpStatus.OK);
	}
	
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<UserAccountForm> getUser(@PathVariable("userId") Integer userId){
		UserAccountForm userAccById = accService.getUserAccountById(userId);
		
		logger.debug("User Account Fetched Successfully...");
		
		return new ResponseEntity<>(userAccById,HttpStatus.OK);
	}
	
	
	
	@PutMapping("/user/{userId}/{status}")
	public ResponseEntity<List<UserAccountForm>> updateUserAcc(@PathVariable("userId") Integer userId, @PathVariable("status") String status){
		
		logger.debug("User Account Updation Process Started...");
		
		accService.changeAccountStatus(userId,status);
		
		logger.debug("User Account updation Process Completed...");
		
		logger.debug("User Account status updated Successfully...");
		List<UserAccountForm> userAccForm = accService.fetchUserAccount();
		
		return new ResponseEntity<>(userAccForm,HttpStatus.OK);
		
		
		
	}
	
	
}
