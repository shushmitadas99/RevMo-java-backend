package com.revature.dao;

import com.revature.model.Account;
import com.revature.utility.ConnectionUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Account> getAccountsByEmail(String email) throws SQLException {
        try (Connection con = ConnectionUtility.createConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT act.type_name, a.type_id, a.balance/100 as " +
                    "amount_in_dollars, a.id as acc_id, uwa.user_id " +
                    "FROM account_types act " +
                    "JOIN accounts a ON a.type_id = act.id " +
                    "JOIN users_with_accounts uwa ON a.id = uwa.account_id " +
                    "JOIN users u ON u.id = uwa.user_id " +
                    "WHERE u.email = ?");

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            List<Account> accounts = new ArrayList<>();
            while (rs.next()) {
                int accountId = rs.getInt("acc_id");
                int typeId = rs.getInt("type_id");
                String typeName = rs.getString("type_name");
                long balance = rs.getLong("amount_in_dollars");
                Account account = new Account(accountId, typeId, typeName, balance);

                accounts.add(account);
            }
            return accounts;
        }
    }


}
