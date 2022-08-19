package com.revature.service;

        import com.revature.dao.AccountDao;
        import com.revature.exception.InvalidParameterException;
        import com.revature.model.Account;
        import com.revature.model.User;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.Test;
        import java.sql.SQLException;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import static org.junit.jupiter.api.Assertions.fail;
        import static org.mockito.Mockito.*;

public class AccountServiceTest {
    @Test
    public void testOpenAccountPositive() throws InvalidParameterException {
        // Arrange

        AccountDao mockedObject = mock(AccountDao.class);
        Account mockAccount = new Account(0, 1, 0);
        Account returnAccount = new Account(1,1,null, 0);
        Map<String, String> addAccount = new HashMap<>();
        addAccount.put("typeId", "1");
        addAccount.put("balance", "0");

        // Whenever the code in the Service layer calls the getAllStudents() method
        // for the dao layer, then return the list of students
        // we have specified above
        when(mockedObject.openAccount(mockAccount)).thenReturn(returnAccount);

        AccountService accountService = new AccountService(mockedObject);

        // Act
        Account actual = accountService.openAccount(addAccount);
//
//        // Assert
        Account expected = new Account(1,1,0);
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testGetAccountsByEmailPositive() throws SQLException {
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

    @Test
    public void testGetAccountsByEmailandAccountId() throws SQLException {
        // Arrange
        AccountDao mockedObject = mock(AccountDao.class);
        String email = "jd80@a.ca";
        int id = 3;

        Account account = (new Account(1, 1, 20));
        Account account2 =(new Account(2, 1, 15));
        Account account3 =(new Account(3, 1, 30));

        // Whenever the code in the Service layer calls the getAllStudents() method
        // for the dao layer, then return the list of students
        // we have specified above
        when(mockedObject.getAccountByEmailAndAccountId(email, id)).thenReturn(account3);

        AccountService accountService = new AccountService(mockedObject);

        // Act
        Account actual = accountService.getAccountByEmailAndAccountId(email, id);
//
//        // Assert
        Account expected = new Account(3,1,30);
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testLinkToAccount() throws InvalidParameterException, SQLException {
        // Arrange

        AccountDao mockedObject = mock(AccountDao.class);
        Account mockAccount = new Account(1, 1, 0);
        User mockuser = new User(1, "John", "Doe", "jd@outlook.com", "password",
                "1234567897", "user");

        // Whenever the code in the Service layer calls the getAllStudents() method
        // for the dao layer, then return the list of students
        // we have specified above
        String mockReturn = ("Account " + mockAccount.getAccountId() + " successfully linked to user" +
                " " +  mockuser.getUserId() + "!");
        when(mockedObject.linkUserToAccount(1,1)).thenReturn(mockReturn);

        AccountService accountService = new AccountService(mockedObject);

        // Act
        String actual = accountService.linkUserToAccount(mockAccount.getAccountId(),mockuser.getUserId());
//
//        // Assert
        String expected = new String("Account " + 1 + " successfully linked to user " + 1 + "!");
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testUnlinkToAccount() throws InvalidParameterException, SQLException {
        // Arrange

        AccountDao mockedObject = mock(AccountDao.class);
        Account mockAccount = new Account(1, 1, 0);
        User mockuser = new User(1, "John", "Doe", "jd@outlook.com", "password",
                "1234567897", "user");

        // Whenever the code in the Service layer calls the getAllStudents() method
        // for the dao layer, then return the list of students
        // we have specified above
        String mockReturn = ("Account " + mockAccount.getAccountId() + " successfully unlinked from user" +
                " " +  mockuser.getUserId() + "!");
        when(mockedObject.unlinkUserFromAccount(1,1)).thenReturn(mockReturn);

        AccountService accountService = new AccountService(mockedObject);

        // Act
        String actual = accountService.unlinkUserFromAccount(mockAccount.getAccountId(),mockuser.getUserId());
//
//        // Assert
        String expected = new String("Account " + 1 + " successfully unlinked from user " + 1 + "!");
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testDeleteAccountPositive() throws InvalidParameterException, SQLException {
        // Arrange

        AccountDao mockedObject = mock(AccountDao.class);
        Account mockAccount = new Account(1, 1, 0);
        List<String> mockowners = new ArrayList<>();
        mockowners.add("John Doe");
        User mockuser = new User(1, "John", "Doe", "jd@outlook.com", "password",
                "1234567897", "user");
        String mockReturn = ("1");

        when(mockedObject.getAccountById(1)).thenReturn(mockAccount);
        when(mockedObject.obtainListOfAccountOwners(1)).thenReturn(mockowners);
        when(mockedObject.deleteAccount(mockAccount.getAccountId())).thenReturn(mockReturn);

        AccountService accountService = new AccountService(mockedObject);

        // Act
        String actual = accountService.deleteAccount(mockAccount.getAccountId());
//
//        // Assert
        String expected = new String("1");
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testObtainListOfAccountOwners() throws InvalidParameterException, SQLException {
        // Arrange

        AccountDao mockedObject = mock(AccountDao.class);
        Account mockAccount = new Account(1, 1, 0);
        List<String> mockowners = new ArrayList<>();
        mockowners.add("John Doe");
        mockowners.add("Jane Doe");
        mockowners.add("Jack Black");

        when(mockedObject.obtainListOfAccountOwners(mockAccount.getAccountId())).thenReturn(mockowners);

        AccountService accountService = new AccountService(mockedObject);

        // Act
        List<String> actual = accountService.obtainListOfAccountOwners(1);
//
//        // Assert
        List<String> expected = mockowners;
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testDeleteAccountBalanceNotZero() throws SQLException {
        // Arrange

        AccountDao mockedObject = mock(AccountDao.class);
        AccountService accountService = new AccountService(mockedObject);

        Account mockAccount = new Account(1, 1, 500);

        List<String> mockReturn = new ArrayList<>();
        mockReturn.add("Account balance must be 0!");

        when(mockedObject.getAccountById(1)).thenReturn(mockAccount);
        try {
            accountService.deleteAccount(1);
            fail();
        } catch (InvalidParameterException e){
            // Act
            List<String> actual = e.getMessages();
//
//        // Assert
            List<String> expected = mockReturn;
            Assertions.assertEquals(expected, actual);
        }}
    @Test
    public void testDeleteAccountMoreThanOneLinkedUser() throws SQLException {
        // Arrange

        AccountDao mockedObject = mock(AccountDao.class);
        AccountService accountService = new AccountService(mockedObject);

        Account mockAccount = new Account(1, 1, 0);

        List<String> mockReturn = new ArrayList<>();
        mockReturn.add("An account with more than one linked user cannot be deleted!");
        List<String> mockowners = new ArrayList<>();
        mockowners.add("John Doe");
        mockowners.add("Jane Doe");
        mockowners.add("Jack Black");

        when(mockedObject.getAccountById(1)).thenReturn(mockAccount);
        when(mockedObject.obtainListOfAccountOwners(1)).thenReturn(mockowners);

        try {
            accountService.deleteAccount(1);
            fail();
        } catch (InvalidParameterException e){
            // Act
            List<String> actual = e.getMessages();
//
//        // Assert
            List<String> expected = mockReturn;
            Assertions.assertEquals(expected, actual);
        }}
    @Test
    public void testDeleteAccountbothFail() throws SQLException {
        // Arrange

        AccountDao mockedObject = mock(AccountDao.class);
        AccountService accountService = new AccountService(mockedObject);

        Account mockAccount = new Account(1, 1, 2000);

        List<String> mockReturn = new ArrayList<>();
        mockReturn.add("Account balance must be 0!");
        mockReturn.add("An account with more than one linked user cannot be deleted!");

        List<String> mockowners = new ArrayList<>();
        mockowners.add("John Doe");
        mockowners.add("Jane Doe");
        mockowners.add("Jack Black");

        when(mockedObject.getAccountById(1)).thenReturn(mockAccount);
        when(mockedObject.obtainListOfAccountOwners(1)).thenReturn(mockowners);

        try {
            accountService.deleteAccount(1);
            fail();
        } catch (InvalidParameterException e){
            // Act
            List<String> actual = e.getMessages();
//
//        // Assert
            List<String> expected = mockReturn;
            Assertions.assertEquals(expected, actual);
        }}
}

