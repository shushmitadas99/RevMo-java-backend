package com.revature.service;

import com.revature.dao.AccountDao;

public class AccountService {
    private AccountDao accountDao;

    public AccountService() {
        accountDao = new AccountDao();
    }
}
