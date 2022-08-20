package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.dao.TransactionDao;
import com.revature.exception.InvalidParameterException;
import com.revature.model.Transaction;
import com.revature.utility.Helpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionServiceTest {
    @Test
    public void moveAmountBetweenSameOwnerAccountsPositive() throws InvalidParameterException {
        // Arrange
        TransactionDao mockedObject = mock(TransactionDao.class);
        AccountDao mockedObject2 = mock(AccountDao.class);
        TransactionService transactionService = new TransactionService(mockedObject, mockedObject2);

        Transaction t = new Transaction(1, 1, 2, 2, 1, "jd80@a,ca", 5000);
        t.setReceivingEmail("jd80@a.ca");
        Map<String, String> trx = new HashMap<>();
        trx.put("requesterId", "1");
        trx.put("sendingId", "1");
        trx.put("receivingId", "2");
        trx.put("statusId", "2");
        trx.put("descriptionId", "1");
        trx.put("amount", "5000");
        trx.put("receivingEmail", "jd80@a.ca");

        when(mockedObject.moveAmountBetweenSameOwnerAccounts(t)).thenReturn("Transaction Successful");
        when(mockedObject2.isOwnerOfAccount(1, 1)).thenReturn(true);
        when(mockedObject2.isOwnerOfAccount(1, 2)).thenReturn(true);
        when(mockedObject2.canWithdraw(1, 5000)).thenReturn(true);

        String actual = transactionService.moveAmountBetweenSameOwnerAccounts(trx);

        String expected = "Transaction Successful";

        // Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void moveAmountBetweenSameOwnerAccountsNegativeInvalidInt() throws InvalidParameterException {
        // Arrange
        TransactionDao mockedObject = mock(TransactionDao.class);
        AccountDao mockedObject2 = mock(AccountDao.class);
        TransactionService transactionService = new TransactionService(mockedObject, mockedObject2);

        Transaction t = new Transaction(1, 1, 2, 2, 1, "jd80@a,ca", 5000);
        t.setReceivingEmail("jd80@a.ca");
        Map<String, String> trx = new HashMap<>();
        trx.put("requesterId", "abc");
        trx.put("sendingId", "1");
        trx.put("receivingId", "2");
        trx.put("statusId", "2");
        trx.put("descriptionId", "1");
        trx.put("amount", "5000");
        trx.put("receivingEmail", "jd80@a.ca");

        List<String> mockReturn = new ArrayList<>();
        mockReturn.add("User ID <" + trx.get("requesterId") + "> is an invalid value. Numeric value is expected.");
        when(mockedObject2.isOwnerOfAccount(1, 1)).thenReturn(true);
        when(mockedObject2.isOwnerOfAccount(1, 2)).thenReturn(true);
        when(mockedObject2.canWithdraw(1, 5000)).thenReturn(true);

        try {
            transactionService.moveAmountBetweenSameOwnerAccounts(trx);
            fail();
        } catch (InvalidParameterException e) {
            // Act
            List<String> actual = e.getMessages();

            // Assert
            Assertions.assertEquals(mockReturn, actual);
        }
    }

    @Test
    public void moveAmountBetweenSameOwnerAccountsNegativeInt() throws InvalidParameterException {
        // Arrange
        TransactionDao mockedObject = mock(TransactionDao.class);
        AccountDao mockedObject2 = mock(AccountDao.class);
        TransactionService transactionService = new TransactionService(mockedObject, mockedObject2);

        Transaction t = new Transaction(1, 1, 2, 2, 1, "jd80@a,ca", 5000);
        t.setReceivingEmail("jd80@a.ca");
        Map<String, String> trx = new HashMap<>();
        trx.put("requesterId", "-1");
        trx.put("sendingId", "1");
        trx.put("receivingId", "2");
        trx.put("statusId", "2");
        trx.put("descriptionId", "1");
        trx.put("amount", "5000");
        trx.put("receivingEmail", "jd80@a.ca");

        List<String> mockReturn = new ArrayList<>();
        mockReturn.add("User ID must be a non-zero positive number.");
        when(mockedObject2.isOwnerOfAccount(1, 1)).thenReturn(true);
        when(mockedObject2.isOwnerOfAccount(1, 2)).thenReturn(true);
        when(mockedObject2.canWithdraw(1, 5000)).thenReturn(true);

        try {
            transactionService.moveAmountBetweenSameOwnerAccounts(trx);
            fail();
        } catch (InvalidParameterException e) {
            // Act
            List<String> actual = e.getMessages();

            // Assert
            Assertions.assertEquals(mockReturn, actual);
        }
    }

    @Test
    public void moveAmountBetweenSameOwnerAccountsNegativeBoundary0() throws InvalidParameterException {
        // Arrange
        TransactionDao mockedObject = mock(TransactionDao.class);
        AccountDao mockedObject2 = mock(AccountDao.class);
        TransactionService transactionService = new TransactionService(mockedObject, mockedObject2);

        Transaction t = new Transaction(1, 1, 2, 2, 1,
                "jd80@a,ca", 5000);
        t.setReceivingEmail("jd80@a.ca");
        Map<String, String> trx = new HashMap<>();
        trx.put("requesterId", "0");
        trx.put("sendingId", "1");
        trx.put("receivingId", "2");
        trx.put("statusId", "2");
        trx.put("descriptionId", "1");
        trx.put("amount", "5000");
        trx.put("receivingEmail", "jd80@a.ca");

        List<String> mockReturn = new ArrayList<>();
        mockReturn.add("User ID must be a non-zero positive number.");
        when(mockedObject2.isOwnerOfAccount(1, 1)).thenReturn(true);
        when(mockedObject2.isOwnerOfAccount(1, 2)).thenReturn(true);
        when(mockedObject2.canWithdraw(1, 5000)).thenReturn(true);

        try {
            transactionService.moveAmountBetweenSameOwnerAccounts(trx);
            fail();
        } catch (InvalidParameterException e) {
            // Act
            List<String> actual = e.getMessages();

            // Assert
            Assertions.assertEquals(mockReturn, actual);
        }
    }

    @Test
    public void moveAmountBetweenSameOwnerAccountsNegativeInvalidNumericForLong() throws InvalidParameterException {
        // Arrange
        TransactionDao mockedObject = mock(TransactionDao.class);
        AccountDao mockedObject2 = mock(AccountDao.class);
        TransactionService transactionService = new TransactionService(mockedObject, mockedObject2);

        Transaction t = new Transaction(1, 1, 2, 2, 1,
                "jd80@a,ca", 5000);
        t.setReceivingEmail("jd80@a.ca");
        Map<String, String> trx = new HashMap<>();
        trx.put("requesterId", "1");
        trx.put("sendingId", "1");
        trx.put("receivingId", "2");
        trx.put("statusId", "2");
        trx.put("descriptionId", "1");
        trx.put("amount", "../");
        trx.put("receivingEmail", "jd80@a.ca");

        List<String> mockReturn = new ArrayList<>();
        mockReturn.add("Transaction amount <" + trx.get("amount") + "> is an invalid value. Numeric value is expected.");
        when(mockedObject2.isOwnerOfAccount(1, 1)).thenReturn(true);
        when(mockedObject2.isOwnerOfAccount(1, 2)).thenReturn(true);
        when(mockedObject2.canWithdraw(1, 5000)).thenReturn(true);

        try {
            transactionService.moveAmountBetweenSameOwnerAccounts(trx);
            fail();
        } catch (InvalidParameterException e) {
            // Act
            List<String> actual = e.getMessages();

            // Assert
            Assertions.assertEquals(mockReturn, actual);
        }
    }

    @Test
    public void moveAmountBetweenSameOwnerAccountsNegativeLong() throws InvalidParameterException {
        // Arrange
        TransactionDao mockedObject = mock(TransactionDao.class);
        AccountDao mockedObject2 = mock(AccountDao.class);
        TransactionService transactionService = new TransactionService(mockedObject, mockedObject2);

        Transaction t = new Transaction(1, 1, 2, 2, 1,
                "jd80@a,ca", 5000);
        t.setReceivingEmail("jd80@a.ca");
        Map<String, String> trx = new HashMap<>();
        trx.put("requesterId", "1");
        trx.put("sendingId", "1");
        trx.put("receivingId", "2");
        trx.put("statusId", "2");
        trx.put("descriptionId", "1");
        trx.put("amount", "-1");
        trx.put("receivingEmail", "jd80@a.ca");

        List<String> mockReturn = new ArrayList<>();
        mockReturn.add("Transaction amount must be a non-zero positive number.");
        when(mockedObject2.isOwnerOfAccount(1, 1)).thenReturn(true);
        when(mockedObject2.isOwnerOfAccount(1, 2)).thenReturn(true);
        when(mockedObject2.canWithdraw(1, 5000)).thenReturn(true);

        try {
            transactionService.moveAmountBetweenSameOwnerAccounts(trx);
            fail();
        } catch (InvalidParameterException e) {
            // Act
            List<String> actual = e.getMessages();

            // Assert
            Assertions.assertEquals(mockReturn, actual);
        }
    }


    @Test
    public void moveAmountBetweenSameOwnerAccountsNegativeBoundary0Long() throws InvalidParameterException {
        // Arrange
        TransactionDao mockedObject = mock(TransactionDao.class);
        AccountDao mockedObject2 = mock(AccountDao.class);
        TransactionService transactionService = new TransactionService(mockedObject, mockedObject2);

        Transaction t = new Transaction(1, 1, 2, 2, 1,
                "jd80@a,ca", 5000);
        t.setReceivingEmail("jd80@a.ca");
        Map<String, String> trx = new HashMap<>();
        trx.put("requesterId", "1");
        trx.put("sendingId", "1");
        trx.put("receivingId", "2");
        trx.put("statusId", "2");
        trx.put("descriptionId", "1");
        trx.put("amount", "0");
        trx.put("receivingEmail", "jd80@a.ca");

        List<String> mockReturn = new ArrayList<>();
        mockReturn.add("Transaction amount must be a non-zero positive number.");
        when(mockedObject2.isOwnerOfAccount(1, 1)).thenReturn(true);
        when(mockedObject2.isOwnerOfAccount(1, 2)).thenReturn(true);
        when(mockedObject2.canWithdraw(1, 5000)).thenReturn(true);

        try {
            transactionService.moveAmountBetweenSameOwnerAccounts(trx);
            fail();
        } catch (InvalidParameterException e) {
            // Act
            List<String> actual = e.getMessages();

            // Assert
            Assertions.assertEquals(mockReturn, actual);
        }
    }

    @Test
    public void moveAmountBetweenSameOwnerAccountsNegativeEmptyEmail() throws InvalidParameterException {
        // Arrange
        TransactionDao mockedObject = mock(TransactionDao.class);
        AccountDao mockedObject2 = mock(AccountDao.class);
        TransactionService transactionService = new TransactionService(mockedObject, mockedObject2);

        Transaction t = new Transaction(1, 1, 2, 2, 1,
                "jd80@a,ca", 5000);
        t.setReceivingEmail("jd80@a.ca");
        Map<String, String> trx = new HashMap<>();
        trx.put("requesterId", "1");
        trx.put("sendingId", "1");
        trx.put("receivingId", "2");
        trx.put("statusId", "2");
        trx.put("descriptionId", "1");
        trx.put("amount", "5000");
        trx.put("receivingEmail", "");

        List<String> mockReturn = new ArrayList<>();
        mockReturn.add("Value for receivingEmail must exist.");
        when(mockedObject2.isOwnerOfAccount(1, 1)).thenReturn(true);
        when(mockedObject2.isOwnerOfAccount(1, 2)).thenReturn(true);
        when(mockedObject2.canWithdraw(1, 5000)).thenReturn(true);

        try {
            transactionService.moveAmountBetweenSameOwnerAccounts(trx);
            fail();
        } catch (InvalidParameterException e) {
            // Act
            List<String> actual = e.getMessages();

            // Assert
            Assertions.assertEquals(mockReturn, actual);
        }
    }

    @Test
    public void moveAmountBetweenSameOwnerAccountsNegativeInvalidEmailFormat() throws InvalidParameterException {
        // Arrange
        TransactionDao mockedObject = mock(TransactionDao.class);
        AccountDao mockedObject2 = mock(AccountDao.class);
        TransactionService transactionService = new TransactionService(mockedObject, mockedObject2);

        Transaction t = new Transaction(1, 1, 2, 2, 1,
                "jd80@a,ca", 5000);
        t.setReceivingEmail("jd80@a.ca");
        Map<String, String> trx = new HashMap<>();
        trx.put("requesterId", "1");
        trx.put("sendingId", "1");
        trx.put("receivingId", "2");
        trx.put("statusId", "2");
        trx.put("descriptionId", "1");
        trx.put("amount", "5000");
        trx.put("receivingEmail", "----");

        List<String> mockReturn = new ArrayList<>();
        mockReturn.add("Value for receivingEmail does not match <username>@<domain> pattern");
        when(mockedObject2.isOwnerOfAccount(1, 1)).thenReturn(true);
        when(mockedObject2.isOwnerOfAccount(1, 2)).thenReturn(true);
        when(mockedObject2.canWithdraw(1, 5000)).thenReturn(true);

        try {
            transactionService.moveAmountBetweenSameOwnerAccounts(trx);
            fail();
        } catch (InvalidParameterException e) {
            // Act
            List<String> actual = e.getMessages();

            // Assert
            Assertions.assertEquals(mockReturn, actual);
        }
    }

    @Test
    public void testGetAllTransactionsPositive() throws InvalidParameterException, SQLException {
        // Arrange
        TransactionDao mockedObject = mock(TransactionDao.class);
        TransactionService transactionService = new TransactionService(mockedObject);
        List<Transaction> trx = new ArrayList<>();
        Transaction transaction1 = new Transaction(1, 1, 1, 1, "jd80@123.com", "JackSparrow@disney.com", "PENDING", "Income", 15000);
        Transaction transaction2 = new Transaction(2, 2, 1, 2, "jacksparrow@disney.com", "JackSparrow@disney.com", "PENDING", "Income", 18000);
        Transaction transaction3 = new Transaction(3, 4, 2, 1, "jd80@123.com", "JackSparrow@disney.com", "PENDING", "Income", 2000);
        trx.add(transaction1);
        trx.add(transaction2);
        trx.add(transaction3);
        List<Transaction> mockReturn = new ArrayList<>();
        mockReturn.add(transaction1);
        mockReturn.add(transaction2);
        mockReturn.add(transaction3);

        when(mockedObject.getAllTransactions()).thenReturn(trx);
        transactionService.getAllTransactions();

        Assertions.assertEquals(trx, mockReturn);
    }

    @Test
    public void testGetAllTransactionsByRequesterIdPositive() throws InvalidParameterException, SQLException {
        // Arrange
        TransactionDao mockedObject = mock(TransactionDao.class);
        TransactionService transactionService = new TransactionService(mockedObject);
        List<Transaction> trx = new ArrayList<>();
        Transaction transaction1 = new Transaction(1, 1, 1, 1, "jd80@123.com", "JackSparrow@disney.com", "PENDING", "Income", 15000);
        trx.add(transaction1);

        List<Transaction> mockReturn = new ArrayList<>();
        mockReturn.add(transaction1);

        when(mockedObject.getAllTransactionsByRequesterId(1)).thenReturn(trx);
        transactionService.getAllTransactionsByRequesterId("1");
        // Assert
        Assertions.assertEquals(trx, mockReturn);
    }

    @Test
    public void testGetAllTransactionsBySenderIdPositive() throws InvalidParameterException, SQLException {
        // Arrange
        TransactionDao mockedObject = mock(TransactionDao.class);
        TransactionService transactionService = new TransactionService(mockedObject);
        List<Transaction> trx = new ArrayList<>();
        Transaction transaction1 = new Transaction(1, 1, 1, 1, "jd80@123.com", "JackSparrow@disney.com", "PENDING", "Income", 15000);
        Transaction transaction2 = new Transaction(2, 2, 1, 2, "jacksparrow@disney.com", "JackSparrow@disney.com", "PENDING", "Income", 18000);
        trx.add(transaction1);
        trx.add(transaction2);
        List<Transaction> mockReturn = new ArrayList<>();
        mockReturn.add(transaction1);
        mockReturn.add(transaction2);


        when(mockedObject.getAllTransactionsBySenderId(1)).thenReturn(trx);
        transactionService.getAllTransactionsBySenderId("1");
        // Assert
        Assertions.assertEquals(trx, mockReturn);
    }

    @Test
    public void testGetAllTransactionsByReceivingIdPositive() throws InvalidParameterException, SQLException {
        // Arrange
        TransactionDao mockedObject = mock(TransactionDao.class);
        TransactionService transactionService = new TransactionService(mockedObject);
        List<Transaction> trx = new ArrayList<>();
        Transaction transaction1 = new Transaction(1, 1, 1, 1, "jd80@123.com", "JackSparrow@disney.com", "PENDING", "Income", 15000);
        Transaction transaction3 = new Transaction(3, 4, 2, 1, "jd80@123.com", "JackSparrow@disney.com", "PENDING", "Income", 2000);
        trx.add(transaction1);
        trx.add(transaction3);
        List<Transaction> mockReturn = new ArrayList<>();
        mockReturn.add(transaction1);
        mockReturn.add(transaction3);

        when(mockedObject.getAllTransactionsByRecievingId(1)).thenReturn(trx);
        transactionService.getAllTransactionsByReceivingId("1");
        // Assert
        Assertions.assertEquals(trx, mockReturn);
    }

    @Test
    public void testGetAllTransactionsByStatusName() throws InvalidParameterException, SQLException {
        // Arrange
        TransactionDao mockedObject = mock(TransactionDao.class);
        TransactionService transactionService = new TransactionService(mockedObject);
        List<Transaction> trx = new ArrayList<>();
        Transaction transaction1 = new Transaction(1, 1, 1, 1, "jd80@123.com", "JackSparrow@disney.com", "PENDING", "Income", 15000);
        Transaction transaction2 = new Transaction(2, 2, 1, 2, "jacksparrow@disney.com", "JackSparrow@disney.com", "PENDING", "Income", 18000);
        trx.add(transaction1);
        trx.add(transaction2);
        List<Transaction> mockReturn = new ArrayList<>();
        mockReturn.add(transaction1);
        mockReturn.add(transaction2);

        when(mockedObject.getAllTransactionsByStatusName("PENDING")).thenReturn(trx);
        transactionService.getAllTransactionsByStatusName("PENDING");
        // Assert
        Assertions.assertEquals(trx, mockReturn);
    }

    @Test
    public void testGetAllTransactionsByDescription() throws InvalidParameterException, SQLException {
        // Arrange
        TransactionDao mockedObject = mock(TransactionDao.class);
        TransactionService transactionService = new TransactionService(mockedObject);
        List<Transaction> trx = new ArrayList<>();
        Transaction transaction1 = new Transaction(1, 1, 1, 1, "jd80@123.com", "JackSparrow@disney.com", "PENDING", "Income", 15000);
        Transaction transaction2 = new Transaction(2, 2, 1, 2, "jacksparrow@disney.com", "JackSparrow@disney.com", "PENDING", "Income", 18000);
        Transaction transaction3 = new Transaction(3, 4, 2, 1, "jd80@123.com", "JackSparrow@disney.com", "PENDING", "Income", 2000);
        trx.add(transaction1);
        trx.add(transaction2);
        trx.add(transaction3);

        List<Transaction> mockReturn = new ArrayList<>();
        mockReturn.add(transaction1);
        mockReturn.add(transaction2);
        mockReturn.add(transaction3);

        when(mockedObject.getAllTransactionsByStatusName("Income")).thenReturn(trx);
        transactionService.getAllTransactionsByStatusName("Income");
        // Assert
        Assertions.assertEquals(trx, mockReturn);
    }
}

