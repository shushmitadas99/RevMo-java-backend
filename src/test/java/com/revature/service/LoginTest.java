package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.exception.InvalidLoginException;
import com.revature.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginTest {

    @Test
    public void testLogin() throws SQLException, InvalidLoginException {

        UserDao mockUserDao = mock(UserDao.class);
        String email = "jd80@a.ca";
        String password = "Password123!";

        User user = new User(1, "John", "Doe", "jd80@a.ca", "Password123!", "555-555-5000", "1");
        when(mockUserDao.getUserByEmailAndPassword(email, password)).thenReturn(user);
        UserService userService = new UserService(mockUserDao);
        User actual = userService.login(email, password);
        Assertions.assertEquals(user, actual);
    }
}
