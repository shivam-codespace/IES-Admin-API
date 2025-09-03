package com.ies.service;


import com.ies.bindings.DasboardCards;
import com.ies.bindings.LoginForm;
import com.ies.bindings.UserAccForm;

public interface UserService {

    public  String login(LoginForm loginForm);

    public boolean recoverPwd(String email);

    public DasboardCards fetchDashboardInfo();

    public UserAccForm getUserByEmail(String email);
}
