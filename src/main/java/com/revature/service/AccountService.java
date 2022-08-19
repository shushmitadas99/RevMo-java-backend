package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.exception.InvalidLoginException;
import com.revature.exception.InvalidParameterException;
import com.revature.model.Account;
import com.revature.model.User;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static java.lang.System.in;

public class AccountService {
    private AccountDao accountDao;
    private UserService userService;

    public AccountService() {
        this.accountDao = new AccountDao();
        this.userService = new UserService();
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
        Account account = accountDao.getAccountByEmailAndAccountId(email, id);

        return account;
    }

    public String linkUserToAccount(int aId, String email) throws SQLException, InvalidParameterException {
        InvalidParameterException exceptions = new InvalidParameterException();
        List<String> owners = accountDao.obtainListOfAccountOwners(aId);
        User myUser = userService.getUserByEmail(email);
        if (userService.getUserByEmail(email) == null) {
            exceptions.addMessage("User not found");
        } else {
            String fullName = myUser.getFirstName() + " " + myUser.getLastName();
            if (owners.contains(fullName)) {
                exceptions.addMessage("User " + myUser.getUserId() + " already linked to account " + aId);
            }
        }
        if (exceptions.containsMessage()) {
            throw exceptions;
        }
        return accountDao.linkUserToAccount(aId, myUser.getUserId());
    }

    public String unlinkUserFromAccount(int aId, String email) throws SQLException, InvalidParameterException {
        List<String> owners = accountDao.obtainListOfAccountOwners(aId);
        User myUser = userService.getUserByEmail(email);
        InvalidParameterException exceptions = new InvalidParameterException();
        if (userService.getUserByEmail(email) == null) {
            exceptions.addMessage("User not found");
        } else {
            String fullName = myUser.getFirstName() + " " + myUser.getLastName();
            if (!owners.contains(fullName)) {
                exceptions.addMessage("User " + myUser.getUserId() + " not linked to account " + aId);
            }
        }
        if (exceptions.containsMessage()) {
            throw exceptions;
        }
        return accountDao.unlinkUserFromAccount(aId, myUser.getUserId());
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

