package com.revature.dao;

import com.revature.model.Account;
import com.revature.utility.ConnectionUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDao {

    public Account openAccount(Account newAccount) {
        try (Connection con = ConnectionUtility.createConnection()) {

            PreparedStatement ps = con.prepareStatement("INSERT INTO accounts" +
                    "(type_id, balance) VALUES (?, ?) RETURNING *");

            ps.setInt(1, newAccount.getTypeId());
            ps.setLong(2, newAccount.getBalance());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(rs.getInt("id"), rs.getInt("type_id"), rs.getLong("balance"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
