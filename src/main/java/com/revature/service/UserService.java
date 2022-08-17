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

    public User login(String email, String pass) throws SQLException, InvalidLoginException {
        User user = userDao.getUserByEmailAndPassword(email, pass);

        if (user == null) {
            throw new InvalidLoginException("Invalid email and/or password");
        }
        return user;
    }
}

