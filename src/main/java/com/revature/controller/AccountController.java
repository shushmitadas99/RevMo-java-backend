package com.revature.controller;

import com.revature.service.AccountService;
import io.javalin.Javalin;

public class AccountController implements Controller{
    private AccountService accountService;

    public AccountController() {
        accountService = new AccountService();
    }
    @Override
    public void mapEndpoints(Javalin app) {

    }
}
