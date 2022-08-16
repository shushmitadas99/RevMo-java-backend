package com.revature.dao;

import com.revature.model.Account;
import com.revature.utility.ConnectionUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    public List<Account> getAccountsByUserId(int userId) {
        try (Connection con = ConnectionUtility.createConnection()) {

            //PreparedStatement ps = con.prepareStatement("SELECT * from accounts");
            PreparedStatement ps = con.prepareStatement("SELECT  act.type_name, a.balance, a.id " +
                    " FROM account_types act " +
                    " JOIN accounts a ON a.type_id = act.id " +
                    " JOIN users_with_accounts uwa ON a.id = uwa.account_id " +
                    " WHERE uwa.user_id = 1; ");

            ps.setInt(1, userId);



            ResultSet rs = ps.executeQuery();
            List<Account> accounts = new ArrayList<>();
            while (rs.next()) {
                int accountId = rs.getInt("id");
                int typeId = rs.getInt("type_id");
                int balance = rs.getInt("balance");

                Account account = new Account(accountId,typeId,balance);

                accounts.add(account); // Add user object to users List
            }
            return accounts;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
