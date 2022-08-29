package com.revature.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.revature.dao.UserDao;
import com.revature.exception.InvalidParameterException;
import com.revature.model.User;
import com.revature.exception.InvalidLoginException;
import com.revature.utility.EmailUtility;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Jwts;
import org.json.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.awt.*;
import java.sql.SQLException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import java.util.List;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao mockedObject) {
        userDao = mockedObject;
    }

    public UserService() {
        this.userDao = new UserDao();
    }

    public boolean getUserEmailByEmail(String email) {
        return userDao.getUserEmailByEmail(email);
    }

    public void updatePassword(String password, String token) {
        userDao.updatePassword(password, token);
    }

    public User getUserByInputEmail(String inputEmail) {
        return userDao.getUserByInputEmail(inputEmail);
    }

    public boolean sendToken(String token, int userId) {
        return userDao.sendToken(token, userId);
    }

    public boolean validateToken(String token) {
        return userDao.validateToken(token);
    }

    public void deleteToken(String token) {
        userDao.deleteToken(token);
    }

    public User login(String email, String password) throws SQLException, InvalidLoginException {
        User user = userDao.getUserByEmailAndPassword(email, password);

        if (user == null) {
            throw new InvalidLoginException("Invalid email and/or password");
        }
        return user;
    }

    public User getUserByEmail(String email) {

        return userDao.getUserByEmail(email);

    }

    public void updateInfo(Map<String, String> newInfo, int userId, String oldEmail) throws InvalidParameterException {
        InvalidParameterException exceptions = new InvalidParameterException();
        String newEmail = newInfo.get("email");
        String newPhone = newInfo.get("phone");
        User oldUser = userDao.getUserByEmail(oldEmail);
        if (userId != oldUser.getUserId()) {
            exceptions.addMessage("User Id does not match our records.");
            throw exceptions;
        }
        if (!Objects.equals(newEmail, oldUser.getEmail()) && userDao.getUserByEmail(newEmail) != null) {
            exceptions.addMessage("Email already in system");
            throw exceptions;
        }
        if (!Objects.equals(newEmail, oldUser.getEmail())) {
            userDao.updateEmail(userId, newEmail);
        }
        if (!Objects.equals(newPhone, oldUser.getPhoneNumber())) {
            userDao.updatephone(userId, newPhone);
        }

    }


    public String getRequesteeByTransactionId(int transactionId) {
        return userDao.getRequesteeEmailByTransactionId(transactionId);
    }

    public List<String> getReceiverByTransactionId(int transactionId) {
        return userDao.getReceiverEmailByTransactionId(transactionId);
    }

    public boolean resetPassword(String tokenvalue, String newpassword){
        //Decode token to check expiration
        DecodedJWT jwt = JWT.decode(tokenvalue);
        //If valid token not expired validate if correct
        if (jwt.getExpiresAt().before(new Date())) {
            throw new RuntimeException("Reset Link Expired. Please try again");
        } else {
            boolean validateToken = userDao.validateToken(tokenvalue); // we need to write a code to verify the token validity
            if (validateToken) {
                //Update password in Database and delete token
                boolean status = userDao.updatePassword(tokenvalue, newpassword);
                userDao.deleteToken(tokenvalue);
                return status;
                // redirect user to setup a new password page
            } else {
                throw new RuntimeException("OOPS something went wrong. Reset Link Expired");
                // return user a message with invalid token
            }
        }
    }

    public boolean forgetPassword(JSONObject inputEmail) {
        try {

            //Check if email is in the database
            if (userDao.getUserEmailByEmail(inputEmail.getString("email"))) {

                //return user Object based on email found
                User currUser = userDao.getUserByInputEmail(inputEmail.getString("email"));

                //Create web Token based on values with expiration
                String jwtToken = Jwts.builder().claim("last_name", currUser.getLastName()).claim("userId", currUser.getUserId()).claim("email", currUser.getEmail()).setSubject(currUser.getFirstName()).setId(UUID.randomUUID().toString()).setIssuedAt(Date.from(Instant.now())).setExpiration(Date.from(Instant.now().plus(5L, ChronoUnit.MINUTES))).compact();

                //Send Token to Database
                userDao.sendToken(jwtToken, currUser.getUserId());

                Dotenv dotenv = Dotenv.load();
                //Create URL and send email with reset URL
                String frontendUrl = dotenv.get("FRONTEND_HOST");
                String addressUrl =  frontendUrl +"/uservalues?token="+jwtToken;
                int status = EmailUtility.email(inputEmail.getString("email"), "Reset your RevMo password", addressUrl);
                if (status == 202) {
                    return true;
                } else {
                    throw new RuntimeException("The email pertaining to the account has been sent an email. Please check email for reset link.");
                }
            } else {
                throw new RuntimeException("The email pertaining to the account has been sent an email. Please check email for reset link.");
            }
       } catch (Exception e) {
            throw new RuntimeException("The email pertaining to the account has been sent an email. Please check email for reset link.");
        }
    }

    public void userValues(String token) {


        try {

            //Check if token is valid
            boolean tokenStatus = userDao.getTokenStatus(token);

            if(tokenStatus){
                if(java.awt.Desktop.isDesktopSupported()){
                    java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

                    if(desktop.isSupported(Desktop.Action.BROWSE)){
                        java.net.URI uri = new java.net.URI("http://localhost:5051/resetpassword.html");
                        desktop.browse(uri);
                    }

                }
            }else{
                throw new RuntimeException(" Reset Link Expired");
            }
        }catch (Exception e){
            throw new RuntimeException("Reset Link Expired");
        }


    }
}

