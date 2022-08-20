package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.dao.TransactionDao;
import com.revature.exception.InvalidParameterException;
import com.revature.model.Transaction;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.revature.utility.Helpers.validateTransactionParams;

public class TransactionService {
    private TransactionDao transactionDao;
    private AccountDao accountDao;

    public TransactionService() {
        this.accountDao = new AccountDao();
        this.transactionDao = new TransactionDao();
    }

    public TransactionService(TransactionDao mockDao) {
        this.transactionDao = mockDao;
    }

    public TransactionService(TransactionDao mockedObject, AccountDao mockedObject2) {
        this.transactionDao = mockedObject;
        this.accountDao = mockedObject2;
    }


    public String moveAmountBetweenSameOwnerAccounts(Map<String, String> addedTransaction) throws InvalidParameterException {
        Transaction t = validateTransactionParams(addedTransaction);
        InvalidParameterException exceptions = new InvalidParameterException();
        //check if both sendingId and receivingId belong to user requesterId
        if (!accountDao.isOwnerOfAccount(t.getRequesterId(), t.getSendingId()) ||
                !accountDao.isOwnerOfAccount(t.getRequesterId(), t.getReceivingId())) {
            exceptions.addMessage("User does not have access to both accounts.");
            throw exceptions;
        }
        //check if sendingId account balance is >= t.amount and :) receivingId.balance < (MAX(Long) - amount -1) -- Jeff Bezos case
        if (!accountDao.canWithdraw(t.getSendingId(), t.getAmount())) {
            exceptions.addMessage("User's balance for the sending account is lower than the amount to be transferred.");
            throw exceptions;
        }
        //check if statusId = 2 not required user owns both accounts

        return transactionDao.moveAmountBetweenSameOwnerAccounts(t);
    }

    public List<Transaction> getAllTransactions() throws SQLException {
        return transactionDao.getAllTransactions();

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

    public List<Transaction> getAllTransactionsByStatusName(String statusName) throws SQLException {
        return transactionDao.getAllTransactionsByStatusName(statusName);
    }

    public List<Transaction> getAllTransactionsByDescription(String description) throws SQLException {
        return transactionDao.getAllTransactionsByDescription(description);
    }

//    public Transaction sendMoneyRequest(Map<String, String> transaction, int uId) throws SQLException, InvalidParameterException {
//        Transaction t = validateTransactionParams(transaction);
//        t.setRequesterId(uId);
////        InvalidParameterException exceptions = new InvalidParameterException();
//        //check if both sendingId and receivingId belong to user requesterId
////        System.out.println(accountDao.isOwnerOfAccount(t.getRequesterId(),t.getSendingId()));
//        //check if sendingId account balance is >= t.amount and :) receivingId.balance < (MAX(Long) - amount -1) -- Jeff Bezos case
////        if(!accountDao.canWithdraw(t.getSendingId(), t.getAmount()))
////            System.out.println("general kenobi");
////            exceptions.addMessage("User's balance for the sending account is lower than the amount to be transferred.");
//        //check if statusId = 2 not required user owns both accounts
////        System.out.println(accountDao.canWithdraw(t.getSendingId(), t.getAmount()));
////        if(exceptions.containsMessage())
////            throw exceptions;
//        System.out.println(t);
//        return transactionDao.sendMoneyRequest(validateTransactionParams(transaction));
//    }
}
