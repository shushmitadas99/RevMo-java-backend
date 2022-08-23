package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.dao.TransactionDao;
import com.revature.exception.InvalidParameterException;
import com.revature.model.Transaction;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.revature.utility.Helpers.validatePositiveInt;
import static com.revature.utility.Helpers.validateTransactionParams;

public class TransactionService {
    private final TransactionDao transactionDao;
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


    public String moveAmountBetweenAccounts(Map<String, String> addedTransaction) throws InvalidParameterException {
        Transaction t = validateTransactionParams(addedTransaction);
        InvalidParameterException exceptions = new InvalidParameterException();
        if ((t.getSendingId() > 0 && t.getReceivingId() > 0) && (!accountDao.isOwnerOfAccount(t.getRequesterId(), t.getSendingId()) ||
                !accountDao.isOwnerOfAccount(t.getRequesterId(), t.getReceivingId()))) {
            exceptions.addMessage("User does not have access to both accounts.");
            throw exceptions;
        }
        if (t.getSendingId() == 0) t.setSendingId(accountDao.getPrimaryAccountById(t.getRequesterId()));
        if (t.getReceivingId() == 0) t.setReceivingId(accountDao.getPrimaryAccountByEmail(t.getReceivingEmail()));
        //check if sendingId account balance is >= t.amount and :) receivingId.balance < (MAX(Long) - amount -1) -- Jeff Bezos case
        if (!accountDao.canWithdraw(t.getSendingId(), t.getAmount())) {
            exceptions.addMessage("User's balance is lower than the amount to be transferred.");
            throw exceptions;
        }
        return transactionDao.moveAmountBetweenAccounts(t);
    }

    public String requestAmount(Map<String, String> addedTransaction) throws InvalidParameterException {
        Transaction t = validateTransactionParams(addedTransaction);
        //the requester is the recipient their primary account goes in receivingId
        t.setReceivingId(accountDao.getPrimaryAccountById(t.getRequesterId()));
        //the requestee's sending account by email
        t.setSendingId(accountDao.getPrimaryAccountByEmail(t.getReceivingEmail()));
        InvalidParameterException exceptions = new InvalidParameterException();
        //check if sendingId account balance is >= t.amount and :) receivingId.balance < (MAX(Long) - amount -1) -- Jeff Bezos case
        if (!accountDao.canWithdraw(t.getSendingId(), t.getAmount())) {
            exceptions.addMessage("User's balance for the sending account is lower than the amount to be transferred.");
            throw exceptions;
        }

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
