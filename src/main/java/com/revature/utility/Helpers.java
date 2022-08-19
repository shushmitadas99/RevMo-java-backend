package com.revature.utility;

import com.revature.exception.InvalidParameterException;
import com.revature.model.Account;
import com.revature.model.Transaction;

import java.util.Map;

public class Helpers {

    public static Boolean validateAccount(Account acc) throws InvalidParameterException{

        return true;
    }

    public static int validatePositiveInt(String s, String val) throws InvalidParameterException {
        InvalidParameterException exceptions = new InvalidParameterException();
        int n = 0;
        try {
            n = Integer.parseInt(s);
            if (n < 1) {
                exceptions.addMessage(val + " must be a non-zero positive number.");
            }
        } catch (NumberFormatException e) {
            exceptions.addMessage(val + " <" + s + "> is an invalid value. Numeric value is expected.");
        }
        if (exceptions.containsMessage()) {
            throw exceptions;
        }
        return n;
    }

    public static Long validatePositiveLong(String s, String val) throws InvalidParameterException {
        InvalidParameterException exceptions = new InvalidParameterException();
        long n = 0L;
        try {
            n = Long.parseLong(s);
            if (n < 1) {
                exceptions.addMessage(val + " must be a non-zero positive number.");
            }
        } catch (NumberFormatException e) {
            exceptions.addMessage(val + " <" + s + "> is an invalid value. Numeric value is expected.");
        }
        if (exceptions.containsMessage()) {
            throw exceptions;
        }
        return n;
    }


    public static Transaction validateTransactionParams(Map<String, String> trx) throws InvalidParameterException{

        Transaction t = new Transaction();
        String requesterId = trx.get("requesterId");
        t.setRequesterId(validatePositiveInt(requesterId, "User ID"));
        String sendingId = trx.get("sendingId");
        t.setSendingId(validatePositiveInt(sendingId, "Sending Account ID"));
        String receivingId = trx.get("receivingId");
        t.setReceivingId(validatePositiveInt(receivingId, "Receiving Account ID"));
        String statusId = trx.get("statusId");
        t.setStatusId(validatePositiveInt(statusId, "Transaction Status ID"));
        String descriptionId = trx.get("descriptionId");
        t.setDescriptionId(validatePositiveInt(descriptionId, "Transaction Description ID"));
        String amount = trx.get("amount");
        t.setAmount(validatePositiveLong(amount, "Transaction amount"));
        t.setReceivingEmail(trx.get("receivingEmail"));
        return t;
    }


}
