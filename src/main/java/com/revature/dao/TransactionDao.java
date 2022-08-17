package com.revature.dao;
import com.revature.model.*;
import com.revature.utility.ConnectionUtility;
import kotlin.time.TimeSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TransactionDao {
    public String addTransactionByRequestingId(Transaction transaction) throws SQLException {
        try (Connection con = ConnectionUtility.createConnection()) {
            PreparedStatement ps = con.prepareStatement("Insert INTO transactions (requester_id, sending_id, " +
                    "receiving_id, status_id, amount, desc_id, req_time) VALUES (?, ?, ?, ?, ?, ?, Now())");

            ps.setInt(1, transaction.getReceivingId());

            ps.setInt(2, transaction.getSendingId());
            ps.setInt(3, transaction.getReceivingId());
//            ps.setTimestamp(4, requesterId.getRequestTime());
            ps.setInt(4, transaction.getStatus_id());
            ps.setLong(5, transaction.getBalance());
            ps.setInt(6, transaction.getDescriptionId());


            return "Transaction Successful";

        }


    }

    public List<Transaction> getAllTransactions() throws SQLException {
        try (Connection con = ConnectionUtility.createConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT t.id, t.requester_id, t.sending_id, t.receiving_id, t.req_time, t.res_time, t.approved, t.amount, t.receiving_email,  concat_ws(' ', u.first_name,u.last_name) as initiated_by, st.type_name, td.description \n" +
                    "\tFROM transactions t\n" +
                    "\tJOIN users u ON t.requester_id = u.id\n" +
                    "\tJOIN status_types st ON t.status_id  = st.id\n" +
                    "\tJOIN transaction_descriptions td ON t.desc_id  = td.id;");

            ResultSet rs = ps.executeQuery();
            List<Transaction> transactionsList = new ArrayList<>();
            while (rs.next()) {
                int transactionId = rs.getInt("id");
                int requesterId = rs.getInt("requester_id");
                int sendingId = rs.getInt("sending_id");
                int receivingId = rs.getInt("receiving_id");
                Timestamp reqTime = rs.getTimestamp("req_time");
                Timestamp resTime = rs.getTimestamp("res_time");
                boolean approve = rs.getBoolean("approved");
                long amount = rs.getLong("amount");
                String receivingEmail = rs.getString("receiving_email");
                String initiatedBy = rs.getString("initiated_by");
                String typeName = rs.getString("type_name");
                String description = rs.getString("description");
                Transaction transaction = new Transaction(transactionId, requesterId,
                        sendingId, receivingId, reqTime, resTime, approve,receivingEmail,
                        initiatedBy, typeName, description, amount);
                transactionsList.add(transaction);
            }
            return transactionsList;
        }
    }

    public List<Transaction> getAllTransactionsbyRequesterId(int requestId) throws SQLException {
        try (Connection con = ConnectionUtility.createConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM transactions WHERE requester_id = ?");
            ps.setInt(1, requestId);
            ResultSet rs = ps.executeQuery();
            List<Transaction> transactionsList = new ArrayList<>();
            while (rs.next()) {
                int transactionId = rs.getInt("id");
                int requesterId = rs.getInt("requester_id");
                int sendingId = rs.getInt("sending_id");
                int receivingId = rs.getInt("receiving_id");
                Timestamp reqTime = rs.getTimestamp("req_time");
                Timestamp resTime = rs.getTimestamp("res_time");
                boolean approve = rs.getBoolean("approved");
                long amount = rs.getLong("amount");
                int status_id = rs.getInt("status_id");
                int descriptionId = rs.getInt("desc_id");
                Transaction transaction = new Transaction(transactionId, requesterId,
                        sendingId, receivingId, reqTime, resTime, approve, status_id,
                        descriptionId, amount);
                transactionsList.add(transaction);
            }
            return transactionsList;
        }
    }

    public List<Transaction> getAllTransactionsbySenderId(int senderId) throws SQLException {
        try (Connection con = ConnectionUtility.createConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM transactions WHERE sending_id = ?");
            ps.setInt(1, senderId);

            ResultSet rs = ps.executeQuery();
            List<Transaction> transactionsList = new ArrayList<>();
            while (rs.next()) {
                int transactionId = rs.getInt("id");
                int requesterId = rs.getInt("requester_id");
                int sendingId = rs.getInt("sending_id");
                int receivingId = rs.getInt("receiving_id");
                Timestamp reqTime = rs.getTimestamp("req_time");
                Timestamp resTime = rs.getTimestamp("res_time");
                boolean approve = rs.getBoolean("approved");
                long amount = rs.getLong("amount");
                int status_id = rs.getInt("status_id");
                int descriptionId = rs.getInt("desc_id");
                Transaction transaction = new Transaction(transactionId, requesterId,
                        sendingId, receivingId, reqTime, resTime, approve, status_id,
                        descriptionId, amount);
                transactionsList.add(transaction);
            }
            return transactionsList;
        }
    }

    public List<Transaction> getAllTransactionsbyRecievingId(int receiveId) throws SQLException {
        try (Connection con = ConnectionUtility.createConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM transactions WHERE receiving_id = ?");
            ps.setInt(1, receiveId);

            ResultSet rs = ps.executeQuery();
            List<Transaction> transactionsList = new ArrayList<>();
            while (rs.next()) {
                int transactionId = rs.getInt("id");
                int requesterId = rs.getInt("requester_id");
                int sendingId = rs.getInt("sending_id");
                int receivingId = rs.getInt("receiving_id");
                Timestamp reqTime = rs.getTimestamp("req_time");
                Timestamp resTime = rs.getTimestamp("res_time");
                boolean approve = rs.getBoolean("approved");
                long amount = rs.getLong("amount");
                int status_id = rs.getInt("status_id");
                int descriptionId = rs.getInt("desc_id");
                Transaction transaction = new Transaction(transactionId, requesterId,
                        sendingId, receivingId, reqTime, resTime, approve, status_id,
                        descriptionId, amount);
                transactionsList.add(transaction);
            }
            return transactionsList;
        }
    }

    public List<Transaction> getAllTransactionsbyStatus(int statusId) throws SQLException {
        try (Connection con = ConnectionUtility.createConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM transactions WHERE status_id = ?");
            ps.setInt(1, statusId);

            ResultSet rs = ps.executeQuery();
            List<Transaction> transactionsList = new ArrayList<>();
            while (rs.next()) {
                int transactionId = rs.getInt("id");
                int requesterId = rs.getInt("requester_id");
                int sendingId = rs.getInt("sending_id");
                int receivingId = rs.getInt("receiving_id");
                Timestamp reqTime = rs.getTimestamp("req_time");
                Timestamp resTime = rs.getTimestamp("res_time");
                boolean approve = rs.getBoolean("approved");
                long amount = rs.getLong("amount");
                int status_id = rs.getInt("status_id");
                int descriptionId = rs.getInt("desc_id");
                Transaction transaction = new Transaction(transactionId, requesterId,
                        sendingId, receivingId, reqTime, resTime, approve, status_id,
                        descriptionId, amount);
                transactionsList.add(transaction);
            }
            return transactionsList;
        }
    }

    public List<Transaction> getAllTransactionsByDiscriptionId(int description) throws SQLException {
        try (Connection con = ConnectionUtility.createConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM transactions WHERE desc_id = ?");
            ps.setInt(1, description);

            ResultSet rs = ps.executeQuery();
            List<Transaction> transactionsList = new ArrayList<>();
            while (rs.next()) {
                int transactionId = rs.getInt("id");
                int requesterId = rs.getInt("requester_id");
                int sendingId = rs.getInt("sending_id");
                int receivingId = rs.getInt("receiving_id");
                Timestamp reqTime = rs.getTimestamp("req_time");
                Timestamp resTime = rs.getTimestamp("res_time");
                boolean approve = rs.getBoolean("approved");
                long amount = rs.getLong("amount");
                int status_id = rs.getInt("status_id");
                int descriptionId = rs.getInt("desc_id");
                Transaction transaction = new Transaction(transactionId, requesterId,
                        sendingId, receivingId, reqTime, resTime, approve, status_id,
                        descriptionId, amount);
                transactionsList.add(transaction);
            }
            return transactionsList;
        }
    }

