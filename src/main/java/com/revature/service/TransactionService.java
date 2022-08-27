package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.dao.TransactionDao;
import com.revature.exception.InvalidParameterException;
import com.revature.model.Account;
import com.revature.model.Transaction;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.revature.utility.Helpers.validatePositiveInt;
import static com.revature.utility.Helpers.validateTransactionParams;

public class TransactionService {
    private final TransactionDao transactionDao;
    private AccountDao accountDao;
    private AccountService accountService;
    private UserService userService;

    public TransactionService() {
        this.accountDao = new AccountDao();
        this.transactionDao = new TransactionDao();
        this.accountService = new AccountService();
        this.userService = new UserService();
    }

    public TransactionService(TransactionDao mockDao) {
        this.transactionDao = mockDao;
    }

    public TransactionService(TransactionDao mockedObject, AccountDao mockedObject2) {
        this.transactionDao = mockedObject;
        this.accountDao = mockedObject2;
    }

    public Object transferBetweenAccounts(Map<String, String> newTransaction, int requesterId) {
        Transaction transaction = new Transaction();
        transaction.setRequesterId(requesterId);
        int sendingId = Integer.parseInt(newTransaction.get("sendingId"));
        int receivingId = Integer.parseInt(newTransaction.get("receivingId"));
        int amount = Integer.parseInt(newTransaction.get("amount"));
        System.out.println(amount);
        String email = (newTransaction.get("email"));
        transaction.setSendingId(sendingId);
        transaction.setReceivingId(receivingId);
        transaction.setAmount(amount);
        transaction.setReceivingEmail(email);

        String pass = transactionDao.transferBetweenAccounts(transaction);

        return pass;
    }

    public String sendMoney(Map<String, String> addedTransaction, int uId, String sendingEmail) throws InvalidParameterException, SQLException {
        String email = addedTransaction.get("receivingEmail");
        InvalidParameterException exception = new InvalidParameterException();
        if (!userService.getUserEmailByEmail(email)) {
            exception.addMessage("Email does not exist");
            throw exception;
        }
        List<Account> myAccounts = accountService.getAccountsByEmail(email);
        Account primaryAccount = myAccounts.get(0);
        String sendingId = addedTransaction.get("sendingId");
        String amount = addedTransaction.get("amount");
        System.out.println(primaryAccount.getAccountId());
        Map<String, String> trx = new HashMap<>();
        trx.put("initiatedBy", sendingEmail);
        trx.put("requesterId", String.valueOf(uId));
        trx.put("sendingId", sendingId);
        trx.put("receivingId", String.valueOf(primaryAccount.getAccountId()));
        trx.put("amount", amount);
        if (Integer.parseInt(amount) > accountDao.getBalanceofAccountById(Integer.parseInt(sendingId))) {
            exception.addMessage("Not Enough Money");
            throw exception;
        }
        trx.put("descriptionId", "3");
        trx.put("receivingEmail", email);
        Transaction t = validateTransactionParams(trx);

        return transactionDao.moveAmountBetweenAccounts(t);
    }

    public String requestAmount(Map<String, String> addedTransaction, int uId, String receivingEmail) throws InvalidParameterException, SQLException {
        String email = addedTransaction.get("receivingEmail");
        InvalidParameterException exception = new InvalidParameterException();
        if (!userService.getUserEmailByEmail(email)) {
            exception.addMessage("Email does not exist");
            throw exception;
        }
        List<Account> myAccounts = accountService.getAccountsByEmail(email);
        Account primaryAccount = myAccounts.get(0);
        String receivingId = addedTransaction.get("receivingId");
        String amount = addedTransaction.get("amount");
        System.out.println(primaryAccount.getAccountId());
        Map<String, String> trx = new HashMap<>();
        trx.put("initiatedBy", receivingEmail);
        trx.put("requesterId", String.valueOf(uId));
        trx.put("sendingId", String.valueOf(primaryAccount.getAccountId()));
        trx.put("receivingId", receivingId);
        trx.put("amount", amount);
        trx.put("descriptionId", "4");
        trx.put("receivingEmail", email);
        System.out.println(trx);
        Transaction t = validateTransactionParams(trx);

        return transactionDao.storeRequest(t);
    }

    public Object handleRequestAmount(Map<String, String> tr) throws InvalidParameterException {
        // this map contains t.id, t.requester_id, t.status_id
        Transaction t = validateTransactionParams(tr);
        InvalidParameterException exceptions = new InvalidParameterException();
        //check if statusId = 2 not required user owns both accounts
        if (t.getStatusId() == 1) {
            exceptions.addMessage("To handle the request the status must change from <Pending> to <Approved> or <Denied>");
            throw exceptions;
        }
        if (t.getStatusId() == 3) {
            return transactionDao.completeRequestDenied(t);
        } else {
            return transactionDao.completeRequestApproved(t);
        }
    }

    public List<Transaction> getAllTransactions() throws SQLException {
        return transactionDao.getAllTransactions();

    }

    public List<Transaction> getAllTransactions(int aid) throws SQLException {
        return transactionDao.getAllTransactions(aid);

    }

    public List<Transaction> getAllTransactionsByRequesterId(String requestId) throws SQLException {
        int request = Integer.parseInt(requestId);
        return transactionDao.getAllTransactionsByRequesterId(request);
    }

    public List<Transaction> getAllTransactionsBySenderId(String sender) throws SQLException {
        int sendId = Integer.parseInt(sender);
        return transactionDao.getAllTransactionsBySenderId(sendId);
    }

    public List<Transaction> getAllTransactionsByReceivingId(String receive) throws SQLException {
        int receiveId = Integer.parseInt(receive);
        return transactionDao.getAllTransactionsByRecievingId(receiveId);
    }

    public List<Transaction> getAllOutgoingTransactionsByStatusName(String statusName, int aId) throws SQLException {
        return transactionDao.getAllOutgoingTransactionsByStatusName(statusName, aId);
    }

    public List<Transaction> getAllIncomingTransactionsByStatusName(String statusName, int aId) throws SQLException {
        return transactionDao.getAllIncomingTransactionsByStatusName(statusName, aId);
    }

    public List<Transaction> getAllTransactionsByDescription(String description) throws SQLException {
        return transactionDao.getAllTransactionsByDescription(description);
    }

    public Long trackAccountIncome(int aId, int month, int year) {
        return transactionDao.monthlyAccountIncome(aId, month, year);
    }

    public Long trackUserIncome(int uId, int month, int year) {
        return transactionDao.monthlyUserIncome(uId, month, year);
    }

    public Long trackAllTimeUserIncome(int uId) {
        return transactionDao.allTimeUserIncome(uId);
    }

    public Long trackAllTimeAccountIncome(int uId, int aId) {
        return transactionDao.allTimeAccountIncome(uId, aId);
    }

    public int getCurrentMonth() {
        return transactionDao.getCurrentMonth();
    }

    public int getCurrentYear() {
        return transactionDao.getCurrentYear();
    }

}
