package com.revature.service;
import com.revature.dao.AccountDao;
import com.revature.dao.TransactionDao;
import com.revature.exception.InvalidParameterException;
import com.revature.model.Transaction;
import java.util.Map;

import static com.revature.utility.Helpers.validateTransactionParams;

public class TransactionService {
    private TransactionDao transactionDao;
    private AccountDao accountDao;

    public TransactionService() {
        this.accountDao = new AccountDao();
        this.transactionDao = new TransactionDao();
    }

    public String addTransactionById(Map<String, String> addedTransaction) throws InvalidParameterException {
        Transaction t = validateTransactionParams(addedTransaction);
        InvalidParameterException exceptions = new InvalidParameterException();
        //check if both sendingId and receivingId belong to user requesterId
//        System.out.println(accountDao.isOwnerOfAccount(t.getRequesterId(),t.getSendingId()));
        if(!accountDao.isOwnerOfAccount(t.getRequesterId(),t.getSendingId()) ||
                !accountDao.isOwnerOfAccount(t.getRequesterId(),t.getReceivingId()) )
            exceptions.addMessage("User does not have access to both accounts.");
        //check if sendingId account balance is >= t.amount and :) receivingId.balance < (MAX(Long) - amount -1) -- Jeff Bezos case
        if(!accountDao.canWithdraw(t.getSendingId(), t.getAmount()))
            exceptions.addMessage("User's balance for the sending account is lower than the amount to be transferred.");
        //check if statusId = 2 not required user owns both accounts
//        System.out.println(accountDao.canWithdraw(t.getSendingId(), t.getAmount()));
        if(exceptions.containsMessage())
            throw exceptions;
        return transactionDao.moveAmountBetweenSameOwnerAccounts(validateTransactionParams(addedTransaction));
    }

//    public List<Transaction> getAllTransactions() throws SQLException {
//        return transactionDao.getAllTransactions();
//
//    }
//
//    public List<Transaction> getAllTransactionsByRequesterId(String requestId) throws SQLException {
//        int request = Integer.parseInt(requestId);
//        return transactionDao.getAllTransactionsbyRequesterId(request);
//    }
//    public List<Transaction> getAllTransactionsbySenderId(String sender) throws SQLException {
//        int sendId = Integer.parseInt(sender);
//        return transactionDao.getAllTransactionsbySenderId(sendId);
//    }
//    public List<Transaction> getAllTransactionsByReceivingId(String receive) throws SQLException {
//        int receiveId = Integer.parseInt(receive);
//        return transactionDao.getAllTransactionsbyRecievingId(receiveId);
//    }
//    public List<Transaction> getAllTransactionsByStatusName(String statusName) throws SQLException {
//        //int statusName = Integer.parseInt(status);
//        return transactionDao.getAllTransactionsbyStatusName(statusName);
//    }
//    public List<Transaction> getAllTransactionsByDescription(String description) throws SQLException {
//        //int descriptionId = Integer.parseInt(description);
//        return transactionDao.getAllTransactionsByDiscription(description);
//    }
//    public List<Transaction> getAllTransactionsByApproved(String approve) throws SQLException {
//        int
//        return transactionDao.getAllTransactionsbyApproved();
//    }



}
