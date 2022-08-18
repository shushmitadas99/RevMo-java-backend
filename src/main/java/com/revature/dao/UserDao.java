package com.revature.dao;

import com.revature.model.User;
import com.revature.utility.ConnectionUtility;

import java.sql.*;

public class UserDao {
    public static boolean getUserEmailByEmail(String email) {
        try (Connection con = ConnectionUtility.createConnection()) {

            PreparedStatement ps = con.prepareStatement("SELECT * from users WHERE email=?");

            ps.setString(1,email);
            //ps.setInt(2,userId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
               return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean updatePassword(String password, int userId) {
        try (Connection con = ConnectionUtility.createConnection()) {

            PreparedStatement ps = con.prepareStatement("UPDATE users SET pass = ? WHERE id=? RETURNING *");

            ps.setString(1,password);
            ps.setInt(2,userId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static User getUserByEmail(String inputEmail) {
        int userId=0;
        String firstName="";
        String lastName="";
        String email="";
        String password="";
        String phoneNumber="";
        String userRole="";

        try (Connection con = ConnectionUtility.createConnection()) {

            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email = ?");

            ps.setString(1,inputEmail);

            ResultSet rs = ps.executeQuery();



            if(rs.next()) {
                while (rs.next()) {
                    userId = rs.getInt("id");
                    firstName = rs.getString("first_name");
                    lastName = rs.getString("last_name");
                    email = rs.getString("email");
                    password = rs.getString("pass");
                    phoneNumber = rs.getString("phone");
                    userRole = rs.getString("role_id");
                }
                return new User(userId, firstName, lastName,email,password,phoneNumber,userRole);
            }else{
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendToken(String token, int userId) {
        try (Connection con = ConnectionUtility.createConnection()) {

            PreparedStatement ps = con.prepareStatement("UPDATE users SET tokenvalue= convert_to(?, 'LATIN1') WHERE id=? RETURNING *");

            ps.setString(1,token);
            ps.setInt(2,userId);
            ResultSet rs = ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    }

