package com.ashokit.Services;

import java.util.List;

import com.ashokit.Binding.UnlockAccForm;
import com.ashokit.Binding.UserAccountForm;

public interface AccountService {
	
	// create account method
	public boolean createUserAccount(UserAccountForm accForm);
	
	
	//view account method 
	public List<UserAccountForm> fetchUserAccount();
	
	//update account method
	public UserAccountForm getUserAccountById(Integer accId);
	
	//change Account Status
	public String changeAccountStatus(Integer accId, String status);
	
	//unlock account
	public String unlockUserAccount(UnlockAccForm unlockAccForm);

}
