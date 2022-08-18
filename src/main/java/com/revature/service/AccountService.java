package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.exception.InvalidLoginException;
import com.revature.exception.InvalidParameterException;
import com.revature.model.Account;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AccountService {
    private AccountDao accountDao;

    public AccountService() {
        this.accountDao = new AccountDao();
    }

    public AccountService(AccountDao mockDao) {
        this.accountDao = mockDao;
    }

    public Account openAccount(Map<String, String> newAccount) throws InvalidParameterException {
        Account account = new Account();
        InvalidParameterException exceptions = new InvalidParameterException();
        String typeId = newAccount.get("typeId");

        if (typeId == null) {
            exceptions.addMessage("Must have an account type");

        } else {
            account.setTypeId(Integer.parseInt(typeId));
        }
        String balance = newAccount.get("balance");

        if (balance == null) {
            exceptions.addMessage("Account balance must not be null");
        } else {
            try {
                long accountBalance = Long.parseLong(balance);
                if (accountBalance < 0) {
                    exceptions.addMessage("Account balance must be positive.");
                }
                account.setBalance(accountBalance);
            } catch (NumberFormatException e) {
                exceptions.addMessage("Account balance " + balance + " is invalid. Please enter a valid numeric amount");
            }

        }

        if (exceptions.containsMessage()) {
            throw exceptions;
        }
        System.out.println(account);
        return accountDao.openAccount(account);
    }

    public List<Account> getAccountsByEmail(String email) throws SQLException {
        return accountDao.getAccountsByEmail(email);
    }

    public Account getAccountByEmailAndAccountId(String email, int id) {
        return accountDao.getAccountByEmailAndAccountId(email, id);
    }

    public String linkUserToAccount(int aId, int uId) throws SQLException {


        return accountDao.linkUserToAccount(aId, uId);
    }

    public String unlinkUserFromAccount(int aId, int uId) throws SQLException {
        return accountDao.unlinkUserFromAccount(aId, uId);
    }

    public String deleteAccount(int aId) throws SQLException, InvalidParameterException {
        Account account = accountDao.getAccountById(aId);
        List<String> accountOwners = accountDao.obtainListOfAccountOwners(aId);
        InvalidParameterException exception = new InvalidParameterException();
        if (account.getBalance() != 0) {
            exception.addMessage("Account balance must be 0!");
        }
        if (accountOwners.size() > 1) {
            exception.addMessage("An account with more than one linked user cannot be deleted!");
        }
        if (exception.containsMessage()) {
            throw exception;
        }
        return accountDao.deleteAccount(aId);
    }

    public List<String> obtainListOfAccountOwners(int aId) throws SQLException {
        return accountDao.obtainListOfAccountOwners(aId);
    }

}

