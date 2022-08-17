package com.revature.dao;

import com.revature.model.User;
import com.revature.utility.ConnectionUtility;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;


public class UserDao {

    public User getUserByEmailAndPassword(String email, String pass) throws SQLException {
        try (Connection con = ConnectionUtility.createConnection()) {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM users WHERE email=? AND pass=convert_to(?, 'LATIN1')");

            pstmt.setString(1, email);
            pstmt.setString(2, pass);

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
}
