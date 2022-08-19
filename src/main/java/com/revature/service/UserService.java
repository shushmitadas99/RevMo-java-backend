package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.model.User;
import com.revature.exception.InvalidLoginException;

import java.sql.SQLException;

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


}

