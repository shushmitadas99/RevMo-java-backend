package com.revature.service;
import com.revature.dao.TransactionDao;
import com.revature.model.Transaction;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TransactionService {
    private TransactionDao transactionDao;

    public TransactionService(){this.transactionDao = new TransactionDao();}

    public String addTransactionById(Map<String, String> addedTransaction) throws SQLException {
        Transaction transaction = new Transaction();
        String requester = addedTransaction.get("requesterId");
        int req = Integer.parseInt(requester);
        transaction.setReceivingId(req);


        String senderId = addedTransaction.get("sendingId");
        int send = Integer.parseInt(senderId);
        transaction.setSendingId(send);

        String receiverId = addedTransaction.get("receivingId");
        int receiver = Integer.parseInt(receiverId);
        transaction.setReceivingId(receiver);

        String status_id = addedTransaction.get("status_id");
        int stat = Integer.parseInt(status_id);
        transaction.setApproved(stat);

        String description = addedTransaction.get("descriptionId");
        int desc = Integer.parseInt(description);
        transaction.setDescriptionId(desc);

        String balance = addedTransaction.get("balance");
        long bal = Long.parseLong(balance);
        transaction.setBalance(bal);


        return transactionDao.addTransactionByRequestingId(transaction);
    }
    public List<Transaction> getAllTransactions() throws SQLException {
        return transactionDao.getAllTransactions();
    }
}
