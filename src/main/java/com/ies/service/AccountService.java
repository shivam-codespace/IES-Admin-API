package com.ies.service;


import com.ies.bindings.UnlockAccForm;
import com.ies.bindings.UserAccForm;

import java.util.List;

public interface AccountService {

    public boolean createUserAccount(UserAccForm accForm);

    public List<UserAccForm> fetchUserAccounts();

    public UserAccForm getUserAccById(Integer accId);

    public String changeAccStatus(Integer userId, String status);

    public String unlockUserAccount(UnlockAccForm unlockAccForm);
}
