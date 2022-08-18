package com.revature.dao;

import com.revature.model.User;
import com.revature.utility.ConnectionUtility;

import java.sql.*;

public class UserDao {
    public static boolean getUserEmailByEmail(String email) {
        try (Connection con = ConnectionUtility.createConnection()) {

            PreparedStatement ps = con.prepareStatement("SELECT * from users WHERE email=?");

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updatePassword(String password, String token) {
        try (Connection con = ConnectionUtility.createConnection()) {

            PreparedStatement ps = con.prepareStatement("UPDATE users SET pass = convert_to(?, 'LATIN1') WHERE tokenvalue=convert_to(?, 'LATIN1') RETURNING *");

            ps.setString(1, password);
            ps.setString(2, token);

            ResultSet rs = ps.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static User getUserByEmail(String inputEmail) {
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

    public static void sendToken(String token, int userId) {
        try (Connection con = ConnectionUtility.createConnection()) {

            PreparedStatement ps = con.prepareStatement("UPDATE users SET tokenvalue= convert_to(?, 'LATIN1') WHERE id=? RETURNING *");

            ps.setString(1, token);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean validateToken(String token) {
        try (Connection con = ConnectionUtility.createConnection()) {

            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE tokenvalue=convert_to(?, 'LATIN1')");
            ps.setString(1, token);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteToken(String token) {
        try (Connection con = ConnectionUtility.createConnection()) {

            PreparedStatement ps = con.prepareStatement("UPDATE users SET tokenvalue = null WHERE tokenvalue = convert_to(?, 'LATIN1')  RETURNING *");
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}


