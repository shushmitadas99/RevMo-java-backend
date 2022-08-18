package com.revature.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Transaction {

    private String initiatedBy;
    private String receivingEmail;
    private String typeName;
    private int transactionId;
    private int requestId;
    private int sendingId;
    private int receivingId;
    private Timestamp requestTime;
    private Timestamp resolveTime;
    private boolean approve;
    private int status_id;
    private int descriptionId;
    private long balance;

    private String description;


    public Transaction(int transactionId, int requesterId, int sendingId, int receivingId,
                       Timestamp requestTime, Timestamp resolveTime, boolean approve,
                       int status_id, int descriptionId, long balance) {

        this.transactionId = transactionId;
        this.requestId = requesterId;
        this.sendingId = sendingId;
        this.receivingId = receivingId;
        this.requestTime = requestTime;
        this.resolveTime = resolveTime;
        this.approve = approve;
        this.status_id = status_id;
        this.descriptionId = descriptionId;
        this.balance = balance;
    }

    public Transaction() {

    }

    public Transaction(int transactionId, int requesterId, int sendingId, int receivingId,
                       Timestamp reqTime, Timestamp resTime, boolean approve,
                       String receivingEmail, String initiatedBy, String typeName,
                       String description, long amount) {
        this.transactionId = transactionId;
        this.requestId = requesterId;
        this.sendingId = sendingId;
        this.receivingId = receivingId;
        this.requestTime = reqTime;
        this.resolveTime = resTime;
        this.approve = approve;
        this.typeName = typeName;
        this.receivingEmail = receivingEmail;
        this.initiatedBy = initiatedBy;
        this.description = description;
        this.balance = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInitiatedBy() {
        return initiatedBy;
    }

    public void setInitiatedBy(String initiatedBy) {
        this.initiatedBy = initiatedBy;
    }

    public String getReceivingEmail() {
        return receivingEmail;
    }

    public void setReceivingEmail(String receivingEmail) {
        this.receivingEmail = receivingEmail;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isApprove() {
        return approve;
    }

    public void setApprove(boolean approve) {
        this.approve = approve;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getSendingId() {
        return sendingId;
    }

    public void setSendingId(int sendingId) {
        this.sendingId = sendingId;
    }

    public int getReceivingId() {
        return receivingId;
    }

    public void setReceivingId(int receivingId) {
        this.receivingId = receivingId;
    }

    public Timestamp getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Timestamp requestTime) {
        this.requestTime = requestTime;
    }

    public Timestamp getResolveTime() {
        return resolveTime;
    }

    public void setResolveTime(Timestamp resolveTime) {
        this.resolveTime = resolveTime;
    }


    public int getStatus_id() {return status_id;}

    public void setApproved(int status) {
        this.status_id = status;

    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(int descriptionId) {
        this.descriptionId = descriptionId;
    }


    public void setBalance(long balance){this.balance = balance;}

    public long getBalance(){return balance;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return getTransactionId() == that.getTransactionId() && getRequestId() == that.getRequestId() && getSendingId() == that.getSendingId() && getReceivingId() == that.getReceivingId() && isApprove() == that.isApprove() && getStatus_id() == that.getStatus_id() && getDescriptionId() == that.getDescriptionId() && getBalance() == that.getBalance() && getInitiatedBy().equals(that.getInitiatedBy()) && getReceivingEmail().equals(that.getReceivingEmail()) && getTypeName().equals(that.getTypeName()) && getRequestTime().equals(that.getRequestTime()) && Objects.equals(getResolveTime(), that.getResolveTime()) && getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInitiatedBy(), getReceivingEmail(), getTypeName(), getTransactionId(), getRequestId(), getSendingId(), getReceivingId(), getRequestTime(), getResolveTime(), isApprove(), getStatus_id(), getDescriptionId(), getBalance(), getDescription());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "initiatedBy='" + initiatedBy + '\'' +
                ", receivingEmail='" + receivingEmail + '\'' +
                ", typeName='" + typeName + '\'' +
                ", transactionId=" + transactionId +
                ", requestId=" + requestId +
                ", sendingId=" + sendingId +
                ", receivingId=" + receivingId +
                ", requestTime=" + requestTime +
                ", resolveTime=" + resolveTime +
                ", approve=" + approve +
                ", status_id=" + status_id +
                ", descriptionId=" + descriptionId +
                ", balance=" + balance +
                ", description='" + description + '\'' +
                '}';
    }
}
