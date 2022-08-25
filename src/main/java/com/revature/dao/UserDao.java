package com.revature.dao;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import com.revature.model.User;
import com.revature.utility.ConnectionUtility;


import java.sql.*;

public class UserDao {
    public boolean getUserEmailByEmail(String email) {
        try (Connection con = ConnectionUtility.createConnection()) {

            PreparedStatement ps = con.prepareStatement("SELECT * from users WHERE email=?");

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePassword(String password, String token) {
        try (Connection con = ConnectionUtility.createConnection()) {

            PreparedStatement ps = con.prepareStatement("UPDATE users SET pass = convert_to(?, 'LATIN1') WHERE tokenvalue=convert_to(?, 'LATIN1') RETURNING *");

            ps.setString(1, password);
            ps.setString(2, token);

            ResultSet rs = ps.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public User getUserByInputEmail(String inputEmail) {
        int userId = 0;
        StringBuilder firstName = new StringBuilder();
        StringBuilder lastName = new StringBuilder();
        StringBuilder email = new StringBuilder();
        StringBuilder password = new StringBuilder();
        StringBuilder phoneNumber = new StringBuilder();
        StringBuilder userRole = new StringBuilder();

        try (Connection con = ConnectionUtility.createConnection()) {

            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email = ?");

            ps.setString(1, inputEmail);

            ResultSet rs = ps.executeQuery();


            if (rs.next()) {
                userId = rs.getInt("id");
                firstName.append(rs.getString("first_name"));
                lastName.append(rs.getString("last_name"));
                email.append(rs.getString("email"));
                password.append(rs.getString("pass"));
                phoneNumber.append(rs.getString("phone"));
                userRole.append(rs.getString("role_id"));

                return new User(userId, firstName.toString(), lastName.toString(), email.toString(), password.toString(), phoneNumber.toString(), userRole.toString());
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendToken(String token, int userId) {
        try (Connection con = ConnectionUtility.createConnection()) {

            PreparedStatement ps = con.prepareStatement("UPDATE users SET tokenvalue= convert_to(?, 'LATIN1') WHERE id=? RETURNING *");

            ps.setString(1, token);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validateToken(String token) {
        try (Connection con = ConnectionUtility.createConnection()) {

            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE tokenvalue=convert_to(?, 'LATIN1')");
            ps.setString(1, token);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteToken(String token) {
        try (Connection con = ConnectionUtility.createConnection()) {

            PreparedStatement ps = con.prepareStatement("UPDATE users SET tokenvalue = null WHERE tokenvalue = convert_to(?, 'LATIN1')  RETURNING *");
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserByEmailAndPassword(String email, String password) throws SQLException {
        try (Connection con = ConnectionUtility.createConnection()) {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM users WHERE email=? AND pass=convert_to(?, 'LATIN1')");

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("first_name"),
                        rs.getString("last_name"), rs.getString("email"),
                        rs.getString("pass"), rs.getString("phone"),
                        rs.getString("role_id"));
            } else {
                return null;
            }
        }
    }

    public User getUserByEmail(String email) {
        try (Connection con = ConnectionUtility.createConnection()) {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM users WHERE email=?");

            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("first_name"),
                        rs.getString("last_name"), rs.getString("email"),
                        rs.getString("pass"), rs.getString("phone"),
                        rs.getString("role_id"));
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public String updateEmail(int userId, String email) {
        try (Connection con = ConnectionUtility.createConnection()) {
            PreparedStatement pstmt = con.prepareStatement("UPDATE users SET email=? WHERE id=? RETURNING *");
            pstmt.setString(1, email);
            pstmt.setInt(2, userId);
            ResultSet rs = pstmt.executeQuery();

            return "Email updated.";

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String updatephone(int userId, String phoneNumber) {
        try (Connection con = ConnectionUtility.createConnection()) {
            PreparedStatement pstmt = con.prepareStatement("UPDATE users SET phone=? WHERE id=? RETURNING *");
            pstmt.setString(1, phoneNumber);
            pstmt.setInt(2, userId);
            ResultSet rs = pstmt.executeQuery();

            return "phone updated. Congrats?";

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getRequesteeEmailByTransactionId(int transactionId) {
        try (Connection con = ConnectionUtility.createConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT u.email FROM users u " +
                    "JOIN users_with_accounts uwa ON uwa.user_id = u.id " +
                    "JOIN transactions t ON uwa.account_id = t.sending_id " +
                    "WHERE t.id = ?");
            ps.setInt(1, transactionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("email");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public String getReceiverEmailByTransactionId(int transactionId) {
        try (Connection con = ConnectionUtility.createConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT u.email FROM users u " +
                    "JOIN users_with_accounts uwa ON uwa.user_id = u.id " +
                    "JOIN transactions t ON uwa.account_id = t.receiving_id " +
                    "WHERE t.id = ?");
            ps.setInt(1, transactionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("email");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}


