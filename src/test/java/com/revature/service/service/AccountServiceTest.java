package com.revature.service.service;

import com.revature.dao.AccountDao;
import com.revature.model.Account;
import com.revature.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceTest {
    @Test
    public void testGetAccountsByEmail() throws SQLException {
        // Arrange
        AccountDao mockedObject = mock(AccountDao.class);
        String email = "jd80@a.ca";
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account(1, 1, 20));
        accountList.add(new Account(2, 1, 15));
        accountList.add(new Account(3, 1, 30));

        // Whenever the code in the Service layer calls the getAllStudents() method
        // for the dao layer, then return the list of students
        // we have specified above
        when(mockedObject.getAccountsByEmail(email)).thenReturn(accountList);

        AccountService accountService = new AccountService(mockedObject);

        // Act
        List<Account> actual = accountService.getAccountsByEmail(email);
//
//        // Assert
        List<Account> expected = new ArrayList<>(accountList);
        Assertions.assertEquals(expected, actual);
    }

}
