package com.ashokit.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.Binding.UserAccountForm;
import com.ashokit.Services.AccountService;

@RestController
public class AccountRestController {
	
	@Autowired
	private AccountService accService;
	
	@PostMapping("/user")
	public ResponseEntity<String> createAccount(@RequestBody UserAccountForm userAccForm){
		boolean status = accService.createUserAccount(userAccForm);
		
		if(status) {
			return new ResponseEntity<>("Account Created" , HttpStatus.CREATED);//201
		}else {
			return new ResponseEntity<>("Account Creation Failed",HttpStatus.INTERNAL_SERVER_ERROR);//500
		}
	}
	
	
	@GetMapping("/users")
	public ResponseEntity<List<UserAccountForm>> getUsers(){
		
		List<UserAccountForm> userAccForm = accService.fetchUserAccount();
		return new ResponseEntity<>(userAccForm,HttpStatus.OK);
	}
	
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<UserAccountForm> getUser(@PathVariable("userId") Integer userId){
		UserAccountForm userAccById = accService.getUserAccountById(userId);
		return new ResponseEntity<>(userAccById,HttpStatus.OK);
	}
	
	@PostMapping("/user/{userId}/{status}")
	public ResponseEntity<List<UserAccountForm>> updateUserAcc(@PathVariable("userId") userId, @PathVariable("status") String status){
		
		accService.changeAccountStatus(userId, status);
		
		List<UserAccountForm> userAccForm = accService.fetchUserAccount();
		
		return new ResponseEntity<>(userAccForm,HttpStatus.OK);
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
