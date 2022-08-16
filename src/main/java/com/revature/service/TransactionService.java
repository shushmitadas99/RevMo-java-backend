package com.revature.service;

import com.revature.dao.TransactionDao;

public class TransactionService {
    private TransactionDao transactionDao;

    public TransactionService() {
        transactionDao = new TransactionDao();
    }
}
