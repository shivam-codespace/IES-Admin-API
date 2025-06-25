package com.ashokit.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.Binding.DasboardCards;
import com.ashokit.Binding.LoginForm;
import com.ashokit.Binding.UserAccountForm;
import com.ashokit.Services.AccountService;
import com.ashokit.Services.UserService;

@RestController
public class UserRestController {
	
	private UserService userService;
	
	@Autowired
	private AccountService accService;
	
	@PostMapping("/login")
	public String login(@RequestBody LoginForm loginForm) {
		
		String status = userService.login(loginForm);
		
		if(status.equals("success")) {
			return "redirect:/dashboard?email="+loginForm.getEmail();
		}else {
			return status;
		}
	}
	
	
	@GetMapping("/dashboard")
	public ResponseEntity<DasboardCards> buildDashboard(@RequestParam ("email") String email){
		
		UserAccountForm user = userService.getUserByEmail(email);
		
		DasboardCards dashboardCard = userService.fetchDashboardInfo();
		
		dashboardCard.setUser(user);
		return new ResponseEntity<>(dashboardCard,HttpStatus.OK);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
