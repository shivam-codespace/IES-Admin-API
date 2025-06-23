package com.ashokit.Services;

import com.ashokit.Binding.DasboardCards;
import com.ashokit.Binding.LoginForm;


public interface UserService {
	
	//login
	public String login(LoginForm loginForm);
	
	//recover password
	public boolean recoverPwd(String email);
	
	//view dashboard
	public DasboardCards fetchDashboardInfo();

}
