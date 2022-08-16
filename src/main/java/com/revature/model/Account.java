package com.revature.model;

import java.util.Objects;

public class Account {

    private int accountId;
    private int typeId;
    private long balance;

    private String typeName;

    public Account(int accountId, int typeId, String typeName, long balance) {
        this.accountId = accountId;
        this.typeId = typeId;
        this.typeName = typeName;
        this.balance = balance;
    }
    public Account(int accountId, int typeId, long balance) {
        this.accountId = accountId;
        this.typeId = typeId;
        this.balance = balance;
    }

    public Account() {

    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId == account.accountId && typeId == account.typeId && balance == account.balance && Objects.equals(typeName, account.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, typeId, balance, typeName);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", typeId=" + typeId +
                ", balance=" + balance +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
