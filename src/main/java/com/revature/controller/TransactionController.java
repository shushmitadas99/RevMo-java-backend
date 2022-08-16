package com.revature.controller;

import com.revature.service.TransactionService;
import io.javalin.Javalin;

public class TransactionController implements Controller{
    private TransactionService transactionService;

    public TransactionController() {
        transactionService = new TransactionService();
    }

    @Override
    public void mapEndpoints(Javalin app) {

    }
}
