package com.revature.dao;
import com.revature.model.*;
import com.revature.utility.ConnectionUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class TransactionDao {
    public String addTransactionByRequestingId(Transaction requesterId) throws SQLException {
        try(Connection con = ConnectionUtility.createConnection()){
            PreparedStatement ps = con.prepareStatement("Insert INTO transactions (requester_id, sending_id, " +
                    "receiving_id, status_id, amount, desc_id) VALUES (?, ?, ?, ?, ?, ?)");

            ps.setInt(1, requesterId.getTransactionId());
            ps.setInt(2, requesterId.getSendingId());
            ps.setInt(3, requesterId.getReceivingId());
//            ps.setTimestamp(4, requesterId.getRequestTime());
            ps.setInt(4, requesterId.getStatus_id());
            ps.setLong(5, requesterId.getBalance());
            ps.setInt(6, requesterId.getDescriptionId());

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return "Transaction Successful";
            }
            else {
                return "Transaction Failed";
            }

        }
    }
}
