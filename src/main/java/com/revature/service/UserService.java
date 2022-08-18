package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.model.User;

public class UserService {
    private UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    public static boolean getUserEmailByEmail(String email){
        return UserDao.getUserEmailByEmail(email);
    }
    public static void updatePassword(String password, String token){
        UserDao.updatePassword(password, token);
    }

    public static User getUserByEmail(String inputEmail) {
        return UserDao.getUserByEmail(inputEmail);
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
}
