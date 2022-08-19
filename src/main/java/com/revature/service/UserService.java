package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.exception.InvalidParameterException;
import com.revature.model.User;
import com.revature.exception.InvalidLoginException;

import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao mockedObject) {
        userDao = mockedObject;
    }

    public static boolean getUserEmailByEmail(String email){
        return UserDao.getUserEmailByEmail(email);
    }
    public static void updatePassword(String password, String token){
        UserDao.updatePassword(password, token);
    }

    public static User getUserByInputEmail(String inputEmail) {
        return UserDao.getUserByInputEmail(inputEmail);
    }

    public static void sendToken(String token, int userId) {
        UserDao.sendToken(token, userId);
    }

    public static boolean validateToken(String token) {
        return UserDao.validateToken(token);
    }

    public static void deleteToken(String token) {
        UserDao.deleteToken(token);
    }
    public User login(String email, String password) throws SQLException, InvalidLoginException {
        User user = userDao.getUserByEmailAndPassword(email, password);

        if (user == null) {
            throw new InvalidLoginException("Invalid email and/or password");
        }
        return user;
    }

    public User getUserByEmail(String email) {
        User user = userDao.getUserByEmail(email);
        return user;
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
        if (!Objects.equals(newEmail, oldUser.getEmail()) && userDao.getUserByEmail(newEmail) != null){
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


}

