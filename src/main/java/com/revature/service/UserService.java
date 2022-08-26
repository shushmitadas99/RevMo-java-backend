package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.exception.InvalidParameterException;
import com.revature.model.User;
import com.revature.exception.InvalidLoginException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public void sendToken(String token, int userId) {
        userDao.sendToken(token, userId);
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
        User nUser = userDao.getUserByEmail(email);
//        System.out.println(nUser);
        return nUser;
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
}

