package com.ashokit.Binding;

import lombok.Data;

@Data
public class LoginForm {
	
	
	
	private String email;
	
	private String pwd;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	 
}
