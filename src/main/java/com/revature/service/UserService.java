package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.exceptions.InvalidLoginException;
import com.revature.model.User;

import java.sql.SQLException;

public class UserService {
    private UserDao userDao;

    public UserService() {

        userDao = new UserDao();
    }

    public User login(String email, String password) throws SQLException, InvalidLoginException {
        User user = userDao.getUserByEmailAndPassword(email, password);

        if (user == null) {
            throw new InvalidLoginException("Invalid email and/or password");
        }
        return user;
    }
<<<<<<< Updated upstream
=======

    public User getUserByEmail(String email) {


        return userDao.getUserByEmail(email);

    }

    public void updateInfo(Map<String, String> newInfo, int userId, String oldEmail) throws InvalidParameterException {
        InvalidParameterException exceptions = new InvalidParameterException();
        String newFirstName = newInfo.get("firstName");
        String newLastName = newInfo.get("lastName");
        String newPhone = newInfo.get("phone");
        User oldUser = userDao.getUserByEmail(oldEmail);
        if (userId != oldUser.getUserId()) {
            exceptions.addMessage("User Id does not match our records.");
            throw exceptions;
        }

        if (!Objects.equals(newFirstName, oldUser.getFirstName())) {
            userDao.updateFirstName(userId, newFirstName);
        }
        if (!Objects.equals(newLastName, oldUser.getLastName())) {
            userDao.updateLastName(userId, newLastName);
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
                String addressUrl =  frontendUrl +"/resetpassword?token="+jwtToken;
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
>>>>>>> Stashed changes
}

