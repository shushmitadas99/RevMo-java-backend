package com.revature.service;
import com.revature.dao.TransactionDao;
import com.revature.model.Transaction;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TransactionService {
    private TransactionDao transactionDao;

    public TransactionService() {
        this.transactionDao = new TransactionDao();
    }

    public String addTransactionById(Map<String, String> addedTransaction) throws SQLException {
        Transaction transaction = new Transaction();
        String requester = addedTransaction.get("requesterId");
        int req = Integer.parseInt(requester);
        transaction.setReceivingId(req);
        System.out.println(req);

        String senderId = addedTransaction.get("sendingId");
        int send = Integer.parseInt(senderId);
        transaction.setSendingId(send);
        System.out.println(send);

        String receiverId = addedTransaction.get("receivingId");
        int receiver = Integer.parseInt(receiverId);
        transaction.setReceivingId(receiver);
        System.out.println("3");

        String status_id = addedTransaction.get("status_id");
        int stat = Integer.parseInt(status_id);
        transaction.setApproved(stat);
        System.out.println("4");

        String description = addedTransaction.get("descriptionId");
        int desc = Integer.parseInt(description);
        transaction.setDescriptionId(desc);
        System.out.println("5");

        String balance = addedTransaction.get("balance");
        long bal = Long.parseLong(balance);
        transaction.setBalance(bal);
        System.out.println("6");

        return transactionDao.addTransactionByRequestingId(transaction);
    }

    public List<Transaction> getAllTransactions() throws SQLException {
        return transactionDao.getAllTransactions();

    }

    public List<Transaction> getAllTransactionsByRequesterId(String requestId) throws SQLException {
        int request = Integer.parseInt(requestId);
        return transactionDao.getAllTransactionsbyRequesterId(request);
    }
    public List<Transaction> getAllTransactionsbySenderId(String sender) throws SQLException {
        int sendId = Integer.parseInt(sender);
        return transactionDao.getAllTransactionsbySenderId(sendId);
    }
    public List<Transaction> getAllTransactionsByReceivingId(String receive) throws SQLException {
        int receiveId = Integer.parseInt(receive);
        return transactionDao.getAllTransactionsbyRecievingId(receiveId);
    }
    public List<Transaction> getAllTransactionsByStatusId(String status) throws SQLException {
        int statusId = Integer.parseInt(status);
        return transactionDao.getAllTransactionsbyStatus(statusId);
    }
    public List<Transaction> getAllTransactionsByDescriptionId(String description) throws SQLException {
        int descriptionId = Integer.parseInt(description);
        return transactionDao.getAllTransactionsByDiscriptionId(descriptionId);
    }
//    public List<Transaction> getAllTransactionsByApproved(String approve) throws SQLException {
//        int
//        return transactionDao.getAllTransactionsbyApproved();
//    }



}
