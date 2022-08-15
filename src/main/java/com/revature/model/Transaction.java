package com.revature.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Transaction {

    private int transactionId;
    private int sendingId;
    private int receivingId;
    private Timestamp requestTime;
    private Timestamp resolveTime;
    private boolean approved;
    private int descriptionId;

    public Transaction(int transactionId, int sendingId, int receivingId, Timestamp requestTime, Timestamp resolveTime, boolean approved, int descriptionId) {
        this.transactionId = transactionId;
        this.sendingId = sendingId;
        this.receivingId = receivingId;
        this.requestTime = requestTime;
        this.resolveTime = resolveTime;
        this.approved = approved;
        this.descriptionId = descriptionId;
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

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public int getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(int descriptionId) {
        this.descriptionId = descriptionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionId == that.transactionId && sendingId == that.sendingId && receivingId == that.receivingId && approved == that.approved && descriptionId == that.descriptionId && Objects.equals(requestTime, that.requestTime) && Objects.equals(resolveTime, that.resolveTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, sendingId, receivingId, requestTime, resolveTime, approved, descriptionId);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", sendingId=" + sendingId +
                ", receivingId=" + receivingId +
                ", requestTime=" + requestTime +
                ", resolveTime=" + resolveTime +
                ", approved=" + approved +
                ", descriptionId=" + descriptionId +
                '}';
    }
}
