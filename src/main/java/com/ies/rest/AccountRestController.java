package com.ies.rest;

import java.util.List;

import com.ies.bindings.UserAccForm;
import com.ies.constants.AppConstants;
import com.ies.service.AccountService;
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

@RestController
public class AccountRestController {

	private Logger logger = LoggerFactory.getLogger(AccountRestController.class);

	@Autowired
	private AccountService accService;

	//http://localhost:9091/user
	@PostMapping("/user")
	public ResponseEntity<String> createUser(@RequestBody UserAccForm form) {

		logger.debug(AppConstants.ACCOUNT_CREATION_PROCESS);
		boolean status = accService.createUserAccount(form);

		logger.debug(AppConstants.ACCOUNT_CREATION_COMPLETED);

		if (status) {
			logger.info(AppConstants.ACCOUNT_CREATED_SUCCESSFUL);
			return new ResponseEntity<>(AppConstants.ACCOUNT_CREATED, HttpStatus.CREATED);
		} else {
			logger.info(AppConstants.ACCOUNT_CREATION_FAILD);
			return new ResponseEntity<>(AppConstants.ACCOUNT_CREATION_FAILD, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//http://localhost:9091/users
	@GetMapping("/users")
	public ResponseEntity<List<UserAccForm>> getUsers() {
		logger.debug(AppConstants.FETCHING_STARTED);
		List<UserAccForm> userAccountsForms = accService.fetchUserAccounts();
		logger.debug(AppConstants.FETCHING_COMPLETED);

		logger.info(AppConstants.FETCHING_SUCCESS);

		return new ResponseEntity<>(userAccountsForms, HttpStatus.OK);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<UserAccForm> getUser(@PathVariable("userId") Integer userId) {
		UserAccForm userAccById = accService.getUserAccById(userId);

		logger.debug(AppConstants.USER_ACC_FETCHING_SUCCESS);
		return new ResponseEntity<>(userAccById, HttpStatus.OK);
	}

	@PutMapping("/user/{userId}/{status}")
	public ResponseEntity<List<UserAccForm>> updateUserAcc(@PathVariable("userId") Integer userId,
			@PathVariable("status") String status) {

		logger.debug(AppConstants.USER_ACC_UPDATE_STARTED);
		accService.changeAccStatus(userId, status); 
		logger.debug(AppConstants.USER_ACC_UPDATE_COMPLETED);
		logger.info(AppConstants.USER_ACC_UPDATE_SUCCESSFUL);
		List<UserAccForm> fetchUserAccounts = accService.fetchUserAccounts();
		return new ResponseEntity<>(fetchUserAccounts, HttpStatus.OK);
	}
}
