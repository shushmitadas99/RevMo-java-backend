package com.revature.service;

import com.revature.dao.UserDao;

public class UserService {
    private UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }
}
