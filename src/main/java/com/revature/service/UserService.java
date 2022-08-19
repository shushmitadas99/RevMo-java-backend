package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.exception.InvalidLoginException;
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

    public User getUserByEmail(String email) {
        User user = userDao.getUserByEmail(email);
        return user;
    }


}