//
//    public List<Transaction> getAllTransactionsbyApproved(int approveId) throws SQLException {
//        try (Connection con = ConnectionUtility.createConnection()) {
//            PreparedStatement ps = con.prepareStatement("SELECT * FROM transactions");
//            ps.setInt(1, approveId);
//
//            ResultSet rs = ps.executeQuery();
//            List<Transaction> transactionsList = new ArrayList<>();
//            while (rs.next()) {
//                int transactionId = rs.getInt("id");
//                int requesterId = rs.getInt("requester_id");
//                int sendingId = rs.getInt("sending_id");
//                int receivingId = rs.getInt("receiving_id");
//                Timestamp reqTime = rs.getTimestamp("req_time");
//                Timestamp resTime = rs.getTimestamp("res_time");
//                boolean approve = rs.getBoolean("approved");
//                long amount = rs.getLong("amount");
//                int status_id = rs.getInt("status_id");
//                int descriptionId = rs.getInt("desc_id");
//                Transaction transaction = new Transaction(transactionId, requesterId,
//                        sendingId, receivingId, reqTime, resTime, approve, status_id,
//                        descriptionId, amount);
//                transactionsList.add(transaction);
//            }
//            return transactionsList;
//        }
//    }
    //              (update(approve/deny) give resolve time, descriptionId, change amount in accounts
//delete-cancel a transaction as long as it hasnt been approved

}

