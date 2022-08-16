package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.model.Account;

import java.util.List;

public class AccountService {
    private AccountDao accountDao;

    public AccountService() {
        accountDao = new AccountDao();
    }

    public List<Account> getAccountsByUserId(int userId) {
        return accountDao.getAccountsByUserId(userId);

    }
}
