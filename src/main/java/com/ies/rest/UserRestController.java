package com.ies.rest;

import com.ies.bindings.DasboardCards;
import com.ies.bindings.LoginForm;
import com.ies.bindings.UserAccForm;
import com.ies.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserRestController {

	@Autowired
	private UserService userService;

	//http://localhost:9091/login
	@GetMapping("/login")
	public String login(LoginForm form) {

		String status = userService.login(form);
		if ("Success".equals(status)) {
			return "redirect:/dashboard?email=" + form.getEmail();
		} else {
			return status;
		}
	}

	@GetMapping("/dashboard")
	public ResponseEntity<DasboardCards> buildDashboard(@PathVariable("email") String email) {

		UserAccForm user = userService.getUserByEmail(email);

		DasboardCards dasboardCards = userService.fetchDashboardInfo();
		dasboardCards.setUser(user);
		return new ResponseEntity<>(dasboardCards, HttpStatus.OK);
	}

}
