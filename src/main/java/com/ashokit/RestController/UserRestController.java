package com.ashokit.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.Binding.DasboardCards;
import com.ashokit.Binding.LoginForm;
import com.ashokit.Services.UserService;

@RestController
public class UserRestController {
	
	private UserService userService;
	
	@PostMapping("/login")
	public String login(@RequestBody LoginForm loginForm) {
		
		String status = userService.login(loginForm);
		
		if(status.equals("success")) {
			return "redirect:/dashboard";
		}else {
			return status;
		}
	}
	
	
	@GetMapping("/dashboard")
	public ResponseEntity<DasboardCards> buildDashboard(){
		
		DasboardCards dashboardCard = userService.fetchDashboardInfo();
		
		return new ResponseEntity<>(dashboardCard,HttpStatus.OK);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
